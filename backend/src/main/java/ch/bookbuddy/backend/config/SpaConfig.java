package ch.bookbuddy.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * Liefert das gebaute React-Frontend aus, das im Jar unter /static liegt.
 *
 * Das Frontend nutzt clientseitiges Routing (react-router). Ruft jemand
 * /list direkt auf oder laedt die Seite dort neu, fragt der Browser diesen
 * Pfad beim Server an - dort gibt es aber keine Datei dieses Namens. Ohne
 * Rueckfall antwortete der Server mit 404, statt die Anwendung zu laden.
 *
 * Deshalb: Existiert die angeforderte Datei, wird sie ausgeliefert. Sonst
 * bekommt der Browser index.html, und react-router entscheidet im Browser,
 * was angezeigt wird.
 */
@Configuration
public class SpaConfig implements WebMvcConfigurer {

    private static final String STATIC_ORDNER = "classpath:/static/";
    private static final String EINSTIEGSSEITE = "/static/index.html";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(STATIC_ORDNER)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String pfad, Resource ort) throws IOException {
                        Resource angefragt = ort.createRelative(pfad);
                        if (angefragt.exists() && angefragt.isReadable()) {
                            return angefragt;
                        }
                        if (istKeineSeitenanfrage(pfad)) {
                            return null; // fuehrt zu einem ehrlichen 404
                        }
                        return new ClassPathResource(EINSTIEGSSEITE);
                    }
                });
    }

    /**
     * Nur echte Seitenaufrufe duerfen auf index.html zurueckfallen.
     *
     * Fuer die API muss ein unbekannter Pfad weiterhin 404 liefern, sonst
     * bekaeme ein fehlerhafter Aufruf HTML statt einer Fehlermeldung. Und
     * fehlt eine Datei mit Endung (etwa ein Bild oder ein Skript), soll das
     * ebenfalls als 404 auffallen und nicht stillschweigend HTML liefern.
     */
    private static boolean istKeineSeitenanfrage(String pfad) {
        if (pfad.startsWith("api/")) {
            return true;
        }
        int letzterSchraegstrich = pfad.lastIndexOf('/');
        String letzterTeil = pfad.substring(letzterSchraegstrich + 1);
        return letzterTeil.contains(".");
    }
}
