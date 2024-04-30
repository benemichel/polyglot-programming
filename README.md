# Project
Das vorliegende Projekt besteht als einzelnen Testfällen und einer polyglotten Beispielappliaktion.

# Setup
Zum Ausführen der Testfälle und der Beispielapplikation wird Java und Docker benötigt.

# Testfälle
Die Testfälle dienen der Verifizierung der in der Abschlussarbeit aufgezeigten Anwendungsfälle und MEchanismen der polyglotten Programmierung mit GraalVM.

Die Testfälle werden mit folgendem Befehl ausgeführt:

``mvn clean test``

# Polyglotte Beispielapplikation
Die Beispielapplikation ist mithilfe von Docker containerisiert. Um sie zu starten, muss folgender Befehl ausgeführt werden:

``docker-compose up``

Die Applikation is daraufhin über eine lokale Webseite erreichbar:

http://localhost:8080/swagger-ui/index.html

Die beigefügten Groovy Testfälle werden mit folgendem Befehl ausgeführt:

``mvn -Dtest=JavaRecommendationServiceImplSpec clean test``