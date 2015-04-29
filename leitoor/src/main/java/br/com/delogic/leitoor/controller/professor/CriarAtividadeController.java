package br.com.delogic.leitoor.controller.professor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.Material;
import br.com.delogic.leitoor.entidade.MaterialConfigurado;
import br.com.delogic.leitoor.entidade.Turma;
import br.com.delogic.leitoor.model.ConfigurarMaterialModel;
import br.com.delogic.leitoor.model.ConfigurarPaginaModel;
import br.com.delogic.leitoor.model.CriarMaterialModel;
import br.com.delogic.leitoor.model.InformarAlunosModel;
import br.com.delogic.leitoor.repository.TurmaRepository;
import br.com.delogic.leitoor.service.AtividadeService;
import br.com.delogic.leitoor.service.AutenticacaoService;
import br.com.delogic.leitoor.service.MaterialService;
import br.com.delogic.leitoor.util.Result;
import br.com.delogic.leitoor.util.csa.UrlMapping;

@Controller
@RequestMapping("/professor/atividade/")
public class CriarAtividadeController {

    @Inject
    private MaterialService     materialService;

    //@Inject
    //private MaterialConfiguradoRepository materialConfiguradoRepository;
    
    @Inject
    private AtividadeService    atividadeService;

    @Inject
    private AutenticacaoService autenticacaoService;

    @Inject
    @Named("urlAtividadeAlunos")
    private UrlMapping          urlAtividadeAlunos;

    @Inject
    @Named("urlConfigurarAtividadeMaterial")
    private UrlMapping          urlConfigurarAtividadeMaterial;

    @Inject
    @Named("urlHomeProfessor")
    private UrlMapping          urlHomeProfessor;
    
    @Inject
    private TurmaRepository turmaRepository;

    @InitBinder
    public void initListBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1000);
    }

    @RequestMapping(value = "/criar-material", method = RequestMethod.GET)
    public ModelAndView getPaginaCriarMaterial(@RequestParam(value = "mat", defaultValue = "0") Integer idMatConfigurado) {
        CriarMaterialModel material = materialService.buscarMaterial(idMatConfigurado, autenticacaoService.getProfessorAutenticado());
        return getViewCriarMaterial(material);
    }

    @RequestMapping(value = "/criar-material", method = RequestMethod.POST)
    public ModelAndView criarMaterial(@Valid @ModelAttribute("material") CriarMaterialModel material,
        BindingResult result) {

        if (result.hasErrors()) {
            return getViewCriarMaterial(material);
        }

        Result<MaterialConfigurado> resultado = null;

        resultado = materialService.criarMaterial(material,
            autenticacaoService.getProfessorAutenticado());

        return new ModelAndView(urlConfigurarAtividadeMaterial.getRedirectPath() + "?mat=" + resultado.getValue().getId());
    }
    
    @RequestMapping(value = "/reusar-material", method = RequestMethod.GET)
    public ModelAndView reaproveitarMaterial(@ModelAttribute("mat") Integer materialId,
        BindingResult result) {

        if (result.hasErrors()) {
            return getViewCriarMaterial(new CriarMaterialModel());
        }

        Result<MaterialConfigurado> resultado = null;

        resultado = materialService.reusarMaterial(materialId,
            autenticacaoService.getProfessorAutenticado(), 0);

        return new ModelAndView(urlConfigurarAtividadeMaterial.getRedirectPath() + "?mat=" + resultado.getValue().getId());
    }

    @RequestMapping(value = "/configurar-material", method = RequestMethod.GET)
    public ModelAndView getPaginaConfigurarMaterial(@RequestParam(value = "mat", defaultValue = "0") Integer idMatConfigurado) {

        ConfigurarMaterialModel configurar = materialService.buscarConfiguracaoMaterial(idMatConfigurado,
            autenticacaoService.getProfessorAutenticado());

        return getViewConfigurarMaterial(configurar, idMatConfigurado);
    }

    @RequestMapping(value = "/configurar-material", method = RequestMethod.POST)
    public ModelAndView salvarConfiguracaoMaterial(@Valid @ModelAttribute("configuracao") ConfigurarMaterialModel configuracao,
        BindingResult result) {

        for (ConfigurarPaginaModel pm : configuracao.getPaginas()) {
            if (pm.getQuestoes() != null) {
                pm.getQuestoes().removeAll(Arrays.asList("", null));
            }
        }

        if (result.hasErrors()) {
            return getViewConfigurarMaterial(configuracao, configuracao.getIdMaterialConfiguracao());
        }

        materialService.configurarMaterial(configuracao, autenticacaoService.getProfessorAutenticado());

        return new ModelAndView(urlAtividadeAlunos.getRedirectPath() + "?mat=" + configuracao.getIdMaterialConfiguracao());
    }

    @RequestMapping(value = "/alunos", method = RequestMethod.GET)
    public ModelAndView getPaginaEnviarAtividade(@RequestParam("mat") Integer idMaterialConfigurado) {

        InformarAlunosModel alunos = new InformarAlunosModel();
        alunos.setIdMaterialConfigurado(idMaterialConfigurado);
        List<Turma> turmas = turmaRepository.findByTurmaByProfessor(autenticacaoService.getProfessorAutenticado());
        
        return getViewInformarAlunos(alunos, idMaterialConfigurado, turmas);

    }

    @RequestMapping(value = "/alunos", method = RequestMethod.POST)
    public ModelAndView enviarAtividade(@Valid @ModelAttribute("alunos") InformarAlunosModel alunos,
        BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            List<Turma> turmas = turmaRepository.findByTurmaByProfessor(autenticacaoService.getProfessorAutenticado());
            return getViewInformarAlunos(alunos, alunos.getIdMaterialConfigurado(), turmas);
        }

        Result<Void> resultado = atividadeService.enviarAtividade(alunos, alunos.getIdMaterialConfigurado(),
            autenticacaoService.getProfessorAutenticado());
        redirectAttributes.addFlashAttribute("sucesso", resultado.getMessage());

        return new ModelAndView(urlHomeProfessor.getRedirectPath());
    }
    
    @RequestMapping(value = "/turma", method = RequestMethod.POST)
    public ModelAndView enviarAtividadeComTurma(
    		@RequestParam("idTurma") Integer idTurma,
    		@RequestParam("idMaterialConfigurado") Integer idMaterialConfigurado,
    		@RequestParam("nomeAtividade") String nomeAtividade,
    		RedirectAttributes redirectAttributes) {

        InformarAlunosModel alunos = new InformarAlunosModel();
        alunos.setIdMaterialConfigurado(idMaterialConfigurado);
        
        Turma turma = turmaRepository.findById(idTurma);
        Set<Aluno> alunosTurma = turma.getAlunos();
        StringBuilder emails = new StringBuilder();
        for (Aluno aluno : alunosTurma) {
        	emails.append(aluno.getEmail());
        	emails.append(' ');
		}
        alunos.setEmails(emails.toString());
        alunos.setTurma(turma.getNome());
        
        //MaterialConfigurado materialConfigurado = materialConfiguradoRepository.findById(idMaterialConfigurado);
        alunos.setNomeAtividade(nomeAtividade);
        
        Result<Void> resultado = atividadeService.enviarAtividade(alunos, alunos.getIdMaterialConfigurado(),
            autenticacaoService.getProfessorAutenticado());
        redirectAttributes.addFlashAttribute("sucesso", resultado.getMessage());

        return new ModelAndView(urlHomeProfessor.getRedirectPath());
    }

    private ModelAndView getViewCriarMaterial(CriarMaterialModel material) {
    	List<Material> materiaisReuso = materialService.buscarMaterialParaReuso(autenticacaoService.getProfessorAutenticado());
        return new ModelAndView("/professor/criar-material")
            .addObject("material", material)
        	.addObject("materiaisReuso", materiaisReuso);
    }

    private ModelAndView getViewConfigurarMaterial(ConfigurarMaterialModel configurar, Integer idMatConfigurado) {
        return new ModelAndView("/professor/configurar-material")
            .addObject("configuracao", configurar)
            .addObject("mat", idMatConfigurado);
    }

    private ModelAndView getViewInformarAlunos(InformarAlunosModel alunos, Integer idMaterialConfigurado, List<Turma> turmas) {
        return new ModelAndView("/professor/informar-alunos")
            .addObject("alunos", alunos)
            .addObject("turmas", turmas)
            .addObject("mat", idMaterialConfigurado);
    }

}
