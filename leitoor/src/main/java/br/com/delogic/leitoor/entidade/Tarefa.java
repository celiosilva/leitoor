package br.com.delogic.leitoor.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.delogic.leitoor.entidade.mapped.DominioProfessor;

@Entity
public class Tarefa extends DominioProfessor<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tarefa")
    @SequenceGenerator(name = "seq_tarefa", sequenceName = "seq_tarefa", allocationSize = 1, initialValue = 1)
    private Integer             id;

    @Column(length = 50)
    private String              descricaoAtividade;

    private Integer             idAtividade;

    @ManyToOne
    private MaterialConfigurado materialConfigurado;

    @ManyToOne
    private Turma               turma;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
    }

    public Integer getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Integer idAtividade) {
        this.idAtividade = idAtividade;
    }

    public MaterialConfigurado getMaterialConfigurado() {
        return materialConfigurado;
    }

    public void setMaterialConfigurado(MaterialConfigurado materialConfigurado) {
        this.materialConfigurado = materialConfigurado;
    }

}
