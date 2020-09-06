package filters;

import javax.servlet.annotation.WebFilter;

@WebFilter({ "/usuarios/enderecos" })
public class EnderecoFilter extends AutenticacaoFilter {
  private String[] PRIVATE_METHODS = { "GET", "PUT", "DELETE", "POST" };

  @Override
  protected String[] getPrivateMethods() {
    return PRIVATE_METHODS;
  }
}