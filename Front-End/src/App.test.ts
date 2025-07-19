// Importiert zusätzliche Matcher für Jest-Assertions wie .toBeInTheDocument()
import "@testing-library/jest-dom";

// React Testing Library: Tools zum Rendern und Interagieren mit Komponenten
import { render, screen } from "@testing-library/react";

// Router-Kontext wird simuliert (für Routing-Tests)
import { MemoryRouter } from "react-router-dom";

// Die App-Komponente, die getestet wird
import App from "./App";

// ✅ MOCK: GlobalNavigation-Komponente wird durch Dummy ersetzt
jest.mock("./components/GlobalNavigation", () => () => (
  <div>NavigationRenderedXYZ</div>
));

// 🔍 Test-Suite für App-Komponente
describe("App-Komponente – Routing und Navigation", () => {
  test("Navigation sollte korrekt gerendert werden", () => {
    // Arrange: Erwarteter Text aus dem Mock
    const gesuchterText = "NavigationRenderedXYZ";

    // Act: App-Komponente wird mit MemoryRouter gerendert (Simulierter Router-Kontext)
    render(
      <MemoryRouter>
        <App />
      </MemoryRouter>
    );

    // Assert: Prüfen, ob die Navigation erscheint
    expect(screen.getByText(gesuchterText)).toBeInTheDocument();
  });

  test("Route '/impressum' zeigt Impressum-Seite", () => {
    render(
      <MemoryRouter initialEntries={["/impressum"]}>
        <App />
      </MemoryRouter>
    );
    expect(screen.getByText("Impressum")).toBeInTheDocument();
  });

  test("Route '/rules' zeigt Fightclub Rules", () => {
    render(
      <MemoryRouter initialEntries={["/rules"]}>
        <App />
      </MemoryRouter>
    );
    expect(screen.getByText("Fightclub Rules")).toBeInTheDocument();
  });

  test("Demo-Test: true ist true", () => {
    expect(true).toBe(true);
  });
});
