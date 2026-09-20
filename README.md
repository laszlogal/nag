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
