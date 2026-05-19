# Simulatore di Pronto Soccorso – Problema Produttori-Consumatori

## Descrizione del progetto

Questo progetto è una simulazione concorrente di un pronto soccorso sviluppata in Java, basata sul classico problema dei produttori-consumatori.

L’obiettivo è mostrare come più thread possano lavorare contemporaneamente su una risorsa condivisa limitata, evitando problemi di sincronizzazione come:
- race condition;
- attese infinite;
- accessi concorrenti non sicuri;
- blocchi del sistema.

Nel progetto:
- i pazienti rappresentano i produttori;
- i medici rappresentano i consumatori;
- la sala d’attesa rappresenta il buffer condiviso.

---

# Struttura del progetto

Il progetto è composto da quattro classi principali:

| Classe | Ruolo |
|---|---|
| `WaitingRoom` | Gestisce la sala d’attesa condivisa |
| `Patient` | Genera pazienti (produttore) |
| `Doctor` | Visita pazienti (consumatore) |
| `Main` | Avvia e gestisce la simulazione |

---

# Funzionamento

## Produttori

I thread `Patient` simulano l’arrivo dei pazienti da diversi reparti ospedalieri.

Ogni produttore:
1. genera un nuovo paziente;
2. assegna un codice colore;
3. tenta di inserirlo nella sala d’attesa.

Se la sala è piena, il paziente non viene inserito e il thread riprova successivamente.

---

## Consumatori

I thread `Doctor` rappresentano i medici del pronto soccorso.

Ogni medico:
1. preleva un paziente dalla sala;
2. simula la visita;
3. completa il trattamento;
4. passa al paziente successivo.

---

## Sala d’attesa

La sala d’attesa utilizza:

```java
ArrayBlockingQueue
```

una struttura thread-safe a capacità limitata.

Questa struttura:
- sincronizza automaticamente i thread;
- evita accessi simultanei non sicuri;
- impedisce race condition.

---

# Tecnologie utilizzate

- Java
- Thread
- ExecutorService
- ArrayBlockingQueue
- AtomicBoolean
- AtomicInteger

---

# Prevenzione del deadlock

Per evitare blocchi infiniti il progetto utilizza:
- timeout su `offer()`;
- timeout su `poll()`;
- controllo condiviso tramite `AtomicBoolean`;
- gestione delle interruzioni dei thread.

Questo permette al programma di terminare correttamente senza lasciare thread bloccati.

---

# Compilazione

Nel terminale eseguire:

```bash
javac *.java
```

---

# Esecuzione

Avviare il programma con:

```bash
java Main
```

---

# Output atteso

Esempio di esecuzione:

```text
APERTURA PRONTO SOCCORSO
------------------------

[Reparto-1] Generato: Paziente-1 [Codice: ROSSO]
[Reparto-1] Inserito in sala. Posti occupati: 1

[Dottore-1] Visita: Paziente-1 [Codice: ROSSO]

[Dottore-1] Visita completata.

Chiusura pronto soccorso...

[Dottore-1] Fine turno.

Tutti i thread terminati.
```

---

# Concetti teorici applicati

Il progetto applica i seguenti concetti di programmazione concorrente:
- thread concorrenti;
- problema produttori-consumatori;
- sincronizzazione;
- buffer condiviso;
- gestione dei thread;
- thread-safe collections;
- gestione della concorrenza in Java.

---

# Obiettivo didattico

Il progetto dimostra come utilizzare i thread in Java per coordinare produttori e consumatori in un sistema reale, garantendo sicurezza nell’accesso alle risorse condivise e corretta terminazione del programma.