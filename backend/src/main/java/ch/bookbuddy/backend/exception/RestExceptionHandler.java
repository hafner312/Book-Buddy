package ch.bookbuddy.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

/**
 * Globale Exception-Handler-Klasse für REST-Endpunkte.
 * <p>
 * Behandelt spezifische und generische Ausnahmen zentral und gibt passende HTTP-Antworten zurück.
 * Diese Klasse wird durch {@link RestControllerAdvice} automatisch bei REST-Fehlern aktiv.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Behandelt {@link IllegalArgumentException} und gibt HTTP 400 (Bad Request) zurück.
     *
     * @param ex die geworfene Ausnahme
     * @return ResponseEntity mit Status 400 und Fehlermeldung im Body
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Behandelt {@link NoSuchElementException} und gibt HTTP 404 (Not Found) zurück.
     *
     * @param ex die geworfene Ausnahme
     * @return ResponseEntity mit Status 404 und Fehlermeldung im Body
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Behandelt alle sonstigen (nicht explizit behandelten) Ausnahmen.
     * Gibt HTTP 500 (Internal Server Error) zurück.
     *
     * @param ex die geworfene Ausnahme
     * @return ResponseEntity mit generischer Fehlermeldung und Status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ein unerwarteter Fehler ist aufgetreten.");
    }
}
