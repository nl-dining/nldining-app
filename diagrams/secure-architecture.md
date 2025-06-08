# Security-Annotated Architecture Diagram

> 🔒 **Security Notes**
>
> - Firebase Authentication is used for secure login and identity management.
> - After login, Firebase provides a cryptographically signed ID Token (JWT).
> - The token is securely stored in `EncryptedSharedPreferences`.
> - All API requests use HTTPS and include the ID Token in the `Authorization` header.
> - Authorization is handled server-side using the UID in the token.
> - Room DB is used for local storage (e.g., user preferences) and does **not** store any credentials or secrets.
> - No authorization logic is implemented on the client — this prevents manipulation or privilege escalation.

```mermaid
graph TD
    UI["UI Layer<br>(Activity/Fragment)"] --> VM["ViewModel<br>(Logic/State Management)"]
    VM --> Repo["Repository<br>(Data Access Layer)"]

    Repo --> Auth["FirebaseAuth<br>Provides ID Token (JWT)<br>via currentUser.getIdToken()"]
    Auth --> Repo

    Repo --> API["RetrofitClient<br>HTTPS + Auth Header (Bearer ID Token)"]
    Repo --> DB["Room DB<br>Stores local tags & preferences only"]
