package br.com.delogic.leitoor.service;

import java.util.List;

import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.model.AvaliacaoQuestionarioModel;
import br.com.delogic.leitoor.model.DetalharLeituraAlunoModel;
import br.com.delogic.leitoor.model.InformarAlunosModel;
import br.com.delogic.leitoor.model.LeituraModel;
import br.com.delogic.leitoor.model.QuestionarioModel;
import br.com.delogic.leitoor.model.ResponderQuestionarioModel;
import br.com.delogic.leitoor.util.Result;

public interface AtividadeService {

    Result<Void> enviarAtividade(InformarAlunosModel alunos, Integer idMaterialConfigurado, Professor professor);

    LeituraModel iniciarLeitura(Integer idTarefaEnviada, Integer pagina);

    LeituraModel resumoLeitura(Integer idTarefaEnviada);

    boolean avancarLeitura(Integer idTarefaEnviada, Integer pagina);

    QuestionarioModel recuperarQuestionario(Integer idTarefaEnviada, Integer pagina);

    boolean gravarQuestionario(Integer idTarefaEnviada,
        Integer pagina, ResponderQuestionarioModel respostas);

    DetalharLeituraAlunoModel getInformacoesDetalheLeitura(
        Integer idTarefaEnviada);

    List<QuestionarioModel> getInformacoesRespostas(Integer idTarefaEnviada);

    AvaliacaoQuestionarioModel buscarAvaliacaoQuestionario(Integer idTarefaEnviada);

    Result<Void> salvarAvaliacaoQuestionario(AvaliacaoQuestionarioModel avaliacao);

}
