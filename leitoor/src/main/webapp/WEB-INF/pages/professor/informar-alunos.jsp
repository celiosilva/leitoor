<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templates"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/componentes"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<t:template-professor>
    <m:wizard-criar-atividade step="3" />
    <h1 class="titulo-wizard">
        Alunos <small>// Informe e-mails dos alunos para enviar o link
            de acesso</small>
    </h1>

    <div class="row">
        <div class="col-md-12">
            <div class="leitoor-tab">
                <ul class="nav nav-tabs nav-left" role="tablist">
                    <li class="active"><a href="#lista-emails" role="tab"
                        data-toggle="tab">LISTA DE E-MAILS</a></li>
                    <li><a href="#turmas-cadastradas" role="tab" data-toggle="tab">TURMAS
                            CADASTRADAS</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="lista-emails">
                        <f:form modelAttribute="alunos"
                            class="form-horizontal form-cadastro" role="form">
                            <f:hidden path="idMaterialConfigurado" />
                            <f:errors path="*">
                                <div class="alert alert-danger">Ops, infelizmente
                                    encontramos algo errado. Corrija os erros encontrados e tente
                                    novamente.</div>
                            </f:errors>
                            <div class="form-group">
                                <label class="control-label col-md-4" for="nomeAtividade">Nome
                                    da Atividade
                                    <p>
                                        <small>Nome, descrição, identificação</small>
                                    </p>
                                </label>
                                <div class="col-md-8">
                                    <div class="input-group">
                                        <f:input path="nomeAtividade" class="form-control"
                                            placeholder="Ex. Leitura Bimestral A1" maxlength="50" />
                                        <span class="input-group-addon"
                                            data-contador-para="#nomeAtividade"></span>
                                    </div>
                                    <f:errors path="nomeAtividade" element="div"
                                        cssClass="text-danger" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4" for="turma">Identificação
                                    do Grupo
                                    <p>
                                        <small>Nome do grupo ou turma</small>
                                    </p>
                                </label>
                                <div class="col-md-8">
                                    <div class="input-group">
                                        <f:input path="turma" class="form-control"
                                            placeholder="Ex. 6º B 2014" maxlength="50" />
                                        <span class="input-group-addon" data-contador-para="#turma"></span>
                                    </div>
                                    <f:errors path="turma" element="div" cssClass="text-danger" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4" for="emails">Lista
                                    de E-mails
                                    <p>
                                        <small>Alunos que receberão as atividades. Coloque
                                            e-mails separados por espaço</small>
                                    </p>
                                </label>
                                <div class="col-md-8">
                                    <div class="input-group">
                                        <f:textarea path="emails" class="form-control"
                                            onkeydown="return this.value.substr(0,999)" maxlength="999"
                                            rows="3" />
                                        <span class="input-group-addon" data-contador-para="#emails"></span>
                                    </div>
                                    <f:errors path="emails" element="div" cssClass="text-danger" />
                                </div>
                            </div>
                            <div class="row footer">
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-success pull-left">
                                        Salvar e Enviar <i class="glyphicon glyphicon-send"></i>
                                    </button>

                                    <c:url value="${urlConfigurarAtividadeMaterial}"
                                        var="configurarMaterialLink">
                                        <c:param name="mat" value="${mat}"></c:param>
                                    </c:url>
                                    <a href="${configurarMaterialLink}"
                                        class="btn btn-link pull-left">&larr; Voltar</a>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </f:form>
                    </div>
                    <div class="tab-pane" id="turmas-cadastradas">
                    	<c:if test="${not empty turmas}">
	                    	<c:forEach items="${turmas}" var="turma">
	                    		<c:url value="/professor/atividade/turma" var="urlTurma">
	                    		</c:url>
		                    	<form role="form" action="${urlTurma}" method="post" style="margin: 0; padding: 0"> 
	                    		<div class="row">
	                    			<div class="col-md-6">
		                    			<h4>${turma.nome}</h4>
		                    		</div>
	                    			<div class="col-md-3">
		                    			<p><input type="text" name="nomeAtividade" class="form-control"
                                            placeholder="Nome da Atividade" maxlength="50" /></p>
		                    		</div>
		                    		<div class="col-md-2">
		                    				<input type="hidden" name="idTurma" value="${turma.id}"/>
		                    				<input type="hidden" name="idMaterialConfigurado" value="${alunos.idMaterialConfigurado}"/>
			                    			<button type="submit" class="btn btn-success pull-left">
		                                        Salvar e Enviar <i class="glyphicon glyphicon-send"></i>
		                                    </button>
		                    		</div>
                                </div>
                                </form> 
	                    	</c:forEach>
                    	</c:if>
                    	<c:if test="${empty turmas}">
                    		<p>Nâo há turmas cadastradas.</p>
                    	</c:if>
					</div>
                </div>
            </div>
        </div>
    </div>

</t:template-professor>
<script type="text/javascript">
    $('.text-danger').parents('.form-group').addClass('has-error');
    new Leitoor.Contador().iniciarContador('span');
</script>