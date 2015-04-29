<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
    <div class="col-md-12 janela">
        <p class="titulo">
            Entrar com meu cadastro Leitoor <span class="pull-right"> <a
                data-toggle="modal" data-target="#modal-senha"
                onclick="modalSenha.get('${urlRecuperarSenha.url}')"> Mas eu esqueci minha
                    senha...</a></span>
        </p>
        <div class="clearfix"></div>
        <form id="login" class="form-group formulario-login" role="form"
            action="${urlLoginAutenticarCadastro.url}" method="post">
            <label class="control-label col-md-1" for="email"> Email </label>
            <div class="col-md-4">
                <input name="email" type="text" class="form-control"
                    value="${email}" />
            </div>
            <label class="control-label col-md-1" for="senha"> Senha </label>
            <div class="col-md-3">
                <input name="senha" type="password" class="form-control"
                    maxlength="50" />
            </div>
            <div class="col-md-3">
                <button type="button" class="btn btn-primary"
                    onClick="Login.form.post('#login')">Entrar &rarr;</button>
            </div>

            <c:if test="${not empty erro}">
                <div class="alertaLogin">
                    <span class="col-md-12  alert alert-danger"> <a href="#"
                        class="close" data-dismiss="alert">&times;</a> ${erro}
                    </span>
                </div>
            </c:if>
        </form>
    </div>
</div>