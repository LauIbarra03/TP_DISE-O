from django.db import models

# Create your models here.


    
class Localidad(models.Model):
    nombre = models.CharField(max_length=255, unique=True)      


class PersonaVulnerable(models.Model):
    class TipoDocumento(models.TextChoices):
        DNI = 'DNI', 'Documento Nacional de Identidad' 
        PASAPORTE = 'PAS', 'Pasaporte'
        LICENCIA_CONDUCIR = 'LIC', 'Licencia de conducir'
    
    nombre = models.CharField(max_length = 200)
    apellido = models.CharField(max_length = 200, default = "")
    fecha_nacimiento = models.DateField()
    fecha_registro = models.DateField(auto_now_add = True)
    localidad = models.ForeignKey(Localidad, on_delete = models.CASCADE, related_name = "vulnerables", default = 1, db_column = "localidad_id")
    
class SolicitudVianda(models.Model):
    persona_vulnerable = models.ForeignKey(PersonaVulnerable, on_delete = models.CASCADE, related_name = "solicitudes", db_column = "persona_vulnerable_id", default = 1)    
    fecha_solicitud = models.DateField(auto_now_add = True)
    localidad = models.ForeignKey(Localidad, on_delete = models.CASCADE, related_name = "solicitudes", db_column = "localidad_id")





    