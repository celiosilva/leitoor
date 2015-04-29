package br.com.delogic.leitoor.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.delogic.leitoor.validador.EmailValido;

/**
 * Classe responsável pelo form de cadastro da home
 *
 * @author Rodrigo
 *
 */
public class Cadastro {

    @NotEmpty
    @EmailValido
    private String email;

    @Pattern(regexp = "\\S+", message = "Não deve conter espaços em branco")
    @Size(min = 8, max = 50)
    private String senha;

    @NotEmpty
    private String repetirSenha;

    @NotEmpty
    private String nomeCompleto;

    private String convite;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getRepetirSenha() {
        return repetirSenha;
    }

    public void setRepetirSenha(String repetirSenha) {
        this.repetirSenha = repetirSenha;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getConvite() {
        return convite;
    }

    public void setConvite(String convite) {
        this.convite = convite;
    }

}
