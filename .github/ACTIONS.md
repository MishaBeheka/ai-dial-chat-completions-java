# GitHub Actions Configuration

This repository uses GitHub Actions for CI/CD automation. The following workflows are configured:

## Workflows Overview

### 1. CI/CD Pipeline (`ci.yml`)
- **Triggers**: Push to `main`/`develop`, Pull requests to `main`
- **Actions**:
  - Build and test with Java 21
  - Run unit tests
  - Package JAR files
  - Upload build artifacts
  - Generate test reports

### 2. Code Quality & Security (`code-quality.yml`)
- **Triggers**: Push, Pull requests, Weekly schedule
- **Actions**:
  - Run OWASP dependency security checks
  - Generate security reports
  - Ready for SonarCloud integration (commented out)

### 3. Code Style Check (`code-style.yml`)
- **Triggers**: Push to `main`/`develop`, Pull requests to `main`
- **Actions**:
  - Check code formatting with Spotless
  - Validate code style (whitespace, line length, tabs)

### 4. Release (`release.yml`)
- **Triggers**: Git tags starting with `v*`
- **Actions**:
  - Build release JAR
  - Generate changelog
  - Create GitHub release
  - Upload release assets

### 5. Dependency Updates (`dependency-updates.yml`)
- **Triggers**: Weekly schedule, Manual trigger
- **Actions**:
  - Check for dependency updates
  - Create pull request with updates

## Required Secrets

The following secrets should be configured in GitHub repository settings:

- `GITHUB_TOKEN` - Automatically provided by GitHub Actions
- `SONAR_TOKEN` - For SonarCloud integration (optional)

## Usage

### Creating a Release
```bash
# Tag a version
git tag v1.0.0
git push origin v1.0.0

# The release workflow will automatically:
# - Build the application
# - Create a GitHub release
# - Upload JAR files as release assets
```

### Manual Dependency Updates
Go to Actions tab → Dependency Updates → Run workflow

### Branch Protection
Recommend configuring branch protection rules for `main`:
- Require pull request reviews
- Require status checks to pass
- Require CI/CD workflow to pass
- Restrict pushes to main branch

## Monitoring
- Check Actions tab for workflow status
- Review security alerts in Security tab
- Monitor dependency updates via pull requests
