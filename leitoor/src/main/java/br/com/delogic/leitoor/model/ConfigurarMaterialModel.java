package br.com.delogic.leitoor.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ConfigurarMaterialModel {

    @NotNull
    private Integer                     idMaterialConfiguracao;

    @Valid
    private List<ConfigurarPaginaModel> paginas;

    public List<ConfigurarPaginaModel> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<ConfigurarPaginaModel> paginas) {
        this.paginas = paginas;
    }

    public Integer getIdMaterialConfiguracao() {
        return idMaterialConfiguracao;
    }

    public void setIdMaterialConfiguracao(Integer idMaterialConfiguracao) {
        this.idMaterialConfiguracao = idMaterialConfiguracao;
    }

}
