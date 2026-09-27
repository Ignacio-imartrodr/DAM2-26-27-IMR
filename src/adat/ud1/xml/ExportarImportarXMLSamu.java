package adat.ud1.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExportarImportarXMLSamu {
    public static void main(String[] args) {
        boolean continuar = true;
        List<Persona> lista = new ArrayList<>();

        while (continuar) {
            Scanner sc = new Scanner(System.in);
            System.out.println("===============");
            System.out.println("1. Añadir persona");
            System.out.println("2. Mostrar personas");
            System.out.println("3. Buscar persona ");
            System.out.println("4. Importar/Exportar XML");
            System.out.println("5. Salir \n");
            System.out.print("Que desea hacer?: ");
            int eleccion = sc.nextInt();
            sc.nextLine();

            switch (eleccion) {
                case 1:
                    System.out.println();
                    System.out.print("Introduce su nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Introduce su edad: ");
                    int edad = sc.nextInt();

                    lista.add(new Persona(nombre, edad));

                    break;
                case 2:
                    System.out.println();
                    for (Persona persona : lista) {
                        System.out.println(persona.toString());
                    }
                    break;

                case 3:
                    System.out.println();
                    System.out.println("1. Filtrar por nombre");
                    System.out.println("2. Filtrar por edad");
                    System.out.print("Que desea hacer?: ");
                    eleccion = sc.nextInt();
                    sc.nextLine();

                    switch (eleccion) {
                        case 1:
                            System.out.print("Introduce el nombre: ");
                            nombre = sc.nextLine();

                            for (Persona persona : lista) {
                                if (persona.getNombre().equals(nombre)) {
                                    System.out.println(persona.toString());
                                }
                            }
                            break;

                        case 2:
                            System.out.print("Introduce la edad: ");
                            edad = sc.nextInt();

                            for (Persona persona : lista) {
                                if (persona.getEdad() == edad) {
                                    System.out.println(persona.toString());
                                }
                            }

                            break;
                        default:
                            System.out.println("Esa selección no es válida");
                            break;
                    }
                    break;
                case 4:
                    File archivo = new File("persona.xml");
                    System.out.println();
                    System.out.println("1. Importar lista desde XML");
                    System.out.println("2. Exportar lista a XML");
                    System.out.print("Que desea hacer?: ");
                    eleccion = sc.nextInt();
                    sc.nextLine();

                    DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = null;

                    try {
                        builder = fabrica.newDocumentBuilder();

                    } catch (ParserConfigurationException e) {
                        System.out.println("Error de creación de documento " + e.getMessage());
                    }
                    switch (eleccion) {
                        case 1:

                            importarXML(lista, archivo, builder);

                            break;
                        case 2:

                            exportarXML(lista, archivo, builder);

                            break;
                        default:
                            System.out.println("Esa selección no es válida");
                            break;
                    }

                    break;

                case 5:
                    System.out.println("Saliendo del programa...");
                    continuar = false;
                    sc.close();
                    break;

                default:
                    System.out.println("Esa selección no es válida");
                    break;
            }
        }
    }

    private static void exportarXML(List<Persona> lista, File archivo, DocumentBuilder builder) {
        Document documento;
        documento = builder.newDocument();
        Element personas = documento.createElement("personas");
        documento.appendChild(personas);
        for (Persona persona : lista) {
            Element elPersona = documento.createElement("persona");
            personas.appendChild(elPersona);

            Element elNombre = documento.createElement("nombre");
            elNombre.setTextContent(persona.getNombre());
            elPersona.appendChild(elNombre);

            Element elEdad = documento.createElement("edad");
            elEdad.setTextContent(Integer.toString(persona.getEdad()));
            elPersona.appendChild(elEdad);
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");

            transformer.transform(new DOMSource(documento), new StreamResult(archivo));

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void importarXML(List<Persona> lista, File archivo, DocumentBuilder builder) {
        Document documento;
        try {
            documento = builder.parse(archivo);
            Element personas = documento.getDocumentElement();
            NodeList nodeLPersona = personas.getElementsByTagName("persona");
            for (int i = 0; i < nodeLPersona.getLength(); i++) {
                Element elPersona = (Element) nodeLPersona.item(i);
                String elNombre = elPersona.getElementsByTagName("nombre").item(0).getTextContent();
                String elEdad = elPersona.getElementsByTagName("edad").item(0).getTextContent();

                lista.add(new Persona(elNombre, Integer.valueOf(elEdad)));
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
