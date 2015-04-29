package br.com.delogic.leitoor.entidade;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import br.com.delogic.jfunk.data.Identity;

@Entity
public class Turma extends Identity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_turma")
    @SequenceGenerator(name = "seq_turma", sequenceName = "seq_turma", allocationSize = 1, initialValue = 1)
    private Integer    id;

    @Column(length = 50)
    private String     nome;

    @ManyToMany
    @CollectionTable
    private Set<Aluno> alunos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

}
