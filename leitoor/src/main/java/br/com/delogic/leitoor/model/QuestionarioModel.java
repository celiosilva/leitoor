package br.com.delogic.leitoor.model;

import java.util.List;

import br.com.delogic.leitoor.entidade.Questao;
import br.com.delogic.leitoor.entidade.Resposta;

public class QuestionarioModel {

	List<Questao> questoes;
	List<Resposta> respostas;
	
	public List<Questao> getQuestoes() {
		return questoes;
	}
	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
	}
	public List<Resposta> getRespostas() {
		return respostas;
	}
	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}
	
}
