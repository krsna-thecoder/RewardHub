# Card Benefit Activation Engine

Automatically detects when a purchase qualifies for a card protection benefit
(purchase / return / travel-delay), pre-fills the claim, and manages the approval workflow —
so card members never miss benefits they've already paid for.

*Amex CodeStreet 2026 · Spring Boot 4 (Java 21) + React*

## How it works

**WATCH** 👀 → **MATCH** 🔍 → **PRE-FILL** 📝 → **APPROVE** 🏦

1. Ingest each purchase and validate it.
2. Match it to the right protection benefit (checked against card entitlements).
3. Auto-build a ready-to-submit claim.
4. Run it through the submission + approval state machine.

## Tech stack

Spring Boot 4 · Spring Data JPA · H2 · Bean Validation · Lombok · JUnit 5 / Mockito · React

## Run

```bash
./mvnw spring-boot:run
```

App starts on `http://localhost:8080` (H2 console at `/h2-console`).
