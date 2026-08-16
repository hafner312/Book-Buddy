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

# Die JVM-Schalter zielen auf schnellen Start, nicht auf Dauerleistung.
#
# Der kostenlose Tarif gibt dem Dienst 0.1 CPU. Damit brauchte Spring 126
# Sekunden bis zur Bereitschaft - so lange wartet die Bereitstellung nicht,
# sie hielt den Dienst fuer haengengeblieben, beendete ihn und versuchte es
# erneut. Das Ergebnis war eine Neustartschleife und am Ende ein Dienst, der
# gar nichts mehr auslieferte.
#
#   TieredStopAtLevel=1  laesst die aufwendige zweite Uebersetzungsstufe weg.
#                        Kostet Dauerleistung, die eine Demo nicht braucht.
#   UseSerialGC          vermeidet mehrere Aufraeum-Faeden, die sich auf
#                        0.1 CPU nur gegenseitig behindern.
#   MaxRAMPercentage=70  gibt der JVM Luft; gemessen nutzt die Anwendung im
#                        Betrieb nur rund 145 MB von 512.
#   Xss512k              kleinere Fadenstapel, spart Speicher bei vielen
#                        Tomcat-Faeden.
#
# Gemessen bei 0.1 CPU und 512 MB bis zur ersten Antwort: 153 s ohne, 67 s mit.
CMD ["sh", "-c", "java -XX:TieredStopAtLevel=1 -XX:+UseSerialGC -XX:MaxRAMPercentage=70 -Xss512k -jar app.jar --server.port=$PORT --spring.profiles.active=render"]
