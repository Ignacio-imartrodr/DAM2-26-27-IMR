package ADAT.UD1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class EjemploCopiaArchivos {


   public static void main(String[] args) throws IOException {
       FileInputStream in = null;
       FileOutputStream out = null;
       try {
           in = new FileInputStream("origen.txt");
           out = new FileOutputStream("destino.txt");
           int c;
           while ((c = in.read()) != -1) {
               out.write(c);
           }
       } finally { // Hay que cerrar el flujo en cualquier condición.
           if (in != null) {
               in.close();
           }
           if (out != null) {
               out.close();
           }
       }
   }
}
