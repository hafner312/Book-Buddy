// Import von React (für JSX-Nutzung)
import React from 'react';

// Definition der erwarteten Props mit TypeScript
interface ButtonProps {
  label: string;             // Der Text, der im Button angezeigt wird
  onClick: () => void;       // Funktion, die beim Klicken des Buttons ausgeführt wird
}

// Wiederverwendbare Button-Komponente
// Verwendung z. B. in Formularen, Navigation oder interaktiven Listen
export default function Button({ label, onClick }: ButtonProps): JSX.Element {
  return (
    <button
      className="button"     // Verweis auf eine CSS-Klasse – Styling extern definiert (z. B. in index.css)
      onClick={onClick}      // Event-Handler für das Klick-Ereignis
    >
      {label}                // Anzeige des Texts im Button
    </button>
  );
}

