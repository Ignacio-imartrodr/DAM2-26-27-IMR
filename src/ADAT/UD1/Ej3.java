package ADAT.UD1;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Ej3 {
    public static void main(String[] args) {
        Integer opcion = opcionMenu();
        
        while (opcion != null && opcion >= 1 && opcion <= 4) {
            File archivo = null;
            switch (opcion) {
                case 1:// crear archivo
                //crearDirectorio()
                    archivo = selectorArchivo(true, true);
                    if (archivo != null) {
                        if (archivo.exists()) {
                            System.out.println("El directorio ya existe");
                        } else {
                            if(!archivo.mkdir()){
                                System.out.println("Error creando el directorio");
                            }
                        }
                    }
                    break;
                case 2://listar archivos
                //listaRecursivo()
                    archivo = selectorArchivo(true, false);
                    if (archivo != null) {
                        if (archivo.exists()) {
                            System.out.println(archivo.getAbsolutePath());
                            listaRecursivo(archivo, 0);
                        } else {
                            System.out.println("Directorio no válido");
                        }
                    }
                    break;
                case 3://eliminar archivos
                    archivo = selectorArchivo(false, false);
                    if (archivo != null) {
                        if (archivo.exists()) {
                            eliminarArchivo(archivo);
                        } else {
                            System.out.println("Archivo no válido");
                        }
                    }
                    break;
                case 4:
                    archivo = selectorArchivo(false, false);
                    if (archivo != null) {
                        if (archivo.exists()) {
                            if(!renombrarOMover(archivo)){
                                System.out.println("Error en el proceso");
                            }
                        } else {
                            System.out.println("Archivo no válido");
                        }
                    }
                    break;
                default:
                    break;
            }
            opcion = opcionMenu();
        }
    }

    private static boolean eliminarArchivo(File archivo){
        if (archivo.isDirectory()){
            for (File file : archivo.listFiles()) {
                eliminarArchivo(file);
            }
        }
        return archivo.delete();
    }

    private static void listaRecursivo(File directorio, int nivel) {
        for (File file : directorio.listFiles()) {
            boolean isDir = file.isDirectory();
            for (int i = 0; i < nivel; i++) {
                System.out.print("  ");
            }
            System.out.println((isDir ? "+ " : "- ") + file.getName() + (isDir ? " DIR" : " (" + file.length() + " B) "));
            if (file.isDirectory() && file.listFiles().length != 0) {
                    listaRecursivo(file, nivel + 1);
            }
        }
    }

    private static boolean renombrarOMover(File archivo){
        boolean flag = false;
        Integer opcion = opcion4();
        if (opcion != null && opcion >= 1 && opcion <= 2)
        switch (opcion) {
            case 1://mover
                String ruta;/* = selectorArchivo(true, false).getAbsolutePath();
                flag = archivo.renameTo(new File(ruta));*/
                flag = archivo.renameTo(selectorArchivo(true, false));
                break;
            case 2://renombrar
                System.out.println("Nuevo nombre?");
                String nombre = pedirTexto().strip();
                if (nombre.length() > 0) {
                    ruta = archivo.getAbsolutePath();
                    ruta = ruta.substring(0, ruta.lastIndexOf("/"));
                    flag = archivo.renameTo(selectorArchivo(false, true));
                } else {
                    System.out.println("Nombre no válido");
                    flag = false;
                }   
                break;
            default://cancelar
                flag = false;
                break;
        }
        return flag;
    }

    private static File selectorArchivo(boolean onlyDirectory, boolean crear){
        JFileChooser chooser = new JFileChooser();
        int selector;
        if (!crear) {
            chooser.setFileSelectionMode(onlyDirectory ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_AND_DIRECTORIES);
            selector = chooser.showOpenDialog(null);
        } else {
            selector = chooser.showSaveDialog(null);
        }
        File directorio = null;
        if (selector == JFileChooser.APPROVE_OPTION) {
            directorio = chooser.getSelectedFile();
        } else {
            System.out.println("Archivo no seleccionado");
        }
        return directorio;
    } 

    private static Scanner sc = new Scanner(System.in);

    private static Integer opcionMenu() {
        System.out.println("Gestor de archivos y directorios");
        System.out.println("1- Crear directorio");
        System.out.println("2- Listar directorio");
        System.out.println("3- Eliminar archivo o directorio");
        System.out.println("4- Mover o renombrar archivos o directorios");
        System.out.print("Opcion: ");
        Integer opcion;
        try {
            opcion = sc.nextInt();
        } catch (Exception e) {
            opcion = null;
        }

        return opcion;
    }

    private static Integer opcion4() {
        System.out.println("Seleccione si mover o renombrar (otro para cancelar)");
        System.out.println("1- Mover");
        System.out.println("2- Renombrar");
        System.out.print("Opcion: ");
        Integer opcion;
        try {
            opcion = sc.nextInt();
        } catch (Exception e) {
            opcion = null;
        }

        return opcion;
    }

    private static String pedirTexto(){
        String respuesta;
        try {
            respuesta = sc.next();
        } catch (Exception e) {
            respuesta = "";
        }
        return respuesta;
    }
}