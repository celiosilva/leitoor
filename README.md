# Leitoor

O Leitoor é uma ferramenta para gestão de atividades ministradas por professores de ensino médio e colegial. A proposta Leitoor é apresentar uma ferramenta que seja fácil ao professor e atrativa aos alunos, onde as atividades possam ser criadas, aplicadas, medidas, avaliadas e compartilhadas. Para saber mais veja nossa <a href="https://drive.google.com/file/d/0B8O7Eidsae0kazBFRDlFVXRDcnM/view?usp=sharing" target="_blank"> apresentação do Leitoor</a>. Se quiser participar entre em contato <a href="mailto:celio@delogic.com.br">conosco</a> e veja como você poderá ajudar a criar algo para sua comunidade.

**Sejam bem vindos Leitoores!**


# Para Desenvolvedores
* A próxima seção trata dos detalhes para quem quiser participar e ajudar a melhorar nossa plataforma. Veja como configurar o seu ambiente e como contribuir com a nossa causa.

# Configurações do Ambiente de Desenvolvimento
## Tecnologias
O Leitoor foi construído com ferramentas opensource e gratuitas na internet. Usamos a plataforma Java no servidor e no cliente apenas HTML, CSS e Javascript. Veja a seguir quais ferramentas devem ser instaladas para montar seu ambiente de desenvolvimento:
```
Java JDK	1.7 (7u60)                    Java Virtual Machine
Eclipse 	Kepler ou SpringToolSuite     IDE
Postgres 	postgresql-9.3.4-1 64bits     Banco de dados
Git 		1.8.3          	              VCS
Maven 		3.0.X                         SCM
Tomcat 		7.X                           Container/Servidor web
pgAdmin 	3                             Ferramenta acesso banco de dados (ou SqlDeveloper 4.X)
```
**Use o tomcat que está incluso no projeto, já vem configurado com log e drivers para banco de dados**

## Configuração do ambiente
1. Instale as tecnologias Java, Eclipse, Git, Maven e sua ferramenta de banco de dados preferida. Se tiver problemas com essa configuração veja como aconselho configurar neste <a href="https://github.com/celiosilva/leitoor/blob/master/docs/guia-instalacao-configuracao.txt" target="blank"> link</a>.
2. Configure o seu Eclipse com o arquivo de formatação contido <a href="https://github.com/celiosilva/leitoor/blob/master/docs/styleformatter.xml" download="styleformatter.xml"> aqui </a>.
3. Instale o Postgres criando o usuário root (criado por padrão) com a senha "manager" na porta 5432 (padrão).
4. Instale o Maven e use o arquivo de configuração settings contido <a href="https://github.com/celiosilva/leitoor/blob/master/docs/settings.xml" download="settings.xml">aqui</a>.
5. Configure o Tomcat dentro do Eclipse adicionando **use-o sem mover de local para que seja atualizado com updates do projeto**
6. Faça checkout do projeto através do github no botão ao lado &rarr;.

## Recursos da aplicação
Para iniciar o Leitoor em seu ambiente de desenvolvimento você precisará criar o banco de dados e configurar algumas variáveis:

* **Banco de dados** - O Leitoor irá se conectar ao banco criado localmente em:
```
  Porta 5432
  Usuário root
  Senha manager
```

## Configuração do projeto
* Instale e configure o tomcat que encontra-se na raiz do projeto em \tomcat dentro do Eclipse. Abra o arquivo server.xml da pasta Servers e:
```
  Altere o parâmetro ${shutdown.porta} para 8005
  Altere o parâmetro ${porta} para 8080 (ou outra porta que esteja desbloqueada e de sua preferência)
```

* Adicione a aplicação Leitoor ao servidor e inicie pela primeira vez. Isso irá gerar erros mas irá criar a configuração inicial do servidor para o próximo passo. Abra o menu de configuracao em Run / Run Configurations. Na aba Arguments, dentro do textarea VM Arguments, inclua ao final os parâmetros e variáveis de ambiente:
```
  -Dspring.profiles.active=DEV
  -DJDBC_CONNECTION_STRING="jdbc:postgresql://localhost:5432/leitoor?user=postgres&password=manager"
  -Dfile.encoding=UTF-8 -Xms128m -Xmx512m -XX:PermSize=64m -XX:MaxPermSize=256m
```

* Crie o banco de dados pela primeira vez *ou sempre que a estrutura da base de dados for alterada*. Para isso execute os inicializador (JUnit) abaixo
```
  mvn test -Dtest=GerarBanco (ou localize a classe GerarBanco no projeto e execute-a via JUnit)
```

* Agora já poderá executar a aplicação (possivelmente http://localhost:8080/leitoor)  Leitoor e começar a contribuir :)!

#Arquitetura do projeto
O sistema possui arquitetura simples, camandas cliente servidor usando padrão MVC. A lista de tecnologias é apresentada abaixo:

```
Tecnologias Cliente
  HTML5
  CSS3
  Javascript
  
Frameworks e bibliotecas Cliente
  jQuery
  Bootstrap
  
Tecnologias Servidor
  Java
  SQL
  
Frameworks e APIs servidor
  Spring
  SpringMVC
  SpringData
  JPA (Eclipselink)
  JSP
  Serlvets
```

# Como Contribuir
#TBD





