<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/componentes"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form class="form-horizontal form-cadastro" role="form">
  <c:forEach items="${questionario.questoes}" var="q" varStatus="i">
    <div class="form-group">
      <label>${i.index + 1}. ${q.descricao}</label>
      <div class="input-group">
      <textarea id="resposta${i.index}" name="respostas[${i.index}].resposta" class="form-control resposta-dissertativa"
      	onkeydown="return this.value.substr(0,1000)" maxlength="1000"
      	data-id="${questionario.respostas[i.index].id}" data-questao-id="${questionario.respostas[i.index].questao.id}"
      	rows="5">${questionario.respostas[i.index].reposta}</textarea>
      	<span class="input-group-addon" data-contador-para="#resposta${i.index}"></span>
      </div>
    </div>
  </c:forEach>
</form>
