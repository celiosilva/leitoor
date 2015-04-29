package br.com.delogic.leitoor.view;

public class ResumoAcompanhamento implements Comparable<ResumoAcompanhamento> {

    private Integer idTarefaEnviada;
    private String  nome;
    private Integer percentualLeitura;
    private Integer acertos;
    private Integer erros;
    private Integer questoes;
    private Integer percentualQuestionario;

    public Integer getIdTarefaEnviada() {
        return idTarefaEnviada;
    }

    public void setIdTarefaEnviada(Integer idTarefaEnviada) {
        this.idTarefaEnviada = idTarefaEnviada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPercentualLeitura() {
        return percentualLeitura;
    }

    public void setPercentualLeitura(Integer percentualLeitura) {
        this.percentualLeitura = percentualLeitura;
    }

    public Integer getAcertos() {
        return acertos;
    }

    public void setAcertos(Integer acertos) {
        this.acertos = acertos;
    }

    public Integer getErros() {
        return erros;
    }

    public void setErros(Integer erros) {
        this.erros = erros;
    }

    public Integer getQuestoes() {
        return questoes;
    }

    public void setQuestoes(Integer questoes) {
        this.questoes = questoes;
    }

    public Integer getPercentualQuestionario() {
        return percentualQuestionario;
    }

    public void setPercentualQuestionario(Integer percentualQuestionario) {
        this.percentualQuestionario = percentualQuestionario;
    }

    public Integer getPercentualTotal() {

        int percentualTotal = getPercentualLeitura();
        if (getPercentualQuestionario() != null) {
            percentualTotal = (percentualTotal + getPercentualQuestionario()) / 2;
        }
        return percentualTotal;
    }

    @Override
    public int compareTo(ResumoAcompanhamento resumoAcompanhamento) {

        if (this.getPercentualTotal() > resumoAcompanhamento.getPercentualTotal()) {
            return 1;
        }
        else if (this.getPercentualTotal() < resumoAcompanhamento.getPercentualTotal()) {
            return -1;
        }

        return this.getNome().compareToIgnoreCase(resumoAcompanhamento.getNome());
    }
}