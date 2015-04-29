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
    <h1 class="titulo">
        Home<small> // Detalhe da Leitura</small>
    </h1>
    
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

	<h2>Leitura</h2>
    <div class="row">
        <div class="col-md-8">

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
        <div class="col-md-4">
        	<div class="row">
        		<div class="col-md-12">
		        	<div id="bar-chart" class="text-center"
		               style="width: 350px; height: 350px;"></div>
	            </div>
        		<div class="col-md-12">
		        	<div id="line-chart" class="text-center"
		               style="width: 350px; height: 350px;"></div>
	            </div>
            </div>
        </div>
    </div>
    
    
    <h2>Questões</h2>
    <c:forEach items="${questionarios}" var="questionario" varStatus="p">
    	<c:if test="${not empty questionario.questoes}">
    	<div class="panel panel-default">
		    <div class="panel-heading">Página ${p.index + 1}</div>
		    <div class="panel-body">
			   	<ul class="list-group">
		 	   	    <c:forEach items="${questionario.questoes}" var="q" varStatus="i">
		 	   	    	<li class="list-group-item">
					    	<p><strong>${i.index + 1}.</strong> ${q.descricao}</p>
						    <div class="row">
							    <div class="col-md-10">
							      <c:if test="${not empty questionario.respostas[i.index].reposta}">
							      <div class="well well-sm">${questionario.respostas[i.index].reposta}</div>
							      </c:if>
							      <c:if test="${empty questionario.respostas[i.index].reposta}">
							      <span class="label label-warning">Não respondido</span>
							      </c:if>
							    </div>
							    <div class="col-md-2">
							     <c:choose>
							     	<c:when test="${questionario.respostas[i.index].avaliacao == 'CORRETO'}">
							     		<span class="label label-success">Correto <span class="glyphicon glyphicon-ok"></span></span></c:when>
							     	<c:when test="${questionario.respostas[i.index].avaliacao == 'INCORRETO'}">
							     		<span class="label label-danger">Incorreto <span class="glyphicon glyphicon-remove"></span></span></c:when>
							     	<c:otherwise><span class="label label-default">Não Avaliado</span></c:otherwise>
							     </c:choose>
							     
							    </div>
						    </div>
						</li>
			   	    </c:forEach>
			   	</ul>
		   	</div>
	    </div>
	    </c:if>
   	</c:forEach>
   	
    <div class="row footer">
        <div class="modal-footer">
            <a href="${urlHomeAluno.url}" class="btn btn-link pull-left">&larr;
                Voltar</a>
            <div class="clearfix"></div>
        </div>
    </div>
</t:template-aluno>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {
        packages : [ "corechart" ]
    });
    function desenharGraficoBarras() {

    	var dataTable = new google.visualization.DataTable();
        dataTable.addColumn('string', '');
        dataTable.addColumn('number', 'Melhor');
        dataTable.addColumn('number', 'Média');
        dataTable.addColumn('number', 'Você');
        dataTable.addRows([
             [ '', ${detalhes.melhorDesempenho} / 100.0, 
             ${detalhes.desempenhoMedio} / 100.0, 
             ${detalhes.desempenhoAluno} / 100.0 ]
          ]);
        
        var chart = new google.visualization.ColumnChart(document.getElementById('bar-chart'));

        var options = {
   		  title: 'Realização da Leitura',
          width: 350, height: 350,
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
    
    function desenharGraficoLinhas() {

    	var dataTable = new google.visualization.DataTable();
        dataTable.addColumn('datetime', 'Realizado');
        dataTable.addColumn('number', 'Melhor');
        dataTable.addColumn('number', 'Média');
        dataTable.addColumn('number', 'Você');
        var items = [];
        <c:forEach items="${detalhes.dadosGrafico}" var="item">
        items.push([ 
                     new Date(${item.key.time}), 
                     ${item.value[1]} / 100.0, 
                     ${item.value[2]} / 100.0, 
                     ${item.value[3]} / 100.0 
            ]);
        </c:forEach>
        dataTable.addRows(items);
        
        var dataView = new google.visualization.DataView(dataTable);
        //dataView.setColumns([{calc: function(data, row) { return data.getFormattedValue(row, 0); }, type:'string'}, 1, 2, 3]);
        dataView.setColumns([{calc: function(data, row) { return ''; }, type:'string'}, 1, 2, 3]);

        var chart = new google.visualization.LineChart(document.getElementById('line-chart'));
        var options = {
   		  title: 'Leitura vs. Tempo',
          width: 350, height: 350,
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

    <c:if test="${not empty detalhes.dadosGrafico}">
    google.setOnLoadCallback(desenharGraficoBarras);
    google.setOnLoadCallback(desenharGraficoLinhas);
    </c:if>
</script>

