# Mon application Spring Boot

## Description
Ceci est une application Spring Boot qui met en place Spring Security avec des jetons JWT pour l'authentification et l'autorisation des utilisateurs. Elle utilise également une base de données H2 pour le stockage des données.

## Prérequis
Avant de pouvoir exécuter l'application, assurez-vous d'avoir les éléments suivants installés :

- Java Development Kit (JDK)
- Maven

## Installation
1. Clonez ce dépôt sur votre machine locale.
2. Naviguez vers le répertoire du projet.
3. Exécutez la commande suivante pour construire l'application :
    ```
    mvn clean install
    ```
4. Exécutez la commande suivante pour démarrer l'application :
    ```
    mvn spring-boot:run
    ```

## Utilisation
Une fois l'application démarrée, vous pouvez accéder à l'API via l'URL suivante : `http://localhost:8080/api`.

### Authentification
Pour vous connecter à l'API, utilisez les endpoints suivants :
- `/api/auth/login` : pour vous connecter à l'application.
- `/api/auth/register` : pour créer un nouveau compte utilisateur.

### Accès aux produits
Pour accéder aux produits de l'API, utilisez l'endpoint suivant :
- `/api/products`

## Base de données
L'application utilise une base de données H2 qui est une base de données en mémoire. Vous pouvez accéder à la console H2 en utilisant l'URL suivante : `http://localhost:8080/h2-console`. Les informations de connexion sont les suivantes :
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: laisser vide
