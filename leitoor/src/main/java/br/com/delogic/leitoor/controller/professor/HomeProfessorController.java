package br.com.delogic.leitoor.controller.professor;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.delogic.csa.repository.Criteria;
import br.com.delogic.csa.repository.QueryRepository;
import br.com.delogic.leitoor.service.AutenticacaoService;
import br.com.delogic.leitoor.util.Result;
import br.com.delogic.leitoor.util.csa.UrlMapping;
import br.com.delogic.leitoor.view.ResumoMaterial;
import br.com.delogic.leitoor.view.ResumoTarefa;

@Controller
@RequestMapping("/professor")
public class HomeProfessorController {

    @Inject
    @Named("resumoMateriais")
    private QueryRepository<ResumoMaterial> queryMateriais;

    @Inject
    @Named("resumoTarefas")
    private QueryRepository<ResumoTarefa>   queryTarefas;

    @Inject
    private AutenticacaoService             autenticacaoService;

    @Inject
    @Named("urlHomeProfessor")
    private UrlMapping                      urlHomeProfessor;

    public static class ConvidarProfessorModel {

        @NotEmpty
        @Size(max = 5000)
        @Pattern(regexp =
            "^([a-zA-Z0-9\\._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]{2,}\\s*)+$*", message =
            "E-mails devem ser algo como nome@email(.com, .gov, .br, etc). Sem virgulas ou caracteres especiais.")
        private String emails;

        public String getEmails() {
            return emails;
        }

        public void setEmails(String emails) {
            this.emails = emails;
        }

    }

    @RequestMapping({ "", "/home", "/" })
    public ModelAndView getHome() {

        Criteria filtroProfessor = new Criteria();
        filtroProfessor.addParameter("idProfessor", autenticacaoService.getProfessorAutenticado().getId());

        List<ResumoMaterial> materiais = queryMateriais.getList(filtroProfessor);

        filtroProfessor.setParameterizedOrderBy("percentualCompleto");
        List<ResumoTarefa> tarefas = queryTarefas.getList(filtroProfessor);

        return new ModelAndView("professor/home")
            .addObject("materiais", materiais)
            .addObject("tarefas", tarefas); // mensagem de sucesso
    }

    @RequestMapping(value = "/convidar", method = RequestMethod.GET)
    public ModelAndView exibirConvidar() {
        return getViewConvidar(new ConvidarProfessorModel());
    }

    @RequestMapping(value = "/convidar", method = RequestMethod.POST)
    public ModelAndView enviarConvites(@Valid @ModelAttribute("convidar") ConvidarProfessorModel model,
        BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return getViewConvidar(model);
        }

        Result<Void> resultado = autenticacaoService.convidarProfessores(model.getEmails(), autenticacaoService.getProfessorAutenticado());

        redirectAttributes.addFlashAttribute("sucesso", resultado.getMessage());

        return new ModelAndView(urlHomeProfessor.getRedirectPath());

    }

    private ModelAndView getViewConvidar(ConvidarProfessorModel model) {
        return new ModelAndView("professor/convidar")
            .addObject("convidar", model);
    }

}