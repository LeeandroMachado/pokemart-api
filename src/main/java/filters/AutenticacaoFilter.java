package filters;

import daos.UsuarioDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Usuario;
import org.apache.commons.lang3.ArrayUtils;
import utils.JWT;

public class AutenticacaoFilter implements Filter {
  protected String[] getPrivateMethods() {
    return new String[]{};
  }

  private void validarJWT(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession session) throws IOException , SQLException {
    if (httpServletRequest.getHeader("authorization") == null) {
      throw new SignatureException("Token não fornecido.");
    }

    String token = httpServletRequest.getHeader("authorization").replace("Bearer ", "");
    Claims claim = JWT.decodeJWT(token);

    if(claim.getSubject() != null) {
      UsuarioDAO udao = new UsuarioDAO();
      Usuario u = udao.listarUm(Integer.parseInt(claim.getId()));

      session.setAttribute("usuario", u);
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    System.out.println("AutenticacaoFilter");
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    String method = httpServletRequest.getMethod();
    HttpSession session = httpServletRequest.getSession();

    if (ArrayUtils.contains(this.getPrivateMethods(), method)) {
      try {
        validarJWT(httpServletRequest, httpServletResponse, session);
      } catch (ExpiredJwtException e) {
        System.out.println("ExpiredJwtException: " + e.toString());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      } catch (SignatureException e) {
        System.out.println("SignatureException: " + e.toString());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      } catch (SQLException e) {
        System.out.println("SQLException: " + e.toString());
        httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return;
      } catch (Exception e) {
        System.out.println("Exception: " + e.toString());
        httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return;
      }

      chain.doFilter(request, response);
      session.invalidate();
    } else {
      chain.doFilter(request, response);
    }
  }
}