# Crazy Critters Attack
A small tower defense game made using Processing.

## Building
Build with Java 17, main class `main.Main`.
To build in debug mode, add argument `dev`.

## Packaging
1. Build > Build Artifacts...
2. Build `Crazy-Critters-Attack-17:jar`
    - If files can't be loaded, check capitalization
3. Copy `out/artifacts/Crazy_Critters_Attack_17_jar/Crazy-Critters-Attack.jar` into a directory
4. Include in the directory the data directory, the JRE, and the system build script.
5. Compress package as `Crazy-Critters-Attack-[system].zip`

## Releasing

### Tag on GitHub
1. Pre release tag format: `mar24a`
2. Set last release as previous release
3. Title release with the date in format "March 20, 2024"
4. Generate release notes
5. Attach release package
6. Set as pre-release

### Release on Itch.io
`butler push [directory] smallbuggames/crazy-critters-attack:[system]-latest --userversion [tag]`
