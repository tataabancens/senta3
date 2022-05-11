# PAW

## Credenciales

Para loggearse como Restaurant, se deber n usar las siguientes credenciales (case sensitive):

-user: Pepito

-pass: 12345678

## A tener en cuenta

El usuario solo puede hacer reservas en horarios disponibles;

Entre los horarios de apertura y cierre del restaurant, 
luego del tiempo en que el usuario est‚ realizando la reserva 
y cuando tenga sillas/mesas disponibles.

Es decir, si el restaurant cierra a las 24hs, los usuarios que entren a la pagina luego de las 23hs no tendr n mesas disponibles. Esto no es un bug.

# Instalacion de Maven
Link https://www.youtube.com/watch?v=J6yeuluYkYE&ab_channel=ReynaldoBernardDeDiosDeLaCruz

# Intrucciones para clonar el repo y que funcione
1. Una vez clonado el repo se debe abrir el projecto con intelij de la siguiente manera
  - Ir al directorio paw-webapp
  - Dar click al archivo pom.xml
  - Seleccionar la opcion abrir como proyecto
2. Instalar el tomcat
  Entrar a la pagina y descargarlo xD, parecido al maven pero sin la parte del PATH
4. Configurar Intelij + Tomcat
  Link (https://www.youtube.com/watch?v=JIRDMGJ66SE&ab_channel=CoolITHelp)
5. Descargar Postgres, configuracion y pequenia intro
  Link https://www.youtube.com/watch?v=cHGaDfzJyY4&ab_channel=FaztCode
  Yo use pgadmin para crear una database que se llame paw, supongo que se puede hacer desde consola tambien.
6. Integrar con intelij (opcional pero sirve)
  Link https://www.youtube.com/watch?v=D-WoteCPi14&ab_channel=CodeJava
7. En WebConfig setear correctamente el path de la bdd, nombre de usuario y contrasenia
