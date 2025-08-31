package br.root.filters;

import br.jasap.core.AppManager;
import br.jasap.core.CallStack;
import br.jasap.core.Effect;
import br.jasap.core.Filter;
import br.jasap.core.JasapAct;
import br.jasap.util.exceptions.JasapAlertException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ErrorFilter extends Filter {
    
    @Override
    public Effect filter(CallStack stack, AppManager manager) throws Exception {
        setManager(manager);
        Effect result = null;
        
        JasapAct action = stack.getAction();
        try{
            
            //antes de chamar a action
            
            result = stack.next(manager);
            
            //depois de executar a action
        
        } catch (Exception e){
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            
            if (e instanceof JasapAlertException){
                System.out.println("alert()->"+e.getMessage());
            } else {
                e.printStackTrace();
            }
            throw e;
        } 
        return result;
    }
    
}
