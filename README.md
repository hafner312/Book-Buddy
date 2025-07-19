# 🚀 Fullstack-Projekt: React + Vite & Spring Boot

Dieses Projekt ist eine moderne Fullstack-Anwendung mit einem React-Frontend (Vite) und einem Spring Boot Backend (Maven). Es bietet eine einfache, modulare Struktur für schnelles Prototyping und produktionsnahe Entwicklung.

## 📁 Projektstruktur

my-project/  
├── frontend/         → React + Vite  
└── backend/          → Spring Boot (Maven)

## ⚙️ Voraussetzungen

- Node.js (empfohlen: ≥ 18)  
- npm (oder alternativ: yarn)  
- Java JDK (empfohlen: Java 17 oder höher)  
- Maven (oder mvnw-Wrapper im Projekt enthalten)

## ▶️ Anwendung starten

### 🖥️ Backend starten (Spring Boot)

Öffne ein Terminal und führe im `backend/`-Verzeichnis folgenden Befehl aus:

cd backend  
mvn spring-boot:run

Wenn ein `mvnw`-Wrapper vorhanden ist:  
- Linux/macOS: `./mvnw spring-boot:run`  
- Windows: `mvnw spring-boot:run`

Der Backend-Server läuft standardmäßig unter:  
http://localhost:8080

### 🌐 Frontend starten (React + Vite)

Öffne ein weiteres Terminal und führe im `frontend/`-Verzeichnis Folgendes aus:

cd frontend  
npm install  
npm run dev

Das Frontend ist erreichbar unter:  
http://localhost:5173

## 🔄 API-Kommunikation (Proxy-Konfiguration)

Um API-Anfragen vom Frontend an das Spring Boot Backend weiterzuleiten, ist in `frontend/vite.config.js` folgende Proxy-Regel eingerichtet:

export default {  
  server: {  
    proxy: {  
      '/api': 'http://localhost:8080'  
    }  
  }  
};

Beispiel für eine API-Anfrage im Frontend:

fetch('/api/example')  
  .then(res => res.json())  
  .then(data => console.log(data));

## 🧪 Entwicklung & Debugging

- React unterstützt Hot Module Reloading (HMR) via Vite  
- Backend-Routen können z. B. mit curl, Postman oder HTTPie getestet werden  
- Konsistente API-Routen über `/api/...` empfohlen

## 📦 Deployment (optional)

Deployment-Prozesse (z. B. Docker oder CI/CD) sind aktuell nicht konfiguriert, können aber bei Bedarf ergänzt werden.

## 🧹 Nützliche Befehle (Zusammenfassung)

| Kontext     | Befehl                | Beschreibung                     |  
|-------------|------------------------|----------------------------------|  
| Backend     | mvn spring-boot:run    | Startet Spring Boot Server       |  
| Frontend    | npm run dev            | Startet React Dev Server (Vite) |  
| Frontend    | npm install            | Installiert Abhängigkeiten       |


📚 JavaDoc anzeigen
Die JavaDoc-Dokumentation für das Backend ist bereits generiert und liegt lokal im Projektordner:

backend/target/site/apidocs/index.html → Dokumentation des Hauptcodes

backend/target/site/testapidocs/index.html → Dokumentation der Testklassen (optional)

📂 So öffnest du sie:

Navigiere im Projektverzeichnis zu backend/target/site/apidocs/

Öffne die Datei index.html per Doppelklick oder ziehe sie in dein Browserfenster

Hinweis: Der target/-Ordner wird nicht versioniert. Falls du die Dokumentation neu generieren willst, nutze mvn javadoc:javadoc bzw. mvn javadoc:test-javadoc.

## 📬 Feedback oder Fragen?

Dieses Projekt dient als Ausgangsbasis für produktionsreife Anwendungen. Verbesserungsvorschläge, Pull Requests oder Feedback sind willkommen.


