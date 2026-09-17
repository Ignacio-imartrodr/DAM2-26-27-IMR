package ADAT.UD1;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Ej1 {
    public static void main(String[] args) {
        File file = new File("src\\UD1\\pruebo.txt");
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd'/'MM'/'YY", Locale.of("es", "es"));
        if (file.exists()) {
            //ruta absoluta, nombre del archivo, tamaño, última modificación y si es un directorio.
            String size = String.valueOf((file.length())) + " B";
            String lastMod = fechaFormat.format( file.lastModified());
            String tipo = file.isDirectory() ? "Directorio" : "Archivo";
            System.out.printf("Ruta absoluta: %s%nNombre: %s%nTamaño: %s%nÚltima modificación: %s%nTipo de Archivo: %s%n",file.getAbsolutePath(), file.getName(), size, lastMod, tipo);
        } else {
            try {
                System.out.println("El archivo no existe, creandolo de forma temporal...");
                File.createTempFile("prueba", ".txt");
                String size = String.valueOf(file.getTotalSpace()) + " B";
                String tipo = file.isDirectory() ? "Directorio" : "Archivo";
                System.out.printf("Ruta absoluta: %s%nNombre: %s%nTamaño: %s%nTipo de Archivo: %s%n", file.getAbsolutePath(), file.getName(), size, tipo);
            } catch (Exception e) {
                System.out.println("Error crean archivo temporal");
            }
        }
        for (String string : args) {
            System.out.println(string);
        }
    }
}
