package ADAT.UD1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

public class EjemploCopiaArchivos {

    public static void main(String[] args) {
        File origen = null;
        File destino = null;

        JFileChooser chooser = new JFileChooser(".");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            origen = chooser.getSelectedFile();
        }

        JFileChooser chooser2 = new JFileChooser(".");
        chooser2.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser2.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            destino = chooser2.getSelectedFile();
        }

        try (var in = new BufferedInputStream(new FileInputStream(origen));
                var out = new BufferedOutputStream(new FileOutputStream(destino));) {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado");
        } catch (IOException e) {
            System.out.println("Error de E/S");
        }
    }
}