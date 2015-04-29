package br.com.delogic.leitoor.model;

import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.Atividade;
import br.com.delogic.leitoor.entidade.Leitura;
import br.com.delogic.leitoor.entidade.Tarefa;

public class LeituraModel {

	private Aluno aluno;
	private Integer paginasAcessiveis;
	private Integer paginaAtual;
	private Integer quantidadePaginas;
	private Leitura leitura;
	private String arquivoPagina;
	private boolean tempoMinimoUltrapassado;
	private boolean completa;
	private Integer tempoDecorrido;
	private Integer tempoMinimo;
	private Atividade atividade;
	private Tarefa tarefa;
	private Integer quantidadeQuestoes;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Integer getPaginasAcessiveis() {
		return paginasAcessiveis;
	}

	public void setPaginasAcessiveis(Integer paginasAcessiveis) {
		this.paginasAcessiveis = paginasAcessiveis;
	}

	public Integer getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(Integer paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

	public Leitura getLeitura() {
		return leitura;
	}

	public void setLeitura(Leitura leitura) {
		this.leitura = leitura;
	}
	
	public void setArquivoPagina(String arquivoPagina) {
		this.arquivoPagina = arquivoPagina;
	}
	
	public String getArquivoPagina() {
		return arquivoPagina;
	}
	
	public void setTempoMinimoUltrapassado(boolean tempoMinimoUltrapassado) {
		this.tempoMinimoUltrapassado = tempoMinimoUltrapassado;
	}
	
	public boolean getTempoMinimoUltrapassado() {
		return tempoMinimoUltrapassado;
	}
	
	public boolean getCompleta() {
		return completa;
	}
	
	public void setCompleta(boolean completa) {
		this.completa = completa;
	}

	public Integer getTempoDecorrido() {
		return tempoDecorrido;
	}

	public void setTempoDecorrido(Integer tempoDecorrido) {
		this.tempoDecorrido = tempoDecorrido;
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
	
	public void setTempoMinimo(Integer tempoMinimo) {
		this.tempoMinimo = tempoMinimo;
	}
	
	public Integer getTempoMinimo() {
		return tempoMinimo;
	}
	
	public void setQuantidadeQuestoes(Integer quantidadeQuestoes) {
		this.quantidadeQuestoes = quantidadeQuestoes;
	}
	
	public Integer getQuantidadeQuestoes() {
		return quantidadeQuestoes;
	}
	
	public void setQuantidadePaginas(Integer quantidadePaginas) {
		this.quantidadePaginas = quantidadePaginas;
	}
	
	public Integer getQuantidadePaginas() {
		return quantidadePaginas;
	}
	
}
