package br.com.delogic.leitoor.configucao;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import br.com.delogic.jfunk.Has;

public class Aplicacao implements ServletContextAware {

    private ServletContext servletContext;

    private String         context;
    private String         resources;

    @PostConstruct
    public void init() {
        context = servletContext.getContextPath();
        if (!Has.content(resources)) {
            resources = context;
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String contexto) {
        this.context = contexto;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

}
