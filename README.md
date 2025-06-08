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

## 🧱 Architecture du projet

| Dossier / Fichier                | Rôle                                                                 |
|----------------------------------|----------------------------------------------------------------------|
| `📁 api/`                         | Interfaces Retrofit pour accéder à l'API REST Spring Boot           |
| ├── `AppartementApi.kt`         | Requêtes liées aux appartements                                     |
| ├── `BatimentApi.kt`            | Requêtes liées aux bâtiments                                        |
| ├── `ContratApi.kt`             | Requêtes liées aux contrats                                         |
| ├── `InterventionApi.kt`        | Requêtes liées aux interventions                                    |
| ├── `LocataireApi.kt`           | Requêtes liées aux locataires                                       |
| ├── `PaiementApi.kt`            | Requêtes liées aux paiements                                        |
| ├── `GarantApi.kt`              | Requêtes liées aux garants                                          |
| └── `RetrofitInstance.kt`       | Configuration Retrofit (base URL, client…)                          |

| `📁 model/`                      | Représentation des données manipulées                               |
| ├── `Batiment.kt`               | Entité bâtiment (id, adresse, ville)                                |
| ├── `Appartement.kt`            | Entité appartement (id, nom, étage, etc.)                           |
| ├── `Contrat.kt`                | Entité contrat (loyer, dates, statut...)                            |
| ├── `Paiement.kt`               | Entité paiement (montant, date, contrat lié...)                     |
| ├── `Locataire.kt`              | Entité locataire (nom, prénom, contrat lié...)                      |
| ├── `Garant.kt`                 | Entité garant (nom, lien, contrat lié...)                           |
| ├── `Intervention.kt`           | Entité intervention (type, description, date...)                    |
| ├── `BatimentRef.kt`            | Référence bâtiment minimale (id uniquement, pour POST/PUT)          |
| ├── `AppartementRef.kt`         | Référence appartement                                                |
| └── `ContractRef.kt`            | Référence contrat pour relations simples                            |

| `📁 repository/`                 | Accès centralisé aux APIs (pattern repository)                      |
| ├── `BatimentRepository.kt`     | Fonctions de gestion des bâtiments                                  |
| ├── `AppartementRepository.kt`  | Fonctions de gestion des appartements                               |
| ├── `ContratRepository.kt`      | Fonctions de gestion des contrats                                   |
| ├── `PaiementRepository.kt`     | Fonctions de gestion des paiements                                  |
| ├── `LocataireRepository.kt`    | Fonctions de gestion des locataires                                 |
| ├── `GarantRepository.kt`       | Fonctions de gestion des garants                                    |
| └── `InterventionRepository.kt` | Fonctions de gestion des interventions                              |

| `📁 viewmodel/`                  | Logique métier et gestion d’état des entités                        |
| ├── `BatimentViewModel.kt`      | ViewModel pour les bâtiments                                        |
| ├── `AppartementViewModel.kt`   | ViewModel pour les appartements                                     |
| ├── `ContratViewModel.kt`       | ViewModel pour les contrats                                         |
| ├── `PaiementViewModel.kt`      | ViewModel pour les paiements                                        |
| ├── `LocataireViewModel.kt`     | ViewModel pour les locataires                                       |
| ├── `GarantViewModel.kt`        | ViewModel pour les garants                                          |
| └── `InterventionViewModel.kt`  | ViewModel pour les interventions                                    |

| `📁 ui/screen/`                  | Écrans principaux de navigation                                     |
| ├── `HomeScreen.kt`             | Écran d’accueil                                                     |
| ├── `BatimentScreen.kt`         | Liste des bâtiments                                                 |
| ├── `BatimentDetailScreen.kt`   | Détail d’un bâtiment + appartements liés                            |
| ├── `AppartementScreen.kt`      | Liste des appartements                                              |
| ├── `AppartementDetailScreen.kt`| Détail d’un appartement + contrats/interventions liés              |
| ├── `ContratScreen.kt`          | Liste des contrats                                                  |
| ├── `ContratDetailScreen.kt`    | Détail d’un contrat + paiements/locataires/garants liés            |
| ├── `InterventionScreen.kt`     | Liste des interventions                                             |
| ├── `InterventionDetailScreen.kt`| Détail d’une intervention                                          |
| ├── `LocataireScreen.kt`        | Liste des locataires                                                |
| ├── `LocataireDetailScreen.kt`  | Détail d’un locataire                                               |
| ├── `PaiementScreen.kt`         | Liste des paiements                                                 |
| ├── `PaiementDetailScreen.kt`   | Détail d’un paiement                                                |
| └── `GarantScreen.kt`           | Liste des garants                                                   |

| `📁 ui/component/`               | Composants réutilisables (UI générique)                             |
| ├── `Header.kt`                 | En-tête commun avec bouton retour                                   |
| ├── `EntityCard.kt`             | Carte d’entité avec actions                                         |
| ├── `EntityForm.kt`             | Formulaire générique (à spécialiser)                                |
| └── `ConfirmationDialog.kt`     | Dialog de confirmation pour suppression                             |

| `📁 navigation/`                | Gestion centralisée de la navigation (`NavHost`)                    |
| └── `AppNavigation.kt`          | Routes et transitions d’écrans                                      |

| `MainActivity.kt`               | Entrée de l’application Android                                     |

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
   git clone https://github.com/<ton-username>/azurimmo-mobile.git