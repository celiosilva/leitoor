package br.com.delogic.leitoor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.Leitura;
import br.com.delogic.leitoor.entidade.Tarefa;
import br.com.delogic.leitoor.entidade.TarefaEnviada;
import br.com.delogic.leitoor.repository.config.EntityRepository;

public interface TarefaEnviadaRepository extends EntityRepository<TarefaEnviada, Integer> {

    @Modifying
    @Query("update TarefaEnviada te set te.aluno = ?1 where te.aluno = ?2")
    void moverTarefasConvidadoParaUsuario(Aluno usuario, Aluno convidado);
    
    List<TarefaEnviada> findByAluno(Aluno aluno);
    
    List<TarefaEnviada> findByTarefa(Tarefa tarefa);
    
    @Query("select l from Leitura l where l.tarefaEnviada.tarefa = ?1")
    List<Leitura> findLeiturasByTarefa(Tarefa tarefa);
    

}
