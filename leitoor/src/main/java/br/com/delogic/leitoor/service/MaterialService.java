package br.com.delogic.leitoor.service;

import java.util.List;

import br.com.delogic.leitoor.entidade.Material;
import br.com.delogic.leitoor.entidade.MaterialConfigurado;
import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.model.ConfigurarMaterialModel;
import br.com.delogic.leitoor.model.CriarMaterialModel;
import br.com.delogic.leitoor.util.Result;

public interface MaterialService {

    Result<MaterialConfigurado> criarMaterial(CriarMaterialModel material, Professor professor);

    Result<MaterialConfigurado> talvezAlterarMaterial(CriarMaterialModel material, Professor professorAutenticado);

    CriarMaterialModel buscarMaterial(Integer idMatConfigurado, Professor professorAutenticado);

    ConfigurarMaterialModel buscarConfiguracaoMaterial(Integer idMatConfigurado, Professor professor);

    Result<Void> configurarMaterial(ConfigurarMaterialModel configuracao, Professor professor);

	Result<MaterialConfigurado> reusarMaterial(Integer materialId, Professor professor, Integer minutosPadrao);

	List<Material> buscarMaterialParaReuso(Professor professorAutenticado);

}
