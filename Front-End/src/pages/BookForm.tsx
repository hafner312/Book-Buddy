import { useState, useEffect } from "react";
import axios from "axios";

export default function BookForm() {
  const [title, setTitle] = useState<string>("");
  const [author, setAuthor] = useState<string>("");
  const [category, setCategory] = useState<string>("");
  const [categories, setCategories] = useState<string[]>([]);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/categories");
        const categoryNames = response.data.map((cat: { name: string }) => cat.name);
        setCategories(categoryNames);
      } catch (err) {
        console.error("Fehler beim Laden der Kategorien:", err);
        setError("Kategorien konnten nicht geladen werden.");
      }
    };
    fetchCategories();
  }, []);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError("");

    if (!title || !author || !category) {
      setError("Bitte fülle alle Felder aus.");
      return;
    }

    const payload = {
      title,
      author,
      category: { name: category }
    };

    try {
      const response = await axios.post("http://localhost:8080/api/books", payload);
      if (response.status === 200 || response.status === 201) {
        alert("Buch erfolgreich gespeichert.");
        setTitle("");
        setAuthor("");
        setCategory("");
      } else {
        setError("Speichern fehlgeschlagen. Versuche es erneut.");
      }
    } catch (err: any) {
      const backendMsg = err?.response?.data;
      setError(backendMsg || "Verbindung zum Server fehlgeschlagen.");
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ maxWidth: "500px", margin: "0 auto", textAlign: "left" }}>
      <h2>Neues Buch hinzufügen</h2>

      <label>
        Titel:
        <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} required />
      </label>
      <br />

      <label>
        Autor:
        <input type="text" value={author} onChange={(e) => setAuthor(e.target.value)} required />
      </label>
      <br />

      <label>
        Kategorie:
        <select value={category} onChange={(e) => setCategory(e.target.value)} required>
          <option value="">Kategorie wählen</option>
          {categories.map((cat, index) => (
            <option key={index} value={cat}>{cat}</option>
          ))}
        </select>
      </label>
      <br />

      {error && <p style={{ color: "red" }}>{error}</p>}

      <button type="submit">Buch hinzufügen</button>
      <button
        type="reset"
        onClick={() => {
          setTitle("");
          setAuthor("");
          setCategory("");
          setError("");
        }}
        style={{ marginLeft: "10px" }}
      >
        Abbrechen
      </button>
    </form>
  );
}
