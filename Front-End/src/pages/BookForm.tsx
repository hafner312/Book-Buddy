import { useNavigate } from "react-router-dom";
import BookEditor from "../components/BookEditor";

export default function BookForm(): JSX.Element {
  const navigate = useNavigate();

  return (
    <div className="page">
      <div className="page-header">
        <h1>Neues Buch hinzufügen</h1>
        <p>Erfasse ein Buch mit Lesestatus, Fortschritt und persönlichen Notizen.</p>
      </div>
      <BookEditor mode="create" onSuccess={() => navigate("/list")} />
    </div>
  );
}
