# 📱 AzurImmo Mobile

**Application mobile de gestion immobilière** pour le projet AzurImmo.  
Développée en **Kotlin** avec **Jetpack Compose**, cette application consomme une **API REST Spring Boot** pour permettre la gestion complète des entités liées à un parc immobilier.

---

## 🚀 Fonctionnalités

- 📋 **CRUD complet** pour :
  - Bâtiments
  - Appartements
  - Contrats
  - Interventions
  - Locataires
  - Garants
  - Paiements

- 🔗 **Relations dynamiques** entre entités :
  - Appartements par bâtiment
  - Contrats par appartement
  - Paiements, locataires et garants par contrat
  - Interventions par appartement

- ⚙️ **Navigation fluide** avec `NavHostController`
- 📱 **Interface moderne et responsive** avec `Jetpack Compose`
- 📡 **Appels réseau** via `Retrofit` + `ViewModel` + `Repository`
- 💾 Gestion d’état avec `MutableState` et `LiveData`

---

## 🔧 Stack technique

| Outil / Lib | Usage |
|-------------|-------|
| Kotlin | Langage principal |
| Jetpack Compose | UI déclarative |
| Retrofit2 | Requêtes HTTP |
| Gson | Parsing JSON |
| ViewModel + LiveData | Architecture MVVM |
| NavController | Navigation entre écrans |

---

## 📡 API attendue

Le projet consomme une **API REST** hébergée séparément, développée en **Java Spring Boot**.  
Exemples d’URL :
- `GET /appartements`
- `GET /appartements/batiment/{id}`
- `POST /contrats`
- `DELETE /paiements/{id}`  
⚠️ L’application suppose que l’API est **déployée et accessible** (via localhost ou IP publique).

---

## 🧪 À venir (feuille de route)

- 🔒 Authentification JWT
- ☁️ Déploiement en production (API + APK)
- 🧼 Refonte UI avec Material 3
- ✅ Ajout de tests unitaires
- 📥 Ajout d’un mode offline (Room DB ?)

---

## 🛠️ Lancer l’application

### Prérequis
- Android Studio Arctic Fox ou supérieur
- Kotlin 1.9+
- API Spring Boot fonctionnelle (lien à jour dans les services Retrofit)

### Étapes
1. Cloner le repo :
   ```bash
   git clone https://github.com/Skrylleur/Azurimmo.git