package filters;

import javax.servlet.annotation.WebFilter;

@WebFilter({ "/produtos" })
public class ProdutoFilter extends AutenticacaoFilter {
  private String[] PRIVATE_METHODS = { "PUT", "DELETE", "POST" };

  @Override
  protected String[] getPrivateMethods() {
    return PRIVATE_METHODS;
  }
}