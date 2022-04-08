package data;

import exсeptions.WrongFieldFormatException;

public class Human {
    private String name; //Поле не может быть null, Строка не может быть пустой
    public Human(String name){
        this.name = name;
    }

    Human(){}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                '}';
    }

    public static boolean validateName (String name) throws WrongFieldFormatException {
        if (name == null) throw new WrongFieldFormatException("governor name can't be null");
        if (name.equals("")){
            throw new WrongFieldFormatException("name can't be empty");
        }
        else {
            return true;
        }
    }
}
