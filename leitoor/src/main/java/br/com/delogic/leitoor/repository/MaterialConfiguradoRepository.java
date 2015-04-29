package br.com.delogic.leitoor.repository;

import br.com.delogic.leitoor.entidade.MaterialConfigurado;
import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.repository.config.EntityRepository;

public interface MaterialConfiguradoRepository extends EntityRepository<MaterialConfigurado, Integer> {

    MaterialConfigurado findByIdAndProfessor(Integer id, Professor professor);

}
