package com.example.menu;



import com.example.InputClass.InputHandler;
import com.example.kafkaconsumer.ClientConsumer;
import org.json.simple.JSONObject;

import java.util.InputMismatchException;

public class MainMenu{

    private static InputHandler inputHandler;

    //metod som anropar meny och stänger scanner
public void runMenu() {
    inputHandler = new InputHandler();
    menu();
    inputHandler.closeScanner();
}
    public void menu() {
        //anropar meny med olika val och switch case
        while (true) {
            try {
                //skriver ut meny
                menyOptions();
                //tar in input från användaren
                int choice = getUserChoice();
                //switch case som kollar vilket val användaren har gjort och sedan anropar olika metoder för olika funktioner
                switch (choice) {
                    //anropar metoden addMovie för att lägga till en ny film
                    case 1:
                        inputHandler.getStringInput();
                        addMovie();
                        break;
                        //anropar metoden viewMovies för att visa alla filmer
                    case 2:
                        inputHandler.getStringInput(); // konsumerar new line character
                        viewMovies();
                        break;
                        //anropar metoden exit för att avsluta applikationen
                    case 3:
                        exit();
                        break;
                        //om användaren inte har valt något av alternativen så skrivs ett felmeddelande ut
                    default:
                        System.out.println("Invalid input. Please enter a valid option.");
                        break;

                }
                //om input inte är en nummer så kastas exception och användaren får försöka igen
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                inputHandler.getStringInput(); // Consume the newline character
            }
        }

    }

    private void exit() {
    //metod som avslutar applikationen genom att anropa System.exit(0)
        System.out.println("Exiting the application.");
        System.exit(0);
    }


    private static void viewMovies() {
    // metod som hämtar data från kafka och skriver ut den i klient applikationen
        ClientConsumer.getDataFromKafka("movieListTopic");
    }



    private static void addMovie() { // metod som skapar en ny film
    // skapar en ny film med hjälp av input från användaren
        System.out.println("Enter id");
        long id = inputHandler.getLongInput();
        inputHandler.getStringInput(); //Konsumerar new line character
        System.out.print("Enter Movie Title: ");
        String title = inputHandler.getStringInput();
        System.out.print("Enter Movie Genre: ");
        String genre = inputHandler.getStringInput();
        System.out.print("Enter Movie Release Date: ");
        String releaseDate = inputHandler.getStringInput();
        // Skapar en JSON-objekt
        JSONObject myObj = new JSONObject();
        // Lägger till värden till JSON-objektet
        myObj.put("id", id);
        myObj.put("movieTitle", title);
        myObj.put("movieGenre", genre);
        myObj.put("movieReleaseDate", releaseDate);
        // Skickar JSON-objektet till WebAPI
        ClientConsumer.sendToWebAPI(myObj);
    }

    private void menyOptions(){
        //metod som skriver ut meny
        String[] options = {"Main Menu:","1. Add a movie", "2. View movies", "3. Exit"};
        // loopar igenom arrayen och skriver ut varje element genom att använda for each loop
        for (String option : options) {
            System.out.println(option);
        }
    }
    private int getUserChoice() {
    //metod som tar in input från användaren
        System.out.print("Select an option:\n ");
        return inputHandler.getIntInput();
    }


}