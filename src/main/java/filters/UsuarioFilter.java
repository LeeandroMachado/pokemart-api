package filters;

import javax.servlet.annotation.WebFilter;

@WebFilter({ "/api/v1/usuarios" })
public class UsuarioFilter extends AutenticacaoFilter {
  private String[] PRIVATE_METHODS = { "GET", "PUT", "DELETE" };

  @Override
  protected String[] getPrivateMethods() {
    return PRIVATE_METHODS;
  }
}