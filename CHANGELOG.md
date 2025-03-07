# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.10.0] - 2022-11-30

### Changed

-   Configure Coil image loader with better caching settings
-   Refresh packaged baseline profile

## [1.9.0] - 2022-11-28

### Changed

-   A loading indicator is added to the bottom of the post list to identify when new posts are being fetched

## [1.8.0] - 2022-11-21

### Changed

-   The release pipeline now automatically generates Play Store release notes from the changelog file

### Fixed

-   Downgrade AGP to 8.0.0-alpha07 to fix Baseline Profiles not being packaged

## [1.7.0] - 2022-11-19

### Changed

-   Switch to Compose Material's swipe refresh feature
-   Change metadata extractor logic to no longer prefer canonical URLs. The submitted URL will be retained as-is now.
-   Remove navigation transition animations

## [1.6.0] - 2022-11-14

### Changed

-   Migrate dependency injection from Hilt to Anvil + Whetstone

## [1.5.0] - 2022-11-10

### Changed

-   Upgrade to Compose 2022.11.00 release

## [1.4.0] - 2022-11-02

### Changed

-   Misc fixes to link metadata extractor

## [1.3.0] - 2022-10-24

### Changed

-   Prevent errors during link metadata retrieval from crashing the app
-   Update Jetpack libraries to 2022-10-24 release

## [1.2.0] - 2022-10-17

### Changed

-   Lazily load link metadata to improve comment page loading speed
-   Upgrade to latest Compose release
-   Refactor code to align with Twitter's public Compose guidelines

## [1.1.0] - 2022-09-30

### Added

-   Add score and relative time to comments

### Changed

-   Update Jetpack Compose
-   Declare data backup and transfer rules
-   Improve legibility of links in comments and user profiles
-   Commonize and improve how network errors are displayed

## [1.0.0] - 2022-09-20

-   Initial Play Store release

[Unreleased]: https://github.com/msfjarvis/compose-lobsters/compare/1.10.0...HEAD

[1.10.0]: https://github.com/msfjarvis/compose-lobsters/compare/1.9.0...1.10.0

[1.9.0]: https://github.com/msfjarvis/compose-lobsters/compare/1.8.0...1.9.0

[1.8.0]: https://github.com/msfjarvis/compose-lobsters/compare/v1.7.0...1.8.0

[1.7.0]: https://github.com/msfjarvis/compose-lobsters/compare/v1.6.0...v1.7.0

[1.6.0]: https://github.com/msfjarvis/compose-lobsters/compare/v1.5.0...v1.6.0

[1.5.0]: https://github.com/msfjarvis/compose-lobsters/compare/v1.4.0...v1.5.0

[1.4.0]: https://github.com/msfjarvis/compose-lobsters/compare/v1.3.0...v1.4.0

[1.3.0]: https://github.com/msfjarvis/compose-lobsters/compare/v1.2.0...v1.3.0

[1.2.0]: https://github.com/msfjarvis/compose-lobsters/compare/v1.1.0...v1.2.0

[1.1.0]: https://github.com/msfjarvis/compose-lobsters/compare/v1.0.0...v1.1.0

[1.0.0]: https://github.com/msfjarvis/compose-lobsters/compare/29c374859b17c5fcef03585b8a01c00070de9097...v1.0.0
