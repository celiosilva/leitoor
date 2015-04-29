package br.com.delogic.leitoor.service.impl;

import java.io.IOException;

import javax.inject.Named;

import br.com.delogic.leitoor.exception.LeitoorException;
import br.com.delogic.leitoor.service.GoogleService;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

@Named("googleService")
public class GoogleServiceImpl implements GoogleService {

    private static final String CLIENT_SECRET = "UDX_mDnc6Vy9GzG2Hf3vXKvF";
    private static final String CLIENT_ID     = "232471467258-nka1vthancampcs3raf6geaaojcgbhpv.apps.googleusercontent.com";

    @Override
    public String getUrlAutenticacao(String convite) {

        return "redirect:https://accounts.google.com/o/oauth2/auth?"
            + "&client_id=" + CLIENT_ID
            + "&redirect_uri=http://leitoor.com.br/login/google/autenticar"
            + "&scope=email%20profile"
            + "&state=" + convite
            + "&response_type=code";
    }

    @Override
    public Userinfoplus getUsuarioGoogle(String codigo) throws LeitoorException {

        try {
            GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(), new JacksonFactory(), CLIENT_ID,
                CLIENT_SECRET, codigo, "http://leitoor.com.br/login/google/autenticar").execute();

            GoogleCredential credential = new GoogleCredential().setAccessToken(response.getAccessToken());
            Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName(
                "Oauth2").build();

            return oauth2.userinfo().get().execute();

        } catch (IOException e) {
            throw ERRO_AUTENTICACAO_GOOGLE;
        }
    }

}
