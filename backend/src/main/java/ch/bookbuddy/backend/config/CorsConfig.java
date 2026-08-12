package ch.bookbuddy.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Konfiguriert die globalen CORS-Einstellungen für die Anwendung.
 * <p>
 * Diese Konfiguration erlaubt Cross-Origin-Anfragen von spezifischen Ursprüngen,
 * um die Kommunikation zwischen Frontend (z. B. React/Vite) und Backend zu ermöglichen.
 */
@Configuration
public class CorsConfig {

    /**
     * Erstellt eine WebMvcConfigurer-Bean, um benutzerdefinierte CORS-Regeln zu definieren.
     *
     * @return ein WebMvcConfigurer mit CORS-Konfiguration
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * Konfiguriert CORS-Mappings für alle Endpunkte.
             *
             * @param registry das CorsRegistry-Objekt zum Registrieren von CORS-Regeln
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "https://bookbuddy-frontend.onrender.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
