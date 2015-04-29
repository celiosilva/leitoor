package br.com.delogic.leitoor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.delogic.leitoor.entidade.ConfiguracaoPagina;
import br.com.delogic.leitoor.entidade.Material;
import br.com.delogic.leitoor.entidade.MaterialConfigurado;
import br.com.delogic.leitoor.entidade.Professor;
import br.com.delogic.leitoor.entidade.Questao;
import br.com.delogic.leitoor.entidade.QuestaoDissertativa;
import br.com.delogic.leitoor.model.ConfigurarMaterialModel;
import br.com.delogic.leitoor.model.ConfigurarPaginaModel;
import br.com.delogic.leitoor.model.CriarMaterialModel;
import br.com.delogic.leitoor.repository.ConfiguracaoPaginaRepository;
import br.com.delogic.leitoor.repository.DocumentosRepository;
import br.com.delogic.leitoor.repository.MaterialConfiguradoRepository;
import br.com.delogic.leitoor.repository.MaterialRepository;
import br.com.delogic.leitoor.repository.QuestaoRepository;
import br.com.delogic.leitoor.service.MaterialService;
import br.com.delogic.leitoor.util.Result;

@Named("materialService")
public class MaterialServiceImpl implements MaterialService {

    @Inject
    private MaterialRepository            materialRepository;

    @Inject
    private DocumentosRepository          documentoRepository;

    @Inject
    private MaterialConfiguradoRepository matConfiguradoRepository;

    @Inject
    private QuestaoRepository             questaoRepository;

    @Inject
    private ConfiguracaoPaginaRepository  configuracaoPaginaRepository;

    @Override
    @Transactional
    public Result<MaterialConfigurado> criarMaterial(CriarMaterialModel criarMaterial, Professor professor) {
        Material material = new Material();
        material.setArquivoUnico(criarMaterial.getNomeArquivo());
        material.setTitulo(criarMaterial.getTitulo());
        material.setProfessor(professor);

        Map<Integer, String> paginas = documentoRepository.dividirArquivo(criarMaterial.getNomeArquivo());
        material.setArquivoPaginado(paginas);
        material.setNumeroPaginas(paginas.size());

        materialRepository.create(material);

        MaterialConfigurado configuracao = new MaterialConfigurado();
        configuracao.setMaterial(material);
        configuracao.setMinMinutosPorPagina(criarMaterial.getMinutos());
        configuracao.setConfiguracaoPaginas(new HashMap<Integer, ConfiguracaoPagina>());
        configuracao.setProfessor(professor);

        for (Entry<Integer, String> pag : paginas.entrySet()) {
            ConfiguracaoPagina configPagina = new ConfiguracaoPagina();
            configPagina.setMaterialConfigurado(configuracao);
            configPagina.setMinMinutosPorPagina(criarMaterial.getMinutos());
            configPagina.setPagina(pag.getKey());
            configuracao.getConfiguracaoPaginas().put(pag.getKey(), configPagina);
        }

        matConfiguradoRepository.create(configuracao);

        return Result.success("Material configurado criado com sucesso", configuracao);
    }
    
    @Override
    @Transactional
    public Result<MaterialConfigurado> reusarMaterial(Integer materialId, Professor professor, Integer minutosPadrao) {
        Material material = materialRepository.findById(materialId);
        
        MaterialConfigurado configuracao = new MaterialConfigurado();
        configuracao.setMaterial(material);
        configuracao.setMinMinutosPorPagina(minutosPadrao);
        configuracao.setConfiguracaoPaginas(new HashMap<Integer, ConfiguracaoPagina>());
        configuracao.setProfessor(professor);
        
        Map<Integer, String> paginas = material.getArquivoPaginado();

        for (Entry<Integer, String> pag : paginas.entrySet()) {
            ConfiguracaoPagina configPagina = new ConfiguracaoPagina();
            configPagina.setMaterialConfigurado(configuracao);
            configPagina.setMinMinutosPorPagina(minutosPadrao);
            configPagina.setPagina(pag.getKey());
            configuracao.getConfiguracaoPaginas().put(pag.getKey(), configPagina);
        }

        matConfiguradoRepository.create(configuracao);

        return Result.success("Material configurado criado com sucesso", configuracao);
    }

    @Override
    public Result<MaterialConfigurado> talvezAlterarMaterial(CriarMaterialModel material, Professor professorAutenticado) {
        throw new UnsupportedOperationException("não implementado ainda");
    }

    @Override
    public CriarMaterialModel buscarMaterial(Integer idMatConfigurado, Professor professorAutenticado) {
        MaterialConfigurado material = matConfiguradoRepository.findByIdAndProfessor(idMatConfigurado, professorAutenticado);
        CriarMaterialModel model = new CriarMaterialModel();
        if (material != null) {
            model.setMinutos(material.getMinMinutosPorPagina());
            model.setNomeArquivo(material.getMaterial().getArquivoUnico());
            model.setTitulo(material.getMaterial().getTitulo());
        }
        return model;
    }
    
    @Override
    public List<Material> buscarMaterialParaReuso(Professor professorAutenticado) {
    	return materialRepository.findByProfessor(professorAutenticado);
    }

    @Override
    public ConfigurarMaterialModel buscarConfiguracaoMaterial(Integer idMatConfigurado, Professor professor) {
        MaterialConfigurado material = matConfiguradoRepository.findByIdAndProfessor(idMatConfigurado, professor);

        ConfigurarMaterialModel model = new ConfigurarMaterialModel();
        model.setIdMaterialConfiguracao(idMatConfigurado);
        List<ConfigurarPaginaModel> paginas = new ArrayList<ConfigurarPaginaModel>();

        for (int pagina = 1; pagina <= material.getConfiguracaoPaginas().size(); pagina++) {

            ConfiguracaoPagina cp = material.getConfiguracaoPaginas().get(pagina);

            ConfigurarPaginaModel pg = new ConfigurarPaginaModel();
            pg.setQuestoes(new ArrayList<String>());

            if (cp.getQuestoes() != null) {
                for (Questao q : cp.getQuestoes()) {
                    pg.getQuestoes().add(q.getDescricao());
                }
            }
            pg.setTempo(cp.getMinMinutosPorPagina());
            pg.setUrl(material.getMaterial().getArquivoPaginado().get(pagina));
            paginas.add(pg);
        }

        model.setPaginas(paginas);

        return model;
    }

    @Override
    @Transactional
    public Result<Void> configurarMaterial(ConfigurarMaterialModel materialModel, Professor professor) {
        MaterialConfigurado material = matConfiguradoRepository.findByIdAndProfessor(materialModel.getIdMaterialConfiguracao(), professor);

        for (int pag = 1; pag <= materialModel.getPaginas().size(); pag++) {
            ConfigurarPaginaModel paginaModel = materialModel.getPaginas().get(pag - 1);
            ConfiguracaoPagina configPagina = material.getConfiguracaoPaginas().get(pag);

            if (configPagina == null) {
                configPagina = new ConfiguracaoPagina();
                configPagina.setMaterialConfigurado(material);
                configPagina.setPagina(pag);
                configPagina.setQuestoes(new ArrayList<Questao>());
                material.getConfiguracaoPaginas().put(pag, configPagina);
            } else if (configPagina.getQuestoes() != null && !configPagina.getQuestoes().isEmpty()) {
                List<Questao> questoesParaRemover = new ArrayList<Questao>(configPagina.getQuestoes());
                configPagina.getQuestoes().clear();
                questaoRepository.delete(questoesParaRemover);
            }

            // configurar tempo
            configPagina.setMinMinutosPorPagina(paginaModel.getTempo());

            // configurar questões
            if (paginaModel.getQuestoes() != null && !paginaModel.getQuestoes().isEmpty()) {

                for (String q : paginaModel.getQuestoes()) {
                    Questao questao = new QuestaoDissertativa();
                    questao.setDescricao(q);
                    configPagina.getQuestoes().add(questao);
                }
                questaoRepository.create(configPagina.getQuestoes());
                configuracaoPaginaRepository.update(configPagina);
            }

        }

        matConfiguradoRepository.update(material);

        return Result.success("Configuração salva com sucesso!");
    }
}
