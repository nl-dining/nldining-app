<!-- diagrams/architecture.md -->

# App Architecture

```mermaid

flowchart TD
    Login --> RestaurantList
    RestaurantList --> RestaurantDetail
    RestaurantDetail --> Tagging
    RestaurantList --> TaggedList
