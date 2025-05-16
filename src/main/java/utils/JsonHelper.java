package utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonHelper implements Helper<Object> {
  private final Gson gson;

  public JsonHelper() {
    this.gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
            new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
        .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
            new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
        .create();
  }

  @Override
  public Object apply(Object context, Options options) throws IOException {
    return gson.toJson(context);  // No escapar el JSON
  }
}
