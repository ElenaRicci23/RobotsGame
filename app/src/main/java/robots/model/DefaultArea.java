package robots.model;

/*
DefaultArea è lo spazio in cui si muovono i robot
DefaultArea è occupata da 100 rettangoli e 100 cerchi che rappresentano areecon determinate carateristiche (label)
 */

import robots.utilities.RandomParam;
import java.util.ArrayList;

public class DefaultArea implements Area{

    private final double weight; //base
    private final double height; //altezza
    private ArrayList<Robot> robots; //lista di robot
    private ArrayList<Rectangle> rectangles; //lista di rettangoli
    private ArrayList<Circle> circles; //lista di cerchi
    private RandomParam random = new RandomParam();

    public DefaultArea() {
        this.weight = 1650;
        this.height = 1080;
        this.robots = generateRobots();
        this.rectangles = generateRectangles();
        this.circles = generateCircles();
    }

    public void addRobot(Robot robot) {
        this.robots.add(robot);
    } //aggiunge un robot alla lista di robot

    //genera 70 robot con posizioni casuali
    public ArrayList<Robot> generateRobots() {
        ArrayList<Robot> robots1 = new ArrayList<>();
        int i = 0;
        while (i <= 70) {
            assert random != null;
            CartesianCoordinates c = random.randomRobotCoordinates();
            robots1.add(new Robot(c));
            i++;
        }
        return robots1;
    }

    //genera 100 rettangoli con parametri casuali
    private ArrayList<Rectangle> generateRectangles(){
        ArrayList<Rectangle> rectangles1 = new ArrayList<>();
        int i = 0;
        while (i <= 100) {
            rectangles1.add(new Rectangle(random.randomWeigh(), random.randomHeight(),
                    random.randomLabel(), random.randomShapeCoordinates()));
            i++;
        }
        return rectangles1;
    }

    //genera 100 cerchi con parametri casuali
    private ArrayList<Circle> generateCircles(){
        ArrayList<Circle> circles1 = new ArrayList<>();
        int i = 0;
        while (i <= 100) {
            circles1.add(new Circle(random.randomRadius(), random.randomLabel(),
                    random.randomShapeCoordinates()));
            i++;
        }
        return circles1;
    }

    //restituisce tutti i robot che condividono una certa label
    public ArrayList<Robot> robotsWithSameLabel(String label) {
        ArrayList<Robot> result = new ArrayList<>();
        for (int i = 0; i < this.robots.size(); i++) {
            Robot r = this.robots.get(i);
            if (r.getLabel().equals(label)) result.add(r);
        }
        return result;
    }

    //restituisce il robot che ha l'id corrispondente [metodo utile per individuare il robot che si vuole comandare]
    @Override
    public Robot getRobot(int id) {
        for (int i = 0; i < this.robots.size(); i++) {
            Robot r = this.robots.get(i);
            if (r.getId() == id) return r;
        }
        return null;
    }

    @Override
    public ArrayList<Robot> getRobots() { return this.robots; } //restituisce la lista di robot nell'area
    @Override
    public ArrayList<Rectangle> getRectangles() { return this.rectangles; } //resituisce la lista di rettangoli nell'area
    @Override
    public ArrayList<Circle> getCircles() { return this.circles; } //resituisce la lista di cerchi nell'area
    @Override
    public double getWeight() { return weight; } //restituisce la base dell'area
    @Override
    public double getHeight() { return height; } //restituisce l'altezza dell'area

    //aggiorna la posizione di un determinato robot
    @Override
    public void updateRobotPosition(int id, CartesianCoordinates newPosition, int speed) {
        this.getRobot(id).updatePosition(newPosition);
        this.getRobot(id).setSpeed(speed);
    }
}
