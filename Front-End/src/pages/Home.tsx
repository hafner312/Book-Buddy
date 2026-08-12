import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { fetchBooks } from "../api";
import type { Book } from "../types";
import CoverChip from "../components/CoverChip";
import StarRating from "../components/StarRating";
import { clampRating } from "../utils";

export default function Home(): JSX.Element {
  const [books, setBooks] = useState<Book[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchBooks()
      .then(setBooks)
      .finally(() => setLoading(false));
  }, []);

  const reading = books.filter((b) => b.status === "READING");
  const finished = books.filter((b) => b.status === "FINISHED");
  const wantToRead = books.filter((b) => b.status === "WANT_TO_READ");
  const ratedFinished = finished.filter((b) => b.rating);
  const avgRating = ratedFinished.length
    ? (ratedFinished.reduce((sum, b) => sum + (b.rating ?? 0), 0) / ratedFinished.length).toFixed(1)
    : "–";
  const recentlyFinished = [...finished].slice(-4).reverse();

  return (
    <div className="page">
      <div className="page-header">
        <h1>Willkommen zurück</h1>
        <p>Deine persönliche Bibliothek auf einen Blick.</p>
      </div>

      {loading ? (
        <p>Lade Bibliothek…</p>
      ) : books.length === 0 ? (
        <div className="card empty-state">
          <p>Noch keine Bücher erfasst.</p>
          <Link to="/add" className="btn btn-accent">
            Erstes Buch hinzufügen
          </Link>
        </div>
      ) : (
        <>
          <div className="stats-grid">
            <div className="card stat-card">
              <div className="stat-value">{books.length}</div>
              <div className="stat-label">Bücher gesamt</div>
            </div>
            <div className="card stat-card">
              <div className="stat-value">{reading.length}</div>
              <div className="stat-label">Wird gelesen</div>
            </div>
            <div className="card stat-card">
              <div className="stat-value">{finished.length}</div>
              <div className="stat-label">Gelesen</div>
            </div>
            <div className="card stat-card">
              <div className="stat-value">{wantToRead.length}</div>
              <div className="stat-label">Leseliste</div>
            </div>
            <div className="card stat-card">
              <div className="stat-value">{avgRating}</div>
              <div className="stat-label">Ø Bewertung</div>
            </div>
          </div>

          <div className="section-title">
            <h2>Aktuell in Arbeit</h2>
            <Link to="/list" className="btn btn-ghost btn-sm">
              Zur Bibliothek
            </Link>
          </div>
          {reading.length === 0 ? (
            <div className="card empty-state" style={{ marginBottom: "2.5rem" }}>
              <p>Gerade wird kein Buch gelesen.</p>
            </div>
          ) : (
            <div className="card" style={{ marginBottom: "2.5rem" }}>
              {reading.map((book) => {
                const progress =
                  book.pages && book.currentPage ? Math.min(100, Math.round((book.currentPage / book.pages) * 100)) : null;
                return (
                  <div className="reading-row" key={book.id}>
                    <CoverChip title={book.title} categoryName={book.category?.name ?? ""} variant="sm" />
                    <div className="reading-info">
                      <strong>{book.title}</strong>
                      <span>{book.author}</span>
                    </div>
                    <div className="reading-progress">
                      {progress !== null ? (
                        <>
                          <div className="progress-label">{progress}%</div>
                          <div className="progress-track">
                            <div className="progress-fill" style={{ width: `${progress}%` }} />
                          </div>
                        </>
                      ) : (
                        <span className="field-hint">Kein Fortschritt erfasst</span>
                      )}
                    </div>
                  </div>
                );
              })}
            </div>
          )}

          {recentlyFinished.length > 0 && (
            <>
              <div className="section-title">
                <h2>Zuletzt gelesen</h2>
              </div>
              <div className="book-grid">
                {recentlyFinished.map((book) => (
                  <div className="card book-card" key={book.id}>
                    <div className="book-card-top">
                      <CoverChip title={book.title} categoryName={book.category?.name ?? ""} />
                      <div>
                        <div className="book-card-title">{book.title}</div>
                        <div className="book-card-author">{book.author}</div>
                      </div>
                    </div>
                    <StarRating value={clampRating(book.rating)} />
                  </div>
                ))}
              </div>
            </>
          )}
        </>
      )}
    </div>
  );
}
