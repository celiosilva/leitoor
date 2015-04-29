package br.com.delogic.leitoor.controller.aluno;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.TarefaEnviada;
import br.com.delogic.leitoor.model.DetalharLeituraAlunoModel;
import br.com.delogic.leitoor.model.QuestionarioModel;
import br.com.delogic.leitoor.repository.AlunoRepository;
import br.com.delogic.leitoor.repository.TarefaEnviadaRepository;
import br.com.delogic.leitoor.service.AtividadeService;
import br.com.delogic.leitoor.service.AutenticacaoService;

@Controller
@RequestMapping("/aluno")
public class HomeAlunoController {

    @Inject
    AutenticacaoService     autenticacaoService;

    @Inject
    TarefaEnviadaRepository tarefaEnviadaRepository;

    @Inject
    AlunoRepository         alunoRepository;
    
    @Inject
    private AtividadeService atividadeService;

    @RequestMapping({ "", "/home", "/" })
    ModelAndView getHome() {

        Aluno aluno = autenticacaoService.getAlunoAutenticado();
        List<TarefaEnviada> tarefas = tarefaEnviadaRepository.findByAluno(aluno);
        return new ModelAndView("aluno/home")
            .addObject("aluno", aluno)
            .addObject("tarefas", tarefas);

    }
    
    @RequestMapping(value = "/detalhar/{idTarefaEnviada}", method = RequestMethod.GET)
    public ModelAndView getPaginaDetalheAtividade(@PathVariable ("idTarefaEnviada") Integer idTarefaEnviada) {
    	
    	DetalharLeituraAlunoModel detalhes = atividadeService.getInformacoesDetalheLeitura(idTarefaEnviada);
    	if (detalhes == null) {
    		throw new RuntimeException("Atividade não cadastrada!");
    	}
    	List<QuestionarioModel> qm = atividadeService.getInformacoesRespostas(idTarefaEnviada);
        return new ModelAndView("/aluno/detalhe-atividade")
        		.addObject("detalhes", detalhes)
        		.addObject("questionarios", qm)
        		.addObject("idTarefaEnviada", idTarefaEnviada);
        
    }

}