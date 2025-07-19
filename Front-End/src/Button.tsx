// Import von React für JSX
import React from "react";

// Definition der erwarteten Eigenschaften (Props)
interface ButtonProps {
  label: string;               // Text, der im Button angezeigt wird
  handler: () => void;         // Funktion, die bei Klick ausgeführt wird
}

// Wiederverwendbare Button-Komponente
export default function Button(props: ButtonProps): JSX.Element {
  return (
    <button className="button" onClick={props.handler}>
      {props.label}
    </button>
  );
}
