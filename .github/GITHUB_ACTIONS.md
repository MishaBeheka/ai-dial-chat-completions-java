# GitHub Actions Setup

Simple CI/CD automation for this sandbox project.

## Workflows

### `simple-ci.yml`
- **Triggers**: Push to main, Pull requests
- **Actions**: Build, test, package JAR, upload artifacts

### `basic-security.yml` 
- **Triggers**: Push to main, Weekly schedule
- **Actions**: Check dependency versions, basic security review

### `release.yml`
- **Triggers**: Git tags (v*)
- **Actions**: Create GitHub release with JAR files

## Usage

### Development
```bash
git push origin main          # Triggers build and test
```

### Releases
```bash
git tag v1.0.0               # Create release
git push origin v1.0.0       # Triggers release workflow
```

## What Happens

- **Every push**: Build → Test → Package → Upload JAR
- **Weekly**: Basic dependency checks
- **On tags**: Create release with downloadable JARs

Simple and clean for learning GitHub Actions! 🚀

