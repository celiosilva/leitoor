<%@ tag description="Template para páginas professor"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html lang="pt">
<head>
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="Leitoor" />
<meta name="author" content="delogic.com.br" />

<title>Leitoor</title>

<!-- CSS COM TODOS OS CSS'S -->
<link href="${app.resources}/resources/css/leitoor.css" rel="stylesheet">

<link href="${app.resources}/resources/taginput/tagsinput.css"
    rel="stylesheet">
<!-- como não sei se o Luiz está alterando então colocarei minhas alterações em outro arquivo css -->
<link href="${app.resources}/resources/css/leitoor-2.css"
    rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body class="pagina-fundo-cinza">
    <!-- header carregado dinamicamente -->
    <div id="header">
        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid leitoor-nav">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed"
                        data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span> <span
                            class="icon-bar"></span> <span class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${urlHomeProfessor.url}">Leitoor</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active nav-item"><a href="#">Home</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown nav-item"><a href="#"
                            class="dropdown-toggle" data-toggle="dropdown">Olá
                                ${autenticacaoService.nomeUsuarioAutenticado} <span
                                class="caret"></span>
                        </a>
                            <ul class="dropdown-menu text-right" role="menu">
                                <li><a href="${urlHomeAluno.url}" class="text-right">Portal
                                        do Aluno <i class="glyphicon glyphicon-transfer"></i>
                                </a></li>
                                <li class="divider"></li>
                                <li><a href="${urlConvidarProfessor.url}">Convidar
                                        Professores <i class="glyphicon glyphicon-envelope"></i>
                                </a></li>
                                <li class="divider"></li>
                                <li><a href="${urlLogout.url}" class="text-right">Sair
                                        <i class="glyphicon glyphicon-off"></i>
                                </a></li>
                            </ul></li>

                    </ul>
                </div>
                <!--/.nav-collapse -->
            </div>
        </div>


    </div>

    <div class="body">
        <div class="container">
            <div class="main-content">
                <c:if test="${not empty sucesso}">
                    <h3>
                        <div class="alert bg-primary alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert">
                                <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                            </button>
                            <strong>${sucesso}</strong>
                        </div>
                    </h3>
                </c:if>
                <jsp:doBody />
            </div>
        </div>
        <!-- fim container -->
    </div>
    <!-- fim body -->

    <!-- footer carregado dinamicmente -->
    <footer id="footer"></footer>

    <!-- jquery -->
    <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <!-- bootstrap -->
    <script
        src="${app.resources}/resources/bootstrap-3.1.1-dist/js/bootstrap.min.js"></script>

    <script
        src="${app.resources}/resources/chosen_v1.1.0/chosen.jquery.min.js"></script>

    <script src="${app.resources}/resources/taginput/tagsinput.js"></script>
    <script
        src="${app.resources}/resources/autosize-master/jquery.autosize.min.js"></script>

    <!-- JQuery Template - Tmpl -->
    <script
        src="http://ajax.microsoft.com/ajax/jquery.templates/beta1/jquery.tmpl.min.js"></script>

    <!-- script da home -->
    <script src="${app.resources}/resources/js/leitoor.js"></script>



</body>
</html>