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
     	<s:url value="${urlAcompanhamento}" var="linkAcompanhamento">
            <s:param name="idTarefa" value="${tarefa.idTarefa}"/>
        </s:url>
        <small> <a href="${urlHomeProfessor.url}">Home</a> &raquo
			<a href="${linkAcompanhamento}">Acompanhamento</a> &raquo</small> 
        	Detalhes da Atividade
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
                        <div class="row footer">
                        	
						    <div class="row">
						    	<div class="col-md-4"><h3>Atividade</h3></div>
						    	<div class="col-md-4"><h3>Aluno</h3></div>
						    	<div class="col-md-4"><h3>Início/Término</h3></div>
						   	</div>
						    <div class="row">
						    	<div class="col-md-4">${detalhes.tarefa.descricaoAtividade}, ${detalhes.tarefa.turma.nome}</div>
						    	<div class="col-md-4">${detalhes.aluno.nome}</div>
						    	<div class="col-md-4">
						    		<c:if test="${detalhes.leituras[0].dataInicial != null}">
										<fmt:formatDate value="${detalhes.leituras[0].dataInicial}" pattern="dd/MM/yyyy HH:mm"/>
									</c:if>
									/
						    		<c:if test="${detalhes.leituras[detalhes.quantidadePaginas - 1].dataFinal != null}">
										<fmt:formatDate value="${detalhes.leituras[detalhes.quantidadePaginas - 1].dataFinal}" pattern="dd/MM/yyyy HH:mm"/>
									</c:if>
						    	</div>
						   	</div>
						
							<div class="row margem-topo-15">
				        		<div class="col-md-6">
						            <!-- <h4 class="text-center">Leitura vs. Tempo</h4> -->
						            <div style="position: static">
						        	<div id="chart-leitura-tempo" class="text-center chart"
						               style="width: 400px; height: 100px;">Informações não disponíveis.</div>
						            </div>
					            </div>
				        		<div class="col-md-6">
				        			<!-- <h4 class="text-center">Realização da Leitura</h4> -->
						        	<div id="chart-realizacao-leitura" class="text-center chart"
						               style="width: 400px; height: 100px;">Informações não disponíveis.</div>
					            </div>
				            </div>
				        	<div class="row margem-topo-15">
				        		<div class="col-md-6">
				        			<!-- <h4 class="text-center">Tempo de leitura por dia</h4> -->
						        	<div id="chart-leitura-por-dia" class="text-center chart"
						               style="width: 400px; height: 100px;">Informações não disponíveis.</div>
					            </div>
				        		<div class="col-md-6">
				        			<!-- <h4 class="text-center">Questionário</h4> -->
						        	<div id="chart-questionario" class="text-center chart"
						               style="width: 400px; height: 100px;">Informações não disponíveis.</div>
					            </div>
				            </div>
						    
						    <div class="panel-group margem-topo-15" role="tablist">
							    <div class="panel panel-default">
							      <div class="panel-heading" role="tab" id="collapseDetalhesAcesso">
							        <h4 class="panel-title">
							          <a class="" data-toggle="collapse" href="#DetalheAcessoAluno" aria-expanded="false" aria-controls="DetalheAcessoAluno">
							            <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span> Detalhes de acesso do aluno
							          </a>
							        </h4>
							      </div>
							      <div id="DetalheAcessoAluno" class="panel-collapse collapse" role="tabpanel" aria-labelledby="collapseDetalhesAcesso">
								      <div class="panel-body">
 										  <table class="table table-striped">
											<thead>
												<tr>
													<th>Atividade</th>
													<th class="text-center">Data Realização</th>
													<th class="text-center">Situação</th>
												</tr>
											</thead> 
											<tbody>
								        	<c:forEach items="${detalhes.leituras}" var="leitura">
								        		<tr>
								        			<td>Pág. ${leitura.pagina}</td>
								        			<td class="text-center">
								        				<c:choose>
								        					<c:when test="${leitura.dataFinal != null}"><fmt:formatDate value="${leitura.dataFinal}" pattern="dd/MM/yyyy HH:mm"/></c:when>
								        					<c:when test="${leitura.dataInicial != null}"><fmt:formatDate value="${leitura.dataInicial}" pattern="dd/MM/yyyy HH:mm"/> ...</c:when>
								        					<c:otherwise>-</c:otherwise>
								        				</c:choose>
								        			</td>
								        			<td class="text-center">
								        				<c:choose>
								        					<c:when test="${leitura.dataFinal != null}">Realizado</c:when>
								        					<c:when test="${leitura.dataInicial != null}">Em Andamento</c:when>
								        					<c:otherwise>-</c:otherwise>
								        				</c:choose>
								        			</td>
								        		</tr>
								        	</c:forEach>
								        	</tbody>	
							        	</table>
								      </div>
								  </div>
							    </div>
							</div>
						    
                            <div class="modal-footer">
                                <a href="${linkAcompanhamento}" class="btn btn-link pull-left">&larr;
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
    google.load("visualization", "1", {packages : [ "corechart" ]});
    var altura = 300, largura = 400;
    function desenharGraficoRealizacaoLeitura() {

    	var dataTable = new google.visualization.DataTable();
        dataTable.addColumn('string', '');
        dataTable.addColumn('number', 'Melhor');
        dataTable.addColumn('number', 'Média');
        dataTable.addColumn('number', 'Aluno');
        dataTable.addRows([
             [ '', ${detalhes.melhorDesempenho} / 100.0, 
             ${detalhes.desempenhoMedio} / 100.0, 
             ${detalhes.desempenhoAluno} / 100.0 ]
          ]);

        var t = $('#chart-realizacao-leitura').html('')
        	.css({
        		'width': largura + 'px',
        		'height': altura + 'px'});
        var chart = new google.visualization.ColumnChart(t.get(0));

        var options = {
   		  title: 'Realização da leitura',
   		  backgroundColor: '#aa0000',
          width: largura, height: altura,
          legend: { position: 'bottom' },
          titleTextStyle : {
              fontSize : 14
          },
          vAxis: {
        	  format:'#%', 
        	  viewWindow: {
        	        max: 1.0,
        	        min: 0.0
        	    }
       	  },
          backgroundColor : 'transparent',
          series: {
              0: { color: '#DC3912' },
              1: { color: '#FF9900' },
              2: { color: '#3366CC' },
              3: { color: '#109618' },
          }
        };
        chart.draw(dataTable, options);
    };
    
    function desenharGraficoLeituraTempo() {

    	var dataTable = new google.visualization.DataTable();
        dataTable.addColumn('datetime', 'Realizado');
        dataTable.addColumn('number', 'Melhor');
        dataTable.addColumn('number', 'Média');
        dataTable.addColumn('number', 'Aluno');
        var items = [];
        <c:forEach items="${detalhes.dadosGrafico}" var="item">
        items.push([ 
                     new Date(${item.key.time}), 
                     ${item.value[1]} / 100.0, 
                     ${item.value[2]} / 100.0, 
                     ${item.value[3]} / 100.0 
            ]);
        
        <c:if test="${empty detalhes.dadosGrafico}">
        </c:if>
        
        </c:forEach>
        dataTable.addRows(items);
        
        var dataView = new google.visualization.DataView(dataTable);
        //dataView.setColumns([{calc: function(data, row) { return data.getFormattedValue(row, 0); }, type:'string'}, 1, 2, 3]);
        dataView.setColumns([{calc: function(data, row) { return ''; }, type:'string'}, 1, 2, 3]);

        var t = $('#chart-leitura-tempo').html('')
        	.css({
        		'width': largura + 'px',
        		'height': altura + 'px'});
        var chart = new google.visualization.LineChart(t.get(0));
        var options = {
   		  title: 'Leitura x Tempo',
          width: largura, height: altura,
          legend: { position: 'bottom' },
          curveType: 'function',
          pointSize: 3,
          titleTextStyle : {
              fontSize : 14
          },
          vAxis: {
        	  format:'#%', 
        	  viewWindow: {
        	        max: 1.0,
        	        min: 0.0
        	    }
       	  },
          backgroundColor : 'transparent',
          series: {
              0: { color: '#DC3912' },
              1: { color: '#FF9900' },
              2: { color: '#3366CC' },
              3: { color: '#109618' },
          }
        };
        chart.draw(dataView, options);

    };
    
    function desenharGraficoLeituraPorDia() {

    	var dataTable = new google.visualization.DataTable();
        dataTable.addColumn('datetime', 'Realizado');
        dataTable.addColumn('number', 'Melhor');
        dataTable.addColumn('number', 'Média');
        dataTable.addColumn('number', 'Aluno');
        var items = [];
        <c:forEach items="${detalhes.dadosGraficoPorDia}" var="item">
        items.push([ 
                     new Date(${item.key.time}), 
                     ${item.value[1]}, 
                     ${item.value[2]}, 
                     ${item.value[3]} 
            ]);
        </c:forEach>
        dataTable.addRows(items);
        
        var formatter = new google.visualization.DateFormat({pattern: 'd/MMM'});
        formatter.format(dataTable, 0);
        
        var dataView = new google.visualization.DataView(dataTable);
        dataView.setColumns([{calc: function(data, row) { return data.getFormattedValue(row, 0); }, type:'string'}, 1, 2, 3]);
        //dataView.setColumns([{calc: function(data, row) { return ''; }, type:'string'}, 1, 2, 3]);
        
        var t = $('#chart-leitura-por-dia').html('')
	    	.css({
	    		'width': largura + 'px',
	    		'height': altura + 'px'});
        var chart = new google.visualization.LineChart(t.get(0));
        var options = {
   		  title: 'Tempo de leitura por dia',
          width: largura, height: altura,
          legend: { position: 'bottom' },
          curveType: 'function',
          pointSize: 3,
          titleTextStyle : {
              fontSize : 14
          },
          vAxis: {
        	  viewWindow: {
        	        min: 0.0
        	    }
       	  },
          backgroundColor : 'transparent',
          series: {
              0: { color: '#DC3912' },
              1: { color: '#FF9900' },
              2: { color: '#3366CC' },
              3: { color: '#109618' },
          }
        };
        chart.draw(dataView, options);

    };
    
    function desenharGraficoQuestionario() {
    	
    	 var data = google.visualization.arrayToDataTable([
              ['', 'Melhor', 'Média', 'Aluno'],
              ['Total Pontos', ${detalhes.melhorPontuacao.pontos}, ${detalhes.mediaPontuacao.pontos}, ${detalhes.alunoPontuacao.pontos}],
              ['Erros', ${detalhes.melhorPontuacao.erros}, ${detalhes.mediaPontuacao.erros}, ${detalhes.alunoPontuacao.erros}],
              ['Acertos', ${detalhes.melhorPontuacao.acertos}, ${detalhes.mediaPontuacao.acertos}, ${detalhes.alunoPontuacao.acertos}]
            ]);
    	 
        var t = $('#chart-questionario').html('')
	    	.css({
	    		'width': largura + 'px',
	    		'height': altura + 'px'});
        
        var chart = new google.visualization.BarChart(t.get(0));

        var options = {
   		  title: 'Questionário',
          width: largura, height: altura,
          legend: { position: 'bottom' },
          titleTextStyle : {
              fontSize : 14
          },
          vAxis: {
        	  viewWindow: {
        	        min: 0.0
        	    }
       	  },
          backgroundColor : 'transparent',
          series: {
              0: { color: '#DC3912' },
              1: { color: '#FF9900' },
              2: { color: '#3366CC' },
              3: { color: '#109618' },
          }
        };
        chart.draw(data, options);

    };

    google.setOnLoadCallback(desenharGraficoLeituraTempo);
    google.setOnLoadCallback(desenharGraficoRealizacaoLeitura);
    google.setOnLoadCallback(desenharGraficoLeituraPorDia);
    google.setOnLoadCallback(desenharGraficoQuestionario);
</script>

