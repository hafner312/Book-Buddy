// React & ReactDOM-Import für Rendering
import React from "react";
import ReactDOM from "react-dom/client";

// Import der Haupt-App-Komponente
import App from "./App";

// Globales CSS importieren (Design-Tokens zuerst, dann Komponentenstile)
import "./index.css";
import "./App.css";

// Einstiegspunkt für die Anwendung (Root-Element finden und App einhängen)
const rootElement = document.getElementById("root");

if (!rootElement) {
  throw new Error("Root-Element nicht gefunden. Stelle sicher, dass index.html ein <div id='root'> enthält.");
}

// Mit TypeScript-Typunterstützung: createRoot erwartet ein Element vom Typ HTMLElement
const root = ReactDOM.createRoot(rootElement as HTMLElement);

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
