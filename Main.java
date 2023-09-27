package TuristasDekker;

class Turista implements Runnable {
    private static int turno = 0;
    private static int[] transportes = {4, 4, 4, 4, 4}; // 5 turistas con 4 transportes cada uno
    private int id;

    public Turista(int id) {
        this.id = id;
    }

    private void entrarALancha() {
        while (true) {
            int otroTurista = (id + 1) % 5; // �ndice del otro turista
            turno = id; // Es el turno de este turista
            while (turno == id && transportes[id] > 0) {
                // El turista tiene el turno y transportes disponibles
                if (transportes[otroTurista] == 0 || turno != id) {
                    // El otro turista no tiene transportes o no es el turno de este turista
                    break;
                }
            }

            // Realizar el viaje en la lancha
            if (transportes[id] > 0) {
                transportes[id]--;
                System.out.println("Turista " + id + " realiz� un viaje, transportes restantes: " + transportes[id]);
                turno = otroTurista; // Es el turno del otro turista
                break;
            }
        }
    }

    @Override
    public void run() {
        while (transportes[id] > 0) {
            entrarALancha();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Thread[] turistas = new Thread[5];

        for (int i = 0; i < 5; i++) {
            turistas[i] = new Thread(new Turista(i));
            turistas[i].start();
        }

        try {
            for (int i = 0; i < 5; i++) {
                turistas[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Todos los turistas han terminado sus paseos.");
    }
}