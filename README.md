<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/RSDosev/RIM-Employees-LUAS">
    <img src="https://ibb.co/YdVkcbt" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">RIM Employees LUAS</h3>

  <p align="center">
    Simple app showing the tram forecast for RIM employees (from the the city center to the office and backawards)
  </p>
</p>

## Demo

[![Demo CountPages alpha](https://s1.gifyu.com/images/untitledaca98192d9660a4b.md.gif)]()

## About the implementation

- MVVM architecture
- Kotlin
- Android Jitpack (ViewModel, Navigation component, LiveData)
- Coroutines and Flow
- Koin dependency injection
- Unit and instrumented tests
- Modularization by layers 
  - App module - contains the UI logic (Activities, Fragments and ViewModels) + unit tests for the ViewModels and UI tests for the Activities and Fragments
  - Data module - contains the logic for fetching and storing (in-memory) the data (using the repository pattern)
  - Domain module - contains the business login (UseCases and Interactors) + unit tests
- Handling of loading, content and failure states
- In-memory caching
