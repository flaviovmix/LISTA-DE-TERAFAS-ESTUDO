package app;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConexaoPostGres {
    
    private Connection dataBase;
    
    public Connection abrirConexaoJNDI() {
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/MeuDB");
            dataBase = ds.getConnection();
            return dataBase;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void fecharConexao() {
        try {
            if (dataBase != null && !dataBase.isClosed()) {
                dataBase.close();
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao fechar conexão: " + erro.getMessage());
        }
    }

    public Connection getConexao() {
        return dataBase;
    }
}
