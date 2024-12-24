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
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * @class Event
 * @brief This class represents an Event that performs various operations, including hash table management, Huffman coding, and algorithms.
 * @details The Event class provides methods for user authentication, event management, attendee handling, and algorithm demonstrations.
 * @author ugur.coruh
 */
public class Event {
 static final int MAX_FEEDBACKS = 10; // Maksimum geri bildirim sayısı
	static int[] feedbackRatings = new int[MAX_FEEDBACKS];
	static int feedbackCount = 0;

	// Hash fonksiyonu
	public static int hash(String phone) {
	    int hash = 0;
	    for (int i = 0; i < phone.length(); i++) { 
	        hash = (hash * 31 + phone.charAt(i)) % TABLE_SIZE;
	    }
	    return hash;
	}
	
	// Event sınıfına ekleyin
	public static User[] getHashTable() {
	    return hashTable;
	}

	
	// Function to clear the screen
	public static void clearScreen() {
	    String os = System.getProperty("os.name").toLowerCase();
	    if (os.contains("win")) { 
	        try {
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();  // for Windows
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
	        try {
	            new ProcessBuilder("clear").inheritIO().start().waitFor();  // for Linux and macOS
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	public static final int TABLE_SIZE = 100;
	public static final int MAX_TREE_NODES = 256;

	// Define the structure for an AttendeE
	class Attendeer{
	    String nameAttendee;
	    String surnameAttendee;
	    String huffmanCode;  // Store the Huffman code
	}

	// Define a structure for the Huffman tree nodes
	public static class MinHeapNode {
	    char data;
	    int freq;
	    MinHeapNode left, right;
	}

	// Define a structure for Min Heap
	public static class MinHeap {
	    int size;
	    int capacity;
	    MinHeapNode[] array;  // Array of MinHeapNode objects
	}
	
	// Function to create a Min Heap
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
	// Function to create a Min Heap Node
	public static MinHeapNode createMinHeapNode(char data, int freq) {
	    MinHeapNode newNode = new MinHeapNode();
	    newNode.data = data;
	    newNode.freq = freq;
	    newNode.left = newNode.right = null;
	    return newNode;
	}
	
	// Function to insert a node into the Min Heap
	public static void insertMinHeap(MinHeap minHeap, MinHeapNode minHeapNode) {
	    minHeap.array[minHeap.size] = minHeapNode;
	    minHeap.size++;
	}

	// Function to extract the minimum node from the Min Heap
	public static MinHeapNode extractMin(MinHeap minHeap) {
	    MinHeapNode temp = minHeap.array[0];
	    minHeap.array[0] = minHeap.array[minHeap.size - 1];
	    minHeap.size--;
	    return temp;
	}
	
	
	
	// Define the structure for a User
	public static class User {
	    String name;
	    String surname;
	    String phone;
	    String password;
	    User next;  // We use linked list to resolve collisions
		public void setPassword(String string) {
			// TODO Auto-generated method stub
			
		}
		public void setEmail(String string) {
			// TODO Auto-generated method stub
			
		}
	}
	class Event1 {
	    String type;      // Event type
	    String date;      // Event date
	    String color;     // Event color
	    String concept;   // Event concept
	    Event1 prev;      // Reference to the previous event
	    Event1 next;      // Reference to the next event

	    // Constructor for the Event1 class
	    public Event1(String type, String date, String color, String concept) {
	        this.type = type;
	        this.date = date;
	        this.color = color;
	        this.concept = concept;
	        this.prev = null;  // Initially, no previous event
	        this.next = null;  // Initially, no next event
	    }
	}
	public static User[] hashTable = new User[TABLE_SIZE]; // Sınıf düzeyinde tanımlama
	// Kullanıcıyı hash tablosuna kaydetme
	public static void saveUser(User newUser) {
	    int index = hash(newUser.phone);
	    newUser.next = hashTable[index];
	    hashTable[index] = newUser;
	}

	// Hash tablosunu yazdırma
	public static void printHashTable() {
	    System.out.println("Hash Table Contents:");
	    for (int i = 0; i < TABLE_SIZE; i++) {
	        User current = hashTable[i];
	        if (current == null) {
	            continue; // Boş hücreleri atla
	        }
	        System.out.println("Index " + i + ":");
	        while (current != null) {
	            System.out.println(" Name: " + current.name + " " + current.surname + ", Phone: " + current.phone + ", Password: " + current.password);
	            current = current.next;
	        }
	    }
	    System.out.println("End of Hash Table.");
	}

	// Kullanıcı kaydetme
	public static void saveUserData(User user) {
	    saveUser(user); // Hash tablosuna ekle
	    saveHashTableToFile(); // Dosyaya kaydet
	    clearScreen(); 
	}

	
	public static void loadHashTableFromFile() {
	    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.bin"))) {
	        while (true) {
	            try {
	                User newUser = (User) in.readObject();  // Nesne oku
	                newUser.next = null;
	                saveUser(newUser);  // Kullanıcıyı hash tablosuna ekle
	            } catch (IOException | ClassNotFoundException e) {
	                break;  // Dosya sonuna gelindi veya başka bir hata oluştu
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("An error occurred while opening or reading the file.");
	        e.printStackTrace();
	    }
	}
	
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
	        System.out.println("An error occurred while opening or writing to the file.");
	        e.printStackTrace();
	    }
	}

	public static boolean validateLogin(String phone, String password) {
	    if (phone == null || password == null) {
	        return false; // Giriş parametrelerinin geçersizliği
	    }

	    int index = hash(phone);
		User[] hashTable = new User[TABLE_SIZE];
	    User current = hashTable[index];
	    while (current != null) {
	        if (current.phone.equals(phone) && current.password.equals(password)) {
	            return true; // Giriş başarılı
	        }
	        current = current.next;
	    }
	    return false; // Giriş başarısız
	}
	public static boolean quadraticProbingInsert(User newUser) {
	    int index = hash(newUser.phone);
	    int i = 0;
	    int originalIndex = index;
	    int startIndex = index;
		// Hash table definition
		User[] hashTable = new User[TABLE_SIZE];
	    while (hashTable[index] != null && i < TABLE_SIZE) {
	        i++;
	        index = (originalIndex + i * i) % TABLE_SIZE;

	        if (index == startIndex) {
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
	
	public static void progressiveOverflowAlgorithm() {
	    int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	    int overflowThreshold = 7; // Set the overflow threshold for demonstration
	    int currentSum = 0;

	    // Step through each element and calculate sum
	    for (int i = 0; i < array.length; i++) {
	        currentSum += array[i];
	        if (currentSum > overflowThreshold) {
	            System.out.printf("Overflow detected at element %d with sum %d%n", i, currentSum);
	            // Corrective action: Reset but include current element in new sum
	            currentSum = array[i];
	        }
	    }
	}
	// Register yöntemi
	static void Register(Scanner scanner) {
	    System.out.print("Enter a name to register: ");
	    String username = scanner.nextLine();
	    System.out.print("Enter a password: ");
	    String password = scanner.nextLine();

	    if (users.containsKey(username)) {
	        System.out.println("Name already exists. Try another one.");
	    } else {
	        // Yeni kullanıcı oluştur
	        User newUser = new User();
	        newUser.name = username;
	        newUser.surname = ""; // Soyad bilgisi yoksa boş bırakabilirsiniz
	        newUser.phone = ""; // Telefon bilgisi yoksa boş bırakabilirsiniz
	        newUser.password = password;

	        // Kullanıcıyı hash tablosuna kaydet
	        saveUser(newUser);

	        // Kullanıcıyı HashMap'e ekle
	        users.put(username, password);

	        System.out.println("Registration successful! You can now log in.");
	        System.out.println("Current Hash Table:");
	        
	        // Hash tablosunu yazdır
	        printHashTable();
	    }
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
	            	 System.out.println("Exiting the program. Goodbye!");
	                 System.exit(0); // Terminate the program
	            default:
	                System.out.println("Invalid choice. Please try again.");
	                break;
	        }
	    } while (choice != 6);
	    return true;
	}

	public static void progressiveOverflow() {
	    System.out.println("Executing Progressive Overflow algorithm...");
	    int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	    int overflowThreshold = 7; // Set the overflow threshold for demonstration
	    int currentSum = 0;

	    for (int i = 0; i < array.length; i++) {
	        currentSum += array[i];
	        if (currentSum > overflowThreshold) {
	            System.out.printf("Overflow detected at index %d with sum %d\n", i, currentSum);
	            currentSum = 0; // Reset for simplicity
	        }
	    }
	}

	public static void linearProbing() {
	    System.out.println("Executing Linear Probing algorithm...");
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

	        if (!placed) {
	            System.out.printf("Hash table is full, cannot place key %d\n", keys[i]);
	        }
	    }

	    // Print hash table contents
	    for (int i = 0; i < hashTable.length; i++) {
	        if (hashTable[i] != -1) {
	            System.out.printf("Index %d: %d\n", i, hashTable[i]);
	        } else {
	            System.out.printf("Index %d: Empty\n", i);
	        }
	    }
	}
	public static void quadraticProbing() {
	    System.out.println("Executing Quadratic Probing algorithm...");
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
	        System.out.printf("Index %d: %d\n", i, hashTable[i]);
	    }
	}

	public static void doubleHashing() {
	    System.out.println("Executing Double Hashing algorithm...");
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
	        System.out.printf("Index %d: %d\n", i, hashTable[i]);
	    }
	}
	public static void useBuckets() {
	    System.out.println("Executing Use of Buckets algorithm...");
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
	        System.out.print("Bucket " + i + ": ");
	        for (int j = 0; j < 10; j++) {
	            if (buckets[i][j] != 0) {
	                System.out.print(buckets[i][j] + " ");
	            }
	        }
	        System.out.println();
	    }
	}

	public static void linearQuotient() {
	    System.out.println("Executing Linear Quotient algorithm...");
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
	        System.out.printf("Index %d: %d\n", i, hashTable[i]);
	    }
	}
	public static void brentsMethod() {
	    System.out.println("Executing Brent's Method algorithm...");
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
	        System.out.printf("Index %d: %d\n", i, hashTable[i]);
	    }
	}

	
	 // Kullanıcı bilgilerini saklamak için bir HashMap
    static Map<String, String> users = new HashMap<>();

    public static boolean authentication() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("----------- Authentication Menu -----------");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Guest Login");
            System.out.println("4. File Operations for Fast Search Operations");
            System.out.println("5. Return to main menu");
            System.out.print("Please enter your choice: ");

            int login = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme

            switch (login) {
                case 1:
                    Register(scanner);
                    break;
                case 2:
                    if (logIn(scanner)) {
                        clearScreen();
                        System.out.println("Login successful!");
                        return true; // Ana menüye dönmek için
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
                    return false;
                default:
                    clearScreen();
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    static boolean logIn(Scanner scanner) {
        System.out.print("Enter your name: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Kullanıcı bilgilerini doğrulama
        return users.containsKey(username) && users.get(username).equals(password);
    }

    static void guest() {
        System.out.println("You are in guest mode.");
    }
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
	            System.out.println("Returning to Authentication Menu.");
	            break;
	        default:
	            System.out.println("Invalid choice. Please try again.");
	    }
	}
 
    private static Scanner scanner = new Scanner(System.in); // Sınıf düzeyinde tanımlama

    static void fileOperationsMenu() {
        System.out.println("----------- File Operations Menu -----------");
        System.out.println("1. Progressive Overflow");
        System.out.println("2. Linear Probing");
        System.out.println("3. Quadratic Probing");
        System.out.println("4. Double Hashing");
        System.out.println("5. Use Buckets");
        System.out.println("6. Linear Quotient");
        System.out.println("7. Brent's Method");
        System.out.println("8. Return to Authentication Menu");
        System.out.print("Please enter your choice: ");

        int fileOperationChoice = scanner.nextInt(); // scanner erişilebilir hale geldi
        handleFileOperation(fileOperationChoice); // choice hatası çözüldü
    }

 
 // Static head and tail for the doubly linked list
    static EventNode head = null;
    private static EventNode tail = null;

    // Static inner class for EventNode (renamed to avoid duplication)
    public static class EventNode {
        String type;
        String date;
        String color;
        String concept;
        EventNode prev;
        EventNode next;

        // Constructor for the EventNode class
        public EventNode(String type, String date, String color, String concept) {
            this.type = type;
            this.date = date;
            this.color = color;
            this.concept = concept;
            this.prev = null; // Initially, no previous event
            this.next = null; // Initially, no next event
        }
    }
 // Function to manage events
    public static boolean manageEvent() {
        if (head == null) {
            System.out.println("No events available. Please create an event first.");
            return false;
        }

        EventNode current = head;  // head is of type EventNode
        Scanner scanner = new Scanner(System.in);
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
    
    public static boolean createEvent() {
        Scanner scanner = new Scanner(System.in);

        EventNode newEvent = new EventNode("", "", "", "");

        System.out.print("Enter event type: ");
        newEvent.type = scanner.nextLine();

        System.out.print("Enter event date (e.g., 01-01-2025): ");
        newEvent.date = scanner.nextLine();

        System.out.print("Enter color option: ");
        newEvent.color = scanner.nextLine();

        System.out.print("Enter concept: ");
        newEvent.concept = scanner.nextLine();

        newEvent.prev = tail;
        newEvent.next = null;

        if (tail != null) {
            tail.next = newEvent;
        } else {
            head = newEvent;
        }
        tail = newEvent;

        // Sadece gerekli alanları bir Map olarak kaydet
        Map<String, String> eventData = new HashMap<>();
        eventData.put("type", newEvent.type);
        eventData.put("date", newEvent.date);
        eventData.put("color", newEvent.color);
        eventData.put("concept", newEvent.concept);

        // Save event data to a binary file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("event.bin", true))) {
            out.writeObject(eventData); // Sadece gerekli alanları yaz
        } catch (IOException e) {
            System.err.println("Error saving event: " + e.getMessage());
            System.out.println("Returning to main menu...");
            mainMenu(); // Ana menüye dön
            return false;
        }

        System.out.println("Event created and saved successfully!");
        return true;
    }


	// Event Details main menu
    public static boolean eventDetails() {
        Scanner scanner = new Scanner(System.in);

        int event;
        System.out.println("\n----------- Event Menu -----------");
        System.out.println("1. Create Event");
        System.out.println("2. Manage Event");
        System.out.println("3. Return to main menu");
        System.out.print("Please enter your choice: ");
        event = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (event) {
            case 1:
                createEvent();
                break;
            case 2:
                manageEvent();
                break;
            case 3:
                return false;  // Return to main menu
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
        return true;  // Continue in the Event Menu
    }

    public static void main(String[] args) {
        // Start the event menu
        while (eventDetails()) {
            // Loop through the event menu until '3' is selected
        }
    }
    public static final int MAX_ATTENDEES = 100;
    public static final int MAX_NAME_LENGTH = 50;

    static Attendee[] attendees = new Attendee[MAX_ATTENDEES];
    static int attendeeCount = 0;

    

public static class Attendee implements Serializable {
    private static final long serialVersionUID = 1L; // Version ID for serialization

    String nameAttendee;
    String surnameAttendee;
    String huffmanCode; // Assuming Huffman code is stored as a string
}
    // Knuth-Morris-Pratt (KMP) search function
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
                    System.out.printf("Pattern found in Huffman code of attendee: %s %s\n", attendees[i].nameAttendee, attendees[i].surnameAttendee);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No match found.");
        }
    }
 // Compute the LPS array for the pattern (used in KMP search)
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
    
    // Function to compress and store the Huffman code for each attendee's name
    public static void compressAttendeeName(Attendee attendee) {
        int len = attendee.nameAttendee.length();
        // Directly store the name for simplicity (as a placeholder for actual Huffman coding)
        StringBuilder huffmanCode = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            huffmanCode.append(attendee.nameAttendee.charAt(i)); // Directly storing name characters
        }
        attendee.huffmanCode = huffmanCode.toString(); // Storing Huffman code
    }

 // Main function for registering attendees
    public static boolean registerAttendees() throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Ask for the number of attendees
        System.out.print("How many people will attend? ");
        int count = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer after reading the integer

        // Validate the number
        if (count <= 0 || count > MAX_ATTENDEES) {
            System.out.printf("Invalid number! Please enter a value between 1 and %d.\n", MAX_ATTENDEES);
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
            System.out.printf("Enter the first name of attendee %d: ", i + 1);
            attendees[attendeeCount] = new Attendee(); // Create a new Attendee object
            attendees[attendeeCount].nameAttendee = scanner.nextLine();
            System.out.printf("Enter the last name of attendee %d: ", i + 1);
            attendees[attendeeCount].surnameAttendee = scanner.nextLine();

            compressAttendeeName(attendees[attendeeCount]); // Compress the attendee's name and add Huffman code
            objectOutputStream.writeObject(attendees[attendeeCount]); // Write the object to the file
            attendeeCount++; // Increment the attendee count
        }

        // Close the streams
        objectOutputStream.close();
        file.close();

        System.out.printf("%d attendees have been registered and stored in binary format.\n", count);
        return true;
    }
    
 // Custom ObjectOutputStream class (for appending operations)
    static class AppendObjectOutputStream extends ObjectOutputStream {
        public AppendObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // Prevent writing a new header
        }
    }



    // Function to print all registered attendees and their Huffman codes
    public static void printAttendees() {
        System.out.println("\nRegistered Attendees:");
        for (int i = 0; i < attendeeCount; i++) {
            System.out.printf("Name: %s, Surname: %s, Huffman Code: %s\n", 
                attendees[i].nameAttendee, attendees[i].surnameAttendee, attendees[i].huffmanCode);
        }
    }
    
 // Main attendee menu function
    public static boolean attendee() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("----------- Attendee Menu -----------");
        System.out.println("1. Register Attendees");
        System.out.println("2. Track Attendees");
        System.out.println("3. Print Attendees");
        System.out.println("4. Manage Attendees List");
        System.out.println("5. Return to main menu");
        System.out.print("Please enter your choice: ");
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
            	   try {
            	        registerAttendees(); // Hata oluşabilecek kod
            	    } catch (IOException e) {
            	        System.out.println("Dosya işlemleri sırasında bir hata oluştu: " + e.getMessage());
            	    }
                return false;  // Return false after registration
            case 2: {
                System.out.print("Enter the name to search: ");
                String searchName = scanner.next();
                kmpSearch(searchName);  // Search in Huffman code
                return false;  // Return after search
            }
            case 3:
                printAttendees();
                return false;  // Return after printing attendees
            case 4: {
                int subChoice;
                System.out.println("--------- Manage Attendees List ---------");
                System.out.println("1. Add Attendee");
                System.out.println("2. Remove Attendee");
                System.out.println("3. Display Activity History");
                System.out.println("4. Back to Main Menu");
                System.out.print("Please enter your choice: ");
                subChoice = scanner.nextInt();

                switch (subChoice) {
                    case 1: {
                        System.out.print("Enter the name of the attendee to add: ");
                        String nameToAdd = scanner.next();
                        addToXORList(nameToAdd); // Add the attendee to XOR list
                        System.out.println("Attendee added: " + nameToAdd);
                        return attendee();  // Return the result of attendee() call
                    }
                    case 2: {
                        System.out.print("Enter the name of the attendee to remove: ");
                        String nameToRemove = scanner.next();
                        removeFromXORList(nameToRemove); // Remove the attendee from XOR list
                        System.out.println("Attendee removed: " + nameToRemove);
                        return attendee();  // Return the result of attendee() call
                    }
                    case 3:
                        displayXORList();  // Display the XOR list
                         // Return the result of attendee() call
                    case 4:
                        return false;  // Return false to go back to the main menu
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        return attendee();  // Return the result of attendee() call
                }
            }
            case 5:
                return false;  // Return false to go back to the main menu
            default:
                System.out.println("Invalid choice. Please try again.");
                return false;  // Return false if an invalid choice is entered
        }
    }

    public static final int MAX_SIZE = 100;
    public static final int STACK_SIZE = 100;
    public static final int QUEUE_SIZE = 100;
 // Sparse Matrix Structure
    public static class Matrix {
        int[] row = new int[MAX_SIZE];
        int[] col = new int[MAX_SIZE];
        String[] value = new String[MAX_SIZE];  // Store activity details
        int size;  // Number of non-zero entries
    }

    public static Matrix activityMatrix = new Matrix();  // Global sparse matrix for activities

    // Stack Structure
    public static class Stack {
        String[] items = new String[STACK_SIZE];
        int top = -1;
    }
    
    public static Stack activityStack = new Stack();  // Stack for storing activities

    // Queue Structure
    public static class Queue {
        String[] items = new String[QUEUE_SIZE];
        int front = 0, rear = 0;
    }

    public static Queue activityQueue = new Queue();  // Queue for storing activities

    // XOR Linked List Structure
    public static class XORNode {
        String value;
        XORNode both; // XOR of previous and next node
    }

    public static XORNode xorHead = null; // Head of the XOR linked list

    public static XORNode XOR(XORNode a, XORNode b) {
        return new XORNode(); // XOR işlevinin daha anlamlı hale getirilmesi gerekiyor
    }

    // Function to add a new node to the XOR linked list
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

    // Function to remove a node from the XOR linked list
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

    // Function to display the XOR linked list
    public static void displayXORList() {
        XORNode current = xorHead;
        XORNode prev = null;
        XORNode next;

        System.out.println("Activity History: ");
        while (current != null) {
            System.out.print(current.value + " -> ");
            next = XOR(prev, current.both); // Get the next node using XOR
            prev = current;
            current = next;
        }
        System.out.println("NULL");
        attendee();   
    }
	

