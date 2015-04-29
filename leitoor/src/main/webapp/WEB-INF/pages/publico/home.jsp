<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html lang="pt">
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="CSA Showcase" />
<meta name="author" content="delogic.com.br" />

<title>Leitoor</title>

<!-- CSS COM TODOS OS CSS'S -->
<link href="${app.resources}/resources/css/leitoor.css" rel="stylesheet">
<link
    href="${app.resources}/resources/font-awesome-4.2.0/css/font-awesome.min.css"
    rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>

    <div id="header">
        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="home.html">Leitoor</a>
                </div>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#porque">Por quê?</a></li>
                    <li><a href="#entrar">Entrar</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="body">

        <div class="container">
            <div class="main-content">

                <h1>Bem-vindo ao Leitoor!</h1>
                <p>
                    Se você é um professor e está preocupado com a evolução dos seus
                    alunos, seja bem-vindo!

                    <!-- Carousel Principal -->
                <div id="home-slider" class="owl-carousel">
                    <div>
                        <img src="resources/images/banner3.jpg" />
                        <div class="caption">Indicadores de desempenho permitem
                            identificar deficiências e direcionar esforços</div>
                    </div>
                    <div>
                        <img src="resources/images/banner4.jpg" />
                        <div class="caption">Fácil de usar, os alunos saberão
                            exatamente como foi seu desempenho em relação aos demais</div>
                    </div>
                    <div>
                        <img src="resources/images/banner1.jpg" />
                        <div class="caption">Uma adição ao arsenal dos verdadeiros
                            educadores</div>
                    </div>
                </div>

                <h1 id="porque">Porque o Leitoor?</h1>
                <p>O Leitoor é uma ferramenta para auxiliar os professores do
                    ensino médio a criar e distribuir atividades entre seus alunos,
                    comparar resultados colher indicadores de desempenho e avaliar os
                    alunos individualmente e coletivamente.</p>
                <h2>Com o Leitoor você pode:</h2>

                <div class="row">
                    <div class="col-lg-3">
                        <p class="text-center">
                            <img src="resources/images/home-icon-1.png" />
                        </p>
                        <p class="text-center">Criar atividades com marcadores de
                            habilidades para poder medir onde seus alunos precisam de reforço
                            e planejar o próximo passo.</p>
                    </div>
                    <div class="col-lg-3">
                        <p class="text-center">
                            <img src="resources/images/home-icon-1.png" />
                        </p>
                        <p class="text-center">Aplicar as atividades para múltiplos
                            alunos e turmas, quantas vezes quiser e onde estiver.</p>
                    </div>
                    <div class="col-lg-3">
                        <p class="text-center">
                            <img src="resources/images/home-icon-1.png" />
                        </p>
                        <p class="text-center">Colher os resultados como respostas e
                            avaliações diretamente na ferramenta.</p>
                    </div>
                    <div class="col-lg-3">
                        <p class="text-center">
                            <img src="resources/images/home-icon-1.png" />
                        </p>
                        <p class="text-center">Contar com indicadores como desempenho,
                            dedicação e engajamento para acompanhar de perto a evolução de
                            cada aluno.</p>
                    </div>
                </div>

                <a id="entrar"></a>
                <h1>Entrar no Leitoor</h1>
                <p>
                    <!-- O Leitoor não requer cadastro prévio nem vai gastar seu tempo -->
                    <!-- com cadastros. Basta se conectar com Facebook e tudo pronto. -->
                    Entrar no Leitoor é muito fácil, basta se conectar com sua rede
                    social favorita e pronto. Se quiser pode criar o seu próprio
                    cadastro na nossa ferramenta também! Atenção, se você pretende usar
                    como professor é necessário que seja convidade por outro professor.
                </p>

                <div class="row">
                    <div class="col-md-4 login-social">
                        <p class="text-center">
                            <s:url value="${urlLoginFacebook}" var="linkLoginFacebook">
                                <s:param name="convite" value="${convite}" />
                            </s:url>
                            <a href="${linkLoginFacebook}"
                                class="btn btn-primary col-md-12 bt-social"> <i
                                class="fa fa-facebook"></i> Entrar usando Facebook
                            </a>

                            <s:url value="${urlLoginGoogle}" var="linkLoginGoogle">
                                <s:param name="convite" value="${convite}" />
                            </s:url>
                            <a href="${linkLoginGoogle}"
                                class="btn btn-danger col-md-12 bt-social"> <i
                                class="fa fa-google-plus"></i> Entrar usando Google
                            </a>

                        </p>
                    </div>
                    <div class="col-md-1">
                        <p class="inter-login">OU</p>
                    </div>

                    <div class="col-md-7">

                        <!-- form login -->
                        <div id="login-div">
                            <%@include file="home-login.jsp"%>
                        </div>
                        <!-- form cadastro -->
                        <div id="cadastro-div">
                            <%@include file="home-cadastro.jsp"%>
                        </div>
                    </div>

                </div>

            </div>
        </div>
        <!-- fim container -->

        <!-- modal recuperar senha -->
        <div id="modal-senha" class="modal fade modal-custom" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content"></div>
            </div>
        </div>

    </div>
    <!-- fim body -->

    <div>&nbsp;</div>
    <div>&nbsp;</div>

    <!-- jquery -->
    <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <!-- bootstrap -->
    <script
        src="${app.resources}/resources/bootstrap-3.1.1-dist/js/bootstrap.min.js"></script>
    <!-- script do Leitoor -->
    <script src="${app.resources}/resources/js/leitoor.js"></script>
    <!-- script leitoor -->
    <script src="resources/js/leitoor.js"></script>
    <!-- script da home -->
    <script src="${app.resources}/resources/js/leitoor.home.js"></script>

</body>
</html>