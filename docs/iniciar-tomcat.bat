@echo on
set "catalina_home=%CD%\tomcat"
set "bin=%catalina_home%\bin"

set SERVER_OPTS=-Dporta=80 -Dshutdown.porta=8005 -Daplicacao="..\..\leitoor\target\leitoor-1.0.0-SNAPSHOT" -DAPP_HOME="..\..\leitoor\target"
set LOGGING_OPTS=-Dlog4j.configuration=log4j-conf.properties -Dlog4j.category=info -Dlog4j.file=%catalina_home%\logs\leitoor.log -Dlog4j.appenders="CONSOLE, ROLLING_FILE"
set MEMORY_OPTS=-Djava.awt.headless=true -Dfile.encoding=UTF-8 -Xms64m -Xmx128m
set APP_OPTS=-Dspring.profiles.active=PROD -DJDBC_CONNECTION_STRING="jdbc:postgresql://localhost:5432/leitoor?user=postgres&password=manager"

set JAVA_OPTS=%SERVER_OPTS% %MEMORY_OPTS% %APP_OPTS% %LOGGING_OPTS%

call "%bin%\startup.bat"