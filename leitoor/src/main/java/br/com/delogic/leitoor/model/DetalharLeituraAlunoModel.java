package br.com.delogic.leitoor.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.Atividade;
import br.com.delogic.leitoor.entidade.Leitura;
import br.com.delogic.leitoor.entidade.Tarefa;

public class DetalharLeituraAlunoModel {

	private Aluno aluno;
	private List<Leitura> leituras;
	private Atividade atividade;
	private Tarefa tarefa;
	private Integer quantidadePaginas;
	private Integer melhorDesempenho;
	private Integer desempenhoMedio;
	private Integer desempenhoAluno;
	private Map<Date, Map<Long, Double>> dadosGrafico;
	private Map<Date, Map<Long, Long>> dadosGraficoPorDia;
	private DesempenhoAlunoModel melhorPontuacao;
	private DesempenhoAlunoModel mediaPontuacao;
	private DesempenhoAlunoModel alunoPontuacao;
	
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public List<Leitura> getLeituras() {
		return leituras;
	}
	public void setLeituras(List<Leitura> leituras) {
		this.leituras = leituras;
	}
	public Atividade getAtividade() {
		return atividade;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	public Tarefa getTarefa() {
		return tarefa;
	}
	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	public Integer getMelhorDesempenho() {
		return melhorDesempenho;
	}
	public void setMelhorDesempenho(Integer melhorDesempenho) {
		this.melhorDesempenho = melhorDesempenho;
	}
	public Integer getDesempenhoMedio() {
		return desempenhoMedio;
	}
	public void setDesempenhoMedio(Integer desempenhoMedio) {
		this.desempenhoMedio = desempenhoMedio;
	}
	public Integer getDesempenhoAluno() {
		return desempenhoAluno;
	}
	public void setDesempenhoAluno(Integer desempenhoAluno) {
		this.desempenhoAluno = desempenhoAluno;
	}
	public void setQuantidadePaginas(Integer quantidadePaginas) {
		this.quantidadePaginas = quantidadePaginas;
	}
	public Integer getQuantidadePaginas() {
		return quantidadePaginas;
	}
	public void setDadosGrafico(Map<Date, Map<Long, Double>> dadosGrafico) {
		this.dadosGrafico = dadosGrafico;
	}
	public Map<Date, Map<Long, Double>> getDadosGrafico() {
		return dadosGrafico;
	}
	public void setDadosGraficoPorDia(
			Map<Date, Map<Long, Long>> dadosGraficoPorDia) {
		this.dadosGraficoPorDia = dadosGraficoPorDia;
	}
	public Map<Date, Map<Long, Long>> getDadosGraficoPorDia() {
		return dadosGraficoPorDia;
	}
	public DesempenhoAlunoModel getMelhorPontuacao() {
		return melhorPontuacao;
	}
	public void setMelhorPontuacao(DesempenhoAlunoModel melhorPontuacao) {
		this.melhorPontuacao = melhorPontuacao;
	}
	public DesempenhoAlunoModel getMediaPontuacao() {
		return mediaPontuacao;
	}
	public void setMediaPontuacao(DesempenhoAlunoModel mediaPontuacao) {
		this.mediaPontuacao = mediaPontuacao;
	}
	public DesempenhoAlunoModel getAlunoPontuacao() {
		return alunoPontuacao;
	}
	public void setAlunoPontuacao(DesempenhoAlunoModel alunoPontuacao) {
		this.alunoPontuacao = alunoPontuacao;
	}
	
}
