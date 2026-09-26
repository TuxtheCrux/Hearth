# Gacrux

Eine sicherheitsorientierte Android-App für Passwortverwaltung mit integrierter Verschlüsselung und Scanner-Funktionalität. Entwickelt als Hochschulprojekt mit Fokus auf praxisnaher Security-Architektur.

---

## Features

- **Passwortverwaltung** – Zugangsdaten speichern, suchen und verwalten; Swipe-to-Delete, alphabetische Sortierung
- **Zweischichtige Verschlüsselung** – SQLCipher verschlüsselt die gesamte Datenbank; einzelne Passwortfelder werden zusätzlich mit PBKDF2 + AES-GCM gesichert
- **Biometrische Authentifizierung** – Entsperrung per Fingerabdruck oder Gerätesperre via Android BiometricPrompt -- Not yet implemented
- **Session-Management** – Master-Key liegt nur im RAM und wird nach Inaktivität automatisch gelöscht
- **Scanner** – *(in Entwicklung)*
- **Cipher-Tools** – *(in Entwicklung)*

---

## Architektur

Redline folgt **Clean Architecture** mit strikter Schichtentrennung:

```
core/
├── database/       # Room + SQLCipher, AppDatabase, Hilt-Modul
├── navigation/     # Routes, NavGraph, BottomBar
└── security/       # CryptoManager, SessionManager, Android Keystore

feature/
├── passwords/
│   ├── data/       # PasswordEntity, DAO, Repository,...
│   ├── domain/     # UseCases, PasswordListItem, PasswordDetail,...
│   └── ui/         # PasswordScreen, Composables,...
├── scanner/        # (in Entwicklung)
└── cipher/         # (in Entwicklung)
```

Jedes Feature folgt dem `Data → Domain → UI`-Muster. `PasswordEntity` verlässt den Data-Layer nie – ViewModels sehen ausschließlich saubere Domain-Objekte.

---

## Security-Architektur

### Zweischichtige Verschlüsselung

| Schicht | Technologie | Geltungsbereich |
|---|---|---|
| Datenbank | SQLCipher | Alle Tabellen im Ruhezustand |
| Feld | PBKDF2-SHA256 + AES-256-GCM | `encryptedPassword`- und `email`-Felder |

### Schlüsselableitung

- Algorithmus: PBKDF2WithHmacSHA256
- Iterationen: 310.000 (OWASP-Empfehlung)
- Salt: 32 Byte, pro Eintrag zufällig generiert via `SecureRandom`
- IV/Nonce: 12 Byte, pro Eintrag zufällig generiert via `SecureRandom`

### Blob-Format

```
[ Salt (32 B) | IV (12 B) | Ciphertext (n B) ]  →  Base64-String  →  Room
```

### SessionManager

Der Master-Key (`CharArray`) lebt ausschließlich im RAM. Beim Sperren wird er aktiv überschrieben. Die Session sperrt sich nach 60 Sekunden Inaktivität automatisch – der Timer wird bei jedem echten Schlüsselzugriff neu gestartet.

### Lazy Decryption

Zugangsdaten werden nur dann entschlüsselt, wenn ein Nutzer einen einzelnen Eintrag im Detail-Sheet öffnet – nicht beim Laden der Liste. Damit
