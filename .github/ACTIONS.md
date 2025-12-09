# GitHub Actions Configuration

This repository uses GitHub Actions for CI/CD automation. The following workflows are configured:

## Workflows Overview

### 1. CI/CD Pipeline (`ci.yml`)
- **Triggers**: Push to `main`/`develop`, Pull requests to `main`
- **Permissions**: Contents read, Issues read, Checks write, Pull-requests write
- **Actions**:
  - Build and test with Java 21
  - Run unit tests with detailed reporting
  - Package JAR files
  - Upload build and test artifacts
  - Generate test result summaries

### 2. Code Quality & Security (`code-quality.yml`)
- **Triggers**: Push, Pull requests, Weekly schedule
- **Permissions**: Contents read, Security-events write, Actions read
- **Actions**:
  - Run OWASP dependency security checks
  - Generate security reports
  - Ready for SonarCloud integration (commented out)

### 3. Code Style Check (`code-style.yml`)
- **Triggers**: Push to `main`/`develop`, Pull requests to `main`
- **Permissions**: Contents read
- **Actions**:
  - Check code formatting with Spotless
  - Validate code style (whitespace, line length, tabs)

### 4. Release (`release.yml`)
- **Triggers**: Git tags starting with `v*`
- **Permissions**: Contents write, Actions read
- **Actions**:
  - Build release JAR
  - Generate changelog
  - Create GitHub release with modern action
  - Upload release assets

### 5. Dependency Updates (`dependency-updates.yml`)
- **Triggers**: Weekly schedule, Manual trigger
- **Permissions**: Contents write, Pull-requests write
- **Actions**:
  - Check for dependency updates
  - Create pull request with updates

## Recent Fixes Applied

### ✅ **Test Reporter Issue Fixed**
- **Problem**: `dorny/test-reporter@v1` was failing with "Resource not accessible by integration"
- **Solution**: 
  - Added proper permissions (`checks: write`, `pull-requests: write`)
  - Replaced with more reliable `EnricoMi/publish-unit-test-result-action@v2`
  - Added comprehensive test artifact uploads

### ✅ **Permissions Added**
- All workflows now have explicit permissions defined
- Follows principle of least privilege
- Ensures proper access for each workflow's needs

### ✅ **Modern Actions**
- Updated release workflow to use `softprops/action-gh-release@v1`
- Replaced deprecated `actions/create-release@v1` and `actions/upload-release-asset@v1`
- Enhanced error handling and artifact management

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
- Review test results in PR check runs
- Monitor security alerts in Security tab
- Track dependency updates via pull requests

## Troubleshooting

### Common Issues Fixed
1. **Permission Errors**: All workflows now have proper permissions
2. **Test Reporting**: Uses reliable test result publisher
3. **Deprecated Actions**: Updated to modern, maintained actions
4. **Artifact Uploads**: Enhanced with proper error handling

The workflows are now robust and should run without permission or integration issues.
