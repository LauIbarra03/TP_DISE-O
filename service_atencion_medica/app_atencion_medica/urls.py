from rest_framework import routers
from django.urls import path, include
from app_atencion_medica import views

router = routers.DefaultRouter()

router.register(r'localidades', views.LocalidadViewSet, "localidades")
router.register(r'vulnerables', views.PersonaVulnerableViewSet, "vulnerables")
router.register(r'solicitudes', views.SolicitudViandaViewSet, "solicitudes")
router.register(r'localidades_solicitud', views.LocalidadesSolicitudViewSet, "localidades_solicitud")


urlpatterns = [
    path('', include(router.urls)),
]
