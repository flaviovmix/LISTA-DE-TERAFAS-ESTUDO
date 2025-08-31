package app;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Utilidades {
    public static String nullTrim(Integer valor){
        if(valor == null){
            return "";
        } else {
            return valor.toString();
        }
    }

    public static String nullTrim(String valor){
        if(valor == null){
            return "";
        } else {
            return valor.trim();
        }
    }
    
    public static String nullTrim(Date valor){
        if(valor == null){
            return "";
        } else {
            return valor.toString();
        }
    }  
    
    public static String arrumarCaractereHtmlJs(String texto) {
        if (texto == null) return "";

        return texto.trim()
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        // Para HTML
        //.replace("\"", "&quot;") // só use se o contexto for atributo HTML
        // Para JS (escapa mantendo as aspas no código)
        .replace("\"", "\\\"")
        .replace("'", "\\'")
        .replace("\\", "\\\\")
        .replace("/", "\\/")
        .replace("`", "&#96;")
        .replace("\n", "\\n")
        .replace("\r", "\\r");
    }

    public static String dateToString(Date data, String pattern) {
        String result = "";
        if (data != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            result = formatter.format(data);
        }
        return result;
    }
    
    public static java.sql.Date stringToDate(String data, String pattern) {
        data = nullTrim(data);
        if (data.isEmpty()) {
            return null;
        } else {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                formatter.setLenient(false);
                java.util.Date utilDate = formatter.parse(data);
                return new java.sql.Date(utilDate.getTime()); // converte para java.sql.Date
            } catch (Exception e) {
                return null;
            }
        }
    }

    
}
