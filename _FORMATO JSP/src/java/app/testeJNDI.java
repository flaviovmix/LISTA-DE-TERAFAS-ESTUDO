package app;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class testeJNDI {
    public static void main(String[] args) {
        try {
            // Obter o contexto JNDI do Tomcat
            InitialContext ic = new InitialContext();

            // Localizar o DataSource configurado no context.xml
            DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/MeuDB");

            // Tentar obter uma conexão
            try (Connection conn = ds.getConnection()) {
                if (conn != null && !conn.isClosed()) {
                    System.out.println("? Conexão JNDI bem-sucedida!");
                } else {
                    System.out.println("? Conexão JNDI retornou null ou está fechada.");
                }
            }

        } catch (Exception e) {
            System.out.println("? Erro ao obter conexão via JNDI");
            e.printStackTrace();
        }
    }
}
