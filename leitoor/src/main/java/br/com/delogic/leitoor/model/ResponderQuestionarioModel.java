package br.com.delogic.leitoor.model;

import java.util.List;

public class ResponderQuestionarioModel {

	public static class ItemResposta {
		Integer idResposta;
		Integer idQuestao;
		String resposta;
		public Integer getIdResposta() {
			return idResposta;
		}
		public void setIdResposta(Integer idResposta) {
			this.idResposta = idResposta;
		}
		public Integer getIdQuestao() {
			return idQuestao;
		}
		public void setIdQuestao(Integer idQuestao) {
			this.idQuestao = idQuestao;
		}
		public String getResposta() {
			return resposta;
		}
		public void setResposta(String resposta) {
			this.resposta = resposta;
		}
		
	}
	
	List<ItemResposta> respostasDissertativas;
	Boolean enviar;
	
	public void setRespostasDissertativas(
			List<ItemResposta> respostasDissertativas) {
		this.respostasDissertativas = respostasDissertativas;
	}
	public List<ItemResposta> getRespostasDissertativas() {
		return respostasDissertativas;
	}
	public Boolean getEnviar() {
		return enviar;
	}
	public void setEnviar(Boolean enviar) {
		this.enviar = enviar;
	}
	
}
