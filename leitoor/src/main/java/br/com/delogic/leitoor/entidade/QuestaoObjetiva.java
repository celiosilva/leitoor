package br.com.delogic.leitoor.entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("OBJETIVA")
public class QuestaoObjetiva extends Questao {

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL)
    private List<QuestaoObjetivaAlternativa> alternativas;

    public List<QuestaoObjetivaAlternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<QuestaoObjetivaAlternativa> alternativas) {
        this.alternativas = alternativas;
    }

}
