import { Link } from "react-router-dom";

export default function NotFound(): JSX.Element {
  return (
    <div className="page">
      <div className="card empty-state">
        <h1>📖 Seite nicht gefunden</h1>
        <p>Diese Seite existiert nicht – vielleicht ist sie in einem anderen Regal.</p>
        <Link to="/" className="btn btn-accent" style={{ marginTop: "1rem" }}>
          Zurück zur Übersicht
        </Link>
      </div>
    </div>
  );
}
