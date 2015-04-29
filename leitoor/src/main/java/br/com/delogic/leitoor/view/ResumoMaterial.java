package br.com.delogic.leitoor.view;

public class ResumoMaterial {

    private Integer idMaterialConfigurado;
    private String  atividade;
    private Integer numeroPaginas;
    private Integer totalQuestoes;

    public Integer getIdMaterialConfigurado() {
        return idMaterialConfigurado;
    }

    public void setIdMaterialConfigurado(Integer idMaterialConfigurado) {
        this.idMaterialConfigurado = idMaterialConfigurado;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Integer getTotalQuestoes() {
        return totalQuestoes;
    }

    public void setTotalQuestoes(Integer totalQuestoes) {
        this.totalQuestoes = totalQuestoes;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

}
