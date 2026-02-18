package com.tuapp.libros.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "¡Backend funcionando correctamente!";
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Backend conectado");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "Libros API");
        return response;
    }

    @GetMapping("/database")
    public Map<String, String> database() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "pending");
        response.put("message", "Conectar base de datos en el siguiente tutorial");
        return response;
    }
}
