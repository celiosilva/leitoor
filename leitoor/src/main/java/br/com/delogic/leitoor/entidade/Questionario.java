package br.com.delogic.leitoor.entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

import br.com.delogic.jfunk.data.Identity;

@Entity
public class Questionario extends Identity<Integer> {

    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "seq_questionario", sequenceName = "seq_questionario")
    @GeneratedValue(generator = "seq_questionario", strategy = GenerationType.SEQUENCE)
    private Integer       id;

    @Column(length = 50)
    private String        nome;

    @Column(length = 1000)
    private String        descricao;

    @ManyToOne
    private Usuario       criador;

    @ManyToMany(cascade = CascadeType.ALL)
    @CollectionTable(name = "QUESTIONARIOQUESTOES")
    @OrderBy("id")
    private List<Questao> questoes;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getCriador() {
        return criador;
    }

    public void setCriador(Usuario proprietario) {
        this.criador = proprietario;
    }

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}