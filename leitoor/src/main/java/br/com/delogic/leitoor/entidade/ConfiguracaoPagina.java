package br.com.delogic.leitoor.entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
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
public class ConfiguracaoPagina extends Identity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_config_pagina")
    @SequenceGenerator(name = "seq_config_pagina", sequenceName = "seq_config_pagina", allocationSize = 1, initialValue = 1)
    private Integer             id;

    @ManyToOne
    private MaterialConfigurado materialConfigurado;

    private Integer             pagina;

    private Integer             minMinutosPorPagina;

    @ManyToMany(cascade = CascadeType.ALL)
    @CollectionTable(name = "CONFIGURACAOPAGINAQUESTAO")
    @OrderBy("id")
    private List<Questao>       questoes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MaterialConfigurado getMaterialConfigurado() {
        return materialConfigurado;
    }

    public void setMaterialConfigurado(MaterialConfigurado materialConfigurado) {
        this.materialConfigurado = materialConfigurado;
    }

    public Integer getPagina() {
        return pagina;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    public Integer getMinMinutosPorPagina() {
        return minMinutosPorPagina;
    }

    public void setMinMinutosPorPagina(Integer minMinutosPorPagina) {
        this.minMinutosPorPagina = minMinutosPorPagina;
    }

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }

}
