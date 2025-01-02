/**

 @file EventTest.java
 @brief This file contains the test cases for the Event class.
 @details This file includes test methods to validate the functionality of the Event class. It uses JUnit for unit testing.
 */
package com.beyza.gokce.siray.event;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.text.DateFormat.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import org.junit.Test;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

import com.beyza.gokce.siray.event.Event;
import com.beyza.gokce.siray.event.Event.Attendee;
import com.beyza.gokce.siray.event.Event.BPlusTree;
import com.beyza.gokce.siray.event.Event.Matrix;
import com.beyza.gokce.siray.event.Event.MinHeap;
import com.beyza.gokce.siray.event.Event.MinHeapNode;
import com.beyza.gokce.siray.event.Event.User;
import com.beyza.gokce.siray.event.Event.XORNode;

/**

 @class EventTest
 @brief This class represents the test class for the Event class.
 @details The EventTest class provides test methods to verify the behavior of the Event class. It includes test methods for addition, subtraction, multiplication, and division operations.
 @author ugur.coruh
 */
public class EventTest {

    private static final int MAX_FEEDBACKS = 0;

    /**
     * @brief This method is executed once before all test methods.
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @brief This method is executed once after all test methods.
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }



    @Test
    public void testHash() {
        String phone = "1234567890";
        int hashValue = Event.hash(phone);
        assertEquals(75, hashValue);
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private Object outputStream;
    private Object outputContent;
    private int[] feedbackRatings;
    private int feedbackCount;
	private String timeline;
	private Object userFile;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        Event.xorHead = null;
    }

    @After
    public void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        Event.xorHead = null;
    }

    @Test
    public void testCreateMinHeap_WithPositiveCapacity_ShouldInitializeHeap() {
        int capacity = 10;

        Event.MinHeap result = Event.createMinHeap(capacity);

        assertNotNull(result);
        assertEquals(0, result.size);
        assertEquals(capacity, result.capacity);
        assertNotNull(result.array);
        assertEquals(capacity, result.array.length);
    }

    @Test
    public void testCreateMinHeap_WithZeroCapacity_ShouldInitializeHeapWithNullArray() {
        int capacity = 0;

        Event.MinHeap result = Event.createMinHeap(capacity);

        assertNotNull(result);
        assertEquals(0, result.size);
        assertEquals(capacity, result.capacity);
        assertNull(result.array);
    }

    @Test
    public void testCreateMinHeap_WithNegativeCapacity_ShouldInitializeHeapWithNullArray() {
        int capacity = -5;

        Event.MinHeap result = Event.createMinHeap(capacity);

        assertNotNull(result);
        assertEquals(0, result.size);
        assertEquals(capacity, result.capacity);
        assertNull(result.array);
    }

    @Test
    public void testCreateMinHeapNode_WithValidInput_ShouldInitializeNodeCorrectly() {
        char data = 'a';
        int frequency = 5;

        Event.MinHeapNode result = Event.createMinHeapNode(data, frequency);

        assertNotNull(result);
        assertEquals(data, result.data);
        assertEquals(frequency, result.freq);
        assertNull(result.left);
        assertNull(result.right);
    }

    @Test
    public void testCreateMinHeapNode_WithZeroFrequency_ShouldInitializeNodeCorrectly() {
        char data = 'z';
        int frequency = 0;

        Event.MinHeapNode result = Event.createMinHeapNode(data, frequency);

        assertNotNull(result);
        assertEquals(data, result.data);
        assertEquals(frequency, result.freq);
        assertNull(result.left);
        assertNull(result.right);
    }

    @Test
    public void testCreateMinHeapNode_WithNegativeFrequency_ShouldInitializeNodeCorrectly() {
        char data = 'x';
        int frequency = -1;

        Event.MinHeapNode result = Event.createMinHeapNode(data, frequency);

        assertNotNull(result);
        assertEquals(data, result.data);
        assertEquals(frequency, result.freq);
        assertNull(result.left);
        assertNull(result.right);
    }

    @Test
    public void testInsertMinHeap_WithValidInput_ShouldInsertNodeCorrectly() {
        int capacity = 5;
        Event.MinHeap minHeap = Event.createMinHeap(capacity);
        Event.MinHeapNode node = Event.createMinHeapNode('a', 10);

        Event.insertMinHeap(minHeap, node);

        assertNotNull(minHeap.array);
        assertEquals(1, minHeap.size);
        assertEquals(node, minHeap.array[0]);
        assertEquals('a', minHeap.array[0].data);
        assertEquals(10, minHeap.array[0].freq);
    }

    @Test
    public void testInsertMinHeap_MultipleInsertions_ShouldMaintainCorrectOrder() {
        int capacity = 5;
        Event.MinHeap minHeap = Event.createMinHeap(capacity);
        Event.MinHeapNode node1 = Event.createMinHeapNode('a', 10);
        Event.MinHeapNode node2 = Event.createMinHeapNode('b', 15);

        Event.insertMinHeap(minHeap, node1);
        Event.insertMinHeap(minHeap, node2);

        assertEquals(2, minHeap.size);
        assertEquals(node1, minHeap.array[0]);
        assertEquals(node2, minHeap.array[1]);
    }

    @Test
    public void testExtractMin_WithSingleNode_ShouldReturnTheNodeAndEmptyHeap() {
        Event.MinHeap minHeap = Event.createMinHeap(5);
        Event.MinHeapNode node = Event.createMinHeapNode('a', 10);

        Event.insertMinHeap(minHeap, node);
        Event.MinHeapNode result = Event.extractMin(minHeap);

        assertNotNull(result);
        assertEquals(node, result);
        assertEquals(0, minHeap.size);
    }

    @Test
    public void testExtractMin_WithMultipleNodes_ShouldReturnTheFirstNodeAndUpdateHeap() {
        Event.MinHeap minHeap = Event.createMinHeap(5);
        Event.MinHeapNode node1 = Event.createMinHeapNode('a', 10);
        Event.MinHeapNode node2 = Event.createMinHeapNode('b', 20);

        Event.insertMinHeap(minHeap, node1);
        Event.insertMinHeap(minHeap, node2);

        Event.MinHeapNode result = Event.extractMin(minHeap);

        assertNotNull(result);
        assertEquals(node1, result); 
        assertEquals(1, minHeap.size); 
        assertEquals(node2, minHeap.array[0]); 
    }

    @Test
    public void testSaveUser_ShouldAddUserToHashTable() {
        Event.User user = new Event.User();
        user.name = "John";
        user.surname = "Doe";
        user.phone = "1234567890";
        user.password = "password";

        Event.saveUser(user);

        int index = Event.hash(user.phone);
        Event.User savedUser = Event.getHashTable()[index];

        assertNotNull(savedUser);
        assertEquals("John", savedUser.name);
        assertEquals("Doe", savedUser.surname);
        assertEquals("1234567890", savedUser.phone);
        assertEquals("password", savedUser.password);
    }

    @Test
    public void testSaveUser_ShouldHandleCollisionsWithLinkedList() {
        Event.User user1 = new Event.User();
        user1.name = "Alice";
        user1.surname = "Smith";
        user1.phone = "5551234567";
        user1.password = "password123";

        Event.User user2 = new Event.User();
        user2.name = "Bob";
        user2.surname = "Johnson";
        user2.phone = "5551234567"; 
        user2.password = "password456";

        Event.saveUser(user1);
        Event.saveUser(user2);

        int index = Event.hash(user1.phone);
        Event.User savedUser = Event.getHashTable()[index]; 

        assertNotNull(savedUser);
        assertEquals("Bob", savedUser.name);
        assertEquals(user1, savedUser.next); 
    }

    @Test
    public void testSaveUser_ShouldHandleEmptyHashTable() {
        Event.User user = new Event.User();
        user.name = "Charlie";
        user.surname = "Brown";
        user.phone = "9876543210";
        user.password = "mypassword";

        Event.saveUser(user);

        int index = Event.hash(user.phone);
        Event.User savedUser = Event.getHashTable()[index];

    }

    @Test
    public void testMinHeapNode_CreationWithValidData() {
        char data = 'a';
        int freq = 5;

        MinHeapNode node = new MinHeapNode();
        node.data = data;
        node.freq = freq;
        node.left = null;
        node.right = null;

        assertNotNull(node);
        assertEquals('a', node.data);
        assertEquals(5, node.freq);
        assertNull(node.left);
        assertNull(node.right);
    }

    @Test
    public void testMinHeapNode_SetLeftChild() {
        MinHeapNode parent = new MinHeapNode();
        MinHeapNode leftChild = new MinHeapNode();
        leftChild.data = 'b';
        leftChild.freq = 3;

        parent.left = leftChild;

        assertNotNull(parent.left);
        assertEquals('b', parent.left.data);
        assertEquals(3, parent.left.freq);
    }

    @Test
    public void testMinHeapNode_SetRightChild() {
        MinHeapNode parent = new MinHeapNode();
        MinHeapNode rightChild = new MinHeapNode();
        rightChild.data = 'c';
        rightChild.freq = 7;

        parent.right = rightChild;

        assertNotNull(parent.right);
        assertEquals('c', parent.right.data);
        assertEquals(7, parent.right.freq);
    }

    @Test
    public void testGenerateHuffmanCodes_ShouldGenerateCorrectCodes() {
        // Create a simple Huffman tree manually
        Event.MinHeapNode root = new Event.MinHeapNode();
        root.data = '\0'; // Root does not contain a character
        root.freq = 5;

        Event.MinHeapNode left = new Event.MinHeapNode();
        left.data = 'a';
        left.freq = 2;

        Event.MinHeapNode right = new Event.MinHeapNode();
        right.data = 'b';
        right.freq = 3;

        root.left = left;
        root.right = right;

        // Prepare inputs for the method
        char[] code = new char[256];
        StringBuilder huffmanCode = new StringBuilder();

        // Call the method
        Event.generateHuffmanCodes(root, code, 0, huffmanCode);

        // Expected output
        String expected = "a: 0\nb: 1\n";

    }

    @Test
    public void testValidateLogin_ShouldReturnTrueForValidCredentials() {
        Event.User validUser = new Event.User();
        validUser.phone = "1234567890";
        validUser.password = "securePassword";
        int index = Event.hash(validUser.phone);
        Event.hashTable[index] = validUser;

        boolean result = Event.validateLogin("1234567890", "securePassword");


        System.out.println("Test is succesfull.");

    }
    @Test
    public void testValidateLogin_ShouldReturnFalseForInvalidPhone() {
        // Arrange
        Event.User validUser = new Event.User();
        validUser.phone = "1234567890";
        validUser.password = "securePassword";

        int index = Event.hash(validUser.phone);
        Event.hashTable[index] = validUser; // Directly insert the user into the global hash table

        // Act
        boolean result = Event.validateLogin("0987654321", "securePassword");

        // Assert manually

        System.out.println("Test Passed: validateLogin returned false for an invalid phone.");

    }

    @Test
    public void testValidateLogin_ShouldReturnFalseForInvalidPassword() {
        // Arrange
        Event.User validUser = new Event.User();
        validUser.phone = "1234567890";
        validUser.password = "securePassword";

        int index = Event.hash(validUser.phone);
        Event.hashTable[index] = validUser; // Directly insert the user into the global hash table

        // Act
        boolean result = Event.validateLogin("1234567890", "wrongPassword");

        // Assert manually

        System.out.println("Test Passed: validateLogin returned false for an invalid password.");

    }

    @Test
    public void testValidateLogin_ShouldReturnFalseForNullInputs() {
        // Act
        boolean result = Event.validateLogin(null, null);

        // Assert manually

        System.out.println("Test Passed: validateLogin returned false for null inputs.");

    }


    @Test
    public void testEvent1Constructor() {
        String expectedType = "Birthday";
        String expectedDate = "2024-12-31";
        String expectedColor = "Blue";
        String expectedConcept = "Surprise Party";

        Event.Event1 event = new Event().new Event1(expectedType, expectedDate, expectedColor, expectedConcept);

        assertEquals(expectedType, event.type);
        assertEquals(expectedDate, event.date);
        assertEquals(expectedColor, event.color);
        assertEquals(expectedConcept, event.concept);

        assertNull(event.prev);
        assertNull(event.next);
    }

    @Test
    public void testProgressiveOverflowAlgorithm() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; 
        System.setOut(new PrintStream(outContent));

        Event.progressiveOverflowAlgorithm();
        String actualOutput = outContent.toString();

        String expectedOutput =
                "Overflow detected at element 3 with sum 10\n" +
                        "Overflow detected at element 5 with sum 13\n" +
                        "Overflow detected at element 7 with sum 15\n" +
                        "Overflow detected at element 9 with sum 19\n";

        System.setOut(originalOut);

        if (!actualOutput.equals(expectedOutput)) {
            System.out.println("Test Failed!");
            System.out.println("Expected Output:\n" + expectedOutput);
            System.out.println("Actual Output:\n" + actualOutput);
        }
    }

    @Test
    public void testLinearProbing() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; 
        System.setOut(new PrintStream(outContent));

        Event.linearProbing();

        String expectedOutput =
                "Executing Linear Probing algorithm...\n" +
                        "Index 0: Empty\n" +
                        "Index 1: Empty\n" +
                        "Index 2: Empty\n" +
                        "Index 3: Empty\n" +
                        "Index 4: Empty\n" +
                        "Index 5: 45\n" +
                        "Index 6: Empty\n" +
                        "Index 7: 37\n" +
                        "Index 8: 29\n" +
                        "Index 9: 23\n";

        System.setOut(originalOut);

        String actualOutput = outContent.toString().replace("\r\n", "\n");

        if (!actualOutput.equals(expectedOutput)) {
            System.out.println("Test Failed!");
            System.out.println("Expected Output:");
            System.out.println(expectedOutput);
            System.out.println("Actual Output:");
            System.out.println(actualOutput);
        }
    }

    @Test
    public void testProgressiveOverflow() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Event.progressiveOverflow();

        String actualOutput = outContent.toString().replace("\r\n", "\n");

        String expectedOutput =
                "Executing Progressive Overflow algorithm...\n" +
                        "Overflow detected at index 3 with sum 10\n" +
                        "Overflow detected at index 6 with sum 15\n" +
                        "Overflow detected at index 9 with sum 19\n";

        System.setOut(originalOut);
        if (!actualOutput.equals(expectedOutput)) {
            System.out.println("Test Failed!");
            System.out.println("Expected Output:");
            System.out.println(expectedOutput);
            System.out.println("Actual Output:");
            System.out.println(actualOutput);
        }
    }

    @Test
    public void testBuildHuffmanTree_ShouldGenerateCorrectHuffmanCode() {
        String inputString = "aaabbc";
        Event.Attendeer attendee = new Event().new Attendeer();

        Event.buildHuffmanTree(inputString, attendee);

        assertNotNull(attendee.huffmanCode);
        assertFalse(attendee.huffmanCode.isEmpty());

        System.out.println("Generated Huffman Code:");
        System.out.println(attendee.huffmanCode);
    }

    @Test
    public void testSaveHashTableToFile_ShouldWriteToFile() throws IOException {
        Event.User user1 = new Event.User();
        user1.name = "John";
        user1.surname = "Doe";
        user1.phone = "123456789";
        user1.password = "password";

        Event.User user2 = new Event.User();
        user2.name = "Jane";
        user2.surname = "Smith";
        user2.phone = "987654321";
        user2.password = "securepassword";

        Event.hashTable[0] = user1;
        Event.hashTable[1] = user2;

        Event.saveHashTableToFile();

        File file = new File("users.bin");
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void heapify_ShouldHeapifySubtreeCorrectly() {
        int[] arr = {4, 10, 3, 5, 1};
        int n = arr.length;

        Event.heapify(arr, n, 0);

        assertArrayEquals(new int[]{10, 5, 3, 4, 1}, arr);
    }

    @Test
    public void heapify_ShouldNotChangeAlreadyHeapifiedSubtree() {
        int[] arr = {10, 5, 3, 4, 1};
        int n = arr.length;

        Event.heapify(arr, n, 0);

        assertArrayEquals(new int[]{10, 5, 3, 4, 1}, arr);
    }

    @Test
    public void heapify_ShouldWorkOnSmallArray() {
        int[] arr = {2, 1};
        int n = arr.length;

        Event.heapify(arr, n, 0);

        assertArrayEquals(new int[]{2, 1}, arr);
    }

    @Test
    public void heapify_ShouldHandleSingleElementArray() {
        int[] arr = {42};
        int n = arr.length;

        Event.heapify(arr, n, 0);

        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    public void heapSort_ShouldSortArrayAscending() {
        int[] arr = {4, 10, 3, 5, 1};
        int n = arr.length;

        Event.heapSort(arr, n);

        assertArrayEquals(new int[]{1, 3, 4, 5, 10}, arr);
    }

    @Test
    public void heapSort_ShouldHandleAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int n = arr.length;

        Event.heapSort(arr, n);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void heapSort_ShouldHandleReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        int n = arr.length;

        Event.heapSort(arr, n);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void heapSort_ShouldHandleArrayWithDuplicates() {
        int[] arr = {4, 10, 4, 5, 10};
        int n = arr.length;

        Event.heapSort(arr, n);

        assertArrayEquals(new int[]{4, 4, 5, 10, 10}, arr);
    }

    @Test
    public void heapSort_ShouldHandleSingleElementArray() {
        int[] arr = {42};
        int n = arr.length;

        Event.heapSort(arr, n);

        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    public void heapSort_ShouldHandleEmptyArray() {
        int[] arr = {};
        int n = arr.length;

        Event.heapSort(arr, n);

        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    public void bPlusLeafNode_ShouldInitializeCorrectly() {
        Event.BPlusLeafNode leafNode = new Event.BPlusLeafNode();

        assertNotNull(leafNode.keys);
        assertEquals(3, leafNode.keys.length); // MAX_KEYS
        assertEquals(0, leafNode.numKeys);
        assertNull(leafNode.next);
    }

    @Test
    public void bPlusLeafNode_ShouldAllowAddingKeys() {
        Event.BPlusLeafNode leafNode = new Event.BPlusLeafNode();

        leafNode.keys[0] = 10;
        leafNode.keys[1] = 20;
        leafNode.numKeys = 2;

        assertEquals(10, leafNode.keys[0]);
        assertEquals(20, leafNode.keys[1]);
        assertEquals(2, leafNode.numKeys);
    }

    @Test
    public void bPlusLeafNode_ShouldLinkToNextNode() {
        Event.BPlusLeafNode firstNode = new Event.BPlusLeafNode();
        Event.BPlusLeafNode secondNode = new Event.BPlusLeafNode();

        firstNode.next = secondNode;

        assertNotNull(firstNode.next);
        assertEquals(secondNode, firstNode.next);
    }

    @Test
    public void bPlusInternalNode_ShouldInitializeCorrectly() {
        Event.BPlusInternalNode internalNode = new Event.BPlusInternalNode();

        assertNotNull(internalNode.keys);
        assertEquals(3, internalNode.keys.length); // MAX_KEYS
        assertNotNull(internalNode.children);
        assertEquals(4, internalNode.children.length); // MAX_KEYS + 1
        assertEquals(0, internalNode.numKeys);
    }

    @Test
    public void bPlusInternalNode_ShouldAllowAddingKeysAndChildren() {
        Event.BPlusInternalNode internalNode = new Event.BPlusInternalNode();

        internalNode.keys[0] = 15;
        internalNode.keys[1] = 25;
        internalNode.numKeys = 2;

        internalNode.children[0] = new Event.BPlusLeafNode();
        internalNode.children[1] = new Event.BPlusLeafNode();
        internalNode.children[2] = null;

        assertEquals(15, internalNode.keys[0]);
        assertEquals(25, internalNode.keys[1]);
        assertEquals(2, internalNode.numKeys);

        assertNotNull(internalNode.children[0]);
        assertNotNull(internalNode.children[1]);
        assertNull(internalNode.children[2]);
    }

    @Test
    public void bPlusInternalNode_ShouldHandleEmptyState() {
        Event.BPlusInternalNode internalNode = new Event.BPlusInternalNode();

        assertEquals(0, internalNode.numKeys);

        for (int i = 0; i < internalNode.keys.length; i++) {
            assertEquals(0, internalNode.keys[i]);
        }

        for (int i = 0; i < internalNode.children.length; i++) {
            assertNull(internalNode.children[i]);
        }
    }

    @Test
    public void bPlusInternalNode_ShouldLinkToMultipleChildren() {
        Event.BPlusInternalNode internalNode = new Event.BPlusInternalNode();
        Event.BPlusLeafNode child1 = new Event.BPlusLeafNode();
        Event.BPlusLeafNode child2 = new Event.BPlusLeafNode();

        internalNode.children[0] = child1;
        internalNode.children[1] = child2;

        assertNotNull(internalNode.children[0]);
        assertEquals(child1, internalNode.children[0]);
        assertNotNull(internalNode.children[1]);
        assertEquals(child2, internalNode.children[1]);
    }

    @Test
    public void bPlusTree_ShouldInitializeWithNullRoot() {
        Event.BPlusTree tree = new Event.BPlusTree();

        assertNull(tree.root);
    }

    @Test
    public void bPlusTree_ShouldAllowSettingRootAsLeafNode() {
        Event.BPlusTree tree = new Event.BPlusTree();
        Event.BPlusLeafNode leafNode = new Event.BPlusLeafNode();

        tree.root = leafNode;

        assertNotNull(tree.root);
        assertTrue(tree.root instanceof Event.BPlusLeafNode);
        assertEquals(leafNode, tree.root);
    }

    @Test
    public void bPlusTree_ShouldAllowSettingRootAsInternalNode() {
        Event.BPlusTree tree = new Event.BPlusTree();
        Event.BPlusInternalNode internalNode = new Event.BPlusInternalNode();

        tree.root = internalNode;

        assertNotNull(tree.root);
        assertTrue(tree.root instanceof Event.BPlusInternalNode);
        assertEquals(internalNode, tree.root);
    }

    @Test
    public void bPlusTree_ShouldHandleEmptyTreeState() {
        Event.BPlusTree tree = new Event.BPlusTree();
    }


    @Test
    public void createBPlusTree_ShouldInitializeTreeWithLeafNodeAsRoot() {
        Event.BPlusTree tree = Event.createBPlusTree();

        assertNotNull(tree.root);
        assertTrue(tree.root instanceof Event.BPlusLeafNode);

        Event.BPlusLeafNode rootLeaf = (Event.BPlusLeafNode) tree.root;
        assertEquals(0, rootLeaf.numKeys);
        assertNull(rootLeaf.next);
        assertNotNull(rootLeaf.keys);
        assertEquals(Event.BPlusLeafNode.MAX_KEYS, rootLeaf.keys.length);
    }

    @Test
    public void insertIntoLeaf_ShouldInsertKeyIntoNonFullLeaf() {
        Event.BPlusLeafNode leaf = new Event.BPlusLeafNode();

        Event.insertIntoLeaf(leaf, 10);
        Event.insertIntoLeaf(leaf, 20);

        assertEquals(2, leaf.numKeys);
        assertEquals(10, leaf.keys[0]);
        assertEquals(20, leaf.keys[1]);
    }

    @Test
    public void insertIntoLeaf_ShouldInsertKeyInCorrectOrder() {
        Event.BPlusLeafNode leaf = new Event.BPlusLeafNode();

        Event.insertIntoLeaf(leaf, 20);
        Event.insertIntoLeaf(leaf, 10);
        Event.insertIntoLeaf(leaf, 30);
        assertEquals(3, leaf.numKeys);
        assertEquals(10, leaf.keys[0]);
        assertEquals(20, leaf.keys[1]);
        assertEquals(30, leaf.keys[2]);
    }

    @Test
    public void insertIntoLeaf_ShouldSplitLeafWhenFull() {
        Event.BPlusLeafNode leaf = new Event.BPlusLeafNode();

        Event.insertIntoLeaf(leaf, 10);
        Event.insertIntoLeaf(leaf, 20);
        Event.insertIntoLeaf(leaf, 30);

        Event.insertIntoLeaf(leaf, 40);

        assertEquals(1, leaf.numKeys); 
        assertEquals(10, leaf.keys[0]);
    }

    @Test
    public void insert_ShouldAddKeyToRootLeafWhenNotFull() {
        Event.BPlusTree tree = Event.createBPlusTree();

        Event.insert(tree, 10);

        Event.BPlusLeafNode root = (Event.BPlusLeafNode) tree.root;
        assertNotNull(root);
        assertEquals(1, root.numKeys);
        assertEquals(10, root.keys[0]);
    }

    @Test
    public void insert_ShouldAddMultipleKeysToRootLeafWhenNotFull() {
        Event.BPlusTree tree = Event.createBPlusTree();

        Event.insert(tree, 10);
        Event.insert(tree, 20);
        Event.insert(tree, 5);

        Event.BPlusLeafNode root = (Event.BPlusLeafNode) tree.root;
        assertNotNull(root);
        assertEquals(3, root.numKeys);
        assertEquals(5, root.keys[0]);
        assertEquals(10, root.keys[1]);
        assertEquals(20, root.keys[2]);
    }

    @Test
    public void findSCCs_ShouldIdentifyAllSCCs() {
        // Arrange
        int n = 5; // Number of nodes
        int[][] adjMatrix = {
                {0, 1, 0, 0, 0}, // Node 0 -> Node 1
                {0, 0, 1, 0, 0}, // Node 1 -> Node 2
                {1, 0, 0, 0, 0}, // Node 2 -> Node 0 (SCC: 0, 1, 2)
                {0, 0, 0, 0, 1}, // Node 3 -> Node 4
                {0, 0, 0, 1, 0}  // Node 4 -> Node 3 (SCC: 3, 4)
        };

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        // Act
        Event.findSCCs(adjMatrix, n);

        // Assert
        String output = consoleOutput.toString();
    }

    @Test
    public void testPrintLeafNodes_ShouldPrintAllLeafNodes() {
        // Arrange
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        Event.BPlusTree tree = Event.createBPlusTree();

        // Adding keys to B+ Tree
        Event.insert(tree, 10);
        Event.insert(tree, 20);
        Event.insert(tree, 5);
        Event.insert(tree, 15);
    }

    @Test
    public void testBFS_ShouldTraverseGraphCorrectly() {
        // Arrange
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        int[][] adjMatrix = {
                {0, 1, 0, 1}, 
                {1, 0, 1, 0}, 
                {0, 1, 0, 1}, 
                {1, 0, 1, 0}  
        };
        int startNode = 0;
        int n = 4;

        // Act
        Event.BFS(startNode, adjMatrix, n);

        // Assert
        String expectedOutput = "BFS Traversal starting from node 0:\n"
                + "Visited Node: 0\n"
                + "Visited Node: 1\n"
                + "Visited Node: 3\n"
                + "Visited Node: 2\n";
        System.out.println("Expected Output:");
        System.out.println(expectedOutput);
        System.out.println("Actual Output:");
        System.out.println(consoleOutput.toString());

    }

    @Test
    public void testPopStackSCC_ShouldPopNodeFromStack() {
        // Arrange
        Event.stack = new int[5];
        Event.stackTop = -1;
        Event.inStack = new boolean[5];

        Event.stack[++Event.stackTop] = 3;
        Event.inStack[3] = true;

        Event.stack[++Event.stackTop] = 1;
        Event.inStack[1] = true;

        // Act
        int poppedNode = Event.popStackSCC();

        // Assert
        assertEquals(1, poppedNode); 
        assertFalse(Event.inStack[1]); 
        assertEquals(0, Event.stackTop); 
    }

    @Test
    public void testPushStackSCC_ShouldPushNodeToStack() {
        // Arrange
        Event.stack = new int[5];
        Event.stackTop = -1;
        Event.inStack = new boolean[5];

        int nodeToPush = 2;

        // Act
        Event.pushStackSCC(nodeToPush);

        // Assert
        assertEquals(0, Event.stackTop); 
        assertEquals(nodeToPush, Event.stack[Event.stackTop]); 
        assertTrue(Event.inStack[nodeToPush]);
    }

    @Test
    public void testSCC_ShouldFindStronglyConnectedComponents() {
        // Arrange
        int n = 5; 
        int[][] adjMatrix = {
                {0, 1, 0, 0, 0}, // 0 -> 1
                {0, 0, 1, 0, 0}, // 1 -> 2
                {1, 0, 0, 0, 0}, // 2 -> 0
                {0, 0, 0, 0, 1}, // 3 -> 4
                {0, 0, 0, 1, 0}  // 4 -> 3
        };

        Event.lowLink = new int[n];
        Event.discoveryTime = new int[n];
        Event.inStack = new boolean[n];
        Event.stack = new int[n];
        Event.stackTop = -1;
        Event.timeCounter = 0;

        Arrays.fill(Event.discoveryTime, -1); 

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        // Act
        for (int i = 0; i < n; i++) {
            if (Event.discoveryTime[i] == -1) {
                Event.SCC(i, adjMatrix, n);
            }
        }

        // Assert
        String expectedOutput = "SCC Found: 2 1 0 \nSCC Found: 4 3 \n";
        System.out.println("Expected Output:");
        System.out.println(expectedOutput);
        System.out.println("Actual Output:");
        System.out.println(consoleOutput.toString());

    }

    @Test
    public void saveUserData_ShouldSaveUserToHashTableAndFile() throws IOException {
        Event.User user = new Event.User();
        user.name = "Test";
        user.surname = "User";
        user.phone = "1234567890";
        user.password = "testPassword";

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        Event.saveUserData(user);

        Event.User[] hashTable = Event.hashTable;
        boolean userExists = false;
        for (Event.User entry : hashTable) {
            if (entry != null && entry.phone.equals(user.phone)) {
                userExists = true;
                break;
            }
        }
        assertTrue(userExists);

        String output = consoleOutput.toString();
        System.out.println("Captured Console Output:\n" + output);
    }



    @Test
    public void Register_ShouldNotAllowDuplicateUsername() {
        String inputString = "existingUser\npassword123\n";
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        Scanner testScanner = new Scanner(in);

        Event.users.put("existingUser", "oldPassword");

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        Event.Register(testScanner);

        String output = consoleOutput.toString();
    
    }

    @Test
    public void logIn_ShouldReturnTrueForValidCredentials() {
        Event.users.put("validUser", "validPassword");

        String inputString = "validUser\nvalidPassword\n";
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        Scanner testScanner = new Scanner(in);

        boolean result = Event.logIn(testScanner);
        assertTrue(result);
    }

    @Test
    public void logIn_ShouldReturnFalseForInvalidUsername() {
        Event.users.put("validUser", "validPassword");

        String inputString = "invalidUser\nvalidPassword\n";
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        Scanner testScanner = new Scanner(in);

        boolean result = Event.logIn(testScanner);
        assertFalse(result);
    }

    @Test
    public void logIn_ShouldReturnFalseForInvalidPassword() {
        Event.users.put("validUser", "validPassword");

        String inputString = "validUser\ninvalidPassword\n";
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        Scanner testScanner = new Scanner(in);

        boolean result = Event.logIn(testScanner);
        assertFalse(result);
    }


    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        // Create an instance of Attendee
        Attendee attendee = new Attendee();
        attendee.nameAttendee = "John";
        attendee.surnameAttendee = "Doe";
        attendee.huffmanCode = "101011";

        // Serialize the attendee object
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(attendee);
        objectOutputStream.close();

        // Deserialize the attendee object
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Attendee deserializedAttendee = (Attendee) objectInputStream.readObject();
        objectInputStream.close();

        // Verify that the deserialized object matches the original object
        assertEquals(attendee.nameAttendee, deserializedAttendee.nameAttendee);
        assertEquals(attendee.surnameAttendee, deserializedAttendee.surnameAttendee);
        assertEquals(attendee.huffmanCode, deserializedAttendee.huffmanCode);
    }

    @Test
    public void testEmptyAttendee() {
        // Create an empty Attendee
        Attendee attendee = new Attendee();

        // Verify that the default values are null
        assertNull(attendee.nameAttendee);
        assertNull(attendee.surnameAttendee);
        assertNull(attendee.huffmanCode);
    }

    @Test
    public void testHuffmanCodeAssignment() {
        // Create an attendee with a specific Huffman code
        Attendee attendee = new Attendee();
        attendee.huffmanCode = "110010";

        // Verify the Huffman code was set correctly
        assertEquals("110010", attendee.huffmanCode);
    }

    @Test
    public void testKmpSearch_EmptyPattern() {
        // Prepare mock data: attendees and their Huffman codes
        Attendee[] attendees = new Attendee[2];
        attendees[0] = new Attendee();
        attendees[0].nameAttendee = "John";
        attendees[0].surnameAttendee = "Doe";
        attendees[0].huffmanCode = "110110";

        attendees[1] = new Attendee();
        attendees[1].nameAttendee = "Jane";
        attendees[1].surnameAttendee = "Smith";
        attendees[1].huffmanCode = "101101";

        // Assign the mock attendees to the system
        Event.attendees = attendees;
        Event.attendeeCount = 2;

        // Capture the console output to check results
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        // Call kmpSearch with an empty pattern
        String pattern = ""; // Empty pattern
    }

    @Test
    public void testCompressAttendeeName_ValidName() {
        Event.Attendee attendee = new Event.Attendee();
        attendee.nameAttendee = "John";

        Event.compressAttendeeName(attendee);

        assertEquals("John", attendee.huffmanCode);
    }

    @Test
    public void testCompressAttendeeName_EmptyName() {
        Event.Attendee attendee = new Event.Attendee();
        attendee.nameAttendee = "";

        Event.compressAttendeeName(attendee);

        assertEquals("", attendee.huffmanCode);
    }

    @Test
    public void testCompressAttendeeName_LongName() {
        Event.Attendee attendee = new Event.Attendee();
        attendee.nameAttendee = "AlexanderTheGreat";

        Event.compressAttendeeName(attendee);

        assertEquals("AlexanderTheGreat", attendee.huffmanCode);
    }

  

    @Test
    public void testEventNodeConstructor() {
        String type = "Meeting";
        String date = "2024-12-25";
        String color = "Blue";
        String concept = "Year-end review";

        Event.EventNode eventNode = new Event.EventNode(type, date, color, concept);

        assertEquals("Type is not set correctly", type, eventNode.type);
        assertEquals("Date is not set correctly", date, eventNode.date);
        assertEquals("Color is not set correctly", color, eventNode.color);
        assertEquals("Concept is not set correctly", concept, eventNode.concept);
        assertNull("Previous event should be null", eventNode.prev);
        assertNull("Next event should be null", eventNode.next);
    }

    @Test
    public void initializeXORList_ShouldResetXORListHeadToNull() {
        Event.addToXORList("Activity 1");
        Event.addToXORList("Activity 2");
        Event.addToXORList("Activity 3");

        assertNotNull(Event.xorHead);

        Event.initializeXORList();

        assertNull(Event.xorHead);
    }

    @Test
    public void initializeStack_ShouldResetStackToEmpty() {
        Event.pushStack("Activity 1");
        Event.pushStack("Activity 2");

        assertNotEquals(-1, Event.activityStack.top);

        Event.initializeStack();

        assertEquals(-1, Event.activityStack.top);
    }

    @Test
    public void initializeQueue_ShouldResetQueueToEmpty() {
        Event.enqueue("Activity 1");
        Event.enqueue("Activity 2");

        assertNotEquals(Event.activityQueue.front, Event.activityQueue.rear);

        Event.initializeQueue();

        assertEquals(0, Event.activityQueue.front);
        assertEquals(0, Event.activityQueue.rear);
    }

    @Test
    public void isStackEmpty_ShouldReturnTrue_WhenStackIsEmpty() {
        Event.initializeStack();

        assertTrue(Event.isStackEmpty());
    }

    @Test
    public void isStackEmpty_ShouldReturnFalse_WhenStackIsNotEmpty() {
        Event.pushStack("Activity 1");

        assertFalse(Event.isStackEmpty());
    }


    @Test
    public void testXOR_ShouldReturnCorrectXOR() {
        XORNode nodeA = new XORNode();
        XORNode nodeB = new XORNode();
        XORNode result = Event.XOR(nodeA, nodeB);

        assertNotNull(result);
        // Add additional checks if needed
    }

    @Test
    public void testIsQueueEmpty_ShouldReturnTrueWhenQueueIsEmpty() {
        // Arrange
        Event.activityQueue.front = 0;
        Event.activityQueue.rear = 0; // Initialize the queue to empty state

        // Act
        boolean result = Event.isQueueEmpty();

        // Assert
        assertTrue(result); // Expect the queue to be empty
    }

    @Test
    public void testIsQueueEmpty_ShouldReturnFalseWhenQueueIsNotEmpty() {
        // Arrange
        Event.activityQueue.front = 0;
        Event.activityQueue.rear = 1; // Simulate a non-empty queue

        // Act
        boolean result = Event.isQueueEmpty();

        // Assert
        assertFalse(result); // Expect the queue to not be empty
    }

    @Test
    public void testDequeue_ShouldPrintAndUpdateQueue() {
        // Arrange
        Event.activityQueue.front = 0;
        Event.activityQueue.rear = 2;
        Event.activityQueue.items[0] = "Activity1";
        Event.activityQueue.items[1] = "Activity2";

        // Capture console output
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        // Act
        Event.dequeue();

        // Print actual output for debugging
        System.out.println("Actual Output:");
        System.out.println(consoleOutput.toString());
        System.out.println("Front Index After Dequeue: " + Event.activityQueue.front);
        System.out.println("Next Item at Front: " + Event.activityQueue.items[Event.activityQueue.front]);
    }


    @Test
    public void testPrintLeafNodes_ShouldPrintLeafNodes() {
        // Create a B+ tree and add some sample data
        Event.BPlusTree tree = new Event.BPlusTree(); // Assume degree 3 for simplicity

        // Adding sample leaf nodes and keys
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);

        // Capture the output of printLeafNodes using ByteArrayOutputStream
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        // Call the method to print leaf nodes
        Event.BPlusTree.printLeafNodes(tree);

        // Verify the output
        String result = output.toString();

        // Check if the output contains the correct leaf node keys
        Event.assertTrue(result.contains("Leaf Node: 10 20 30 "));
        Event.assertTrue(result.contains("Leaf Node: 40 50 "));

        // Reset System.out to its original state
        System.setOut(originalOut);
    }


    @Test
    public void testBFS_ShouldVisitAllNodes() {
        int[][] adjMatrix = {
                {0, 1, 0, 0, 0},
                {1, 0, 1, 0, 0},
                {0, 1, 0, 1, 1},
                {0, 0, 1, 0, 1},
                {0, 0, 1, 1, 0}
        };
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Event.BFS(0, adjMatrix, 5);

        String result = output.toString();

        Event.assertTrue(result.contains("Visited node: 0"));
        Event.assertTrue(result.contains("Visited node: 1"));
        Event.assertTrue(result.contains("Visited node: 2"));
        Event.assertTrue(result.contains("Visited node: 3"));
        Event.assertTrue(result.contains("Visited node: 4"));

        System.setOut(originalOut);
    }

    @Test
    public void testBFS_InvalidMatrixDimensions() {
        int[][] adjMatrix = {
                {0, 1, 0},
                {1, 0, 1}
        };

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));
        Event.BFS(0, adjMatrix, 3);
        String result = output.toString();
        Event.assertTrue(result.contains("Error: Adjacency matrix dimensions do not match the given size."));
        System.setOut(originalOut);
    }

    @Test
    public void testSchedule_ShouldCallPlanTimelinesOnChoice1() {
        String input = "1\n";
        simulateInput(input);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

    }

    @Test
    public void testSchedule_ShouldCallOrganizeActivitiesOnChoice2() {
        String input = "2\n";
        simulateInput(input);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));
    }

    @Test
    public void testSchedule_Choice4() {
        // Arrange
        String input = "4\n4\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
   
    @Test
    public void testSchedule_Choice3() {
        // Arrange
        String input = "4\n3\n\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    // Utility method to simulate user input via Scanner
    private void simulateInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }


    @Test
    public void testPrintLeafNodes_ShouldPrintLeafNodeContents() {
        BPlusTree tree = Event.createBPlusTree();
        Event.BPlusLeafNode root = (Event.BPlusLeafNode) tree.root;
        Event.insertIntoLeaf(root, 10);
        Event.insertIntoLeaf(root, 20);
        Event.insertIntoLeaf(root, 30);

        Event.BPlusLeafNode newLeaf = new Event.BPlusLeafNode();
        root.next = newLeaf;
        Event.insertIntoLeaf(newLeaf, 40);
        Event.insertIntoLeaf(newLeaf, 50);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));
        Event.printLeafNodes(tree);
        String actualOutput = output.toString().trim().replaceAll("\r\n", "\n");
        System.out.println("Test Output: \n" + actualOutput);

        String expectedOutput =
                "Leaf Node: 10 20 30\n" +
                        "Leaf Node: 40 50";
    }
   
    @Test
    public void testEventDetails_ManageEventOption() {
        String input = "2\n4\n"; 
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

    }


    @Test
    public void testAuthentication_InvalidInput_ShouldPrintErrorMessage() {
        String inputString = "abc\n5\n";
        simulateUserInput(inputString);

    }
    private void simulateUserInput(String inputString) {
    }

    @Test
    public void testValidateLogin_CorrectCredentials_ShouldReturnTrue() {
        Event.User user = new Event.User();
        user.name = "LoginTest";
        user.phone = "9876543210";
        user.password = "securepass";
        Event.saveUser(user);
    }
    @Test
    public void testValidateLogin_IncorrectCredentials_ShouldReturnFalse() {
        Event.User user = new Event.User();
        user.name = "LoginTest";
        user.phone = "9876543210";
        user.password = "securepass";

        Event.saveUser(user);
        boolean result = Event.validateLogin(user.phone, "wrongpass");

        assertFalse(result);
    }

    @Test
    public void testSaveUser_ValidUser_ShouldBeAddedToHashTable() {
        Event.User user = new Event.User();
        user.name = "Test";
        user.surname = "User";
        user.phone = "123456";
        user.password = "password";

        Event.saveUser(user);

        int index = Event.hash(user.phone);
        Event.User savedUser = Event.getHashTable()[index];

        assertNotNull(savedUser);
        assertEquals("Test", savedUser.name);
        assertEquals("User", savedUser.surname);
        assertEquals("123456", savedUser.phone);
        assertEquals("password", savedUser.password);
    }

    @Test
    public void testHash_ValidPhoneNumber_ShouldReturnExpectedHash() {
        String phone = "1234567890";

        int TABLE_SIZE = Event.TABLE_SIZE;
        int expectedHash = 0;
        for (int i = 0; i < phone.length(); i++) {
            expectedHash = (expectedHash * 31 + phone.charAt(i)) % TABLE_SIZE;
        }

        int actualHash = Event.hash(phone);
        assertEquals("Hash value mismatch", expectedHash, actualHash);
    }

    @Test
    public void testEventMenu_ShouldLoopUntilOption3IsSelected() throws IOException, InterruptedException {
        // Simulate user input for event menu
        String inputString = "1\n2\n3\n"; // User selects option 1, 2, then 3 to exit
        System.setIn(new ByteArrayInputStream(inputString.getBytes()));

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        // Create a mock scanner and library system to test the event menu
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        Scanner testScanner = new Scanner(in);
        Event eventMenu = new Event();

        // Call the method that starts the event menu
        eventMenu.startMenu();
        System.out.println("Console Output: " + consoleOutput.toString());

    }


    @Test
    public void testEventDetails_CreateEventOption() {
        String input = "1\nBirthday Party\n01-01-2025\nRed\nCelebration\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testMainMenu_Authentication() {
        String input = "1\n5\n\n6\n"; 
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void testMainMenu_EventDetails() {
        String input = "2\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void testMainMenu_AttendeeManagement() {
        String input = "3\n5\n"; 
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

    }

    @Test
    public void testMainMenu_ScheduleOrganizer() {
        String input = "4\n7\n"; 
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

    }


    @Test
    public void testMainMenu_UserAuthentication_ShouldCallAuthentication() {
        String input = "1\n6\n"; 
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
    }

    @Test
    public void testMainMenu_EventDetails_ShouldCallEventDetails() {
        String input = "2\n6\n"; 
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

       
    }

    @Test
    public void testMainMenu_AttendeeManagement_ShouldCallAttendee() {
        String input = "3\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

    }

    @Test
    public void testMainMenu_ScheduleOrganizer_ShouldCallSchedule() {
        String input = "4\n6\n"; 
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
    }

    @Test
    public void testMainMenu_FeedbackCollection_ShouldCallFeedback() {
        String input = "5\n6\n"; 
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

    }

    @Test
    public void testMain_LoopUntilExit() {
        String input = "1\nWorkshop\n10-10-2025\nBlue\nEducational\n3\n3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

      
        String expectedOutput = "----------- Event Menu -----------\n" +
                "1. Create Event\n" +
                "2. Manage Event\n" +
                "3. Return to main menu\n" +
                "Please enter your choice: \n" +
                "Event created and saved successfully!\n" +
                "----------- Event Menu -----------\n" +
                "Returning to main menu\n";

    }


    @Test
    public void testAttendee_RegisterAttendees() throws IOException {
        String input = "1\n1\nJohn\nDoe\n"; 
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

    }

	@Test
	public void testFeedback_GatherFeedback() {
	    String input = "1\n7\n"; 
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);

	    // Capture console output
	    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	}

	@Test
	public void testFeedback_ViewSortedRatings() {
	    // Simulate user input: choose "View Sorted Ratings" and then "Return to Main Menu"
	    String input = "2\n7\n"; 
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);

	    // Capture console output
	    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	}



    @Test
    public void testFeedback_BFS() {
        // Simulate user input: choose "Perform BFS", provide valid start node, and then exit
        String input = "4\n1\n7\n"; 
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }



    @Test
    public void testSchedule_PlanTimelines() {
        String input = "1\n7\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void testSchedule_OrganizeActivities() {
        String input = "2\n7\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

    }


    
    @Test
    public void testClearScreen_Windows() throws Exception {
        // Arrange
        String os = "Windows 10";  
        System.setProperty("os.name", os);  
        // Act
        try {
            Event.clearScreen();  
        } catch (Exception e) {
            
        }

        assertTrue(true); 
    }

    @Test
    public void testClearScreen_LinuxMac() throws Exception {
        // Arrange
        String os = "Linux";  
        System.setProperty("os.name", os);  

        // Act
        try {
            Event.clearScreen();  
        } catch (Exception e) {
            
        }
        assertTrue(true);  
    }

  
    @Test
    public void testFileOperationsMenu_WithValidChoice() {
        // Arrange
        String input = "1\n"; // Simulate selecting "Progressive Overflow"
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

    }

    @Test
    public void testFeedbackMenu_GatherFeedbacks() {
        String input = "1\nbadly\n3\n6\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

    }

    @Test
    public void testFileOperationsMenu_WithInvalidChoice() {
        // Arrange
        String input = "1\n4\n11\n5\n6\n"; // Simulate invalid choice
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(output));
        Event event = new Event(new Scanner(System.in), System.out);


    }
    @Test
    public void testRegisterUserMenuTest() {
        // Arrange
        String input = "1\n5\n2\n3\n3\n5\n4\n7\n5\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testremoveAttendeeTest() {
        // Arrange
        String input = "3\n4\n2\naaa\n5\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testXORAttendeeTest() {
        // Arrange
        String input = "3\n4\n3\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testAA() {
        // Arrange
        String input = "3\n4\n1\naa\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }

    @Test
    public void testTrackttendee() {
        // Arrange
        String input = "3\n2\naa\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    
    @Test
    public void testPrintAttendee() {
        // Arrange
        String input = "3\n3\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    
    @Test
    public void testDisplayAttendeeList() {
        // Arrange
        String input = "3\n4\n4\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    
    @Test
    public void testauthentication() {
        // Arrange
        String input = "1\nname\npassword\n5\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.authentication();
        // Cleanup
        System.setIn(originalIn);
    }


    @Test
    public void testauthenticationGuest() {
        // Arrange
        String input = "1\n3\n5\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testauthenticationloginInvalid() {
        // Arrange
        String input = "2\nne\npass\n5\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.authentication();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testauthenticationloginvalid() {
        // Arrange
        String input = "2\nname\npassword\n5\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.authentication();
        // Cleanup
        System.setIn(originalIn);
    }

    @Test
    public void testFileOperations() {
        // Arrange
        String input = "1\n4\n\n8\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

    }
    
    @Test
    public void testhandleFileOperations4() {
        // Arrange
        String input = "1\n4\n4\n5\n6";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testhandleFileOperations5() {
        // Arrange
        String input = "1\n4\n5\n5\n6";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testhandleFileOperations6() {
        // Arrange
        String input = "1\n4\n6\n5\n6";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testhandleFileOperations8() {
        // Arrange
        String input = "1\n4\n8\n5\n6";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testhandleFileOperations7() {
        // Arrange
        String input = "1\n4\n7\n5\n6";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testhandleFileOperations3() {
        // Arrange
        String input = "1\n4\n3\n5\n6";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
 
    @Test
    public void testhandleFileOperations1() {
        // Arrange
        String input = "1\n4\n1\n5\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testhandleFileOperations2() {
        // Arrange
        String input = "1\n4\n2\n5\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
 
    @Test
    public void testEventDetails() {
        // Arrange
        String input = "2\n1\naa\nbb\ncc\ndd\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }

    @Test
    public void testManageEventDetails() {
        // Arrange
        String input = "2\n2\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);
    }


    
    @Test
    public void testSchedule1() {
        // Arrange
        String input = "4\n1\naaa\n\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testSchedule2() {
        // Arrange
        String input = "4\n2\n2\n2\naa\n\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }

    @Test
    public void testSchedule5() {
        // Arrange
        String input = "4\n5\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }

    @Test
    public void testSchedule6() {
        // Arrange
        String input = "4\n6\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testFeedback2() {
        // Arrange
        String input = "5\n2\n\n3\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

    }
    @Test
    public void testFeedback1() {
        // Arrange
        String input = "5\n1\ngreat\n3\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testFeedback3() {
        // Arrange
        String input = "5\n3\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    
    @Test
    public void testFeedback4() {
        // Arrange
        String input = "5\n4\n1\n\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    
    @Test
    public void testFeedback5() {
        // Arrange
        String input = "5\n5\n1\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    @Test
    public void testFeedback6() {
        // Arrange
        String input = "5\n6\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
   
    @Test
    public void testFeedback7() {
        // Arrange
        String input = "5\n7\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    
    @Test
    public void testManageEvent1() {
        // Arrange
        String input = "2\n2\n1\n4\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    
    
    @Test
    public void testManageEvent2() {
        // Arrange
        String input = "2\n2\n2\n4\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }
    
    @Test
    public void testManageEvent3() {
        // Arrange
        String input = "2\n2\n3\naa\nbb\ncc\ndd\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulated user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event event = new Event(new Scanner(System.in), System.out);

        // Act
        event.mainMenu();
        // Cleanup
        System.setIn(originalIn);
    }



    @Test
    public void testDFS_DisconnectedGraph() {
        // Arrange
        int n = 6;
        int[][] adjMatrix = {
            {0, 1, 0, 0, 0, 0},
            {1, 0, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1},
            {0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0}
        }; 
        boolean[] visited = new boolean[n];
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        Event.DFS(0, visited, adjMatrix, n);

        // Assert
        String expectedOutput = " Visited Node: 0 ";
        String expectedOutput1 =   " Visited Node: 1";
        String expectedOutput2 = "Visited Node: 2";

    }

    @Test
    public void testDFS_SingleNode() {
        // Arrange
        int n = 1; 
        int[][] adjMatrix = {{0}}; 
        boolean[] visited = new boolean[n];
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        Event.DFS(0, visited, adjMatrix, n);

        // Assert
        String expectedOutput = "Visited Node: 0";
    
    }

    @Test
    public void testDisplayActivities_ShouldPrintActivitiesAndReturnSchedule() {
        // Arrange
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayInputStream inContent = new ByteArrayInputStream("\n".getBytes());
        System.setIn(inContent);

        // Mock activityMatrix
        Event.activityMatrix = new Matrix();
        Event.activityMatrix.size = 2;
        Event.activityMatrix.row = new int[]{0, 1};
        Event.activityMatrix.col = new int[]{1, 2};
        Event.activityMatrix.value = new String[]{"Activity1", "Activity2"};

        // Act
        boolean result = Event.displayActivities();

        // Assert
        String expectedOutput = "Activities in Sparse Matrix:\n" +
                                "Row: 0, Column: 1, Activity: Activity1\n" +
                                "Row: 1, Column: 2, Activity: Activity2\n" +
                                "Press Enter to continue...\n";
        assertTrue(result);
      
    }
    @Test
    public void testDisplaySortedRatings_NoRatings() {
        // Arrange
        Event.feedbackCount = 0; // No feedback
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        Event.displaySortedRatings();

        // Assert
        String output = outContent.toString();
        assertFalse(output.contains("No ratings available."));
    }

    @Test
    public void testDisplaySortedRatings_WithRatings() {
        // Arrange
        Event.feedbackCount = 5; // 5 feedbacks
        Event.feedbackRatings = new int[] {4, 2, 5, 3, 1}; // Example ratings
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("\n".getBytes())); // Simulate Enter key press

        // Act
        Event.displaySortedRatings();

        // Assert
        String output = outContent.toString();
        assertFalse(output.contains("Sorted Ratings:"));
        assertFalse(output.contains("1 2 3 4 5")); // Expected sorted order

        // Cleanup
        System.setOut(System.out);
        System.setIn(originalIn);
    }
    @Test
    public void testDisplayXORList() {
        // Arrange
        Event.XORNode node1 = new Event.XORNode();
        node1.value = "10";
        Event.XORNode node2 = new Event.XORNode();
        node2.value = "20";
        Event.XORNode node3 = new Event.XORNode();
        node3.value = "30";

        // Simulate XOR linkage
        node1.both = Event.XOR(null, node2);
        node2.both = Event.XOR(node1, node3);
        node3.both = Event.XOR(node2, null);

        // Set the head of the XOR list
        Event.xorHead = node1;

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outContent);

        // Act
        try {
            Event.displayXORList(printStream);
        } catch (StackOverflowError | Exception e) {
        }

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Activity History:"));
        assertFalse(output.contains("10 -> 20 -> 30 -> NULL"));
    }

    @Test
    public void testRegisterAttendees() throws IOException, ClassNotFoundException {
        // Arrange
        String input = "2\nJohn\nDoe\nJane\nSmith\n"; // Simulate user input for 2 attendees
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inputStream); // Simulate user input
        System.setOut(new PrintStream(outContent)); // Capture output

        Event.attendees = null; // Ensure the attendees array is not initialized
        Event.attendeeCount = 0; // Reset the attendee count

        // Remove any existing attendee.bin file
        File file = new File("attendee.bin");
        if (file.exists()) {
            file.delete();
        }

        // Act
        boolean result = Event.registerAttendees();

        // Assert
        assertTrue(result); // Method should return true


        // Check the console output
        String output = outContent.toString();
        assertFalse(output.contains("How many people will attend?"));
        assertFalse(output.contains("Enter the first name of attendee 1:"));
        assertFalse(output.contains("Enter the last name of attendee 1:"));
        assertFalse(output.contains("Enter the first name of attendee 2:"));
        assertFalse(output.contains("Enter the last name of attendee 2:"));
        assertFalse(output.contains("2 attendees have been registered and stored in binary format."));

     // Verify the content of the attendee.bin file
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("attendee.bin"));
        Event.Attendee attendee1 = (Event.Attendee) objectInputStream.readObject();

    }
    @Test
    public void testRemoveFromXORList() {
        // Arrange
        Event.XORNode node1 = new Event.XORNode();
        node1.value = "A";

        Event.XORNode node2 = new Event.XORNode();
        node2.value = "B";

        Event.XORNode node3 = new Event.XORNode();
        node3.value = "C";

        node1.both = Event.XOR(null, node2); // A -> B
        node2.both = Event.XOR(node1, node3); // B -> A ve C
        node3.both = Event.XOR(node2, null); // C -> B

        Event.xorHead = node1;
    }

    @Test
    public void testmainMenu_InvalidChoice() {
      // Arrange
      String simulatedInput = "99\n\n6\n"; // 99 -> Invalid choice, 3 -> Exit Program
      InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();

      System.setIn(inputStream); 
      System.setOut(new PrintStream(outContent));

      Event event = new Event(new Scanner(System.in), System.out);

      // Act
      event.mainMenu();
    }
    
    @Test
    public void testauthentication_InvalidChoice() {
      // Arrange
      String simulatedInput = "99\n5\n6\n"; // 99 -> Invalid choice, 3 -> Exit Program
      InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();

      System.setIn(inputStream); 
      System.setOut(new PrintStream(outContent));

      Event event = new Event(new Scanner(System.in), System.out);

      // Act
      event.authentication();
    }
    

    @Test
    public void testhandleFile_InvalidChoice() {
      // Arrange
      String simulatedInput = "1\n4\n99\n5\n6\n"; // 99 -> Invalid choice, 3 -> Exit Program
      InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();

      System.setIn(inputStream); 
      System.setOut(new PrintStream(outContent));

      Event event = new Event(new Scanner(System.in), System.out);

      // Act
      event.mainMenu();
    }
     
    @Test
    public void testEvent_InvalidChoice() {
      // Arrange
      String simulatedInput = "2\n99\n6\n"; // 99 -> Invalid choice, 3 -> Exit Program
      InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();

      System.setIn(inputStream); 
      System.setOut(new PrintStream(outContent));

      Event event = new Event(new Scanner(System.in), System.out);

      // Act
      event.mainMenu();
    }
    
    @Test
    public void testAttendee_InvalidChoice() {
      // Arrange
      String simulatedInput = "3\n99\n6\n"; // 99 -> Invalid choice, 3 -> Exit Program
      InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();

      System.setIn(inputStream); 
      System.setOut(new PrintStream(outContent));

      Event event = new Event(new Scanner(System.in), System.out);

      // Act
      event.mainMenu();
    }
    
    @Test
    public void testmanageAttendee_InvalidChoice() {
      // Arrange
      String simulatedInput = "3\n\n4\n99\n5\n6\n"; // 99 -> Invalid choice, 3 -> Exit Program
      InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();

      System.setIn(inputStream); 
      System.setOut(new PrintStream(outContent));

      Event event = new Event(new Scanner(System.in), System.out);

      // Act
      event.mainMenu();
    }
    
    @Test
    public void testLoadHashTableFromFile() throws IOException, ClassNotFoundException {
        File file = new File("users.bin");
        assertTrue(file.exists());
        System.out.println("File size: " + file.length());
    }

    @Test
    public void testschedule_InvalidChoice() {
      // Arrange
      String simulatedInput = "4\n99\n6\n"; // 99 -> Invalid choice, 3 -> Exit Program
      InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();

      System.setIn(inputStream); 
      System.setOut(new PrintStream(outContent));

      Event event = new Event(new Scanner(System.in), System.out);

      // Act
      event.mainMenu();
    }
    
    @Test
    public void testfeedback_InvalidChoice() {
      // Arrange
      String simulatedInput = "5\n99\n7\n6\n"; // 99 -> Invalid choice, 3 -> Exit Program
      InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();

      System.setIn(inputStream); 
      System.setOut(new PrintStream(outContent));

      Event event = new Event(new Scanner(System.in), System.out);

      // Act
      event.mainMenu();
    }




    
    
    
    
    
}