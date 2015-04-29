package br.com.delogic.leitoor.view;

import java.math.BigDecimal;

public class ResumoTarefa {

    private Integer    idTarefa;
    private String     tarefa;
    private String     turma;
    private String     material;
    private BigDecimal percentualCompleto;

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public BigDecimal getPercentualCompleto() {
        return percentualCompleto;
    }

    public void setPercentualCompleto(BigDecimal percentualCompleto) {
        this.percentualCompleto = percentualCompleto;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

}
