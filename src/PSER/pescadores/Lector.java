package PSER.pescadores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Lector {
    private BufferedReader in;

    public Lector(File conversacion) throws FileNotFoundException{
        in = new BufferedReader(new FileReader(conversacion));
    }
    
    public synchronized String leer() {
        String frase = null;
        try {
            in.read();
            int codePoint;
            char letra;
            if ((codePoint = in.read()) != -1 && (letra = Character.toChars(codePoint)[0]) != '—') {
                frase = "";
                do {
                    if (letra == '\n' || letra == '\r') {
                        letra = ' ';
                    }
                    frase += letra;
                } while ((codePoint = in.read()) != -1 && (letra = Character.toChars(codePoint)[0]) != '—');
            }
        } catch (Exception e) {
            frase = "Error leyendo";
        }
        notify();
        return frase;
    }
}
