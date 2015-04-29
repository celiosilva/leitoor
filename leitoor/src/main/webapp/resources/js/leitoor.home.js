var modalSenha = new Leitoor.Modal("#modal-senha");

var Cadastro = {};
Cadastro.form = new Leitoor.Form('#cadastro-div');

var Login = {};
Login.form = new Leitoor.Form('#login-div');

$(document).ready(
        function() {

            // carousel (slides) in main page
            $.getScript('resources/owl/owl.carousel.min.js', function(data,
                    textStatus, jqxhr) {
                $("#home-slider").owlCarousel({
                    singleItem : true,
                    autoPlay : true,
                    pagination : true
                });
            });

            // smooth scroll from top menu
            $('.navbar-nav a').click(function() {
                var href = $.attr(this, 'href');
                $('html,body').animate({
                    scrollTop : $(href).offset().top - 60
                }, 1000, function() {
                    window.location.hash = href;
                });
                return false;
            });
        });