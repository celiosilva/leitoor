package br.com.delogic.leitoor.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends Aluno {

}
