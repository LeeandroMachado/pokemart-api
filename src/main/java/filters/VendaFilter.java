package filters;

import javax.servlet.annotation.WebFilter;

@WebFilter({ "/api/v1/vendas" })
public class VendaFilter extends AutenticacaoFilter {
  private String[] PRIVATE_METHODS = { "GET", "PUT", "DELETE", "POST" };

  @Override
  protected String[] getPrivateMethods() {
    return PRIVATE_METHODS;
  }
}