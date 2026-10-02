package adat.ud1.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EjemploServicioJSONapiOSRM {

        public static void main(String[] args) throws Exception {
                System.setProperty("java.net.useSystemProxies", "true");

                // Coordenadas GPS
                double latOrigen = 42.4310;
                double lonOrigen = -8.6440;

                double latDestino = 42.2406;
                double lonDestino = -8.7207;

                // IMPORTANTE: OSRM utiliza longitud,latitud
                String coordenadas = lonOrigen + "," + latOrigen + ";" +
                                lonDestino + "," + latDestino;

                String url = "https://router.project-osrm.org/route/v1/driving/"
                                + coordenadas
                                + "?overview=false";

                System.out.println("URL petición OSRM: " + url);

                // 1. Crear cliente HTTP
                HttpClient cliente = HttpClient.newHttpClient();

                // 2. Crear petición
                HttpRequest peticion = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .GET()
                                .build();

                // 3. Realizar petición
                HttpResponse<String> respuesta = cliente.send(
                                peticion,
                                HttpResponse.BodyHandlers.ofString());

                // 4. Comprobar código HTTP
                if (respuesta.statusCode() != 200) {
                        System.out.println(
                                        "Error HTTP: " + respuesta.statusCode());
                        return;
                }

                // 5. JSON recibido
                String json = respuesta.body();

                System.out.println("JSON recibido:");
                System.out.println(json);

                // 6. Analizar JSON SIN utilizar clases modelo
                ObjectMapper mapper = new ObjectMapper();

                JsonNode raiz = mapper.readTree(json);

                // 7. Comprobar resultado de OSRM
                if (!"Ok".equals(raiz.get("code").asText())) {
                        System.out.println("No se pudo calcular la ruta");
                        return;
                }

                // 8. Obtener la primera ruta
                JsonNode ruta = raiz.get("routes").get(0);

                // 9. Distancia en metros
                double distanciaMetros = ruta.get("distance").asDouble();

                // 10. Duración en segundos
                double duracionSegundos = ruta.get("duration").asDouble();

                // 11. Conversión
                double distanciaKm = distanciaMetros / 1000;

                double duracionMinutos = duracionSegundos / 60;

                System.out.printf(
                                "Distancia: %.2f km%n",
                                distanciaKm);

                System.out.printf(
                                "Duración: %.0f minutos%n",
                                duracionMinutos);
        }
}