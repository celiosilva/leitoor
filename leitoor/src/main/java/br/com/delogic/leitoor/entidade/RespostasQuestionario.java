package br.com.delogic.leitoor.entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.delogic.jfunk.data.Identity;

@Entity
public class RespostasQuestionario extends Identity<Integer> {

    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "seq_respostas_questionario", sequenceName = "seq_respostas_questionario")
    @GeneratedValue(generator = "seq_respostas_questionario", strategy = GenerationType.SEQUENCE)
    private Integer        id;

    @ManyToOne
    private Questionario   questionario;

    @ManyToMany(cascade = CascadeType.ALL)
    @CollectionTable(name = "RESPOSTASQUESTIONARIORESPOSTAS")
    private List<Resposta> respostas;

    @ManyToOne
    private Usuario        respondente;

    @ManyToOne
    private TarefaEnviada  tarefaEnviada;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getRespondente() {
        return respondente;
    }

    public void setRespondente(Usuario respondente) {
        this.respondente = respondente;
    }

    public TarefaEnviada getTarefaEnviada() {
        return tarefaEnviada;
    }

    public void setTarefaEnviada(TarefaEnviada tarefaEnviada) {
        this.tarefaEnviada = tarefaEnviada;
    }

    public Questionario getQuestionario() {
        return questionario;
    }

    public void setQuestionario(Questionario questionario) {
        this.questionario = questionario;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

}
