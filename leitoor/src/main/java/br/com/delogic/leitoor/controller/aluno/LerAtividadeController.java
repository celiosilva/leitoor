package br.com.delogic.leitoor.controller.aluno;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.model.LeituraModel;
import br.com.delogic.leitoor.model.QuestionarioModel;
import br.com.delogic.leitoor.model.ResponderQuestionarioModel;
import br.com.delogic.leitoor.service.AtividadeService;
import br.com.delogic.leitoor.service.AutenticacaoService;

@Controller
@RequestMapping("/aluno/tarefa")
public class LerAtividadeController {

    @Inject
    private AtividadeService atividadeService;
    
    @Inject
    AutenticacaoService autenticacaoService;

    @RequestMapping(value = "/{idTarefaEnviada}", method = RequestMethod.GET)
    public ModelAndView getPaginaLerMaterial(@PathVariable ("idTarefaEnviada") Integer idTarefaEnviada,
    		@RequestParam(value="pagina", required=false) Integer pagina) {
    	if (pagina == null) {
    		pagina = 1;
    	}
    	LeituraModel leitura = atividadeService.iniciarLeitura(idTarefaEnviada, pagina);
    	if (leitura == null) {
    		throw new RuntimeException("Atividade não cadastrada!");
    	}
    	List<Integer> paginas = new ArrayList<Integer>();
    	for (int i = 1; i <= leitura.getPaginasAcessiveis(); i++) {
			paginas.add(i);
		}
    	
    	//Aluno aluno = autenticacaoService.getAlunoAutenticado();
    	Aluno aluno = leitura.getAluno();
    	
        return new ModelAndView("/aluno/ler-material")
        		.addObject("leitura", leitura)
        		.addObject("paginas", paginas)
        		.addObject("aluno", aluno)
        		.addObject("idTarefaEnviada", idTarefaEnviada);
    }
    
    @RequestMapping(value = "/concluida/{idTarefaEnviada}", method = RequestMethod.GET)
    public ModelAndView getPaginaTarefaFinalizada(@PathVariable ("idTarefaEnviada") Integer idTarefaEnviada) {
    	LeituraModel leitura = atividadeService.resumoLeitura(idTarefaEnviada);
    	if (leitura == null) {
    		throw new RuntimeException("Atividade não cadastrada!");
    	}
    	//Aluno aluno = autenticacaoService.getAlunoAutenticado();
    	Aluno aluno = leitura.getAluno();
        return new ModelAndView("/aluno/leitura-concluida")
        		.addObject("leitura", leitura)
        		.addObject("aluno", aluno)
        		.addObject("idTarefaEnviada", idTarefaEnviada);
    }
    
    @RequestMapping(value = "/concluir/{idTarefaEnviada}", method = RequestMethod.GET)
    public ModelAndView avancarLeitura(@PathVariable ("idTarefaEnviada") Integer idTarefaEnviada,
    		@RequestParam(value="pagina") Integer pagina,
    		RedirectAttributes redirectAttrs) {
    	
    	boolean concluida = atividadeService.avancarLeitura(idTarefaEnviada, pagina);
    	redirectAttrs.addAttribute("idTarefaEnviada", idTarefaEnviada);
    	if (concluida) {
    		return new ModelAndView("redirect:/aluno/tarefa/concluida/{idTarefaEnviada}");
    	} else {
    		return new ModelAndView("redirect:/aluno/tarefa/{idTarefaEnviada}?pagina=" + (pagina + 1));
    	}
    }

    public ModelAndView getPaginaResponderQuestoes() {
    	return null;
    }
    
    public ModelAndView submeterQuestoes() {
    	return null;
    }
    
    @RequestMapping(value = "/questionario/{idTarefaEnviada}", method = RequestMethod.GET)
    public ModelAndView recuperarQuestionario(@PathVariable ("idTarefaEnviada") Integer idTarefaEnviada,
    		@RequestParam(value="pagina", required=false) Integer pagina) {
    	if (pagina == null) {
    		pagina = 1;
    	}
    	QuestionarioModel qm = atividadeService.recuperarQuestionario(idTarefaEnviada, pagina);
    	if (qm == null) {
    		throw new RuntimeException("Atividade não cadastrada!");
    	}
        return new ModelAndView("/aluno/questionario")
        		.addObject("questionario", qm)
        		.addObject("idTarefaEnviada", idTarefaEnviada);
    }
    
    @RequestMapping(value = "/questionario/{idTarefaEnviada}", method = RequestMethod.POST)
    @ResponseBody
    public String salvarQuestionario(
    		@PathVariable ("idTarefaEnviada") Integer idTarefaEnviada,
    		@RequestParam(value="pagina", required=false) Integer pagina,
    		@RequestBody ResponderQuestionarioModel respostas) {
    	if (pagina == null) {
    		pagina = 1;
    	}
    	return new Boolean(atividadeService.gravarQuestionario(idTarefaEnviada, pagina, respostas)).toString();
    }

}
