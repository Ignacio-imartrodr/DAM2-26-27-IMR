package PSER.ventas;

import java.util.Random;

/**
 * 
 * Realice una simulación de un concesionario de Seat que intenta vender a sus
 * clientes su catálogo de oferta actual, que consiste en 10 unidades del modelo
 * león, 7 unidades de ibiza y 3 unidades de ateca. Un cliente intentará comprar
 * un coche (y solo uno) hasta que no quede ninguna a la venta. Si en algún
 * momento decide comprar uno, ya no seguirá mirando más (no vaya a ser que
 * encuentre uno mejor y más barato, con lo que se llevaría un disgusto).
 * 
 * Los clientes (~50) le piden cita al vendedor que les indicará un coche que
 * esté libre en ese momento (de entre todos los ofertados) para que lo vean y
 * lo prueben (el vendedor quedará en su sitio dando citas al resto de los
 * clientes).
 * 
 * Antes de decidir si lo compran o no, los clientes se pasan un pequeño tiempo
 * aleatorio (no más de 20ms), en donde se fijan en un cartelito que hay encima
 * del capó de cada coche, en donde se puede ver el n.º de clientes que ya lo
 * han visitado anteriormente.
 * 
 * La probabilidad de que compren el coche se puede calcular con una
 * probabilidad igual al n.º de visitas de clientes hasta el momento (si lo han
 * probado 12 clientes, la probabilidad de compra será de un 12%)
 * 
 * Finalmente se visualizarán únicamente los productos y qué clientes lo
 * adquirieron. Tanto los clientes como los productos se mostrarán siempre
 * numerados (p.e. cliente3 compró León7).
 * 
 * Existirá una variable booleana denominada VerDetalle que, en el caso de valer
 * true, también se mostrará por pantalla la información de que un cliente está
 * probando un coche, y si lo compra o no le convence.
 * 
 * Ajuste las constantes y los tiempos de manera adecuada para la correcta
 * ejecución de la aplicación, que se diseñará maximizando el paralelismo para
 * optimizar los tiempos.
 * 
 * AppVentaCoches
 */
public class AppVentaCoches {
    private final static int CANT_MODELOS = 4;
/*  private final static int CANT_MODELO1 = 10;
    private final static int CANT_MODELO2 = 7;
    private final static int CANT_MODELO3 = 3;*/
    private final static int NUM_CLIENTES = 50;
    private final static boolean VER_DETALLE = false;

    public static void main(String[] args) {
        Concesionario concesionario = new Concesionario();
        /*concesionario.addCoche("León", CANT_MODELO1);
        concesionario.addCoche("Corvett", CANT_MODELO2);
        concesionario.addCoche("Benz",CANT_MODELO3);*/
        for (int i = 0; i < CANT_MODELOS; i++) {
            concesionario.addCoche(String.valueOf((char)('A' + i)), new Random().nextInt(1,11));
        }
        Cliente[] clientes = new Cliente[NUM_CLIENTES];
        for (int i = 0; i < NUM_CLIENTES; i++) {
            clientes[i] = new Cliente("cliente" + i, concesionario, VER_DETALLE);
            clientes[i].start();
        }
    }
}
