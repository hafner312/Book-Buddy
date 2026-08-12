import axios from "axios";
import { API_BASE_URL } from "./apiConfig";
import { getOwnerId } from "./ownerId";
import type { Book, BookInput, Category } from "./types";

const client = axios.create({ baseURL: API_BASE_URL });
client.interceptors.request.use((config) => {
  config.headers["X-Owner-Id"] = getOwnerId();
  return config;
});

function unwrapList<T>(data: unknown): T[] {
  if (Array.isArray(data)) return data as T[];
  if (data && typeof data === "object" && Array.isArray((data as { content?: T[] }).content)) {
    return (data as { content: T[] }).content;
  }
  return [];
}

export async function fetchBooks(): Promise<Book[]> {
  const { data } = await client.get("/api/books");
  return unwrapList<Book>(data);
}

export async function fetchCategories(): Promise<Category[]> {
  const { data } = await client.get("/api/categories");
  return unwrapList<Category>(data);
}

export async function createCategory(name: string): Promise<Category> {
  const { data } = await client.post("/api/categories", { name });
  return data;
}

export async function createBook(book: BookInput): Promise<Book> {
  const { data } = await client.post("/api/books", book);
  return data;
}

export async function updateBook(id: number, book: BookInput): Promise<Book> {
  const { data } = await client.put(`/api/books/${id}`, book);
  return data;
}

export async function deleteBook(id: number): Promise<void> {
  await client.delete(`/api/books/${id}`);
}

export function extractErrorMessage(err: unknown, fallback: string): string {
  const anyErr = err as { response?: { data?: unknown } };
  const data = anyErr?.response?.data;
  if (typeof data === "string" && data.trim()) return data;
  return fallback;
}
