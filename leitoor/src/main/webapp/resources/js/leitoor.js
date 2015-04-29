//$(document).ready(function() {
//    $("#header").load("template/header.html");
//    $("#double-header").load("template/double-header.html");
//});

if ($('select').length > 0 && $.chosen) {
    $('select').chosen({
        width : '100%',
        no_results_text : 'Nenhuma informação encontrada com '
    });
}

if ($('textarea').length > 0) {
    $('textarea').textareaAutoSize();
}

/**
 * Namespace para o módulo Leitoor
 */
var Leitoor = (Leitoor || {});

Leitoor.Contador = function() {
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

/**
 * Classe para manipulação do Modal do bootstrap
 */
Leitoor.Modal = function(modalDivId) {

    /**
     * Obtem o html para determinada view e inclui dentro do modal configurado
     */
    this.get = function(url) {
        $.get(url, function(data) {
            $(modalDivId + ' .modal-content').html(data);
        });
    };

    /**
     * Faz o post do form e o replace do modal com o resultado esperado
     */
    this.post = function(url, formId) {
        $.post(url, $(formId).serialize(), function(data) {
            $(modalDivId + ' .modal-content').html(data);
        });
    };

    /**
     * Faz o post do form e o replace do modal com o resultado esperado
     */
    this.post = function(formId) {
        $.post($(formId).attr('action'), $(formId).serialize(), function(data) {
            $(modalDivId + ' .modal-content').html(data);
        });
    };

};

/**
 * Classe para manipulação de Formulario Spring
 */
Leitoor.Form = function(modalDivId) {
    /**
     * Obtem o html para determinada view e inclui dentro do modal configurado
     */
    this.get = function(url) {
        $.get(url, function(data) {
            $(modalDivId).html(data);
        });
    };

    /**
     * Faz o post do form e o replace do modal com o resultado esperado
     */
    this.post = function(url, formId) {
        $.post(url, $(formId).serialize(), function(data) {
            $(modalDivId).html(data);
        });
    };

    /**
     * Faz o post do form e o replace do modal com o resultado esperado
     */
    this.post = function(formId) {
        $.post($(formId).attr('action'), $(formId).serialize(), function(data) {
            $(modalDivId).html(data);
        });
    };

};