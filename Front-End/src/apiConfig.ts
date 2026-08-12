// Basis-URL des Backends. Lokal: http://localhost:8080 (Standard).
// Auf Render wird VITE_API_BASE_URL beim Build gesetzt und zeigt auf den
// deployten Backend-Dienst.
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'
