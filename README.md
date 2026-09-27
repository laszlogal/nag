# Norbi Adventure Games

## Private game assets

The NQ1 images and audio are stored in the private
[`laszlogal/nq1-assets`](https://github.com/laszlogal/nq1-assets) repository using Git LFS.
You need read access to that repository and a working Git LFS installation.

Authenticate GitHub on a new machine, then fetch the asset version pinned by this repository:

```shell
gh auth login --hostname github.com --git-protocol https --web
git lfs install
./gradlew :nq1:fetchAssets
```

The task safely clones or updates the private repository at
`nq1/src/main/java/hu/norbisquest/nq1/resources`. It refuses to overwrite an unrelated
directory, a checkout with local changes, an unexpected remote, or an asset tag that no
longer matches the pinned commit.

To verify an existing checkout without accessing the network:

```shell
./gradlew :nq1:verifyAssets
```

GWT compilation and development-mode tasks run this local verification automatically:

```shell
./gradlew :nq1:gwtCompile
```

## Deploying the GWT output

To copy the generated output from `nq1/build/gwt/war` into the checked-out
`nq1/war` directory:

```shell
./gradlew :nq1:copyGwtToWar
```

To compile and copy the application directly into an existing web-server
application root, without creating an archive or another staging directory:

```shell
./gradlew :nq1:deployGwt -Pnq1DeployDirectory=/absolute/path/to/webroot
```

The direct deployment combines the static files from `nq1/war` with the fresh
GWT module output from `nq1/build/gwt/war`. It does not copy the generated output
back through `nq1/war` first. To keep the machine-specific destination out of the
repository, put the following in `~/.gradle/gradle.properties`:

```properties
nq1DeployDirectory=/absolute/path/to/webroot
```

Then deployment only requires:

```shell
./gradlew :nq1:deployGwt
```

The destination must already exist and must be dedicated to this web application.
Deployment copies files in place and deliberately does not delete unrelated or
older files from the destination.
