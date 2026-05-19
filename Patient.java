// Importa AtomicBoolean per condividere lo stato apertura/chiusura
import java.util.concurrent.atomic.AtomicBoolean;

// Importa AtomicInteger per generare ID sicuri tra thread
import java.util.concurrent.atomic.AtomicInteger;

// Classe produttore: genera pazienti
public class Patient implements Runnable {

    // Riferimento alla sala condivisa
    private final WaitingRoom waitingRoom;

    // Flag condiviso che indica se il pronto soccorso è aperto
    private final AtomicBoolean isOpen;

    // Nome del reparto che genera pazienti
    private final String departmentName;

    // Contatore globale thread-safe per ID unici
    private static final AtomicInteger GLOBAL_COUNTER =
            new AtomicInteger(0);

    // Costruttore
    public Patient(
            WaitingRoom waitingRoom,
            AtomicBoolean isOpen,
            String departmentName
    ) {

        // Salva riferimento sala
        this.waitingRoom = waitingRoom;

        // Salva stato apertura
        this.isOpen = isOpen;

        // Salva nome reparto
        this.departmentName = departmentName;
    }

    // Metodo eseguito automaticamente dal thread
    @Override
    public void run() {

        // Contatore locale per alternare i codici colore
        int localCount = 0;

        // Array dei livelli di priorità
        String[] priorities = {
                "ROSSO",
                "GIALLO",
                "VERDE"
        };

        try {

            // Continua finché il pronto soccorso è aperto
            while (isOpen.get()) {

                // Genera nuovo ID thread-safe
                int id =
                        GLOBAL_COUNTER.incrementAndGet();

                // Alterna i codici colore
                String priority =
                        priorities[localCount % 3];

                // Costruisce stringa paziente
                String patient =
                        "Paziente-" + id +
                        " [Codice: " + priority + "]";

                // Stampa paziente generato
                System.out.println(
                        "[" + departmentName +
                        "] Generato: " + patient
                );

                // Tenta inserimento nella sala
                boolean admitted =
                        waitingRoom.admit(patient, 1500);

                // Se inserito correttamente
                if (admitted) {

                    // Mostra dimensione sala
                    System.out.println(
                            "[" + departmentName +
                            "] Inserito in sala. Posti occupati: "
                            + waitingRoom.size()
                    );

                } else {

                    // Sala piena
                    System.out.println(
                            "[" + departmentName +
                            "] Sala piena."
                    );
                }

                // Simula intervallo casuale tra arrivi
                Thread.sleep(
                        400 + (int)(Math.random() * 600)
                );

                // Incrementa contatore locale
                localCount++;
            }

        } catch (InterruptedException e) {

            // Ripristina stato di interruzione
            Thread.currentThread().interrupt();
        }
    }
}