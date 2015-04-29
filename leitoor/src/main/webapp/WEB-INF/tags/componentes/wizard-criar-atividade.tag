<%@ tag description="Wizard Criar Atividade" pageEncoding="UTF-8"%>
<%@ attribute name="step" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div
    class="navbar navbar-inverse navbar-fixed-top top-green leitoor-wizard"
    role="navigation">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li>Criar Atividade</li>
            <li>//</li>
            <li class="${step eq 1 ? 'invert':''}"><span class="badge">1</span></li>
            <li class="${step eq 1 ? 'invert':''}">Escolher Material</li>
            <li><span class="glyphicon glyphicon-chevron-right"></span></li>
            <li class="${step eq 2 ? 'invert':''}"><span class="badge">2</span>
            </li>
            <li class="${step eq 2 ? 'invert':''}">Configurar</li>
            <li><span class="glyphicon glyphicon-chevron-right"></span></li>
            <li class="${step eq 3 ? 'invert':''}"><span class="badge">3</span>
            </li>
            <li class="${step eq 3 ? 'invert':''}">Informar Alunos e Enviar</li>
        </ul>
    </div>
</div>