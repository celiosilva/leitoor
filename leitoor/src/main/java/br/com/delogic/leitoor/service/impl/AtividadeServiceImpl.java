package br.com.delogic.leitoor.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.transaction.annotation.Transactional;

import br.com.delogic.csa.manager.EmailManager;
import br.com.delogic.csa.manager.email.EmailAddress;
import br.com.delogic.csa.manager.email.EmailContent;
import br.com.delogic.jfunk.Has;
import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.Atividade;
import br.com.delogic.leitoor.entidade.ConfiguracaoPagina;
import br.com.delogic.leitoor.entidade.Leitura;
import br.com.delogic.leitoor.entidade.Material;
import br.com.delogic.leitoor.entidade.MaterialConfigurado;
import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.entidade.Questao;
import br.com.delogic.leitoor.entidade.Resposta;
import br.com.delogic.leitoor.entidade.RespostaDissertativa;
import br.com.delogic.leitoor.entidade.Tarefa;
import br.com.delogic.leitoor.entidade.TarefaEnviada;
import br.com.delogic.leitoor.entidade.Turma;
import br.com.delogic.leitoor.entidade.enums.Avaliacao;
import br.com.delogic.leitoor.model.AvaliacaoQuestionarioModel;
import br.com.delogic.leitoor.model.AvaliacaoQuestionarioModel.RespostaModel;
import br.com.delogic.leitoor.model.DesempenhoAlunoModel;
import br.com.delogic.leitoor.model.DetalharLeituraAlunoModel;
import br.com.delogic.leitoor.model.InformarAlunosModel;
import br.com.delogic.leitoor.model.LeituraModel;
import br.com.delogic.leitoor.model.QuestionarioModel;
import br.com.delogic.leitoor.model.ResponderQuestionarioModel;
import br.com.delogic.leitoor.model.ResponderQuestionarioModel.ItemResposta;
import br.com.delogic.leitoor.repository.AlunoRepository;
import br.com.delogic.leitoor.repository.AtividadeRepository;
import br.com.delogic.leitoor.repository.MaterialConfiguradoRepository;
import br.com.delogic.leitoor.repository.TarefaEnviadaRepository;
import br.com.delogic.leitoor.repository.TarefaRepository;
import br.com.delogic.leitoor.repository.TurmaRepository;
import br.com.delogic.leitoor.service.AtividadeService;
import br.com.delogic.leitoor.util.Result;
import br.com.delogic.leitoor.util.csa.UrlMapping;

@Named("atividadeService")
public class AtividadeServiceImpl implements AtividadeService {

    @Inject
    private MaterialConfiguradoRepository matConfiguradoRepository;

    @Inject
    private AtividadeRepository           atividadeRepository;

    @Inject
    private AlunoRepository               alunoRepository;

    @Inject
    private TurmaRepository               turmaRepository;

    @Inject
    private TarefaRepository              tarefaRepository;

    @Inject
    private EmailManager                  emailManager;

    @Inject
    private TarefaEnviadaRepository       tarefaEnviadaRepository;

    @Inject
    @Named("urlLerTarefaEnviada")
    private UrlMapping                    urlLerTarefaEnviada;

    @Override
    @Transactional
    public Result<Void> enviarAtividade(InformarAlunosModel informarAlunos, Integer idMaterialConfigurado, Professor professor) {

        Atividade atividade = new Atividade();
        atividade.setDescricao(informarAlunos.getNomeAtividade());
        atividade.setMaterialConfigurado(matConfiguradoRepository.findById(idMaterialConfigurado));
        atividade.setProfessor(professor);

        atividadeRepository.create(atividade);

        Set<Aluno> alunos = new HashSet<Aluno>();
        String[] emails = informarAlunos.getEmails().split(" ");
        for (String email : emails) {
            Aluno aluno = alunoRepository.findByEmail(email.toLowerCase().trim());
            if (aluno == null) {
                aluno = new Aluno();
                aluno.setEmail(email.toLowerCase().trim());
                aluno.setNome(email.toLowerCase().trim());
                aluno.setConvite(gerarConvite());
                aluno.setConvidadoPor(professor);
                aluno.setAtivo(Boolean.TRUE);
                alunoRepository.create(aluno);
            } else if (!Has.content(aluno.getConvite())) {
                aluno.setConvite(gerarConvite());
                aluno.setConvidadoPor(professor);
                alunoRepository.update(aluno);
            }
            alunos.add(aluno);
        }

        Turma turma = new Turma();
        turma.setAlunos(alunos);
        turma.setNome(informarAlunos.getTurma());
        turmaRepository.create(turma);

        Tarefa tarefa = new Tarefa();
        tarefa.setDescricaoAtividade(atividade.getDescricao());
        tarefa.setIdAtividade(atividade.getId());
        tarefa.setMaterialConfigurado(atividade.getMaterialConfigurado());
        tarefa.setTurma(turma);
        tarefa.setProfessor(professor);
        tarefaRepository.create(tarefa);

        EmailAddress emailProfessor = new EmailAddress(professor.getNome(), professor.getEmail());
        StringBuilder html = new StringBuilder();
        html.append("<p>Olá %s.</p>");
        html.append("<p>Informamos que o seu professor acabou de colocar uma nova tarefa para você. Se ainda não sabe como acessar basta seguir o link <strong>%s</strong> .</p>");
        html.append("</br><p><strong>Boa sorte! Equipe Leitoor.</strong></p>");
        String mensagem = html.toString();

        for (Aluno aluno : alunos) {
            TarefaEnviada tarefaEnviada = new TarefaEnviada();
            tarefaEnviada.setAluno(aluno);
            tarefaEnviada.setTarefa(tarefa);
            tarefaEnviadaRepository.create(tarefaEnviada);

            String linkLeitoor = "http://www.leitoor.com.br";
            linkLeitoor += ("?convite=" + aluno.getConvite());

            EmailContent conteudo = new EmailContent("Nova tarefa no Leitoor ;) - " + "Tarefa " + tarefa.getDescricaoAtividade()
                + " - Prof. " + tarefa.getProfessor().getNome(), String.format(mensagem, aluno.getNome(), linkLeitoor));

            emailManager.sendAsynchronously(emailProfessor, conteudo, new EmailAddress(aluno.getNome(), aluno.getEmail()));
        }

        return Result.success(String.format("E-mail enviado com sucesso para %s aluno(s)", alunos.size()));
    }

    @Override
    @Transactional
    public LeituraModel resumoLeitura(Integer idTarefaEnviada) {

        TarefaEnviada tarefaEnviada = tarefaEnviadaRepository.findById(idTarefaEnviada);
        Tarefa tarefa = tarefaEnviada.getTarefa();

        // começa recuperar informações de retorno
        LeituraModel informacaoLeitura = new LeituraModel();
        informacaoLeitura.setAluno(tarefaEnviada.getAluno());
        informacaoLeitura.setTarefa(tarefa);
        informacaoLeitura.setAtividade(atividadeRepository.findById(tarefa.getIdAtividade()));

        return informacaoLeitura;
    }

    @Override
    @Transactional
    public LeituraModel iniciarLeitura(Integer idTarefaEnviada, Integer pagina) {

        if (pagina <= 0) {
            throw new RuntimeException("Página inválida!");
        }
        TarefaEnviada tarefaEnviada = tarefaEnviadaRepository.findById(idTarefaEnviada);
        if (tarefaEnviada == null) {
            return null;
        }

        // recupera informações da tarefa
        Tarefa tarefa = tarefaEnviada.getTarefa();
        MaterialConfigurado materialConfigurado = tarefa.getMaterialConfigurado();
        Material material = materialConfigurado.getMaterial();

        if (pagina > material.getNumeroPaginas()) {
            throw new RuntimeException("Página inválida!");
        }

        // verifica leituras realizadas
        Map<Integer, Leitura> leiturasMap = tarefaEnviada.getLeituras();
        List<Leitura> leiturasAcessiveis = new ArrayList<Leitura>();
        List<Boolean> leiturasCompletas = new ArrayList<Boolean>();
        boolean temIncompleta = false;
        for (int i = 1; i <= material.getNumeroPaginas(); i++) {
            Leitura leitura = leiturasMap.get(i);
            if (leitura != null) {
                leiturasAcessiveis.add(leitura);

                // verifica se a leitura está completa
                boolean completa = leitura.getDataFinal() != null;
                leiturasCompletas.add(completa);
                if (!completa) {
                    temIncompleta = true;
                    break;
                }
            } else {
                leitura = new Leitura();
                leitura.setDataInicial(new Date());
                leitura.setPagina(leiturasAcessiveis.size() + 1);
                leitura.setTarefaEnviada(tarefaEnviada);
                leiturasMap.put(leiturasAcessiveis.size() + 1, leitura);
                leiturasAcessiveis.add(leitura);
                leiturasCompletas.add(false);
            }
        }

        // verifica se a última leitura foi completada, em caso afirmativo
        // inicia uma nova
        if ((!temIncompleta && (pagina == leiturasAcessiveis.size() + 1)) || (pagina == 1 && leiturasAcessiveis.isEmpty())) {
            Leitura leitura = new Leitura();
            leitura.setDataInicial(new Date());
            leitura.setPagina(leiturasAcessiveis.size() + 1);
            leitura.setTarefaEnviada(tarefaEnviada);
            leiturasMap.put(leiturasAcessiveis.size() + 1, leitura);
            leiturasAcessiveis.add(leitura);
            leiturasCompletas.add(false);
        }

        if (pagina > leiturasAcessiveis.size()) {
            throw new RuntimeException("O acesso a esta página não foi permitido! É preciso concluir as atividades anteriores.");
        }

        // já passou o tempo mínimo de leitura
        DateTime inicio = new DateTime(leiturasAcessiveis.get(pagina - 1).getDataInicial());
        boolean tempoMinimoUltrapassado = true;
        int quantidadeQuestoes = 0;
        int tempoMinimoMinutos = 0;
        if (materialConfigurado.getConfiguracaoPaginas().get(pagina) != null) {
            List<Questao> questoes = materialConfigurado.getConfiguracaoPaginas().get(pagina).getQuestoes();
            quantidadeQuestoes = questoes == null ? 0 : questoes.size();
            tempoMinimoMinutos = materialConfigurado.getConfiguracaoPaginas().get(pagina).getMinMinutosPorPagina();
            DateTime dataLimite = inicio.plusMinutes(tempoMinimoMinutos);
            tempoMinimoUltrapassado = dataLimite.isBeforeNow();
        }

        // calcula o período de tempo em que o aluno está lendo
        // o tempo é congelado após a conclusão da atividade
        boolean completa = leiturasCompletas.get(pagina - 1);
        DateTime fim;
        if (completa) {
            fim = new DateTime(leiturasAcessiveis.get(pagina - 1).getDataFinal());
        } else {
            fim = new DateTime();
        }

        // período em hh:m:ss
        Period p = new Period(inicio, fim, PeriodType.seconds());
        Integer tempoDecorrido = p.getSeconds();

        // páginas acessíveis
        int paginasAcessiveis = leiturasAcessiveis.size();

        // começa recuperar informações de retorno
        LeituraModel informacaoLeitura = new LeituraModel();
        informacaoLeitura.setAluno(tarefaEnviada.getAluno());
        informacaoLeitura.setLeitura(leiturasAcessiveis.get(pagina - 1));
        informacaoLeitura.setPaginaAtual(pagina);
        informacaoLeitura.setPaginasAcessiveis(paginasAcessiveis);
        informacaoLeitura.setArquivoPagina(material.getArquivoPaginado().get(pagina));
        informacaoLeitura.setTempoMinimoUltrapassado(tempoMinimoUltrapassado);
        informacaoLeitura.setCompleta(completa);
        informacaoLeitura.setAtividade(atividadeRepository.findById(tarefa.getIdAtividade()));
        informacaoLeitura.setTarefa(tarefa);
        informacaoLeitura.setTempoDecorrido(tempoDecorrido);
        informacaoLeitura.setTempoMinimo(tempoMinimoMinutos * 60);
        informacaoLeitura.setQuantidadeQuestoes(quantidadeQuestoes);
        informacaoLeitura.setQuantidadePaginas(material.getNumeroPaginas());

        registrarAcesso(tarefaEnviada);

        return informacaoLeitura;
    }

    @Override
    @Transactional
    public boolean avancarLeitura(Integer idTarefaEnviada, Integer pagina) {

        if (pagina <= 0) {
            throw new RuntimeException("Página inválida!");
        }

        TarefaEnviada tarefaEnviada = tarefaEnviadaRepository.findById(idTarefaEnviada);

        // recupera informações da tarefa
        Tarefa tarefa = tarefaEnviada.getTarefa();
        MaterialConfigurado materialConfigurado = tarefa.getMaterialConfigurado();
        Material material = materialConfigurado.getMaterial();

        if (pagina > material.getNumeroPaginas()) {
            throw new RuntimeException("Página inválida!");
        }

        Map<Integer, Leitura> leiturasMap = tarefaEnviada.getLeituras();
        Leitura leitura = leiturasMap.get(pagina);
        if (leitura == null) {
            throw new RuntimeException("Página inválida!");
        }

        if (leitura.getDataFinal() == null) {
            boolean completa = leituraCompleta(leitura, tarefaEnviada, materialConfigurado.getConfiguracaoPaginas().get(pagina));
            if (completa) {
                leitura.setDataFinal(new Date());
                if (pagina == material.getNumeroPaginas()) {
                    return true;
                }
            } else {
                throw new RuntimeException("A tarefa anterior não está completa!");
            }
        } else {
            if (pagina == material.getNumeroPaginas()) {
                return true;
            }
        }

        registrarAcesso(tarefaEnviada);
        return false;

    }

    private boolean leituraCompleta(
        Leitura leitura,
        TarefaEnviada tarefaEnviada,
        ConfiguracaoPagina configuracaoPagina) {

        // verifica se a leitura está completa
        boolean completa = false;
        if (leitura.getDataFinal() == null) {
            boolean respostasPreenchidas = true;
            if (configuracaoPagina != null) {
                List<Questao> questoes = configuracaoPagina.getQuestoes();
                List<Resposta> respostas = tarefaEnviada.getRespostas();
                if (questoes != null) {
                    Map<Integer, Resposta> mapaRespostas = respostasPorQuestao(questoes, respostas);
                    for (Questao questao : questoes) {
                        Resposta resposta = mapaRespostas.get(questao.getId());
                        if (resposta instanceof RespostaDissertativa) {
                            RespostaDissertativa respostaDissertativa = (RespostaDissertativa) resposta;
                            if (respostaDissertativa.getReposta() == null || respostaDissertativa.getReposta().length() == 0) {
                                respostasPreenchidas = false;
                                break;
                            }
                        }
                    }
                }
            }
            if (respostasPreenchidas) {
                completa = true;
                // leitura.setDataFinal(new Date());
            }

        } else {
            completa = true;
        }
        return completa;

    }

    private void registrarAcesso(TarefaEnviada tarefaEnviada) {
        // marca acesso atual
        List<Date> acessos = tarefaEnviada.getAcessos();
        if (acessos == null) {
            acessos = new ArrayList<Date>();
            tarefaEnviada.setAcessos(acessos);
        }
        acessos.add(new Date());
    }

    private String gerarConvite() {
        return UUID.randomUUID().toString();
    }

    @Override
    @Transactional
    public QuestionarioModel recuperarQuestionario(
        Integer idTarefaEnviada, Integer pagina) {

        if (pagina <= 0) {
            throw new RuntimeException("Página inválida!");
        }

        TarefaEnviada tarefaEnviada = tarefaEnviadaRepository.findById(idTarefaEnviada);

        // recupera informações da tarefa
        Tarefa tarefa = tarefaEnviada.getTarefa();
        MaterialConfigurado materialConfigurado = tarefa.getMaterialConfigurado();
        Material material = materialConfigurado.getMaterial();

        if (pagina > material.getNumeroPaginas()) {
            throw new RuntimeException("Página inválida!");
        }

        // recupera questões
        ConfiguracaoPagina configuracaoPagina = materialConfigurado.getConfiguracaoPaginas().get(pagina);
        if (configuracaoPagina == null) {
            return null;
        }

        // verifica se possui questões
        List<Questao> questoes = configuracaoPagina.getQuestoes();
        if (questoes == null || questoes.size() == 0) {
            return null;
        }

        // inicia o model com as questões
        QuestionarioModel mqm = new QuestionarioModel();
        mqm.setQuestoes(questoes);

        // recupera respostas, se houver
        List<Resposta> respostas = tarefaEnviada.getRespostas();

        // resposta por questao
        Map<Integer, Resposta> mapaRespostas = respostasPorQuestao(questoes, respostas);

        // cria lista de respostas
        List<Resposta> respostasModel = new ArrayList<Resposta>();
        for (Questao questao : questoes) {
            Resposta resposta = mapaRespostas.get(questao.getId());
            if (resposta == null) {
                resposta = new RespostaDissertativa();
                resposta.setQuestao(questao);
            }
            respostasModel.add(resposta);
        }
        mqm.setRespostas(respostasModel);
        return mqm;

    }

    @Override
    @Transactional
    public boolean gravarQuestionario(
        Integer idTarefaEnviada,
        Integer pagina,
        ResponderQuestionarioModel respostasEnviadas) {

        if (pagina <= 0) {
            throw new RuntimeException("Página inválida!");
        }

        TarefaEnviada tarefaEnviada = tarefaEnviadaRepository.findById(idTarefaEnviada);

        // recupera informações da tarefa
        Tarefa tarefa = tarefaEnviada.getTarefa();
        MaterialConfigurado materialConfigurado = tarefa.getMaterialConfigurado();
        Material material = materialConfigurado.getMaterial();

        if (pagina > material.getNumeroPaginas()) {
            throw new RuntimeException("Página inválida!");
        }

        // recupera questões
        ConfiguracaoPagina configuracaoPagina = materialConfigurado.getConfiguracaoPaginas().get(pagina);
        if (configuracaoPagina == null) {
            throw new RuntimeException("Não há configuração para a página!");
        }

        // verifica se possui questões
        List<Questao> questoes = configuracaoPagina.getQuestoes();
        if (questoes == null || questoes.size() == 0) {
            throw new RuntimeException("Não há questões!");
        }

        // recupera respostas, se houver
        List<Resposta> respostas = tarefaEnviada.getRespostas();

        // resposta por questao
        Map<Integer, Resposta> mapaRespostas = respostasPorQuestao(questoes, respostas);

        // cria lista de respostas
        for (ItemResposta respostaEnviada : respostasEnviadas.getRespostasDissertativas()) {

            Resposta original = mapaRespostas.get(respostaEnviada.getIdQuestao());
            if (original != null) {
                ((RespostaDissertativa) original).setReposta(respostaEnviada.getResposta());
            } else {
                RespostaDissertativa respostaDissertativa = new RespostaDissertativa();
                respostaDissertativa.setTarefaEnviada(tarefaEnviada);
                respostaDissertativa.setReposta(respostaEnviada.getResposta());
                for (Questao questao : questoes) {
                    if (questao.getId().equals(respostaEnviada.getIdQuestao())) {
                        respostaDissertativa.setQuestao(questao);
                        break;
                    }
                }
                respostas.add(respostaDissertativa);
            }

        }

        // retorna boolean para dizer se pode ou não avançar
        Map<Integer, Leitura> leiturasMap = tarefaEnviada.getLeituras();
        Leitura leitura = leiturasMap.get(pagina);
        if (leitura == null) {
            throw new RuntimeException("Página inválida!");
        }

        // tenta avançar de página caso clicou em enviar
        if (leitura.getDataFinal() == null && respostasEnviadas.getEnviar()) {
            avancarLeitura(idTarefaEnviada, pagina);
        }

        return leitura.getDataFinal() != null && leituraCompleta(leitura, tarefaEnviada, configuracaoPagina);

    }

    private Map<Integer, Resposta> respostasPorQuestao(List<Questao> questoes, List<Resposta> respostas) {
        // resposta por questao
        Map<Integer, Resposta> mapaRespostas = new HashMap<Integer, Resposta>();

        if (respostas != null) {
            for (Resposta resposta : respostas) {
                mapaRespostas.put(resposta.getQuestao().getId(), resposta);
            }
        }
        return mapaRespostas;
    }

    @Override
    public List<QuestionarioModel> getInformacoesRespostas(Integer idTarefaEnviada) {

        // recupera informações da tarefa
        TarefaEnviada tarefaEnviada = tarefaEnviadaRepository.findById(idTarefaEnviada);
        Tarefa tarefa = tarefaEnviada.getTarefa();
        MaterialConfigurado materialConfigurado = tarefa.getMaterialConfigurado();
        Material material = materialConfigurado.getMaterial();

        // pega questões de cada página já lida
        int paginas = material.getNumeroPaginas();
        List<QuestionarioModel> questionario = new ArrayList<QuestionarioModel>();
        Map<Integer, Leitura> leiturasMap = tarefaEnviada.getLeituras();
        for (int i = 1; i <= paginas; i++) {
            Leitura leitura = leiturasMap.get(i);
            if (leitura != null) {
                QuestionarioModel qm = recuperarQuestionario(idTarefaEnviada, i);
                questionario.add(qm);
            }
        }
        return questionario;

    }

    @Override
    public DetalharLeituraAlunoModel getInformacoesDetalheLeitura(Integer idTarefaEnviada) {

        TarefaEnviada tarefaEnviada = tarefaEnviadaRepository.findById(idTarefaEnviada);

        // recupera informações da tarefa
        Tarefa tarefa = tarefaEnviada.getTarefa();
        MaterialConfigurado materialConfigurado = tarefa.getMaterialConfigurado();
        Material material = materialConfigurado.getMaterial();

        // calcular desempenho
        int desempenhoAluno = 0;
        int desempenhoMedio = 0;
        int melhorDesempenho = 0;
        int melhorPontuacao = 0;

        // resumo das leituras
        int paginas = material.getNumeroPaginas();
        List<Leitura> leituras = new ArrayList<Leitura>();
        Map<Integer, Leitura> leiturasMap = tarefaEnviada.getLeituras();
        for (int i = 1; i <= paginas; i++) {
            Leitura leitura = leiturasMap.get(i);
            if (leitura == null) {
                leitura = new Leitura();
                leitura.setPagina(i);
            }
            leituras.add(leitura);
        }

        // desempenho dos alunos
        List<TarefaEnviada> tarefaEnviadaList = tarefaEnviadaRepository.findByTarefa(tarefa);
        int somaLeituras = 0;
        DesempenhoAlunoModel melhorLeitura = new DesempenhoAlunoModel();
        DesempenhoAlunoModel melhorPontuacaoModel = new DesempenhoAlunoModel();
        DesempenhoAlunoModel aluno = new DesempenhoAlunoModel();
        DesempenhoAlunoModel media = new DesempenhoAlunoModel();
        List<Date> todosJuntosLeitura = new ArrayList<Date>();
        List<Date> acessosAluno = new ArrayList<Date>();
        List<Date> acessosMelhor = new ArrayList<Date>();
        Map<Date, Long> dadosTempoTodos = new TreeMap<Date, Long>();
        for (TarefaEnviada tarefaEnviadaItem : tarefaEnviadaList) {
            DesempenhoAlunoModel d = getDesempenhoAluno(tarefaEnviadaItem);
            somaLeituras += d.getPorcentagemConcluida();
            media.setAcertos(media.getAcertos() + d.getAcertos());
            media.setErros(media.getErros() + d.getErros());
            media.setPontos(media.getPontos() + d.getPontos());
            if (tarefaEnviadaItem.getId().equals(tarefaEnviada.getId())) {
                desempenhoAluno = d.getPorcentagemConcluida();
                aluno = d;
                acessosAluno = tarefaEnviadaItem.getAcessos();
            }
            if (d.getPorcentagemConcluida() > melhorDesempenho) {
                melhorDesempenho = d.getPorcentagemConcluida();
                melhorLeitura = d;
            }
            if (d.getAcertos() > melhorPontuacao) {
                melhorPontuacao = d.getAcertos();
                melhorPontuacaoModel = d;
                acessosMelhor = tarefaEnviadaItem.getAcessos();
            }
            todosJuntosLeitura.addAll(d.getLeiturasConcluidas());
            Map<Date, Long> dadosTempoAtual = getTempoLeituraPorDia(tarefaEnviadaItem.getAcessos());
            for (Date data : dadosTempoAtual.keySet()) {
                Long v = dadosTempoTodos.get(data);
                if (v == null) v = 0l;
                v += dadosTempoAtual.get(data);
                dadosTempoTodos.put(data, v);
            }

        }
        desempenhoMedio = somaLeituras / tarefaEnviadaList.size();
        media.setAcertos(media.getAcertos() / tarefaEnviadaList.size());
        media.setErros(media.getErros() / tarefaEnviadaList.size());
        media.setPontos(media.getPontos() / tarefaEnviadaList.size());

        DetalharLeituraAlunoModel detalhes = new DetalharLeituraAlunoModel();

        // dados para gráficos
        Map<Date, Double> dadosAluno = getDadosLeituraPorData(aluno.getLeiturasConcluidas(), paginas);
        Map<Date, Double> dadosMelhor = getDadosLeituraPorData(melhorLeitura.getLeiturasConcluidas(), paginas);
        Map<Date, Double> dadosTodos = getDadosLeituraPorData(todosJuntosLeitura, paginas * tarefaEnviadaList.size());
        detalhes.setDadosGrafico(sintetizar(dadosMelhor, dadosTodos, dadosAluno));

        // gráfico de tempo de leitura
        Map<Date, Long> dadosTempoAluno = getTempoLeituraPorDia(acessosAluno);
        Map<Date, Long> dadosTempoMelhor = getTempoLeituraPorDia(acessosMelhor);

        detalhes.setDadosGraficoPorDia(sintetizarPorDia(dadosTempoMelhor, dadosTempoTodos, dadosTempoAluno, tarefaEnviadaList.size()));

        detalhes.setAluno(tarefaEnviada.getAluno());
        detalhes.setAtividade(atividadeRepository.findById(tarefa.getIdAtividade()));
        detalhes.setTarefa(tarefa);
        detalhes.setDesempenhoAluno(desempenhoAluno);
        detalhes.setDesempenhoMedio(desempenhoMedio);
        detalhes.setMelhorDesempenho(melhorDesempenho);
        detalhes.setLeituras(leituras);
        detalhes.setQuantidadePaginas(paginas);
        detalhes.setMelhorPontuacao(melhorPontuacaoModel);
        detalhes.setAlunoPontuacao(aluno);
        detalhes.setMediaPontuacao(media);
        return detalhes;

    }

    private Map<Date, Map<Long, Double>> sintetizar(
        Map<Date, Double> dadosMelhor,
        Map<Date, Double> dadosTodos,
        Map<Date, Double> dadosAluno) {

        Map<Date, Map<Long, Double>> tudo = new TreeMap<Date, Map<Long, Double>>();
        Double aluno = 0.0;
        Double melhor = 0.0;
        if (dadosTodos != null) {
            for (Date data : dadosTodos.keySet()) {
                Map<Long, Double> item = new TreeMap<Long, Double>();
                if (dadosAluno.containsKey(data)) {
                    aluno = dadosAluno.get(data);
                }
                if (dadosMelhor.containsKey(data)) {
                    melhor = dadosMelhor.get(data);
                }
                item.put(1l, melhor);
                item.put(2l, dadosTodos.get(data));
                item.put(3l, aluno);
                tudo.put(data, item);
            }
        }
        return tudo;

    }

    private Map<Date, Map<Long, Long>> sintetizarPorDia(
        Map<Date, Long> dadosMelhor,
        Map<Date, Long> dadosTodos,
        Map<Date, Long> dadosAluno,
        int quantidadeAlunos) {

        Map<Date, Map<Long, Long>> tudo = new TreeMap<Date, Map<Long, Long>>();
        if (dadosTodos != null && !dadosTodos.isEmpty()) {

            // intervalo de datas
            List<Date> datas = new ArrayList<Date>(dadosTodos.keySet());
            DateTime inicio = new DateTime(datas.get(0));
            DateTime fim = new DateTime(datas.get(datas.size() - 1));

            // colcoa todas as datas
            while (!inicio.toDateMidnight().isAfter(fim.toDateMidnight())) {
                Map<Long, Long> item = new TreeMap<Long, Long>();
                item.put(1l, 0l);
                item.put(2l, 0l);
                item.put(3l, 0l);
                tudo.put(inicio.toDateMidnight().toDate(), item);
                inicio = inicio.plusDays(1);
            }

            // coloca valor em cada data
            for (Date data : datas) {

                // Map<Long, Long> item = tudo.get(getDataSemHora(data));
                Map<Long, Long> item = tudo.get(data);
                if (item == null) {
                    System.out.println("olá");
                }

                if (dadosMelhor.containsKey(data)) {
                    item.put(1l, item.get(1l) + dadosMelhor.get(data));
                }
                if (dadosTodos.containsKey(data)) {
                    item.put(2l, item.get(2l) + dadosTodos.get(data));
                }
                // item.put(4l, item.get(4l) + 1);
                if (dadosAluno.containsKey(data)) {
                    item.put(3l, item.get(3l) + dadosAluno.get(data));
                }

            }

            for (Date data : tudo.keySet()) {
                Map<Long, Long> item = tudo.get(data);
                // item.put(2l, item.get(2l) / item.get(4l));
                if (dadosTodos.containsKey(data)) {
                    item.put(2l, item.get(2l) / quantidadeAlunos);
                }
            }

            // mantém apenas o útimo mês até a última data
            DateTime limiteSuperior = fim; // new DateTime();
            DateTime limiteInferior = limiteSuperior.minusDays(10);
            // DateTime limiteInferior = limiteSuperior.minusMonths(1);
            datas = new ArrayList<Date>(tudo.keySet());
            for (Date data : datas) {
                if (data.before(limiteInferior.toDate())
                    || data.after(limiteSuperior.toDate())) {
                    tudo.remove(data);
                }
            }

        }

        return tudo;

    }

    private Map<Date, Double> getDadosLeituraPorData(List<Date> datas, Integer total) {
        Map<Date, Double> dl = new TreeMap<Date, Double>();
        if (datas != null) {
            Collections.sort(datas);
            Double accumulator = 1.0;
            for (Date data : datas) {
                dl.put(data, 100.0 * accumulator / total);
                accumulator++;
            }
        }
        return dl;
    }

    private Map<Date, Long> getTempoLeituraPorDia(List<Date> datas) {
        Map<Date, Long> dl = new TreeMap<Date, Long>();
        if (datas != null && !datas.isEmpty()) {
            Collections.sort(datas);
            long tempo = 10; // 10 minutos para cada acesso
            DateTime anterior = new DateTime(datas.get(0).getTime());
            for (int i = 1; i < datas.size(); i++) {
                DateTime atual = new DateTime(datas.get(i).getTime());
                if (!atual.toDateMidnight().equals(anterior.toDateMidnight())
                    || Minutes.minutesBetween(anterior, atual).getMinutes() > 10) {
                    dl.put(atual.toDateMidnight().toDate(), tempo);
                    tempo = 10;
                } else {
                    tempo += Minutes.minutesBetween(anterior, atual).getMinutes();
                }
                anterior = atual;
                if (i == datas.size() - 1) {
                    dl.put(atual.toDateMidnight().toDate(), tempo);
                }
            }
        }
        return dl;
    }

    private DesempenhoAlunoModel getDesempenhoAluno(TarefaEnviada tarefaEnviada) {

        // recupera informações da tarefa
        Tarefa tarefa = tarefaEnviada.getTarefa();

        MaterialConfigurado materialConfigurado = tarefa.getMaterialConfigurado();
        Material material = materialConfigurado.getMaterial();

        // resumo das leituras
        int paginasLidas = 0;
        int paginas = material.getNumeroPaginas();
        List<Date> leituras = new ArrayList<Date>();
        Map<Integer, Leitura> leiturasMap = tarefaEnviada.getLeituras();
        for (int i = 1; i <= paginas; i++) {
            Leitura leitura = leiturasMap.get(i);
            if (leitura != null && leitura.getDataFinal() != null) {
                paginasLidas++;
                leituras.add(leitura.getDataFinal());
            }
        }

        // verifica acertos e erros
        int pontos = 0;
        int erros = 0;
        int acertos = 0;
        List<Resposta> respostas = tarefaEnviada.getRespostas();
        if (respostas != null) {
            for (Resposta resposta : respostas) {
                if (Avaliacao.CORRETO.equals(resposta.getAvaliacao())) {
                    acertos++;
                    pontos++; // TODO somar pontos
                }
                if (Avaliacao.INCORRETO.equals(resposta.getAvaliacao())) {
                    erros++;
                }
            }
        }

        DesempenhoAlunoModel d = new DesempenhoAlunoModel();
        d.setLeiturasConcluidas(leituras);
        d.setQuantidadePaginas(paginas);
        d.setPorcentagemConcluida(100 * paginasLidas / paginas);
        d.setPontos(pontos);
        d.setErros(erros);
        d.setAcertos(acertos);
        return d;

    }

    @Override
    public AvaliacaoQuestionarioModel buscarAvaliacaoQuestionario(Integer idTarefaEnviada) {
        TarefaEnviada tarefaEnviada = tarefaEnviadaRepository.findById(idTarefaEnviada);

        AvaliacaoQuestionarioModel avaliacao = new AvaliacaoQuestionarioModel();
        avaliacao.setAluno(tarefaEnviada.getAluno().getNome());
        avaliacao.setAtividade(tarefaEnviada.getTarefa().getDescricaoAtividade());
        avaliacao.setIdTarefaEnviada(idTarefaEnviada);
        avaliacao.setMaterial(tarefaEnviada.getTarefa().getMaterialConfigurado().getMaterial().getTitulo());
        avaliacao.setTurma(tarefaEnviada.getTarefa().getTurma().getNome());
        avaliacao.setRespostas(new ArrayList<AvaliacaoQuestionarioModel.RespostaModel>());
        avaliacao.setIdTarefa(tarefaEnviada.getTarefa().getId());

        if (!tarefaEnviada.getRespostas().isEmpty()) {
            for (Resposta resposta : tarefaEnviada.getRespostas()) {
                AvaliacaoQuestionarioModel.RespostaModel respostaModel = new RespostaModel();
                respostaModel.setAvaliacao(resposta.getAvaliacao());
                respostaModel.setId(resposta.getId());
                respostaModel.setQuestao(resposta.getQuestao().getDescricao());
                respostaModel.setResposta(resposta.getAlternativaSelecionada());
                avaliacao.getRespostas().add(respostaModel);
            }
        }

        return avaliacao;
    }

    @Override
    @Transactional
    public Result<Void> salvarAvaliacaoQuestionario(AvaliacaoQuestionarioModel avaliacao) {
        TarefaEnviada tarefa = tarefaEnviadaRepository.findById(avaliacao.getIdTarefaEnviada());
        if (!avaliacao.getRespostas().isEmpty()) {
            Map<Integer, Resposta> respostas = new HashMap<Integer, Resposta>();
            for (Resposta resposta : tarefa.getRespostas()) {
                respostas.put(resposta.getId(), resposta);
            }

            for (AvaliacaoQuestionarioModel.RespostaModel respostaModel : avaliacao.getRespostas()) {
                respostas.get(respostaModel.getId()).setAvaliacao(respostaModel.getAvaliacao());
            }
        }
        tarefaEnviadaRepository.update(tarefa);

        return Result.success("Avaliações salvas com sucesso!");
    }

}
