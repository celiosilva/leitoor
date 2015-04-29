package br.com.delogic.leitoor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.entidade.Turma;
import br.com.delogic.leitoor.repository.config.EntityRepository;

public interface TurmaRepository extends EntityRepository<Turma, Integer> {

    List<Turma> findByAlunosIn(Aluno convidado);
    
    @Query(value="select distinct t.turma from Tarefa t where t.professor = ?1 ")
    List<Turma> findByTurmaByProfessor(Professor p);

}
