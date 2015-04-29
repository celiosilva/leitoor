package br.com.delogic.leitoor.model;

import java.util.Date;
import java.util.List;

public class DesempenhoAlunoModel {

	Integer pontos = 0;
	Integer erros = 0;
	Integer acertos = 0;
	Integer porcentagemConcluida = 0;
	Integer quantidadePaginas = 0;
	List<Date> leiturasConcluidas;
	public Integer getPorcentagemConcluida() {
		return porcentagemConcluida;
	}
	public void setPorcentagemConcluida(Integer porcentagemConcluida) {
		this.porcentagemConcluida = porcentagemConcluida;
	}
	public Integer getQuantidadePaginas() {
		return quantidadePaginas;
	}
	public void setQuantidadePaginas(Integer quantidadePaginas) {
		this.quantidadePaginas = quantidadePaginas;
	}
	public List<Date> getLeiturasConcluidas() {
		return leiturasConcluidas;
	}
	public void setLeiturasConcluidas(List<Date> leiturasConcluidas) {
		this.leiturasConcluidas = leiturasConcluidas;
	}
	public Integer getPontos() {
		return pontos;
	}
	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}
	public Integer getErros() {
		return erros;
	}
	public void setErros(Integer erros) {
		this.erros = erros;
	}
	public Integer getAcertos() {
		return acertos;
	}
	public void setAcertos(Integer acertos) {
		this.acertos = acertos;
	}
	
}
