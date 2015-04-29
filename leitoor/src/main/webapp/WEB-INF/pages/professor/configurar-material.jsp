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
    <m:wizard-criar-atividade step="2" />
    <h1 class="titulo-wizard">
        Configurações<small> // Inclua tempo de leitura e questões
            entre as páginas</small>
    </h1>

    <div class="row">
        <div class="col-md-12">
            <div class="leitoor-tab">
                <ul class="nav nav-tabs nav-left" role="tablist">
                    <li class="active"><a href="#criar-material" role="tab"
                        data-toggle="tab">CONFIGURAR PÁGINAS INDIVIDUALMENTE</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="criar-material">
                        <f:form class="form-horizontal form-cadastro"
                            modelAttribute="configuracao">
                            <f:errors path="*">
                                <div class="alert alert-danger">Ops, infelizmente
                                    encontramos algo errado. Corrija os erros encontrados e tente
                                    novamente.</div>
                            </f:errors>
                            <f:hidden path="IdMaterialConfiguracao" />
                            <c:forEach items="${configuracao.paginas}" var="pagina"
                                varStatus="it">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="config-pag">
                                            <div class="abrir-pagina pull-left text-center"
                                                onclick="exibirPdf(this, '${contentManager.get(pagina.url)}', ${it.index})">
                                                <i class="glyphicon glyphicon-chevron-right"></i>
                                            </div>
                                            <div class="pull-left numero">Página ${it.count}</div>
                                            <div class="pull-right add">
                                                <button type="button" class="btn btn-primary"
                                                    onclick="adicionarQuestao(${it.index}, '')">
                                                    Adicionar Questão <i class="glyphicon glyphicon-plus"></i>
                                                </button>
                                            </div>
                                            <div class="pull-right form-tempo">
                                                <div class="input-group tempo-min">
                                                    <span class="input-group-addon">Tempo Mínimo em
                                                        Minutos</span>
                                                    <f:input path="paginas[${it.index}].tempo" type="text"
                                                        class="form-control" />
                                                </div>
                                                <f:errors path="paginas[${it.index}].tempo" element="div"
                                                    cssClass="text-danger" />
                                            </div>
                                            <div class="clearfix"></div>

                                            <div class="questionario" id="questionarioPag${it.index}">
                                            </div>
                                            <c:forEach items="${pagina.questoes}" var="questao">
                                                <span class="adicionarQuestao hidden">
                                                    adicionarQuestao(${it.index}, '${questao}'); </span>
                                            </c:forEach>

                                            <div id="pdf${it.index}"></div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <div class="row footer">
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-success pull-left">
                                        Salvar e Continuar <i
                                            class="glyphicon glyphicon-chevron-right"></i>
                                    </button>
                                    <c:url value="${urlCriarAtividadeMaterial}"
                                        var="configurarMaterialLink">
                                        <c:param name="mat" value="${mat}"></c:param>
                                    </c:url>
                                    <a href="${configurarMaterialLink}"
                                        class="btn btn-link pull-left">&larr; Voltar</a>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </f:form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script id="questaoDissertativaTemplate" type="text/x-jQuery-tmpl">
        <div class="form-group">
            <label class="control-label col-md-2" for="paginas\${nrPagina}.questoes\${nrQuestao}">Questão
                <p>
                    <small>Descrição textual</small>
                </p>
            </label>
            <div class="col-md-10">
                <div class="input-group">
                    <textarea id="paginas\${nrPagina}.questoes\${nrQuestao}" name="paginas[\${nrPagina}].questoes[\${nrQuestao}]"
                        onkeydown="return this.value.substr(0,500)" maxlength="500"
                        class="form-control" rows="1">\${descricao}</textarea>
                    <span class="input-group-addon" data-contador-para="#paginas\${nrPagina}\.questoes\${nrQuestao}"></span>
                    <span class="btn btn-danger input-group-addon btn-remover" onclick="remover(this, \${nrPagina})"><span><i class="glyphicon glyphicon-minus"></i></span></span>
                </div>
            </div>
        </div>
    </script>

</t:template-professor>

<script type="text/javascript">
    function exibirPdf(div, pagina, nrpagina) {
        var pdfDiv = '#pdf' + nrpagina;
        if ($(pdfDiv + ':has(*)').length > 0) {
            $(pdfDiv).html('');
            $(div).children('i').removeClass('glyphicon-chevron-down');
        } else {
            var pdfPag = '<iframe src="' + pagina + '" class="pdf" frameborder="0"></iframe>';
            $(pdfDiv).html(pdfPag);
            $(div).children('i').addClass('glyphicon-chevron-down');
        }
    }

    var totalQuestoes = 0;

    function adicionarQuestao(pagina, descricao) {
        var questionario = "#questionarioPag" + pagina;
        if ($(questionario).children().length == 0) {
            $(questionario).html('<p class="aviso">Questões a serem adicionadas ao final da leitura desta página</p>');
        }
        var questao = {
            nrPagina : pagina,
            nrQuestao : totalQuestoes++,
            descricao : descricao
        };
        $("#questaoDissertativaTemplate").tmpl(questao).appendTo(questionario);
        $('textarea').textareaAutoSize();
        new Leitoor.Contador().iniciarContador('span');
    }

    function remover(id, pagina) {
        $(id).parents('.form-group').remove();
        var questionario = "#questionarioPag" + pagina;
        if ($(questionario).children().length == 1) {
            $(questionario).html('');
        }
    }

    $(document).ready(function(){
        var arr = $('.adicionarQuestao');
        for (var n = 0; n < arr.length; n++){
            eval(arr[n].innerHTML);
        }
    });

    $('.text-danger').parents('.form-tempo').addClass('has-error');

</script>