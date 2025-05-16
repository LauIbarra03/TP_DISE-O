package utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import java.io.IOException;

public class IfEqHelper implements Helper<Object> {

  @Override
  public Object apply(Object a, Options options) throws IOException {
    Object b = options.param(0, null);

    if (a != null && a.equals(b)) {

      return options.fn(options.context);
    } else {

      return options.inverse(options.context);
    }
  }
}