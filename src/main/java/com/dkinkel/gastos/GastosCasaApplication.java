// Define el paquete donde se encuentra esta clase
package com.dkinkel.gastos;

// Importa las clases necesarias de Spring Boot
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;



// @SpringBootApplication es una anotación que combina:
// - @Configuration: Indica que esta clase puede definir beans de Spring
// - @EnableAutoConfiguration: Habilita la configuración automática de Spring Boot
// - @ComponentScan: Le dice a Spring que busque otros componentes en este paquete
@SpringBootApplication
// @EntityScan("com.gastoscasa.model")
public class GastosCasaApplication {
    
    // El método main es el punto de entrada de la aplicación
    // Es donde comienza la ejecución del programa
    public static void main(String[] args) {
        // SpringApplication.run inicia la aplicación Spring Boot
        // - GastosCasaApplication.class: Le dice a Spring qué clase debe usar como configuración principal
        // - args: Argumentos pasados al programa desde la línea de comandos
        SpringApplication.run(GastosCasaApplication.class, args);
                                                           //java -jar tu-aplicacion.jar --server.port=8081
        													// ese argumento cambia el puerto en vez de 8080 a 8081
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3535")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }

}








