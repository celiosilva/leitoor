<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal-body">
    <div class="row">
        <button type="button" class="close pull-right" data-dismiss="modal"
            aria-hidden="true">&times;</button>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="alertaLogin">
            <span class="col-md-12  alert alert-success"> ${mensagem} </span>
        </div>
    </div>
</div>