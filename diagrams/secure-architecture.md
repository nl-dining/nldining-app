<!-- diagrams/login.md -->

# Security-Annotated Architecture Diagram

```mermaid

graph TD
    UI["UI Layer\n(Activity/Fragment)"] --> VM["ViewModel\n(Logic/State)"]
    VM --> Repo["Repository\n(Abstraction)"]
    Repo --> API["RetrofitClient\n(HTTPS + Auth Token)"]
    Repo --> DB["Room DB\n(Local Storage\n(No secrets)"]

