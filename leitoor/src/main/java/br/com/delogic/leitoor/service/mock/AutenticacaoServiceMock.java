package br.com.delogic.leitoor.service.mock;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.AuthenticationManager;

import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.entidade.Usuario;
import br.com.delogic.leitoor.repository.ProfessorRepository;
import br.com.delogic.leitoor.service.AutenticacaoService;
import br.com.delogic.leitoor.service.impl.AutenticacaoServiceImpl;

import com.restfb.types.User;

public class AutenticacaoServiceMock extends AutenticacaoServiceImpl implements AutenticacaoService {

    private Professor             professor;

    @Inject
    private ProfessorRepository   professorRepository;

    @Inject
    @Named("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Override
    public Professor getProfessorAutenticado() {
        if (professor == null) {
            List<Professor> professores = professorRepository.findByAtivoTrueOrderByIdAsc();
            if (professores.isEmpty()) {
                throw new IllegalStateException("É necessário executar o gerador para ter professores");
            }
            // obter primeiro professor sempre
            professor = professores.get(0);
        }
        return professor;
    }

    @Override
    public Aluno getAlunoAutenticado() {
        return getProfessorAutenticado();
    }

    @Override
    public boolean isUsuarioAutenticado() {
        return isProfessorAutenticado();
    }

    @Override
    public Usuario getUsuarioAutenticado() {
        return getProfessorAutenticado();
    }

    @Override
    public boolean isProfessorAutenticado() {
        autenticarUsuario(obterUsuarioViaFacebook(null, null));
        return true;
    }

    @Override
    public boolean isAlunoAutenticado() {
        return isProfessorAutenticado();
    }

    @Override
    public Usuario obterUsuarioViaFacebook(User userFacebook, String tokenAcesso) {
        // criar uma sessão para o spring security poder ser guardado
        return getProfessorAutenticado();

    }

    @Override
    public String getNomeUsuarioAutenticado() {
        return getUsuarioAutenticado().getNome();
    }

    @Override
    public String getEmailUsuarioAutenticado() {
        return getUsuarioAutenticado().getEmail();
    }

}
