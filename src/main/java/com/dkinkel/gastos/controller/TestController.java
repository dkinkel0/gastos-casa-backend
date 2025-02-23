// Define el paquete donde se encuentra esta clase
package com.dkinkel.gastos.controller;

// Importaciones necesarias de Spring
// Estas son como incluir herramientas que vamos a usar
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController le dice a Spring que esta clase manejará peticiones web
// y que las respuestas se enviarán directamente al cliente
@RestController
public class TestController {

    // @Autowired le dice a Spring que inyecte (proporcione) automáticamente
    // un JdbcTemplate (herramienta para hacer consultas a la base de datos)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // @GetMapping("/test-db") indica que este método responderá a
    // peticiones HTTP GET en la ruta /test-db
    @GetMapping("/test-db")
    public String testDb() {
        try {
            // Intenta ejecutar una consulta simple para verificar la conexión
            // queryForObject ejecuta la consulta y convierte el resultado a String
            String result = jdbcTemplate.queryForObject("SELECT version()", String.class);
            
            // Si la consulta fue exitosa, retorna un mensaje con la versión
            return "Conexión exitosa a la BD. Versión: " + result;
                  //Conexión exitosa a la BD. Versión: PostgreSQL 14.15 (Ubuntu 14.15-0ubuntu0.22.04.1) on x86_64-pc-linux-gnu, compiled by gcc (Ubuntu 11.4.0-1ubuntu1~22.04) 11.4.0, 64-bit
            
        } catch (Exception e) {
            // Si algo sale mal, captura el error y retorna un mensaje de error
            return "Error de conexión: " + e.getMessage();
        }
    }
}