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

    <s:url value="${urlAcompanhamento}" var="linkAcompanhamento">
        <s:param name="idTarefa" value="${avaliacao.idTarefa}" />
    </s:url>

    <h1 class="titulo">
        <small> <a href="{urlHomeProfessor.url}">Home &raquo</a> <a
            href="${linkAcompanhamento}">Acompanhamento &raquo</a></small> Avaliação do
        Questionário
    </h1>

    <div class="row acomp">
        <div class="col-md-12">
            <div class="leitoor-tab">
                <ul class="nav nav-tabs nav-left" role="tablist">
                    <li class="active"><a href="#atividade" role="tab"
                        data-toggle="tab">${avaliacao.atividade} - ${avaliacao.turma}</a></li>
                </ul>
                <div class="clearfix"></div>
                <div class="tab-content avaliacao">
                    <div class="tab-pane active acompanhamento" id="atividade">
                        <f:form modelAttribute="avaliacao" role="form">
                            <div class="row cab-dados">
                                <div class="col-md-3">
                                    <span>Material</span>
                                    <p>${avaliacao.material}</p>
                                </div>
                                <div class="col-md-3">
                                    <span>Aluno</span>
                                    <p>${avaliacao.aluno}</p>
                                </div>
                            </div>
                            <div class="row quest">
                                <div class="col-md-12">
                                    <c:forEach items="${avaliacao.respostas}" var="resposta"
                                        varStatus="it">
                                        <f:hidden path="respostas[${it.index}].id" />
                                        <div class="row">
                                            <div class="col-md-10">
                                                <span class="tit-quest">${it.count} -
                                                    ${resposta.questao} ?</span>
                                                <p class="resposta">${resposta.resposta}</p>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="tit-quest text-center"
                                                    id="legendaAvaliacao${it.index}">${avaliacao.respostas[it.index].avaliacao != null ? avaliacao.respostas[it.index].avaliacao.descricao: 'Não Avaliado'}</div>
                                                <div data-toggle="buttons">
                                                    <c:forEach items="${avaliacoes}" var="av" varStatus="avit">
                                                        <label
                                                            class="btn btn-nao-selec ${avit.first ? 'btn-incorreto': 'btn-correto'} ${avaliacao.respostas[it.index].avaliacao == av ? 'active':'' }"
                                                            onclick="avaliar(${!avit.first}, ${it.index})"> <f:radiobutton
                                                                path="respostas[${it.index}].avaliacao"
                                                                autocomplete="off" value="${av}" /> <i
                                                            class="glyphicon glyphicon-${avit.first ? 'remove':'ok'}"></i>
                                                        </label>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="row footer">
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-success pull-left">
                                        Salvar</button>

                                    <a href="${linkAcompanhamento}"
                                        class="btn btn-link pull-left">&larr; Voltar</a>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </f:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:template-professor>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    function avaliar(correto, id) {
        $('#legendaAvaliacao' + id).text(correto ? 'Correto': 'Incorreto');
        $('#respostas' + id + "\\.avaliacao1").prop('checked', !correto);
        $('#respostas' + id + "\\.avaliacao2").prop('checked', correto);
    }
</script>

