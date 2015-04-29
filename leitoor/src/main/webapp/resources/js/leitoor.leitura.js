var tempoDecorrido = 0;
var tempoMinimo = 0;
var completa = false;
var hasFocus = true;

$(document).ready(function() {
    $("#page-navigation").on('change', function() {
    	var pagina = $(this).val();
    	if (pagina) window.location = $(this).data('url') + $(this).val();
    });
    
    $('#tela-cheia').click(function() {
    	$('body').toggleClass('tela-cheia');
    });
    
   	$('#page-navigation').tooltip({ placement: 'bottom' });
   	
   	//eventos questionario
   	$('#questionario-modal')
   	.on('click', '#btn-salvar-respostas', function() { salvarResposta(false); })
   	.on('click', '#btn-enviar-respostas', function() { salvarResposta(true); });
    
   	$('#btn-responder').click(function() {
		mostrarQuestionarioModal();
	});
   	
   	initTimer();
});

function salvarResposta(enviar) {
	var dados = { 
			enviar: enviar, 
			respostasDissertativas: []
		};
	var concluido = true;
	$('#questionario-modal .resposta-dissertativa').each(function(i) {
		var $this = $(this);
		if ($this.val() == '') concluido = false;
		dados.respostasDissertativas[i] = {
			idResposta: $this.data('id'),
			idQuestao: $this.data('questao-id'),
			resposta: $this.val()
		}
	});
	if (enviar && !concluido) { 
		alert('Preencha todas as respostas antes de enviar o questionário!');
		return false;
	}
	if (!enviar || confirm('Confirma o envio do questionário?')) {

		$.ajax({
			url: $('#questionario-modal').data('url'),
			type: 'post',
			cache: false,
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
		    mimeType: 'application/json',
		    processData: false,
			data: JSON.stringify(dados)
		}).done(function(resultado) {
			//avançar para próxima página
			if (enviar) {
				if (resultado) {
					window.location = $('#btn-responder').data('url-avancar');
				}
			}
			$('#questionario-modal').modal('hide');
		});
		
	}
}

function initTimer() {
    completa = $('#timer').data('completa');
    tempoMinimo = parseInt($('#timer').data('minimo'));
   	if (!completa) {
   		tempoDecorrido = 0;
    	setInterval(updateTimer, 1000);
    } else {
    	tempoDecorrido = parseInt($('#timer').data('tempo'));
    }
   	updateTimer();
   	
   	if (!completa && tempoMinimo > 0) {
	   	var segundos = tempoMinimo % 60;
	    var minutos = Math.floor(tempoMinimo / 60);
	    var horas = Math.floor(minutos / 60);
	   	$('#timer')
	   		.attr('title',
				'Tempo mínimo de leitura: ' + (horas > 0 ? '' + horas + ':' : '') + 
				(horas > 0 && minutos < 10 ? '0' : '') + minutos + ':' + 
				(segundos < 10  ? '0' : '') + segundos)
			.tooltip({ placement: 'bottom' });
   	}
   	
}

function updateTimer() {
	
	var segundos = tempoDecorrido % 60;
    var minutos = Math.floor(tempoDecorrido / 60);
    var horas = Math.floor(minutos / 60);
    minutos %= 60;
	$('#timer').text(
			(horas > 0 ? '' + horas + ':' : '') + 
			(horas > 0 && minutos < 10 ? '0' : '') + minutos + ':' + 
			(segundos < 10  ? '0' : '') + segundos);
	
	//se já completou não precisa mostrar o botão
	if (!completa) {
		var quantidadeQuestoes = $('#timer').data('questoes');
		if (quantidadeQuestoes > 0) {
			mostrarBotao($('#btn-responder'));
		} else {
			mostrarBotao($('#btn-avancar'));
		}
		//incrementa o tempo somente se s a janela estiver com o foco
		if (document.hasFocus()) tempoDecorrido++;
	} else {
		//$('#timer').css('color', '#ccc');
		//TODO colorir?
	}
}

function mostrarBotao(botao) {
	if (tempoDecorrido >= tempoMinimo && botao.hasClass('hidden')) {
		botao.removeClass('hidden');
	}
}

function mostrarQuestionarioModal() {
	var questionario = $('#questionario-modal');
	//carrega questionário no body do modal
	questionario.find(".modal-body" ).load( questionario.data('url') , function() {
		//mostrar modal
		$('#questionario-modal').modal({
			show: true,
			keyboard: false
		});
		new Contador().iniciarContador('span');
	});
}

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