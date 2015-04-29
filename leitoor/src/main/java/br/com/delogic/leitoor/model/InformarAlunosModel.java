package br.com.delogic.leitoor.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class InformarAlunosModel {

    @NotEmpty
    @Size(max = 50)
    private String  nomeAtividade;

    @NotEmpty
    @Size(max = 50)
    private String  turma;

    @NotEmpty
    @Size(max = 999)
    @Pattern(regexp =
        "^([a-zA-Z0-9\\._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]{2,}\\s*)+$*", message =
        "E-mails devem ser algo como nome@email(.com, .gov, .br, etc). Sem virgulas ou caracteres especiais.")
    private String  emails;

    @NotNull
    private Integer idMaterialConfigurado;

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public Integer getIdMaterialConfigurado() {
        return idMaterialConfigurado;
    }

    public void setIdMaterialConfigurado(Integer idMaterialConfigurado) {
        this.idMaterialConfigurado = idMaterialConfigurado;
    }

}