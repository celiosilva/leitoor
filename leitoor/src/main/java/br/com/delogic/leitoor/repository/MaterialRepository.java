package br.com.delogic.leitoor.repository;

import java.util.List;

import br.com.delogic.leitoor.entidade.Material;
import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.repository.config.EntityRepository;

public interface MaterialRepository extends EntityRepository<Material, Integer> {

	List<Material> findByProfessor(Professor professor);
	
}
