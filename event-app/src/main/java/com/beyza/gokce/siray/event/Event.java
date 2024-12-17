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
import java.util.Scanner;

/**
 * @class Event
 * @brief This class represents an Event that performs various operations, including hash table management, Huffman coding, and algorithms.
 * @details The Event class provides methods for user authentication, event management, attendee handling, and algorithm demonstrations.
 * @author ugur.coruh
 */
public class Event {

    private static final int TABLE_SIZE = 100;
    private static final int MAX_TREE_NODES = 256;

    // Inner class for Attendee
    static class Attendee {
        String nameAttendee;
        String surnameAttendee;
        String huffmanCode;
		public String name;
		public String surname;

        Attendee(String name, String surname) {
            this.nameAttendee = name;
            this.surnameAttendee = surname;
            this.huffmanCode = "";
        }
    }

    // Inner class for Huffman Tree Node
    static class MinHeapNode {
        char data;
        int freq;
        MinHeapNode left, right;

        MinHeapNode(char data, int freq) {
            this.data = data;
            this.freq = freq;
            this.left = this.right = null;
        }
    }

    // Inner class for MinHeap
    static class MinHeap {
        int size;
        int capacity;
        MinHeapNode[] array;

        MinHeap(int capacity) {
            this.size = 0;
            this.capacity = capacity;
            this.array = new MinHeapNode[capacity];
        }

        void insertMinHeap(MinHeapNode minHeapNode) {
            array[size++] = minHeapNode;
            Arrays.sort(array, 0, size, Comparator.comparingInt(a -> a.freq));
        }

        MinHeapNode extractMin() {
            MinHeapNode temp = array[0];
            System.arraycopy(array, 1, array, 0, --size);
            return temp;
        }
    }

    // Inner class for User
    static class User {
        String name;
        String surname;
        String phone;
        String password;
        User next;

        User(String name, String surname, String phone, String password) {
            this.name = name;
            this.surname = surname;
            this.phone = phone;
            this.password = password;
            this.next = null;
        }
    }

    private static final User[] hashTable = new User[TABLE_SIZE];

    // Clear the console (simulated for Java)
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Compute hash function
    public static int hash(String phone) {
        int hash = 0;
        for (int i = 0; i < phone.length(); i++) {
            hash = (hash * 31 + phone.charAt(i)) % TABLE_SIZE;
        }
        return hash;
    }

    // Save a user to the hash table
    public static void saveUser(User newUser) {
        int index = hash(newUser.phone);
        newUser.next = hashTable[index];
        hashTable[index] = newUser;
    }

    // Save hash table to a file
    public static void saveHashTableToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.bin"))) {
            for (User user : hashTable) {
                while (user != null) {
                    oos.writeObject(user);
                    user = user.next;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load hash table from a file
 // Load hash table from a file
    public static void loadHashTableFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.bin"))) {
            while (true) {
                try {
                    User newUser = (User) ois.readObject();
                    // Process the user object (add to a collection, print, etc.)
                    System.out.println("Loaded user: " + newUser);
                } catch (EOFException e) {
                    // End of file reached
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while loading the hash table: " + e.getMessage());
        }
    }

    // Quadratic Probing Algorithm
    public static boolean quadraticProbing() {
        System.out.println("Executing Quadratic Probing algorithm...");
        int[] hashTable = new int[10];
        Arrays.fill(hashTable, 0);
        int[] keys = {23, 45, 12, 37, 29};

        for (int key : keys) {
            int index = key % 10;
            int j = 0;
            while (hashTable[(index + j * j) % 10] != 0) {
                j++;
            }
            hashTable[(index + j * j) % 10] = key;
        }

        for (int i = 0; i < hashTable.length; i++) {
            System.out.printf("Index %d: %d%n", i, hashTable[i]);
        }

        return true;
    }

    public static void doubleHashing() {
        System.out.println("Executing Double Hashing algorithm...");
        int[] hashTable = new int[10];
        Arrays.fill(hashTable, 0);
        int[] keys = {23, 45, 12, 37, 29};

        for (int key : keys) {
            int index = key % 10;
            int step = 7 - (key % 7);
            while (hashTable[index] != 0) {
                index = (index + step) % 10;
            }
            hashTable[index] = key;
        }

        for (int i = 0; i < hashTable.length; i++) {
            System.out.printf("Index %d: %d%n", i, hashTable[i]);
        }
    }

    public static void useBuckets() {
        System.out.println("Executing Use of Buckets algorithm...");
        int[][] buckets = new int[3][10];
        for (int[] bucket : buckets) {
            Arrays.fill(bucket, 0);
        }
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

        for (int i = 0; i < buckets.length; i++) {
            System.out.printf("Bucket %d: ", i);
            for (int j = 0; j < buckets[i].length; j++) {
                if (buckets[i][j] != 0) {
                    System.out.printf("%d ", buckets[i][j]);
                }
            }
            System.out.println();
        }
    }

    public static void linearQuotient() {
        System.out.println("Executing Linear Quotient algorithm...");
        int[] hashTable = new int[10];
        Arrays.fill(hashTable, 0);
        int[] keys = {23, 45, 12, 37, 29};

        for (int key : keys) {
            int index = key % 10;
            int increment = 1;
            while (hashTable[index] != 0) {
                index = (index + increment) % 10;
            }
            hashTable[index] = key;
        }

        for (int i = 0; i < hashTable.length; i++) {
            System.out.printf("Index %d: %d%n", i, hashTable[i]);
        }
    }

    public static void brentsMethod() {
        System.out.println("Executing Brent's Method algorithm...");
        int[] hashTable = new int[10];
        Arrays.fill(hashTable, 0);
        int[] keys = {23, 45, 12, 37, 29};

        for (int key : keys) {
            int index = key % 10;
            int step = 1;
            while (hashTable[index] != 0) {
                int newIndex = (index + step) % 10;
                if (hashTable[newIndex] == 0) {
                    index = newIndex;
                    break;
                }
                step++;
            }
            hashTable[index] = key;
        }

        for (int i = 0; i < hashTable.length; i++) {
            System.out.printf("Index %d: %d%n", i, hashTable[i]);
        }
    }
   
    // Insert user using quadratic probing
    public static boolean quadraticProbingInsert(User newUser) {
        int index = hash(newUser.phone);
        int i = 0;
        int originalIndex = index;

        while (hashTable[index] != null && i < TABLE_SIZE) {
            i++;
            index = (originalIndex + i * i) % TABLE_SIZE;
            if (index == originalIndex) {
                System.out.println("Hash table full. User not added.");
                return false;
            }
        }

        if (i < TABLE_SIZE) {
            hashTable[index] = newUser;
            return true;
        }
        return false;
    }

    // Print the hash table contents
    public static void printHashTable() {
        System.out.println("Hash Table Contents:");
        for (int i = 0; i < TABLE_SIZE; i++) {
            User current = hashTable[i];
            if (current == null) continue;

            System.out.println("Index " + i + ":");
            while (current != null) {
                System.out.printf(" Name: %s %s, Phone: %s, Password: %s%n",
                        current.name, current.surname, current.phone, current.password);
                current = current.next;
            }
        }
        System.out.println("End of Hash Table.");
    }

    // Build a Huffman tree and generate codes
    public static void buildHuffmanTree(String str, Attendee attendee) {
        int[] freq = new int[MAX_TREE_NODES];
        for (char c : str.toCharArray()) {
            freq[c]++;
        }

        MinHeap minHeap = new MinHeap(MAX_TREE_NODES);
        for (int i = 0; i < MAX_TREE_NODES; i++) {
            if (freq[i] > 0) {
                minHeap.insertMinHeap(new MinHeapNode((char) i, freq[i]));
            }
        }

        while (minHeap.size > 1) {
            MinHeapNode left = minHeap.extractMin();
            MinHeapNode right = minHeap.extractMin();

            MinHeapNode top = new MinHeapNode('$', left.freq + right.freq);
            top.left = left;
            top.right = right;

            minHeap.insertMinHeap(top);
        }

        char[] code = new char[256];
        StringBuilder huffmanCode = new StringBuilder();
        generateHuffmanCodes(minHeap.extractMin(), code, 0, huffmanCode);
        attendee.huffmanCode = huffmanCode.toString();
    }

    private static void generateHuffmanCodes(MinHeapNode root, char[] code, int top, StringBuilder huffmanCode) {
        if (root.left != null) {
            code[top] = '0';
            generateHuffmanCodes(root.left, code, top + 1, huffmanCode);
        }

        if (root.right != null) {
            code[top] = '1';
            generateHuffmanCodes(root.right, code, top + 1, huffmanCode);
        }

        if (root.left == null && root.right == null) {
            huffmanCode.append(root.data).append(": ").append(new String(code, 0, top)).append("\n");
        }
    }

    // Validate user login
    public static boolean validateLogin(String phone, String password) {
        if (phone == null || password == null) {
            return false;
        }

        int index = hash(phone);
        User current = hashTable[index];
        while (current != null) {
            if (current.phone.equals(phone) && current.password.equals(password)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

	    public static void progressiveOverflowAlgorithm() {
	        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	        int overflowThreshold = 7; // Set the overflow threshold for demonstration
	        int currentSum = 0;

	        for (int i = 0; i < array.length; i++) {
	            currentSum += array[i];
	            if (currentSum > overflowThreshold) {
	                System.out.printf("Overflow detected at element %d with sum %d%n", i, currentSum);
	                currentSum = array[i]; // Reset but include current element in new sum
	            }
	        }
	    }
		   

	    public static boolean guest() {
	        clearScreen();
	        System.out.println("Guest login successful!");
	        return true;
	    }
	    
	    class User {
		    private String name;
		    private String surname;
		    private String phone;
		    private String password;

		    public String getName() {
		        return name;
		    }

		    public void setName(String name) {
		        this.name = name;
		    }

		    public String getSurname() {
		        return surname;
		    }

		    public void setSurname(String surname) {
		        this.surname = surname;
		    }

		    public String getPhone() {
		        return phone;
		    }

		    public void setPhone(String phone) {
		        this.phone = phone;
		    }

		    public String getPassword() {
		        return password;
		    }

		    public void setPassword(String password) {
		        this.password = password;
		    }

	    public static boolean Register() {
	        Scanner scanner = new Scanner(System.in);
	        User newUser = new User();
	        System.out.print("Enter your name: ");
	        newUser.setName(scanner.nextLine());
	        System.out.print("Enter your surname: ");
	        newUser.setSurname(scanner.nextLine());
	        System.out.print("Enter your phone number: ");
	        newUser.setPhone(scanner.nextLine());
	        System.out.print("Enter your password: ");
	        newUser.setPassword(scanner.nextLine());

	        saveUserData(newUser);
	        clearScreen();
	        System.out.println("Registration successful! You can now log in.");

	        printHashTable(); // Print table after each record
	        return true;
	    }
	    public static boolean logIn() {
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Enter your phone number: ");
	        String phone = scanner.nextLine();
	        System.out.print("Enter your password: ");
	        String password = scanner.nextLine();

	        return validateLogin(phone, password); // Check login validity
	    }

	    public static boolean handleAuthenticationChoice(int login) {
	        Scanner scanner = new Scanner(System.in);
	        do {
	            switch (login) {
	                case 1:
	                    Register();
	                    break;
	                case 2:
	                    if (logIn()) {
	                        clearScreen();
	                        System.out.println("Login successful!");
	                    } else {
	                        clearScreen();
	                        System.out.println("Invalid login. Returning to main menu.");
	                    }
	                    break;
	                case 3:
	                    guest();
	                    break;
	                case 4:
	                    fileOperationsMenu();
	                    break;
	                case 5:
	                    return true;
	                default:
	                    clearScreen();
	                    System.out.println("Invalid choice. Please try again.");
	                    break;
	            }
	            System.out.print("Enter your choice again (5 to exit): ");
	            login = scanner.nextInt();
	        } while (login != 5);
	        return true;
	    }

		public static boolean authentication() {
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("----------- Authentication Menu -----------");
	        System.out.println("1. Register");
	        System.out.println("2. Login");
	        System.out.println("3. Guest Login");
	        System.out.println("4. File Operations for Fast Search Operations");
	        System.out.println("5. Exit");

	        System.out.print("Please enter your choice: ");
	        int login = scanner.nextInt();

	        return handleAuthenticationChoice(login);
	    }
	    public static boolean mainMenu() {
	        Scanner scanner = new Scanner(System.in);
	        int choice;

	        do {
	            System.out.println("\n-----------------------------------");
	            System.out.println("WELCOME TO OUR PLANNER!!");
	            System.out.println("-----------------------------------");
	            System.out.println("1. User Authentication");
	            System.out.println("2. Event Details");
	            System.out.println("3. Attendee Management");
	            System.out.println("4. Schedule Organizer");
	            System.out.println("5. Feedback Collection");
	            System.out.println("6. Exit");
	            System.out.print("Please enter your choice: ");
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
	                    System.exit(0);
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	                    return false;
	            }
	        } while (choice != 6);
	        return true;
	    }

	    public static void progressiveOverflow() {
	        System.out.println("Executing Progressive Overflow algorithm...");
	        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	        int overflowThreshold = 7;
	        int currentSum = 0;

	        for (int i = 0; i < array.length; i++) {
	            currentSum += array[i];
	            if (currentSum > overflowThreshold) {
	                System.out.printf("Overflow detected at index %d with sum %d%n", i, currentSum);
	                currentSum = 0; // Reset for simplicity
	            }
	        }
	    }

	    public static void linearProbing() {
	        System.out.println("Executing Linear Probing algorithm...");
	        int[] hashTable = new int[10];
	        Arrays.fill(hashTable, -1); // -1,
	        int[] keys = {23, 45, 12, 37, 29};

	        for (int key : keys) {
	            int index = key % 10;
	            while (hashTable[index] != -1) { 
	                index = (index + 1) % 10;
	            }
	            hashTable[index] = key;
	        }
	        for (int i = 0; i < hashTable.length; i++) {
	            if (hashTable[i] != -1) {
	                System.out.printf("Index %d: %d%n", i, hashTable[i]);
	            } else {
	                System.out.printf("Index %d: Empty%n", i); 
	            }
	        }
	    }
	    public static void handleFileOperation(int choice) {
	        Scanner scanner = new Scanner(System.in);
	        do {
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
	                    return;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	            System.out.print("Enter your choice again (8 to exit): ");
	            choice = scanner.nextInt();
	        } while (choice != 8);
	    }

	    public static void fileOperationsMenu() {
	        Scanner scanner = new Scanner(System.in);
	        int choice;
	        while (true) {
	            System.out.println("----------- File Operations Menu -----------");
	            System.out.println("1. Progressive Overflow");
	            System.out.println("2. Linear Probing");
	            System.out.println("3. Quadratic Probing");
	            System.out.println("4. Double Hashing");
	            System.out.println("5. Use of Buckets");
	            System.out.println("6. Linear Quotient");
	            System.out.println("7. Brent's Method");
	            System.out.println("8. Back to main Menu");
	            System.out.print("Please enter your choice: ");
	            choice = scanner.nextInt();

	            if (choice == 8) {
	                handleFileOperation(choice);
	                break;
	            }
	            handleFileOperation(choice);
	        }
	    }

	    public static void saveUserData(User user) {
	        // Save user data to a hash table or database
	        System.out.println("User data saved.");
	    }
	  
	}
	    class Task {
	        String type;
	        String date;
	        String color;
	        String concept;
	        Task prev;
	        Task next;

	        // Constructor
	        public Task(String type, String date, String color, String concept) {
	            this.type = type;
	            this.date = date;
	            this.color = color;
	            this.concept = concept;
	            this.prev = null;
	            this.next = null;
	        }
	    }

	    private static Task head = null;
	    private static Task tail = null;

	    // Function to create a new task
	    public static boolean createTask() {
	        Scanner scanner = new Scanner(System.in);

	        System.out.print("Enter task type: ");
	        String type = scanner.nextLine();

	        System.out.print("Enter task date (e.g., 01-01-2025): ");
	        String date = scanner.nextLine();

	        System.out.print("Enter color option: ");
	        String color = scanner.nextLine();

	        System.out.print("Enter concept: ");
	        String concept = scanner.nextLine();

	        Task newTask = new Task(type, date, color, concept);

	        newTask.prev = tail;
	        newTask.next = null;

	        if (tail != null) {
	            tail.next = newTask;
	        } else {
	            head = newTask;
	        }
	        tail = newTask;

	        // Write task data to the binary file "task.bin"
	        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("task.bin", true))) {
	            out.writeObject(newTask);
	        } catch (IOException e) {
	            System.err.println("Error writing to file: " + e.getMessage());
	            return false;
	        }

	        clearScreen();
	        System.out.println("Task created and saved successfully!");
	        return true;
	    }

	    // Function to manage tasks
	    public static boolean manageTask() {
	        if (head == null) {
	            System.out.println("No tasks available. Please create a task first.");
	            return false;
	        }

	        Scanner scanner = new Scanner(System.in);
	        Task current = head;
	        int choice;

	        do {
	            System.out.println("\n--- Task Information ---");
	            System.out.println("Type: " + current.type);
	            System.out.println("Date: " + current.date);
	            System.out.println("Color: " + current.color);
	            System.out.println("Concept: " + current.concept);

	            System.out.println("\n1. Go to the next task");
	            System.out.println("2. Go to the previous task");
	            System.out.println("3. Update task information");
	            System.out.println("4. Return to main menu");
	            System.out.print("Enter your choice: ");
	            choice = scanner.nextInt();
	            scanner.nextLine(); // Consume newline

	            switch (choice) {
	                case 1:
	                    if (current.next != null) {
	                        current = current.next;
	                    } else {
	                        System.out.println("No next task.");
	                    }
	                    clearScreen();
	                    break;
	                case 2:
	                    if (current.prev != null) {
	                        current = current.prev;
	                    } else {
	                        System.out.println("No previous task.");
	                    }
	                    clearScreen();
	                    break;
	                case 3:
	                    System.out.print("Enter new type: ");
	                    current.type = scanner.nextLine();

	                    System.out.print("Enter new date: ");
	                    current.date = scanner.nextLine();

	                    System.out.print("Enter new color: ");
	                    current.color = scanner.nextLine();

	                    System.out.print("Enter new concept: ");
	                    current.concept = scanner.nextLine();

	                    System.out.println("Task updated successfully!");
	                    clearScreen();
	                    break;
	                case 4:
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        } while (choice != 4);

	        return true;
	    }

	    // Task Details main menu
	    public static boolean taskDetails() {
	        Scanner scanner = new Scanner(System.in);
	        int task;
	        do {
	            System.out.println("\n----------- Task Menu -----------");
	            System.out.println("1. Create Task");
	            System.out.println("2. Manage Task");
	            System.out.println("3. Return to Main Menu");
	            System.out.print("Please enter your choice: ");
	            task = scanner.nextInt();
	            scanner.nextLine(); // Consume newline

	            switch (task) {
	                case 1:
	                    createTask();
	                    break;
	                case 2:
	                    manageTask();
	                    break;
	                case 3:
	                    break;
	                default:
	                    clearScreen();
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        } while (task != 3);
	        return true;
	    }

	 // Class to hold attendee details
        public static class attende implements Serializable {
            String name;
            String surname;
            String huffmanCode;

            public attende(String name, String surname) {
                this.name = name;
                this.surname = surname;
                this.huffmanCode = compressName(name);
            }

            private static String compressName(String name) {
                // Simulating Huffman compression
                return name.toLowerCase();
            }
        }

        // List to store attendees
        private static final List<Attendee> attendees = new ArrayList<>();
        private static final int MAX_ATTENDEES = 100;

        /**
         * Registers attendees by adding them to the list and saving them in a binary file.
         *
         * @throws IOException If there is an error during file operations.
         */
        public static void registerAttendees() throws IOException {
            Scanner scanner = new Scanner(System.in);
            System.out.print("How many people will attend? ");
            int count = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (count <= 0 || count > MAX_ATTENDEES) {
                System.out.println("Invalid number! Please enter a value between 1 and " + MAX_ATTENDEES);
                return;
            }

            for (int i = 0; i < count; i++) {
                System.out.print("Enter the name of attendee " + (i + 1) + ": ");
                String name = scanner.nextLine();
                System.out.print("Enter the surname of attendee " + (i + 1) + ": ");
                String surname = scanner.nextLine();

                Attendee attendee = new Attendee(name, surname);
                attendees.add(attendee);

                // Save attendee in binary file
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("attendee.bin", true))) {
                    out.writeObject(attendee);
                }
            }
            System.out.println(count + " attendees registered and saved in binary format.");
        }

        /**
         * Prints all registered attendees.
         */
        public static void printAttendees() {
            if (attendees.isEmpty()) {
                System.out.println("No attendees registered.");
                return;
            }

            System.out.println("Registered Attendees:");
            for (Attendee attendee : attendees) {
                System.out.println("Name: " + attendee.name + ", Surname: " + attendee.surname +
                        ", Huffman Code: " + attendee.huffmanCode);
            }
        }

        // XOR Linked List Node
        private static class XORNode {
            String value;
            XORNode both;

            public XORNode(String value) {
                this.value = value;
            }
        }

        private static class XORLinkedList {
            private XORNode head = null;

            public void add(String value) {
                XORNode newNode = new XORNode(value);
                newNode.both = head;
                if (head != null) {
                    XORNode next = XOR(null, head.both);
                    head.both = XOR(newNode, next);
                }
                head = newNode;
            }

            public void display() {
                XORNode current = head, prev = null, next;
                System.out.println("Activity History:");
                while (current != null) {
                    System.out.print(current.value + " -> ");
                    next = XOR(prev, current.both);
                    prev = current;
                    current = next;
                }
                System.out.println("NULL");
            }

            private XORNode XOR(XORNode a, XORNode b) {
                return (XORNode) (Object) ((a == null ? 0 : System.identityHashCode(a))
                        ^ (b == null ? 0 : System.identityHashCode(b)));
            }

			public void remove(String nameToRemove) {
				// TODO Auto-generated method stub
				
			}
        }

        // Data Structures
        private static final SparseMatrix activityMatrix = new SparseMatrix();
        private static final ActivityStack activityStack = new ActivityStack();
        private static final ActivityQueue activityQueue = new ActivityQueue();
        private static final XORLinkedList xorList = new XORLinkedList();

        public static void addActivityToMatrix(int row, int col, String activity) {
            if (activityMatrix.rows.size() < 100) {
                activityMatrix.rows.add(row);
                activityMatrix.cols.add(col);
                activityMatrix.values.add(activity);
                activityStack.push(activity);
                activityQueue.enqueue(activity);
                xorList.add(activity);
            } else {
                System.out.println("Error: Sparse matrix is full!");
            }
        }

        public static void displayActivities() {
            System.out.println("Activities in Sparse Matrix:");
            for (int i = 0; i < activityMatrix.rows.size(); i++) {
                System.out.printf("Row: %d, Column: %d, Activity: %s\n", activityMatrix.rows.get(i),
                        activityMatrix.cols.get(i), activityMatrix.values.get(i));
            }
        }

        public static void attendeeMenu() throws IOException {
            Scanner scanner = new Scanner(System.in);
            XORLinkedList xorList = new XORLinkedList();

            while (true) {
                System.out.println("----------- Attendee Menu -----------");
                System.out.println("1. Register Attendees");
                System.out.println("2. Print Attendees");
                System.out.println("3. Manage Attendees List");
                System.out.println("4. Exit");
                System.out.print("Please enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        registerAttendees();
                        break;
                    case 2:
                        printAttendees();
                        break;
                    case 3:
                        System.out.println("--------- Manage Attendees List ---------");
                        System.out.println("1. Add Attendee");
                        System.out.println("2. Remove Attendee");
                        System.out.println("3. Display XOR List");
                        System.out.print("Please enter your choice: ");
                        int subChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (subChoice) {
                            case 1:
                                System.out.print("Enter the name of the attendee to add: ");
                                String nameToAdd = scanner.nextLine();
                                xorList.add(nameToAdd);
                                System.out.println("Attendee added: " + nameToAdd);
                                break;
                            case 2:
                                System.out.print("Enter the name of the attendee to remove: ");
                                String nameToRemove = scanner.nextLine();
                                xorList.remove(nameToRemove);
                                System.out.println("Attendee removed: " + nameToRemove);
                                break;
                            case 3:
                                xorList.display();
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        // Sparse Matrix for Activities
        private static class SparseMatrix {
            List<Integer> rows = new ArrayList<>();
            List<Integer> cols = new ArrayList<>();
            List<String> values = new ArrayList<>();
        }

        // Stack for Activity Tracking
        private static class ActivityStack {
            private final Stack<String> stack = new Stack<>();

            public void push(String activity) {
                stack.push(activity);
            }

            public void pop() {
                if (!stack.isEmpty()) {
                    System.out.println("Popped activity: " + stack.pop());
                } else {
                    System.out.println("Error: Stack is empty!");
                }
            }
        }

        // Queue for Activity Tracking
        private static class ActivityQueue {
            private final Queue<String> queue = new LinkedList<>();

            public void enqueue(String activity) {
                queue.add(activity);
            }

            public void dequeue() {
                if (!queue.isEmpty()) {
                    System.out.println("Dequeued activity: " + queue.poll());
                } else {
                    System.out.println("Error: Queue is empty!");
                }
            }
        }

        public static void planTimelines() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the timeline details (e.g., start and end dates): ");
            String timeline = scanner.nextLine();
            System.out.println("Timeline planned: " + timeline);
        }

        public static void organizeActivities() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the row index for the activity: ");
            int row = scanner.nextInt();
            System.out.print("Enter the column index for the activity: ");
            int col = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter the activity details: ");
            String activity = scanner.nextLine();
            addActivityToMatrix(row, col, activity);
            System.out.println("Activity organized: " + activity);
        }

        public static void scheduleMenu() {
            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("----------- Schedule Menu -----------");
                System.out.println("1. Plan Timelines");
                System.out.println("2. Organize Activities");
                System.out.println("3. Display Activities");
                System.out.println("4. Display Activity History");
                System.out.println("5. Pop Activity from Stack");
                System.out.println("6. Dequeue Activity");
                System.out.println("7. Return to Main Menu");
                System.out.print("Please enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: planTimelines();
                    case 2 : organizeActivities();
                    case 3 :displayActivities();
                    case 4 : xorList.display();
                    case 5 : activityStack.pop();
                    case 6 : activityQueue.dequeue();
                    case 7 : System.out.println("Returning to Main Menu...");
                    default : System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 7);
        }
        private static final int MAX_FEEDBACKS = 4;
        private static final int MAX_KEYS = 3; // Maximum number of keys for B+ tree

        private int[] feedbackRatings = new int[MAX_FEEDBACKS];
        private int feedbackCount = 0;

        // Global variables required for SCC
        private int[] discoveryTime = new int[MAX_FEEDBACKS];
        private int[] lowLink = new int[MAX_FEEDBACKS];
        private boolean[] inStack = new boolean[MAX_FEEDBACKS];
        private Stack<Integer> stack = new Stack<>();
        private int timeCounter = 0;

        // B+ tree node definitions
        static class BPlusLeafNode {
            int[] keys = new int[MAX_KEYS];
            BPlusLeafNode next;
            int numKeys = 0;
        }

        static class BPlusInternalNode {
            int[] keys = new int[MAX_KEYS];
            Object[] children = new Object[MAX_KEYS + 1];
            int numKeys = 0;
        }

        static class BPlusTree {
            Object root;

            public BPlusTree() {
                BPlusLeafNode rootLeaf = new BPlusLeafNode();
                root = rootLeaf;
            }
        }

        // Add a key to a leaf node
        private void insertIntoLeaf(BPlusLeafNode leaf, int key) {
            if (leaf.numKeys < MAX_KEYS) {
                int i = leaf.numKeys - 1;
                while (i >= 0 && leaf.keys[i] > key) {
                    leaf.keys[i + 1] = leaf.keys[i];
                    i--;
                }
                leaf.keys[i + 1] = key;
                leaf.numKeys++;
            } else {
                // Detach a leaf node
                BPlusLeafNode newLeaf = new BPlusLeafNode();
                newLeaf.next = leaf.next;
                leaf.next = newLeaf;

                int mid = MAX_KEYS / 2;
                newLeaf.numKeys = MAX_KEYS - mid;
                System.arraycopy(leaf.keys, mid, newLeaf.keys, 0, newLeaf.numKeys);
                leaf.numKeys = mid;

                // Adding a new key
                if (key > newLeaf.keys[0]) {
                    insertIntoLeaf(newLeaf, key);
                } else {
                    insertIntoLeaf(leaf, key);
                }
            }
        }

        // Add key to B+ tree
        private void insert(BPlusTree tree, int key) {
            BPlusLeafNode root = (BPlusLeafNode) tree.root;
            if (root.numKeys < MAX_KEYS) {
                insertIntoLeaf(root, key);
            } else {
                // Creating new roots and separating leaves
                BPlusInternalNode newRoot = new BPlusInternalNode();
                newRoot.numKeys = 1;
                newRoot.keys[0] = root.keys[MAX_KEYS / 2];
                newRoot.children[0] = root;
                BPlusLeafNode newLeaf = new BPlusLeafNode();
                newRoot.children[1] = newLeaf;

                tree.root = newRoot;
                insertIntoLeaf(newLeaf, key);
            }
        }

        // Helper functions for SCC
        private void pushStackSCC(int node) {
            stack.push(node);
            inStack[node] = true;
        }

        private int popStackSCC() {
            int node = stack.pop();
            inStack[node] = false;
            return node;
        }

        // SCC (Tarjan's Algorithm)
        private void SCC(int node, int[][] adjMatrix, int n) {
            discoveryTime[node] = lowLink[node] = ++timeCounter;
            pushStackSCC(node);

            for (int i = 0; i < n; i++) {
                if (adjMatrix[node][i] == 1) {
                    if (discoveryTime[i] == -1) {
                        SCC(i, adjMatrix, n);
                        lowLink[node] = Math.min(lowLink[node], lowLink[i]);
                    } else if (inStack[i]) {
                        lowLink[node] = Math.min(lowLink[node], discoveryTime[i]);
                    }
                }
            }

            if (lowLink[node] == discoveryTime[node]) {
                System.out.print("SCC Found: ");
                int w;
                do {
                    w = popStackSCC();
                    System.out.print("Feedback " + (w + 1) + " (Rating: " + feedbackRatings[w] + ") ");
                } while (w != node);
                System.out.println();
            }
        }

        // Feedback collection function
        private void gatherFeedbacks(BPlusTree tree) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your feedback (max 255 characters): ");
            String feedback = scanner.nextLine();

            System.out.print("Rate the application (1 to 5): ");
            int rating = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (rating >= 1 && rating <= 5) {
                feedbackRatings[feedbackCount++] = rating;
                insert(tree, rating);
                System.out.println("Feedback received: " + feedback);
                System.out.println("Rating received: " + rating);
            } else {
                System.out.println("Invalid rating. Please enter a value between 1 and 5.");
            }

            System.out.println("Press Enter to continue...");
            scanner.nextLine();
        }

        private void displaySortedRatings() {
            if (feedbackCount == 0) {
                System.out.println("No ratings available.");
                return;
            }

            int[] sortedRatings = Arrays.copyOf(feedbackRatings, feedbackCount);
            Arrays.sort(sortedRatings);

            System.out.println("Sorted Ratings:");
            for (int rating : sortedRatings) {
                System.out.print(rating + " ");
            }
            System.out.println();
        }

        // BFS function
        private void BFS(int startNode, int[][] adjMatrix, int n) {
            Queue<Integer> queue = new LinkedList<>();
            boolean[] visited = new boolean[MAX_FEEDBACKS];

            queue.add(startNode);
            visited[startNode] = true;

            System.out.println("BFS Traversal starting from Feedback " + (startNode + 1) + ":");
            while (!queue.isEmpty()) {
                int node = queue.poll();
                System.out.println("Visited Feedback " + (node + 1) + " with Rating " + feedbackRatings[node]);

                for (int i = 0; i < n; i++) {
                    if (adjMatrix[node][i] == 1 && !visited[i]) {
                        queue.add(i);
                        visited[i] = true;
                    }
                }
            }
        }

        // DFS function
        private void DFS(int node, boolean[] visited, int[][] adjMatrix, int n) {
            visited[node] = true;
            System.out.println("Visited Feedback " + (node + 1) + " with Rating " + feedbackRatings[node]);

            for (int i = 0; i < n; i++) {
                if (adjMatrix[node][i] == 1 && !visited[i]) {
                    DFS(i, visited, adjMatrix, n);
                }
            }
        }

        // Print leaf nodes and their contents in a B+ tree
        private void printLeafNodes(BPlusTree tree) {
            BPlusLeafNode current = (BPlusLeafNode) tree.root;
            while (current != null) {
                System.out.print("Leaf Node: ");
                for (int i = 0; i < current.numKeys; i++) {
                    System.out.print(current.keys[i] + " ");
                }
                System.out.println();
                current = current.next;
            }
        }

        // Feedback menu
        public void feedback() {
            BPlusTree tree = new BPlusTree();
            Scanner scanner = new Scanner(System.in);

            int choice;
            do {
                System.out.println("\n----------- Feedback Menu -----------");
                System.out.println("1. Gather Feedback");
                System.out.println("2. View Sorted Ratings");
                System.out.println("3. Print B+ Tree");
                System.out.println("4. Perform BFS");
                System.out.println("5. Perform DFS");
                System.out.println("6. Find SCC (Tarjan Algorithm)");
                System.out.println("7. Return to Main Menu");
                System.out.print("Please enter your choice: ");

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        gatherFeedbacks(tree);
                        break;
                    case 2:
                        displaySortedRatings();
                        break;
                    case 3:
                        System.out.println("Feedbacks stored in B+ Tree:");
                        printLeafNodes(tree);
                        break;
                    case 4: {
                        System.out.print("Enter starting feedback number for BFS (1 to " + feedbackCount + "): ");
                        int startNode = scanner.nextInt() - 1;

                        if (startNode < 0 || startNode >= feedbackCount) {
                            System.out.println("Invalid node.");
                            break;
                        }

                        int[][] adjMatrix = new int[MAX_FEEDBACKS][MAX_FEEDBACKS];
                        adjMatrix[0][1] = adjMatrix[1][0] = 1;
                        adjMatrix[1][2] = adjMatrix[2][1] = 1;

                        BFS(startNode, adjMatrix, feedbackCount);
                        break;
                    }
                    case 5: {
                        System.out.print("Enter starting feedback number for DFS (1 to " + feedbackCount + "): ");
                        int startNode = scanner.nextInt() - 1;

                        if (startNode < 0 || startNode >= feedbackCount) {
                            System.out.println("Invalid node.");
                            break;
                        }

                        int[][] adjMatrix = new int[MAX_FEEDBACKS][MAX_FEEDBACKS];
                        adjMatrix[0][1] = adjMatrix[1][0] = 1;
                        adjMatrix[1][2] = adjMatrix[2][1] = 1;

                        boolean[] visited = new boolean[MAX_FEEDBACKS];
                        DFS(startNode, visited, adjMatrix, feedbackCount);
                        break;
                    }
                    case 6: {
                        int[][] adjMatrix = new int[MAX_FEEDBACKS][MAX_FEEDBACKS];
                        adjMatrix[0][1] = adjMatrix[1][0] = 1;
                        adjMatrix[1][2] = adjMatrix[2][1] = 1;

                        Arrays.fill(discoveryTime, -1);
                        Arrays.fill(lowLink, -1);
                        Arrays.fill(inStack, false);
                        stack.clear();
                        timeCounter = 0;

                        System.out.println("Finding SCCs:");
                        for (int i = 0; i < feedbackCount; i++) {
                            if (discoveryTime[i] == -1) {
                                SCC(i, adjMatrix, feedbackCount);
                            }
                        }
                        break;
                    }
                    case 7:
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            } while (choice != 7);
        }
       
       
        

	        


}
