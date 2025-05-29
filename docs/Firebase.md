# STRIDE Threat Modeling with Firebase

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
