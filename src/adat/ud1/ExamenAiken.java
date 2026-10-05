package adat.ud1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

public class ExamenAiken {
    private static final String RUTA_EXAMEN = "src\\adat\\FicherosDatos\\examenCulturaGeneral.txt";
    private static final String RUTA_EXAMEN_JSON = "src\\adat\\FicherosDatos\\examenCulturaGeneralJSON.json";
    private static final String RUTA_EXAMEN_XML = "src\\adat\\FicherosDatos\\examenCulturaGeneralXML.xml";

    public static void main(String[] args) {
        List<String[]> lPreguntas = new ArrayList<>();
        List<String> lCorrectas = new ArrayList<>();
        int nPregunta = 1;
        String[] pregunta = leerPregunta(nPregunta);
        String correcta;
        do {
            correcta = pregunta[pregunta.length - 1];
            lCorrectas.add(correcta.substring(correcta.length() - 1));
            lPreguntas.add(Arrays.copyOf(pregunta, pregunta.length - 1));
            nPregunta++;
            pregunta = leerPregunta(nPregunta);
        } while (pregunta != null);

        String[][] preguntas = new String[lPreguntas.size()][];
        nPregunta = 0;
        for (String[] strings : lPreguntas) {
            preguntas[nPregunta] = strings;
            nPregunta++;
        }

        String[] correctas = new String[lCorrectas.size()];
        nPregunta = 0;
        for (String string : lCorrectas) {
            correctas[nPregunta] = string;
            nPregunta++;
        }
        Examen e = new ExamenAiken.Examen(preguntas, correctas, new String[] { "A", "B", null });
        // e.start();
        writeJSON(e);
        writeXML(e);
        Examen eJSON = readJSON(RUTA_EXAMEN_JSON);
        Examen eXML = readXML(RUTA_EXAMEN_XML);
        System.out.println(eJSON.getNota());
        System.out.println(eXML.getNota());
    }

    public static String[] leerPregunta(int nPregunta) {
        List<String> l = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(RUTA_EXAMEN))) {
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

        String[] res = l.size() == 0 ? null : new String[l.size()];
        if (res != null) {
            int i = 0;
            for (String string : l) {
                res[i] = string;
                i++;
            }
        }
        return res;
    }

    public static Examen readJSON(String ruta) {
        Gson gson = new Gson();
        Examen ex = null;
        try (BufferedReader in = new BufferedReader(new FileReader(ruta))) {
            ex = gson.fromJson(in, Examen.class);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return ex;
    }

    public static Examen readXML(String ruta) {
        Document d = null;
        try {
            d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ruta);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        NodeList contenedores = d.getDocumentElement().getElementsByTagName("Preguntas");
        NodeList nodosPregunta = contenedores.item(0).getChildNodes();
        List<String[]> preguntas = new ArrayList<>();
        List<String> correctas = new ArrayList<>();
        List<String> respuestas = new ArrayList<>();

        for (int i = 0; i < nodosPregunta.getLength(); i++) {
            Node nodo = nodosPregunta.item(i);
            if (nodo.getNodeType() != Node.ELEMENT_NODE) {
                continue; // Ignora los saltos de línea y la indentación.
            }

            Element elementoPregunta = (Element) nodo;
            NodeList campos = elementoPregunta.getChildNodes();
            String enunciado = null;
            String correcta = null;
            String respuestaEscogida = null;
            List<String> opciones = new ArrayList<>();

            for (int j = 0; j < campos.getLength(); j++) {
                Node campo = campos.item(j);

                if (campo.getNodeType() == Node.TEXT_NODE
                        && enunciado == null
                        && !campo.getNodeValue().isBlank()) {
                    enunciado = campo.getNodeValue().strip();
                } else if (campo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) campo;
                    String etiqueta = elemento.getTagName();
                    String texto = elemento.getTextContent().strip();

                    if (etiqueta.matches("Respuesta[A-Z]")) {
                        opciones.add(texto);
                    } else if (etiqueta.equals("Correcta")) {
                        correcta = texto;
                    } else if (etiqueta.equals("RespuestaEscogida") && !texto.isEmpty()) {
                        respuestaEscogida = texto;
                    }
                }
            }

            String[] pregunta = new String[opciones.size() + 1];
            pregunta[0] = enunciado;
            for (int j = 0; j < opciones.size(); j++) {
                pregunta[j + 1] = opciones.get(j);
            }

            preguntas.add(pregunta);
            correctas.add(correcta);
            respuestas.add(respuestaEscogida);
        }
        return new Examen(
                preguntas.toArray(new String[0][]),
                correctas.toArray(new String[0]),
                respuestas.toArray(new String[0]));
    }

    public static boolean writeJSON(Examen e) {
        Gson gson = new Gson();
        String json = gson.toJson(e);
        try (var out = new BufferedWriter(new FileWriter(RUTA_EXAMEN_JSON));) {
            out.write(json);
        } catch (FileNotFoundException e1) {
            System.out.println("Fichero no encontrado");
        } catch (IOException IOE) {
            System.out.println("Error de E/S");
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }
        return false;
    }

    public static boolean writeXML(Examen e) {
        Document d = null;
        try {
            d = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        Element ex = d.createElement("Examen");
        Element p = d.createElement("Preguntas");
        Element preg;
        Element resp;
        String[][] preguntas = e.getPreguntas();
        String[] correctas = e.getCorrectas();
        String[] respuestas = e.getRespuestas();
        for (int i = 0; i < preguntas.length; i++) {
            preg = d.createElement("Pregunta" + (i + 1));
            preg.setTextContent(preguntas[i][0]);
            for (int j = 1; j < preguntas[i].length; j++) {

                resp = d.createElement("Respuesta" + (Character.toString('A' + (j - 1))));
                resp.setTextContent(preguntas[i][j]);
                preg.appendChild(resp);
            }
            resp = d.createElement("Correcta");
            resp.setTextContent(correctas[i]);
            preg.appendChild(resp);
            resp = d.createElement("RespuestaEscogida");
            if (respuestas[i] != null) {
                resp.setTextContent(respuestas[i]);
            }
            preg.appendChild(resp);
            p.appendChild(preg);
        }

        ex.appendChild(p);
        Transformer optimus = null;
        try {
            optimus = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException | TransformerFactoryConfigurationError e1) {
            e1.printStackTrace();
        }

        optimus.setOutputProperty("indent", "yes");
        try {
            optimus.transform(new DOMSource(ex), new StreamResult(RUTA_EXAMEN_XML));
        } catch (TransformerException e1) {
            e1.printStackTrace();
        }
        return true;
    }

    private static class Examen {
        String[][] preguntas;
        String[] respuestas;
        String[] correctas;
        Float nota;

        public Examen(String[][] preguntas, String[] correctas) {
            if (preguntas == null || correctas == null || preguntas.length != correctas.length) {
                throw new IllegalArgumentException();
            }
            this.preguntas = preguntas;
            this.correctas = correctas;
            respuestas = null;
            nota = null;
        }

        public Examen(String[][] preguntas, String[] correctas, String[] respuestas) {
            this(preguntas, correctas);
            if (preguntas == null || correctas == null || preguntas.length != correctas.length) {
                throw new IllegalArgumentException();
            }
            if (respuestas != null && correctas.length != respuestas.length) {
                throw new IllegalArgumentException();
            }
            this.respuestas = respuestas;
        }

        public String[][] getPreguntas() {
            return preguntas;
        }

        public String[] getRespuestas() {
            return respuestas;
        }

        public String[] getCorrectas() {
            return correctas;
        }

        public Float getNota() {
            if (nota == null) {
                nota = calculateNota();
            }
            return nota;
        }

        private Float calculateNota() {
            Float nota = 0f;
            if (respuestas == null) {
                return nota;
            }
            for (int i = 0; i < correctas.length; i++) {
                if (respuestas[i] != null && respuestas[i].equals(correctas[i])) {
                    nota++;
                }
            }
            nota *= 10f / correctas.length;
            return nota;
        }

        public void start() {
            String[] res = new String[correctas.length];
            String[] resValidas = null;
            boolean isValid = true;
            for (int i = 0; i < preguntas.length; i++) {
                String[] pregunta = preguntas[i];
                resValidas = new String[pregunta.length - 1];
                for (int j = 0; j < pregunta.length; j++) {
                    System.out.println(pregunta[j]);
                    if (isValid && j > 0)
                        resValidas[j - 1] = pregunta[j].substring(0, 1);
                }
                System.out.println("Respuesta:");
                res[i] = pedirTxt();
                isValid = false;
                for (int j = 0; res[i] != null && j < resValidas.length && !isValid; j++) {
                    isValid = res[i].equalsIgnoreCase(resValidas[j]);
                    if (isValid)
                        res[i] = resValidas[j];
                }
                if (!isValid && res[i] != null) {
                    System.out.println("Respuesta no valida. Escoja una de las letras: " + Arrays.toString(resValidas));
                    i--;
                }
            }
            this.respuestas = res;
        }
    }

    private static Scanner sc = new Scanner(System.in);

    public static String pedirTxt() {
        String res = null;
        try {
            res = sc.nextLine();
            if (res.isBlank()) {
                res = null;
            }
        } catch (Exception e) {
            res = null;
        }
        return res;
    }
}
