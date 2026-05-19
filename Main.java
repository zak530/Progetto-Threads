// Importa ExecutorService per gestire pool thread
import java.util.concurrent.ExecutorService;

// Importa Executors per creare thread pool
import java.util.concurrent.Executors;

// Importa TimeUnit
import java.util.concurrent.TimeUnit;

// Importa AtomicBoolean
import java.util.concurrent.atomic.AtomicBoolean;

// Classe principale
public class Main {

    // Capacità massima sala d'attesa
    private static final int WAITING_ROOM_CAPACITY = 5;

    // Numero produttori
    private static final int NUM_DEPARTMENTS = 2;

    // Numero consumatori
    private static final int NUM_DOCTORS = 3;

    // Durata simulazione
    private static final long SHIFT_DURATION_MS = 6000;

    // Metodo principale
    public static void main(String[] args)
            throws InterruptedException {

        // Crea sala condivisa
        WaitingRoom waitingRoom =
                new WaitingRoom(
                        WAITING_ROOM_CAPACITY
                );

        // Flag condiviso apertura pronto soccorso
        AtomicBoolean isOpen =
                new AtomicBoolean(true);

        // Crea pool thread
        ExecutorService hospitalPool =
                Executors.newFixedThreadPool(
                        NUM_DEPARTMENTS + NUM_DOCTORS
                );

        // Messaggio iniziale
        System.out.println(
                "APERTURA PRONTO SOCCORSO"
        );

        // Separatore grafico
        System.out.println(
                "------------------------"
        );

        // Avvia thread produttori
        for (int i = 1;
             i <= NUM_DEPARTMENTS;
             i++) {

            // Inserisce thread nel pool
            hospitalPool.submit(

                    new Patient(
                            waitingRoom,
                            isOpen,
                            "Reparto-" + i
                    )
            );
        }

        // Avvia thread consumatori
        for (int i = 1;
             i <= NUM_DOCTORS;
             i++) {

            // Inserisce thread nel pool
            hospitalPool.submit(

                    new Doctor(
                            waitingRoom,
                            isOpen,
                            "Dottore-" + i
                    )
            );
        }

        // Attende durata simulazione
        Thread.sleep(
                SHIFT_DURATION_MS
        );

        // Messaggio chiusura
        System.out.println(
                "\nChiusura pronto soccorso..."
        );

        // Imposta flag di stop
        isOpen.set(false);

        // Blocca nuovi task
        hospitalPool.shutdown();

        // Attende terminazione thread
        if (!hospitalPool.awaitTermination(
                5,
                TimeUnit.SECONDS
        )) {

            // Forza terminazione
            hospitalPool.shutdownNow();

            // Messaggio errore
            System.out.println(
                    "Chiusura forzata."
            );

        } else {

            // Chiusura corretta
            System.out.println(
                    "Tutti i thread terminati."
            );
        }
    }
}