package PSER;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utiles {
    // implementación implode de PHP. A partir de Java 8 usar String.join o similar con StringBuilder
    static public String implode(String delimitador, String[] lista) {
        StringBuilder resultado=new StringBuilder();
        Iterator<String> it= Arrays.asList(lista).iterator();
        while(it.hasNext())
        {
            resultado.append(it.next());
            if(it.hasNext())
                resultado.append(delimitador);
        }
        return resultado.toString();
    }



    static public boolean NifOk(String nif) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        Pattern pattern = Pattern.compile("(\\d{8})([" + letras + "])",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nif);
        if (matcher.matches()) {
            int numero = Integer.parseInt(matcher.group(1));
            String letra = matcher.group(2);
            char letraCorrecta = letras.charAt(numero % 23);
            if (letra.toUpperCase().charAt(0) == letraCorrecta) return true;
        }
        return false;
    }


    // Gracias a http://www.coderblog.de/producing-the-same-sha-512-hash-in-java-and-php/
    static public String generateHash(String texto) {
        MessageDigest md = null;
        byte[] hash = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            hash = md.digest(texto.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
        return Utiles.convertToHex(hash);
    }
    static private String convertToHex(byte[] raw) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < raw.length; i++) {
            sb.append(Integer.toString((raw[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}