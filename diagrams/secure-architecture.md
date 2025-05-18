<!-- diagrams/login.md -->

# Security-Annotated Architecture Diagram
> 🔒 **Security Notes**  
> - All API requests are sent over HTTPS  
> - Tokens are stored in EncryptedSharedPreferences  
> - Room DB contains only user tags — no passwords or secrets  


```mermaid

graph TD
    UI["UI Layer<br>(Activity/Fragment)"] --> VM["ViewModel<br>(Logic/State)"]
    VM --> Repo["Repository<br>(Abstraction Layer)"]
    Repo --> API["RetrofitClient<br>Uses HTTPS and Auth Token"]
    Repo --> DB["Room DB<br>Local storage only<br>No secrets stored"]
