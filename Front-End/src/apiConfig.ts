// Basis-URL des Backends.
//
// Im gebauten Stand ist sie leer: Das Frontend wird vom Spring-Boot-Dienst
// selbst ausgeliefert, Aufrufe gehen also an dieselbe Herkunft und kommen mit
// relativen Pfaden aus. Das spart CORS und macht die Anwendung unabhaengig
// davon, unter welcher Domain sie laeuft.
//
// Beim Entwickeln mit `npm run dev` laeuft das Frontend dagegen auf Port 5173
// und das Backend getrennt auf 8080 - dort wird die volle Adresse gebraucht.
// VITE_API_BASE_URL kann beides ueberschreiben.
export const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL ?? (import.meta.env.DEV ? 'http://localhost:8080' : '')
