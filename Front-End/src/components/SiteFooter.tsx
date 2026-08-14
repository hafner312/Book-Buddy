import { Link } from "react-router-dom";

export default function SiteFooter(): JSX.Element {
  return (
    <footer className="site-footer">
      <div className="footer-inner">
        <div className="footer-col footer-brand">
          <span className="footer-logo">📚 BookBuddy</span>
          <p>
            Die eigene Bibliothek im Blick behalten: Bücher erfassen, nach Status
            und Kategorie sortieren, bewerten und wiederfinden – vom Wunschtitel
            bis zum ausgelesenen Buch.
          </p>
        </div>

        <div className="footer-col">
          <h3>Navigation</h3>
          <ul>
            <li><Link to="/">Übersicht</Link></li>
            <li><Link to="/list">Bibliothek</Link></li>
            <li><Link to="/add">Buch hinzufügen</Link></li>
            <li><Link to="/impressum">Impressum</Link></li>
          </ul>
        </div>

        <div className="footer-col">
          <h3>Technologie</h3>
          <ul>
            <li>React &amp; TypeScript</li>
            <li>Vite</li>
            <li>Java &amp; Spring Boot</li>
            <li>REST-API</li>
          </ul>
        </div>

        <div className="footer-col">
          <h3>Mehr von mir</h3>
          <ul>
            <li>
              <a
                href="https://hafner312.github.io/BewerbungsPortfolio/"
                target="_blank"
                rel="noopener"
              >
                Portfolio-Website
              </a>
            </li>
            <li>
              <a
                href="https://github.com/hafner312/Book-Buddy"
                target="_blank"
                rel="noopener"
              >
                Quellcode auf GitHub
              </a>
            </li>
            <li>
              <a href="mailto:hafner312@gmail.com">hafner312@gmail.com</a>
            </li>
          </ul>
        </div>
      </div>

      <div className="footer-bottom">
        <span>&copy; {new Date().getFullYear()} Patrik Hafner</span>
        <span className="footer-note">
          Demo-Projekt – jede Besucherin und jeder Besucher sieht eine eigene
          Bibliothek.
        </span>
      </div>
    </footer>
  );
}
