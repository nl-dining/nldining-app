<!-- diagrams/tag-sequence.md -->

# Tag-Sequence

```mermaid

graph TD
    UI --> ViewModel
    ViewModel --> Repository
    Repository --> RetrofitClient
    Repository --> RoomDatabase
