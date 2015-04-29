package br.com.delogic.leitoor.controller.publico;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.delogic.leitoor.entidade.Usuario;
import br.com.delogic.leitoor.exception.LeitoorException;
import br.com.delogic.leitoor.model.Cadastro;
import br.com.delogic.leitoor.service.AutenticacaoService;
import br.com.delogic.leitoor.util.csa.UrlMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Inject
    @Named("urlHomePublico")
    private UrlMapping          urlHomePublico;

    @Inject
    private AutenticacaoService autenticacaoService;

    @RequestMapping(value = { "", "home" }, method = RequestMethod.GET)
    public ModelAndView getHome(@RequestParam(value = "convite", defaultValue = "") String convite,
        final RedirectAttributes redirectAttributes) {

        Cadastro cadastro = new Cadastro();
        cadastro.setConvite(convite);

        return new ModelAndView("publico/home")
            .addObject("convite", convite)
            .addObject("cadastro", cadastro);
    }

    @RequestMapping(value = "recuperarsenha", method = RequestMethod.GET)
    public ModelAndView getModalSenha() {
        return new ModelAndView("publico/home-senha");
    }

    @RequestMapping(value = "recuperarsenha", method = RequestMethod.POST)
    public ModelAndView recuperarSenha(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {

        try {

            return new ModelAndView("publico/home-senha-sucesso")
                .addObject("mensagem", autenticacaoService.recuperarSenha(email));
        } catch (LeitoorException e) {

            return new ModelAndView("publico/home-senha")
                .addObject("email", email)
                .addObject("mensagem", e.getMessage());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView cadastrarEAutenticar(@Valid @ModelAttribute("cadastro") Cadastro cadastro, BindingResult validacao) {

        if (validacao.hasErrors()) {
            return new ModelAndView("publico/home-cadastro")
                .addObject("cadastro", cadastro);
        }
        try {

            Usuario usuario = autenticacaoService.criarUsuarioPorCadastro(cadastro);
            autenticacaoService.autenticarUsuario(usuario);
            return new ModelAndView("util/redirect-js").addObject("location", "login");

        } catch (LeitoorException e) {
            if (AutenticacaoService.SENHAS_DIFERENTES_EXCEPTION.equals(e)) {
                validacao
                    .addError(new FieldError("cadastro", "repetirSenha", cadastro.getRepetirSenha(), true, null, null, e.getMessage()));
                validacao.addError(new FieldError("cadastro", "senha", cadastro.getSenha(), true, null, null, ""));
            }
            return new ModelAndView("publico/home-cadastro")
                .addObject("cadastro", cadastro)
                .addObject("violacoes", e.getMessage());
        }

    }

}