package br.root.filters;

import br.jasap.core.*;
import br.jasap.dao.DataBase;
import br.jasap.dbl.dao.DblGlobalDataBase;
import br.jasap.util.Js;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.commons.dbcp2.PoolingDataSource; 

public final class DataBaseFilter extends Filter {
    
    public static String id;
    public static Integer sgdb;
    public static String host;
    public static Integer port;
    public static String catalog;
    public static String user;
    public static String pass;
    
    private String domain = null;
    private DblGlobalDataBase db = null;
    private PoolingDataSource ds = null;
    private Connection conn = null;
    
    public DataBaseFilter(String server_id, Integer server_sgdb, String server_host, Integer server_port, String server_catalog, String server_user, String server_pass) throws Exception{
        DataBase.setSGDB(server_sgdb);
        DataBaseFilter.id = server_id;
        DataBaseFilter.sgdb = server_sgdb;
        DataBaseFilter.host = server_host;
        DataBaseFilter.port = server_port;
        DataBaseFilter.catalog = server_catalog;
        DataBaseFilter.user = server_user;
        DataBaseFilter.pass = server_pass;
    }
    
    @Override
    public Effect filter(CallStack stack, AppManager manager) throws Exception {
        setManager(manager);
        Effect result = null;
                
        JasapAct  action = (JasapAct)stack.getAction();
        try{
            if (action instanceof NoDBAct){
                
                result = stack.next(manager);
            
            } else {
                
                if (manager.getDataBase().getConn()==null){
                    
                        try {

                            Context initialContext = new InitialContext();
                            Context envContext  = (Context)initialContext.lookup("java:/comp/env");
                            
                            db = (DblGlobalDataBase)envContext.lookup("JasapDBLocal");
                            db.createPool(DataBaseFilter.id, 
                                    DataBaseFilter.sgdb, 
                                    DataBaseFilter.host, 
                                    DataBaseFilter.port, 
                                    DataBaseFilter.catalog, 
                                    DataBaseFilter.user, 
                                    DataBaseFilter.pass);
                            
                            setDs(db.getPoolDataSource(DataBaseFilter.id));
                            setConn(db.getPoolConnection(getDs(), DataBaseFilter.id));
                            manager.getDataBase().setConn(getConn());

                            
                        } catch (Exception e){
                            e.printStackTrace();
                            manager.getDataBase().setFail(true);
                            if (getManager().getSession().isSet(JasapContext.SESSION_USER)){
                                eval(Js.alert("Falha de Autentica��o! Verifique os dados e tente novamente."));
                            } else {
                                eval(Js.alert("Falha de Conex�o com o Banco de Dados! Informe ao suporte."));
                            }
                        }

                } else {
                    //System.out.println("DB READY - "+manager.getDataBase().getCatalog());
                }

                //antes action
                result = stack.next(manager);
                //depois action
                
            }
        } catch (Exception e ){
            throw e;
        }finally{
            if (manager.getDataBase().getConn()!=null && !manager.getDataBase().isClosed()){
                try {
                    manager.getDataBase().commit(true);   
                    manager.getDataBase().closeConnection(true);
                } catch (Exception e ){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void setCatalog(String catalog) throws SQLException {
        if(this.conn!=null)this.conn.setCatalog(catalog);
    }
    
    public String getCatalog() throws SQLException {
        return this.conn.getCatalog();
    }
    
    public void closeConnection(Boolean close) throws Exception {
        if (conn != null && !conn.isClosed() && close) {
             if(isTransaction())conn.commit();
            conn.close();
        }
    }

    public boolean isTransaction() throws Exception {
        if (conn.getAutoCommit())return false;
        else return true;
    }

    public Boolean isClosed() throws Exception {
        if (conn==null){
            return true;
        }else{
            return conn.isClosed();
        }
    }

    public PoolingDataSource getDs() {
        return ds;
    }
    public void setDs(PoolingDataSource ds) {
        this.ds = ds;
    }
}
