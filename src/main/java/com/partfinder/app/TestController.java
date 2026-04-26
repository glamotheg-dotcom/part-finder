package com.partfinder.app; // Asegúrate de que este sea tu paquete real

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final OpenAiChatModel chatModel;
    private final ScrapperService scraperService; // Inyectamos el nuevo servicio

    public TestController(OpenAiChatModel chatModel, ScrapperService scraperService) {
        this.chatModel = chatModel;
        this.scraperService = scraperService;
    }

    @GetMapping("/test-ia")
    public String probar(@RequestParam(defaultValue = "bateria") String pieza) {
        // 1. La IA recomienda la marca usando tu configuración de Groq
        String recomendacion = chatModel.call("Dime solo la mejor marca para " + pieza);

        // 2. El scraper busca información real en la web
        String infoWeb = scraperService.buscarPrecioReal(pieza);

        return "🤖 IA dice: " + recomendacion + " | 🌐 Web dice: " + infoWeb;
    }
}