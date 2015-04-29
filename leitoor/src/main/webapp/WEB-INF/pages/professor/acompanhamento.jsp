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
        <small> <a href="${urlHomeProfessor.url}">Home &raquo</a></small>
        Acompanhamento
    </h1>

    <div class="row acomp">
        <div class="col-md-12">
            <div class="leitoor-tab">
                <ul class="nav nav-tabs nav-left" role="tablist">
                    <li class="active"><a href="#atividade" role="tab"
                        data-toggle="tab">${tarefa.tarefa} - ${tarefa.turma}</a></li>
                </ul>
                <div class="clearfix"></div>
                <div class="tab-content">
                    <div class="tab-pane active acompanhamento" id="atividade">
                        <div class="row text-center legendas">
                            <div class="col-md-3 nao-iniciado">
                                <span>Tarefa Não Iniciada</span>
                            </div>
                            <div class="col-md-3 abaixo50">
                                <span>Iniciada - Até 50%</span>
                            </div>
                            <div class="col-md-3 acima50">
                                <span>Acima de 50%</span>
                            </div>
                            <div class="col-md-3 finalizado">
                                <span>Tarefa Finalizada</span>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="geral">
                                    <div id="totalgeral-chart" class="text-center"
                                        style="width: 250px; height: 250px;"></div>
                                    <h4 class="text-center titulo">Alunos</h4>
                                    <div class="nome-aluno">
                                        <c:forEach items="${acompanhamentos}" var="acompanhamento">
                                            <c:url value="/professor/acompanhamento/detalhe"
                                                var="urlDetalheAluno">
                                                <c:param name="idTarefaEnviada"
                                                    value="${acompanhamento.idTarefaEnviada}" />
                                            </c:url>
                                            <p>
                                                <m:percentual-color-name
                                                    percentual="${acompanhamento.percentualTotal}"
                                                    nome="${acompanhamento.nome}" url="${urlDetalheAluno}" />
                                            </p>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="leitura">
                                    <div>
                                        <div id="totalleitura-chart" class="text-center"
                                            style="width: 250px; height: 250px;"></div>
                                        <h4 class="text-center titulo">${tarefa.material}</h4>
                                        <div class="progresso">
                                            <c:forEach items="${acompanhamentos}" var="acompanhamento">
                                                <div class="progress">
                                                    <div class="progress-bar" role="progressbar"
                                                        aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"
                                                        style="width: ${acompanhamento.percentualLeitura}%">
                                                        ${acompanhamento.percentualLeitura}%</div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="questionario">
                                    <div>
                                        <div id="totalquestionario-chart" class="text-center"
                                            style="width: 250px; height: 250px;"></div>
                                        <h4 class="text-center titulo">
                                            <span class="acertos">Acertos</span>/<span class="erros">Erros</span>/<span>Total
                                                de Questões</span>
                                        </h4>
                                        <div class="resultado">
                                            <c:forEach items="${acompanhamentos}" var="acompanhamento">
                                                <h4 class="text-center titulo contagem pull-left">
                                                    <span class="acertos">${acompanhamento.acertos}</span>/<span
                                                        class="erros">${acompanhamento.erros}</span>/<span>${acompanhamento.questoes}</span>
                                                </h4>
                                                <s:url value="${urlAvaliacaoQuestionario}"
                                                    var="linkAvaliacaoQuestionario">
                                                    <s:param name="idTarefaEnviada"
                                                        value="${acompanhamento.idTarefaEnviada}"></s:param>
                                                </s:url>
                                                <a href="${linkAvaliacaoQuestionario}"
                                                    class="btn btn-primary pull-right"> Respostas <i
                                                    class="glyphicon glyphicon-new-window"></i>
                                                </a>
                                                <div class="clearfix"></div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row footer">
                            <div class="modal-footer">
                                <a href="${urlHomeProfessor.url}" class="btn btn-link pull-left">&larr;
                                    Voltar</a>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:template-professor>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {
        packages : [ "corechart" ]
    });

    var options = {
            legend : 'none',
            pieSliceText : 'value',
            title : '',
            titleTextStyle : {
                fontSize : 15
            },
            backgroundColor : 'transparent',
            slices: {
                0: { color: '#DC3912' },
                1: { color: '#FF9900' },
                2: { color: '#3366CC' },
                3: { color: '#109618' },
            }
    };

    function desenharTotalGeral() {

        var data = google.visualization.arrayToDataTable([
                [ 'Acompanhamento Geral', 'Percentual' ],
                [ 'Não Iniciado', ${graficoGeral.naoIniciado} ], [ 'Iniciado - Até 50%', ${graficoGeral.abaixo50} ],
                [ 'Acima de 50%', ${graficoGeral.acima50} ], [ 'Finalizado', ${graficoGeral.finalizado} ] ]);

        options.title = 'Total ${graficoGeral.media}% Concluída';

        var chart = new google.visualization.PieChart(document
                .getElementById('totalgeral-chart'));

        chart.draw(data, options);
    };

    function desenharTotalLeitura() {

        var data = google.visualization.arrayToDataTable([
                [ 'Acompanhamento Geral', 'Percentual' ],
                [ 'Não Iniciado', ${graficoLeitura.naoIniciado} ], [ 'Iniciado - Até 50%', ${graficoLeitura.abaixo50} ],
                [ 'Acima de 50%', ${graficoLeitura.acima50} ], [ 'Finalizado', ${graficoLeitura.finalizado} ] ]);

        options.title = 'Leitura ${graficoLeitura.media}%';

        var chart = new google.visualization.PieChart(document
                .getElementById('totalleitura-chart'));

        chart.draw(data, options);
    };

    function desenharTotalQuestionario() {

        var data = google.visualization.arrayToDataTable([
                [ 'Acompanhamento Geral', 'Percentual' ],
                [ 'Não Iniciado', ${graficoQuestionario.naoIniciado} ], [ 'Iniciado - Até 50%', ${graficoQuestionario.abaixo50} ],
                [ 'Acima de 50%', ${graficoQuestionario.acima50} ], [ 'Finalizado', ${graficoQuestionario.finalizado} ] ]);

        options.title = 'Questionário ${graficoQuestionario.media}%';

        var chart = new google.visualization.PieChart(document
                .getElementById('totalquestionario-chart'));

        chart.draw(data, options);
    };

    google.setOnLoadCallback(desenharTotalGeral);
    google.setOnLoadCallback(desenharTotalLeitura);
    google.setOnLoadCallback(desenharTotalQuestionario);
</script>

