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
        Home<small> // Minhas Atividades</small>
    </h1>

    <div class="row">
        <div class="col-md-12">

			<table class="table table-striped"> 
				<tbody>
	        	<c:forEach items="${tarefas}" var="tarefa">
	        		<tr>
	        			<td>${tarefa.tarefa.descricaoAtividade}</td>
	        			<td width="30">
	        			<c:choose> 
	        				<c:when test="${tarefa.porcentagemConcluida == 0}"><span class="label label-danger">${tarefa.porcentagemConcluida}%</span></c:when>
	        				<c:when test="${tarefa.porcentagemConcluida < 50}"><span class="label label-warning">${tarefa.porcentagemConcluida}%</span></c:when>
	        				<c:when test="${tarefa.porcentagemConcluida < 100}"><span class="label label-info">${tarefa.porcentagemConcluida}%</span></c:when>
	        				<c:otherwise><span class="label label-success">${tarefa.porcentagemConcluida}%</span></c:otherwise>
	        			</c:choose>
	        			</td>
	        			<td class="text-right" width="100">
	        				<a href="<c:url value="/aluno/detalhar/${tarefa.id}"/>" class="btn btn-warning btn-xs">Detalhar <span class="glyphicon glyphicon-signal"></span></a>
	        			</td>
	        			<td class="text-right" width="100">
	        				<c:if test="${tarefa.porcentagemConcluida < 100}">
	        				<a href="<c:url value="/aluno/tarefa/${tarefa.id}"><c:param name="pagina" value="${tarefa.ultimaPagina}"/></c:url>" class="btn btn-success btn-xs">Continuar <span class="glyphicon glyphicon-share-alt"></span></a>
	        				</c:if>
	        				<c:if test="${tarefa.porcentagemConcluida == 100}">
	        				<span class="label label-success">Concluída</span>
	        				</c:if>
	        			</td>
	        		</tr>
	        	</c:forEach>
	        	</tbody>	
        	</table>

        </div>
    </div>
</t:template-aluno>