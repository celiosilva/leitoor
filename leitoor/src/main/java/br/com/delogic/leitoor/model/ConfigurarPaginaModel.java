package br.com.delogic.leitoor.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ConfigurarPaginaModel {

    @NotNull
    private Integer      tempo;
    private List<String> questoes;
    private String       url;

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public List<String> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<String> questoes) {
        this.questoes = questoes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
