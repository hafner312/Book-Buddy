package ch.bookbuddy.backend.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Liest die anonyme Besucher-Kennung aus dem Header "X-Owner-Id", den das
 * Frontend bei jedem Request mitschickt (siehe Front-End/src/ownerId.ts).
 * <p>
 * Ein Cookie waere hier nicht robust: Frontend und Backend laufen auf
 * unterschiedlichen Origins (bookbuddy-frontend-*.onrender.com vs.
 * book-buddy-*.onrender.com), was fuer Cross-Site-Cookies zusaetzliche
 * SameSite=None-Regeln und teils browserabhaengige Einschraenkungen
 * (z. B. Safari ITP) mit sich bringt. Ein selbst erzeugter Header
 * umgeht das vollstaendig.
 * <p>
 * Fehlt der Header (z. B. direkter Swagger-/curl-Aufruf ohne Frontend),
 * wird pro Request eine neue, nicht persistierte Kennung erzeugt - ein
 * solcher Aufrufer sieht dann bei jedem Request eine neue, leere "Bibliothek".
 */
@Component
public class OwnerIdFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "X-Owner-Id";
    public static final String REQUEST_ATTRIBUTE = "ownerId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String ownerId = request.getHeader(HEADER_NAME);
        if (ownerId == null || ownerId.isBlank()) {
            ownerId = UUID.randomUUID().toString();
        }

        request.setAttribute(REQUEST_ATTRIBUTE, ownerId);
        chain.doFilter(request, response);
    }
}
