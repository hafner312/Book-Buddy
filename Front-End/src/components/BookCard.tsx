import type { Book } from "../types";
import { STATUS_LABELS } from "../types";
import CoverChip from "./CoverChip";
import StarRating from "./StarRating";
import { clampRating } from "../utils";

interface BookCardProps {
  book: Book;
  onEdit: (book: Book) => void;
  onDelete: (book: Book) => void;
}

export default function BookCard({ book, onEdit, onDelete }: BookCardProps): JSX.Element {
  const progress =
    book.status === "READING" && book.pages && book.currentPage
      ? Math.min(100, Math.round((book.currentPage / book.pages) * 100))
      : null;

  return (
    <div className="card book-card">
      <div className="book-card-top">
        <CoverChip title={book.title} categoryName={book.category?.name ?? ""} />
        <div>
          <div className="book-card-title">{book.title}</div>
          <div className="book-card-author">{book.author}</div>
        </div>
      </div>

      <div className="book-card-meta">
        <span className="badge badge-category">{book.category?.name ?? "Ohne Kategorie"}</span>
        <span className={`badge badge-status-${book.status}`}>{STATUS_LABELS[book.status]}</span>
      </div>

      {book.status === "FINISHED" && <StarRating value={clampRating(book.rating)} />}

      {progress !== null && (
        <div>
          <div className="progress-track">
            <div className="progress-fill" style={{ width: `${progress}%` }} />
          </div>
          <div className="field-hint">
            Seite {book.currentPage} von {book.pages} ({progress}%)
          </div>
        </div>
      )}

      {book.notes && <p className="book-card-notes">„{book.notes}"</p>}

      <div className="book-card-actions">
        <button className="btn btn-ghost btn-sm" onClick={() => onEdit(book)}>
          Bearbeiten
        </button>
        <button className="btn btn-danger btn-sm" onClick={() => onDelete(book)}>
          Löschen
        </button>
      </div>
    </div>
  );
}
