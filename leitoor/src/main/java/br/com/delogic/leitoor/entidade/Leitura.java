package br.com.delogic.leitoor.entidade;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.delogic.jfunk.data.Identity;

@Entity
public class Leitura extends Identity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_leitura")
    @SequenceGenerator(name = "seq_leitura", sequenceName = "seq_leitura", allocationSize = 1, initialValue = 1)
    private Integer          id;

    @ManyToOne
    private TarefaEnviada tarefaEnviada;

    private Integer          pagina;

    @Temporal(TemporalType.TIMESTAMP)
    private Date             dataInicial;

    @Temporal(TemporalType.TIMESTAMP)
    private Date             dataFinal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TarefaEnviada getTarefaEnviada() {
        return tarefaEnviada;
    }

    public void setTarefaEnviada(TarefaEnviada tarefaEnviada) {
        this.tarefaEnviada = tarefaEnviada;
    }

    public Integer getPagina() {
        return pagina;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

}
