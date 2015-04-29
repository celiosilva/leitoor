package br.com.delogic.leitoor.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ALUNO")
public class Aluno extends Usuario {

}
