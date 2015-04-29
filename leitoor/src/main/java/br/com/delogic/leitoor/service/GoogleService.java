package br.com.delogic.leitoor.service;

import java.io.IOException;

import br.com.delogic.leitoor.exception.LeitoorException;
import br.com.delogic.leitoor.exception.RegraNegocioException;

import com.google.api.services.oauth2.model.Userinfoplus;

public interface GoogleService {

    /**
     * Obtém a url de conexão com o Google
     *
     * @param convite
     * @return uma url
     */
    String getUrlAutenticacao(String convite);

    /**
     * Obtém um usuario GooglePlus
     *
     * @param code
     * @return usuario google
     * @throws IOException
     * @throws LeitoorException
     */
    Userinfoplus getUsuarioGoogle(String code) throws LeitoorException;

    RegraNegocioException ERRO_AUTENTICACAO_GOOGLE = new RegraNegocioException(
                                                       "Aconteceu um erro ao tentar efetuar a autenticação");
}
