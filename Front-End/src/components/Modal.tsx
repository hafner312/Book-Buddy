import { ReactNode } from "react";

interface ModalProps {
  title: string;
  onClose: () => void;
  children: ReactNode;
}

export default function Modal({ title, onClose, children }: ModalProps): JSX.Element {
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-card card" onClick={(e) => e.stopPropagation()}>
        <div className="modal-card-header">
          <h2>{title}</h2>
          <button className="modal-close" onClick={onClose} aria-label="Schliessen">
            ✕
          </button>
        </div>
        {children}
      </div>
    </div>
  );
}
