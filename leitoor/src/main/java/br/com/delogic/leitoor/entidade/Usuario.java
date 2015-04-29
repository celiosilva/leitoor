package br.com.delogic.leitoor.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.delogic.jfunk.data.Identity;
import br.com.delogic.leitoor.entidade.enums.RedeSocial;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PERFIL", length = 50)
public abstract class Usuario extends Identity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
    private Integer    id;

    @Column(unique = true, length = 50)
    private String     idFacebook;

    @Column(unique = true, length = 50)
    private String     idGoogle;

    @Column(unique = true, length = 100)
    private String     email;

    @Column(length = 100)
    private String     nome;

    private Boolean    ativo;

    @Column(unique = true, length = 50)
    private String     convite;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private RedeSocial redeSocial;

    @Column(length = 50)
    private String     senha;

    @ManyToOne
    private Usuario    convidadoPor;

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getConvite() {
        return convite;
    }

    public void setConvite(String convite) {
        this.convite = convite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RedeSocial getRedeSocial() {
        return redeSocial;
    }

    public void setRedeSocial(RedeSocial redeSocial) {
        this.redeSocial = redeSocial;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getConvidadoPor() {
        return convidadoPor;
    }

    public void setConvidadoPor(Usuario convidadoPor) {
        this.convidadoPor = convidadoPor;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

}
