<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templates"%>

<div class="row">
    <div class="col-md-12 janela">
        <p class="titulo">Me Cadastrar no Leitoor</p>
        <f:form class="form-horizontal formulario-cadastro" role="form"
            action="${urlCadastro.url}" commandName="cadastro" method="POST">

            <c:choose>
                <c:when test="${not empty violacoes}">
                    <div class="alert alert-danger">${violacoes}.</div>
                </c:when>
                <c:otherwise>
                    <f:errors path="*">
                        <div class="alert alert-danger">Ops, encontramos algo
                            errado. Corrija os erros encontrados e tente novamente.</div>
                    </f:errors>
                </c:otherwise>
            </c:choose>

            <f:hidden path="convite" />

            <div class="form-group">
                <label class="control-label col-md-4" for="email"> E-mail
                    <p>
                        <small>Será usado como login</small>
                    </p>
                </label>
                <div class="col-md-8">
                    <f:input path="email" type="text" class="form-control" />
                    <f:errors path="email" element="div" cssClass="text-danger" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-4" for="senha"> Senha
                    <p>
                        <small>Mínimo de 8 caracteres</small>
                    </p>
                </label>
                <div class="col-md-8">
                    <f:input path="senha" type="password" class="form-control"
                        maxlength="50" />
                    <f:errors path="senha" element="div" cssClass="text-danger" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-4" for="repetirSenha">
                    Confirmar Senha
                    <p>
                        <small>Insira a senha novamente.</small>
                    </p>
                </label>
                <div class="col-md-8">
                    <f:input path="repetirSenha" type="password" class="form-control"
                        maxlength="50" />
                    <f:errors path="repetirSenha" element="div" cssClass="text-danger" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-4" for="nomeCompleto">
                    Nome Completo
                    <p>
                        <small>Nome completo do aluno</small>
                    </p>
                </label>
                <div class="col-md-8">
                    <f:input path="nomeCompleto" type="text" class="form-control" />
                    <f:errors path="nomeCompleto" element="div" cssClass="text-danger" />
                </div>
            </div>
            <div class="form-group col-md-12">
                <button class="btn btn-success" type="button"
                    onClick="Cadastro.form.post('#cadastro')">Cadastrar e
                    Entrar &rarr;</button>
            </div>
        </f:form>
    </div>
</div>

<script>
    $('.text-danger').parents('.form-group').addClass('has-error');
</script>