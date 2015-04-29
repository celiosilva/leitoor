package br.com.delogic.leitoor.service;

import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.entidade.Usuario;
import br.com.delogic.leitoor.exception.LeitoorException;
import br.com.delogic.leitoor.exception.RegraNegocioException;
import br.com.delogic.leitoor.model.Cadastro;
import br.com.delogic.leitoor.util.Result;

import com.google.api.services.oauth2.model.Userinfoplus;
import com.restfb.types.User;

public interface AutenticacaoService {

    RegraNegocioException CONVITE_INVALIDO_EXCEPTION                    = new RegraNegocioException("Esse convite não é válido");

    RegraNegocioException SENHAS_DIFERENTES_EXCEPTION                   = new RegraNegocioException("As senhas digitadas são diferentes");

    RegraNegocioException USUARIO_INATIVO_EXCEPTION                     = new RegraNegocioException(
                                                                            "Seu usuário está inativo e não pode mais ser utilizado");

    RegraNegocioException USUARIO_INEXISTENTE_EXCEPTION                 = new RegraNegocioException();

    RegraNegocioException SENHA_INCORRETA_EXCEPTION                     = new RegraNegocioException("A senha digitada é incorreta");

    RegraNegocioException SENHA_VAZIA_EXCEPTION                         = new RegraNegocioException("A senha deve ser informada");

    RegraNegocioException RECUPERAR_SENHA_EMAIL_VAZIO_EXCEPTION         = new RegraNegocioException("O e-mail deve ser informado");

    RegraNegocioException RECUPERAR_SENHA_CONTA_CONVIDADA_EXCEPTION     = new RegraNegocioException(
                                                                            "Você já foi convidado, crie um cadastro ou conecte-se com o facebook!");

    RegraNegocioException RECUPERAR_SENHA_CONTA_TIPO_FACEBOOK_EXCEPTION = new RegraNegocioException(
                                                                            "A conta informada é vinculada ao facebook, por favor conecte-se com facebook!");

    RegraNegocioException RECUPERAR_SENHA_CONTA_INEXISTENTE_EXCEPTION   = new RegraNegocioException(
                                                                            "Não existe usuário com o e-mail informado");

    Professor getProfessorAutenticado();

    Aluno getAlunoAutenticado();

    Usuario getUsuarioAutenticado();

    Usuario criarUsuarioPorCadastro(Cadastro cadastro) throws LeitoorException;

    boolean isUsuarioAutenticado();

    boolean isProfessorAutenticado();

    boolean isAlunoAutenticado();

    Usuario obterUsuarioViaFacebook(User userFacebook, String convite) throws LeitoorException;

    void autenticarUsuario(Usuario usuario);

    Usuario autenticarUsuarioPorCadastro(String email, String senha) throws LeitoorException;

    String getNomeUsuarioAutenticado();

    String getEmailUsuarioAutenticado();

    Result<Void> convidarProfessores(String emails, Professor professor);

    String recuperarSenha(String email) throws LeitoorException;

    Usuario obterUsuarioViaGoogle(Userinfoplus userInfoPlus, String convite) throws LeitoorException;

}
