// Importa AtomicBoolean
import java.util.concurrent.atomic.AtomicBoolean;

// Classe consumatore: visita pazienti
public class Doctor implements Runnable {

    // Sala condivisa
    private final WaitingRoom waitingRoom;

    // Stato apertura pronto soccorso
    private final AtomicBoolean isOpen;

    // Nome medico
    private final String doctorName;

    // Costruttore
    public Doctor(
            WaitingRoom waitingRoom,
            AtomicBoolean isOpen,
            String doctorName
    ) {

        // Salva sala
        this.waitingRoom = waitingRoom;

        // Salva stato apertura
        this.isOpen = isOpen;

        // Salva nome medico
        this.doctorName = doctorName;
    }

    // Metodo eseguito dal thread
    @Override
    public void run() {

        try {

            // Continua se:
            // il pronto soccorso è aperto
            // oppure ci sono ancora pazienti
            while (
                    isOpen.get()
                    || waitingRoom.size() > 0
            ) {

                // Tenta di prendere un paziente
                String patient =
                        waitingRoom.callNext(1000);

                // Se un paziente è stato trovato
                if (patient != null) {

                    // Inizio visita
                    System.out.println(
                            "[" + doctorName +
                            "] Visita: " + patient
                    );

                    // Simula durata visita
                    Thread.sleep(
                            800 + (int)(Math.random() * 1200)
                    );

                    // Fine visita
                    System.out.println(
                            "[" + doctorName +
                            "] Visita completata."
                    );

                } else {

                    // Se pronto soccorso chiuso
                    if (!isOpen.get()) {

                        // Esce dal ciclo
                        break;
                    }
                }
            }

            // Fine turno medico
            System.out.println(
                    "[" + doctorName +
                    "] Fine turno."
            );

        } catch (InterruptedException e) {

            // Ripristina interruzione
            Thread.currentThread().interrupt();
        }
    }
}