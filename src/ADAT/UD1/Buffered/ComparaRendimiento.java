package ADAT.UD1.Buffered;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Crea un programa que escriba un número de bytes introducido por el usuario
 * (por defecto 1.000.000) de dos formas:
 * a) con FileOutputStream directo,
 * b) con BufferedOutputStream.
 * 
 * Mide el tiempo empleado por cada método, por ejemplo con System.nanoTime() y
 * muestra el resultado de la comparación.
 * 
 * Amplía el experimento permitiendo al usuario elegir el tamaño del buffer y
 * guardando en un fichero de texto (PruebasRendimiento.txt) las condiciones y
 * los resultados de cada prueba.
 * 
 * @author Ignacio MR
 */
public class ComparaRendimiento {
    private final static String RUTA_BASIC = "src\\ADAT\\UD1\\Buffered\\DocPruebas\\FileOutput.bin";
    private final static String RUTA_BUFFER = "src\\ADAT\\UD1\\Buffered\\DocPruebas\\BufferedOutput.bin";
    private final static String RUTA_RECORD = "src\\ADAT\\UD1\\Buffered\\DocPruebas\\PruebasRendimiento.txt";

    /**
     * Lee un archivo de texto por lineas utilizando un buffer
     * 
     * @param rutaObjetivo ruta al archivo de texto
     * @return Una {@code Lista<String>} conteniendo un {@code String} por línea en
     *         el archivo si todo sale bien.
     *         <p>
     *         En caso de error la lista contiene
     *         {@code "-1"} en la primera posición, la causa del error y el número
     *         asignado al error en última posición.
     *         <p>
     *         - {@code "0"}: Archivo no encontrado
     *         <p>
     *         - {@code "1"}: Error Entrada/Salida
     *         <p>
     *         - {@code "2"}: Error desconocido
     */
    public static List<String> leerTxt(String rutaObjetivo) {
        List<String> error = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(rutaObjetivo))) {
            List<String> txt = new ArrayList<>(in.readAllLines());
            return txt;
        } catch (FileNotFoundException e) {
            error.add("-1");
            error.add("No se encuentra el fichero de la ruta: " + rutaObjetivo);
            error.add("0");
        } catch (IOException e) {
            error.add("-1");
            error.add("Error Entrada/Salida");
            error.add(e.getStackTrace().toString());
            error.add("1");
        } catch (Exception e) {
            error.add("-1");
            error.add("Error desconocido");
            error.add(e.getStackTrace().toString());
            error.add("2");
        }
        return error;
    }
    private static Integer pedirNum(){
        Integer res;
        try (Scanner sc = new Scanner(System.in)) {
            String txt = sc.nextLine();
            for (int i = 0; i < txt.length(); i++) {
                if (!Character.isDigit(txt.charAt(i)) || txt.charAt(i) == '-') {
                    return null;
                }
            }
            res = Integer.valueOf(txt);
        } catch (Exception e) {
            res = null;
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println("Cantidad de Bytes?");
        Integer numBytes = pedirNum();
        if (numBytes == null) {
            numBytes = 1000000;
            System.out.println("Cantidad de Bytes inválida, utilizando " + numBytes + " bytes en su lugar");
        }
        
        String recordBasic = "FileOutput time: ";
        String recordBuffer = "BufferedOutput time: ";

        try (FileOutputStream outA = new FileOutputStream(RUTA_BASIC);) {

            long inicio = System.nanoTime();
            for (int i = 0; i < numBytes; i++) {
                outA.write(1);
            }
            outA.flush();
            long fin = System.nanoTime();
            recordBasic += (fin - inicio) + " ms";
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedOutputStream outB = new BufferedOutputStream(new FileOutputStream(RUTA_BUFFER))) {
            long inicio = System.nanoTime();
            for (int i = 0; i < numBytes; i++) {
                outB.write(1);

            }
            outB.flush();
            long fin = System.nanoTime();
            recordBuffer += (fin - inicio) + " ms";
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fullRecord = "Cant: " + numBytes + " bytes \n" + recordBasic + "\n" + recordBuffer;
        System.out.println(fullRecord);

        File fileOut = new File(RUTA_RECORD);
        List<String> preRecord = null;
        try {
            if (!fileOut.exists()) {
                fileOut.createNewFile();
            } else {
                preRecord = leerTxt(RUTA_RECORD);
            }
        } catch (Exception e) {
            System.out.println("Error creando el archivo de Record");
        }
        try (var out = new BufferedWriter(new FileWriter(RUTA_RECORD));) {
            if (preRecord == null) {
                out.write(fullRecord);
                out.flush();
            } else {
                String separador = "------------------------";
                int sizePreRecord = preRecord.size();
                if (sizePreRecord > 0 && !preRecord.getFirst().equals("-1")) {
                    for (int i = 0; i < sizePreRecord; i++) {
                        out.write(preRecord.get(i) + "\n");
                        if (i == sizePreRecord - 1) {
                            out.write(separador + "\n");
                        }
                    }
                    out.write(fullRecord);
                    out.flush();
                } else {
                    for (int i = 1; i < sizePreRecord - 1; i++) {
                        System.out.println(preRecord.get(i));
                    }
                }
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado");
        } catch (IOException IOE) {
            System.out.println("Error de E/S");
        } catch (Exception ex){}
    }
}
