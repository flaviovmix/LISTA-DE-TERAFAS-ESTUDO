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

            // Tentar obter uma conex�o
            try (Connection conn = ds.getConnection()) {
                if (conn != null && !conn.isClosed()) {
                    System.out.println("? Conex�o JNDI bem-sucedida!");
                } else {
                    System.out.println("? Conex�o JNDI retornou null ou est� fechada.");
                }
            }

        } catch (Exception e) {
            System.out.println("? Erro ao obter conex�o via JNDI");
            e.printStackTrace();
        }
    }
}
