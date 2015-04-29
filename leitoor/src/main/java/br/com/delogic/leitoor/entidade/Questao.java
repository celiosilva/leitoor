package br.com.delogic.leitoor.entidade;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import br.com.delogic.jfunk.data.Identity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO", length = 50)
public abstract class Questao extends Identity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_questao")
    @SequenceGenerator(name = "seq_questao", sequenceName = "seq_questao", allocationSize = 1, initialValue = 1)
    private Integer      id;

    @Column(length = 1000)
    private String       descricao;

    private Integer      peso;

    @ElementCollection
    @CollectionTable(name = "QUESTAOHABILIDADE")
    @Column(name = "habilidade", length = 50)
    private List<String> habilidades;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

}
