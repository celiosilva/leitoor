package br.com.delogic.leitoor.repository;

import java.util.List;

import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.repository.config.EntityRepository;

public interface ProfessorRepository extends EntityRepository<Professor, Integer> {

    List<Professor> findByAtivoTrueOrderByIdAsc();

}
