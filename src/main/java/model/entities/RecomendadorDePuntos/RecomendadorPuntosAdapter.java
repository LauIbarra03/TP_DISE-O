package model.entities.RecomendadorDePuntos;

import java.io.IOException;
import java.lang.annotation.Annotation;
import model.entities.RecomendadorDePuntos.entities.ErrorRecomendador;
import model.entities.RecomendadorDePuntos.entities.ListadoDePuntos;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecomendadorPuntosAdapter {
  private static final String URL_API = "https://7091ae82-f797-4581-87c0-6ccfc09eb141.mock.pstmn.io/api/";
  private static RecomendadorPuntosAdapter instancia = null;
  private final Retrofit retrofit;


  private RecomendadorPuntosAdapter() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(URL_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static RecomendadorPuntosAdapter getInstance() {
    if (instancia == null) {
      instancia = new RecomendadorPuntosAdapter();
    }
    return instancia;
  }


  public ListadoDePuntos puntoDeColocacionRecomendados(double longitud, double latitud, double radio) throws IOException {
    RecomendadorPuntosService recomendadorPuntosService = this.retrofit.create(RecomendadorPuntosService.class);
    Call<ListadoDePuntos> requestListadoDePuntos = recomendadorPuntosService.puntoDeColocacionRecomendados(longitud, latitud, radio);
    Response<ListadoDePuntos> responseListadoDePuntos = requestListadoDePuntos.execute();
    return responseListadoDePuntos.body();
  }

  public ErrorRecomendador puntoDeColocacionRecomendados(double longitud, double latitud) throws IOException {
    RecomendadorPuntosService recomendadorPuntosService = this.retrofit.create(RecomendadorPuntosService.class);
    Call<ErrorRecomendador> requestListadoDePuntos = recomendadorPuntosService.puntoDeColocacionRecomendados(longitud, latitud);
    Response<ErrorRecomendador> responseListadoDePuntos = requestListadoDePuntos.execute();
    if (!responseListadoDePuntos.isSuccessful()) {
      // Manejo de error explícito
      Converter<ResponseBody, ErrorRecomendador> errorConverter =
          retrofit.responseBodyConverter(ErrorRecomendador.class, new Annotation[0]);
      ErrorRecomendador errorResponse = errorConverter.convert(responseListadoDePuntos.errorBody());
      return errorResponse;
    }
    return responseListadoDePuntos.body();
  }

  public ErrorRecomendador puntoDeColocacionRecomendados() throws IOException {
    RecomendadorPuntosService recomendadorPuntosService = this.retrofit.create(RecomendadorPuntosService.class);
    Call<ErrorRecomendador> requestListadoDePuntos = recomendadorPuntosService.puntoDeColocacionRecomendados();
    Response<ErrorRecomendador> responseListadoDePuntos = requestListadoDePuntos.execute();
    if (!responseListadoDePuntos.isSuccessful()) {
      // Manejo de error explícito
      Converter<ResponseBody, ErrorRecomendador> errorConverter =
          retrofit.responseBodyConverter(ErrorRecomendador.class, new Annotation[0]);
      ErrorRecomendador errorResponse = errorConverter.convert(responseListadoDePuntos.errorBody());
      return errorResponse;
    }

    return responseListadoDePuntos.body();
  }
}



