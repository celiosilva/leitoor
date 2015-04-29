package br.com.delogic.leitoor.service;

import br.com.delogic.leitoor.entidade.Usuario;

import com.restfb.types.User;

public interface UsuarioService {

    Usuario criarUsuarioFacebook(User user);

}
