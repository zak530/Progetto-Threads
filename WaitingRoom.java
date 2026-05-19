// Importa ArrayBlockingQueue, una coda thread-safe a capacità fissa
import java.util.concurrent.ArrayBlockingQueue;

// Importa l'interfaccia BlockingQueue
import java.util.concurrent.BlockingQueue;

// Importa TimeUnit per specificare l'unità di tempo dei timeout
import java.util.concurrent.TimeUnit;

// Classe che rappresenta la sala d'attesa condivisa
public class WaitingRoom {

    // Coda condivisa tra produttori e consumatori
    private final BlockingQueue<String> queue;

    // Costruttore: crea la sala con capacità massima
    public WaitingRoom(int capacity) {

        // Inizializza la coda con numero massimo di posti
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    // Metodo usato dai produttori per inserire un paziente
    public boolean admit(String patientInfo, long timeoutMs)
            throws InterruptedException {

        // Tenta di inserire il paziente nella coda
        // Aspetta massimo timeoutMs millisecondi
        // Restituisce true se inserito, false se piena
        return queue.offer(
                patientInfo,
                timeoutMs,
                TimeUnit.MILLISECONDS
        );
    }

    // Metodo usato dai consumatori per prelevare un paziente
    public String callNext(long timeoutMs)
            throws InterruptedException {

        // Preleva il prossimo paziente
        // Aspetta massimo timeoutMs millisecondi
        // Restituisce null se la coda è vuota
        return queue.poll(
                timeoutMs,
                TimeUnit.MILLISECONDS
        );
    }

    // Restituisce il numero di pazienti presenti in sala
    public int size() {

        // Numero corrente di elementi nella coda
        return queue.size();
    }
}