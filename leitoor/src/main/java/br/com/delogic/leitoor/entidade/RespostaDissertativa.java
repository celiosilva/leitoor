package br.com.delogic.leitoor.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DISSERTATIVA")
public class RespostaDissertativa extends Resposta {

    @Column(length = 5000)
    private String reposta;

    public String getReposta() {
        return reposta;
    }

    public void setReposta(String reposta) {
        this.reposta = reposta;
    }

    @Override
    public String getAlternativaSelecionada() {
        return reposta;
    }

}
