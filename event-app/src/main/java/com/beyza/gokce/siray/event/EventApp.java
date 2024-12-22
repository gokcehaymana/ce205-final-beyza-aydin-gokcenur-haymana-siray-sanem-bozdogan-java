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
    public static void main(String[] args) {
        Event.loadHashTableFromFile(); // Event sınıfındaki yöntemi çağırıyoruz
        Event.mainMenu(); // Event sınıfındaki mainMenu metodunu çağırıyoruz
    }
}

