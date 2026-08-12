import { coverGradient, initials } from "../utils";

interface CoverChipProps {
  title: string;
  categoryName: string;
  variant?: "sm" | "lg";
}

export default function CoverChip({ title, categoryName, variant = "lg" }: CoverChipProps): JSX.Element {
  const className = variant === "lg" ? "book-card-cover" : "cover-chip";
  return (
    <div className={className} style={{ background: coverGradient(categoryName || title) }} title={categoryName}>
      {initials(title)}
    </div>
  );
}
