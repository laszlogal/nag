FROM eclipse-temurin:21-jdk-jammy

RUN apt-get update \
    && apt-get install --yes --no-install-recommends \
        ca-certificates \
        git \
        git-lfs \
        openssh-client \
    && git lfs install --system \
    && rm -rf /var/lib/apt/lists/*

RUN useradd --create-home --uid 10001 builder

COPY ci/fresh-build.sh /usr/local/bin/fresh-build
RUN chmod 0755 /usr/local/bin/fresh-build

USER builder
WORKDIR /home/builder

ENV NAG_CI_IN_CONTAINER=1

ENTRYPOINT ["/usr/local/bin/fresh-build"]
