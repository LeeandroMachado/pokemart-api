package interfaces;

import java.util.List;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public interface IDao<M> {
  void cadastrar(M m) throws SQLException;
  List<M> listar() throws SQLException;
  List<M> listar(int id) throws SQLException;
  void excluir(int id) throws SQLException;
  void atualizar(int id, M m) throws SQLException;
  List<M> buscar(PreparedStatement st) throws SQLException;
}