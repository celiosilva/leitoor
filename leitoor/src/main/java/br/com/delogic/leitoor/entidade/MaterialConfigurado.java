package br.com.delogic.leitoor.entidade;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

import br.com.delogic.leitoor.entidade.mapped.DominioProfessor;

@Entity
public class MaterialConfigurado extends DominioProfessor<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mat_configurado")
    @SequenceGenerator(name = "seq_mat_configurado", sequenceName = "seq_mat_configurado", allocationSize = 1, initialValue = 1)
    private Integer                          id;

    @ManyToOne
    private Material                         material;

    private Integer                          minMinutosPorPagina;

    @MapKey(name = "pagina")
    @OrderBy("pagina")
    @OneToMany(mappedBy = "materialConfigurado", cascade = CascadeType.ALL)
    private Map<Integer, ConfiguracaoPagina> configuracaoPaginas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getMinMinutosPorPagina() {
        return minMinutosPorPagina;
    }

    public void setMinMinutosPorPagina(Integer minMinutosPorPagina) {
        this.minMinutosPorPagina = minMinutosPorPagina;
    }

    public Map<Integer, ConfiguracaoPagina> getConfiguracaoPaginas() {
        return configuracaoPaginas;
    }

    public void setConfiguracaoPaginas(Map<Integer, ConfiguracaoPagina> configuracaoPaginas) {
        this.configuracaoPaginas = configuracaoPaginas;
    }

}
