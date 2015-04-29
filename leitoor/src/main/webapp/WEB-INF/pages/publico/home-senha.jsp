<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal-body">

    <div class="recuperar-senha row">
        <p class="borda-inferior">
            <span class="titulo">Digite seu e-mail abaixo e clique em
                recuperar que enviaremos por e-mail </span> <a href="#"
                class="close pull-right" data-dismiss="modal">&times;</a>
        </p>
        <div class="clearfix"></div>

        <form id="formsenha" action="${urlRecuperarSenha.url}" onsubmit="return false">
            <div class="form-group">
                <label class="control-label col-md-1" for="email"> E-mail </label>
                <div class="col-md-7">
                    <input name="email" type="text" class="form-control"
                        maxlength="200" value="${email}" />
                </div>
            </div>
        </form>
        <button class="btn btn-primary col-md-4" type="button"
            onclick="modalSenha.post('#formsenha')">
            Recuperar Senha <i class="fa fa-magic"></i>
        </button>

        <c:if test="${not empty mensagem}">
            <div class="alertaLogin">
                <span class="col-md-12  alert alert-danger"> <a href="#"
                    class="close" data-dismiss="alert">&times;</a> ${mensagem}
                </span>
            </div>
        </c:if>

    </div>
</div>