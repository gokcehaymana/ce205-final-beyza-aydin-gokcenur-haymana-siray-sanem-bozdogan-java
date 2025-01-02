/**

@file EventApp.java
@brief This file serves as the main application file for the Event App.
@details This file contains the entry point of the application, which is the main method. It initializes the necessary components and executes the Event App.
*/
/**

@package com.beyza.gokce.siray.event
@brief The com.beyza.gokce.siray.event package contains all the classes and files related to the Event App.
*/
package com.beyza.gokce.siray.event;

import java.util.Arrays;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 * @class EventApp
 * @brief This class represents the main application class for the Event
 *        App.
 * @details The EventApp class provides the entry point for the Event
 *          App. It initializes the necessary components, performs calculations,
 *          and handles exceptions.
 * @author ugur.coruh
 */
import java.util.Arrays;

public class EventApp {

    /**
     * The main method serves as the entry point for the Java application.
     * It orchestrates the initialization of the event management system.
     * 
     * <p>Workflow:</p>
     * <ol>
     *   <li>Loads event data from a file using the {@link Event#loadHashTableFromFile()} method.</li>
     *   <li>Displays the main menu using the {@link Event#mainMenu()} method for user interactions.</li>
     * </ol>1
     * 
     * @param args Command-line arguments (not utilized in this application).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Kullanıcı girişini okuyacak
        PrintStream out = System.out; // Konsola çıktı gönderecek

        Event event = new Event(scanner, out);
        Event.loadHashTableFromFile(); 
        Event.mainMenu(); 
    }
}
