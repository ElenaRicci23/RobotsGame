Progetto svolto da Elena Ricci per l'esame di Programmazione Avanzata

L’obiettivo del progetto è quello di realizzare una libreria Java che consenta la simulazione di
uno sciame di robot che si muovono nello spazio.
L’implementazione realizzata si basa sul pattern di programmazione MVC, difatti vi sono 3 pacchetti :
- Model (i componenti che presentano gli oggetti principali)
- View (il terminale per la comunicazione con l’utente)
- Controllers (qui si trovano la componente che gestisce l’interazione dell’utente con l’area e i vari oggetti)
- Utilities (le classi utili al funzionamento delle classi Model)

Descrizione interfacce e responsabilità associate
•	Interfacce
Area (model) : Rappresenta lo spazio dove si muovono i robot. Area ha base e altezza prestabilite e si
occupa in parte della gestione della posizione dei robot.
RobotFactory (model) : Interfaccia che rappresenta un robot. Il robot si trova all'interno di una DefaultArea
e ha posizione, velocità, angolo e un id che serve per individuare il robot interessato.
Shape (model) : Interfaccia che rappresenta una figura (rettangolo/cerchio) che rappresenta una zona dell’area
contrassegnata da una label.
•	Implementazioni
Robot (model): Implementazione di un robot dell'interfaccia RobotFactory.
DefaultArea (model): Implementazione di default dell'interfaccia Area.
Rectangle (model): Implementazione dell'interfaccia Shape rappresentante un rettangolo.
Circle (model): Implementazione dell'interfaccia Shape rappresentante un cerchio.
•	Altre classi
CartesianCoordinates (model) : Rappresenta una generica posizione (x, y) in un piano a due dimensioni.
Prende una x e una y come parametri, entrambi double.
ControllerArea (controller) : Controller dell’area, contiene principalmente l’implementazione dei vari comandi
del robot, in generale si occupa della gestione di essi.
•	Esecuzione
Terminal (view) : Si occupa dell’esecuzione del programma da terminale, contiene un oggetto di tipo ControllerArea
che agisce a seconda degli input dell’utente.
L’app va in esecuzione grazie al metodo Terminal.init() che richiama due metodi fondamentali : printMenu() e
readCommand(). printMenu() stampa il menù del gioco mostrando le due opzioni “Nuova area di gioco (l'area verrà
popolata da varie zone rettangolari e circolari)” ed “Esci”, che corrispondono rispettivamente ai metodi
createArea() e System.exit. Una volta che il programma è inizializzato, in qualsiasi momento si può uscire
scrivendo “exit” ed è possibile leggere la lista di tutti i comandi scrivendo “help”.
L’esecuzione può essere effettuata anche andando sulla sezione Gradle in alto a dx, poi Tasks>application>run
oppure in Run Configurations.

Esempio di una sequenza di comandi eseguibili da terminale.
move
5
45
45
7
continue
5
6
stop
Ricci Elena | 114995
2
5
signal
5
Ciao_
signal
3
Ciao_
Follow
7
Ciao_
70
8
Stop
7
Move random
7
67
68
80
86
6
