# Betclic Kotlin Backend

![image](https://upload.wikimedia.org/wikipedia/fr/thumb/f/fe/Logo_Betclic_2019.svg/1200px-Logo_Betclic_2019.svg.png)

L'application est séparée en 2 modules: Domain et Infrastucture

- Domain contient les Entités et Usecases
- Infrastructure contient les routes, repositories et interfaces

### Lancer le service

```
./launcher.sh
```

### Technologies:

Ktor, Exposed, Koin, Gradle.

### Fonctionnalitées:

- Ajout d'un nouveau joueur
```
POST /player/{pseudo}
```
- MAJ d'un joueur
```
PUT /player/{id}/edit/{score}
```
- Récupération des données d'un joueur
```
GET /player/{id}
```
- Récupération des joueurs ordonnées par nombre de point
```
GET /player
```
- Supprimer tous les joueurs
```
DELETE /player
```

### Tests:

- Test unitaires
  - CreatePlayerTest.kt
  - DeleteAllPlayersTest.kt
  - GetPlayerTest.kt
  - ListPlayersTest.kt
  - UpdateScorePlayerTest.kt
  - PlayerRepositoryTest.kt
- Test E2E
  - ApplicationTest.kt

### Pour aller plus loin:

- Système d'exception
- Système de presenter (clean archi)
- Configuration custom