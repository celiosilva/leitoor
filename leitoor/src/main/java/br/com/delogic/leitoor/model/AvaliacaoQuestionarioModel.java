package br.com.delogic.leitoor.model;

import java.util.List;

import br.com.delogic.leitoor.entidade.enums.Avaliacao;

public class AvaliacaoQuestionarioModel {

    public static class RespostaModel {

        private Integer   id;
        private String    questao;
        private String    resposta;
        private Avaliacao avaliacao;

        public String getQuestao() {
            return questao;
        }

        public void setQuestao(String questao) {
            this.questao = questao;
        }

        public String getResposta() {
            return resposta;
        }

        public void setResposta(String resposta) {
            this.resposta = resposta;
        }

        public Avaliacao getAvaliacao() {
            return avaliacao;
        }

        public void setAvaliacao(Avaliacao avaliacao) {
            this.avaliacao = avaliacao;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }

    private Integer             idTarefaEnviada;
    private Integer             idTarefa;
    private String              atividade;
    private String              turma;
    private String              material;
    private String              aluno;
    private List<RespostaModel> respostas;

    public Integer getIdTarefaEnviada() {
        return idTarefaEnviada;
    }

    public void setIdTarefaEnviada(Integer idTarefaEnviada) {
        this.idTarefaEnviada = idTarefaEnviada;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public List<RespostaModel> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaModel> respostas) {
        this.respostas = respostas;
    }

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

}
