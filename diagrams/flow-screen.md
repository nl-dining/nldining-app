<!-- diagrams/flow-screen.md -->

# App screen flow (Mermaid)

```mermaid

flowchart TD
    Login --> RestaurantList
    RestaurantList --> RestaurantDetail
    RestaurantDetail --> Tagging
    RestaurantList --> TaggedList
