interface StarRatingProps {
  value: number;
  onChange?: (value: number) => void;
}

export default function StarRating({ value, onChange }: StarRatingProps): JSX.Element {
  const interactive = !!onChange;
  return (
    <span className={`star-rating${interactive ? " interactive" : ""}`} role={interactive ? "radiogroup" : undefined} aria-label="Bewertung">
      {[1, 2, 3, 4, 5].map((n) => (
        <span
          key={n}
          className={`star${n <= value ? " filled" : ""}`}
          onClick={interactive ? () => onChange!(n === value ? 0 : n) : undefined}
          role={interactive ? "radio" : undefined}
          aria-checked={interactive ? n <= value : undefined}
        >
          {n <= value ? "★" : "☆"}
        </span>
      ))}
    </span>
  );
}
