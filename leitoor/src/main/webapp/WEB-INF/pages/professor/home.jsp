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
    <h1 class="titulo">
        Home<small> // Controle de Atividades e Tarefas</small>
    </h1>

    <div class="row">
        <div class="col-md-6">
            <div class="leitoor-tab">
                <ul class="nav nav-tabs nav-left" role="tablist">
                    <li class="${materiais.size() gt 0 ? 'active':''}"><a
                        href="#atividade" role="tab" data-toggle="tab">ATIVIDADES</a></li>
                    <li><a href="${urlCriarAtividadeMaterial.url}">CRIAR
                            ATIVIDADE <i class="glyphicon glyphicon-plus"></i>
                    </a></li>
                    <li class="pull-right ${materiais.size() eq 0 ? 'active':''}"><a
                        href="#atividade_oquee" role="tab" data-toggle="tab">O que é ?</a></li>
                </ul>
                <div class="clearfix"></div>
                <div class="tab-content">
                    <div class="tab-pane ${materiais.size() gt 0 ? 'active':''}"
                        id="atividade">
                        <div class="row">
                            <c:forEach items="${materiais}" var="material">
                                <c:url value="${urlAtividadeAlunos}" var="atividadeAlunosLink">
                                    <c:param name="mat" value="${material.idMaterialConfigurado}" />
                                </c:url>
                                <div class="col-md-4 text-center icone-atividade">
                                    <div class="triangulo pull-right"></div>
                                    <div class="icone-painel">
                                        <p>
                                            <strong>${fn:substring(material.atividade, 0, 15)}..</strong>
                                        </p>
                                        <p>${material.numeroPaginas}&nbsp;Pág.</p>
                                        <p>${material.totalQuestoes}&nbsp;Quest.</p>
                                    </div>
                                    <div class="clearfix"></div>
                                    <a href="${atividadeAlunosLink}" class="btn btn-primary"
                                        role="button">Aplicar <i class="glyphicon glyphicon-send"></i></a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="tab-pane ${materiais.size() eq 0 ? 'active':''}"
                        id="atividade_oquee">
                        <img class="img-responsive pull-left"
                            src="${app.resources}/resources/icons/moleskine.png" />
                        <p class="text-justify">São conjuntos de tarefas como leituras
                            e/ou questionários, previamente preparados pelo professor, que
                            poderão ser aplicados a um aluno ou turmas de alunos quando
                            desejar. As atividades ajudam o professor a preparar o conteúdo
                            uma única vez e replicar quantas vezes quiser. Se ainda não
                            possui atividade clique em Criar Atividade para começar!</p>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="leitoor-tab">
                <ul class="nav nav-tabs nav-left" role="tablist">
                    <li class="${tarefas.size() gt 0 ? 'active':''}"><a
                        href="#acompanhamento" role="tab" data-toggle="tab">ACOMPANHAMENTO</a></li>
                    <li class="pull-right ${tarefas.size() eq 0 ? 'active':''}"><a
                        href="#acompanhamento_oquee" role="tab" data-toggle="tab">O
                            que é ?</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane ${tarefas.size() gt 0 ? 'active':''}"
                        id="acompanhamento">
                        <div class="row">
                            <c:forEach items="${tarefas}" var="tarefa">
                                <div class="col-md-4 text-center icone-tarefa">
                                    <div
                                        class="text-center icone-painel ${tarefa.percentualCompleto gt 70 ? 'circulo-success':
                                                              tarefa.percentualCompleto gt 35 ? 'circulo-info': 'circulo-warning'}">

                                        <p>
                                            <strong>${fn:substring(tarefa.tarefa, 0, 10)}..</strong>
                                        </p>
                                        <p>${fn:substring(tarefa.turma, 0, 10)}</p>
                                        <span>${tarefa.percentualCompleto}%</span>
                                    </div>
                                    <s:url value="${urlAcompanhamento}" var="linkAcompanhamento">
                                        <s:param name="idTarefa" value="${tarefa.idTarefa}"/>
                                    </s:url>
                                    <a href="${linkAcompanhamento}"
                                        class="btn ${tarefa.percentualCompleto gt 70 ? 'btn-success':
                                                              tarefa.percentualCompleto gt 35 ? 'btn-info': 'btn-warning'}"
                                        role="button">Detalhar <i
                                        class="glyphicon glyphicon-signal"></i>
                                    </a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="tab-pane ${tarefas.size() eq 0 ? 'active':''}"
                        id="acompanhamento_oquee">
                        <img class="img-responsive pull-left"
                            src="${app.resources}/resources/icons/atividade.png" />
                        <p class="text-justify">São atividades que foram aplicadas e
                            agora estão sendo executadas pelos alunos. O acompanhamento provê
                            ao professor métricas e indicadores do trabalho do aluno e também
                            permite analisar e avaliar as respostas que foram criadas.
                            Aplique uma atividade para poder acompanha-la por aqui.</p>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:template-professor>