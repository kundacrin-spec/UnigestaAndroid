UNIGESTA ANDROID - PROJET DE DÉPART
==================================

Ce projet est une enveloppe Android WebView qui ouvre l'application PHP Unigesta hébergée sur un serveur. Il ne contient PAS le serveur PHP ni la base MySQL.

IMPORTANT
- L'adresse trycloudflare.com préremplie est temporaire et peut expirer. Dans l'application, remplace-la par une adresse stable HTTPS de ton serveur.
- Pour accéder à Unigesta partout, le PC WAMP ne doit pas être la seule solution fragile : configure un hébergement/serveur permanent HTTPS, ou un tunnel permanent.
- Cette enveloppe ne rend pas à elle seule toute l'application utilisable hors ligne. Le service worker et localStorage existants doivent être testés dans Android WebView, et les modifications hors ligne doivent être mises en file puis synchronisées par l'API PHP au retour du réseau.
- Avant toute exposition publique, sécurise api.php, change la clé qui se trouvait dans config.php et n'utilise pas MySQL root sans mot de passe.
- Aucun secret ni identifiant de base de données n'est inclus dans ce projet Android.

COMPILATION SUR WINDOWS
1. Installer Android Studio : https://developer.android.com/studio
2. Extraire ce ZIP dans un dossier simple, par exemple C:\Projets\UnigestaAndroid
3. Android Studio > Open > sélectionner le dossier UnigestaAndroid.
4. Attendre la synchronisation Gradle.
5. Brancher un téléphone Android avec le débogage USB activé, ou créer un émulateur.
6. Pour générer un APK : Build > Build Bundle(s) / APK(s) > Build APK(s).
7. L'APK debug se trouve normalement dans app\build\outputs\apk\debug\app-debug.apk

Le projet utilise Android Gradle Plugin 8.7.3, Kotlin 2.0.21 et compileSdk 35. Android Studio peut demander l'installation des composants SDK correspondants.
