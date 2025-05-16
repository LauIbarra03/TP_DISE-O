from rest_framework import viewsets
from .models import Localidad, SolicitudVianda, PersonaVulnerable
from .serializer import LocalidadSerializer, SolicitudViandaSerializer, PersonaVulnerableSerializer, LocalidadesSolicitudSerializer
from django.db.models import Count, Value
from django.db.models.functions import Concat
from rest_framework.response import Response
from django.utils import timezone
from datetime import date
from django.urls import get_resolver
from django.http import HttpResponse

# Create your views here.
class LocalidadViewSet(viewsets.ModelViewSet):
    queryset = Localidad.objects.all()
    serializer_class = LocalidadSerializer
    
class PersonaVulnerableViewSet(viewsets.ModelViewSet):
    queryset = PersonaVulnerable.objects.all()
    serializer_class = PersonaVulnerableSerializer
    
class SolicitudViandaViewSet(viewsets.ModelViewSet):
    queryset = SolicitudVianda.objects.all()
    serializer_class = SolicitudViandaSerializer

class LocalidadesSolicitudViewSet(viewsets.ViewSet):
    """
    ViewSet que devuelve una lista de localidades con la cantidad de personas en situación de calle 
    que solicitaron al menos una vianda, y sus nombres.
    """
    def list(self, request):
        # Filtrar localidades donde al menos una persona vulnerable ha solicitado viandas
        localidades_solicitudes = (
            Localidad.objects
            .filter(
                solicitudes__persona_vulnerable__isnull = False, 
                solicitudes__fecha_solicitud = timezone.now().date())
            .annotate(cantidad=Count('solicitudes__persona_vulnerable', distinct=True))
        )

        # Preparar la estructura de datos para pasar al serializador
        resultados = []
        for localidad in localidades_solicitudes:
            # Obtener los nombres de las personas que solicitaron viandas en esta localidad
            nombres = (
                PersonaVulnerable.objects
                .filter(solicitudes__localidad=localidad, solicitudes__fecha_solicitud=date.today())  # Filtra por la localidad actual y la fecha de hoy
                .annotate(nombre_completo=Concat('apellido', Value(', '), 'nombre'))  # Concatena apellido y nombre
                .values_list('nombre_completo', flat=True)
                .distinct()  # Elimina duplicados si la misma persona hizo más de una solicitud
            )
            
            resultado = {
                'localidad': localidad.nombre,
                'cantidad': localidad.cantidad,
                'nombres': list(nombres),
            }
            resultados.append(resultado)
            
        # Estructurar la respuesta como "localidades"
        respuesta = {
            "localidades": resultados
        }

        # Devolver la respuesta con el serializador
        serializer = LocalidadesSolicitudSerializer(respuesta['localidades'], many=True)
        return Response({'localidades': serializer.data})
    
        # SIN EL NOMBRE EN LA LISTA
        # serializer = LocalidadesSolicitudSerializer(resultados, many=True)
        # return Response(serializer.data)
def list_urls(request):
    # Obtener todas las URLs del proyecto
    url_patterns = get_resolver().url_patterns
    
    # Crear una lista de las rutas disponibles
    urls = []
    for pattern in url_patterns:
        if hasattr(pattern, 'pattern'):
            url_str = str(pattern.pattern)
            # Excluir '/admin/' y la ruta vacía
            if url_str != 'admin/' and url_str != '':
                urls.append(str(pattern.pattern))
    
    host = request.get_host()
    response_text = "<h1>Rutas disponibles</h1><ul>"
    for url in urls:
        full_url = f"http://{host}/{url}"  # Crear la URL completa con el host
        response_text += f'<li><a href="{full_url}">{full_url}</a></li>'
    response_text += "</ul>"
    
    return HttpResponse(response_text)