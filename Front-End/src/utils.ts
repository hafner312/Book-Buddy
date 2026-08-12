const PALETTE: [string, string][] = [
  ["#7a3b3b", "#a35555"],
  ["#2f4d3a", "#4a7a5c"],
  ["#1f5c8b", "#3f83b5"],
  ["#5c4a8a", "#8570bb"],
  ["#8a6a2f", "#c79a4a"],
  ["#3b6e6e", "#5fa3a3"],
  ["#7a3b64", "#a35590"],
  ["#4a5c8a", "#7288ba"],
];

/** Leitet aus einem Kategorienamen deterministisch ein Farbverlauf-Paar ab, damit jede Kategorie ein wiedererkennbares "Cover" bekommt. */
export function coverGradient(seed: string): string {
  let hash = 0;
  for (let i = 0; i < seed.length; i++) {
    hash = (hash << 5) - hash + seed.charCodeAt(i);
    hash |= 0;
  }
  const [from, to] = PALETTE[Math.abs(hash) % PALETTE.length];
  return `linear-gradient(155deg, ${from}, ${to})`;
}

export function initials(title: string): string {
  const trimmed = title.trim();
  return trimmed ? trimmed[0].toUpperCase() : "?";
}

export function clampRating(value: number | null | undefined): number {
  if (!value) return 0;
  return Math.min(5, Math.max(0, Math.round(value)));
}
