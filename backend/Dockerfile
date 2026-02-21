# --- Étape 1 : Build (Compilation) ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# On copie d'abord le pom.xml pour mettre en cache les dépendances
COPY . .
RUN mvn dependency:go-offline

# On copie le code et on build
COPY src ./src
# On lance les tests Maven ici pour que l'image ne build pas si les tests échouent !
RUN mvn clean package

# --- Étape 2 : Runtime (Exécution) ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# On ne récupère que le JAR généré à l'étape précédente
COPY --from=build /app/target/*.jar app.jar

# On expose le port 8080
EXPOSE 8080

# On lance l'app avec le profil prod par défaut (qu'on va configurer plus tard)
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]