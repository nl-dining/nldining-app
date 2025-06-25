# nldining-app
Mobile app for reviewing restaurants in the Netherlands

# nldining 🍽️

**nldining** is a mobile app focused on discovering and reviewing restaurants across the Netherlands.  
It integrates with Google Maps and OpenData sources to help users find places they've visited, rate them, and discover new ones based on location.

---

## 🚀 Features

- 🗺️ Integration with Google Maps
- 🧾 Uses OpenData about restaurants in the Netherlands
- 🇳🇱 Only works within the Netherlands
- 🔐 Secure backend using Identity API
- 🧑 User login, password recovery, and rate limiting
- 📍 Default to current location, option to manually set a location
- ⭐ Track previously visited restaurants & add feedback

---

## 📦 Tech Stack

- **Frontend**: 
- **Backend**: 
- **Database**: PostgreSQL / MongoDB *(to be confirmed)*
- **API**: REST + secured endpoints
- **Authentication**: Identity (OAuth2/OpenID)

---

## 👨‍👩‍👧‍👦 Team

This app is developed by a team of four students as part of a collaborative mobile development project.  
GitHub organization: [`nldining`](https://github.com/nldining)

---

## 📂 Project Structure

- **/app**:
The app folder contains the front-end of the application

- **/NLDiningApi**:
Restaurant API used within the app

- **/diagrams**:
Diagrams to describe the UI/UX of the application

- **/docs**:
Simple explaination of the used Secure Software Development LifeCycle (SSDLC)

- **.github**:
The github actions workflows used to implement CI/CD within the project

## 🧩 Diagrams

- [Screen Flow](diagrams/flow-screen.md)
- [Architecture](diagrams/architecture.md)
- [Data Model (Room)](diagrams/data-model.md)
- [Sequence: Tagging](diagrams/tag-sequence.md)


## ✅ Kotlin/Android Security Checklist

We follow key Kotlin/Android security practices throughout development and deployment:

- [x] Use `EncryptedSharedPreferences` and `EncryptedFile` for secure data storage
- [x] Avoid hardcoding secrets or API keys in the codebase
- [x] Enforce HTTPS (TLS 1.2+) and use certificate pinning with OkHttp
- [x] Use `android:exported="false"` for non-public components
- [x] Implement BiometricPrompt or secure login flow for authentication
- [x] Obfuscate the code using R8/ProGuard
- [x] Avoid enabling JavaScript in `WebView` unless required
- [x] Request the minimum set of permissions at runtime
- [x] Apply root/jailbreak detection (if necessary for the use case)
- [x] Run static analysis tools like **Detekt** and **Android Lint**
- [x] Scan for exposed secrets using tools like **truffleHog** or **Gitleaks**
- [x] Follow [Android Security Tips](https://developer.android.com/privacy-and-security/security-tips)

### 🔐 OAuth2 Security

- [x] Use Authorization Code Flow with PKCE
- [x] Use custom scheme or App Links for redirect URIs
- [x] Encrypt stored access/refresh tokens
- [x] Validate ID tokens (`iss`, `aud`, `exp`)
- [x] Avoid Implicit Flow; prefer short-lived tokens + refresh tokens
- [x] Invalidate tokens properly on logout

---

📚 For more, see:
- [OWASP MSTG](https://owasp.org/www-project-mobile-security-testing-guide/)
- [Jetpack Security](https://developer.android.com/topic/security/data)
- [Detekt for Kotlin](https://detekt.dev/)
- [Semgrep Kotlin Rules](https://semgrep.dev/r?q=kotlin)
- [OAuth 2.0 for Native Apps (RFC 8252)](https://datatracker.ietf.org/doc/html/rfc8252)
