<!-- diagrams/datamodel.md -->

# Room/ER diagram

`placeId` is a unique identifier from the **Google Places API**, assigned to every place (e.g., restaurant, store, landmark).

```mermaid

erDiagram
    TaggedRestaurant {
        String placeId PK
        String name
        String[] tags
        Long timestamp
    }
