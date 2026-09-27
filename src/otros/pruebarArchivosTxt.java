package otros;

import java.util.ArrayList;
import java.util.List;

public class pruebarArchivosTxt {
    private static final String RUTA = "src\\Otros\\prueba.txt";
    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        a.add("ni");
        a.add("c");
        a.add("er");
        System.out.println(ManejarTxt.writeInTxt(RUTA, a, true));
        System.out.println(ManejarTxt.leerTxt(RUTA));
        System.out.println(ManejarTxt.writeInTxt(RUTA, (String) null, true));
        System.out.println(ManejarTxt.leerTxt(RUTA));
        System.out.println(ManejarTxt.writeInTxt(RUTA, "nicer", true));
        System.out.println(ManejarTxt.leerTxt(RUTA));
        System.out.println(ManejarTxt.writeInTxt(RUTA, "", false));
        System.out.println(ManejarTxt.leerTxt(RUTA));
    }
}
