<!-- diagrams/architecture.md -->

# UI–ViewModel–Repo–API

```mermaid

graph TD
    UI --> ViewModel
    ViewModel --> Repository
    Repository --> RetrofitClient
    Repository --> RoomDatabase
