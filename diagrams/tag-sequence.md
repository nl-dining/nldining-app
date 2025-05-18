sequenceDiagram
    User->>UI: Tap on restaurant
    UI->>ViewModel: addTag("vegetarian")
    ViewModel->>Repository: saveTag(placeId, tag)
    Repository->>Room: insertOrUpdate(taggedRestaurant)
    Room-->>Repository: success
    ViewModel-->>UI: Update tag UI
