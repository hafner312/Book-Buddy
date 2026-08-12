import { NavLink } from "react-router-dom";

export default function GlobalNavigation(): JSX.Element {
  const linkClass = ({ isActive }: { isActive: boolean }) => (isActive ? "active" : "");

  return (
    <nav className="navbar">
      <NavLink to="/" className="navbar-brand">
        📚 BookBuddy
      </NavLink>
      <div className="navbar-links">
        <NavLink to="/" end className={linkClass}>
          Übersicht
        </NavLink>
        <NavLink to="/list" className={linkClass}>
          Bibliothek
        </NavLink>
        <NavLink to="/add" className={linkClass}>
          Buch hinzufügen
        </NavLink>
        <NavLink to="/impressum" className={linkClass}>
          Impressum
        </NavLink>
      </div>
    </nav>
  );
}
