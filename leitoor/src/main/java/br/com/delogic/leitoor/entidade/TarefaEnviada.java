package br.com.delogic.leitoor.entidade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.delogic.leitoor.entidade.mapped.DominioAluno;

@Entity
public class TarefaEnviada extends DominioAluno<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tarefa_individual")
    @SequenceGenerator(name = "seq_tarefa_individual", sequenceName = "seq_tarefa_individual", allocationSize = 1, initialValue = 1)
    private Integer               id;

    @ManyToOne
    private Tarefa                tarefa;

    @OneToMany(mappedBy = "tarefaEnviada", cascade = CascadeType.ALL)
    @MapKey(name = "pagina")
    private Map<Integer, Leitura> leituras;

    @OneToMany(mappedBy = "tarefaEnviada", cascade = CascadeType.ALL)
    @OrderBy("id")
    private List<Resposta>        respostas;

    @ElementCollection
    @Column(name = "dataAcesso")
    @CollectionTable(name = "TAREFAENVIADAACESSO")
    @Temporal(TemporalType.TIMESTAMP)
    private List<Date>            acessos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<Integer, Leitura> getLeituras() {
        return leituras;
    }

    public void setLeituras(Map<Integer, Leitura> leituras) {
        this.leituras = leituras;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public List<Date> getAcessos() {
        return acessos;
    }

    public void setAcessos(List<Date> acessos) {
        this.acessos = acessos;
    }

    @Deprecated
    public int getPorcentagemConcluida() {
        Map<Integer, Leitura> leituras = getLeituras();
        if (leituras == null) return 0;
        int total = tarefa.getMaterialConfigurado().getMaterial().getNumeroPaginas();
        int lidas = 0;
        for (Leitura leitura : leituras.values()) {
            if (leitura != null && leitura.getDataFinal() != null) {
                lidas++;
            }
        }
        return 100 * lidas / total;
    }

    @Deprecated
    public int getUltimaPagina() {
        Map<Integer, Leitura> leituras = getLeituras();
        if (leituras == null) return 0;
        int pagina = 0;
        for (Leitura leitura : leituras.values()) {
            if (leitura != null && leitura.getDataFinal() != null && leitura.getPagina() > pagina) {
                pagina = leitura.getPagina();
            }
        }
        return pagina + 1;
    }

}
