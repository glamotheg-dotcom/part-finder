package com.partfinder.app;

import com.microsoft.playwright.*;
import org.springframework.stereotype.Service;

@Service
public class ScrapperService {

    public String buscarPrecioReal(String pieza) {
        // Playwright.create() inicia el motor del navegador
        try (Playwright playwright = Playwright.create()) {
            // Lanzamos Chromium en modo 'headless' (sin ventana visible)
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();

            // Vamos a Google Shopping para obtener un precio rápido de referencia
            page.navigate("https://www.google.com/search?q=precio+recambio+" + pieza);

            // Obtenemos el título de la página como prueba inicial
            String resultado = page.title();

            browser.close();
            return "Información encontrada en la web: " + resultado;
        } catch (Exception e) {
            return "No se pudo obtener el precio: " + e.getMessage();
        }
    }
}