package br.com.delogic.leitoor.service.impl;

import javax.inject.Named;

import org.springframework.web.client.RestTemplate;

import br.com.delogic.leitoor.service.FacebookService;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.User;

@Named("facebookService")
public class FaceboookServiceImpl implements FacebookService {

    private static final String MY_APP_SECRET = "6af07dfb2c04e9bffa770a8206b3c999";
    private static final String MY_APP_ID     = "941559452525045";

    @Override
    public String getUrlAutenticacao(String convite) {

        return "redirect:https://www.facebook.com/dialog/oauth/?"
            + "client_id=" + MY_APP_ID
            + "&redirect_uri=http://leitoor.com.br/login/facebook/autenticar"
            + "&scope=email,public_profile,user_friends"
            + "&state=" + convite
            + "&display=page"
            + "&response_type=code";

    }

    @Override
    public User getUsuarioFacebook(String codigo) {

        String url = "https://graph.facebook.com/oauth/access_token?"
            + "client_id=" + MY_APP_ID
            + "&redirect_uri=http://leitoor.com.br/login/facebook/autenticar"
            + "&client_secret=" + MY_APP_SECRET
            + "&code=" + codigo;

        RestTemplate rest = new RestTemplate();
        AccessToken accessToken = AccessToken.fromQueryString(rest.getForObject(url, String.class));

        FacebookClient client = new DefaultFacebookClient(accessToken.getAccessToken());
        User usuario = client.fetchObject("me", User.class);

        return usuario;
    }

}
