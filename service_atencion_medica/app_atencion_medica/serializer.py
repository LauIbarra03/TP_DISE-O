from rest_framework import serializers
from .models import PersonaVulnerable, SolicitudVianda, Localidad


class PersonaVulnerableSerializer(serializers.ModelSerializer):
    class Meta:
        model = PersonaVulnerable
        fields = '__all__'
        
class SolicitudViandaSerializer(serializers.ModelSerializer):
    class Meta:
        model = SolicitudVianda
        fields = '__all__'

class LocalidadSerializer(serializers.ModelSerializer):
    class Meta:
        model = Localidad
        fields = '__all__'

class LocalidadesSolicitudSerializer(serializers.Serializer):
    localidad = serializers.CharField(max_length = 100)
    cantidad = serializers.IntegerField()
    nombres = serializers.ListField(
        child = serializers.CharField(max_length = 100)
    )
