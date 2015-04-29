package br.com.delogic.leitoor.service.impl;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.transaction.annotation.Transactional;

import br.com.delogic.csa.manager.EmailManager;
import br.com.delogic.csa.manager.email.EmailAddress;
import br.com.delogic.csa.manager.email.EmailContent;
import br.com.delogic.jfunk.Each;
import br.com.delogic.jfunk.ForEach;
import br.com.delogic.jfunk.Has;
import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.entidade.Turma;
import br.com.delogic.leitoor.entidade.Usuario;
import br.com.delogic.leitoor.entidade.enums.RedeSocial;
import br.com.delogic.leitoor.exception.LeitoorException;
import br.com.delogic.leitoor.model.Cadastro;
import br.com.delogic.leitoor.repository.AlunoRepository;
import br.com.delogic.leitoor.repository.TarefaEnviadaRepository;
import br.com.delogic.leitoor.repository.TurmaRepository;
import br.com.delogic.leitoor.repository.UsuarioRepository;
import br.com.delogic.leitoor.service.AutenticacaoService;
import br.com.delogic.leitoor.util.Result;

import com.google.api.services.oauth2.model.Userinfoplus;
import com.restfb.types.User;

public class AutenticacaoServiceImpl implements AutenticacaoService {

    @Inject
    private HttpSession             session;

    @Inject
    private HttpServletRequest      request;

    @Inject
    @Named("authenticationManager")
    private AuthenticationManager   authenticationManager;

    @Inject
    private AlunoRepository         alunoRepository;

    @Inject
    private UsuarioRepository       usuarioRepository;

    @Inject
    private TarefaEnviadaRepository tarefaEnviadaRepository;

    @Inject
    private EmailManager            emailManager;

    @Inject
    private TurmaRepository         turmaRepository;

    private String                  USUARIO_AUTENTICADO = "br.com.delogic.leitoor.service.impl.AutenticacaoServiceImpl:UsuarioAutenticado";

    @Override
    public Professor getProfessorAutenticado() {
        if (!isProfessorAutenticado()) {
            throw new IllegalStateException("Não há professor autenticado no momento");
        }
        return (Professor) session.getAttribute(USUARIO_AUTENTICADO);
    }

    @Override
    public Aluno getAlunoAutenticado() {
        if (!isAlunoAutenticado()) {
            throw new IllegalStateException("Não há aluno autenticado no momento");
        }
        return (Aluno) session.getAttribute(USUARIO_AUTENTICADO);
    }

    @Override
    public Usuario getUsuarioAutenticado() {
        if (!isUsuarioAutenticado()) {
            throw new IllegalStateException("Não há usuário autenticado no momento");
        }
        return (Usuario) session.getAttribute(USUARIO_AUTENTICADO);
    }

    @Override
    public boolean isUsuarioAutenticado() {
        return session.getAttribute(USUARIO_AUTENTICADO) instanceof Usuario;
    }

    @Override
    public boolean isProfessorAutenticado() {
        return session.getAttribute(USUARIO_AUTENTICADO) instanceof Professor;
    }

    @Override
    public boolean isAlunoAutenticado() {
        return session.getAttribute(USUARIO_AUTENTICADO) instanceof Aluno;
    }

    @Override
    @Transactional
    public Usuario obterUsuarioViaFacebook(User userFacebook, String convite) throws LeitoorException {

        Usuario usuario = usuarioRepository.findByIdFacebook(userFacebook.getId());
        if (usuario == null) {
            usuario = usuarioRepository.findByEmail(userFacebook.getEmail());
        }

        if (usuario == null) {
            usuario = criarNovoUsuarioFacebook(userFacebook, convite);

        } else if (Has.content(convite)) {
            usuario = alterarUsuarioConvidado(usuario, userFacebook, null, convite);

        } else {
            usuario.setIdFacebook(userFacebook.getId());
            usuario.setNome(userFacebook.getName());
            usuario.setRedeSocial(RedeSocial.FACEBOOK);
            usuario.setAtivo(true);
            usuarioRepository.update(usuario);
        }

        return usuario;
    }

    @Override
    @Transactional
    public Usuario obterUsuarioViaGoogle(Userinfoplus userInfoPlus, String convite) throws LeitoorException {

        Usuario usuario = usuarioRepository.findByIdGoogle(userInfoPlus.getId());
        if (usuario == null) {
            usuario = usuarioRepository.findByEmail(userInfoPlus.getEmail());
        }

        if (usuario == null) {
            usuario = criarNovoUsuarioGoogle(userInfoPlus, convite);

        } else if (Has.content(convite)) {
            usuario = alterarUsuarioConvidado(usuario, null, userInfoPlus, convite);

        } else {
            usuario.setIdGoogle(userInfoPlus.getId());
            usuario.setNome(userInfoPlus.getName());
            usuario.setRedeSocial(RedeSocial.GOOGLE);
            usuario.setAtivo(true);
            usuarioRepository.update(usuario);
        }

        return usuario;

    }

    // metodo para cadastro pois não possui um usuarioFacebook nem usuarioGoogle
    private Usuario alterarUsuarioConvidado(final Usuario usuario) throws LeitoorException {
        return alterarUsuarioConvidado(usuario, null, null, usuario.getConvite());
    }

    private Usuario alterarUsuarioConvidado(final Usuario usuario, User userFacebook, Userinfoplus userInfoPlus, String convite)
        throws LeitoorException {

        final Usuario convidado = usuarioRepository.findByConvite(convite);
        if (convidado == null) {
            throw LeitoorException.conviteInvalidoException(convite);
        }

        // se o usuario for o mesmo que o convidado então apenas retornar
        if (convidado.equals(usuario)) {
            return usuario;
        }

        if (Has.content(userFacebook)) {
            usuario.setEmail(convidado.getEmail());
            usuario.setIdFacebook(userFacebook.getId());
            usuario.setNome(userFacebook.getName());
            usuario.setRedeSocial(RedeSocial.FACEBOOK);

        } else if (Has.content(userInfoPlus)) {
            usuario.setEmail(convidado.getEmail());
            usuario.setIdGoogle(userInfoPlus.getId());
            usuario.setNome(userInfoPlus.getName());
            usuario.setRedeSocial(RedeSocial.GOOGLE);
        }

        if (convidado instanceof Professor) {
            usuarioRepository.delete(convidado);
            usuario.setAtivo(Boolean.TRUE);
            usuario.setConvite(convidado.getConvite());
            usuario.setConvidadoPor(convidado.getConvidadoPor());

            usuarioRepository.update(usuario);

            // update diretamente na base para tornar professor
            usuarioRepository.tornarProfessor(usuario.getId());

            // retornar um professor para autenticação
            Professor professor = new Professor();
            BeanUtils.copyProperties(usuario, professor);
            return professor;
        }
        else if (convidado instanceof Aluno) {
            tarefaEnviadaRepository.moverTarefasConvidadoParaUsuario((Aluno) usuario, (Aluno) convidado);
            // remover aluno da turma
            ForEach.element(turmaRepository.findByAlunosIn((Aluno) convidado), new Each<Turma>() {
                @Override
                public void each(Turma turma, int index) {
                    turma.getAlunos().remove(convidado);
                    turma.getAlunos().add((Aluno) usuario);
                    turmaRepository.update(turma);
                }
            });
            usuarioRepository.delete(convidado);
            usuario.setEmail(convidado.getEmail());
            usuario.setConvite(convidado.getConvite());
            usuario.setAtivo(true);
            return usuario;
        }
        else {
            throw new IllegalStateException("Não foi possível identificar qual tipo de autenticação foi usado");
        }

    }

    private Usuario criarNovoUsuarioFacebook(User userFacebook, String convite) {

        if (Has.content(convite)) {
            Usuario usuario = usuarioRepository.findByConvite(convite);
            if (usuario == null) {
                LeitoorException.conviteInvalidoException(convite);
            }
            usuario.setIdFacebook(userFacebook.getId());
            usuario.setNome(userFacebook.getName());
            usuario.setRedeSocial(RedeSocial.FACEBOOK);
            usuario.setAtivo(true);
            usuarioRepository.update(usuario);
            return usuario;
        } else {
            // sem convites é criado como aluno
            Aluno aluno = new Aluno();
            aluno.setAtivo(Boolean.TRUE);
            aluno.setEmail(userFacebook.getEmail());
            aluno.setIdFacebook(userFacebook.getId());
            aluno.setNome(userFacebook.getName());
            aluno.setRedeSocial(RedeSocial.FACEBOOK);
            alunoRepository.create(aluno);
            return aluno;
        }
    }

    private Usuario criarNovoUsuarioGoogle(Userinfoplus userInfoPlus, String convite) {

        if (Has.content(convite)) {
            Usuario usuario = usuarioRepository.findByConvite(convite);
            if (usuario == null) {
                LeitoorException.conviteInvalidoException(convite);
            }
            usuario.setIdGoogle(userInfoPlus.getId());
            usuario.setNome(userInfoPlus.getName());
            usuario.setRedeSocial(RedeSocial.GOOGLE);
            usuario.setAtivo(true);
            usuarioRepository.update(usuario);
            return usuario;
        } else {
            // sem convites é criado como aluno
            Aluno aluno = new Aluno();
            aluno.setAtivo(Boolean.TRUE);
            aluno.setEmail(userInfoPlus.getEmail());
            aluno.setIdGoogle(userInfoPlus.getId());
            aluno.setNome(userInfoPlus.getName());
            aluno.setRedeSocial(RedeSocial.GOOGLE);
            alunoRepository.create(aluno);
            return aluno;
        }
    }

    @Override
    public void autenticarUsuario(Usuario usuario) {
        // autenticar no spring security

        Object credentials;
        if (usuario.getRedeSocial() == RedeSocial.FACEBOOK) {
            credentials = usuario.getIdFacebook();

        } else if (usuario.getRedeSocial() == RedeSocial.GOOGLE) {
            credentials = usuario.getIdGoogle();

        } else {
            credentials = usuario.getSenha();
        }

        // se não possuir idfacebook autentica com senha
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usuario.getEmail(), credentials);
        token.setDetails(new WebAuthenticationDetails((HttpServletRequest) request));
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // salvar o usuário na sessão
        session.setAttribute(USUARIO_AUTENTICADO, usuario);

    }

    @Override
    public String getNomeUsuarioAutenticado() {
        return getUsuarioAutenticado().getNome();
    }

    @Override
    public String getEmailUsuarioAutenticado() {
        return getUsuarioAutenticado().getEmail();
    }

    @Override
    @Transactional
    public Result<Void> convidarProfessores(String emails, Professor professor) {
        String[] listaEmails = emails.split(" ");

        EmailAddress emailProfessor = new EmailAddress(professor.getNome(), professor.getEmail());

        for (String email : listaEmails) {

            Usuario convidado = usuarioRepository.findByEmail(email);
            if (convidado != null && convidado instanceof Professor) {
                // se já for professor ignoramos o convite
                continue;
            }

            if (convidado != null) {
                convidado.setConvite(gerarConvite());
                usuarioRepository.update(convidado);
                usuarioRepository.tornarProfessor(convidado.getId());
            } else {
                convidado = new Professor();
                convidado.setConvite(gerarConvite());
                convidado.setEmail(email);
                convidado.setAtivo(Boolean.TRUE);
                usuarioRepository.create(convidado);
            }

            convidado.setConvidadoPor(professor);

            StringBuilder html = new StringBuilder();
            html.append("<p>Olá %s, tudo bom?</p>");
            html.append("<p>Gostaríamos de convidá-lo a usar a nova ferramenta de gestão de aulas, o <strong>Leitoor</strong>.</p>");
            html.append("<p>Com o Leitoor você poderá distribuir conteúdo entre os alunos como leituras, questionários e acompanhar de perto a evolução da sua turma.</p>");
            html.append("<p>Venha fazer parte da nossa comunidade de professores! Basta acessar o link com o seu convite para professor em %s e começar a usar.</p>");
            html.append("<p>Nós da equipe Leitoor queremos fazer mais por você, nossa ferramenta ainda está em desenvolvimento e algumas funções estão sendo desenvolvidas agora mesmo. Se encontrar algum problema ou quiser dar sugestões entre em contato enviando um e-mail para celio@delogic.com.br.</p>");
            html.append("</br><p><strong>A Equipe Leitoor deseja boa sorte e muito sucesso em sua caminhada!</strong></p>");
            html.append("<p>Célio Silva, celio@delogic.com.br - Responsável Área de Desenvolvimento.</p>");

            String link = "http://leitoor.com.br?convite=" + convidado.getConvite();

            String mensagem = String.format(html.toString(), email, link, professor.getNome());
            emailManager.sendAsynchronously(
                emailProfessor,
                new EmailContent(professor.getNome() + " lhe recomenda o Leitoor!", mensagem),
                new EmailAddress(email, email));

        }

        return Result.success("Convite enviado com sucesso para " + listaEmails.length + " professor(es)!");
    }

    private String gerarConvite() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Usuario autenticarUsuarioPorCadastro(String email, String senha) throws LeitoorException {
        SENHA_VAZIA_EXCEPTION.thrownIf(empty(senha));

        Usuario usuario = usuarioRepository.findByEmail(email);
        USUARIO_INEXISTENTE_EXCEPTION.withMessage("Nenhum usuario com email '%s' foi encontrado", email).thrownIf(usuario == null);
        USUARIO_INATIVO_EXCEPTION.thrownIf(usuario.getAtivo() == null || !usuario.getAtivo());
        SENHA_INCORRETA_EXCEPTION.thrownIf(different(senha, usuario.getSenha()));

        autenticarUsuario(usuario);

        return usuario;
    }

    private boolean empty(String senha) {
        return senha == null || senha.isEmpty() || senha.trim().isEmpty();
    }

    @Override
    @Transactional
    public Usuario criarUsuarioPorCadastro(Cadastro cadastro) throws LeitoorException {

        SENHAS_DIFERENTES_EXCEPTION.thrownIf(different(cadastro.getSenha(), cadastro.getRepetirSenha()));

        // se possuir convite
        if (Has.content(cadastro.getConvite())) {
            Usuario usuario = usuarioRepository.findByConvite(cadastro.getConvite());
            CONVITE_INVALIDO_EXCEPTION.thrownIf(usuario == null);

            usuario.setEmail(cadastro.getEmail());
            usuario.setNome(cadastro.getNomeCompleto());
            usuario.setSenha(cadastro.getSenha());

            return alterarUsuarioConvidado(usuario);

        } else {

            // sem convites é criado como aluno
            Usuario usuario = usuarioRepository.findByEmail(cadastro.getEmail());
            if (usuario == null) {
                // se não existir usuário criamos um novo como aluno
                usuario = new Aluno();
                usuario.setAtivo(Boolean.TRUE);
            }

            USUARIO_INATIVO_EXCEPTION.thrownIf(usuario.getAtivo() == null || !usuario.getAtivo());

            usuario.setEmail(cadastro.getEmail());
            usuario.setNome(cadastro.getNomeCompleto());
            usuario.setSenha(cadastro.getSenha());

            if (usuario.getId() != null) {
                usuarioRepository.update(usuario);
            } else {
                usuarioRepository.create(usuario);
            }
            return usuario;
        }
    }

    private boolean different(String senha, String repetirSenha) {
        return !senha.equals(repetirSenha);
    }

    @Override
    public String recuperarSenha(String email) throws LeitoorException {

        if (!Has.content(email)) {
            throw RECUPERAR_SENHA_EMAIL_VAZIO_EXCEPTION;
        }

        Usuario usuario = usuarioRepository.findByEmail(email);

        if (Has.content(usuario)) {

            if (Has.content(usuario.getSenha())) {

                EmailAddress emailAdmin = new EmailAddress("Equipe Leitoor", "admin@delogic.com");
                StringBuilder html = new StringBuilder();
                html.append("<p>Recuperação de Senha.</p>");
                html.append("<p>Ola %s.</p>");
                html.append("<p>você solicitou para recuperar a sua senha, se não solicitou desconsiderar este e-mail.</p>");
                html.append("<p>A sua senha atual é <strong> " + usuario.getSenha() + " </strong> .</p>");
                html.append("</br><p><strong>Equipe Leitoor.</strong></p>");
                String corpoEmail = html.toString();
                EmailContent conteudo = new EmailContent("Equipe Leitoor ;)", String.format(corpoEmail, usuario.getNome()));
                emailManager.sendAsynchronously(emailAdmin, conteudo, new EmailAddress(usuario.getNome(), usuario.getEmail()));

                return "Enviamos um e-mail para " + usuario.getEmail() + " com sua senha";
            } else if (!Has.content(usuario.getIdFacebook()) && Has.content(usuario.getConvite())) {

                throw RECUPERAR_SENHA_CONTA_CONVIDADA_EXCEPTION;
            } else {

                throw RECUPERAR_SENHA_CONTA_TIPO_FACEBOOK_EXCEPTION;
            }
        } else {
            throw RECUPERAR_SENHA_CONTA_INEXISTENTE_EXCEPTION;
        }
    }

}
