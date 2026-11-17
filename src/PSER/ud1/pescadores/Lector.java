package PSER.pescadores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lector {
    private BufferedReader in;

    public Lector(File conversacion) throws FileNotFoundException{
        in = new BufferedReader(new FileReader(conversacion));
    }
    
    public synchronized void leer(Pescador p) {
        String frase = null;
        try {
            in.read();
            int codePoint;
            char letra;
            while ((codePoint = in.read()) != -1 && (letra = Character.toChars(codePoint)[0]) != '—') {
                frase = "";
                do {
                    if (letra == '\n' || letra == '\r') {
                        letra = ' ';
                    }
                    frase += letra;
                } while ((codePoint = in.read()) != -1 && (letra = Character.toChars(codePoint)[0]) != '—');
                p.hablar(frase.strip());
                notify();
                wait();
            }
        } catch (IOException e) {
            frase = "Error leyendo";
            p.hablar(frase);
        } catch (InterruptedException e) {
            frase = "Error esperando";
            p.hablar(frase);
        }
        notify();
    }
}
