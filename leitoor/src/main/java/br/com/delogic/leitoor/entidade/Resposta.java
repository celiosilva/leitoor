package br.com.delogic.leitoor.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.delogic.jfunk.data.Identity;
import br.com.delogic.leitoor.entidade.enums.Avaliacao;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO", length = 50)
public abstract class Resposta extends Identity<Integer> {

    public abstract String getAlternativaSelecionada();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_resposta")
    @SequenceGenerator(name = "seq_resposta", sequenceName = "seq_resposta", allocationSize = 1, initialValue = 1)
    private Integer       id;

    @ManyToOne
    private TarefaEnviada tarefaEnviada;

    @ManyToOne
    private Questao       questao;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Avaliacao     avaliacao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public TarefaEnviada getTarefaEnviada() {
        return tarefaEnviada;
    }

    public void setTarefaEnviada(TarefaEnviada tarefaEnviada) {
        this.tarefaEnviada = tarefaEnviada;
    }

}
