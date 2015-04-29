package br.com.delogic.leitoor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.repository.config.EntityRepository;

public interface AlunoRepository extends EntityRepository<Aluno, Integer> {

    Aluno findByEmail(String email);

    @Query("select a from Aluno as a")
    List<Aluno> findAll();

}
