package com.partfinder.app;

import com.microsoft.playwright.*;
import org.springframework.stereotype.Service;

@Service
public class ScraperService {

    public String buscarPrecioAutodoc(String pieza) {
        try (Playwright playwright = Playwright.create()) {
            // Usamos un navegador Chromium
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            
            // Le damos una "identidad" de navegador normal para evitar bloqueos
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"));
            
            Page page = context.newPage();

            // 1. Vamos a la búsqueda de Autodoc
            // Sustituimos espacios por %20 para la URL
            String url = "https://www.autodoc.es/search?keyword=" + pieza.replace(" ", "%20");
            page.navigate(url);

            // 2. Esperamos a que aparezca la lista de productos
            // Usamos un selector común en Autodoc para el precio del primer artículo
            page.waitForSelector(".product-card__price", new Page.WaitForSelectorOptions().setTimeout(10000));

            // 3. Extraemos el texto del primer precio que encuentre
            String precio = page.locator(".product-card__price").first().innerText();

            browser.close();
            return precio.trim();
        } catch (Exception e) {
            // Si algo falla (bloqueo, cambio de diseño...), devolvemos un mensaje útil
            return "No disponible actualmente";
        }
    }
}