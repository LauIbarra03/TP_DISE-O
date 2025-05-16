*Servicio 3: Atención Médica*

1. Instalar Python, de la version 3.11.2 para arriba
   puede hacerlo con el siguiente link: https://www.python.org/downloads/
   o buscar en su navegador Python download
1. Luego de instalar Python posiciónese dentro de la carpeta service\_atencion\_medica/, desde una
   terminal o consola. Puede usar el comando cd “direccion\_carpeta”
1. Ya dentro de la carpeta de service\_atencion\_medica/ ejecute el siguiente comando:

   ```pythom -m venv nombre\_entorno\_virtual```<br>
   Este comando creara un entorno virtual, si desea conocer mas de estos puede visitar el siguiente
   link: <https://docs.python.org/3/library/venv.html>

1. Lo siguiente es activar el entorno virutal, para esto debe ejecutar el script llamado activate
   dentro de Script de su nuevo entorno:

   `nombre\_entorno\_virtual\Scripts\activate`

1. Ya con el entorno activado debe ejecutar el siguiente comando ():

   `pip install -r requirements.txt`

1. Con todo hecho solo queda correrlo, para esto debe ejecutar:

   ```python manage.py runserver```

1. Ahora puede acceder a la URL de su local host, dentro de este se la abrirá una vista con las URLS
   disponibles. Las cuales son:
    1. api/ -> con esta puede utilizar la API
    1. docs/ -> con esta puede acceder a la documentación

