# Baut Frontend und Backend zu EINEM Dienst zusammen.
#
# Vorher liefen beide als getrennte Render-Dienste. Auf dem kostenlosen Tarif
# schlaeft jeder Dienst nach Inaktivitaet ein - es mussten also zwei Dienste
# aufwachen, bevor die Demo etwas anzeigte. Jetzt landet das gebaute React-
# Frontend als statische Dateien im Spring-Boot-Jar: ein Dienst, ein Kaltstart,
# und weil alles von derselben Herkunft kommt, braucht es auch kein CORS mehr.
#
# Wichtig: Der Build-Kontext ist das Repository-Wurzelverzeichnis (nicht
# backend/), weil beide Teilprojekte gebraucht werden.

# ---------- Stufe 1: React-Frontend bauen ----------
FROM node:20-alpine AS frontend
WORKDIR /app
# Erst nur die Abhaengigkeitsdateien kopieren, damit Docker die Installation
# zwischenspeichern kann, solange sich diese nicht aendern.
COPY Front-End/package.json Front-End/package-lock.json ./
RUN npm ci
COPY Front-End/ ./
RUN npm run build

# ---------- Stufe 2: Backend bauen, Frontend darin ablegen ----------
FROM maven:3.9-eclipse-temurin-17 AS backend
WORKDIR /src
COPY backend/pom.xml .
RUN mvn -B dependency:go-offline
COPY backend/src ./src
# Spring Boot liefert alles unter src/main/resources/static automatisch aus.
COPY --from=frontend /app/dist ./src/main/resources/static/
RUN mvn -B clean package -DskipTests

# ---------- Stufe 3: Laufzeit ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=backend /src/target/*.jar app.jar
EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT --spring.profiles.active=render"]
