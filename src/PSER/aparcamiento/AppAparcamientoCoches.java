package PSER.aparcamiento;

public class AppAparcamientoCoches {
    final static int N_PLAZAS = 10;
    final static int N_COCHES = 50;
    public static void main(String[] args) {
        Parking parking = new Parking(N_PLAZAS);
        parking.mostrarPlazas();
        Coche[] coches = new Coche[N_COCHES];
        for (int i = 0; i < N_COCHES; i++) {
            coches[i] = new Coche("C" + /*i*/String.valueOf((char)('A' + i)), parking);
            coches[i].start();
        }
    }
}
