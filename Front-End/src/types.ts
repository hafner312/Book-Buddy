export type ReadingStatus = "WANT_TO_READ" | "READING" | "FINISHED";

export const STATUS_LABELS: Record<ReadingStatus, string> = {
  WANT_TO_READ: "Möchte ich lesen",
  READING: "Wird gelesen",
  FINISHED: "Gelesen",
};

export const STATUS_ORDER: ReadingStatus[] = ["WANT_TO_READ", "READING", "FINISHED"];

export interface Category {
  id: number;
  name: string;
}

export interface Book {
  id: number;
  title: string;
  author: string;
  category: Category;
  status: ReadingStatus;
  rating?: number | null;
  pages?: number | null;
  currentPage?: number | null;
  notes?: string | null;
}

export type BookInput = {
  title: string;
  author: string;
  category: { name: string };
  status: ReadingStatus;
  rating: number | null;
  pages: number | null;
  currentPage: number | null;
  notes: string | null;
};
