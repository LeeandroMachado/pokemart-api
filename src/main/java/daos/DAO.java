package daos;

import java.sql.Connection;
import utils.ConexaoBanco;

public class DAO {
  protected Connection con;

  public DAO() {
    this.con = new ConexaoBanco().getConnection();
  }
}