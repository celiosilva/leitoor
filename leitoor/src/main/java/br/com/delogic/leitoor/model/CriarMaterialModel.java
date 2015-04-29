package br.com.delogic.leitoor.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CriarMaterialModel {

    @NotEmpty(message = "O arquivo é obrigatório e não foi carregado")
    @Pattern(regexp = "(.+\\.pdf)?", message = "Apenas arquivos do tipo PDF são aceitos")
    private String  nomeArquivo;

    @NotEmpty
    @Size(max = 50)
    private String  titulo;

    @NotNull
    @Max(99)
    private Integer minutos;

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }

}
