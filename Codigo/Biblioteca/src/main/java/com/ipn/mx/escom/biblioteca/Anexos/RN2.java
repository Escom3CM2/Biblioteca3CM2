
package com.ipn.mx.escom.biblioteca.Anexos;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author Diego
 */
public class RN2 {
    
    private static Matcher matcher; //variables expresiones regulares
    private static Pattern pattern;
    
    public static boolean RN2_1b1(String noTt){
       
        return !noTt.isEmpty();
    }
    
    public static boolean RN2_1b2(String noTt){
        String noTtTokens="";
        StringTokenizer tokens=new StringTokenizer(noTt,"-");//quita los guiones
        while(tokens.hasMoreTokens()){
            noTtTokens+=tokens.nextToken();
        }
        noTtTokens=noTtTokens.replaceAll("\\s+","");//quita los espacios
        //String REGEX="^{20}$";//expresion regular ISBN
        String REGEX = "\\d*";//expresion regular noTT
        pattern= Pattern.compile(REGEX); 
        matcher= pattern.matcher(noTtTokens);
        return matcher.matches(); //match de la cadena 
    }
    
}

