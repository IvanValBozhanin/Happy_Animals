package com.company;

import java.io.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Animal> animals = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        /**
         * For first start of the file
         */
        {
            initialize();

            showAll();

            add();
            add();

            showAll();

            edit();

            showAll();

            backUp();
        }

        /*
        For every consecutive start of the file
        {
            restoreFromBackUp();
            showAll();
            add();
            showAll();
            edit();
            backUp();
        }
        */
    }

    public static void restoreFromBackUp() throws IOException{
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("back.dat"))){
            Animal.setCurrentId(in.readInt());
            for(;;){
                Animal animal = (Animal) in.readObject();
                animals.add(animal);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e){
            System.out.println("======Restored From Back Up======");
        }

        push();
    }

    public static void backUp() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("back.dat"))) {
            out.writeInt(animals.size());
            for (Animal animal : animals) {
                out.writeObject(animal);
            }
        }
    }

    public static void initialize() throws IOException {
        animals.add(new Animal(10, "Aaaaa", "monkey", "Ped"));
        animals.add(new Animal(7, "Ivan", "dog", "Bruh"));
        animals.add(new Animal(19, "Georgi", "cat", "Wis"));
        animals.add(new Animal(2, "BBBBBB", "hen", "ChickenNuggets"));

        push();
    }

    public static void showAll() throws IOException {
        System.out.println();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src.dat"))) {
            try {
                for (; ; ) {
                    Animal animal = (Animal) in.readObject();
                    System.out.println(animal.toString());
                }
            } catch (EOFException e) {
                System.out.println();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void add() throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter year: ");
        int year = in.nextInt();

        System.out.print("Enter name: ");
        String name = in.next();

        System.out.print("Enter type: ");
        String type = in.next();

        System.out.print("Enter food: ");
        String food = in.next();

        animals.add(new Animal(year, name, type, food));

        push();
    }

    public static void edit() throws IOException {
        Scanner key = new Scanner(System.in);
        System.out.print("Enter id to change: ");
        int id = key.nextInt();

        int pos = -1;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src.dat"))) {
            try {
                for (int i = 0; true; ++i) {
                    Animal animal = (Animal) in.readObject();
                    if (animal.getId() == id) {
                        pos = i;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EOFException e) {
                if (pos == -1) {
                    System.out.println("--------Cannot find this ID!------");
                    return;
                }
            }
        }

        System.out.print(">You have to write all the new data\nChange to year: ");
        int year = key.nextInt();

        System.out.print("Change to name: ");
        String name = key.next();

        System.out.print("Change to type: ");
        String type = key.next();

        System.out.print("Change to food: ");
        String food = key.next();

        animals.remove(pos);
        animals.add(pos, new Animal(pos + 1, year, name, type, food));

        push();
    }

    public static void push() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src.dat"))) {
            for (Animal animal : animals) {
                out.writeObject(animal);
            }
        }
    }
}
