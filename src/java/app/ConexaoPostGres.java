package app;

// Importa��es da API JDBC para trabalhar com banco de dados
import java.sql.Connection;       // Interface que representa uma conex�o com o banco
import java.sql.DriverManager;    // Classe que cria conex�es via URL, usu�rio e senha
import java.sql.SQLException;     // Classe que representa erros de SQL (como falha ao conectar)


public class ConexaoPostGres {
    //private -> S� pode ser acessada dentro da pr�pria classe.
    //final -> S� pode ser definida uma vez (normalmente no construtor) e n�o pode ser alterada depois.
    //String -> Uuma classe do pacote java.lang usada para representar sequ�ncias de caracteres.
    //host, porta, banco, usuario, senha -> Vari�veis que armazenam as informa��es da conex�o com o banco.
    private final String host;    
    private final String porta;   
    private final String banco;   
    private final String usuario; 
    private final String senha;  


    
    // Vari�vel privada do tipo 'Connection'. N�o � um tipo primitivo, e sim uma interface da biblioteca JDBC.
    // Ser� usada para enviar comandos SQL ao banco de dados e receber os resultados.
    private Connection conexaoComBanco;

    
    
    // Construtor p�blico chamado ConexaoPostGres que recebe o nome do banco de dados como par�metro.
    // Os demais par�metros (host, porta, usu�rio e senha) s�o definidos com valores fixos.
    public ConexaoPostGres(String nomeDoBanco) {
        
        // Atribui o valor recebido no par�metro ao atributo 'banco'
        banco = nomeDoBanco;

        // Define os demais par�metros de conex�o com valores fixos
        host = "localhost";      // Endere�o do servidor (localhost = computador local)
        porta = "5432";          // Porta padr�o do PostgreSQL
        usuario = "postgres";    // Nome de usu�rio padr�o do PostgreSQL
        senha = "masterkey";     // Senha do usu�rio (n�o recomend�vel deixar fixa em produ��o)

    }
    
    
    // M�todo p�blico chamado 'abrirConexao'.
    // Ele retorna um objeto do tipo 'Connection', que representa uma conex�o com o banco de dados.
    // 'Connection' n�o � um tipo primitivo, e sim uma interface da biblioteca JDBC (pacote java.sql).
    // Esse objeto � usado para enviar comandos SQL ao banco e manipular os resultados.
    public Connection abrirConexao() {
        
        //Monte a URL de conex�o com o banco de dados PostgreSQL, usando o host, a porta e o nome do banco, e guarde tudo na vari�vel de texto url.�
        String url = "jdbc:postgresql://" + host + ":" + porta + "/" + banco;
        
        //Tente executar este bloco de c�digo.
        try {
            //carrega a classe do driver JDBC do PostgreSQL dinamicamente em tempo de execu��o.
            Class.forName("org.postgresql.Driver");
            
            //Essa linha abre uma conex�o com o banco de dados e atribui � vari�vel conexao.
            conexaoComBanco = DriverManager.getConnection(url, usuario, senha);
        } 
        
        //Se acontecer um erro relacionado ao banco de dados, capture esse erro e armazene na variavel erro.
        catch (SQLException erro) {
            //Mostre no console a mensagem de erro que ocorreu ao tentar fechar a conex�o com o banco.
            System.out.println("Erro ao conectar ao banco: " + erro.getMessage());
        } 
        
        //Se o Java n�o conseguir encontrar a classe do driver, capture esse erro e armazene na variavel erro.
        catch (ClassNotFoundException erro) {
            System.out.println("Driver JDBC n�o encontrado: " + erro.getMessage());
        } 
        
        //Pegue qualquer erro gen�rico que n�o tenha sido capturado antes, e armazene na variavel erro.
        catch (Exception erro) {
            System.out.println("Erro inesperado: " + erro.getMessage());
        }
        
        //Devolva a conex�o com o banco de dados que est� guardada na vari�vel conexaoComBanco."
        return conexaoComBanco;
    }
    
    
    
    //M�todo p�blico que n�o retorna nada chamado fecharConexao.
    public void fecharConexao() {
        
        //Tente executar este bloco de c�digo.
        try {
            
            //Se a conex�o com o banco n�o for nula e ainda n�o estiver fechada
            if (conexaoComBanco != null && !conexaoComBanco.isClosed()) {
                
                //Fecha a conex�o com o banco de dados
                conexaoComBanco.close();
            }
            
        //Se acontecer um erro relacionado ao banco de dados, capture esse erro e armazene na variavel erro.
        } catch (SQLException erro) {
            System.out.println("Erro ao fechar conex�o: " + erro.getMessage());
        }
    }
    
    
    
    //Obter a conex�o com o banco que essa classe est� usando agora."
    public Connection getConexao() {
        return conexaoComBanco;
    }
    
    
    
    //Troque a conex�o que essa classe est� usando por essa nova aqui que eu estou passando.
    public void setConexao(Connection conexao) {
        this.conexaoComBanco = conexao;
    }

}
