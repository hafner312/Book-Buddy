import { Link } from "react-router-dom";

export default function GlobalNavigation(): JSX.Element {
  return (
    <nav style={{ marginBottom: "2rem" }}>
      <Link to="/" style={{ marginRight: "1rem" }}>
        Home
      </Link>

      <Link to="/add" style={{ marginRight: "1rem" }}>
        Buch hinzufügen
      </Link>

      <Link to="/impressum" style={{ marginLeft: "1rem" }}>
        Impressum
      </Link>
    </nav>
  );
}
