### 🔐 Login Flow met Firebase (email/wachtwoord)

```mermaid
sequenceDiagram
    participant U as Gebruiker
    participant App as Android App
    participant Firebase as Firebase Auth

    U->>App: Voert e-mail en wachtwoord in
    App->>Firebase: signInWithEmailAndPassword()
    Firebase-->>App: Firebase ID-token
    App-->>App: Gebruiker is ingelogd
```
