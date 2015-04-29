package br.com.delogic.leitoor.entidade.enums;

public enum Avaliacao {

    INCORRETO("Incorreto"), CORRETO("Correto");

    private final String descricao;

    private Avaliacao(String desc) {
        this.descricao = desc;
    }

    public String getDescricao() {
        return descricao;
    }

}
