package br.com.delogic.leitoor.util.csa;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

public class UrlMapping implements ServletContextAware {

    private String url;
    private String path;
    private String contexto;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return path;
    }

    public String getPath() {
        return path;
    }

    public String getRedirectPath() {
        return "redirect:" + path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.contexto = servletContext.getContextPath();
        this.url = contexto + path;
    }

}
