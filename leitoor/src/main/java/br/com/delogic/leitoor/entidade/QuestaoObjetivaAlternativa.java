package br.com.delogic.leitoor.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.delogic.jfunk.data.Identity;

@Entity
public class QuestaoObjetivaAlternativa extends Identity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_questao_obj_alternativa")
    @SequenceGenerator(name = "seq_questao_obj_alternativa", sequenceName = "seq_questao_obj_alternativa", allocationSize = 1, initialValue = 1)
    private Integer         id;

    @Column(nullable = false)
    private Boolean         correta;

    @Column(length = 1000, nullable = false)
    private String          descricao;

    @ManyToOne
    private QuestaoObjetiva questao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getCorreta() {
        return correta;
    }

    public void setCorreta(Boolean correta) {
        this.correta = correta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public QuestaoObjetiva getQuestao() {
        return questao;
    }

    public void setQuestao(QuestaoObjetiva questao) {
        this.questao = questao;
    }

}
