
<!-- diagrams/login.md -->

# Login + Token Sequence Diagram

```mermaid

sequenceDiagram
    participant U as User
    participant UI as App UI
    participant API as Auth API
    participant Store as EncryptedStorage

    U->>UI: Enter email/password
    UI->>API: POST /login (HTTPS)
    API-->>UI: JWT Token
    UI->>Store: Save token (EncryptedSharedPreferences)
    UI->>API: Use token in Authorization header
