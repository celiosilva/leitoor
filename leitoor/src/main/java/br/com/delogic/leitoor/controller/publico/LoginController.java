package br.com.delogic.leitoor.controller.publico;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.delogic.jfunk.Has;
import br.com.delogic.leitoor.entidade.Usuario;
import br.com.delogic.leitoor.exception.LeitoorException;
import br.com.delogic.leitoor.service.AutenticacaoService;
import br.com.delogic.leitoor.service.FacebookService;
import br.com.delogic.leitoor.service.GoogleService;
import br.com.delogic.leitoor.util.csa.UrlMapping;

import com.google.api.services.oauth2.model.Userinfoplus;
import com.restfb.types.User;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Inject
    @Named("urlHomeProfessor")
    private UrlMapping          urlHomeProfessor;

    @Inject
    @Named("urlHomeAluno")
    private UrlMapping          urlHomeAluno;

    @Inject
    @Named("urlHomePublico")
    private UrlMapping          urlHomePublico;

    @Inject
    private GoogleService       googleService;

    @Inject
    private AutenticacaoService autenticacaoService;

    @Inject
    private FacebookService     facebookService;

    @RequestMapping({ "", "/" })
    public String exibirLoginOuEntrar(HttpServletRequest request) {

        if (autenticacaoService.isProfessorAutenticado()) {
            return getPrimeiroRequestOuEntrar(request, urlHomeProfessor);
        }
        else if (autenticacaoService.isAlunoAutenticado()) {
            return getPrimeiroRequestOuEntrar(request, urlHomeAluno);
        }

        return urlHomePublico.getRedirectPath();

    }

    private String getPrimeiroRequestOuEntrar(HttpServletRequest req, UrlMapping url) {
        DefaultSavedRequest primeiroRequest = (DefaultSavedRequest) req.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (primeiroRequest == null || !Has.content(primeiroRequest.getRequestURI())) {
            return url.getRedirectPath();
        } else {
            return "redirect:" + primeiroRequest.getRedirectUrl();
        }

    }

    @RequestMapping(value = "/facebook")
    public ModelAndView exibirLoginOuEntrarFacebook(@RequestParam(value = "convite", defaultValue = "") String convite) {

        if (autenticacaoService.isProfessorAutenticado()) {
            return new ModelAndView(urlHomeProfessor.getRedirectPath());
        }
        else if (autenticacaoService.isAlunoAutenticado()) {
            return new ModelAndView(urlHomeAluno.getRedirectPath());
        }

        return new ModelAndView(facebookService.getUrlAutenticacao(convite));
    }

    @RequestMapping(value = "/facebook/autenticar")
    public String autenticarFacebook(
        @RequestParam(value = "code", defaultValue = "") String codigo,
        @RequestParam(value = "state", defaultValue = "") String convite, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        try {
            User userFacebook = facebookService.getUsuarioFacebook(codigo);
            Usuario usuario = autenticacaoService.obterUsuarioViaFacebook(userFacebook, convite);

            autenticacaoService.autenticarUsuario(usuario);
            return exibirLoginOuEntrar(request);

        } catch (LeitoorException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());

            return urlHomePublico.getRedirectPath();
        }
    }

    @RequestMapping(value = "/google")
    public ModelAndView exibirLoginOuEntrarGoogle(@RequestParam(value = "convite", defaultValue = "") String convite) {

        return new ModelAndView(googleService.getUrlAutenticacao(convite));
    }

    @RequestMapping(value = "/google/autenticar")
    public String autenticarGoogle(
        @RequestParam(value = "code", defaultValue = "") String codigo,
        @RequestParam(value = "state", defaultValue = "") String convite, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        try {
            Userinfoplus userInfoPlus = googleService.getUsuarioGoogle(codigo);
            Usuario usuario = autenticacaoService.obterUsuarioViaGoogle(userInfoPlus, convite);

            autenticacaoService.autenticarUsuario(usuario);

            return exibirLoginOuEntrar(request);

        } catch (LeitoorException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", e.getMessage());

            return urlHomePublico.getRedirectPath();
        }
    }

    @RequestMapping(value = "/autenticarcadastro", method = RequestMethod.POST)
    public ModelAndView autenticarPorCadastro(String email, String senha, RedirectAttributes redirectAttributes) {

        try {
            autenticacaoService.autenticarUsuarioPorCadastro(email, senha);
            return new ModelAndView("util/redirect-js").addObject("location", "login");

        } catch (LeitoorException e) {
            return new ModelAndView("publico/home-login")
                .addObject("erro", e.getMessage())
                .addObject("email", email)
                .addObject("senha", senha);
        }

    }

}
