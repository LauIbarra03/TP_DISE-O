# Generated by Django 5.1.1 on 2024-09-05 19:34

import django.db.models.deletion
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('app_atencion_medica', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='personavulnerable',
            name='direccion',
        ),
        migrations.AddField(
            model_name='personavulnerable',
            name='localidad',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, related_name='vulnerables', to='app_atencion_medica.localidad'),
        ),
        migrations.DeleteModel(
            name='Direccion',
        ),
    ]
