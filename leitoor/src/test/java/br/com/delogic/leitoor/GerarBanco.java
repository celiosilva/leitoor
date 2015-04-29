package br.com.delogic.leitoor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.log4j.Level;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import br.com.delogic.jfunk.Each;
import br.com.delogic.jfunk.ForEach;
import br.com.delogic.jnerator.AttributeConfiguration;
import br.com.delogic.jnerator.AttributeGenerator;
import br.com.delogic.jnerator.InstanceGenerator;
import br.com.delogic.jnerator.JNerator;
import br.com.delogic.jnerator.JNeratorImpl;
import br.com.delogic.jnerator.impl.generator.ProvidedAttributeGenerator;
import br.com.delogic.leitoor.entidade.Aluno;
import br.com.delogic.leitoor.entidade.Atividade;
import br.com.delogic.leitoor.entidade.ConfiguracaoPagina;
import br.com.delogic.leitoor.entidade.Leitura;
import br.com.delogic.leitoor.entidade.Material;
import br.com.delogic.leitoor.entidade.MaterialConfigurado;
import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.entidade.Questao;
import br.com.delogic.leitoor.entidade.QuestaoDissertativa;
import br.com.delogic.leitoor.entidade.QuestaoObjetiva;
import br.com.delogic.leitoor.entidade.QuestaoObjetivaAlternativa;
import br.com.delogic.leitoor.entidade.Questionario;
import br.com.delogic.leitoor.entidade.Resposta;
import br.com.delogic.leitoor.entidade.RespostaDissertativa;
import br.com.delogic.leitoor.entidade.RespostaObjetiva;
import br.com.delogic.leitoor.entidade.RespostasQuestionario;
import br.com.delogic.leitoor.entidade.Tarefa;
import br.com.delogic.leitoor.entidade.TarefaEnviada;
import br.com.delogic.leitoor.entidade.Turma;
import br.com.delogic.leitoor.util.Log4jManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    locations = { "classpath:database/gerar-banco.xml" })
public class GerarBanco {

    @PersistenceContext
    private EntityManager         entityManager;

    @Inject
    @Named("sqlDS")
    private DataSource            dataSource;

    @Inject
    private JpaTransactionManager transactionManager;

    @SuppressWarnings("serial")
    class Contos extends HashMap<Integer, String> {
        {
            for (int p = 1; p <= 49; p++) {
                put(p, "contos" + p + ".pdf");
            }
        }
    }

    private TransactionStatus  transactionStatus;

    private JNerator           jNerator            = new JNeratorImpl();

    AttributeGenerator<String> emailGenerator      = new AttributeGenerator<String>() {
                                                       private int contador = 1;

                                                       @Override
                                                       public String generate(int index, AttributeConfiguration attributeConfiguration,
                                                           Object instance) {
                                                           return "email" + (contador++) + "@gmail.com";
                                                       }
                                                   };

    AttributeGenerator<String> conviteGenerator    = new AttributeGenerator<String>() {
                                                       private int contador = 1;

                                                       @Override
                                                       public String generate(int index, AttributeConfiguration attributeConfiguration,
                                                           Object instance) {
                                                           return "convite" + (contador++);
                                                       }
                                                   };

    AttributeGenerator<String> idFacebookGenerator = new AttributeGenerator<String>() {
                                                       private int contador = 1;

                                                       @Override
                                                       public String generate(int index, AttributeConfiguration attributeConfiguration,
                                                           Object instance) {
                                                           return "idfacebook" + (contador++);
                                                       }
                                                   };

    protected void beginTransaction() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    protected void commitTransaction() {
        if (transactionManager == null || transactionStatus == null) throw new IllegalStateException(
            "A transação não foi aberta e provavelmente o begin() não foi chamado");

        transactionManager.commit(transactionStatus);
    }

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GerarBanco.class);

    static {
        /* desligar o log para melhorar performance */
        Log4jManager.setLevel(Level.INFO);
    }

    private <E> AttributeGenerator<E> of(E... elements) {
        return new ProvidedAttributeGenerator<E>(elements);
    }

    @SuppressWarnings("unused")
    private <E> AttributeGenerator<E> of(List<E> elements) {
        return new ProvidedAttributeGenerator<E>(elements);
    }

    private Random random = new Random();

    @Test
    public void popular() throws ScriptException, SQLException {

        beginTransaction();

        jNerator.doNotGenerateFor("id", "convidadoPor");

        persist(jNerator.prepare(Aluno.class)
            .setAttributeGenerator("email", emailGenerator)
            .setAttributeGenerator("convite", conviteGenerator)
            .setAttributeGenerator("idFacebook", idFacebookGenerator)
            .setAttributeGenerator("idGoogle", idFacebookGenerator)
            .generate(50));

        persist(jNerator.prepare(Professor.class)
            .setAttributeGenerator("email", emailGenerator)
            .setAttributeGenerator("convite", conviteGenerator)
            .setAttributeGenerator("idFacebook", idFacebookGenerator)
            .setAttributeGenerator("idGoogle", idFacebookGenerator)
            .generate(1));

        persist(jNerator.prepare(Material.class)
            .setAttributeGenerator("numeroPaginas", of(49))
            .setAttributeGenerator("arquivoUnico", of("contos.pdf"))
            .setAttributeGenerator("arquivoPaginado", of(new Contos()))
            .generate(10));

        persist(jNerator.prepare(MaterialConfigurado.class)
            .setAttributeGenerator("configuracaoPaginas", new AttributeGenerator<Map<Integer, ConfiguracaoPagina>>() {
                @Override
                public Map<Integer, ConfiguracaoPagina> generate(int index, AttributeConfiguration attributeConfiguration,
                    Object materialConfigurado) {
                    InstanceGenerator<ConfiguracaoPagina> generator = jNerator.prepare(ConfiguracaoPagina.class);
                    generator.setAttributeGenerator("questoes", new AttributeGenerator<List<? extends Questao>>() {
                        @Override
                        public List<? extends Questao> generate(int index, AttributeConfiguration attributeConfiguration, Object instance) {
                            if (random.nextBoolean()) {
                                return jNerator.prepare(QuestaoObjetiva.class)
                                    .setAttributeGenerator("alternativas", new AttributeGenerator<List<QuestaoObjetivaAlternativa>>() {
                                        @Override
                                        public List<QuestaoObjetivaAlternativa> generate(int index,
                                            AttributeConfiguration attributeConfiguration, Object instance) {
                                            return
                                            jNerator
                                                .prepare(QuestaoObjetivaAlternativa.class)
                                                .setAttributeGenerator("questao", of(instance))
                                                .generate(5);
                                        }
                                    }).generate(3);
                            } else {
                                return jNerator.prepare(QuestaoDissertativa.class).generate(3);
                            }
                        }
                    });
                    generator.setAttributeGenerator("materialConfigurado", of(materialConfigurado));
                    Map<Integer, ConfiguracaoPagina> paginas = new HashMap<Integer, ConfiguracaoPagina>();
                    int p = 1;
                    for (ConfiguracaoPagina configuracaoPagina : generator.generate(new Contos().size())) {
                        configuracaoPagina.setPagina(p++);
                        configuracaoPagina.setMaterialConfigurado((MaterialConfigurado) materialConfigurado);
                        paginas.put(configuracaoPagina.getPagina(), configuracaoPagina);
                    }
                    return paginas;
                }
            }).generate(80));

        persist(jNerator.prepare(Atividade.class).generate(30));

        persist(jNerator.prepare(Turma.class).generate(15));

        final List<Tarefa> tarefas = persist(jNerator.prepare(Tarefa.class).generate(100));

        List<TarefaEnviada> tarefasIndividuais = jNerator.prepare(TarefaEnviada.class)
            .setAttributeGenerator("tarefa", new AttributeGenerator<Tarefa>() {
                @Override
                public Tarefa generate(int index, AttributeConfiguration attributeConfiguration, Object instance) {
                    return tarefas.get(index % 100);
                }
            })
            .setAttributeGenerator("respostas", new AttributeGenerator<List<? extends Resposta>>() {
                @Override
                public List<? extends Resposta> generate(int index, AttributeConfiguration attributeConfiguration, Object instance) {
                    final List<Resposta> respostas = new ArrayList<Resposta>();
                    for (final ConfiguracaoPagina conf : tarefas.get(index % 100).getMaterialConfigurado().getConfiguracaoPaginas()
                        .values()) {
                        // sair a cada três falses
                        if (!(random.nextBoolean() || random.nextBoolean() || random.nextBoolean())) {
                            break;
                        }
                        if (conf.getQuestoes() != null) {
                            for (final Questao questao : conf.getQuestoes()) {
                                if (questao instanceof QuestaoDissertativa) {
                                    respostas.add(
                                        jNerator.prepare(RespostaDissertativa.class)
                                            .setAttributeGenerator("tarefaEnviada", of(instance))
                                            .setAttributeGenerator("questao", of(questao))
                                            .generate(1).get(0));
                                } else {
                                    respostas.addAll(
                                        jNerator
                                            .prepare(RespostaObjetiva.class)
                                            .setAttributeGenerator("tarefaEnviada", of(instance))
                                            .setAttributeGenerator("questao", of(questao))
                                            .setAttributeGenerator("alternativasSelecionadas",
                                                new AttributeGenerator<Set<QuestaoObjetivaAlternativa>>() {
                                                    @Override
                                                    public Set<QuestaoObjetivaAlternativa> generate(int index,
                                                        AttributeConfiguration attributeConfiguration, Object instance) {
                                                        return new HashSet<QuestaoObjetivaAlternativa>(((QuestaoObjetiva) questao)
                                                            .getAlternativas().subList(0, 2));
                                                    }
                                                })
                                            .generate(2));
                                }

                            }
                        }

                    }
                    return respostas;
                }
            })
            .setAttributeGenerator("leituras", new AttributeGenerator<Map<Integer, Leitura>>() {
                @Override
                public Map<Integer, Leitura> generate(int index, AttributeConfiguration attributeConfiguration, Object instance) {
                    int paginas = tarefas.get(index % 100).getMaterialConfigurado().getMaterial().getArquivoPaginado().size();
                    Map<Integer, Leitura> leituras = new HashMap<Integer, Leitura>();
                    Calendar cal = Calendar.getInstance();
                    for (int pagina = 1; pagina <= paginas; pagina++) {
                        // sair a cada três falses
                        if (!(random.nextBoolean() || random.nextBoolean() || random.nextBoolean())) {
                            break;
                        }
                        Leitura leitura = new Leitura();
                        cal.add(Calendar.MINUTE, pagina);
                        leitura.setDataInicial(cal.getTime());
                        cal.add(Calendar.MINUTE, pagina);
                        leitura.setDataFinal(cal.getTime());
                        leitura.setPagina(pagina);
                        leitura.setTarefaEnviada((TarefaEnviada) instance);
                        leituras.put(pagina, leitura);
                    }
                    return leituras;
                }
            })
            .generate(200);

        persist(tarefasIndividuais);

        persist(jNerator.prepare(Questionario.class)
            .setAttributeGenerator("questoes", new AttributeGenerator<List<? extends Questao>>() {
                @Override
                public List<? extends Questao> generate(int index, AttributeConfiguration attributeConfiguration, Object instance) {
                    return jNerator.prepare(QuestaoDissertativa.class).generate(20);
                }
            })
            .generate(100));

        persist(jNerator.prepare(RespostasQuestionario.class)
            .setAttributeGenerator("respostas", new AttributeGenerator<List<? extends Resposta>>() {
                @Override
                public List<? extends Resposta> generate(int index, AttributeConfiguration attributeConfiguration, Object instance) {
                    return jNerator.prepare(RespostaDissertativa.class).generate(20);
                }
            }).generate(1000));

        commitTransaction();

    }

    private <E, C extends Collection<E>, T> C persist(C entidades) {
        Class<?> tipo = entidades.iterator().next().getClass();
        logger.info("persistindo " + tipo);
        ForEach.element(entidades, new Each<E>() {
            @Override
            public void each(E object, int arg1) {
                entityManager.persist(object);
            }
        });
        entityManager.flush();

        return entidades;
    }

}
