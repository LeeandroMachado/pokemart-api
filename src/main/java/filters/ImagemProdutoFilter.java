package filters;

import javax.servlet.annotation.WebFilter;

@WebFilter({ "/produtos/imagens" })
public class ImagemProdutoFilter extends AutenticacaoFilter {
  private String[] PRIVATE_METHODS = { "GET", "PUT", "DELETE", "POST" };

  @Override
  protected String[] getPrivateMethods() {
    return PRIVATE_METHODS;
  }
}