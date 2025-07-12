# GitHub Users App

## Overview

This Android project is built entirely with Kotlin and Gradle, featuring a modern architecture with
Jetpack Compose for UI, Paging 3 for efficient data loading, Clean Architecture principles, Hilt for
dependency injection, and KSP (Kotlin Symbol Processing) for annotation processing.

## Architecture

This project follows the principles of **Clean Architecture**, separating concerns into distinct layers. This makes the codebase modular, scalable, testable, and maintainable.

```mermaid
graph TD
    subgraph App Layer
        App["<b>App Module</b><br/>(Ties everything together)<br/><br/>- MainActivity<br/>- BaseApplication (Hilt)<br/>- Android Manifest"]
    end

    subgraph UI Layer
        Presentation["<b>Presentation Module</b><br/>(UI & State)<br/><br/>- Jetpack Compose (Screens, UI Models)<br/>- ViewModels (@HiltViewModel)<br/>- Navigation (AppNavGraph)<br/>- StateFlow, Paging 3"]
    end

    subgraph Data Layer
        Data["<b>Data Module</b><br/>(Data Sources & Implementation)<br/><br/>- RepositoryImpl<br/>- Remote: Retrofit, DTOs<br/>- Local: Room, DB Entities<br/>- PagingSource<br/>- Mappers (toDomain, toEntity)"]
    end

    subgraph Core Business Logic
        Domain["<b>Domain Module</b><br/>(Business Rules & Interfaces)<br/><br/>- Entities (User, UserDetails)<br/>- Use Cases<br/>- Repository Interfaces<br/>- Pure Kotlin Module"]
    end

    %% Dependencies
    App --> Presentation
    App --> Data
    Presentation --> Domain
    Data --> Domain

    %% Styling
    style App fill:#D2B4DE,stroke:#512E5F,stroke-width:2px
    style Presentation fill:#A9CCE3,stroke:#2471A3,stroke-width:2px
    style Data fill:#A3E4D7,stroke:#1D8348,stroke-width:2px
    style Domain fill:#F9E79F,stroke:#B7950B,stroke-width:2px
```

## Project Structure

The project is organized into the following modules:

```
.
├── app/                # Main application module (entry point)
├── data/               # Data sources, repositories, API, local storage
│   ├── api/
│   ├── di/
│   ├── localstorage/
│   └── repoimpl/
├── domain/             # Business logic, entities, use cases, repository interfaces
│   ├── di/
│   ├── entities/
│   ├── repositories/
│   └── usecases/
├── presentation/       # UI layer (Screens, ViewModels, UI models, Navigation)
│   ├── ui/
│   │   ├── components/
│   │   ├── navigation/
│   │   └── userdetails/
│   │   └── userslist/
└── build.gradle.kts    # Root build file
```

## Features

- Search GitHub users locally
- Paginated list with efficient loading using `PagingData`
- User details with fallback to local cache
- Network status tracking with automatic retry on reconnection
- Clean Architecture (Domain, Data, App layers)
- Dependency Injection with Hilt
- Modular, testable, and scalable codebase

## Requirements

- Android Studio (latest stable)
- JDK 11 or newer
- Gradle 7.x or newer

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourname/github-users-app.git

2. Open the project in Android Studio

3. Ensure the Gradle JDK is set to JDK 11 or newer:
    - `Preferences` > `Build, Execution, Deployment` > `Build Tools` > `Gradle` > `Gradle JDK`
4. Sync the project and build.

## Troubleshooting

- If you encounter issues with annotation processing, ensure you are using KSP and not KAPT in your
  `build.gradle` files.
- Make sure your JDK version matches the project requirements.

⚠️ Dark Mode Notice:
Only light mode design was provided and implemented. Dark mode support is not fully styled or verified.