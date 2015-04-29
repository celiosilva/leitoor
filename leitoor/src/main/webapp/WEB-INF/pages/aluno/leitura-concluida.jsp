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
            <ul class="nav navbar-nav">
                <li><span class="titulo" title="${leitura.tarefa.descricaoAtividade} - ${leitura.tarefa.turma.nome}">${leitura.tarefa.descricaoAtividade} - ${leitura.tarefa.turma.nome}</span></li>
            </ul>
        </div>
    </div>
	<h1>Leitura concluída!</h1>
	<p>Parabéns, você terminou a atividade.</p>
	<p>Você pode acompanhar os resultados acessando o botão <strong>Detalhar</strong> na sua página de atividades.</p>
	<div class="row footer">
        <div class="modal-footer">
            <a href="${urlHomeAluno.url}" class="btn btn-link pull-left">&larr;
                Voltar</a>
            <div class="clearfix"></div>
        </div>
    </div>
</t:template-aluno>

<!-- carregar scripts ao final da página -->
<script
    src="${app.resources}/resources/jQuery-File-Upload-master/js/vendor/jquery.ui.widget.js"></script>
<script
    src="${app.resources}/resources/js/leitoor.leitura.js"></script>

