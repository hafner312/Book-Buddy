import { useEffect, useState } from "react";

/**
 * Ladeanzeige, die nach einigen Sekunden erklaert, warum es dauert.
 *
 * Die Live-Demo laeuft auf dem kostenlosen Render-Tarif. Dort wird der Dienst
 * nach einer Weile ohne Zugriffe angehalten und muss beim naechsten Aufruf
 * erst wieder hochfahren - das kann ein paar Minuten dauern. Ohne Erklaerung
 * sieht die Seite in dieser Zeit schlicht kaputt aus, und wer sie zum ersten
 * Mal oeffnet, klickt weg.
 *
 * Deshalb: die ersten Sekunden ein normaler Ladehinweis, danach die ehrliche
 * Erklaerung samt laufender Sekundenzahl, damit erkennbar bleibt, dass noch
 * etwas passiert.
 */

/** Ab hier gilt es nicht mehr als normales Laden, sondern als Kaltstart */
const KALTSTART_AB_SEKUNDEN = 4;

/** Erfahrungswert fuer die Fortschrittsanzeige; wird nie als Zusage formuliert */
const ERWARTETE_DAUER_SEKUNDEN = 150;

export default function LadeHinweis({ text = "Lade Daten…" }: { text?: string }): JSX.Element {
  const [sekunden, setSekunden] = useState(0);

  useEffect(() => {
    const uhr = window.setInterval(() => setSekunden((s) => s + 1), 1000);
    return () => window.clearInterval(uhr);
  }, []);

  const kaltstart = sekunden >= KALTSTART_AB_SEKUNDEN;
  // Deckelung knapp unter 100 %: Ein voller Balken ohne Ergebnis wirkt wie ein Fehler.
  const anteil = Math.min(95, Math.round((sekunden / ERWARTETE_DAUER_SEKUNDEN) * 100));

  if (!kaltstart) {
    return <p className="lade-hinweis-kurz">{text}</p>;
  }

  return (
    <div className="card lade-hinweis" role="status" aria-live="polite">
      <div className="lade-hinweis-kopf">
        <span className="lade-punkt" aria-hidden="true" />
        <strong>Der Server wird gestartet…</strong>
      </div>
      <p>
        Diese Demo läuft auf einem kostenlosen Server, der bei Inaktivität
        pausiert. Der erste Aufruf weckt ihn wieder auf – das dauert
        erfahrungsgemäss ein bis drei Minuten. Danach reagiert alles normal
        schnell.
      </p>
      <div className="progress-track">
        <div className="progress-fill" style={{ width: `${anteil}%` }} />
      </div>
      <p className="field-hint">Seit {sekunden} Sekunden am Starten</p>
    </div>
  );
}
