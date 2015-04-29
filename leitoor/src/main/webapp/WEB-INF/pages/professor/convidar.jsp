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
        Convidar Professores<small> // Apenas convidados tornam-se
            professores</small>
    </h1>

    <div class="row">
        <div class="col-md-12">
            <div class="leitoor-tab">
                <ul class="nav nav-tabs nav-left" role="tablist">
                    <li class="active"><a href="#criar-material" role="tab"
                        data-toggle="tab">Enviar Convites</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="criar-material">
                        <f:form modelAttribute="convidar"
                            class="form-horizontal form-cadastro" role="form">
                            <f:errors path="*">
                                <div class="alert alert-danger">Ops, infelizmente
                                    encontramos algo errado. Corrija os erros encontrados e tente
                                    novamente.</div>
                            </f:errors>
                            <div class="form-group">
                                <label class="control-label col-md-4" for="emails">E-mails
                                    dos Convidados
                                    <p>
                                        <small>Digite os emails de outros professores
                                            separados por espaço sem vírgula ou caracteres especiais</small>
                                    </p>
                                </label>
                                <div class="col-md-8">
                                    <div class="input-group">
                                        <f:textarea path="emails" class="form-control"
                                            onkeydown="return this.value.substr(0,5000)" maxlength="5000"
                                            rows="10" />
                                        <span class="input-group-addon" data-contador-para="#emails"></span>
                                    </div>
                                    <f:errors path="emails" element="div" cssClass="text-danger" />
                                </div>
                            </div>
                            <div class="row footer">
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-success pull-left">
                                        Enviar Convites <i class="glyphicon glyphicon-envelope"></i>
                                    </button>
                                    <a href="${urlHomeProfessor.url}"
                                        class="btn btn-link pull-left">Cancelar</a>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </f:form>
                    </div>
                </div>
            </div>
        </div>


    </div>
</t:template-professor>
<script type="text/javascript">
    $('.text-danger').parents('.form-group').addClass('has-error');

    Contador = function() {
        this.iniciarContador = function(htmlElement) {
            $(htmlElement + '[data-contador-para]').each(function() {
                var contador = this;
                var input = $(contador).data('contador-para');
                contar(input, contador);
                $(input).keyup(function() {
                    contar(input, contador);
                });
            });
        };

        function contar(input, counterId) {
            var currentLenght = $(input).val().length;
            var maxLenght = $(input).attr("maxLength");
            var counterLenght = maxLenght - currentLenght;
            $(counterId).text(counterLenght);
        }
    };

    new Contador().iniciarContador('span');


</script>