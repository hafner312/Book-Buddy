import { useState } from "react";
import { NavLink } from "react-router-dom";

export default function GlobalNavigation(): JSX.Element {
  const [open, setOpen] = useState(false);
  const linkClass = ({ isActive }: { isActive: boolean }) => (isActive ? "active" : "");

  return (
    <nav className="navbar">
      <div className="navbar-row">
        <NavLink to="/" className="navbar-brand" onClick={() => setOpen(false)}>
          📚 BookBuddy
        </NavLink>
        <button
          className="navbar-toggle"
          aria-label="Menü öffnen"
          aria-expanded={open}
          onClick={() => setOpen((prev) => !prev)}
        >
          {open ? "✕" : "☰"}
        </button>
      </div>
      <div className={`navbar-links${open ? " open" : ""}`}>
        <NavLink to="/" end className={linkClass} onClick={() => setOpen(false)}>
          Übersicht
        </NavLink>
        <NavLink to="/list" className={linkClass} onClick={() => setOpen(false)}>
          Bibliothek
        </NavLink>
        <NavLink to="/add" className={linkClass} onClick={() => setOpen(false)}>
          Buch hinzufügen
        </NavLink>
        {/* Kein Impressum hier: Die Hauptnavigation gehoert den Funktionen der
            Anwendung. Rechtliches steht in der Fussleiste - dort ist es
            ohnehin schon verlinkt. */}
      </div>
    </nav>
  );
}
