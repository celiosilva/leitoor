<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templates"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/componentes"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<t:template-aluno>
    <!-- Green top bar (wizard) -->
    <div
        class="navbar navbar-inverse navbar-fixed-top top-green leitoor-wizard"
        role="navigation">
        <div class="container-fluid">
            <ul id="titulo-atividade" class="nav navbar-nav">
                <li><span id="tela-cheia" class="glyphicon glyphicon-fullscreen"></span></li>
                <li><span class="titulo" title="${leitura.tarefa.descricaoAtividade} - ${leitura.tarefa.turma.nome}">${leitura.tarefa.descricaoAtividade} - ${leitura.tarefa.turma.nome}</span></li>
            </ul>
            <ul class="nav navbar-nav reading-pagination">
                <c:if test="${leitura.paginaAtual > 1}">
                <li><a class="seta" href="<c:url value=""><c:param name="pagina" value="${leitura.paginaAtual - 1}" /></c:url>">
                    <span class="glyphicon glyphicon-arrow-left"></span></a></li>
                </c:if>
                <c:if test="${not (leitura.paginaAtual > 1)}">
                <li><span class="glyphicon glyphicon-arrow-left disabled"></span></li>
                </c:if>
                <li>
                    <!-- <span class="glyphicon glyphicon-chevron-down"></span>	 -->
                    <c:url value="" var="urlPaginaEspecifica">
                        <c:param name="pagina" value="" />
                    </c:url>
                    <select id="page-navigation" class="chosen-select" data-url="${urlPaginaEspecifica}" 
                    		title="Página ${leitura.paginaAtual} de ${leitura.quantidadePaginas}">
                        <c:forEach items="${paginas}" var="i">
                        <option value="${i}" ${i == leitura.paginaAtual ? ' selected ' : '' }>${i}</option>
                        </c:forEach>
                     </select>
                </li>
                <c:if test="${leitura.paginaAtual < leitura.paginasAcessiveis}">
                <c:url value="" var="urlProxima">
                    <c:param name="pagina" value="${leitura.paginaAtual + 1}" />
                </c:url>
                <li><a class="seta" href="${urlProxima}">
                    <span class="glyphicon glyphicon-arrow-right"></span></a></li>
                </c:if>
                <c:if test="${not (leitura.paginaAtual < leitura.paginasAcessiveis)}">
                <li><span class="glyphicon glyphicon-arrow-right disabled"></span></li>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><span class="glyphicon glyphicon-time"></span></li>
                <li id="timer" data-tempo="${leitura.tempoDecorrido}" data-minimo="${leitura.tempoMinimo}"
                        data-questoes="${leitura.quantidadeQuestoes}" data-completa="${leitura.completa}"></li>
                <li>
                    <c:url value="concluir/${idTarefaEnviada}" var="urlAvancar">
                        <c:param name="pagina" value="${leitura.paginaAtual}" />
                    </c:url>
                    <button id="btn-responder" class="btn btn-default btn-sm btn-white hidden"
                    	data-url-avancar="${urlAvancar}">Responder Questões
                        <span class="glyphicon glyphicon-question-sign"></span></button>
                    <a href="${urlAvancar}" id="btn-avancar" class="btn btn-default btn-sm btn-white hidden">Avançar
                        <span class="glyphicon glyphicon-arrow-right'"></span></a>
                </li>
            </ul>
        </div>
    </div>
    <iframe src="${contentManager.get(leitura.arquivoPagina)}" class="frame-leitura" frameborder="0"></iframe>
    
<c:url value="questionario/${idTarefaEnviada}" var="urlQuestionario">
  <c:param name="pagina" value="${leitura.paginaAtual}" />
</c:url>

<div class="modal fade" id="questionario-modal" data-url="${urlQuestionario}">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">${leitura.atividade.descricao} - ${leitura.tarefa.descricaoAtividade} - Questionário Pág. ${leitura.paginaAtual}</h4>
      </div>
      <div class="modal-body">
        <p>One fine body&hellip;</p>
      </div>
      <div class="modal-footer">
	  	<button id="btn-salvar-respostas" type="submit" class="btn btn-success">Salvar e Fechar <span class="glyphicon glyphicon-ok"></span></button>
	  	<button id="btn-enviar-respostas" type="submit" class="btn btn-success">Enviar Respostas <span class="glyphicon glyphicon-send"></span></button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
    
</t:template-aluno>

<!-- carregar scripts ao final da página -->
<script
    src="${app.resources}/resources/jQuery-File-Upload-master/js/vendor/jquery.ui.widget.js"></script>
<script
    src="${app.resources}/resources/js/leitoor.leitura.js"></script>

