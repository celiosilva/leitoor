package br.com.delogic.leitoor.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DISSERTATIVA")
public class QuestaoDissertativa extends Questao {

}
