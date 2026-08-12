import { useEffect, useState } from "react";
import axios from "axios";
import { API_BASE_URL } from "../apiConfig";

interface Category {
  id: number;
  name: string;
}

interface Book {
  id: number;
  title: string;
  author: string;
  category?: { name: string };
}

interface EditBookFormProps {
  book: Book;
  onCancel: () => void;
  onSave: (updatedBook: Book) => void;
}

export default function EditBookForm({ book, onCancel, onSave }: EditBookFormProps) {
  const [title, setTitle] = useState(book.title);
  const [author, setAuthor] = useState(book.author);
  const [category, setCategory] = useState(book.category?.name || "");
  const [categories, setCategories] = useState<Category[]>([]);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get(`${API_BASE_URL}/api/categories`);
        setCategories(response.data);
      } catch (error) {
        console.error("Fehler beim Laden der Kategorien:", error);
        setError("Kategorien konnten nicht geladen werden.");
      }
    };
    fetchCategories();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    if (!title || !author || !category) {
      setError("Bitte fülle alle Felder aus.");
      return;
    }

    try {
      const response = await axios.put(`${API_BASE_URL}/api/books/${book.id}`, {
        id: book.id,
        title,
        author,
        category: { name: category }
      });

      if (response.status === 200) {
        onSave(response.data);
      } else {
        setError("Fehler beim Speichern.");
      }
    } catch (err: any) {
      const msg = err?.response?.data || "Unbekannter Fehler beim Speichern.";
      setError(msg);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginTop: "10px" }}>
      <input
        type="text"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        placeholder="Titel"
        required
        style={{ display: "block", marginBottom: "10px" }}
      />

      <input
        type="text"
        value={author}
        onChange={(e) => setAuthor(e.target.value)}
        placeholder="Autor"
        required
        style={{ display: "block", marginBottom: "10px" }}
      />

      <select
        value={category}
        onChange={(e) => setCategory(e.target.value)}
        required
        style={{ display: "block", marginBottom: "10px" }}
      >
        <option value="">Kategorie wählen</option>
        {categories.map((cat) => (
          <option key={cat.id} value={cat.name}>{cat.name}</option>
        ))}
      </select>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <button type="submit">Speichern</button>
      <button type="button" onClick={onCancel} style={{ marginLeft: "10px" }}>
        Abbrechen
      </button>
    </form>
  );
} 
