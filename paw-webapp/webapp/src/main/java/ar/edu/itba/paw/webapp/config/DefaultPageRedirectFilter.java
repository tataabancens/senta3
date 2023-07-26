package ar.edu.itba.paw.webapp.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class DefaultPageRedirectFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPageRedirectFilter.class);

    private static final String[] excludedPaths = { "/static/", "/favicon" };
    private static final String PREFIX = "/paw-2022a-05";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Convertir ServletRequest a HttpServletRequest
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        // Excluye las rutas de los recursos estáticos de la redirección

        boolean isExcluded = isExcluded(requestURI);
        // Excluye las rutas de la API (por ejemplo, aquellas que comienzan con /api) de la redirección
        if (isExcluded) {
            chain.doFilter(request, response);
        } else {
            // Redirige todas las demás solicitudes a la página principal
            request.getRequestDispatcher("/").forward(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    public void logRequestInfo(HttpServletRequest httpRequest) {
        // Imprimir información sobre la solicitud HTTP
        logger.info("Metodo HTTP: {}", httpRequest.getMethod());
        logger.info("URL solicitada: {}", httpRequest.getRequestURL());
        logger.info("URI solicitada: {}", httpRequest.getRequestURI());
    }

    public boolean isExcluded(String requestURI) {
        boolean exclude = false;
        for (String excludedPath : excludedPaths) {
            if (requestURI.startsWith(PREFIX + excludedPath)) {
                exclude = true;
                break;
            }
        }
        return exclude;
    }



    // Implementa los otros métodos de la interfaz Filter (init y destroy)
}
