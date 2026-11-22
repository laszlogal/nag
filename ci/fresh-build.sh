#!/usr/bin/env bash
set -euo pipefail

readonly DEFAULT_REPOSITORY_URL="https://github.com/laszlogal/nag.git"
readonly DEFAULT_REPOSITORY_REF="main"
readonly DEFAULT_ASSETS_REPOSITORY="git@github.com:laszlogal/nq1-assets.git"
readonly DEFAULT_IMAGE="nag-fresh-build:local"
BUILD_CHECKOUT_DIRECTORY=""

log() {
    printf '\n==> %s\n' "$*"
}

fail() {
    printf 'error: %s\n' "$*" >&2
    exit 1
}

cleanup_checkout() {
    if [[ -n "$BUILD_CHECKOUT_DIRECTORY" ]]; then
        rm -rf -- "$BUILD_CHECKOUT_DIRECTORY"
    fi
}

run_build() {
    local repository_url="${CI_REPOSITORY_URL:-$DEFAULT_REPOSITORY_URL}"
    local repository_ref="${CI_REPOSITORY_REF:-$DEFAULT_REPOSITORY_REF}"
    local assets_repository="${NQ1_ASSETS_REPOSITORY:-$DEFAULT_ASSETS_REPOSITORY}"
    local ssh_agent_status

    mkdir -p "$HOME"
    BUILD_CHECKOUT_DIRECTORY="$(mktemp -d)"
    trap cleanup_checkout EXIT

    log "Cloning ${repository_url} at ${repository_ref}"
    git clone \
        --branch "$repository_ref" \
        --depth 1 \
        --single-branch \
        "$repository_url" \
        "$BUILD_CHECKOUT_DIRECTORY/repository"

    cd "$BUILD_CHECKOUT_DIRECTORY/repository"
    printf 'Building commit %s\n' "$(git rev-parse HEAD)"

    log "Fetching pinned private assets"
    mkdir -p "$HOME/.ssh"
    chmod 0700 "$HOME/.ssh"
    export GIT_SSH_COMMAND="${GIT_SSH_COMMAND:-ssh -o StrictHostKeyChecking=accept-new}"
    if [[ "$assets_repository" == git@* || "$assets_repository" == ssh://* ]]; then
        [[ -S "${SSH_AUTH_SOCK:-}" ]] || fail "No forwarded SSH agent is available for the private assets repository"
        if ! ssh_agent_status="$(ssh-add -l 2>&1)"; then
            fail "Cannot use the forwarded SSH agent:
${ssh_agent_status}"
        fi
    fi
    ./gradlew --no-daemon :nq1:fetchAssets \
        "-Pnq1AssetsRepository=${assets_repository}"

    log "Running tests and compiling the GWT application"
    ./gradlew --no-daemon clean test :nq1:gwtCompile

    log "Fresh-clone build succeeded"
}

run_container() {
    local script_directory repository_root image docker_status
    local -a docker_arguments

    command -v docker >/dev/null 2>&1 || fail "Docker is not installed or is not on PATH"
    if ! docker_status="$(docker info 2>&1)"; then
        fail "Docker is unavailable:
${docker_status}
Start Docker Desktop for the current user and verify it with: docker info"
    fi

    script_directory="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
    repository_root="$(cd "$script_directory/.." && pwd)"
    image="${CI_IMAGE:-$DEFAULT_IMAGE}"

    log "Building clean-room image ${image}"
    docker build --file "$repository_root/ci/Dockerfile" --tag "$image" "$repository_root"

    docker_arguments=(
        run
        --rm
        --env "CI_REPOSITORY_URL=${CI_REPOSITORY_URL:-$DEFAULT_REPOSITORY_URL}"
        --env "CI_REPOSITORY_REF=${CI_REPOSITORY_REF:-$DEFAULT_REPOSITORY_REF}"
        --env "NQ1_ASSETS_REPOSITORY=${NQ1_ASSETS_REPOSITORY:-$DEFAULT_ASSETS_REPOSITORY}"
    )

    case "$(uname -s)" in
        Darwin)
            docker_arguments+=(
                --group-add 0
                --mount "type=bind,source=/run/host-services/ssh-auth.sock,target=/run/host-services/ssh-auth.sock"
                --env "SSH_AUTH_SOCK=/run/host-services/ssh-auth.sock"
            )
            ;;
        Linux)
            [[ -n "${SSH_AUTH_SOCK:-}" ]] || fail "SSH_AUTH_SOCK is not set; start an SSH agent and add the assets deploy key"
            [[ -S "$SSH_AUTH_SOCK" ]] || fail "SSH_AUTH_SOCK does not point to a socket: $SSH_AUTH_SOCK"
            docker_arguments+=(
                --user "$(id -u):$(id -g)"
                --mount "type=bind,source=${SSH_AUTH_SOCK},target=/run/ssh-agent.sock"
                --env "SSH_AUTH_SOCK=/run/ssh-agent.sock"
                --env "HOME=/tmp/nag-ci-home"
            )
            ;;
        *)
            fail "Unsupported host operating system: $(uname -s)"
            ;;
    esac

    log "Starting fresh clone build"
    docker "${docker_arguments[@]}" "$image"
}

if [[ "${NAG_CI_IN_CONTAINER:-}" == "1" ]]; then
    run_build
else
    run_container
fi
