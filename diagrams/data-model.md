<!-- diagrams/datamodel.md -->

# Room/ER diagram

```mermaid

erDiagram
    TaggedRestaurant {
        String placeId PK
        String name
        String[] tags
        Long timestamp
    }
