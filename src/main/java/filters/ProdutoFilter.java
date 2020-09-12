package filters;

import javax.servlet.annotation.WebFilter;

@WebFilter({ "/api/v1/produtos" })
public class ProdutoFilter extends AutenticacaoFilter {
  private String[] PRIVATE_METHODS = { "PUT", "DELETE", "POST" };

  @Override
  protected String[] getPrivateMethods() {
    return PRIVATE_METHODS;
  }
}