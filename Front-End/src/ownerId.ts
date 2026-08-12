const STORAGE_KEY = "bookbuddy_owner_id";

// Anonyme, im Browser erzeugte Kennung, damit jeder Besucher der Live-Demo
// seine eigene Bibliothek sieht, statt sich eine gemeinsame mit allen
// anderen gleichzeitigen Besuchern zu teilen. Wird als Header an jeden
// API-Aufruf angehängt (siehe api.ts) statt per Cookie, da Frontend und
// Backend auf unterschiedlichen Origins laufen.
export function getOwnerId(): string {
  let ownerId = localStorage.getItem(STORAGE_KEY);
  if (!ownerId) {
    ownerId = crypto.randomUUID();
    localStorage.setItem(STORAGE_KEY, ownerId);
  }
  return ownerId;
}
