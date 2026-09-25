package adat.ud1;

import java.io.File;

import javax.swing.JFileChooser;

public class Ej2 {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int selector = chooser.showOpenDialog(null);

        if (selector == JFileChooser.APPROVE_OPTION) {
            File directorio = chooser.getSelectedFile();

            System.out.println(directorio.getAbsolutePath());
            long tamañoTotal = 0;
            for (File string : directorio.listFiles()) {
                System.out.println(
                        "-" + string.getName() + " (" + string.length() + ") " + (string.isDirectory() ? "DIR" : ""));
                tamañoTotal += string.length();
            }
            System.out.println("Tamaño total " + tamañoTotal);
        }
    }
}
