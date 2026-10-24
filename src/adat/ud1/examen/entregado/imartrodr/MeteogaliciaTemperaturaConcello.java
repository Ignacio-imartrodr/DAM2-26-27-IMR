package adat.ud1.examen.entregado.imartrodr;

import java.io.BufferedReader;
import java.io.FileReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Ignacio Martínez Rodríguez
 */
public class MeteogaliciaTemperaturaConcello {
    public final static String RUTA_JSON = "src\\adat\\ud1\\examen\\DATOS\\observacionConcellos.json";
    public static void main(String[] args) {
        String json = null;
        try (var in = new BufferedReader(new FileReader(RUTA_JSON))) {
            json = in.readAllAsString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Double temp = temperaturaConcello(json, "Marín");
        System.out.println(Double.toString(temp));
    }
    public static Double temperaturaConcello(String jsonObservacion, String concello){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode datos = mapper.readTree(jsonObservacion).get(concello).findValue("listaObservacionConcellos");
            for (int i = 0; i < datos.size(); i++) {
                JsonNode element = datos.get(i);
                String nombre = element.get("nomeConcello").asText();
                if (nombre.equalsIgnoreCase(concello)) {
                    return element.get("temperatura").asDouble();
                }
            }
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        return null;
    }
}
