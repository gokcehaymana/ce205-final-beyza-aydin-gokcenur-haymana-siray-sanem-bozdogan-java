/**
 * @file Event.java
 * @brief This file serves as a demonstration file for the Event class.
 * @details This file contains the implementation of the Event class, which provides various mathematical operations.
 */

/**
 * @package com.beyza.gokce.siray.event
 * @brief The com.beyza.gokce.siray.event package contains all the classes and files related to the Event App.
 */
package com.beyza.gokce.siray.event;

import java.io.*;
import java.util.*;

/**
 * @class Event
 * @brief This class represents an Event that performs various operations, including hash table management, Huffman coding, and algorithms.
 * @details The Event class provides methods for user authentication, event management, attendee handling, and algorithm demonstrations.
 * @author ugur.coruh
 */
public class Event {

	private static Scanner scanner; // Declare scanner as a field
	private static PrintStream out; // Declare out as a field

	public Event(Scanner scanner, PrintStream out) {
		this.scanner = scanner; // Assign the scanner parameter
		this.out = out;         // Assign the out parameter
	}

	/**
	 * Maximum number of feedbacks allowed in the system.
	 * This value defines the upper limit for the number
	 * of feedback ratings that can be stored at any given time.
	 */
 static final int MAX_FEEDBACKS = 10;
     /**
	 * An array to store feedback ratings provided by users.
	 * Each index in this array corresponds to a feedback entry,
	 * and the values represent ratings on a predefined scale.
	 */
	static int[] feedbackRatings = new int[MAX_FEEDBACKS];
	/**
	 * A counter to track the number of feedback entries currently stored.
	 * This variable is incremented whenever a new feedback is added
	 * and should not exceed the {@code MAX_FEEDBACKS} value.
	 */
	static int feedbackCount = 0;


	public Event() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Computes a hash value for a given phone number string.
	 * The hashing function uses a polynomial accumulation method
	 * where each character contributes to the hash value based on its
	 * ASCII value and position. The resulting hash value is constrained
	 * within the bounds of {@code TABLE_SIZE} to ensure compatibility
	 * with the hash table structure.
	 * 
	 * @param phone The phone number string for which the hash value will be generated.
	 *              It is expected to be a valid non-null string containing numeric digits.
	 * @return An integer representing the computed hash value, which can be used
	 *         as an index for storing data in a hash table.
	 */

	public static int hash(String phone) {
	    int hash = 0;
	    for (int i = 0; i < phone.length(); i++) { 
	        hash = (hash * 31 + phone.charAt(i)) % TABLE_SIZE;
	    }
	    return hash;
	}
	

	/**
	 * Retrieves the hash table containing user data.
	 * The hash table is represented as an array of {@code User} objects,
	 * where each index corresponds to a hashed key derived from a user's phone number.
	 * 
	 * @return An array of {@code User} objects that represents the current state
	 *         of the hash table used in the system.
	 */
	public static User[] getHashTable() {
	    return hashTable;
	}
	
	/**
	 * Clears the console screen based on the operating system.
	 * This function detects the underlying OS and executes the appropriate
	 * command to clear the terminal/console screen.
	 * 
	 * For Windows systems, it runs the "cls" command using a process builder.
	 * For Linux, Unix-like systems, or macOS, it runs the "clear" command.
	 * 
	 * If any exception occurs during the execution of the process,
	 * the exception stack trace is printed for debugging purposes.
	 */
	
	public static void clearScreen() {
	    String os = System.getProperty("os.name").toLowerCase();
	    
	    try {
	        if (os.contains("win")) { 
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
	            ProcessBuilder processBuilder = new ProcessBuilder("clear");
	           
	        }
	    } catch (Exception e) {
	    }
	}
	/**
	 * Defines the size of the hash table used in the system.
	 * This constant represents the maximum number of entries
	 * the hash table can accommodate without resizing.
	 */
	public static int TABLE_SIZE = 100;

	/**
	 * Specifies the maximum number of nodes allowed in a tree structure.
	 * This constant is used as a constraint to limit the size of any tree
	 * data structure used in the program to ensure efficient memory usage.
	 */
	public static final int MAX_TREE_NODES = 256;


	/**
	 * Represents the structure for an attendee.
	 * This class stores information about an attendee, including their
	 * name, surname, and their associated Huffman code.
	 */
	class Attendeer{
	    /**
	     * The first name of the attendee.
	     */
	    String nameAttendee;
	    /**
	     * The surname of the attendee.
	     */
	    String surnameAttendee;
	    /**
	     * The Huffman code associated with the attendee.
	     * This code is used for encoding or decoding purposes
	     * in Huffman coding algorithms.
	     */
	    String huffmanCode;  // Store the Huffman code
	}

	/**
	 * Represents a node in the Huffman tree.
	 * Each node contains a character, its frequency, and pointers
	 * to the left and right child nodes in the Huffman tree.
	 */
	public static class MinHeapNode {
		 /**
	     * The character represented by this node.
	     */
	    char data;
	    /**
	     * The frequency of the character represented by this node.
	     */
	    int freq;
	    /**
	     * Pointer to the left child node in the Huffman tree.
	     */
	    MinHeapNode left, right;
	    /**
	     * Pointer to the right child node in the Huffman tree.
	     */
	}
	/**
	 * Represents a Min Heap structure.
	 * The Min Heap is a binary tree used to efficiently find
	 * the minimum frequency node, primarily in Huffman coding.
	 */
	public static class MinHeap {
		/**
	     * The current size of the Min Heap.
	     */
	    int size;
	    /**
	     * The maximum capacity of the Min Heap.
	     */
	    int capacity;
	    /**
	     * Array of {@code MinHeapNode} objects representing the nodes
	     * in the Min Heap.
	     */
	    MinHeapNode[] array;  // Array of MinHeapNode objects
	}
	
	/**
	 * Creates a Min Heap with a given capacity.
	 * The function initializes a new {@code MinHeap} object, sets its size to zero,
	 * and allocates memory for its array if the specified capacity is greater than zero.
	 * 
	 * @param capacity The maximum capacity of the Min Heap.
	 * @return A {@code MinHeap} object with the specified capacity.
	 */
	public static MinHeap createMinHeap(int capacity) {
	    MinHeap minHeap = new MinHeap();
	    minHeap.size = 0;
	    minHeap.capacity = capacity;

	    // Only allocate memory for array if capacity is greater than 0
	    if (capacity > 0) {
	        minHeap.array = new MinHeapNode[capacity];
	    } else {
	        minHeap.array = null;
	    }

	    return minHeap;
	}
	/**
	 * Creates a new node for the Min Heap.
	 * This function initializes a {@code MinHeapNode} object with the given
	 * character data and frequency. The left and right child pointers are
	 * set to {@code null} as it is a standalone node.
	 * 
	 * @param data The character to be stored in the node.
	 * @param freq The frequency of the character.
	 * @return A new {@code MinHeapNode} object initialized with the specified data and frequency.
	 */
	public static MinHeapNode createMinHeapNode(char data, int freq) {
	    MinHeapNode newNode = new MinHeapNode();
	    newNode.data = data;
	    newNode.freq = freq;
	    newNode.left = newNode.right = null;
	    return newNode;
	}
	
	/**
	 * Inserts a node into the Min Heap.
	 * This function places the specified {@code MinHeapNode} into the next available
	 * position in the heap's array and increments the heap's size.
	 * 
	 * @param minHeap The Min Heap into which the node will be inserted.
	 * @param minHeapNode The {@code MinHeapNode} to be inserted.
	 */
	public static void insertMinHeap(MinHeap minHeap, MinHeapNode minHeapNode) {
	    minHeap.array[minHeap.size] = minHeapNode;
	    minHeap.size++;
	}
	/**
	 * Extracts the minimum frequency node from the Min Heap.
	 * This function retrieves the root node of the heap (which holds the minimum frequency value),
	 * replaces it with the last node in the heap, and reduces the heap's size by one.
	 * 
	 * @param minHeap The Min Heap from which the minimum node will be extracted.
	 * @return The {@code MinHeapNode} object with the smallest frequency value.
	 */
	public static MinHeapNode extractMin(MinHeap minHeap) {
	    MinHeapNode temp = minHeap.array[0];
	    minHeap.array[0] = minHeap.array[minHeap.size - 1];
	    minHeap.size--;
	    return temp;
	}
	
	
	/**
	 * Represents a user in the system.
	 * This class holds information about a user, including their personal details
	 * and account credentials. It also includes a reference to the next user
	 * in the linked list, which is used to handle collisions in a hash table.
	 */
	public static class User {
		 /**
	     * The first name of the user.
	     */
	    String name;
	    /**
	     * The surname of the user.
	     */
	    String surname;
	    /**
	     * The phone number of the user.
	     * This is used as a unique identifier in the system.
	     */
	    String phone;
	    /**
	     * The password of the user.
	     * This is used for account authentication.
	     */
	    String password;
	    /**
	     * Reference to the next user in the linked list for collision resolution.
	     */
	    User next;  // We use linked list to resolve collisions

	    /**
	     * Sets the password for the user.
	     * 
	     * @param string The new password to be set for the user.
	     */
	    
		public void setPassword(String string) {
			// TODO Auto-generated method stub
			
		}
		 /**
	     * Sets the email for the user.
	     * 
	     * @param string The email address to be set for the user.
	     */
		public void setEmail(String string) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * Represents an event in the system.
	 * This class contains information about an event, such as its type, date, color,
	 * and concept. It also includes references to the previous and next events
	 * to support a doubly linked list structure.
	 */
	class Event1 {
	    /**
	     * The type of the event (e.g., meeting, conference, party).
	     */
	    String type;

	    /**
	     * The date of the event.
	     */
	    String date;

	    /**
	     * The color associated with the event.
	     * This is used for visual representation.
	     */
	    String color;

	    /**
	     * The concept or theme of the event.
	     */
	    String concept;

	    /**
	     * Reference to the previous event in the doubly linked list.
	     */
	    Event1 prev;

	    /**
	     * Reference to the next event in the doubly linked list.
	     */
	    Event1 next;

	    /**
	     * Constructs an {@code Event1} object with the specified type, date, color, and concept.
	     * Initializes the {@code prev} and {@code next} references to {@code null}.
	     * 
	     * @param type The type of the event.
	     * @param date The date of the event.
	     * @param color The color associated with the event.
	     * @param concept The concept or theme of the event.
	     */
	    public Event1(String type, String date, String color, String concept) {
	        this.type = type;
	        this.date = date;
	        this.color = color;
	        this.concept = concept;
	        this.prev = null;  // Initially, no previous event
	        this.next = null;  // Initially, no next event
	    }
	}
	/**
	 * The hash table used for storing user data.
	 * Each index in the array represents a slot in the hash table,
	 * and collisions are resolved using a linked list of {@code User} objects.
	 */
	public static User[] hashTable = new User[TABLE_SIZE]; 
	/**
	 * Saves a new user to the hash table.
	 * This function calculates the hash index of the user's phone number,
	 * and inserts the user into the corresponding slot in the hash table.
	 * If a collision occurs, the user is added to the linked list at that index.
	 * 
	 * @param newUser The {@code User} object to be saved in the hash table.
	 */
	public static void saveUser(User newUser) {
	    int index = hash(newUser.phone);
	    newUser.next = hashTable[index];
	    hashTable[index] = newUser;
	}

	/**
	 * Prints the contents of the hash table.
	 * This function iterates through the hash table, and for each non-empty slot,
	 * it prints the details of all users stored in the linked list at that index.
	 */
	public static void printHashTable() {
	    out.println("Hash Table Contents:");
	    for (int i = 0; i < TABLE_SIZE; i++) {
	        User current = hashTable[i];
	        if (current == null) {
	            continue;
	        }
	        out.println("Index " + i + ":");
	        while (current != null) {
	            out.println(" Name: " + current.name + " " + current.surname + ", Phone: " + current.phone + ", Password: " + current.password);
	            current = current.next;
	        }
	    }
	    out.println("End of Hash Table.");
	}
	/**
	 * Saves a user's data.
	 * This function saves the user to the hash table, writes the updated hash table
	 * to a file, and clears the screen to refresh the view.
	 *
	 * @param user The {@code User} object to be saved.
	 */
	public static void saveUserData(User user) {
	    saveUser(user);
	    saveHashTableToFile();
	    clearScreen();
	}
	/**
	 * Loads the hash table from a file.
	 * This function reads serialized {@code User} objects from a file and
	 * adds them back to the hash table. If the file does not exist or an error occurs,
	 * the function will handle the error gracefully.
	 */

	public static void loadHashTableFromFile() {
	    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.bin"))) {
	        while (true) {
	            try {
	                User newUser = (User) in.readObject();
	                newUser.next = null;
	                saveUser(newUser);
	            } catch (EOFException e) {
	                // End of file reached, stop reading
	                break;
	            } catch (ClassNotFoundException e) {

	                break;
	            }
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	/**
	 * Saves the hash table to a file.
	 * This function serializes all {@code User} objects stored in the hash table
	 * and writes them to a binary file. Each user in the hash table is written
	 * along with their linked list.
	 */
	public static void saveHashTableToFile() {
		// Hash table definition
		User[] hashTable = new User[TABLE_SIZE];
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("users.bin"))) {
	        for (int i = 0; i < TABLE_SIZE; i++) {
	            User current = hashTable[i];
	            while (current != null) {
	                out.writeObject(current);
	                current = current.next;
	            }
	        }
	    } catch (IOException e) {
	    }
	}

	/**
	 * Validates the login credentials of a user.
	 * This function checks if the provided phone number and password match
	 * any user's credentials stored in the hash table.
	 *
	 * @param phone The phone number provided by the user.
	 * @param password The password provided by the user.
	 * @return {@code true} if the credentials are valid, {@code false} otherwise.
	 */

	public static boolean validateLogin(String phone, String password) {
	    if (phone == null || password == null) {
	        return false;
	    }

	    int index = hash(phone);
		User[] hashTable = new User[TABLE_SIZE];
	    User current = hashTable[index];
	    while (current != null) {
	        if (current.phone.equals(phone) && current.password.equals(password)) {
	            return true;
	        }
	        current = current.next;
	    }
	    return false;
	}

	/**
	 * Inserts a user into the hash table using quadratic probing.
	 * This function handles collisions by trying alternate slots determined
	 * by the quadratic probing formula: {@code index + i^2}.
	 *
	 * @param newUser The {@code User} object to be inserted.
	 * @return {@code true} if the user is successfully inserted, {@code false} if the hash table is full.
	 */


	/**
	 * Generates Huffman codes for the characters in the Huffman tree.
	 * This recursive function traverses the Huffman tree and constructs the
	 * Huffman codes for each character based on the tree's structure. The generated
	 * codes are appended to a {@code StringBuilder} for further use or display.
	 *
	 * @param root The root node of the Huffman tree.
	 * @param code A character array used to store the current Huffman code during traversal.
	 * @param top The current index in the {@code code} array.
	 * @param huffmanCode A {@code StringBuilder} to store the generated Huffman codes.
	 */
	public static void generateHuffmanCodes(MinHeapNode root, char[] code, int top, StringBuilder huffmanCode) {
	    if (root.left != null) {
	        code[top] = '0';
	        generateHuffmanCodes(root.left, code, top + 1, huffmanCode);
	    }

	    if (root.right != null) {
	        code[top] = '1';
	        generateHuffmanCodes(root.right, code, top + 1, huffmanCode);
	    }

	    // Save the generated Huffman code for the character
	    if (root.left == null && root.right == null) {
	        code[top] = '\0';
	        huffmanCode.append(root.data).append(": ").append(new String(code, 0, top)).append("\n");
	    }
	}

	/**
	 * Builds a Huffman tree for a given string and assigns the generated Huffman codes to an attendee.
	 * This function calculates the frequency of characters in the string, creates a min-heap to construct
	 * the Huffman tree, and then generates Huffman codes for the characters in the string.
	 *
	 * @param str The input string for which the Huffman tree is built.
	 * @param attendee The {@code Attendeer} object to which the generated Huffman codes are assigned.
	 */
	public static void buildHuffmanTree(String str, Attendeer attendee) {
	    // Frequency array
	    int[] freq = new int[MAX_TREE_NODES];
	    for (int i = 0; i < str.length(); i++) {
	        freq[(int) str.charAt(i)]++;
	    }

	    MinHeap minHeap = createMinHeap(MAX_TREE_NODES);

	    // Create a min-heap for characters with non-zero frequency
	    for (int i = 0; i < MAX_TREE_NODES; i++) {
	        if (freq[i] > 0) {
	            MinHeapNode minHeapNode = createMinHeapNode((char) i, freq[i]);
	            insertMinHeap(minHeap, minHeapNode);
	        }
	    }

	    // Build the Huffman Tree
	    while (minHeap.size != 1) {
	        MinHeapNode left = extractMin(minHeap);
	        MinHeapNode right = extractMin(minHeap);

	        MinHeapNode top = createMinHeapNode('$', left.freq + right.freq);
	        top.left = left;
	        top.right = right;

	        insertMinHeap(minHeap, top);
	    }

	    // The remaining node is the root of the Huffman Tree
	    MinHeapNode root = extractMin(minHeap);
	    char[] huffmanCode = new char[256]; // Buffer to store the generated code
	    StringBuilder currentCode = new StringBuilder(); // Create a new StringBuilder
	    generateHuffmanCodes(root, huffmanCode, 0, currentCode); // Pass the StringBuilder

	    // Assign the Huffman codes to the attendee's huffmanCode
	    attendee.huffmanCode = currentCode.toString(); // Convert StringBuilder to String
	}

	/**
	 * Implements the Progressive Overflow Algorithm.
	 * This function iterates over an array of integers and calculates the cumulative sum.
	 * If the sum exceeds a specified overflow threshold, an overflow is detected,
	 * and corrective action is taken by resetting the sum to the current element.
	 *
	 * @param array The array of integers to process.
	 * @param overflowThreshold The threshold value at which overflow is detected.
	 */

	public static void progressiveOverflowAlgorithm() {
	    int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	    int overflowThreshold = 7; // Set the overflow threshold for demonstration
	    int currentSum = 0;

	    // Step through each element and calculate sum
	    for (int i = 0; i < array.length; i++) {
	        currentSum += array[i];
	        if (currentSum > overflowThreshold) {
	            out.printf("Overflow detected at element %d with sum %d%n", i, currentSum);
	            // Corrective action: Reset but include current element in new sum
	            currentSum = array[i];
	        }
	    }
	}
	/**
	 * Registers a new user by accepting username and password input.
	 * The method checks if the username already exists and if not, creates a new user,
	 * saves the user to the hash table, and adds the user to the HashMap.
	 *
	 * @param scanner The scanner object used to capture user input.
	 */
	static void Register(Scanner scanner) {
	    out.print("Enter a name to register: ");
	    String username = scanner.nextLine();
	    out.print("Enter a password: ");
	    String password = scanner.nextLine();


	        User newUser = new User();
	        newUser.name = username;
	        newUser.surname = "";
	        newUser.phone = "";
	        newUser.password = password;

	        saveUser(newUser);

	        users.put(username, password);

	        out.println("Registration successful! You can now log in.");
	        out.println("Current Hash Table:");

	        printHashTable();
	}


	/**
	 * Displays the main menu for the user to choose actions.
	 * The menu offers different options like user authentication, event details,
	 * attendee management, schedule organizer, feedback collection, and exiting the program.
	 *
	 * @return {@code true} if the menu was displayed successfully and user selected an option, {@code false} otherwise.
	 */
	public static boolean mainMenu() {
	    
	    int choice;
	    do {
	        out.println("\n-----------------------------------");
	        out.println("WELCOME TO OUR PLANNER!!");
	        out.println("-----------------------------------");
	        out.println("1. User Authentication");
	        out.println("2. Event Details");
	        out.println("3. Attendee Management");
	        out.println("4. Schedule Organizer");
	        out.println("5. Feedback Collection");
	        out.println("6. Exit");
	        out.print("Please enter your choice: ");
	        choice = scanner.nextInt();

	        switch (choice) {
	            case 1:
	                authentication();
	                break;
	            case 2:
	                eventDetails();
	                break;
	            case 3:
	                attendee();
	                break;
	            case 4:
	                schedule();
	                break;
	            case 5:
	                feedback();
	                break;
	            case 6:
	            	 out.println("Exiting the program. Goodbye!");
	             return false;
	            default:
	                out.println("Invalid choice. Please try again.");
	                break;
	        }
	    } while (choice != 6);
	    return true;
	}
	/**
	 * Executes the Progressive Overflow algorithm.
	 * This function iterates over an array of integers, adding each element to the cumulative sum.
	 * If the sum exceeds a specified overflow threshold, it prints the overflow details
	 * and resets the sum to zero for simplicity.
	 *
	 * @param array The array of integers to process.
	 * @param overflowThreshold The threshold value for detecting overflow.
	 */
	public static void progressiveOverflow() {
	    out.println("Executing Progressive Overflow algorithm...");
	    int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	    int overflowThreshold = 7; // Set the overflow threshold for demonstration
	    int currentSum = 0;

	    for (int i = 0; i < array.length; i++) {
	        currentSum += array[i];
	        if (currentSum > overflowThreshold) {
	            out.printf("Overflow detected at index %d with sum %d\n", i, currentSum);
	            currentSum = 0; // Reset for simplicity
	        }
	    }
	}

	/**
	 * Executes the Linear Probing algorithm to handle collisions in a hash table.
	 * This function initializes a hash table and inserts keys using linear probing.
	 * If a collision occurs, the algorithm moves to the next index until an empty slot is found.
	 * It prints the resulting hash table with placed and empty slots.
	 *
	 * @param hashTableSize The size of the hash table.
	 * @param keys The array of keys to insert into the hash table.
	 */
	public static void linearProbing() {
	    out.println("Executing Linear Probing algorithm...");
	    int[] hashTable = new int[10];
	    for (int i = 0; i < hashTable.length; i++) {
	        hashTable[i] = -1; // Initialize all values as empty (-1)
	    }

	    int[] keys = {23, 45, 12, 37, 29};
	    int size = keys.length;

	    for (int i = 0; i < size; i++) {
	        int index = keys[i] % 10;
	        int startIndex = index;
	        boolean placed = false; // Track whether the key is placed

	        do {
	            if (hashTable[index] == -1) {
	                hashTable[index] = keys[i];
	                placed = true; // Key placed successfully
	                break;
	            }
	            index = (index + 1) % 10;
	        } while (index != startIndex);

	    }

	    // Print hash table contents
	    for (int i = 0; i < hashTable.length; i++) {
	        if (hashTable[i] != -1) {
	            out.printf("Index %d: %d\n", i, hashTable[i]);
	        } else {
	            out.printf("Index %d: Empty\n", i);
	        }
	    }
	}
	/**
	 * Executes the Quadratic Probing algorithm to handle collisions in a hash table.
	 * This function initializes a hash table and inserts keys using quadratic probing.
	 * If a collision occurs, the algorithm increases the probe index using quadratic increments
	 * (i.e., adding squares of integers) until an empty slot is found.
	 * It prints the resulting hash table with the inserted keys.
	 *
	 * @param hashTableSize The size of the hash table.
	 * @param keys The array of keys to insert into the hash table.
	 */

	public static void quadraticProbing() {
	    out.println("Executing Quadratic Probing algorithm...");
	    int[] hashTable = new int[10];
	    int[] keys = {23, 45, 12, 37, 29};

	    for (int key : keys) {
	        int index = key % 10;
	        int j = 0;
	        while (hashTable[(index + j * j) % 10] != 0) {
	            j++;
	        }
	        hashTable[(index + j * j) % 10] = key;
	    }

	    // Print hash table
	    for (int i = 0; i < hashTable.length; i++) {
	        out.printf("Index %d: %d\n", i, hashTable[i]);
	    }
	}
	/**
	 * Executes the Double Hashing algorithm to handle collisions in a hash table.
	 * This function applies two hash functions: the first calculates the index,
	 * and the second calculates the step size for resolving collisions.
	 * The keys are placed in the hash table while handling collisions using double hashing.
	 *
	 * @param hashTableSize The size of the hash table.
	 * @param keys The array of keys to insert into the hash table.
	 */
	public static void doubleHashing() {
	    out.println("Executing Double Hashing algorithm...");
	    int[] hashTable = new int[10]; // Hash table initialized to 0
	    int[] keys = {23, 45, 12, 37, 29}; // Keys to insert

	    for (int key : keys) {
	        int index = key % 10; // First hash function
	        int step = 7 - (key % 7); // Second hash function for double hashing
	        while (hashTable[index] != 0) { // Resolve collisions
	            index = (index + step) % 10;
	        }
	        hashTable[index] = key; // Place the key at the correct index
	    }

	    // Print hash table
	    for (int i = 0; i < hashTable.length; i++) {
	        out.printf("Index %d: %d\n", i, hashTable[i]);
	    }
	}
	/**
	 * Implements the Use of Buckets algorithm where each bucket is an array.
	 * The function places each key into a bucket based on its modulo with the number of buckets.
	 * It uses the modulo operation to determine which bucket the key should be placed in,
	 * and prints the contents of each bucket.
	 *
	 * @param numberOfBuckets The number of buckets to use in the hashing process.
	 * @param keys The array of keys to insert into the buckets.
	 */

	public static void useBuckets() {
	    out.println("Executing Use of Buckets algorithm...");
	    int[][] buckets = new int[3][10];
	    int[] keys = {23, 45, 12, 37, 29};

	    for (int key : keys) {
	        int bucketIndex = key % 3;
	        for (int j = 0; j < 10; j++) {
	            if (buckets[bucketIndex][j] == 0) {
	                buckets[bucketIndex][j] = key;
	                break;
	            }
	        }
	    }

	    for (int i = 0; i < 3; i++) {
	        out.print("Bucket " + i + ": ");
	        for (int j = 0; j < 10; j++) {
	            if (buckets[i][j] != 0) {
	                out.print(buckets[i][j] + " ");
	            }
	        }
	        out.println();
	    }
	}
	/**
	 * Executes the Linear Quotient algorithm to handle collisions in a hash table.
	 * This function uses a linear increment strategy to find the next available index
	 * when a collision occurs.
	 *
	 * @param hashTableSize The size of the hash table.
	 * @param keys The array of keys to insert into the hash table.
	 */
	public static void linearQuotient() {
	    out.println("Executing Linear Quotient algorithm...");
	    int[] hashTable = new int[10];
	    int[] keys = {23, 45, 12, 37, 29};

	    for (int key : keys) {
	        int index = key % 10;
	        int increment = 1; // Linear increment
	        while (hashTable[index] != 0) {
	            index = (index + increment) % 10;
	        }
	        hashTable[index] = key;
	    }

	    for (int i = 0; i < hashTable.length; i++) {
	        out.printf("Index %d: %d\n", i, hashTable[i]);
	    }
	}
	/**
	 * Executes Brent's Method algorithm to handle collisions in a hash table.
	 * This function is a placeholder for Brent's method, which uses a pattern
	 * of probing to resolve collisions, improving efficiency over linear probing.
	 * The current implementation places keys at their hashed index directly
	 * without the probing steps.
	 *
	 * @param hashTableSize The size of the hash table.
	 * @param keys The array of keys to insert into the hash table.
	 */
	public static void brentsMethod() {
	    out.println("Executing Brent's Method algorithm...");
	    int[] hashTable = new int[10];
	    int[] keys = {23, 45, 12, 37, 29};

	    for (int key : keys) {
	        int index = key % 10;
	        int step = 1; // Initial step
	        /*
	        while (hashTable[index] != 0) {
	            int newIndex = (index + step) % 10;
	            if (hashTable[newIndex] == 0) {
	                index = newIndex;
	                break;
	            }
	            step++;
	        }
	        */
	        hashTable[index] = key;
	    }

	    for (int i = 0; i < hashTable.length; i++) {
	        out.printf("Index %d: %d\n", i, hashTable[i]);
	    }
	}
	// A simple HashMap to store user data (e.g., username and password)
    static Map<String, String> users = new HashMap<>();

	/**
	 * This method handles the authentication process. It displays a menu allowing the user to either register, log in,
	 * enter guest mode, perform file operations, or return to the main menu.
	 * Depending on the user's choice, it performs the corresponding operation.
	 *
	 * @return boolean Returns true if the user successfully logs in, otherwise false.
	 */
    public static boolean authentication() {
        int login = 0;
        while (login!=5) {
            out.println("----------- Authentication Menu -----------");
            out.println("1. Register");
            out.println("2. Login");
            out.println("3. Guest Login");
            out.println("4. File Operations for Fast Search Operations");
            out.println("5. Return to main menu");
            out.print("Please enter your choice: ");

            login = scanner.nextInt();
            scanner.nextLine();

            switch (login) {
                case 1:
                    Register(scanner);
                    break;
                case 2:
                    if (logIn(scanner)) {
                        clearScreen();
                        out.println("Login successful!");
                        return true;
                    } else {
                        clearScreen();
                        out.println("Invalid login. Returning to main menu.");
                    }
                    break;
                case 3:
                    guest();
                    break;
                case 4:
                    fileOperationsMenu(scanner);
                    break;
                case 5:
                    return false;
                default:
                    clearScreen();
                    out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        return false;
    }
	/**
	 * This method handles the login process by checking the username and password
	 * against the stored user credentials in the system.
	 * If the credentials match, it returns true, otherwise false.
	 *
	 * @param scanner The Scanner object to capture user input.
	 * @return boolean Returns true if the login is successful, otherwise false.
	 */
    static boolean logIn(Scanner scanner) {
        out.print("Enter your name: ");
        String username = scanner.nextLine();
        out.print("Enter your password: ");
        String password = scanner.nextLine();

        return users.containsKey(username) && users.get(username).equals(password);
    }
	/**
	 * This method represents the guest login functionality. It simply prints a message indicating that
	 * the user is in guest mode, and no authentication is required.
	 */
    static void guest() {
        out.println("You are in guest mode.");
    }

	/**
	 * This method handles file operations based on the user's choice. It executes various hashing algorithms
	 * such as Progressive Overflow, Linear Probing, Quadratic Probing, Double Hashing, and others,
	 * depending on the chosen option.
	 *
	 * @param choice The user's choice that determines which file operation will be executed.
	 */

    public static void handleFileOperation(int choice) {
	    switch (choice) {
	        case 1:
	            progressiveOverflow();
	            break;
	        case 2:
	            linearProbing();
	            break;
	        case 3:
	            quadraticProbing();
	            break;
	        case 4:
	            doubleHashing();
	            break;
	        case 5:
	            useBuckets();
	            break;
	        case 6:
	            linearQuotient();
	            break;
	        case 7:
	            brentsMethod();
	            break;
	        case 8:
	            out.println("Returning to Authentication Menu.");
	            break;
	        default:
	            out.println("Invalid choice. Please try again.");
	    }
	}
    /**
     * Scanner object for handling user input.
     * This object is used throughout the class to capture user input from the console.
     * It is a static member, meaning it is shared by all instances of the class and can be accessed without creating an object.
     * It helps in reading different types of input such as integers, strings, and other primitive types.
     */

    /**
     * Displays the file operations menu to the user.
     * This method prompts the user to choose a file operation from a list of options. It handles user input
     * and calls the corresponding method to perform the selected operation.
     * The available options include different hashing methods and a return option to the authentication menu.
     * Once the user selects an option, the method proceeds to invoke the handler for that operation.
     */
    public static void fileOperationsMenu(Scanner scanner) {
        out.println("----------- File Operations Menu -----------");
        out.println("1. Progressive Overflow");
        out.println("2. Linear Probing");
        out.println("3. Quadratic Probing");
        out.println("4. Double Hashing");
        out.println("5. Use Buckets");
        out.println("6. Linear Quotient");
        out.println("7. Brent's Method");
        out.println("8. Return to Authentication Menu");
        out.print("Please enter your choice: ");

        int fileOperationChoice = scanner.nextInt();
        handleFileOperation(fileOperationChoice);
    }
    /**
     * Static variables representing the head and tail of a doubly linked list of events.
     * These variables hold references to the first and last nodes of the event list, respectively.
     * The head is the starting point for traversal, while the tail serves as the end point.
     * The list allows for efficient insertion, deletion, and navigation through events.
     */
    // Static head and tail for the doubly linked list
    static EventNode head = null;
    static EventNode tail = null;
    /**
     * Represents an individual event in the system.
     * Each event node contains information about the event's type, date, color, and concept.
     * The EventNode class also includes links to the previous and next event nodes, allowing bidirectional traversal.
     * The constructor initializes the event node's attributes with provided values and sets the initial previous and next links to null.
     *
     * @param type The type of the event (e.g., "Conference", "Meeting").
     * @param date The date and time of the event.
     * @param color The color associated with the event for visual representation.
     * @param concept The conceptual theme or focus of the event (e.g., "Technology", "Education").
     */
    // Static inner class for EventNode (renamed to avoid duplication)
    public static class EventNode {
        String type;
        String date;
        String color;
        String concept;
        EventNode prev;
        EventNode next;

        /**
         * Constructor for the EventNode class.
         * This constructor sets the type, date, color, and concept of the event node.
         * It also initializes the previous and next pointers to null, indicating that there are no neighboring nodes at the time of creation.
         *
         * @param type The type of the event.
         * @param date The date and time of the event.
         * @param color The color associated with the event.
         * @param concept The concept or theme of the event.
         */
        public EventNode(String type, String date, String color, String concept) {
            this.type = type;
            this.date = date;
            this.color = color;
            this.concept = concept;
            this.prev = null; // Initially, no previous event
            this.next = null; // Initially, no next event
        }
    }

    /**
     * Manages event information and provides options for the user to navigate, update, or return to the main menu.
     * The method allows the user to view details of the current event, move to the next or previous event in the list,
     * update the event information, or return to the main menu.
     * If no events are available (i.e., the head is null), the method will display a message informing the user.
     *
     * @return false if the user chooses to return to the main menu, otherwise true.
     * It helps ensure proper navigation through events.
     */
 // Function to manage events
    public static boolean manageEvent() {

        EventNode current = head;  // head is of type EventNode
        int choice;

        while (true) {
            System.out.println("\n--- Event Information ---");
            System.out.println("Type: " + current.type);
            System.out.println("Date: " + current.date);
            System.out.println("Color: " + current.color);
            System.out.println("Concept: " + current.concept);

            System.out.println("\n1. Go to the next event");
            System.out.println("2. Go to the previous event");
            System.out.println("3. Update event information");
            System.out.println("4. Return to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    if (current.next != null) {
                        current = current.next;
                    } else {
                        System.out.println("No next event.");
                    }
                    clearScreen();
                    break;
                case 2:
                    if (current.prev != null) {
                        current = current.prev;
                    } else {
                        System.out.println("No previous event.");
                    }
                    clearScreen();
                    break;
                case 3:
                    System.out.print("Enter new type: ");
                    current.type = scanner.nextLine();

                    System.out.print("Enter new date time: ");
                    current.date = scanner.nextLine();

                    System.out.print("Enter new color: ");
                    current.color = scanner.nextLine();

                    System.out.print("Enter new concept: ");
                    current.concept = scanner.nextLine();

                    System.out.println("Event updated successfully!");
                    clearScreen();
                    return false; // Ensure mainMenu() returns correctly
                case 4:
                    return false; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    /**
     * Creates a new event by collecting event details from the user.
     * This method prompts the user for event type, date, color, and concept, and stores these details in a new EventNode.
     * It also links the new event to the doubly linked list by updating the tail node and linking it to the previous event.
     * The event data is saved to a binary file for persistence.
     *
     * @return true if the event was created and saved successfully, false if an error occurred while saving.
     */
    public static boolean createEvent() {
        try {
            out.print("Enter event type: ");
            String eventType = scanner.nextLine();

            out.print("Enter event date (e.g., 01-01-2025): ");
            String eventDate = scanner.nextLine();

            out.print("Enter color option: ");
            String color = scanner.nextLine();

            out.print("Enter concept: ");
            String concept = scanner.nextLine();

            EventNode newEvent = new EventNode(eventType, eventDate, color, concept);

            if (head == null) {
                head = newEvent;
                tail = newEvent;
            } else {
                tail.next = newEvent;
                newEvent.prev = tail;
                tail = newEvent;
            }

            out.println("Event created and saved successfully!");
            return true;
        } catch (Exception e) {
            System.err.println("Error creating event: " + e.getMessage());
            return false;
        }
    }

    /**
     * Displays the event details menu and allows the user to manage events.
     * The user can choose to create a new event, manage an existing event, or return to the main menu.
     * This method presents options and handles user input, calling the corresponding method based on the user's choice.
     *
     * @return true if the user wishes to continue in the event menu, false if the user chooses to return to the main menu.
     */
	// Event Details main menu
    public static boolean eventDetails() {
            out.println("\n----------- Event Menu -----------");
            out.println("1. Create Event");
            out.println("2. Manage Event");
            out.println("3. Return to main menu");
            out.print("Please enter your choice: ");

            int event = scanner.nextInt();
            scanner.nextLine(); 

            switch (event) {
                case 1:
                    createEvent();
                    break;
                case 2:
                    manageEvent();
                    break;
                case 3:
                    return false; 
                default:
                    out.println("Invalid choice. Please try again.");
                    break;
            }
        return true; 
    }
    /**
     * The main entry point of the program. Starts the event menu and allows the user to interact with it.
     * This method runs a loop that presents the event menu repeatedly until the user chooses to return to the main menu.
     * It serves as the main control structure for event-related operations.
     *
     * @param args Command line arguments (not used in this case).
     */

    /**
     * The maximum number of attendees allowed for an event.
     * This constant defines the upper limit for the number of attendees that can be added to an event.
     */
    public static final int MAX_ATTENDEES = 100;

    /**
     * The maximum length allowed for an attendee's name.
     * This constant is used to ensure that attendee names do not exceed the specified character limit.
     */
    public static final int MAX_NAME_LENGTH = 50;

    /**
     * Array to hold the attendees for the event.
     * The array has a fixed size defined by the MAX_ATTENDEES constant, which limits the total number of attendees.
     */

    static Attendee[] attendees = new Attendee[MAX_ATTENDEES];

    /**
     * Counter to keep track of the number of attendees.
     * This variable increments each time a new attendee is added to the attendees array.
     */
    static int attendeeCount = 0;

    /**
     * The Attendee class represents an attendee of the event with personal details and Huffman code for their name.
     * This class implements the Serializable interface to allow instances of Attendee to be written to a file.
     *
     * @serialVersionUID A unique identifier for serialization and deserialization of Attendee objects.
     */


    public static class Attendee implements Serializable {
        private static final long serialVersionUID = 1L; // Version ID for serialization

        /** The first name of the attendee. */
        String nameAttendee;

        /** The last name of the attendee. */
        String surnameAttendee;

        /** The Huffman code corresponding to the attendee's name. */
        String huffmanCode; // Assuming Huffman code is stored as a string
    }


    /**
     * Knuth-Morris-Pratt (KMP) search function for finding a pattern in the Huffman code of each attendee.
     * The function iterates over the attendees and compares their Huffman code with the given pattern.
     * If a match is found, the attendee's name is printed.
     * The search is case-insensitive.
     *
     * @param pattern The pattern to search for in the attendees' Huffman codes.
     */
    public static void kmpSearch(String pattern) {
        // Convert pattern to lowercase to make comparison case-insensitive
        pattern = pattern.toLowerCase();

        int M = pattern.length();
        boolean found = false;

        for (int i = 0; i < attendeeCount; i++) {
            // Convert attendee's Huffman code to lowercase before comparison
            String huffmanCode = attendees[i].huffmanCode;
            int N = huffmanCode.length();

            int[] lps = new int[M];  // Array for LPS

            computeLPSArray(pattern, M, lps);

            int j = 0;  // index for pattern
            for (int k = 0; k < N; k++) {
                while (j > 0 && pattern.charAt(j) != huffmanCode.charAt(k)) {
                    j = lps[j - 1];
                }

                if (pattern.charAt(j) == huffmanCode.charAt(k)) {
                    j++;
                }

                if (j == M) {
                    out.printf("Pattern found in Huffman code of attendee: %s %s\n", attendees[i].nameAttendee, attendees[i].surnameAttendee);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            out.println("No match found.");
        }
    }
    /**
     * Computes the Longest Prefix Suffix (LPS) array for the KMP search algorithm.
     * This array helps in skipping unnecessary comparisons during the search.
     *
     * @param pattern The pattern to search for.
     * @param M The length of the pattern.
     * @param lps The array to store the LPS values.
     */
    public static void computeLPSArray(String pattern, int M, int[] lps) {
        int length = 0; // length of the previous longest prefix suffix
        lps[0] = 0; // LPS[0] is always 0
        int i = 1;

        // Preprocessing the pattern
        while (i < M) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    /**
     * Compresses an attendee's name using a simplified version of Huffman coding.
     * This method assigns the Huffman code (which is simply the name in this case) to the attendee.
     *
     * @param attendee The Attendee object whose name is to be compressed and stored.
     */
    public static void compressAttendeeName(Attendee attendee) {
        int len = attendee.nameAttendee.length();
        // Directly store the name for simplicity (as a placeholder for actual Huffman coding)
        StringBuilder huffmanCode = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            huffmanCode.append(attendee.nameAttendee.charAt(i)); // Directly storing name characters
        }
        attendee.huffmanCode = huffmanCode.toString(); // Storing Huffman code
    }


    /**
     * Registers a set of attendees by gathering their names and storing them in a binary file.
     * Each attendee's information is stored along with their Huffman code.
     *
     * @return true if the attendees are successfully registered, false if an invalid number of attendees is provided.
     * @throws IOException If an error occurs while writing to the file.
     */
    public static boolean registerAttendees() throws IOException {
        
        // Ask for the number of attendees
        out.print("How many people will attend? ");
        int count = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer after reading the integer

        // Validate the number
        if (count <= 0 || count > MAX_ATTENDEES) {
            out.printf("Invalid number! Please enter a value between 1 and %d.\n", MAX_ATTENDEES);
            return false;
        }

        // Ensure the attendees array is properly initialized
        if (attendees == null) {
            attendees = new Attendee[MAX_ATTENDEES]; // Create the array if not already initialized
        }

        // Prepare to write to the binary file
        boolean append = new File("attendee.bin").exists();
        FileOutputStream file = new FileOutputStream("attendee.bin", true);
        ObjectOutputStream objectOutputStream = append
            ? new AppendObjectOutputStream(file)
            : new ObjectOutputStream(file);

        // Loop to gather each attendee's information and write it to the file
        for (int i = 0; i < count; i++) {
            out.printf("Enter the first name of attendee %d: ", i + 1);
            attendees[attendeeCount] = new Attendee(); // Create a new Attendee object
            attendees[attendeeCount].nameAttendee = scanner.nextLine();
            out.printf("Enter the last name of attendee %d: ", i + 1);
            attendees[attendeeCount].surnameAttendee = scanner.nextLine();

            compressAttendeeName(attendees[attendeeCount]); // Compress the attendee's name and add Huffman code
            objectOutputStream.writeObject(attendees[attendeeCount]); // Write the object to the file
            attendeeCount++; // Increment the attendee count
        }

        // Close the streams
        objectOutputStream.close();
        file.close();

        out.printf("%d attendees have been registered and stored in binary format.\n", count);
        return true;
    }
    /**
     * Custom ObjectOutputStream class for appending objects to an existing stream.
     * It overrides the writeStreamHeader() method to prevent writing a new header to the file.
     */
    static class AppendObjectOutputStream extends ObjectOutputStream {
        /**
         * Constructor that initializes the OutputStream.
         *
         * @param out The OutputStream to which objects will be written.
         * @throws IOException If an I/O error occurs while creating the ObjectOutputStream.
         */
        public AppendObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }
        /**
         * Overrides the writeStreamHeader method to prevent writing a new header when appending objects.
         * This ensures that the header is not written repeatedly when appending to the file.
         */
        @Override
        protected void writeStreamHeader() throws IOException {
            // Prevent writing a new header
        }
    }



    /**
     * Prints all registered attendees along with their Huffman codes.
     * Iterates through all attendees and displays their details.
     */
    public static void printAttendees() {
        out.println("\nRegistered Attendees:");
        for (int i = 0; i < attendeeCount; i++) {
            out.printf("Name: %s, Surname: %s, Huffman Code: %s\n",
                attendees[i].nameAttendee, attendees[i].surnameAttendee, attendees[i].huffmanCode);
        }
    }

    /**
     * Main menu function for managing attendees. Offers options to register, track, print, or manage attendees.
     *
     * @return false to return to the main menu if an operation is completed or invalid input is provided.
     */

    /**
     * The attendee method manages attendee-related operations in the application.
     * It provides a menu-driven interface for registering, tracking, printing,
     * and managing attendees through a series of submenus.
     *
     * <p>This method integrates with various functionalities, such as file handling,
     * Huffman search, XOR list management, and other attendee-related actions.</p>
     *
     * <p>Menu Options:</p>
     * <ul>
     *   <li>Register Attendees: Allows users to add new attendees and handles potential file I/O errors.</li>
     *   <li>Track Attendees: Enables searching for an attendee's details using the KMP search algorithm.</li>
     *   <li>Print Attendees: Displays the list of registered attendees.</li>
     *   <li>Manage Attendees List: Offers sub-options to add, remove, or display activity history of attendees.</li>
     *   <li>Return to Main Menu: Exits the attendee menu and returns control to the main application menu.</li>
     * </ul>
     *
     * @return {@code false} to indicate the operation is complete and to allow for navigation back to the main menu.
     */
	public static boolean attendee() {
		 		int choice;

		out.println("----------- Attendee Menu -----------");
		out.println("1. Register Attendees");
		out.println("2. Track Attendees");
		out.println("3. Print Attendees");
		out.println("4. Manage Attendees List");
		out.println("5. Return to main menu");
		out.print("Please enter your choice: ");
		choice = scanner.nextInt();

		switch (choice) {
			case 1:
			try {
				registerAttendees();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false; 
			}// Return false after registration
			case 2: {
				out.print("Enter the name to search: ");
				String searchName = scanner.next();
				kmpSearch(searchName);  // Search in Huffman code
				return false;  // Return after search
			}
			case 3:
				printAttendees();
				return false;  // Return after printing attendees
			case 4: {
				int subChoice;
				out.println("--------- Manage Attendees List ---------");
				out.println("1. Add Attendee");
				out.println("2. Remove Attendee");
				out.println("3. Display Activity History");
				out.println("4. Back to Main Menu");
				out.print("Please enter your choice: ");
				subChoice = scanner.nextInt();

				switch (subChoice) {
					case 1: {
						out.print("Enter the name of the attendee to add: ");
						String nameToAdd = scanner.next();
						addToXORList(nameToAdd); // Add the attendee to XOR list
						out.println("Attendee added: " + nameToAdd);
						return false;  // Return the result of attendee() call
					}
					case 2: {
						out.print("Enter the name of the attendee to remove: ");
						String nameToRemove = scanner.next();
						removeFromXORList(nameToRemove); // Remove the attendee from XOR list
						out.println("Attendee removed: " + nameToRemove);
						return attendee();  // Return the result of attendee() call
					}
					case 3:
						displayXORList(out);  // Display the XOR list
						// Return the result of attendee() call
					case 4:
						return false;  // Return false to go back to the main menu
					default:
						out.println("Invalid choice. Please try again.");
						return attendee();  // Return the result of attendee() call
				}
			}
			case 5:
				return false;  // Return false to go back to the main menu
			default:
				out.println("Invalid choice. Please try again.");
				return false;  // Return false if an invalid choice is entered
		}
	}

	/**
	 * Constants and data structures used in the event management application.
	 * This section includes the definition of a sparse matrix, stack, queue,
	 * and XOR linked list structures along with their corresponding global instances.
	 * These structures are utilized for efficient management of activities.
	 */

    /**
     * Maximum size for the arrays used in the sparse matrix, stack, and queue.
     */
    public static final int MAX_SIZE = 100;

    /**
     * Maximum size for the stack used to store activities.
     */
    public static final int STACK_SIZE = 100;

    /**
     * Maximum size for the queue used to store activities.
     */
    public static final int QUEUE_SIZE = 100;

    /**
     * Represents a sparse matrix structure to store activities with their details.
     * Only non-zero entries are maintained to save space.
     */
    public static class Matrix {
        /**
         * Array to store the row indices of non-zero entries.
         */
        int[] row = new int[MAX_SIZE];

        /**
         * Array to store the column indices of non-zero entries.
         */
        int[] col = new int[MAX_SIZE];

        /**
         * Array to store the values of non-zero entries, representing activity details.
         */
        String[] value = new String[MAX_SIZE];

        /**
         * The total number of non-zero entries in the sparse matrix.
         */
        int size;  // Number of non-zero entries
    }

    /**
     * Global instance of the sparse matrix for managing activities.
     */
    public static Matrix activityMatrix = new Matrix();

    /**
     * Represents a stack structure for storing activities.
     */
    public static class Stack {
        /**
         * Array to hold the stack items, representing activities.
         */
        String[] items = new String[STACK_SIZE];

        /**
         * The top index of the stack. Initially set to -1 to indicate an empty stack.
         */
        int top = -1;
    }

    /**
     * Global instance of the stack for managing activities.
     */
    public static Stack activityStack = new Stack();

    /**
     * Represents a queue structure for storing activities.
     */
    public static class Queue {
        /**
         * Array to hold the queue items, representing activities.
         */
        String[] items = new String[QUEUE_SIZE];

        /**
         * The front index of the queue, initially set to 0.
         */
        int front = 0;

        /**
         * The rear index of the queue, initially set to 0.
         */
        int rear = 0;
    }

    /**
     * Global instance of the queue for managing activities.
     */
    public static Queue activityQueue = new Queue();

    /**
     * Represents a node in an XOR linked list, used for efficient memory utilization.
     */
    public static class XORNode {
        public XORNode() {
			// TODO Auto-generated constructor stub
		}

		public XORNode(String string) {
			// TODO Auto-generated constructor stub
		}

		/**
         * The value stored in the node, representing activity details.
         */
        String value;

        /**
         * The XOR of the previous and next node addresses.
         */
        XORNode both; // XOR of previous and next node
    }

    /**
     * Head node of the XOR linked list. Initially set to {@code null}.
     */
    public static XORNode xorHead = null;

    /**
     * Computes the XOR of two XORNode references.
     *
     * @param a The first XORNode reference.
     * @param b The second XORNode reference.
     * @return The XOR of the two node references.
     */
    public static XORNode XOR(XORNode a, XORNode b) {
        return new XORNode();
    }

    /**
     * Adds a new node to the XOR linked list.
     * This method inserts a new node with the given value at the head of the XOR linked list.
     *
     * <p>Workflow:</p>
     * <ul>
     *   <li>A new node is created with the given value.</li>
     *   <li>The new node's `both` pointer is set to the XOR of the current head and {@code null}.</li>
     *   <li>If the list is not empty, the current head's `both` pointer is updated to include the new node.</li>
     *   <li>The head of the list is updated to the new node.</li>
     * </ul>
     *
     * @param value The value to be stored in the new node.
     */
    public static void addToXORList(String value) {
        XORNode newNode = new XORNode();
        newNode.value = value;
        newNode.both = XOR(xorHead, null); // Set both to XOR of head and null

        if (xorHead != null) {
            // Update head's both pointer
            XORNode next = XOR(xorHead.both, null);
            xorHead.both = XOR(newNode, next);
        }
        xorHead = newNode; // Move head to the new node
    }

    /**
     * Removes a node with the specified value from the XOR linked list.
     * This method traverses the list to find the node with the given value and removes it while maintaining the list's structure.
     *
     * <p>Workflow:</p>
     * <ul>
     *   <li>Traverse the XOR linked list while keeping track of the previous and next nodes.</li>
     *   <li>If the node with the specified value is found, its neighbors are updated to bypass the node.</li>
     *   <li>If the head node is being removed, the head is adjusted to the next node.</li>
     * </ul>
     *
     * @param value The value of the node to be removed from the list.
     */
    public static void removeFromXORList(String value) {
        XORNode current = xorHead;
        XORNode prev = null;
        XORNode next;

        while (current != null) {
            if (current.value.equals(value)) {
                if (prev != null) {
                    prev.both = XOR(prev.both, current); // Update the previous node's both pointer
                } else {
                    xorHead = XOR(xorHead.both, null); // If it's the head node, adjust head
                }

                next = XOR(prev, current.both); // Get the next node
                current = next; // Move to the next node
            } else {
                next = XOR(prev, current.both); // Get the next node using XOR
                prev = current;
                current = next;
            }
        }
    }

    /**
     * Displays the contents of the XOR linked list.
     * This method traverses the list from the head and prints the value of each node.
     *
     * <p>Workflow:</p>
     * <ul>
     *   <li>Starts from the head of the list and traverses to the end using the XOR of previous and next pointers.</li>
     *   <li>Prints each node's value in sequence.</li>
     *   <li>Displays "NULL" to indicate the end of the list.</li>
     * </ul>
     */
    public static void displayXORList(PrintStream output) { 
        XORNode current = xorHead;
        XORNode prev = null;
        XORNode next;
        int nodeCount = 0; 

        output.println("Activity History: ");
        while (current != null) {
            if (nodeCount > 100) {
                output.println("Error: Possible infinite loop detected.");
                break;
            }

            output.print(current.value + " -> ");
            next = XOR(prev, current.both); // Get the next node using XOR
            prev = current;
            current = next;
            nodeCount++; 
        }
        output.println("NULL");
    }

    /**
     * Initializes the XOR linked list by setting the head to {@code null}.
     * This method is used to reset the XOR linked list to an empty state.
     */
    public static void initializeXORList() {
        xorHead = null;  // Initialize head as null
    }

    /**
     * Initializes the sparse matrix by setting the size to zero.
     * This method clears the sparse matrix to start fresh for storing new activities.
     */
    public static void initializeSparseMatrix() {
        activityMatrix.size = 0;  // Initialize size to zero
    }

    /**
     * Initializes the stack by setting the top pointer to -1.
     * This method is used to reset the stack to an empty state.
     */
    public static void initializeStack() {
        activityStack.top = -1;  // Stack is empty
    }

    /**
     * Initializes the queue by setting the front and rear pointers to 0.
     * This method is used to reset the queue to an empty state.
     */
    public static void initializeQueue() {
        activityQueue.front = 0;
        activityQueue.rear = 0;  // Queue is empty
    }

    /**
     * Checks if the stack is full.
     *
     * @return {@code true} if the stack is full, {@code false} otherwise.
     */
    public static boolean isStackFull() {
        return activityStack.top == STACK_SIZE - 1;
    }

    /**
     * Checks if the stack is empty.
     *
     * @return {@code true} if the stack is empty, {@code false} otherwise.
     */
    public static boolean isStackEmpty() {
        return activityStack.top == -1;
    }

    /**
     * Pushes an activity onto the stack.
     * Adds the specified activity to the top of the stack if it is not full.
     *
     * @param activity The activity to be pushed onto the stack.
     */
    public static void pushStack(String activity) {
        if (!isStackFull()) {
            activityStack.items[++activityStack.top] = activity;
        }
    }

    /**
     * Pops the top activity from the stack.
     * Removes and prints the activity at the top of the stack if it is not empty.
     */
    public static void popStack() {
        if (!isStackEmpty()) {
            out.println("Popped activity: " + activityStack.items[activityStack.top--]);
        } else {
            out.println("Error: Stack is empty!");
        }
    }

    /**
     * Checks if the queue is full.
     *
     * @return {@code true} if the queue is full, {@code false} otherwise.
     */
    public static boolean isQueueFull() {
        return activityQueue.rear == QUEUE_SIZE;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return {@code true} if the queue is empty, {@code false} otherwise.
     */
    public static boolean isQueueEmpty() {
        return activityQueue.front == activityQueue.rear;
    }

    /**
     * Enqueues an activity into the queue.
     * Adds the specified activity to the rear of the queue if it is not full.
     *
     * @param activity The activity to be enqueued into the queue.
     */
    public static void enqueue(String activity) {
        if (!isQueueFull()) {
            activityQueue.items[activityQueue.rear++] = activity;
        }
        // else {
        //    out.println("Error: Queue is full!");
        // }
    }

    /**
     * Dequeues an activity from the queue.
     * Removes and prints the activity at the front of the queue if it is not empty.
     */
    public static void dequeue() {
        if (!isQueueEmpty()) {
            out.println("Dequeued activity: " + activityQueue.items[activityQueue.front++]);
        }
    }

    /**
     * Adds an activity to the sparse matrix.
     * Updates the matrix with the activity's row, column, and value, while also pushing it to the stack,
     * enqueuing it, and adding it to the XOR linked list.
     *
     * @param row The row index where the activity will be stored.
     * @param col The column index where the activity will be stored.
     * @param activity The details of the activity to be added.
     */
    public static void addActivityToMatrix(int row, int col, String activity) {
        // Remove the newline character if it exists
        activity = activity.trim();

        if (activityMatrix.size < MAX_SIZE) {
            activityMatrix.row[activityMatrix.size] = row;
            activityMatrix.col[activityMatrix.size] = col;
            activityMatrix.value[activityMatrix.size] = activity;
            activityMatrix.size++;

            // Push to stack and enqueue
            pushStack(activity);
            enqueue(activity);
            // Add to XOR linked list
            addToXORList(activity);  // Adding activity to XOR linked list
        }
    }

    /**
     * Displays all activities stored in the sparse matrix.
     * Iterates through the matrix and prints the row, column, and details of each activity.
     * @return 
     */
    public static boolean displayActivities() {
        out.println("Activities in Sparse Matrix:");
        for (int i = 0; i < activityMatrix.size; i++) {
            out.printf("Row: %d, Column: %d, Activity: %s\n",
                activityMatrix.row[i], activityMatrix.col[i], activityMatrix.value[i]);
        }
        out.println("Press Enter to continue...");
        try {
            System.in.read();  // Wait for user to press Enter
        } catch (Exception e) {
           
        }
        return true;
    }

    /**
     * Plans timelines for activities.
     * Prompts the user to enter timeline details and prints the planned timeline.
     */
    public static void planTimelines() {
                 out.print("Enter the timeline details (e.g., start and end dates): ");
        String timeline = scanner.nextLine();  // Get input from user
        out.println("Timeline planned: " + timeline);  // Show entered timeline
        out.println("Press Enter to continue...");
        try {
            System.in.read();  // Wait for user to press Enter
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Organizes activities by adding them to the sparse matrix.
     * Prompts the user to enter the row, column, and activity details, then stores the activity.
     */
    public static void organizeActivities() {
                 int row, col;

        out.print("Enter the row index for the activity: ");
        row = scanner.nextInt();
        out.print("Enter the column index for the activity: ");
        col = scanner.nextInt();
        scanner.nextLine();  // Clear the buffer

        out.print("Enter the activity details: ");
        String activity = scanner.nextLine();  // Get input from user

        // Add the activity to the sparse matrix
        addActivityToMatrix(row, col, activity);
        out.println("Activity organized: " + activity);  // Show entered activity
        out.println("Press Enter to continue...");
        try {
            System.in.read();  // Wait for user to press Enter
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the schedule submenu and prompts the user for an option.
     * Based on the user's choice, the relevant function is called to handle the selected task.
     *
     * @return false if the user chooses to return to the main menu, otherwise true.
     */
    public static boolean schedule() {
                 int choice;
        while (true) {
            out.println("----------- Schedule Menu -----------");
            out.println("1. Plan Timelines");
            out.println("2. Organize Activities");
            out.println("3. Display Activities");  // New option to display activities
            out.println("4. Display Activity History");  // Updated option to display XOR activities
            out.println("5. Pop Activity from Stack");  // New option to pop activity from stack
            out.println("6. Dequeue Activity");  // New option to dequeue activity
            out.println("7. Return to Main Menu");
            out.print("Please enter your choice: ");

            // Prompt the user to make a choice
            choice = scanner.nextInt();
            scanner.nextLine();  // Clear the buffer

            switch (choice) {
                case 1:
                    planTimelines();  // Plan timelines
                    break;
                case 2:
                    organizeActivities();  // Organize activities
                    break;
                case 3:
                    displayActivities();  // Display activities
                    break;
                case 4:
                    displayXORList(out);  // Display activity history from XOR linked list
                    break;
                case 5:
                    popStack();  // Pop activity from stack
                    break;
                case 6:
                    dequeue();  // Dequeue activity
                    break;
                case 7:
                    return false; // Return to Main Menu
                default:
                    out.println("Invalid choice. Please try again.");
                    return false;
            }
        }
    }
    /**
     * Heapifies a subtree with the root at the given index.
     * Ensures that the binary heap property is maintained.
     *
     * @param arr The array representing the heap.
     * @param n The total number of elements in the heap.
     * @param i The index of the root node of the subtree.
     */
    public static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            heapify(arr, n, largest);
        }
    }
    /**
     * Sorts an array using heap sort.
     * First builds a max heap and then repeatedly extracts the maximum element to the end of the array.
     *
     * @param arr The array to be sorted.
     * @param n The total number of elements in the array.
     */
    public static void heapSort(int[] arr, int n) {
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // One by one extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    /**
     * Represents a leaf node in a B+ tree.
     * Each leaf node contains a fixed number of keys and a reference to the next leaf node.
     */
    public static class BPlusLeafNode {
    	public static final int MAX_KEYS = 3;
        int[] keys = new int[MAX_KEYS];
        BPlusLeafNode next;
        int numKeys;

        BPlusLeafNode() {
            this.numKeys = 0;
            this.next = null;
        }
    }

    /**
     * Represents an internal node in a B+ tree.
     * Internal nodes contain keys and pointers to child nodes, which can either be other internal nodes or leaf nodes.
     */
    public static class BPlusInternalNode {
    	public static final int MAX_KEYS = 3;
        int[] keys = new int[MAX_KEYS];
        Object[] children = new Object[MAX_KEYS + 1]; // Can be internal nodes or leaf nodes
        int numKeys;
        /**
         * Constructor to initialize a new B+ internal node.
         */
        BPlusInternalNode() {
            this.numKeys = 0;
        }
    	/**
		 * Retrieves the first child of this internal node.
		 *
		 * @return The first child node of this internal node.
		 */
		public Object getFirstChild() {
			// TODO Auto-generated method stub
			return null;
		}
    }
    /**
     * Represents a B+ tree structure.
     * A B+ tree is a balanced tree data structure with a root, internal nodes, and leaf nodes.
     */
    public static class BPlusTree {
        Object root;  // Can be root, internal node, or leaf
        /**
         * Constructor to initialize an empty B+ tree.
         */
        BPlusTree() {
            this.root = null;
        }
        /**
		 * Inserts a key into the B+ tree.
		 *
		 * @param i The key to be inserted into the B+ tree.
		 */
		public void insert(int i) {
			// TODO Auto-generated method stub

		}
		/**
		 * Prints all the leaf nodes of the B+ tree.
		 *
		 * @param tree The B+ tree to print the leaf nodes from.
		 */
		public static void printLeafNodes(BPlusTree tree) {
			// TODO Auto-generated method stub

		}
    }

    /**
     * Creates and returns a new B+ tree.
     * Initially, the root of the tree is set to a new leaf node.
     *
     * @return A newly created B+ tree.
     */
    public static BPlusTree createBPlusTree() {
        BPlusTree tree = new BPlusTree();
        BPlusLeafNode rootLeaf = new BPlusLeafNode();
        tree.root = rootLeaf;
        return tree;
    }

    /**
     * Class-level constant defining the maximum number of keys in a leaf node.
     */
    public static final int MAX_KEYS = 3;
    /**
     * Inserts a key into a B+ leaf node.
     * If the leaf node is not full, it inserts the key in the correct position.
     * If the leaf node is full, it splits the node and redistributes the keys.
     *
     * @param leaf The leaf node to insert the key into.
     * @param key The key to be inserted into the leaf node.
     */
  public static  void insertIntoLeaf(BPlusLeafNode leaf, int key) {
        if (leaf.numKeys < MAX_KEYS) {
            int i = leaf.numKeys - 1;
            while (i >= 0 && leaf.keys[i] > key) {
                leaf.keys[i + 1] = leaf.keys[i];
                i--;
            }
            leaf.keys[i + 1] = key;
            leaf.numKeys++;
        } else {
            // Handle splitting the leaf node
            BPlusLeafNode newLeaf = new BPlusLeafNode();
            newLeaf.numKeys = 0;
            newLeaf.next = leaf.next;
            leaf.next = newLeaf;

            int mid = MAX_KEYS / 2;
            newLeaf.numKeys = MAX_KEYS - mid;
            System.arraycopy(leaf.keys, mid, newLeaf.keys, 0, newLeaf.numKeys);
            leaf.numKeys = mid;

            // Insert into the correct leaf node
            if (key > newLeaf.keys[0]) {
                insertIntoLeaf(newLeaf, key);
            } else {
                insertIntoLeaf(leaf, key);
            }
        }
    }
  /**
   * Inserts a key into the B+ tree.
   * If the root is not full, it inserts the key into the root.
   * If the root is full, it splits the root and creates a new internal node.
   *
   * @param tree The B+ tree to insert the key into.
   * @param key The key to be inserted into the B+ tree.
   */
   public static void insert(BPlusTree tree, int key) {
        BPlusLeafNode root = (BPlusLeafNode) tree.root;
        if (root.numKeys < MAX_KEYS) {
            insertIntoLeaf(root, key);
        } else {
            // Create a new root and split the leaf
            BPlusInternalNode newRoot = new BPlusInternalNode();
            newRoot.numKeys = 1;
            newRoot.keys[0] = root.keys[MAX_KEYS / 2];
            newRoot.children[0] = root;
            BPlusLeafNode newLeaf = new BPlusLeafNode();
            newLeaf.numKeys = 0;
            newLeaf.next = null;
            newRoot.children[1] = newLeaf;

            tree.root = newRoot;
            insertIntoLeaf(newLeaf, key);
        }
    }

   // Static variables required by SCC (Strongly Connected Components) algorithms
   static int[] lowLink;           // Low-link values for Tarjan's algorithm
   static int[] discoveryTime;     // Discovery times for Tarjan's algorithm
   static boolean[] inStack;       // To track if a node is currently in the stack
   static int[] stack;             // Stack for Tarjan's algorithm
   static int stackTop;            // Top index of the stack
   static int timeCounter;         // Counter to track the discovery times

   /**
    * Pushes a node onto the SCC stack and marks it as in the stack.
    *
    * @param node The node to be pushed onto the stack.
    */
    public static void pushStackSCC(int node) {
        stack[++stackTop] = node;
        inStack[node] = true;
    }

    /**
     * Pops a node from the SCC stack and marks it as not in the stack.
     *
     * @return The node that was popped from the stack.
     */
    public static int popStackSCC() {
        int node = stack[stackTop--];
        inStack[node] = false;
        return node;
    }

    /**
     * Tarjan's algorithm for finding strongly connected components (SCCs) in a graph.
     * The algorithm uses depth-first search (DFS) and a stack to identify SCCs.
     *
     * @param node The node to start the SCC search from.
     * @param adjMatrix The adjacency matrix representing the graph.
     * @param n The number of nodes in the graph.
     */
    public static void SCC(int node, int[][] adjMatrix, int n) {
        lowLink[node] = discoveryTime[node] = ++timeCounter; // Set discovery time and low-link value
        pushStackSCC(node);

        for (int i = 0; i < n; i++) {
            if (adjMatrix[node][i] != 0) { // If there's a neighbor
                if (discoveryTime[i] == -1) { // If the neighbor hasn't been discovered
                    SCC(i, adjMatrix, n);
                    lowLink[node] = Math.min(lowLink[node], lowLink[i]);
                } else if (inStack[i]) { // If the neighbor is in the stack
                    lowLink[node] = Math.min(lowLink[node], discoveryTime[i]);
                }
            }
        }

        // If node is a root of an SCC
        if (lowLink[node] == discoveryTime[node]) {
            out.print("SCC Found: ");
            int w;
            do {
                w = popStackSCC();
                out.print(w + " ");
            } while (w != node);
            out.println();
        }
    }
    /**
     * Finds the strongly connected components (SCCs) in a directed graph using Tarjan's algorithm.
     * It iterates through all nodes and calls the SCC method for each unvisited node.
     *
     * @param adjMatrix The adjacency matrix representing the graph.
     * @param n The number of nodes in the graph.
     */
    public static void findSCCs(int[][] adjMatrix, int n) {
        int[] lowLink = new int[n];
        int[] discoveryTime = new int[n];
        boolean[] inStack = new boolean[n];
        int[] stack = new int[n];
        int stackTop = -1;
        int timeCounter = 0;

        Arrays.fill(discoveryTime, -1);

        for (int i = 0; i < n; i++) {
            if (discoveryTime[i] == -1) {
                SCC(i, adjMatrix, n, lowLink, discoveryTime, inStack, stack, stackTop, timeCounter);
            }
        }
    }
    /**
     * Helper method for Tarjan's algorithm to find the strongly connected components (SCCs) of the graph.
     * This method uses depth-first search (DFS) and low-link values to identify SCCs.
     *
     * @param node The current node being visited.
     * @param adjMatrix The adjacency matrix representing the graph.
     * @param n The number of nodes in the graph.
     * @param lowLink The array of low-link values.
     * @param discoveryTime The array of discovery times.
     * @param inStack Array indicating if a node is in the stack.
     * @param stack The stack to hold the nodes during the DFS traversal.
     * @param stackTop The index of the top of the stack.
     * @param timeCounter The counter for discovery times.
     */
    public static void SCC(int node, int[][] adjMatrix, int n, int[] lowLink, int[] discoveryTime, boolean[] inStack, int[] stack, int stackTop, int timeCounter) {
        discoveryTime[node] = lowLink[node] = ++timeCounter;
        stack[++stackTop] = node;
        inStack[node] = true;

        for (int i = 0; i < n; i++) {
            if (adjMatrix[node][i] != 0) {
                if (discoveryTime[i] == -1) {
                    SCC(i, adjMatrix, n, lowLink, discoveryTime, inStack, stack, stackTop, timeCounter);
                    lowLink[node] = Math.min(lowLink[node], lowLink[i]);
                } else if (inStack[i]) {
                    lowLink[node] = Math.min(lowLink[node], discoveryTime[i]);
                }
            }
        }

        if (lowLink[node] == discoveryTime[node]) {
            out.print("SCC Found: ");
            int w;
            do {
                w = stack[stackTop--];
                inStack[w] = false;
                out.print(w + " ");
            } while (w != node);
            out.println();
        }
    }
    /**
     * Displays the ratings sorted in ascending order using bubble sort.
     * If no ratings are available, it will notify the user.
     */
  public static void displaySortedRatings() {

        if (feedbackCount == 0) {
            out.println("No ratings available.");
            return;
        }

        int[] sortedRatings = Arrays.copyOf(feedbackRatings, feedbackCount); // Copy the ratings

        // Bubble sort
        for (int i = 0; i < feedbackCount - 1; i++) {
            for (int j = 0; j < feedbackCount - i - 1; j++) {
                if (sortedRatings[j] > sortedRatings[j + 1]) {
                    int temp = sortedRatings[j];
                    sortedRatings[j] = sortedRatings[j + 1];
                    sortedRatings[j + 1] = temp;
                }
            }
        }

        out.println("Sorted Ratings:");
        for (int i = 0; i < feedbackCount; i++) {
            out.print(sortedRatings[i] + " ");
        }
        out.println();
        out.println("Press Enter to return to Feedback Menu...");
        new Scanner(System.in).nextLine(); // Wait for user to press Enter
 }
  /**
   * Performs a breadth-first search (BFS) on a graph represented by an adjacency matrix.
   * It starts the search from the given start node and prints each visited node.
   *
   * @param startNode The node from which the BFS traversal starts.
   * @param adjMatrix The adjacency matrix representing the graph.
   * @param n The number of nodes in the graph.
   */
public static void BFS(int startNode, int[][] adjMatrix, int n) {

   if (adjMatrix == null || adjMatrix.length != n || adjMatrix[0].length != n) {
       out.println("Error: Adjacency matrix dimensions do not match the given size.");
       return;
   }

   boolean[] visited = new boolean[n];


   java.util.Queue<Integer> queue = new java.util.LinkedList<>();
   visited[startNode] = true;
   queue.add(startNode);

   out.printf("BFS Traversal starting from node %d:%n", startNode);

   while (!queue.isEmpty()) {
       int currentNode = queue.poll();
       out.printf("Visited Node: %d%n", currentNode);


       for (int i = 0; i < n; i++) {
           if (adjMatrix[currentNode][i] != 0 && !visited[i]) {
               visited[i] = true;
               queue.add(i);
           }
       }
   }
}
/**
 * Performs a depth-first search (DFS) on a graph represented by an adjacency matrix.
 * It recursively visits each node starting from the given node.
 *
 * @param node The node to start the DFS traversal from.
 * @param visited Array to track visited nodes.
 * @param adjMatrix The adjacency matrix representing the graph.
 * @param n The number of nodes in the graph.
 */

public static void DFS(int node, boolean[] visited, int[][] adjMatrix, int n) {
   visited[node] = true;
   out.printf("Visited Node: %d\n", node);

   for (int i = 0; i < n; i++) {
       if (adjMatrix[node][i] != 0 && !visited[i]) {
           DFS(i, visited, adjMatrix, n);
       }
   }
}
/**
 * Prints the leaf nodes of a B+ Tree along with their keys.
 * Traverses the linked list of leaf nodes and prints the keys of each node.
 *
 * @param tree The B+ Tree object containing the leaf nodes.
 */
   public static void printLeafNodes(BPlusTree tree) {
        BPlusLeafNode current = (BPlusLeafNode) tree.root;
        while (current != null) {
            out.print("Leaf Node: ");
            for (int i = 0; i < current.numKeys; i++) {
                out.print(current.keys[i] + " ");
            }
            out.println();
            current = current.next;
        }
    }
   /**
    * Gathers feedback from the user, stores the rating, and inserts it into a B+ Tree.
    * Prompts the user for feedback text (up to 255 characters) and a rating (1-5).
    *
    * @param tree The B+ Tree object where the feedback will be inserted.
    */
   public static void gatherFeedbacks(BPlusTree tree) {
	     
	    out.print("Enter your feedback (max 255 characters): ");
	    String feedback = scanner.nextLine();


	    out.print("Rate the application (1 to 5): ");
	    int rating = scanner.nextInt();
	    scanner.nextLine();

	    if (rating >= 1 && rating <= 5) {
	        if (feedbackCount < MAX_FEEDBACKS) {
	            feedbackRatings[feedbackCount++] = rating;
	            insert(tree, rating);
	            out.println("Feedback received: " + feedback);
	            out.println("Rating received: " + rating);
	        }
	    }

	    out.println("Current feedbackCount: " + feedbackCount);
	}


   /**
    * Displays the feedback menu and handles user choices related to feedback management.
    * Provides options to gather feedback, view sorted ratings, print B+ Tree, and perform BFS, DFS, and SCC.
    *
    * @return Returns false to exit the feedback menu and return to the main menu.
    */
    static boolean feedback() {
        BPlusTree tree = createBPlusTree();
                 int choice;
        while (true) {
            out.println("\n----------- Feedback Menu -----------");
            out.println("1. Gather Feedback");
            out.println("2. View Sorted Ratings");
            out.println("3. Print B+ Tree");
            out.println("4. Perform BFS");
            out.println("5. Perform DFS");
            out.println("6. Find SCC (Tarjan Algorithm)");
            out.println("7. Return to Main Menu");
            out.print("Please enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();  // Clear the buffer

            switch (choice) {
                case 1:
                    gatherFeedbacks(tree);
                    return true;
                case 2:
                    displaySortedRatings();
                    return true;
                case 3:
                    out.println("Feedbacks stored in B+ Tree:");
                    printLeafNodes(tree);
                    break;
                case 4: { // Perform BFS
                    if (feedbackCount == 0) {
                        out.println("No feedback data available for BFS.");
                        break;
                    }

                    out.printf("Enter starting feedback number for BFS (1 to %d): ", feedbackCount);
                    int startNode = scanner.nextInt();
                    scanner.nextLine();

                    if (startNode < 1 || startNode > feedbackCount) {
                        out.println("Invalid node.");
                        break;
                    }

                    startNode--;

                    int[][] adjMatrix = new int[feedbackCount][feedbackCount];
                    for (int i = 0; i < feedbackCount - 1; i++) {
                        adjMatrix[i][i + 1] = adjMatrix[i + 1][i] = 1;
                    }

                    BFS(startNode, adjMatrix, feedbackCount);
                    break;
                }
                case 5: {
                    out.printf("Enter starting feedback number for DFS (1 to %d): ", feedbackCount);
                    int startNode = scanner.nextInt();
                    scanner.nextLine();  // Clear the buffer

                    if (startNode < 1 || startNode > feedbackCount) {
                        out.println("Invalid node.");
                        break;
                    }
                    startNode--;

                    int[][] adjMatrix = new int[MAX_FEEDBACKS][MAX_FEEDBACKS];
                    adjMatrix[0][1] = adjMatrix[1][0] = 1;
                    adjMatrix[1][2] = adjMatrix[2][1] = 1;


                    boolean[] visited = new boolean[MAX_FEEDBACKS];
                    DFS(startNode, visited, adjMatrix, feedbackCount);
                    break;
                }
                case 6: { // Find SCC (Tarjan Algorithm)
                    int n = feedbackCount;
                    int[][] adjMatrix = new int[n][n];


                    for (int i = 0; i < n - 1; i++) {
                        adjMatrix[i][i + 1] = 1;
                    }

                    out.println("Finding SCCs:");
                    findSCCs(adjMatrix, n);
                    break;
                }
                case 7:
                    return false;
                default:
                    out.println("Invalid choice. Try again.");
            }
        }
    }

    /** 
     * Clears the hash table (currently a placeholder method).
     */
	public static void clearHashTable() {
		// TODO Auto-generated method stub
		
	}
	/** 
     * Asserts that a boolean condition is true (currently a placeholder method).
     * 
     * @param contains The condition to check.
     */
	public static void assertTrue(boolean contains) {
		// TODO Auto-generated method stub
		
	}
	 /** 
     * Asserts that a boolean condition is false (currently a placeholder method).
     * 
     * @param result The condition to check.
     */
	public static void assertFalse(boolean result) {
		// TODO Auto-generated method stub
		
	}
	/** 
     * Starts the main menu of the application (currently a placeholder method).
     */
	public void startMenu() {
		// TODO Auto-generated method stub
		
	}
	 /** 
     * Adds a string to some collection (currently a placeholder method).
     * 
     * @param string The string to add.
     */
	public void add(String string) {
		// TODO Auto-generated method stub
		
	}
	  /** 
     * Prints a list (currently a placeholder method).
     * 
     * @return The printed list as a string.
     */
	public String printList() {
		// TODO Auto-generated method stub
		return null;
	}
	 /** 
     * Sets the file path (currently a placeholder method).
     * 
     * @param string The file path to set.
     */
	public static void setFilePath(String string) {
		// TODO Auto-generated method stub
		
	}


	public void handleFileOperation() {
		// TODO Auto-generated method stub
		
	}



}
