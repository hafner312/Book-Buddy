// Import der benötigten Module für Routing
import { BrowserRouter, Routes, Route } from "react-router-dom";

// Import der Seitenkomponenten
import Home from "./pages/Home";
import BookForm from "./pages/BookForm";
import BookList from "./pages/BookList";
import Impressum from "./pages/Impressum";
import NotFound from "./pages/NotFound";

// Import der Navigationsleiste
import GlobalNavigation from "./components/GlobalNavigation";

// Hauptkomponente der Anwendung
function App(): JSX.Element {
  return (
    <BrowserRouter>
      {/* Globale Navigation am oberen Rand */}
      <GlobalNavigation />

      {/* Definierte Routen der Anwendung */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/add" element={<BookForm />} />
        <Route path="/list" element={<BookList />} />
        <Route path="/impressum" element={<Impressum />} />
        <Route path="*" element={<NotFound />} /> {/* Fallback-Route */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
