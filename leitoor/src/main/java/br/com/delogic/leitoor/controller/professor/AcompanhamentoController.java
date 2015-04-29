package br.com.delogic.leitoor.controller.professor;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.delogic.csa.repository.Criteria;
import br.com.delogic.csa.repository.sql.SqlQuery;
import br.com.delogic.jfunk.data.Result;
import br.com.delogic.leitoor.entidade.enums.Avaliacao;
import br.com.delogic.leitoor.model.AvaliacaoQuestionarioModel;
import br.com.delogic.leitoor.model.DetalharLeituraAlunoModel;
import br.com.delogic.leitoor.service.AtividadeService;
import br.com.delogic.leitoor.view.ResumoAcompanhamento;
import br.com.delogic.leitoor.view.ResumoTarefa;

@Controller
@RequestMapping("/professor/acompanhamento")
public class AcompanhamentoController {

    @Inject
    @Named("resumoAcompanhamento")
    private SqlQuery<ResumoAcompanhamento> resumoAcompanhamento;

    @Inject
    @Named("resumoTarefas")
    private SqlQuery<ResumoTarefa>         resumoTarefas;

    @Inject
    private AtividadeService               atividadeService;

    public static class Grafico {

        private int media;
        private int total;
        private int naoIniciado;
        private int abaixo50;
        private int acima50;
        private int finalizado;

        public int getNaoIniciado() {
            return naoIniciado;
        }

        public void setNaoIniciado(int naoIniciado) {
            this.naoIniciado = naoIniciado;
        }

        public int getAbaixo50() {
            return abaixo50;
        }

        public void setAbaixo50(int abaixo50) {
            this.abaixo50 = abaixo50;
        }

        public int getAcima50() {
            return acima50;
        }

        public void setAcima50(int acima50) {
            this.acima50 = acima50;
        }

        public int getFinalizado() {
            return finalizado;
        }

        public void setFinalizado(int finalizado) {
            this.finalizado = finalizado;
        }

        public int getMedia() {
            return media;
        }

        public void setMedia(int media) {
            this.media = media;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getPaginaAcompanhamento(@RequestParam(value = "idTarefa") Integer idTarefa) {

        Criteria filtroAcompanhamento = new Criteria();
        filtroAcompanhamento.addParameter("idTarefa", idTarefa);

        List<ResumoAcompanhamento> acompanhamentos = resumoAcompanhamento.getList(filtroAcompanhamento);

        Collections.sort(acompanhamentos);

        Result<ResumoTarefa> tarefa = resumoTarefas.getFirst(new Criteria("idTarefa", idTarefa));
        if (!tarefa.hasValue()) {
            throw new IllegalArgumentException("É obrigatório ter uma tarefa");
        }

        Grafico graficoGeral = new Grafico();
        Grafico graficoLeitura = new Grafico();
        Grafico graficoQuestionario = new Grafico();

        for (ResumoAcompanhamento resumoAcompanhamento : acompanhamentos) {
            computarPercentual(graficoGeral, resumoAcompanhamento.getPercentualTotal());
            computarPercentual(graficoLeitura, resumoAcompanhamento.getPercentualLeitura());
            if (resumoAcompanhamento.getPercentualQuestionario() != null) {
                computarPercentual(graficoQuestionario, resumoAcompanhamento.getPercentualQuestionario());
            }
        }
        calcularMedia(graficoGeral, acompanhamentos.size());
        calcularMedia(graficoLeitura, acompanhamentos.size());
        calcularMedia(graficoQuestionario, acompanhamentos.size());

        return getViewAcompanhamento(acompanhamentos, tarefa.getValue(), graficoGeral, graficoLeitura, graficoQuestionario);

    }

    private void calcularMedia(Grafico graficoGeral, int totalAcompanhamentos) {
        graficoGeral.media = graficoGeral.getTotal() / totalAcompanhamentos;
    }

    private void computarPercentual(Grafico grafico, int percentual) {
        if (percentual == 0) {
            grafico.naoIniciado++;
        } else if (percentual <= 50) {
            grafico.abaixo50++;
        } else if (percentual == 100) {
            grafico.finalizado++;
        } else {
            grafico.acima50++;
        }
        grafico.total += percentual;
    }

    private ModelAndView getViewAcompanhamento(List<ResumoAcompanhamento> acompanhamentos, ResumoTarefa tarefa, Grafico graficoGeral,
        Grafico graficoLeitura, Grafico graficoQuestionario) {
        return new ModelAndView("/professor/acompanhamento")
            .addObject("acompanhamentos", acompanhamentos)
            .addObject("tarefa", tarefa)
            .addObject("graficoGeral", graficoGeral)
            .addObject("graficoLeitura", graficoLeitura)
            .addObject("graficoQuestionario", graficoQuestionario);
    }

    @RequestMapping(value = "/detalhe", method = RequestMethod.GET)
    public ModelAndView getPaginaAcompanhamentoDetalhe(@RequestParam(value = "idTarefaEnviada") Integer idTarefaEnviada) {

        DetalharLeituraAlunoModel detalhes = atividadeService.getInformacoesDetalheLeitura(idTarefaEnviada);
        if (detalhes == null) {
            throw new RuntimeException("Atividade não cadastrada!");
        }
        Result<ResumoTarefa> tarefa = resumoTarefas.getFirst(new Criteria("idTarefa", detalhes.getTarefa().getId()));
        if (!tarefa.hasValue()) {
            throw new IllegalArgumentException("É obrigatório ter uma tarefa");
        }
        return new ModelAndView("/professor/acompanhamento-aluno")
            .addObject("detalhes", detalhes)
            .addObject("tarefa", tarefa.getValue())
            .addObject("idTarefaEnviada", idTarefaEnviada);

    }

    @RequestMapping(value = "/avaliacao", method = RequestMethod.GET)
    public ModelAndView getPaginaAvaliacao(@RequestParam(value = "idTarefaEnviada") Integer idTarefaEnviada) {
        AvaliacaoQuestionarioModel avaliacao = atividadeService.buscarAvaliacaoQuestionario(idTarefaEnviada);
        return getViewAvaliacao(avaliacao);
    }

    @RequestMapping(value = "/avaliacao", method = RequestMethod.POST)
    public ModelAndView getPaginaAvaliacao(@ModelAttribute("avaliacao") AvaliacaoQuestionarioModel avaliacao) {
        br.com.delogic.leitoor.util.Result<Void> resultado = atividadeService.salvarAvaliacaoQuestionario(avaliacao);
        return getViewAvaliacao(atividadeService.buscarAvaliacaoQuestionario(avaliacao.getIdTarefaEnviada()))
            .addObject("sucesso", resultado.getMessage());
    }

    private ModelAndView getViewAvaliacao(AvaliacaoQuestionarioModel avaliacao) {
        return new ModelAndView("/professor/avaliacao-aluno")
            .addObject("avaliacao", avaliacao).addObject("avaliacoes", Avaliacao.values());
    }

}
