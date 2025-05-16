package model.entities.RecomendadorDePuntos;

import model.entities.RecomendadorDePuntos.entities.ErrorRecomendador;
import model.entities.RecomendadorDePuntos.entities.ListadoDePuntos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecomendadorPuntosService {
  @GET("punto")
  Call<ListadoDePuntos> puntoDeColocacionRecomendados(@Query("longitud") double longitud, @Query("latitud") double latitud, @Query("radio") double radio);

  @GET("punto")
  Call<ErrorRecomendador> puntoDeColocacionRecomendados(@Query("longitud") double longitud, @Query("latitud") double latitud);

  @GET("punto_asdasd")
  Call<ErrorRecomendador> puntoDeColocacionRecomendados();
}
