<!-- diagrams/architecture.md -->

# 🧱 App Architecture

This diagram shows the Clean MVVM architecture of the app.

- **UI** calls **ViewModel**
- **ViewModel** calls **Repository**
- **Repository** chooses to get/store data via:
  - **RoomDatabase** (local)
  - or **RetrofitClient** (API)
```mermaid

graph TD
    UI --> ViewModel
    ViewModel --> Repository
    Repository --> RetrofitClient
    Repository --> RoomDatabase



