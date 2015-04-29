package br.com.delogic.leitoor.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.delogic.leitoor.entidade.Usuario;
import br.com.delogic.leitoor.repository.config.EntityRepository;

public interface UsuarioRepository extends EntityRepository<Usuario, Integer> {

    Usuario findByConvite(String convite);

    Usuario findByEmail(String email);

    Usuario findByIdFacebook(String id);

    Usuario findByIdGoogle(String id);

    Usuario findByEmailAndSenha(String email, String senha);

    @Modifying
    @Query(nativeQuery = true, value = "update usuario set perfil = 'PROFESSOR' where id = ?1")
    int tornarProfessor(Integer id);

}
