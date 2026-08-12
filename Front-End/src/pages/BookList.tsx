import { useEffect, useState } from "react";
import axios from "axios";
import { API_BASE_URL } from "../apiConfig";

// Typdefinition für ein Buch
interface Book {
  id: number;
  title: string;
  author: string;
  category?: {
    name: string;
  };
}

export default function BookList() {
  const [books, setBooks] = useState<Book[]>([]);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editedTitle, setEditedTitle] = useState("");
  const [editedAuthor, setEditedAuthor] = useState("");
  const [loading, setLoading] = useState(false);

  // Bücher beim Start laden
  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/books`);
      const list = Array.isArray(response.data)
        ? response.data
        : response.data.content || [];
      setBooks(list);
    } catch (error) {
      console.error("❌ Fehler beim Laden der Bücher", error);
      alert("Fehler beim Laden der Bücher.");
    }
  };

  const startEditing = (book: Book) => {
    setEditingId(book.id);
    setEditedTitle(book.title);
    setEditedAuthor(book.author);
  };

  const cancelEdit = () => {
    setEditingId(null);
    setEditedTitle("");
    setEditedAuthor("");
  };

  const handleSave = async (id: number) => {
    if (!editedTitle || !editedAuthor) {
      alert("Bitte alle Felder ausfüllen.");
      return;
    }

    try {
      setLoading(true);
      const payload = {
        title: editedTitle,
        author: editedAuthor,
        category: books.find((b) => b.id === id)?.category ?? null,
      };
      await axios.put(`${API_BASE_URL}/api/books/${id}`, payload);
      alert("Buch erfolgreich aktualisiert.");
      fetchBooks();
      cancelEdit();
    } catch (err) {
      console.error("Fehler beim Speichern", err);
      alert("Fehler beim Aktualisieren des Buches.");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!window.confirm("Buch wirklich löschen?")) return;

    try {
      await axios.delete(`${API_BASE_URL}/api/books/${id}`);
      fetchBooks();
    } catch (err) {
      console.error("Fehler beim Löschen", err);
      alert("Fehler beim Löschen des Buches.");
    }
  };

  return (
    <div style={{ maxWidth: "800px", margin: "0 auto", padding: "1rem" }}>
      <h2>Bücherübersicht</h2>

      {books.length === 0 ? (
        <p>Keine Bücher vorhanden.</p>
      ) : (
        <ul>
          {books.map((book) =>
            editingId === book.id ? (
              <li key={book.id} style={{ marginBottom: "20px" }}>
                <input
                  type="text"
                  value={editedTitle}
                  onChange={(e) => setEditedTitle(e.target.value)}
                  placeholder="Titel"
                  style={{ display: "block", marginBottom: "10px", width: "100%" }}
                />
                <input
                  type="text"
                  value={editedAuthor}
                  onChange={(e) => setEditedAuthor(e.target.value)}
                  placeholder="Autor"
                  style={{ display: "block", marginBottom: "10px", width: "100%" }}
                />
                <button onClick={() => handleSave(book.id)} disabled={loading}>
                  {loading ? "Speichert..." : "Speichern"}
                </button>
                <button onClick={cancelEdit} style={{ marginLeft: "10px" }}>
                  Abbrechen
                </button>
              </li>
            ) : (
              <li key={book.id} style={{ marginBottom: "20px" }}>
                <strong>{book.title}</strong> von {book.author}
                <br />
                Kategorie: {book.category?.name || "Keine Kategorie"}
                <br />
                <button onClick={() => handleDelete(book.id)}>Löschen</button>
                <button onClick={() => startEditing(book)} style={{ marginLeft: "10px" }}>
                  Bearbeiten
                </button>
              </li>
            )
          )}
        </ul>
      )}
    </div>
  );
}
