# 🔐 STRIDE Threat Model – Firebase Login & Registration

This document applies the STRIDE threat modeling methodology to the login and registration process in the **NLdining Android app**, which uses **Firebase Authentication** for user identity management.

## 🎯 Purpose

The goal of this threat model is to identify and mitigate potential security threats during the authentication flow. We use two approaches:

- The **Microsoft Threat Modeling Tool (TMT)** to define assets, trust boundaries, and threats.
- A **Mermaid diagram** to illustrate the login flow and token handling visually.

## 📊 Authentication Flow – Mermaid Diagram

The diagram below represents the key steps during the login flow with Firebase Authentication and backend authorization:

```mermaid
flowchart TD
    Attacker -->|Fake email| FirebaseAuth
    FirebaseAuth -->|Token issued| App
    App -->|Send token| Backend
    Backend -->|Validate token| Firebase
    Backend -->|Grant access if role matches| Resource
