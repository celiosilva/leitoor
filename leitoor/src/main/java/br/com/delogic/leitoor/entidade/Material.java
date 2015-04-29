package br.com.delogic.leitoor.entidade;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.SequenceGenerator;

import br.com.delogic.leitoor.entidade.mapped.DominioProfessor;

@Entity
public class Material extends DominioProfessor<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_material")
    @SequenceGenerator(name = "seq_material", sequenceName = "seq_material", allocationSize = 1, initialValue = 1)
    private Integer              id;

    @Column(length = 50)
    private String               titulo;

    @Column(length = 50)
    private String               arquivoUnico;

    private Integer              numeroPaginas;

    @ElementCollection
    @MapKeyColumn(name = "pagina")
    @Column(name = "arquivoPaginado", length = 50)
    @CollectionTable(name = "MATERIALPAGINADO")
    private Map<Integer, String> arquivoPaginado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getArquivoUnico() {
        return arquivoUnico;
    }

    public void setArquivoUnico(String arquivoUnico) {
        this.arquivoUnico = arquivoUnico;
    }

    public Map<Integer, String> getArquivoPaginado() {
        return arquivoPaginado;
    }

    public void setArquivoPaginado(Map<Integer, String> arquivoPaginado) {
        this.arquivoPaginado = arquivoPaginado;
    }

}
