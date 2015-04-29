package br.com.delogic.leitoor.service;

import com.restfb.types.User;

public interface FacebookService {

    /**
     * Obtém um usuario (facebook) com todas as informações do facebook
     *
     * @param codigo
     * @return usuario (facebook)
     */
    User getUsuarioFacebook(String codigo);

    /**
     * Obtém a url de conexão com o facebook
     *
     * @param token
     *            de acesso passado pelo convite quando foi enviado
     *
     * @return uma url
     */
    String getUrlAutenticacao(String token);

}
