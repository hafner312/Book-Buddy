import { FormEvent, useEffect, useState } from "react";
import { createBook, createCategory, extractErrorMessage, fetchCategories, updateBook } from "../api";
import type { Book, BookInput, Category, ReadingStatus } from "../types";
import { STATUS_LABELS, STATUS_ORDER } from "../types";
import StarRating from "./StarRating";

const NEW_CATEGORY = "__new__";

interface BookEditorProps {
  mode: "create" | "edit";
  initial?: Book;
  onSuccess: (book: Book) => void;
  onCancel?: () => void;
}

export default function BookEditor({ mode, initial, onSuccess, onCancel }: BookEditorProps): JSX.Element {
  const [title, setTitle] = useState(initial?.title ?? "");
  const [author, setAuthor] = useState(initial?.author ?? "");
  const [categories, setCategories] = useState<Category[]>([]);
  const [categoryChoice, setCategoryChoice] = useState<string>(initial?.category?.name ?? "");
  const [newCategoryName, setNewCategoryName] = useState("");
  const [status, setStatus] = useState<ReadingStatus>(initial?.status ?? "WANT_TO_READ");
  const [pages, setPages] = useState<string>(initial?.pages != null ? String(initial.pages) : "");
  const [currentPage, setCurrentPage] = useState<string>(initial?.currentPage != null ? String(initial.currentPage) : "");
  const [rating, setRating] = useState<number>(initial?.rating ?? 0);
  const [notes, setNotes] = useState(initial?.notes ?? "");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    fetchCategories()
      .then(setCategories)
      .catch(() => setError("Kategorien konnten nicht geladen werden."));
  }, []);

  const resetFields = () => {
    setTitle("");
    setAuthor("");
    setCategoryChoice("");
    setNewCategoryName("");
    setStatus("WANT_TO_READ");
    setPages("");
    setCurrentPage("");
    setRating(0);
    setNotes("");
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    const categoryName = categoryChoice === NEW_CATEGORY ? newCategoryName.trim() : categoryChoice;
    if (!title.trim() || !author.trim() || !categoryName) {
      setError("Bitte Titel, Autor und Kategorie ausfüllen.");
      return;
    }

    setSubmitting(true);
    try {
      if (categoryChoice === NEW_CATEGORY) {
        const created = await createCategory(categoryName);
        setCategories((prev) => (prev.some((c) => c.name === created.name) ? prev : [...prev, created]));
      }

      const payload: BookInput = {
        title: title.trim(),
        author: author.trim(),
        category: { name: categoryName },
        status,
        rating: status === "FINISHED" ? rating || null : null,
        pages: pages ? Number(pages) : null,
        currentPage: status === "READING" && currentPage ? Number(currentPage) : null,
        notes: notes.trim() || null,
      };

      const saved = mode === "edit" && initial ? await updateBook(initial.id, payload) : await createBook(payload);

      setSuccess(mode === "edit" ? "Buch aktualisiert." : "Buch hinzugefügt.");
      onSuccess(saved);
      if (mode === "create") resetFields();
    } catch (err) {
      setError(extractErrorMessage(err, "Verbindung zum Server fehlgeschlagen."));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <form className="form" onSubmit={handleSubmit}>
      <div className="field">
        <label htmlFor="title">Titel</label>
        <input id="title" type="text" value={title} onChange={(e) => setTitle(e.target.value)} required />
      </div>

      <div className="field">
        <label htmlFor="author">Autor</label>
        <input id="author" type="text" value={author} onChange={(e) => setAuthor(e.target.value)} required />
      </div>

      <div className="field">
        <label htmlFor="category">Kategorie</label>
        <select id="category" value={categoryChoice} onChange={(e) => setCategoryChoice(e.target.value)} required>
          <option value="">Kategorie wählen</option>
          {categories.map((cat) => (
            <option key={cat.id} value={cat.name}>
              {cat.name}
            </option>
          ))}
          <option value={NEW_CATEGORY}>+ Neue Kategorie…</option>
        </select>
        {categoryChoice === NEW_CATEGORY && (
          <input
            type="text"
            placeholder="Name der neuen Kategorie"
            value={newCategoryName}
            onChange={(e) => setNewCategoryName(e.target.value)}
            style={{ marginTop: "0.5rem" }}
            required
          />
        )}
      </div>

      <div className="field">
        <label htmlFor="status">Lesestatus</label>
        <select id="status" value={status} onChange={(e) => setStatus(e.target.value as ReadingStatus)}>
          {STATUS_ORDER.map((s) => (
            <option key={s} value={s}>
              {STATUS_LABELS[s]}
            </option>
          ))}
        </select>
      </div>

      <div className="form-row">
        <div className="field">
          <label htmlFor="pages">Seitenzahl</label>
          <input id="pages" type="number" min={1} value={pages} onChange={(e) => setPages(e.target.value)} placeholder="optional" />
        </div>
        {status === "READING" && (
          <div className="field">
            <label htmlFor="currentPage">Aktuelle Seite</label>
            <input
              id="currentPage"
              type="number"
              min={0}
              value={currentPage}
              onChange={(e) => setCurrentPage(e.target.value)}
              placeholder="optional"
            />
          </div>
        )}
      </div>

      {status === "FINISHED" && (
        <div className="field">
          <label>Bewertung</label>
          <StarRating value={rating} onChange={setRating} />
        </div>
      )}

      <div className="field">
        <label htmlFor="notes">Notizen</label>
        <textarea id="notes" value={notes} onChange={(e) => setNotes(e.target.value)} placeholder="Persönliche Eindrücke, Zitate, …" />
      </div>

      {error && <p className="form-error">{error}</p>}
      {success && <p className="form-success">{success}</p>}

      <div className="form-actions">
        <button type="submit" className="btn btn-accent" disabled={submitting}>
          {submitting ? "Speichert…" : mode === "edit" ? "Speichern" : "Buch hinzufügen"}
        </button>
        {onCancel && (
          <button type="button" className="btn btn-ghost" onClick={onCancel}>
            Abbrechen
          </button>
        )}
      </div>
    </form>
  );
}
