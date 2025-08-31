package app.configuracao;

import app.ConexaoPostGres;
import java.sql.*;

public class ConfiguracaoDAO {
    private ConexaoPostGres dataBase;

    public ConfiguracaoDAO() {
        dataBase = new ConexaoPostGres();
        dataBase.abrirConexaoJNDI();
    }
        
    public void alterarTema(int tema, int id_configuracao) {
        
        String sql = "UPDATE configuracao SET tema = ? WHERE id_configuracao = ?;";
        
        try (PreparedStatement ps = dataBase.getConexao().prepareStatement(sql);) {
            
            ps.setInt(1, tema);
            ps.setInt(2, id_configuracao);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void selecionarTema(ConfiguracaoBean configuracao) {
        
        String sql = "SELECT * FROM configuracao;";
        
        try (
                PreparedStatement ps = dataBase.getConexao().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
            ) {
            
            if (rs.next()) {
                configuracao.setTema(rs.getInt("tema"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
        
    public void fecharConexao() {
        dataBase.fecharConexao();
    }
    
}
