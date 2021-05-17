package com.company;

import java.io.Serializable;

public class Animal implements Serializable {
    private int id, year;
    private String name, type, food;
    private static int currentId = 0;

    public Animal(int id, int year, String name, String type, String food) {
        this.id = id;
        this.year = year;
        this.name = name;
        this.type = type;
        this.food = food;
    }

    public Animal(int year, String name, String type, String food) {
        this.id = (++currentId);
        this.year = year;
        this.name = name;
        this.type = type;
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getFood() {
        return food;
    }

    @Override
    public String toString() {
        return String.format("id = %d,\tyears = %d,\tname = %s,\ttype = %s,\tfood = %s", id, year, name, type, food);
    }
}
