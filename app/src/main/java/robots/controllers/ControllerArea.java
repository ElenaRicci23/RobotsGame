package robots.controllers;

import robots.model.*;

import java.io.IOException;
import java.util.*;
import java.awt.geom.Point2D;

public class ControllerArea {

    private DefaultArea area;

    public void init() throws IOException {
        area = new DefaultArea();
        System.out.println("Area di gioco creata, lo sciame di robot è pronto!");
    }

    public DefaultArea getDefaultArea() { return this.area; }

    //il robot si muove verso la posizione (x,y) alla data velocità s espressa in m/s
    public void move (){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire id del robot che si vuole muovere: ");
        int id = scanner.nextInt();
        CartesianCoordinates position = this.area.getRobot(id).getPosition();
        System.out.println("Inserire la x della nuova posizione: ");
        double x = scanner.nextDouble();
        System.out.println("Inserire la y della nuova posizione: ");
        double y = scanner.nextDouble();
        System.out.println("Inserire la velocità: ");
        int s = scanner.nextInt();
        CartesianCoordinates newPosition = new CartesianCoordinates(x,y);
        this.area.updateRobotPosition(id, newPosition, s);
        updateRobotLabel(id);
        this.area.getRobot(id).setAngle(calculateAngle(position, newPosition));
        System.out.println("Robot <" + id + "> spostato in posizione <" + newPosition + "> a <" + s + "> m/s.");
    }

    /*
    il robot si muove alla velocità s espressa in m/s verso
    una posizione (x,y) scelta casualmente nell’intervallo [x1, x2] e [y1, y2];
    */
    public void moveRandom (){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire id del robot che si vuole muovere: ");
        int id = scanner.nextInt();
        CartesianCoordinates position = this.area.getRobot(id).getPosition();
        System.out.println("Inserire la x delle prime coordinate: ");
        double x1 = scanner.nextDouble();
        System.out.println("Inserire la y delle prime coordinate: ");
        double y1 = scanner.nextDouble();
        CartesianCoordinates c1 = new CartesianCoordinates(x1,y1);
        System.out.println("Inserire la x delle seconde coordinate: ");
        double x2 = scanner.nextDouble();
        System.out.println("Inserire la y delle seconde coordinate: ");
        double y2 = scanner.nextDouble();
        System.out.println("Inserire la velocità: ");
        int s = scanner.nextInt();
        CartesianCoordinates c2 = new CartesianCoordinates(x2,y2);
        double randomX = randomPointBetween(c1.getX(), c2.getX());
        double randomY = randomPointBetween(c1.getY(), c2.getY());
        CartesianCoordinates newPosition = new CartesianCoordinates(randomX, randomY);
        this.area.updateRobotPosition(id, newPosition, s);
        updateRobotLabel(id);
        this.area.getRobot(id).setAngle(calculateAngle(position, newPosition));
        System.out.println("Robot <" + id + "> spostato in posizione randomica <" + newPosition + "> a <" + s + "> m/s.");
    }

    public void signal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire id del robot interessato: ");
        int id = scanner.nextInt();
        System.out.println("Inserire la label: ");
        String label = scanner.next();
        this.area.getRobot(id).signal(label);
    }

    public void unsignal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire id del robot interessato: ");
        int id = scanner.nextInt();
        System.out.println("Inserire la label: ");
        String label = scanner.next();
        this.area.getRobot(id).unsignal(label);
    }

    /*
    il robot si muove alla velocità s espressa in m/s una direzione che è la media delle posizioni dei robot
    che segnalano la condizione label e che si trovano ad una distanza minore o uguale a dist. Se non
    esistono robot viene scelta una direzione a caso nell’intervallo [-dist, dist]x[-dist, dist]y.
     */
    public void follow (){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire id del robot che si vuole muovere: ");
        int id = scanner.nextInt();
        CartesianCoordinates position = this.area.getRobot(id).getPosition();
        System.out.println("Inserire la label: ");
        String label = scanner.next();
        System.out.println("Inserire la distanza: ");
        double dist = scanner.nextDouble();
        System.out.println("Inserire la velocità: ");
        int s = scanner.nextInt();
        CartesianCoordinates newPosition;
        ArrayList<Robot> robots = this.area.robotsWithSameLabel(label);
        if (!robots.isEmpty()) { //se esistono robot con stessa label cerco quelli nelle vicinzanze
            ArrayList<Robot> nearbyRobots = nearbyRobots(id, robots, dist);
            if (!nearbyRobots.isEmpty()) { //se esistono robot con stessa label nelle vicinanze procedo
                newPosition = averagePosition(nearbyRobots);
                this.area.updateRobotPosition(id, newPosition, s);
                this.area.getRobot(id).setAngle(calculateAngle(position, newPosition));
            }
        } else { //non esistono robot con stessa label nelle vicinanze
            double x = randomPointBetween(-dist, dist);
            double y = randomPointBetween(-dist, dist);
            newPosition = new CartesianCoordinates(x,y);
            this.area.updateRobotPosition(id, newPosition, s);
            this.area.getRobot(id).setAngle(calculateAngle(position, newPosition));
        }
        updateRobotLabel(id);
        System.out.println("Robot <" + id + "> in movimento a <" + s + "> m/s.");
    }


    //il robot continua a muoversi per s secondi con la medesima direzione e velocità.
    public void continuecmd() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire id del robot che si vuole muovere: ");
        int id = scanner.nextInt();
        System.out.println("Inserire i secondi: ");
        int s = scanner.nextInt();
        Robot r = this.area.getRobot(id);
        int speed = r.getSpeed(); //velocità attuale del robot
        double dist = s * speed; //distanza che percorrerà dati tali velocità e tempo [S=V*T]
        double angle = r.getAngle(); //angolo attuale del robot
        double x = r.getPosition().getX() + dist * Math.cos(angle); //X2 = X1 + d*cos(phi)
        double y = r.getPosition().getY() + dist * Math.sin(angle); //Y2 = Y1 + d*sin(phi)
        CartesianCoordinates c = new CartesianCoordinates(x,y);
        this.area.updateRobotPosition(id, c, speed);
        System.out.println("Robot <" + id + "> in movimento per <" + s + "> secondi.");
    }

    public void stop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire id del robot che si vuole fermare: ");
        int id = scanner.nextInt();
        Robot r = this.area.getRobot(id);
        r.setSpeed(0);
        System.out.println("Il robot è fermo.");
    }

    //restituisce l'angolo del robot, ottenuto utilizzando la foruma dell'arcocoseno
    private double calculateAngle(CartesianCoordinates c1, CartesianCoordinates c2) {
        double[] sides = calculateSides(c1, c2);
        double cosA = (Math.pow(sides[0],2) + Math.pow(sides[1],2) - Math.pow(sides[2],2))/(2 * sides[0] * sides[1]);
        return Math.toDegrees(Math.acos(cosA));
    }

    /*
     restituisce le dimensioni di cateti e ipotenusa del triangolo rettangolo ipotetico che
     immaginiamo si formi tra due punti, ciò serve a trovare l'angolo corrispondente al robot
     (immaginato come uno dei vertici del triangolo)
     */
    private double[] calculateSides(CartesianCoordinates c1, CartesianCoordinates c2) {
        double hipotenuse = distance(c1, c2);
        double cateteX = Math.abs(c1.getX() - c2.getX());
        double cateteY = Math.abs(c1.getY() - c2.getY());
        double[] sides = new double[3];
        sides[0] = hipotenuse;
        sides[1] = cateteX;
        sides[2] = cateteY;
        return sides;
    }

    //aggiorna la label del robot con la label della figura (rettangolo/cerchio) in cui si trova
    private void updateRobotLabel(int id) {
        Robot r = this.area.getRobot(id);
        CartesianCoordinates position = r.getPosition();
        ArrayList<Rectangle> rectangles = this.area.getRectangles();
        ArrayList<Circle> circles = this.area.getCircles();
        for (Rectangle rectangle: rectangles) { //controllo se il robot si trova in un rettangolo
            if (rectangle.contains(position)) { r.setLabel(rectangle.getLabel()); }
        }
        for (Circle circle: circles) { //controllo se il robot si trova in un cerchio
            if (circle.contains(position)) { r.setLabel(circle.getLabel()); }
        }
    }

    //resituisce una lista dei robot che si trovano nelle vicinanze di un certo robot, entro la distanza specificata
    private ArrayList<Robot> nearbyRobots(int id, ArrayList<Robot> robots, double dist) {
        ArrayList<Robot> nearbyRobots = new ArrayList<>();
        Robot r1 = this.area.getRobot(id);
        for (int i = 0; i < robots.size(); i++) {
            Robot r2 = robots.get(i);
            double distance = distance(r1.getPosition(), r2.getPosition());
            if (distance <= dist) nearbyRobots.add(r2);
        }
        return nearbyRobots;
    }

    //restituisce la posizione media rispetto alle posizioni di certi robot
    private CartesianCoordinates averagePosition(ArrayList<Robot> robots) {
        double xsum = 0;
        double ysum = 0;
        for (int i = 0; i < robots.size(); i++) {
            Robot r = robots.get(i);
            xsum += r.getPosition().getX();
            ysum += r.getPosition().getY();
        }
        double x = xsum/robots.size();
        double y = ysum/robots.size();
        return new CartesianCoordinates(x,y);
    }

    /*
ordina i valori x in modo che il valore minore sia indicato come x1 e il maggiore come x2,
e la differenza tra loro sia calcolata e memorizzata in delta; viene quindi calcolato un valore casuale
che restituisce un valore compreso tra 0,0 (incluso) e 1,0 (escluso) e moltiplicando il valore casuale
per il delta; questo ci dà un valore casuale, offset, che è compreso tra zero e la differenza totale tra
i due valori; infine dobbiamo aggiungere questo offset casuale al valore x minimo, x1, in modo che il punto
casuale si trovi davvero all'interno del rettangolo, da qualche parte tra x1 (incluso) e x2 (escluso).
 */
    private double randomPointBetween (double x1, double x2){
        Random rand = new Random();
        double delta = x2 - x1;
        double offset = rand.nextDouble() * delta;
        return x1 + offset;
    }

    //restituisce la distanza tra due punti nell'Area
    private double distance (CartesianCoordinates c1, CartesianCoordinates c2){
        return Point2D.distance(c1.getX(), c1.getY(), c2.getX(), c2.getY());
    }

}
