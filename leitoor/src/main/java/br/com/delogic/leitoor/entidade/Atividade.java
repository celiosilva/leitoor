package br.com.delogic.leitoor.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import br.com.delogic.leitoor.entidade.mapped.DominioProfessor;

@Entity
public class Atividade extends DominioProfessor<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_atividade")
    @SequenceGenerator(name = "seq_atividade", sequenceName = "seq_atividade", allocationSize = 1, initialValue = 1)
    private Integer             id;

    @Column(length = 50)
    private String              descricao;

    @OneToOne
    private MaterialConfigurado materialConfigurado;

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

    public MaterialConfigurado getMaterialConfigurado() {
        return materialConfigurado;
    }

    public void setMaterialConfigurado(MaterialConfigurado materialConfigurado) {
        this.materialConfigurado = materialConfigurado;
    }

}
