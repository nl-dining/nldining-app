# STRIDE Threat Modeling with Firebase - General considerations

Firebase is a popular Backend-as-a-Service (BaaS) platform by Google that provides tools such as Authentication, Firestore (database), Cloud Functions, and Hosting. Many mobile and web apps use **Firebase Authentication** to manage user sign-ins via email/password, Google, Facebook, and other providers. While Firebase simplifies authentication and session management, it's still essential to assess and mitigate security risks.

This table provides a STRIDE-based threat model focusing on Firebase services, especially Authentication and Firestore.

| **STRIDE Category**        | **Threat with Firebase**                       | **Mitigation**                                                                                     |
|---------------------------|------------------------------------------------|-----------------------------------------------------------------------------------------------------|
| **Spoofing**               | Attackers may try to fake identities or tokens | Firebase Auth with token validation; enforce email verification; use `uid` from `currentUser` only |
| **Tampering**              | Manipulation of Firestore data                 | Use Firestore Security Rules; apply principle of least privilege                                   |
| **Repudiation**            | Malicious users deny actions taken             | Enable Firebase Authentication logging; use Cloud Functions with audit logs                        |
| **Information Disclosure** | Data leaks due to weak rules                   | Proper Firestore rules; avoid public reads/writes; use environment separation                      |
| **Denial of Service**      | Abuse of unauthenticated endpoints             | Use Firebase rate limiting and reCAPTCHA for public endpoints                                      |
| **Elevation of Privilege** | Unintended access due to misconfigured roles   | Secure Firebase Functions, limit Admin SDK use on the client                                       |

# 🔐 STRIDE Threat Modeling: Firebase Authentication (Login & Register Flow)

This document applies the STRIDE methodology to harden the **login**, **register**, and **authentication** flow in the NLdining Android app using **Firebase Authentication**.

---

## ✅ Context

**Firebase Authentication** is used for:
- `signInWithEmailAndPassword`
- `createUserWithEmailAndPassword`
- (Optional) social login methods in the future

---

## 🧱 STRIDE Threat Model (Login & Register Only)

| **Threat**             | **Risk in Login/Register Flow**                                   | **Recommended Mitigations (Firebase Features or App Logic)**                             |
|------------------------|--------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| **Spoofing**           | Attacker impersonates another user or creates fake accounts        | - Use `currentUser.uid` as only source of identity<br>- Enable **email verification**      |
| **Tampering**          | Attacker manipulates login data (e.g., modify requests, token)     | - Use **HTTPS only** (enabled by default)<br>- Never trust data from the client            |
| **Repudiation**        | User denies registration or login actions                          | - Use **Firebase Authentication logs**<br>- Store login/register timestamps (optional)     |
| **Information Disclosure** | Leaking emails or tokens through logs or error messages       | - Do **not log** user credentials or token<br>- Use generic error messages (`"Invalid login"`) |
| **Denial of Service**  | Spam or brute-force account creation/login                         | - Enable **Firebase reCAPTCHA** on signup<br>- Use **Firebase rate limiting rules**        |
| **Elevation of Privilege** | Normal user gains admin access or overrides roles             | - Use **custom claims** on backend only<br>- **Never assign roles via client logic**       |

---

## 🧪 Firebase features to consider

| Feature                    | Benefit                                            | STRIDE Category Impacted |
|----------------------------|----------------------------------------------------|---------------------------|
| ✅ Email Verification       | Prevents use of fake/temporary emails              | Spoofing, Repudiation     |
| ✅ reCAPTCHA on Sign-Up     | Prevents bot-driven fake account creation          | DoS                       |
| ✅ Account Lockout/Rate Limit | Stops brute force and abuse                     | DoS, Spoofing             |
| ✅ Error Obfuscation        | Prevents attackers from knowing if user exists     | Information Disclosure    |
| ✅ Multi-Factor Auth (MFA)  | Stops password-only attacks                        | Spoofing, Elevation       |
| 🔒 Do not log credentials   | Avoid leaking passwords or tokens in crash logs    | Information Disclosure    |

---

Remarks:
  
Firebase automatically:

Uses SafetyNet or Play Integrity API (reCAPTCHA v3 equivalent) on Android

Detects suspicious activity from IP addresses

Applies invisible bot detection in background

## 🧰 Developer checklist

- [ ] ✅ Users must verify email before gaining full access
- [ ] ✅ Use `FirebaseAuth.getInstance().currentUser.uid` for identity — never `email` alone
- [ ] ✅ Do **not** log exceptions or tokens publicly
- [ ] ✅ Show only generic error messages to the user
- [ ] ✅ Enable `setFirebaseUserDisplayName()` only after registration and verification
- [ ] ✅ Prevent unauthenticated access to screens or network calls
- [ ] ✅ Prepare for possible social login later — don’t hardcode flows
- [ ] ✅ Keep business logic (roles, rules) on the backend — never in the app

---

## 🛠 Firebase console settings

| What to Check                      | Where                          |
|-----------------------------------|---------------------------------|
| Email verification enabled        | Firebase Console → Auth → Templates |
| Blocking functions or reCAPTCHA   | Firebase Console → Auth → Settings |
| Multi-Factor Authentication       | Firebase Console → Auth → MFA   |
| Logs of login/register attempts   | Firebase Console → Auth Logs (or Cloud Logging) |

---

## ✅ Summary

Use Firebase Authentication as the foundation, but actively **configure and monitor** it to protect against STRIDE-class threats. Combine:
- Client-side hygiene (error handling, not trusting client input)
- Firebase-side configuration (verification, rate limits)
- Optional backend authorization (if roles are involved)


