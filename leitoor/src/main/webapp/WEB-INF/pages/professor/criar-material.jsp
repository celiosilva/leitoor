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
    <m:wizard-criar-atividade step="1" />
    <h1 class="titulo-wizard">
        Material<small> // Crie ou escolha um material existente</small>
    </h1>

    <div class="row">
        <div class="col-md-12">
            <div class="leitoor-tab">
                <ul class="nav nav-tabs nav-left" role="tablist">
                    <li class="active"><a href="#criar-material" role="tab"
                        data-toggle="tab">CRIAR MATERIAL</a></li>
                    <li><a href="#materiais-disponiveis" role="tab"
                        data-toggle="tab">MATERIAIS DISPONÍVEIS</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="criar-material">
                        <f:form modelAttribute="material"
                            class="form-horizontal form-cadastro" role="form">
                            <f:errors path="*">
                                <div class="alert alert-danger">Ops, infelizmente
                                    encontramos algo errado. Corrija os erros encontrados e tente
                                    novamente.</div>
                            </f:errors>
                            <div class="form-group">
                                <label class="control-label col-md-4" for="uploadarquivo">Selecionar
                                    Arquivo
                                    <p>
                                        <small>Apenas arquivos tipo PDF</small>
                                    </p>
                                </label>
                                <div class="col-md-8">
                                    <span class="btn btn-file btn-primary">Localizar Arquivo
                                        <i class="glyphicon glyphicon-upload"></i><input type="file"
                                        accept=".pdf" name="file" id="uploadarquivo"
                                        data-url="${app.context}/upload""> <f:hidden
                                            path="nomeArquivo" />
                                    </span>
                                    <c:if test="${not empty material.nomeArquivo}">
                                        <a href="${contentManager.get(material.nomeArquivo)}"
                                            download="${material.nomeArquivo}"> Arquivo carregado com
                                            sucesso!</a>
                                    </c:if>
                                    <c:if test="${empty material.nomeArquivo}">
                                        <a id="arquivoCarregado"> Nenhum arquivo carregado
                                            ainda...</a>
                                    </c:if>

                                    <span id="loader" class="hidden">
                                        <img src="${app.resources}/resources/icons/loader.gif " /> Carregando arquivo...
                                    </span>

                                    <f:errors path="nomeArquivo" element="div"
                                        cssClass="text-danger" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4" for="titulo">Título
                                    Material
                                    <p>
                                        <small>Nome do livro, texto, matéria, etc</small>
                                    </p>
                                </label>
                                <div class="col-md-8">
                                    <div class="input-group">
                                        <f:input path="titulo" class="form-control"
                                            placeholder="Os Lusíadas, Camões, etc" maxlength="50"/>
                                        <span class="input-group-addon" data-contador-para="#titulo"></span>
                                    </div>
                                    <f:errors path="titulo" element="div" cssClass="text-danger" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4" for="minutos">Tempo
                                    Minimo de Leitura
                                    <p>
                                        <small>Mínimo para mudar de página</small>
                                    </p>
                                </label>
                                <div class="col-md-8">
                                    <div class="input-group">
                                        <span class="input-group-addon">Minutos</span>
                                        <f:input path="minutos" class="form-control" type="number"
                                            placeholder="Minutos para poder avançar para a próxima página" maxlength="2"/>
                                        <span class="input-group-addon" data-contador-para="#minutos"></span>
                                    </div>
                                    <f:errors path="minutos" element="div" cssClass="text-danger" />
                                </div>
                            </div>
                            <div class="row footer">
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-success pull-left">
                                        Salvar e Continuar <i
                                            class="glyphicon glyphicon-chevron-right"></i>
                                    </button>
                                    <a href="${urlHomeProfessor.url}"
                                        class="btn btn-link pull-left">Cancelar e Sair</a>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </f:form>
                    </div>
                    <div class="tab-pane" id="materiais-disponiveis">

                        <div class="row">
                            <c:forEach items="${materiaisReuso}" var="materialReuso">
                                <c:url value="/professor/atividade/reusar-material" var="urlReusoMaterial">
                                    <c:param name="mat" value="${materialReuso.id}" />
                                </c:url>
                                <div class="col-md-2 text-center icone-atividade">
                                    <div class="triangulo pull-right"></div>
                                    <div class="icone-painel">
                                        <p>
                                            <strong>${fn:substring(materialReuso.titulo, 0, 30)}...</strong>
                                        </p>
                                        <p>${materialReuso.numeroPaginas}&nbsp;Pág.</p>
                                    </div>
                                    <div class="clearfix"></div>
                                    <a href="${urlReusoMaterial}" class="btn btn-success"
                                        role="button">Selecionar <span
                                            class="glyphicon glyphicon-chevron-right"></span></a>
                                </div>
                            </c:forEach>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</t:template-professor>

<!-- carregar scripts ao final da página -->
<script
    src="${app.resources}/resources/jQuery-File-Upload-master/js/vendor/jquery.ui.widget.js"></script>
<script
    src="${app.resources}/resources/jQuery-File-Upload-master/js/jquery.iframe-transport.js"></script>
<script
    src="${app.resources}/resources/jQuery-File-Upload-master/js/jquery.fileupload.js"></script>

<script type="text/javascript">

    // recupera o ok e mostra o loader
    $(function() {
       $('#uploadarquivo').change(function (){
           $('#loader').removeClass('hidden');
           $('#arquivoCarregado').text('');
       });
    });

    $('#uploadarquivo').fileupload({
        dataType : 'json',
        done : function(e, data) {
            $.each(data.result.files, function(index, file) {
                $('#nomeArquivo').val(file.name);
                $('#arquivoCarregado').attr('href', file.url);
                $('#arquivoCarregado').attr('download', file.name);
            });
        },
        success : function(e, data){
             $('#loader').addClass('hidden');
             $('#arquivoCarregado').text('Arquivo carregado com sucesso!');
        }
    });

    $('.text-danger').parents('.form-group').addClass('has-error');
    new Leitoor.Contador().iniciarContador('span');


</script>