// React-Import (wird für JSX benötigt)
import React from "react";

// Wiederverwendbare Button-Komponente
import Button from "./Button";

// Definition der Props, die an die Komponente übergeben werden
interface CategorySelectionProps {
  categories: string[]; // Liste der Kategorienamen (z. B. ["Roman", "Thriller", ...])
}

// React-Komponente, die Buttons für jede übergebene Kategorie rendert
export default function CategorySelection({
  categories,
}: CategorySelectionProps): JSX.Element {
  return (
    <div>
      {/* Überschrift für den Bereich */}
      <h1>CategorySelection</h1>

      {/* Für jede Kategorie wird ein Button erzeugt */}
      {categories.map((category) => (
        <Button
          key={category}                    // Eindeutiger Schlüssel für React (optimiert Rendering)
          label={category}                  // Der Text, der auf dem Button erscheint
          onClick={() => console.log(`Kategorie gewählt: ${category}`)} 
          // Aktuell wird nur eine Konsolenausgabe gemacht.
          // Hier könnte später z. B. ein Filtermechanismus ausgelöst werden.
        />
      ))}
    </div>
  );
}
