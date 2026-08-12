import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import BookForm from "./pages/BookForm";
import BookList from "./pages/BookList";
import Impressum from "./pages/Impressum";
import NotFound from "./pages/NotFound";

import GlobalNavigation from "./components/GlobalNavigation";

function App(): JSX.Element {
  return (
    <BrowserRouter>
      <GlobalNavigation />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/add" element={<BookForm />} />
        <Route path="/list" element={<BookList />} />
        <Route path="/impressum" element={<Impressum />} />
        <Route path="*" element={<NotFound />} />
      </Routes>

      <footer className="site-footer">BookBuddy – ein Projekt von Patrik Hafner</footer>
    </BrowserRouter>
  );
}

export default App;
