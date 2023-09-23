package robots.view;

import robots.controllers.ControllerArea;

import java.io.IOException;
import java.util.Scanner;

/*
Terminal gestisce la parte terminale del gioco al fine di peremttere un'interazione con l'utente
 */
public class Terminal {

    static ControllerArea controllerArea = new ControllerArea();

    public static void init() throws IOException {
        printMenu();
        System.out.println("Programma inizializzato.");
        System.out.println("Per uscire digitare 'exit'.");
        System.out.println("Digitare 'help' per visualizzare la lista dei comandi.");
        readCommand();
    }

    private static void printMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("SCIAME DI ROBOT --- Progetto di Elena Ricci");
        System.out.println("1) Nuova area di gioco (l'area verrà popolata da varie zone rettangolari e circolari)");
        System.out.println("2) Esci");
        System.out.println("Scegli un'opzione: ");
        int opzione = scanner.nextInt();

        switch (opzione) {
            case 1 -> controllerArea.init();
            case 2 -> System.exit(0);
            default -> {
                System.out.println("Opzione non valida, riprovare!");
                printMenu();
            }
        }
    }

    private static void readCommand() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("> ");
        String command = scanner.nextLine();

        switch (command) {
            case "move" -> {
                controllerArea.move();
                readCommand();
            }
            case "move random" -> {
                controllerArea.moveRandom();
                readCommand();
            }
            case "signal" -> {
                controllerArea.signal();
                readCommand();
            }
            case "unsignal" -> {
                controllerArea.unsignal();
                readCommand();
            }
            case "follow" -> {
                controllerArea.follow();
                readCommand();
            }
            case "continue" -> {
                controllerArea.continuecmd();
                readCommand();
            }
            case "stop" -> {
                controllerArea.stop();
                readCommand();
            }
            case "help" -> {
                help();
                readCommand();
            }
            case "exit" -> {
                //exit();
                System.out.println("Arrivederci!");
                System.exit(0);
            }
            default -> {
                System.out.println("Comando non riconosciuto");
                readCommand();
            }
        }
    }

    //mostra i vari comandi possibili
    private static void help() {
        System.out.println("Comandi disponibili:");
        System.out.println("move\t\t\t Muove il robot verso la posizione (x,y) alla data velocità s espressa in m/s;");
        System.out.println("move random\t\t Muove il robot alla velocità s espressa in m/s verso\n" +
                           "\t\t\t\t una posizione (x,y) scelta casualmente nell’intervallo [x1, x2] e [y1, y2]");
        System.out.println("signal\t\t\t Segnala una particolare condizione");
        System.out.println("unsignal\t\t Termina la segnalazione della condizione;");
        System.out.println("follow\t\t\t Muove il robot alla velocità s espressa in m/s verso una\n" +
                           "\t\t\t\t direzione che è la media delle posizioni dei robot che segnalano la condizione label e\n" +
                           "\t\t\t\t che si trovano ad una distanza minore o uguale a dist. Se non esistono robot viene\n" +
                           "\t\t\t\t scelta una direzione a caso nell’intervallo [-dist, dist]x - [-dist, dist]y");
        System.out.println("continue\t\t Il robot continua a muoversi per s secondi con la medesima direzione e\n" +
                           "\t\t\t\t velocità");
        System.out.println("stop\t\t\t Il robot non si muove più ma continua a segnalare le proprie condizioni");
        System.out.println("help\t\t\t Visualizza questo messaggio");
        System.out.println("exit\t\t\t Esce dal programma ed esegue il salvataggio automatico");
    }


}
