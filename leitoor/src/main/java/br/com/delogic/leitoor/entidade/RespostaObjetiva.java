package br.com.delogic.leitoor.entidade;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("OBJETIVA")
public class RespostaObjetiva extends Resposta {

    @ManyToMany
    @CollectionTable
    private Set<QuestaoObjetivaAlternativa> alternativasSelecionadas;

    @Override
    public String getAlternativaSelecionada() {
        throw new UnsupportedOperationException("precisa ser implementada trocando String para List<String> como retorno");
    }

}
