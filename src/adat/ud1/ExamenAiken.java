package adat.ud1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

public class ExamenAiken {
    private static final String RUTA_EXAMEN = "src\\adat\\FicherosDatos\\examenCulturaGeneral.txt";
    private static final String RUTA_EXAMEN_JSON = "src\\adat\\FicherosDatos\\examenCulturaGeneralJSON.txt";
    private static final String RUTA_EXAMEN_XML = "src\\adat\\FicherosDatos\\examenCulturaGeneralXML.txt";
    public static void main(String[] args) {
        List<List<String>> lPreguntas = new ArrayList<>();
        List<String> lCorrectas = new ArrayList<>();
        int nPregunta = 1;
        List<String> pregunta;
        String correcta;
        do {
            pregunta = leerPregunta(nPregunta);
            correcta = pregunta.getLast();
            lCorrectas.add(correcta.substring(correcta.length() - 2)); //Comprobar que debuelve
            pregunta.removeLast();
            lPreguntas.add(pregunta);
            nPregunta++;
        } while (pregunta != null);

        @SuppressWarnings("unchecked")
        List<String>[] preguntas = (List<String>[]) new List[lPreguntas.size()];

        String[] correctas = new String[lCorrectas.size()];
        Examen e = new ExamenAiken.Examen(preguntas, correctas);
        

    }

    public static List<String> leerPregunta(int nPregunta){
        List<String> l = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(RUTA_EXAMEN))){
            boolean end = false;
            int nActualPregunta = 1;
            while (!end) {
                String linea = in.readLine();
                if (nActualPregunta > nPregunta || linea == null) {
                    end = true;
                } else {
                    if (nActualPregunta == nPregunta) {
                        if (!linea.isBlank()) {
                            l.add(linea.strip());
                        }
                    }
                    if (linea.matches("[Aa][Nn][Ss][Ww][Ee][Rr].*")) {
                        nActualPregunta++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return l.size() == 0 ? null : l;
    }
    public static boolean writeJSON(){
        Gson gson = new Gson();
        String json = gson.toJson();
        return false;
    }
    public static boolean writeXML(){
        return false;
    }

    private static class Examen {
        List<String>[] preguntas;
        String[] respuestas;
        String[] correctas;

        
        public Examen(List<String>[] preguntas, String[] correctas) {
            this.preguntas = preguntas;
            this.correctas = correctas;
        }

        public List<String>[] getPreguntas() {
            return preguntas;
        }
        public void setPreguntas(List<String>[] preguntas) {
            this.preguntas = preguntas;
        }
        public String[] getRespuestas() {
            return respuestas;
        }
        public void setRespuestas(String[] respuestas) {
            this.respuestas = respuestas;
        }
        public String[] getCorrectas() {
            return correctas;
        }
        public void setCorrectas(String[] correctas) {
            this.correctas = correctas;
        }

        public void start(){
            String[] res = new String[correctas.length];
            String[] resValidas = null;
            boolean isValid = true;
            for (int i = 0; i < preguntas.length; i++) {
                List<String> pregunta = preguntas[i];
                if (isValid) {
                    resValidas = new String[pregunta.size() - 1];
                    for (int j = 0; j < pregunta.size(); j++) {
                        System.out.println(pregunta.get(j));
                        if (j > 0) resValidas[j - 1] = pregunta.get(j).substring(0, 1);
                    }
                }
                System.out.println("Respuesta:");
                res[i] = pedirTxt();
                isValid = false;
                for (int j = 0; res != null && j < resValidas.length && !isValid; j++) {
                    isValid = res[i].equalsIgnoreCase(resValidas[j]);
                }
                if (!isValid && res != null) {
                    System.out.println("Respuesta no valida. Escoja una de las letras: " + Arrays.toString(resValidas));
                    i--;
                }
            }
            this.respuestas = res;
        }
    }

    public static String pedirTxt(){
        String res = null;
        try (Scanner sc = new Scanner(System.in)){
            res = sc.nextLine();
        } catch (Exception e) {
            res = null;
        }
        return res;
    }
}
