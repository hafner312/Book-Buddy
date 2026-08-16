import { useEffect, useMemo, useState } from "react";
import { deleteBook, extractErrorMessage, fetchBooks } from "../api";
import type { Book } from "../types";
import { STATUS_LABELS, STATUS_ORDER } from "../types";
import BookCard from "../components/BookCard";
import BookEditor from "../components/BookEditor";
import LadeHinweis from "../components/LadeHinweis";
import Modal from "../components/Modal";

type SortKey = "title" | "author" | "rating";

export default function BookList(): JSX.Element {
  const [books, setBooks] = useState<Book[]>([]);
  const [loading, setLoading] = useState(true);
  const [loadError, setLoadError] = useState("");

  const [search, setSearch] = useState("");
  const [categoryFilter, setCategoryFilter] = useState("");
  const [statusFilter, setStatusFilter] = useState("");
  const [sortKey, setSortKey] = useState<SortKey>("title");

  const [editingBook, setEditingBook] = useState<Book | null>(null);
  const [pendingDelete, setPendingDelete] = useState<Book | null>(null);
  const [deleteError, setDeleteError] = useState("");

  const loadBooks = () => {
    setLoading(true);
    fetchBooks()
      .then((data) => {
        setBooks(data);
        setLoadError("");
      })
      .catch(() => setLoadError("Bücher konnten nicht geladen werden."))
      .finally(() => setLoading(false));
  };

  useEffect(loadBooks, []);

  const categories = useMemo(
    () => Array.from(new Set(books.map((b) => b.category?.name).filter(Boolean))) as string[],
    [books]
  );

  const filtered = useMemo(() => {
    const term = search.trim().toLowerCase();
    let result = books.filter((b) => {
      const matchesTerm = !term || b.title.toLowerCase().includes(term) || b.author.toLowerCase().includes(term);
      const matchesCategory = !categoryFilter || b.category?.name === categoryFilter;
      const matchesStatus = !statusFilter || b.status === statusFilter;
      return matchesTerm && matchesCategory && matchesStatus;
    });

    result = [...result].sort((a, b) => {
      if (sortKey === "rating") return (b.rating ?? 0) - (a.rating ?? 0);
      return a[sortKey].localeCompare(b[sortKey], "de");
    });

    return result;
  }, [books, search, categoryFilter, statusFilter, sortKey]);

  const handleDelete = async () => {
    if (!pendingDelete) return;
    setDeleteError("");
    try {
      await deleteBook(pendingDelete.id);
      setBooks((prev) => prev.filter((b) => b.id !== pendingDelete.id));
      setPendingDelete(null);
    } catch (err) {
      setDeleteError(extractErrorMessage(err, "Löschen fehlgeschlagen."));
    }
  };

  return (
    <div className="page">
      <div className="page-header">
        <h1>Bibliothek</h1>
        <p>Durchsuchen, filtern und verwalten deiner gesamten Sammlung.</p>
      </div>

      <div className="toolbar">
        <input
          type="text"
          placeholder="Titel oder Autor suchen…"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
        <select value={categoryFilter} onChange={(e) => setCategoryFilter(e.target.value)}>
          <option value="">Alle Kategorien</option>
          {categories.map((cat) => (
            <option key={cat} value={cat}>
              {cat}
            </option>
          ))}
        </select>
        <select value={statusFilter} onChange={(e) => setStatusFilter(e.target.value)}>
          <option value="">Alle Status</option>
          {STATUS_ORDER.map((s) => (
            <option key={s} value={s}>
              {STATUS_LABELS[s]}
            </option>
          ))}
        </select>
        <select value={sortKey} onChange={(e) => setSortKey(e.target.value as SortKey)}>
          <option value="title">Sortieren: Titel</option>
          <option value="author">Sortieren: Autor</option>
          <option value="rating">Sortieren: Bewertung</option>
        </select>
        <span className="result-count">
          {filtered.length} von {books.length} Büchern
        </span>
      </div>

      {loading ? (
        <LadeHinweis text="Lade Bücher…" />
      ) : loadError ? (
        <p className="form-error">{loadError}</p>
      ) : filtered.length === 0 ? (
        <div className="card empty-state">
          <p>Keine Bücher gefunden.</p>
        </div>
      ) : (
        <div className="book-grid">
          {filtered.map((book) => (
            <BookCard key={book.id} book={book} onEdit={setEditingBook} onDelete={setPendingDelete} />
          ))}
        </div>
      )}

      {editingBook && (
        <Modal title="Buch bearbeiten" onClose={() => setEditingBook(null)}>
          <BookEditor
            mode="edit"
            initial={editingBook}
            onCancel={() => setEditingBook(null)}
            onSuccess={(updated) => {
              setBooks((prev) => prev.map((b) => (b.id === updated.id ? updated : b)));
              setEditingBook(null);
            }}
          />
        </Modal>
      )}

      {pendingDelete && (
        <Modal title="Buch löschen" onClose={() => setPendingDelete(null)}>
          <p>
            „{pendingDelete.title}" von {pendingDelete.author} wirklich unwiderruflich löschen?
          </p>
          {deleteError && <p className="form-error">{deleteError}</p>}
          <div className="form-actions">
            <button className="btn btn-danger" onClick={handleDelete}>
              Endgültig löschen
            </button>
            <button className="btn btn-ghost" onClick={() => setPendingDelete(null)}>
              Abbrechen
            </button>
          </div>
        </Modal>
      )}
    </div>
  );
}
