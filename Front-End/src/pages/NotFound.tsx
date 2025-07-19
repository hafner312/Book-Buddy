// React importieren (für JSX)
import React from "react";

// Fallback-Komponente für nicht gefundene Routen (404-Seite)
export default function NotFound(): JSX.Element {
  return (
    <div>
      <h1>Seite nicht gefunden</h1>
      <p>Die aufgerufene URL existiert nicht.</p>
    </div>
  );
}
