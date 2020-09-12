package filters;

import javax.servlet.annotation.WebFilter;

@WebFilter({ "/api/v1/formas_pagamento" })
public class FormaPagamentoFilter extends AutenticacaoFilter {
  private String[] PRIVATE_METHODS = { "GET", "PUT", "DELETE", "POST" };

  @Override
  protected String[] getPrivateMethods() {
    return PRIVATE_METHODS;
  }
}