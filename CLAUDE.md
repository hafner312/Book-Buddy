# CLAUDE.md

Guidance for Claude Code when working in this repository.

## Project overview

Book-Buddy is a fullstack book-management app: a React + TypeScript SPA (Vite) talking to a Spring Boot REST API backed by MySQL. It is a Swiss vocational-school project (module M294/M295, `ch.wiss` group id). Code comments, docs, and validation messages are in German.

## Build / run / test commands

Backend (Spring Boot, Maven, Java 17) — run from `backend/`:
```bash
./mvnw spring-boot:run      # start API on http://localhost:8080
./mvnw test                 # run backend tests (JUnit 5 + Mockito)
./mvnw package               # build jar
```

Frontend (React + Vite) — run from `Front-End/`:
```bash
npm install
npm run dev        # dev server on http://localhost:5173
npm run build       # production build
npm run lint         # ESLint
npm run test          # Jest + React Testing Library
```

The backend requires a running MySQL instance matching `backend/src/main/resources/application.properties` (db `bookbuddy`, user `bookbuddy_user`); schema/seed data is in `Ressourcen/bookbuddy_init.sql`.

## Architecture notes

- **Backend** (`backend/src/main/java/ch/bookbuddy/backend/`): standard layered Spring Boot structure — `controller/` (REST endpoints under `/api/...`), `service/` (business logic), `repository/` (Spring Data JPA), `model/` (`Book`, `Category` JPA entities, many-to-one relationship), `config/`, `exception/`. `BookController` exposes `GET/POST/PUT/DELETE /api/books`.
- **Frontend** (`Front-End/src/`): plain Vite + React (not Create React App), TypeScript, `react-router-dom` for routing, `axios` for API calls. `pages/` holds route-level components (Home, BookList, BookForm, Impressum, NotFound); `components/` holds reusable UI (Button, CategorySelection, EditBookForm, GlobalNavigation).
- The frontend reaches the API through `src/apiConfig.ts`, not through a Vite proxy: in a production build the base URL is empty (same origin), during `npm run dev` it points at `http://localhost:8080`. `VITE_API_BASE_URL` overrides both.
- Each visitor gets their own library. The browser generates an id (`src/ownerId.ts`), sends it as the `X-Owner-Id` header on every call, and `OwnerIdFilter` scopes the data to it server-side.
- There is a pre-existing `README.md` inside the nested project folder (original student README, German) — don't confuse it with the top-level one at the repo root.

## Deployment: one service, not two

The repo-root `Dockerfile` builds **both** parts into a single artifact: stage 1 builds the React app, stage 2 copies `dist/` into `backend/src/main/resources/static/` and packages the Spring Boot jar, stage 3 runs it. Spring then serves the frontend and the API from the same origin.

Two consequences worth keeping in mind when editing:

- **The Docker build context is the repo root**, not `backend/`. A `Dockerfile` under `backend/` could not reach `Front-End/`.
- **`SpaConfig` routes unknown paths to `index.html`** so that react-router routes such as `/list` survive a reload. Paths under `api/` and anything that looks like a file (has an extension) deliberately still return 404 — otherwise a wrong API call would answer with HTML instead of an error.

CORS is therefore only needed for local development (Vite on 5173, backend on 8080); `CorsConfig` allows just those origins.

## Conventions

- Backend Javadoc-style comments and validation messages are in German — match that style when editing Java code.
- Bean Validation (`@NotBlank`, `@Size`) is used on entities; keep frontend-side validation consistent with backend constraints.
- The live demo runs on Render's free tier with an in-memory H2 database (`application-render.properties`), so data resets whenever the service restarts. `BookSeeder` refills the example books.

## Ordnerstruktur

Dieser Ordner ist ein echter Git-Klon des Repositorys - der Quellcode liegt direkt hier, nicht in einem Unterordner. Aenderungen hier committen und pushen; die frueher genutzte ZIP-Entpackung wurde entfernt, weil sie keine Verbindung zu GitHub hatte und veraltete.
