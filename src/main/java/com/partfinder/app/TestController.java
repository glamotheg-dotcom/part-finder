package com.partfinder.app;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final OpenAiChatModel chatModel;
    private final ScraperService scraperService;

    public TestController(OpenAiChatModel chatModel, ScraperService scraperService) {
        this.chatModel = chatModel;
        this.scraperService = scraperService;
    }

    @GetMapping("/test-ia")
    public String probar(@RequestParam(defaultValue = "bateria") String pieza) {
        // Pedimos a la IA una recomendación muy corta
        String recomendacion = chatModel.call("Dime solo la mejor marca y modelo para " + pieza + ". Responde en una frase.");
        
        // Buscamos el precio real en Autodoc
        String precioAutodoc = scraperService.buscarPrecioAutodoc(pieza);
        
        // Devolvemos el resultado combinado
        return "🛠️ RECOMENDACIÓN IA: " + recomendacion + 
               "\n\n💰 PRECIO EN AUTODOC: " + precioAutodoc;
    }
}