import { useEffect, useState } from "react";
import axios from "axios";
import { API_BASE_URL } from "../apiConfig";
import EditBookForm from "../components/EditBookForm";

// Typdefinition eines Buch-Objekts
interface Book {
  id: number;
  title: string;
  author: string;
  category?: {
    name: string;
  };
}

export default function Home() {
  const [books, setBooks] = useState<Book[]>([]);
  const [editingBook, setEditingBook] = useState<Book | null>(null);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const response = await axios.get(`${API_BASE_URL}/api/books`);
        const data = Array.isArray(response.data)
          ? response.data
          : response.data.content || [];
        setBooks(data);
      } catch (error) {
        console.error("Fehler beim Laden der Bücher:", error);
      }
    };

    fetchBooks();
  }, []);

  const handleDelete = async (id: number) => {
    try {
      const response = await axios.delete(`${API_BASE_URL}/api/books/${id}`);
      if (response.status === 200 || response.status === 204) {
        setBooks((prev) => prev.filter((book) => book.id !== id));
      } else {
        console.error("Löschen fehlgeschlagen");
      }
    } catch (error) {
      console.error("Netzwerkfehler beim Löschen:", error);
    }
  };

  const handleEdit = (book: Book) => {
    setEditingBook(book);
  };

  const handleSave = async (updatedBook: Book) => {
    try {
      const response = await axios.put(
        `${API_BASE_URL}/api/books/${updatedBook.id}`,
        updatedBook,
        { headers: { "Content-Type": "application/json" } }
      );

      if (response.status === 200) {
        setBooks((prev) =>
          prev.map((b) => (b.id === updatedBook.id ? updatedBook : b))
        );
        setEditingBook(null);
      } else {
        console.error("Update fehlgeschlagen");
      }
    } catch (error) {
      console.error("Netzwerkfehler beim Speichern:", error);
    }
  };

  return (
    <div>
      <h1>BookBuddy</h1>
      <h2>Bücherübersicht</h2>

      <ul>
        {books.map((book) => (
          <li key={book.id} style={{ marginBottom: "20px" }}>
            <strong>{book.title}</strong> von {book.author}
            <br />
            Kategorie: {book.category?.name || "Unbekannt"}
            <br />
            <button onClick={() => handleDelete(book.id)}>Löschen</button>
            <button onClick={() => handleEdit(book)}>Bearbeiten</button>

            {editingBook && editingBook.id === book.id && (
              <EditBookForm
                book={editingBook}
                onCancel={() => setEditingBook(null)}
                onSave={handleSave}
              />
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}
