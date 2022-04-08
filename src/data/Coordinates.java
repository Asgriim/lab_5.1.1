package data;

import exсeptions.WrongFieldFormatException;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Coordinates")

public class Coordinates {
    private Integer x; //Значение поля должно быть больше -251, Поле не может быть null
    private double y;

    public Coordinates(){};

    public Coordinates(Integer x, double y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public static boolean validateX(String x) throws WrongFieldFormatException {
        if (x == null) throw new WrongFieldFormatException("x can't be null");
        Integer num;
        try {
            num = Integer.valueOf(x);
        } catch (NumberFormatException e) {
            throw new WrongFieldFormatException("x must be an integer");
        }
        if (num <= -251){
            throw new WrongFieldFormatException("x must be greater than -251");
        }
        return true;
    }
    public static boolean validateY(String y) throws WrongFieldFormatException {
        if (y == null) throw new WrongFieldFormatException("y can't be null");
        Double num;
        try {
            Double.valueOf(y);
        } catch (NumberFormatException e) {
            throw new WrongFieldFormatException("y must be double");
        }
        return true;
    }
}
