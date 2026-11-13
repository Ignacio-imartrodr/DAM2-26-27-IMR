package PSER.naves;

import java.util.ArrayList;
import java.util.List;

/**
 * El planeta tierra está en peligro. Varios meteoritos se acercan directamente
 * para impactar contra ella destruyéndola.
 * La empresa HWWC (House Water Watch Cooper) va a mandar todas las naves
 * disponibles para intentar solucionar el problema.
 * 
 * Las naves tipo A (Armageddon) van a dedicarse a aterrizar en un meteorito,
 * taladrar durante un tiempo aleatorio y a continuación,
 * esperar a que llegue una nave tipo B para que les surta de combustible para
 * poder despegar e irse a taladrar a otro meteorito
 * 
 * Las naves tipo BS (Bomberos-Surtidores) se dedican a ir a un meteorito y, si
 * ya ha sido taladrado, surtir de combustible la nave que
 * allí se encuentra y poner una bomba para explosionarlo.
 * 
 * Las naves se comunicarán con la HWWC para obtener la localización
 * (referencia) de un meteorito (al azar) de entre los que quedan
 * (que no han sido explosionados). También entenderán que cuando la HWWC
 * devuelva cierta localización especial (null), es que no
 * quedan meteoritos y la tierra ha sido salvada, con lo cual la misión ha
 * terminado y deberán aterrizar y regresar como héroes.
 * Debido a la radiación cósmica existente, la comunicación entre naves y HWWC
 * no puede incluir información del estado del
 * meteorito (es decir, la HWWC solo incluye la referencia al meteorito pero no
 * sabe si está taladrado o no).
 * Hay NUM_METEORITOS = 10 meteoritos
 * Hay NUM_NAVES_A naves = 5 de tipo A, NUM_NAVES_BS = 3 de tipo BS
 * Las valores de las constantes se pueden modificar para adecuar el tiempo de
 * desarrollo y ejecución.
 * Sacar por pantalla la información necesaria para poder evaluar el correcto
 * funcionamiento.
 * Debido a la importancia de la misión (salvar la tierra), el código debe ser
 * óptimo:
 * - las naves realizan concurrentemente su trabajo
 * - no habrá más de una nave taladrando un meteorito
 * - una bomba, un meteorito
 * - la CPU no debe perder el tiempo testeando condiciones innecesarias
 * 
 * AppNaves
 */
public class AppNaves {
    private final static int NUM_METEORITOS = 10;
    private final static int NUM_NAVES_A = 5;
    private final static int NUM_NAVES_BS = 3;
    public static void main(String[] args) {
        List<Meteorito> meteoritos = new ArrayList<>(NUM_METEORITOS);
        for (int i = 0; i < NUM_METEORITOS; i++) {
            meteoritos.add(new Meteorito(String.valueOf(i)));
        }
        Central central = new Central(meteoritos);
        Nave[] naves = new Nave[NUM_NAVES_A + NUM_NAVES_BS];
        for (int i = 0, a = 0, bs = 0; i < naves.length; i++) {
            if (i < NUM_NAVES_A) {
                naves[i] = new NaveA("nA" + a++, central);
            } else {
                naves[i] = new NaveBS("nBS" + bs++, central);
            }
        }
        for (Nave nave : naves) {
            nave.start();
        }
    }
    
}
