graph TD
    UI --> ViewModel
    ViewModel --> Repository
    Repository --> RetrofitClient
    Repository --> RoomDatabase
