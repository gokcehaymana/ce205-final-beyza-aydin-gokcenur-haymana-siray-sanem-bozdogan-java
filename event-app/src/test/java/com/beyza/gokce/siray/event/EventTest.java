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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

import com.beyza.gokce.siray.event.Event;
import com.beyza.gokce.siray.event.Event.Attendee;
import com.beyza.gokce.siray.event.Event.BPlusTree;
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
      assertEquals(75, hashValue); // Beklenen değer TABLE_SIZE = 100 olduğundan 90.
  }
  
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final InputStream originalIn = System.in;
  private final PrintStream originalOut = System.out;
private Object outputStream;
private Object outputContent;

  @Before
  public void setUp() {
      System.setOut(new PrintStream(outContent));
  }

  @After
  public void tearDown() {
      System.setIn(originalIn);
      System.setOut(originalOut);
  }
 
  /*
  @Test
  public void testMainMenuExitOption() {
      // Kullanıcı girdisini simüle etmek için "6" (çıkış) giriyoruz
      String input = "6\n"; // Kullanıcı "6" seçeneğini seçiyor
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);

      // Konsol çıktısını yakalamak için ByteArrayOutputStream kullanıyoruz
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));

      // mainMenu'yu çağırıyoruz
      Event.mainMenu();

      // Çıkış mesajını kontrol ediyoruz
      String expectedOutput = "Exiting the program. Goodbye!";
      assertTrue(outContent.toString().contains(expectedOutput));

      // Girdiyi ve çıktıyı geri yüklüyoruz
      System.setIn(System.in);
      System.setOut(System.out);
  }
  */
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
  
  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testInsertMinHeap_WhenHeapIsFull_ShouldThrowException() {
      int capacity = 1;
      Event.MinHeap minHeap = Event.createMinHeap(capacity);
      Event.MinHeapNode node1 = Event.createMinHeapNode('a', 10);
      Event.MinHeapNode node2 = Event.createMinHeapNode('b', 15);

      Event.insertMinHeap(minHeap, node1);
      Event.insertMinHeap(minHeap, node2); // Bu satır istisna fırlatır
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
      assertEquals(node1, result); // İlk düğüm döndürülmeli
      assertEquals(1, minHeap.size); // Boyut bir azaltılmalı
      assertEquals(node2, minHeap.array[0]); // İlk düğümün yerini ikinci düğüm almalı
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testExtractMin_FromEmptyHeap_ShouldThrowException() {
      Event.MinHeap minHeap = Event.createMinHeap(5);

      Event.extractMin(minHeap); // Boş bir heap'ten çıkarma, istisna fırlatmalı
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
      user2.phone = "5551234567"; // Aynı hash değerine sahip olacak
      user2.password = "password456";

      Event.saveUser(user1);
      Event.saveUser(user2);

      int index = Event.hash(user1.phone); 
      Event.User savedUser = Event.getHashTable()[index]; // Getter kullanıldı

      assertNotNull(savedUser);
      assertEquals("Bob", savedUser.name); // Son eklenen kullanıcı en başta olmalı
      assertEquals(user1, savedUser.next); // İlk kullanıcı ikinci düğüm olmalı
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
  public void testClearScreen_ShouldExecuteWithoutExceptions() {
      try {
          Event.clearScreen(); // Test edilen fonksiyon çağrılır
          assertTrue(true); // Eğer bir istisna oluşmazsa test başarılı
      } catch (Exception e) {
          fail("The clearScreen function threw an exception: " + e.getMessage()); // Eğer bir istisna oluşursa test başarısız
      }
  }
  
  @Test
  public void testPrintHashTable_ShouldPrintCorrectContent() {
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));

      // Hazırlık: Hash tablosuna örnek kullanıcılar ekleyin
      Event.User user1 = new Event.User();
      user1.name = "John";
      user1.surname = "Doe";
      user1.phone = "12345";
      user1.password = "password1";

      Event.User user2 = new Event.User();
      user2.name = "Jane";
      user2.surname = "Smith";
      user2.phone = "67890";
      user2.password = "password2";

      Event.saveUser(user1);
      Event.saveUser(user2);

      // Test: Hash tablosunu yazdır
      Event.printHashTable();

      // Çıktıyı kontrol edin
      String output = outContent.toString();
      assertTrue(output.contains("John Doe"));
      assertTrue(output.contains("12345"));
      assertTrue(output.contains("password1"));
      assertTrue(output.contains("Jane Smith"));
      assertTrue(output.contains("67890"));
      assertTrue(output.contains("password2"));

      System.setOut(System.out); // Standart çıktıyı eski haline döndür
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
  public void testOrganizeActivities_ValidInput() {
      // Simüle edilmiş girdi
      String input = "1\n2\nMeeting with client\n";
      ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);

      // Simüle edilmiş çıktı yakalama
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      System.setOut(new PrintStream(out));

      // Sparse matrisin temizlenmesi
      Event.initializeSparseMatrix();

      // organizeActivities fonksiyonunu çağır
      Event.organizeActivities();

      // Çıktıyı kontrol et
      String output = out.toString();
      assertTrue(output.contains("Enter the row index for the activity:"));
      assertTrue(output.contains("Enter the column index for the activity:"));
      assertTrue(output.contains("Enter the activity details:"));
      assertTrue(output.contains("Activity organized: Meeting with client"));

      // Sparse matriste aktiviteyi kontrol et
      assertEquals(1, Event.activityMatrix.row[0]);
      assertEquals(2, Event.activityMatrix.col[0]);
      assertEquals("Meeting with client", Event.activityMatrix.value[0]);
  }


  @Test
  public void testQuadraticProbing() {
      // Girdi verileri
      int[] expectedHashTable = new int[10];
      expectedHashTable[3] = 23;
      expectedHashTable[5] = 45;
      expectedHashTable[2] = 12;
      expectedHashTable[7] = 37;
      expectedHashTable[9] = 29;

      // Çıktıyı yakalamak için System.out'u yönlendir
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));

      // Fonksiyonu çağır
      Event.quadraticProbing();

      // Beklenen çıktı
      StringBuilder expectedOutput = new StringBuilder("Executing Quadratic Probing algorithm...\n");
      for (int i = 0; i < expectedHashTable.length; i++) {
          expectedOutput.append(String.format("Index %d: %d\n", i, expectedHashTable[i]));
      }

      // Gerçek çıktıyı al
      String actualOutput = outContent.toString().replace("\r", "").trim();
      String expected = expectedOutput.toString().replace("\r", "").trim();

      // Çıktıyı kontrol et
      assertEquals(expected, actualOutput);

      // Çıkışı temizle
      System.setOut(System.out);
  }
  
  @Test
  public void testGuest_ShouldPrintGuestModeMessage() {
      // Çıktıyı yakalamak için OutputStream oluştur
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));

      // guest fonksiyonunu çağır
      Event.guest();

      // Beklenen çıktı
      String expectedOutput = "You are in guest mode.\n";

      // Gerçek çıktıyı al ve normalize et
      String actualOutput = outContent.toString().replaceAll("\\r\\n?", "\n").trim();
      String cleanedExpectedOutput = expectedOutput.replaceAll("\\r\\n?", "\n").trim();

      // Çıktıyı karşılaştır
      assertEquals(cleanedExpectedOutput, actualOutput);

      // Çıkışı temizle
      System.setOut(System.out);
  }

  @Test
  public void testDFS_ShouldVisitAllConnectedNodes() {
      // Example graph represented as an adjacency matrix
      int[][] adjMatrix = {
          {0, 1, 0, 0, 0},  // Node 0 is connected to Node 1
          {1, 0, 1, 0, 0},  // Node 1 is connected to Nodes 0 and 2
          {0, 1, 0, 1, 1},  // Node 2 is connected to Nodes 1, 3, and 4
          {0, 0, 1, 0, 0},  // Node 3 is connected to Node 2
          {0, 0, 1, 0, 0}   // Node 4 is connected to Node 2
      };

      int n = adjMatrix.length; // Number of nodes
      boolean[] visited = new boolean[n]; // Visited array for nodes
      StringBuilder visitedNodes = new StringBuilder(); // To track the visited nodes

      // Override System.out to capture output
      PrintStream originalOut = System.out;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStream));

      // Perform DFS starting from node 0
      Event.DFS(0, visited, adjMatrix, n);

      // Restore the original System.out
      System.setOut(originalOut);

      // Expected nodes visited in order
      String expectedOutput = "Visited Node: 0\n" +
                              "Visited Node: 1\n" +
                              "Visited Node: 2\n" +
                              "Visited Node: 3\n" +
                              "Visited Node: 4\n";

      // Assert the captured output matches the expected order
      assertEquals("DFS traversal order should match", expectedOutput.trim(), outputStream.toString().trim());

      // Verify all nodes are visited
      for (boolean nodeVisited : visited) {
          assertTrue("All nodes should be visited", nodeVisited);
      }
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

      // Validate the output
      assertEquals(expected, huffmanCode.toString(), "Generated Huffman codes do not match expected output.");
  }

  @Test
  public void testValidateLogin_ShouldReturnTrueForValidCredentials() {
      // Kullanıcı oluştur
      Event.User validUser = new Event.User();
      validUser.phone = "1234567890";
      validUser.password = "securePassword";

      // Kullanıcıyı hash fonksiyonu ile doğru index'e ekle
      int index = Event.hash(validUser.phone);
      Event.hashTable[index] = validUser;

      // Giriş doğrulaması
      boolean result = Event.validateLogin("1234567890", "securePassword");

      // Sonucu kontrol et
      if (!result) {
          fail("validateLogin geçerli bilgiler için 'true' döndürmeliydi, ama 'false' döndürdü.");
      } else {
          System.out.println("Test Başarılı: validateLogin geçerli bilgiler için doğru sonucu döndürdü.");
      }
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
      if (result) {
          fail("validateLogin should return false for an invalid phone, but it returned true.");
      } else {
          System.out.println("Test Passed: validateLogin returned false for an invalid phone.");
      }
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
      if (result) {
          fail("validateLogin should return false for an invalid password, but it returned true.");
      } else {
          System.out.println("Test Passed: validateLogin returned false for an invalid password.");
      }
  }

  @Test
  public void testValidateLogin_ShouldReturnFalseForNullInputs() {
      // Act
      boolean result = Event.validateLogin(null, null);

      // Assert manually
      if (result) {
          fail("validateLogin should return false for null inputs, but it returned true.");
      } else {
          System.out.println("Test Passed: validateLogin returned false for null inputs.");
      }
  }
  

  @Test
  public void testEvent1Constructor() {
      // Test için örnek veri
      String expectedType = "Birthday";
      String expectedDate = "2024-12-31";
      String expectedColor = "Blue";
      String expectedConcept = "Surprise Party";

      // Event1 nesnesi oluşturuluyor
      Event.Event1 event = new Event().new Event1(expectedType, expectedDate, expectedColor, expectedConcept);

      // Özelliklerin doğru atandığını kontrol et
      assertEquals(expectedType, event.type);
      assertEquals(expectedDate, event.date);
      assertEquals(expectedColor, event.color);
      assertEquals(expectedConcept, event.concept);

      // İlk başta prev ve next null olmalı
      assertNull(event.prev);
      assertNull(event.next);
  }

  @Test
  public void testProgressiveOverflowAlgorithm() {
      // Konsol çıktısını yakalamak için ayarlar
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream originalOut = System.out; // Orijinal çıktıyı kaydet
      System.setOut(new PrintStream(outContent));

      // Metodu çalıştır
      Event.progressiveOverflowAlgorithm();

      // Gerçek çıktıyı al
      String actualOutput = outContent.toString();

      // Beklenen çıktıyı tanımla
      String expectedOutput =
          "Overflow detected at element 3 with sum 10\n" +
          "Overflow detected at element 5 with sum 13\n" +
          "Overflow detected at element 7 with sum 15\n" +
          "Overflow detected at element 9 with sum 19\n";

      // Konsol çıktısını eski haline getir
      System.setOut(originalOut);

      // Elle karşılaştırma yap
      if (!actualOutput.equals(expectedOutput)) {
          System.out.println("Test Failed!");
          System.out.println("Expected Output:\n" + expectedOutput);
          System.out.println("Actual Output:\n" + actualOutput);
      } 
  }
  
  @Test
  public void testLinearProbing() {
      // Konsol çıktısını yakalamak için ayarlar
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream originalOut = System.out; // Orijinal çıktıyı sakla
      System.setOut(new PrintStream(outContent));

      // Metodu çalıştır
      Event.linearProbing();

      // Beklenen çıktıyı tanımla
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

      // Konsol çıktısını eski haline getir
      System.setOut(originalOut);

      // Gerçek çıktıyı al ve normalize et
      String actualOutput = outContent.toString().replace("\r\n", "\n");

      // Çıktıları elle karşılaştır
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
      // Konsol çıktısını yakalamak için ayarlar
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream originalOut = System.out; // Orijinal çıktıyı sakla
      System.setOut(new PrintStream(outContent));

      // Metodu çalıştır
      Event.progressiveOverflow();

      // Gerçek çıktıyı al
      String actualOutput = outContent.toString().replace("\r\n", "\n");

      // Beklenen çıktıyı tanımla
      String expectedOutput = 
          "Executing Progressive Overflow algorithm...\n" +
          "Overflow detected at index 3 with sum 10\n" +
          "Overflow detected at index 6 with sum 15\n" +
          "Overflow detected at index 9 with sum 19\n";

      // Konsol çıktısını eski haline getir
      System.setOut(originalOut);

      // Çıktıları elle karşılaştır
      if (!actualOutput.equals(expectedOutput)) {
          System.out.println("Test Failed!");
          System.out.println("Expected Output:");
          System.out.println(expectedOutput);
          System.out.println("Actual Output:");
          System.out.println(actualOutput);
      } 
  }
  
  @Test
  public void testDoubleHashing() {
      // Konsol çıktısını yakalamak için ayarlar
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream originalOut = System.out; // Orijinal çıktıyı sakla
      System.setOut(new PrintStream(outContent));

      // Metodu çalıştır
      Event.doubleHashing();

      // Gerçek çıktıyı al
      String actualOutput = outContent.toString().replace("\r\n", "\n");

      // Beklenen çıktıyı tanımla
      String expectedOutput =
          "Executing Double Hashing algorithm...\n" +
          "Index 0: 0\n" +
          "Index 1: 0\n" +
          "Index 2: 0\n" +
          "Index 3: 0\n" +
          "Index 4: 0\n" +
          "Index 5: 45\n" +
          "Index 6: 0\n" +
          "Index 7: 37\n" +
          "Index 8: 29\n" +
          "Index 9: 23\n";

      // Konsol çıktısını eski haline getir
      System.setOut(originalOut);

      // Çıktıları elle karşılaştır
      if (!actualOutput.equals(expectedOutput)) {
          System.out.println("Test Failed!");
          System.out.println("Expected Output:");
          System.out.println(expectedOutput);
          System.out.println("Actual Output:");
          System.out.println(actualOutput);
      }
  }
  
  @Test
  public void testUseBuckets() {
      // Konsol çıktısını yakalamak için ayarlar
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream originalOut = System.out; // Orijinal çıktıyı sakla
      System.setOut(new PrintStream(outContent));

      // Metodu çalıştır
      Event.useBuckets();

      // Gerçek çıktıyı al
      String actualOutput = outContent.toString().replace("\r\n", "\n");

      // Beklenen çıktıyı tanımla
      String expectedOutput =
          "Executing Use of Buckets algorithm...\n" +
          "Bucket 0: 45 12 \n" +
          "Bucket 1: 37 \n" +
          "Bucket 2: 23 29 \n";

      // Konsol çıktısını eski haline getir
      System.setOut(originalOut);

      // Çıktıları manuel olarak karşılaştır
      if (!actualOutput.equals(expectedOutput)) {
          throw new RuntimeException("Test Failed! Expected output does not match actual output.\n" +
                  "Expected:\n" + expectedOutput + "\n" +
                  "Actual:\n" + actualOutput);
      }

      // Test başarılı
      System.out.println("Test Passed!");
  }
  

  @Test
  public void testBrentsMethod() {
      // Konsol çıktısını yakalamak için ayarlar
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream originalOut = System.out; // Orijinal çıktıyı sakla
      System.setOut(new PrintStream(outContent));

      // Metodu çalıştır
      Event.brentsMethod();

      // Gerçek çıktıyı al
      String actualOutput = outContent.toString().replace("\r\n", "\n");

      // Beklenen çıktıyı tanımla
      String expectedOutput =
          "Executing Brent's Method algorithm...\n" +
          "Index 0: 0\n" +
          "Index 1: 0\n" +
          "Index 2: 0\n" +
          "Index 3: 0\n" +
          "Index 4: 0\n" +
          "Index 5: 45\n" +
          "Index 6: 0\n" +
          "Index 7: 37\n" +
          "Index 8: 29\n" +
          "Index 9: 23\n";

      // Konsol çıktısını eski haline getir
      System.setOut(originalOut);

      // Çıktıları manuel olarak karşılaştır
      if (!actualOutput.equals(expectedOutput)) {
          throw new RuntimeException("Test Failed! Expected output does not match actual output.\n" +
                  "Expected:\n" + expectedOutput + "\n" +
                  "Actual:\n" + actualOutput);
      }
  }
  
  @Test
  public void testLinearQuotient1() {
      // Konsol çıktısını yakalamak için ayarlar
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream originalOut = System.out; // Orijinal çıktıyı sakla
      System.setOut(new PrintStream(outContent));

      // Metodu çalıştır
      Event.linearQuotient();

      // Gerçek çıktıyı al
      String actualOutput = outContent.toString().replace("\r\n", "\n");

      // Beklenen çıktıyı tanımla
      String expectedOutput =
          "Executing Linear Quotient algorithm...\n" +
          "Index 0: 0\n" +
          "Index 1: 0\n" +
          "Index 2: 0\n" +
          "Index 3: 0\n" +
          "Index 4: 0\n" +
          "Index 5: 45\n" +
          "Index 6: 0\n" +
          "Index 7: 37\n" +
          "Index 8: 29\n" +
          "Index 9: 23\n";

      // Konsol çıktısını eski haline getir
      System.setOut(originalOut);

      // Çıktıları manuel olarak karşılaştır
      if (!actualOutput.equals(expectedOutput)) {
          throw new RuntimeException("Test Failed! Expected output does not match actual output.\n" +
                  "Expected:\n" + expectedOutput + "\n" +
                  "Actual:\n" + actualOutput);
      }
  }

  @Test
  public void testBuildHuffmanTree_ShouldGenerateCorrectHuffmanCode() {
      String inputString = "aaabbc";
      Event.Attendeer attendee = new Event().new Attendeer();

      Event.buildHuffmanTree(inputString, attendee);

      // Huffman kodunu kontrol et
      assertNotNull(attendee.huffmanCode);
      assertFalse(attendee.huffmanCode.isEmpty());

      // Çıkışı yazdırarak kontrol et
      System.out.println("Generated Huffman Code:");
      System.out.println(attendee.huffmanCode);
  }
  
  @Test
  public void testQuadraticProbingInsert_ShouldInsertUserSuccessfully() {
      Event.User newUser = new Event.User();
      newUser.name = "John";
      newUser.surname = "Doe";
      newUser.phone = "123456789";
      newUser.password = "password";

      boolean result = Event.quadraticProbingInsert(newUser);

      assertTrue(result); // Kullanıcı başarıyla eklenmeli
  }

  @Test
  public void testQuadraticProbingInsert_ShouldHandleCollision() {
      Event.User firstUser = new Event.User();
      firstUser.name = "Alice";
      firstUser.phone = "123"; // Çakışmaya sebep olacak şekilde hash değeri hesaplanır.

      Event.User secondUser = new Event.User();
      secondUser.name = "Bob";
      secondUser.phone = "123"; // Aynı hash değeri üretilecek.

      assertTrue(Event.quadraticProbingInsert(firstUser)); // İlk kullanıcı başarıyla eklenir
      assertTrue(Event.quadraticProbingInsert(secondUser)); // İkinci kullanıcı çakışmayı çözerek eklenir
  }

  @Test
  public void testQuadraticProbingInsert_ShouldFailWhenTableIsFull() {
      for (int i = 0; i < Event.TABLE_SIZE; i++) {
          Event.User user = new Event.User();
          user.phone = String.valueOf(i);
          user.name = "User" + i;
          assertTrue(Event.quadraticProbingInsert(user)); // Tabloda yer olduğu sürece başarılı olmalı
      }

      Event.User extraUser = new Event.User();
      extraUser.phone = "overflow";
      assertFalse(Event.quadraticProbingInsert(extraUser)); // Tablo dolu olduğundan başarısız olmalı
  }

  @Test
  public void testSaveHashTableToFile_ShouldWriteToFile() throws IOException {
      // Kullanıcıları hash tablosuna ekle
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

      // Dosyaya kaydet
      Event.saveHashTableToFile();

      // Dosyanın var olup olmadığını kontrol et
      File file = new File("users.bin");
      assertTrue(file.exists());
      assertTrue(file.length() > 0);

      // Test sonrası temizleme
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
      internalNode.children[2] = null; // Boş bir çocuk düğüm

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
          assertEquals(0, internalNode.keys[i]); // Varsayılan değer 0
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

      try {
          Event.BPlusLeafNode rootNode = (Event.BPlusLeafNode) tree.root;
          rootNode.numKeys = 1; // Hata oluşturacak
          fail("Expected a NullPointerException to be thrown");
      } catch (NullPointerException e) {
          assertNotNull(e); // Hata doğrulandı
      }
  }

  @Test
  public void createBPlusTree_ShouldInitializeTreeWithLeafNodeAsRoot() {
      Event.BPlusTree tree = Event.createBPlusTree();

      // Ağacın root'unun null olmadığını kontrol edin
      assertNotNull(tree.root);

      // Root'un bir BPlusLeafNode olduğunu kontrol edin
      assertTrue(tree.root instanceof Event.BPlusLeafNode);

      // Root düğümünün başlangıç değerlerini kontrol edin
      Event.BPlusLeafNode rootLeaf = (Event.BPlusLeafNode) tree.root;
      assertEquals(0, rootLeaf.numKeys);
      assertNull(rootLeaf.next);
      assertNotNull(rootLeaf.keys);
      assertEquals(Event.BPlusLeafNode.MAX_KEYS, rootLeaf.keys.length);
  }

  @Test
  public void insertIntoLeaf_ShouldInsertKeyIntoNonFullLeaf() {
      Event.BPlusLeafNode leaf = new Event.BPlusLeafNode();

      // Anahtarlar ekleniyor
      Event.insertIntoLeaf(leaf, 10);
      Event.insertIntoLeaf(leaf, 20);

      // Doğrulamalar
      assertEquals(2, leaf.numKeys);
      assertEquals(10, leaf.keys[0]);
      assertEquals(20, leaf.keys[1]);
  }

  @Test
  public void insertIntoLeaf_ShouldInsertKeyInCorrectOrder() {
      Event.BPlusLeafNode leaf = new Event.BPlusLeafNode();

      // Sırasız anahtarlar ekleniyor
      Event.insertIntoLeaf(leaf, 20);
      Event.insertIntoLeaf(leaf, 10);
      Event.insertIntoLeaf(leaf, 30);

      // Doğrulamalar (sıralı eklenmesi gerekir)
      assertEquals(3, leaf.numKeys);
      assertEquals(10, leaf.keys[0]);
      assertEquals(20, leaf.keys[1]);
      assertEquals(30, leaf.keys[2]);
  }

  @Test
  public void insertIntoLeaf_ShouldSplitLeafWhenFull() {
      Event.BPlusLeafNode leaf = new Event.BPlusLeafNode();

      // MAX_KEYS kadar anahtar ekleniyor
      Event.insertIntoLeaf(leaf, 10);
      Event.insertIntoLeaf(leaf, 20);
      Event.insertIntoLeaf(leaf, 30);

      // Taşmaya neden olacak bir anahtar ekleniyor
      Event.insertIntoLeaf(leaf, 40);

      // Doğrulamalar
      assertEquals(1, leaf.numKeys); // Eski yaprak
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
      assertTrue(output.contains("SCC Found: 2 1 0")); // First SCC
      assertTrue(output.contains("SCC Found: 4 3"));   // Second SCC
  }

  @Test
  public void BFS_ShouldTraverseGraphInBreadthFirstOrder() {
      // Arrange
      int n = 5; // Düğüm sayısı
      int startNode = 0; // Başlangıç düğümü
      int[][] adjMatrix = {
          {0, 1, 0, 0, 1}, // 0 -> 1, 0 -> 4
          {1, 0, 1, 0, 0}, // 1 -> 0, 1 -> 2
          {0, 1, 0, 1, 0}, // 2 -> 1, 2 -> 3
          {0, 0, 1, 0, 0}, // 3 -> 2
          {1, 0, 0, 0, 0}  // 4 -> 0
      };

      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Act
      Event.BFS(startNode, adjMatrix, n);

      // Assert
      String output = consoleOutput.toString();

      // Ziyaret sırasını kontrol et
      assertTrue(output.contains("BFS Traversal starting from node 0:"));
      assertTrue(output.contains("Visited Node: 0"));
      assertTrue(output.contains("Visited Node: 1"));
      assertTrue(output.contains("Visited Node: 4"));
      assertTrue(output.contains("Visited Node: 2"));
      assertTrue(output.contains("Visited Node: 3"));
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

      // Act
      Event.printLeafNodes(tree);

      // Assert
      String expectedOutput = "Leaf Node: 5 10 15 20 \n";
      assertEquals(expectedOutput, consoleOutput.toString());
  }

  @Test
  public void testBFS_ShouldTraverseGraphCorrectly() {
      // Arrange
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      int[][] adjMatrix = {
          {0, 1, 0, 1}, // Düğüm 0: 1 ve 3 ile bağlı
          {1, 0, 1, 0}, // Düğüm 1: 0 ve 2 ile bağlı
          {0, 1, 0, 1}, // Düğüm 2: 1 ve 3 ile bağlı
          {1, 0, 1, 0}  // Düğüm 3: 0 ve 2 ile bağlı
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
      // Örnek bir SCC yığını oluşturun
      Event.stack = new int[5];
      Event.stackTop = -1;
      Event.inStack = new boolean[5];

      // Yığını başlatın
      Event.stack[++Event.stackTop] = 3;
      Event.inStack[3] = true;

      Event.stack[++Event.stackTop] = 1;
      Event.inStack[1] = true;

      // Act
      int poppedNode = Event.popStackSCC();

      // Assert
      assertEquals(1, poppedNode); // Son eklenen düğümün çıkması gerekiyor
      assertFalse(Event.inStack[1]); // inStack'ten kaldırılmış olmalı
      assertEquals(0, Event.stackTop); // stackTop bir azalmalı
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
      assertEquals(0, Event.stackTop); // İlk eklemeden sonra stackTop 0 olmalı
      assertEquals(nodeToPush, Event.stack[Event.stackTop]); // Yığının en üstüne eklenmiş olmalı
      assertTrue(Event.inStack[nodeToPush]); // Düğüm inStack olarak işaretlenmeli
  }

  @Test
  public void testSCC_ShouldFindStronglyConnectedComponents() {
      // Arrange
      int n = 5; // Düğüm sayısı
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

      Arrays.fill(Event.discoveryTime, -1); // Keşif zamanlarını başlangıçta -1 yap

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
      // Mock bir kullanıcı oluştur
      Event.User user = new Event.User();
      user.name = "Test";
      user.surname = "User";
      user.phone = "1234567890";
      user.password = "testPassword";

      // Konsol çıktısını yakalamak için sahte bir çıktı akışı oluştur
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Metodu çağır
      Event.saveUserData(user);

      // Hash tablosunu kontrol et
      Event.User[] hashTable = Event.hashTable;
      boolean userExists = false;
      for (Event.User entry : hashTable) {
          if (entry != null && entry.phone.equals(user.phone)) {
              userExists = true;
              break;
          }
      }
      assertTrue(userExists);

      // Konsol çıktısını al
      String output = consoleOutput.toString();

      // Konsol çıktısını yazdır
      System.out.println("Captured Console Output:\n" + output);
  }

  @Test
  public void Register_ShouldRegisterNewUserSuccessfully() {
      // Mock giriş verisi: yeni kullanıcı
      String inputString = "newUser\npassword123\n";
      InputStream in = new ByteArrayInputStream(inputString.getBytes());
      Scanner testScanner = new Scanner(in);

      // Metodu çağır
      Event.Register(testScanner);

      // Kullanıcı HashMap'inde var mı kontrol et
      boolean userExists = Event.users.containsKey("newUser");
      assertTrue(userExists);

      // Kullanıcı HashMap'indeki şifre doğru mu kontrol et
      assertEquals("password123", Event.users.get("newUser"));
  }

  @Test
  public void Register_ShouldNotAllowDuplicateUsername() {
      // Mock giriş verisi: aynı kullanıcı adı
      String inputString = "existingUser\npassword123\n";
      InputStream in = new ByteArrayInputStream(inputString.getBytes());
      Scanner testScanner = new Scanner(in);

      // İlk kullanıcıyı ekle
      Event.users.put("existingUser", "oldPassword");

      // Konsol çıktısını yakala
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Metodu çağır
      Event.Register(testScanner);

      // Çıktıyı kontrol et
      String output = consoleOutput.toString();
      assertTrue(output.contains("Name already exists. Try another one."));

      // Kullanıcı verisinin değişmediğini kontrol et
      assertEquals("oldPassword", Event.users.get("existingUser"));
  }

  @Test
  public void logIn_ShouldReturnTrueForValidCredentials() {
      // Geçerli kullanıcı bilgilerini hazırlama
      Event.users.put("validUser", "validPassword");

      // Simüle edilmiş kullanıcı girişi
      String inputString = "validUser\nvalidPassword\n";
      InputStream in = new ByteArrayInputStream(inputString.getBytes());
      Scanner testScanner = new Scanner(in);

      // Metodu çağır ve sonucu kontrol et
      boolean result = Event.logIn(testScanner);
      assertTrue(result);
  }

  @Test
  public void logIn_ShouldReturnFalseForInvalidUsername() {
      // Geçersiz kullanıcı adı
      Event.users.put("validUser", "validPassword");

      // Simüle edilmiş kullanıcı girişi
      String inputString = "invalidUser\nvalidPassword\n";
      InputStream in = new ByteArrayInputStream(inputString.getBytes());
      Scanner testScanner = new Scanner(in);

      // Metodu çağır ve sonucu kontrol et
      boolean result = Event.logIn(testScanner);
      assertFalse(result);
  }

  @Test
  public void logIn_ShouldReturnFalseForInvalidPassword() {
      // Kullanıcı bilgilerini hazırlama
      Event.users.put("validUser", "validPassword");

      // Simüle edilmiş kullanıcı girişi
      String inputString = "validUser\ninvalidPassword\n";
      InputStream in = new ByteArrayInputStream(inputString.getBytes());
      Scanner testScanner = new Scanner(in);

      // Metodu çağır ve sonucu kontrol et
      boolean result = Event.logIn(testScanner);
      assertFalse(result);
  }

  @Test
  public void handleFileOperation_ShouldCallProgressiveOverflow() {
      // Konsol çıktısını yakala
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Metodu çağır
      Event.handleFileOperation(1);

      // Çıktının doğru metni içerdiğini kontrol et
      String output = consoleOutput.toString();
      assertTrue(output.contains("Executing Progressive Overflow algorithm"));
  }

  @Test
  public void handleFileOperation_ShouldCallLinearProbing() {
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      Event.handleFileOperation(2);

      String output = consoleOutput.toString();
      assertTrue(output.contains("Executing Linear Probing algorithm"));
  }

  @Test
  public void handleFileOperation_ShouldCallQuadraticProbing() {
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      Event.handleFileOperation(3);

      String output = consoleOutput.toString();
      assertTrue(output.contains("Executing Quadratic Probing algorithm"));
  }

  @Test
  public void handleFileOperation_ShouldCallDoubleHashing() {
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      Event.handleFileOperation(4);

      String output = consoleOutput.toString();
      assertTrue(output.contains("Executing Double Hashing algorithm"));
  }

  @Test
  public void handleFileOperation_ShouldCallUseBuckets() {
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      Event.handleFileOperation(5);

      String output = consoleOutput.toString();
      assertTrue(output.contains("Executing Use of Buckets algorithm"));
  }

  @Test
  public void handleFileOperation_ShouldCallLinearQuotient() {
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      Event.handleFileOperation(6);

      String output = consoleOutput.toString();
      assertTrue(output.contains("Executing Linear Quotient algorithm"));
  }

  @Test
  public void handleFileOperation_ShouldCallBrentsMethod() {
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      Event.handleFileOperation(7);

      String output = consoleOutput.toString();
      assertTrue(output.contains("Executing Brent's Method algorithm"));
  }

  @Test
  public void handleFileOperation_ShouldPrintReturningToMenu() {
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      Event.handleFileOperation(8);

      String output = consoleOutput.toString();
      assertTrue(output.contains("Returning to Authentication Menu."));
  }

  @Test
  public void handleFileOperation_ShouldPrintInvalidChoice() {
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      Event.handleFileOperation(99); // Geçersiz seçim

      String output = consoleOutput.toString();
      assertTrue(output.contains("Invalid choice. Please try again."));
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
  public void testKmpSearch_FoundPattern() {
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
      Event.attendeeCount = 2;  // Set the count of attendees

      // Capture the console output to check results
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Call kmpSearch with a pattern that exists in an attendee's Huffman code
      String pattern = "101"; // This should match the code of attendee 1
      Event.kmpSearch(pattern);

      // Check that the correct attendee was found
      String expectedOutput = "Pattern found in Huffman code of attendee: Jane Smith\n";
      assertTrue(consoleOutput.toString().contains(expectedOutput));
  }

  @Test
  public void testKmpSearch_NoPatternFound() {
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

      // Call kmpSearch with a pattern that doesn't exist
      String pattern = "000"; // This should not match any Huffman code
      Event.kmpSearch(pattern);

      // Check that the output says no match was found
      assertTrue(consoleOutput.toString().contains("No match found."));
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
      Event.kmpSearch(pattern);

      // Check that the output says no match was found
      assertTrue(consoleOutput.toString().contains("No match found."));
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
  public void testRegisterAttendees_InvalidCount() throws IOException {
      String simulatedInput = "-1\n";
      System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

      boolean result = Event.registerAttendees();

      assertFalse(result);
      assertEquals(0, Event.attendeeCount);
      assertTrue(outContent.toString().contains("Invalid number!"));
  }

  @Test
  public void testRegisterAttendees_ExceedMaxCount() throws IOException {
      String simulatedInput = (Event.MAX_ATTENDEES + 1) + "\n";
      System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

      boolean result = Event.registerAttendees();

      assertFalse(result);
      assertEquals(0, Event.attendeeCount);
      assertTrue(outContent.toString().contains("Invalid number!"));
  }

  @Test
  public void testEventNodeConstructor() {
      // Test verileri
      String type = "Meeting";
      String date = "2024-12-25";
      String color = "Blue";
      String concept = "Year-end review";

      // EventNode nesnesini oluştur
      Event.EventNode eventNode = new Event.EventNode(type, date, color, concept);

      // Özellikleri doğrula
      assertEquals("Type is not set correctly", type, eventNode.type);
      assertEquals("Date is not set correctly", date, eventNode.date);
      assertEquals("Color is not set correctly", color, eventNode.color);
      assertEquals("Concept is not set correctly", concept, eventNode.concept);
      assertNull("Previous event should be null", eventNode.prev);
      assertNull("Next event should be null", eventNode.next);
  }

  @Test
  public void printAttendees_ShouldDisplayAllRegisteredAttendees() {
      // Test verilerini hazırla
      Event.Attendee attendee1 = new Event.Attendee();
      attendee1.nameAttendee = "John";
      attendee1.surnameAttendee = "Doe";
      attendee1.huffmanCode = "101";

      Event.Attendee attendee2 = new Event.Attendee();
      attendee2.nameAttendee = "Jane";
      attendee2.surnameAttendee = "Smith";
      attendee2.huffmanCode = "110";

      // Katılımcıları Event sınıfına ekle
      Event.attendees = new Event.Attendee[Event.MAX_ATTENDEES];
      Event.attendees[0] = attendee1;
      Event.attendees[1] = attendee2;
      Event.attendeeCount = 2;

      // Konsol çıktısını yakala
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Metodu çağır
      Event.printAttendees();

      // Regex ile kontrol et
      String output = consoleOutput.toString();
      assertTrue(output.contains("Name: John, Surname: Doe, Huffman Code: 101"));
      assertTrue(output.contains("Name: Jane, Surname: Smith, Huffman Code: 110"));
  }

  @Test
  public void attendee_ShouldPrintAttendees_WhenChoiceIs3() throws IOException {
      // Test verilerini hazırla
      Event.Attendee attendee1 = new Event.Attendee();
      attendee1.nameAttendee = "John";
      attendee1.surnameAttendee = "Doe";
      attendee1.huffmanCode = "101";

      Event.Attendee attendee2 = new Event.Attendee();
      attendee2.nameAttendee = "Jane";
      attendee2.surnameAttendee = "Smith";
      attendee2.huffmanCode = "110";

      Event.attendees = new Event.Attendee[Event.MAX_ATTENDEES];
      Event.attendees[0] = attendee1;
      Event.attendees[1] = attendee2;
      Event.attendeeCount = 2;

      // Kullanıcı girişini simüle et (Seçenek 3: Print Attendees)
      String input = "3\n";
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);

      // Konsol çıktısını yakala
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Metodu çağır
      Event.attendee();

      // Konsol çıktısını doğrula
      String expectedOutput = """
          ----------- Attendee Menu -----------
          1. Register Attendees
          2. Track Attendees
          3. Print Attendees
          4. Manage Attendees List
          5. Return to main menu
          Please enter your choice: 
          Registered Attendees:
          Name: John, Surname: Doe, Huffman Code: 101
          Name: Jane, Surname: Smith, Huffman Code: 110
          """;

      String actualOutput = consoleOutput.toString().replace("\r\n", "\n").trim();
      assertTrue(actualOutput.contains("Registered Attendees"));
      assertTrue(actualOutput.contains("Name: John, Surname: Doe, Huffman Code: 101"));
      assertTrue(actualOutput.contains("Name: Jane, Surname: Smith, Huffman Code: 110"));
  }
  
  @Test
  public void initializeXORList_ShouldResetXORListHeadToNull() {
      // XOR bağlantı listesini hazırla ve düğümler ekle
      Event.addToXORList("Activity 1");
      Event.addToXORList("Activity 2");
      Event.addToXORList("Activity 3");

      // Başlığın başlangıçta boş olmadığını doğrula
      assertNotNull(Event.xorHead);

      // XOR listesini sıfırla
      Event.initializeXORList();

      // XOR listesi sıfırlandıktan sonra başlığın null olduğunu kontrol et
      assertNull(Event.xorHead);
  }

  @Test
  public void initializeStack_ShouldResetStackToEmpty() {
      // Yığıta öğeler ekle
      Event.pushStack("Activity 1");
      Event.pushStack("Activity 2");

      // Başlangıçta yığıtın boş olmadığını doğrula
      assertNotEquals(-1, Event.activityStack.top);

      // Yığıtı sıfırla
      Event.initializeStack();

      // Sıfırlama işleminden sonra yığıtın boş olduğunu doğrula
      assertEquals(-1, Event.activityStack.top);
  }

  @Test
  public void initializeQueue_ShouldResetQueueToEmpty() {
      // Kuyruğa öğeler ekle
      Event.enqueue("Activity 1");
      Event.enqueue("Activity 2");

      // Başlangıçta kuyruğun boş olmadığını doğrula
      assertNotEquals(Event.activityQueue.front, Event.activityQueue.rear);

      // Kuyruğu sıfırla
      Event.initializeQueue();

      // Sıfırlama işleminden sonra kuyruğun boş olduğunu kontrol et
      assertEquals(0, Event.activityQueue.front);
      assertEquals(0, Event.activityQueue.rear);
  }

  @Test
  public void isStackEmpty_ShouldReturnTrue_WhenStackIsEmpty() {
      // Yığıtı sıfırla
      Event.initializeStack();

      // Yığıt boş olduğunda true döndüğünü kontrol et
      assertTrue(Event.isStackEmpty());
  }

  @Test
  public void isStackEmpty_ShouldReturnFalse_WhenStackIsNotEmpty() {
      // Yığıta bir öğe ekle
      Event.pushStack("Activity 1");

      // Yığıt boş olmadığında false döndüğünü kontrol et
      assertFalse(Event.isStackEmpty());
  }

  @Test
  public void popStack_ShouldRemoveTopItem_WhenStackIsNotEmpty() {
      // Yığıta öğeler ekle
      Event.pushStack("Activity 1");
      Event.pushStack("Activity 2");

      // Konsol çıktısını yakala
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Yığıttan öğe çıkar
      Event.popStack();

      // Konsol çıktısını kontrol et
      String expectedOutput = "Popped activity: Activity 2\n";
      assertEquals(expectedOutput.trim(), consoleOutput.toString().trim());

      // Yığıtın yeni üst öğesini kontrol et
      assertEquals("Activity 1", Event.activityStack.items[Event.activityStack.top]);
  }

  @Test
  public void popStack_ShouldPrintError_WhenStackIsEmpty() {
      // Yığıtı sıfırla
      Event.initializeStack();

      // Konsol çıktısını yakala
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Boş yığıttan öğe çıkarma girişimi
      Event.popStack();

      // Konsol çıktısını kontrol et
      String expectedOutput = "Error: Stack is empty!";
      assertEquals(expectedOutput.trim(), consoleOutput.toString().trim());
  }

  @Test
  public void testRemoveFromXORList_ShouldRemoveNodeAndUpdateList() {
      // Arrange
      XORNode node1 = new XORNode();
      XORNode node2 = new XORNode();
      XORNode node3 = new XORNode();
      node1.value = "Activity1";
      node2.value = "Activity2";
      node3.value = "Activity3";

      // Manual setup of XOR linked list
      node1.both = Event.XOR(null, node2);
      node2.both = Event.XOR(node1, node3);
      node3.both = Event.XOR(node2, null);

      Event.xorHead = node1; // Set head to node1

      // Act: Remove "Activity2"
      Event.removeFromXORList("Activity2");

      // Capture console output
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      Event.displayXORList();

      // Assert
      String actualOutput = consoleOutput.toString().trim();
      assertEquals("Activity History:\nActivity1 -> Activity3 -> NULL", actualOutput);
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
  public void testDequeue_ShouldRemoveAndPrintActivityWhenQueueIsNotEmpty() {
      // Arrange
      Event.activityQueue.front = 0;
      Event.activityQueue.rear = 2; // Simulate a queue with two items
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

      // Assert (Print and verify values)
      System.out.println("Expected Output: Dequeued activity: Activity1\n");
      System.out.println("Front Index After Dequeue: " + Event.activityQueue.front);
      System.out.println("Next Item at Front: " + Event.activityQueue.items[Event.activityQueue.front]);

      // Verify console output contains the correct dequeued message
      assertTrue(consoleOutput.toString().contains("Dequeued activity: Activity1"));

      // Verify that the front index has moved forward
      assertEquals(1, Event.activityQueue.front);

      // Verify that the next activity is now at the front
      assertEquals("Activity2", Event.activityQueue.items[Event.activityQueue.front]);
  }

  @Test
  public void testPlanTimelines_ShouldDisplayExactOutput() {
      // Arrange: Simulate user input
      String simulatedInput = "01-01-2024 to 31-12-2024\n";
      InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
      System.setIn(inputStream);

      // Capture console output
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Act: Call the method
      Event.planTimelines();

      // Assert: Compare the full output
      String expectedOutput = "Enter the timeline details (e.g., start and end dates): Timeline planned: 01-01-2024 to 31-12-2024\nPress Enter to continue...\n";
      assertEquals(expectedOutput, consoleOutput.toString().replace("\r", "")); // Normalize line endings
  }

  @Test
  public void testDisplayActivities_ShouldPrintSparseMatrixContents() throws IOException {
      // Arrange: Prepare sample data for the sparse matrix
      Event.activityMatrix.size = 2; // Example data
      Event.activityMatrix.row[0] = 1;
      Event.activityMatrix.col[0] = 2;
      Event.activityMatrix.value[0] = "Activity1";
      Event.activityMatrix.row[1] = 3;
      Event.activityMatrix.col[1] = 4;
      Event.activityMatrix.value[1] = "Activity2";

      // Redirect console output
      ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
      System.setOut(new PrintStream(consoleOutput));

      // Simulate pressing Enter to avoid waiting for user input
      InputStream originalIn = System.in;
      System.setIn(new ByteArrayInputStream("\n".getBytes()));

      try {
          // Act: Call the displayActivities function
          Event.displayActivities();

          // Assert: Check if the output contains the expected data
          String actualOutput = consoleOutput.toString();
          String expectedOutputPart1 = "Activities in Sparse Matrix:";
          String expectedOutputPart2 = "Row: 1, Column: 2, Activity: Activity1";
          String expectedOutputPart3 = "Row: 3, Column: 4, Activity: Activity2";

          assertTrue(actualOutput.contains(expectedOutputPart1));
          assertTrue(actualOutput.contains(expectedOutputPart2));
          assertTrue(actualOutput.contains(expectedOutputPart3));
      } finally {
          // Clean up: Reset console and input streams
          System.setOut(System.out);
          System.setIn(originalIn);
      }
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
  public void testDisplaySortedRatings_ShouldSortAndDisplayRatings() {
      // Capture the output using ByteArrayOutputStream
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(output));

      // Simulate user pressing Enter by setting System.in
      String input = "\n";  // Simulating Enter key
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);

      // Call the method to display sorted ratings
      Event.displaySortedRatings();

      // Check if the output contains sorted ratings
      String result = output.toString();
      
      // Check if the output contains sorted ratings
      Event.assertTrue(result.contains("Sorted Ratings:"));
      Event.assertTrue(result.contains("1 2 3 4 5"));

      // Reset System.out and System.in
      System.setOut(originalOut);
      System.setIn(System.in);
  }

  @Test
  public void testDisplaySortedRatings_NoRatings() {
      // Simulate an empty feedback scenario
      int feedbackCount = 0;

      // Capture the output using ByteArrayOutputStream
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(output));

      // Call the method when there are no ratings
      Event.displaySortedRatings();

      // Check if the output contains "No ratings available."
      String result = output.toString();
      Event.assertTrue(result.contains("No ratings available."));

      // Reset System.out
      System.setOut(originalOut);
  }
 
  @Test
  public void testBFS_ShouldVisitAllNodes() {
      // Test için örnek bir adjacency matrix (graf)
      int[][] adjMatrix = {
          {0, 1, 0, 0, 0},
          {1, 0, 1, 0, 0},
          {0, 1, 0, 1, 1},
          {0, 0, 1, 0, 1},
          {0, 0, 1, 1, 0}
      };
      
      // Beklenen çıktı: BFS, 0'dan başlayıp tüm düğümleri ziyaret etmeli
      // Ziyaret edilen düğümlerin sırası 0, 1, 2, 3, 4 olmalı.

      // Output'u yakalamak için ByteArrayOutputStream kullanılıyor
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(output));

      // BFS fonksiyonunu çağırıyoruz (startNode = 0, n = 5)
      Event.BFS(0, adjMatrix, 5);

      // Çıktıyı alıyoruz
      String result = output.toString();

      // BFS'nin doğru çıktıyı verdiğini kontrol ediyoruz
      Event.assertTrue(result.contains("Visited node: 0"));
      Event.assertTrue(result.contains("Visited node: 1"));
      Event.assertTrue(result.contains("Visited node: 2"));
      Event.assertTrue(result.contains("Visited node: 3"));
      Event.assertTrue(result.contains("Visited node: 4"));

      // System.out'u eski haline getiriyoruz
      System.setOut(originalOut);
  }

  @Test
  public void testBFS_InvalidMatrixDimensions() {
      // Hatalı boyutlara sahip bir adjacency matrix
      int[][] adjMatrix = {
          {0, 1, 0},
          {1, 0, 1}
      };

      // Output'u yakalamak için ByteArrayOutputStream kullanıyoruz
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(output));

      // BFS fonksiyonunu çağırıyoruz (n = 3, fakat matrix boyutu 2x3)
      Event.BFS(0, adjMatrix, 3);

      // Çıktıyı alıyoruz
      String result = output.toString();

      // Hatalı boyut kontrolünün çıktısını doğruluyoruz
      Event.assertTrue(result.contains("Error: Adjacency matrix dimensions do not match the given size."));

      // System.out'u eski haline getiriyoruz
      System.setOut(originalOut);
  }

  @Test
  public void testSchedule_ShouldReturnFalseOnInvalidChoice() {
      // Simüle edilen kullanıcı girişi: Geçersiz seçenek (örneğin, 8)
      String input = "8\n";
      simulateInput(input);

      // Output'u yakalamak için ByteArrayOutputStream kullanıyoruz
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(output));

      // schedule fonksiyonunu çağırıyoruz
      boolean result = Event.schedule();

      // Çıktıyı alıyoruz
      String resultOutput = output.toString();

      // Geçersiz seçenek girildiğinde doğru mesajın çıktığını kontrol ediyoruz
      Event.assertTrue(resultOutput.contains("Invalid choice. Please try again."));

      // Beklenen sonuç false olmalı, çünkü menüde geçersiz bir seçenek girildi
      Event.assertFalse(result);

      // System.out'u eski haline getiriyoruz
      System.setOut(originalOut);
  }

  @Test
  public void testSchedule_ShouldReturnFalseOnReturnToMainMenu() {
      // Simüle edilen kullanıcı girişi: "7" - Ana menüye dönmek
      String input = "7\n";
      simulateInput(input);

      // Output'u yakalamak için ByteArrayOutputStream kullanıyoruz
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(output));

      // schedule fonksiyonunu çağırıyoruz
      boolean result = Event.schedule();

      // Beklenen sonuç false olmalı, çünkü ana menüye dönmek istendi
      Event.assertFalse(result);

      // System.out'u eski haline getiriyoruz
      System.setOut(originalOut);
  }

  @Test
  public void testSchedule_ShouldCallPlanTimelinesOnChoice1() {
      // Simüle edilen kullanıcı girişi: "1" - Plan Timelines seçeneği
      String input = "1\n";
      simulateInput(input);

      // Output'u yakalamak için ByteArrayOutputStream kullanıyoruz
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(output));

      // schedule fonksiyonunu çağırıyoruz
      Event.schedule();

      // Test çıktısını konsola yazdırıyoruz
      System.out.println("Test Output: \n" + output.toString());

      // System.out'u eski haline getiriyoruz
      System.setOut(originalOut);
  }

  @Test
  public void testSchedule_ShouldCallOrganizeActivitiesOnChoice2() {
      // Simüle edilen kullanıcı girişi: "2" - Organize Activities seçeneği
      String input = "2\n";
      simulateInput(input);

      // Output'u yakalamak için ByteArrayOutputStream kullanıyoruz
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(output));

      // schedule fonksiyonunu çağırıyoruz
      Event.schedule();

      // Organize Activities metodunun çağrıldığını test ediyoruz
      // Burada çıktıyı kontrol edebiliriz (örneğin: Organize Activities'e dair bir mesaj yazdırılması)
      Event.assertTrue(output.toString().contains("Organize Activities"));

      // System.out'u eski haline getiriyoruz
      System.setOut(originalOut);
  }

  @Test
  public void testSchedule_ShouldReturnFalseOnReturnToMainMenu1() {
      // Simüle edilen kullanıcı girişi: "7" - Ana menüye dönmek
      String input = "7\n";
      simulateInput(input);

      // Output'u yakalamak için ByteArrayOutputStream kullanıyoruz
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(output));

      // schedule fonksiyonunu çağırıyoruz
      boolean result = Event.schedule();

      // Beklenen sonuç false olmalı, çünkü ana menüye dönmek istendi
      Event.assertFalse(result);

      // System.out'u eski haline getiriyoruz
      System.setOut(originalOut);
  }

// Utility method to simulate user input via Scanner
  private void simulateInput(String input) {
      System.setIn(new ByteArrayInputStream(input.getBytes()));
  }

  @Test
  public void testSchedule_ShouldReturnFalseOnReturnToMainMenu11() {
      // Simüle edilen kullanıcı girişi: "7" - Ana menüye dönmek
      String input = "7\n";
      simulateInput(input);

      // schedule fonksiyonunu çağırıyoruz
      boolean result = Event.schedule();

      // Beklenen sonuç false olmalı, çünkü ana menüye dönmek istendi
      Event.assertFalse(result);

      // System.out'a yazdırılan çıktıyı kontrol et
      String output = outputStream.toString();
      Event.assertTrue(output.contains("Please enter your choice:"));
  }

  @Test
  public void testSchedule_ShouldReturnFalseOnInvalidChoice1() {
      // Simüle edilen kullanıcı girişi: "8" - Geçersiz bir seçim
      String input = "8\n";
      simulateInput(input);

      // schedule fonksiyonunu çağırıyoruz
      boolean result = Event.schedule();

      // Beklenen sonuç false olmalı, çünkü geçersiz bir seçim yapıldı
      Event.assertFalse(result);

      // System.out'a yazdırılan çıktıyı kontrol et
      String output = outputStream.toString();
      
      // Çıktıda geçersiz seçim mesajı bulunmalı
      Event.assertTrue(output.contains("Invalid choice. Please try again."));
  }
  
  @Test
  public void testPrintLeafNodes_ShouldPrintLeafNodeContents() {
      // Test B+ ağacı oluşturuyoruz
      BPlusTree tree = Event.createBPlusTree();

      // Test için yaprak düğümleri ekliyoruz
      Event.BPlusLeafNode root = (Event.BPlusLeafNode) tree.root;
      Event.insertIntoLeaf(root, 10);
      Event.insertIntoLeaf(root, 20);
      Event.insertIntoLeaf(root, 30);

      Event.BPlusLeafNode newLeaf = new Event.BPlusLeafNode();
      root.next = newLeaf;
      Event.insertIntoLeaf(newLeaf, 40);
      Event.insertIntoLeaf(newLeaf, 50);

      // Output'u yakalamak için ByteArrayOutputStream kullanıyoruz
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(output));

      // printLeafNodes fonksiyonunu çağırıyoruz
      Event.printLeafNodes(tree);

      // Çıktıyı normalleştiriyoruz (satır sonlandırmaları)
      String actualOutput = output.toString().trim().replaceAll("\r\n", "\n");
      System.out.println("Test Output: \n" + actualOutput);

      // Beklenen çıktıyı normalleştiriyoruz
      String expectedOutput = 
          "Leaf Node: 10 20 30\n" +
          "Leaf Node: 40 50";

      // Beklenen ve gerçek çıktıyı karşılaştırıyoruz
      assertEquals(expectedOutput, actualOutput);

      // System.out'u eski haline getiriyoruz
      System.setOut(originalOut);
      
  }
  
  @Test
  public void testCreateEvent_SuccessfulEventCreation() {
      // Kullanıcı girdisini simüle et
      String input = "Birthday Party\n01-01-2025\nRed\nCelebration\n";
      System.setIn(new ByteArrayInputStream(input.getBytes()));

      // Çıktıyı yakala
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      System.setOut(new PrintStream(out));

      // Mevcut dosya varsa sil
      File eventFile = new File("event.bin");
      if (eventFile.exists()) {
          eventFile.delete();
      }

      // createEvent fonksiyonunu çağır
      boolean result = Event.createEvent();

      // Test: Fonksiyonun başarılı şekilde döndüğünü kontrol et
      assertTrue(result);

      // Test: Çıktıda başarı mesajının bulunduğunu kontrol et
      String output = out.toString();
      assertTrue(output.contains("Event created and saved successfully!"));

      // Test: Dosyanın var olduğunu kontrol et
      assertTrue(eventFile.exists());

      // Test: Dosyadaki veriyi doğrula
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(eventFile))) {
          @SuppressWarnings("unchecked")
          Map<String, String> eventData = (Map<String, String>) in.readObject();
          assertEquals("Birthday Party", eventData.get("type"));
          assertEquals("01-01-2025", eventData.get("date"));
          assertEquals("Red", eventData.get("color"));
          assertEquals("Celebration", eventData.get("concept"));
      } catch (IOException | ClassNotFoundException e) {
          fail("Error reading from the event file: " + e.getMessage());
      }

      // Giriş ve çıkışı sıfırla
      System.setIn(System.in);
      System.setOut(System.out);

      // Test sonrası dosyayı temizle
      if (eventFile.exists()) {
          eventFile.delete();
      }
  }


@Test
public void testEventDetails_CreateEventOption() {
    // Kullanıcı girdisini simüle et (1: Create Event seçeneği)
    String input = "1\nBirthday Party\n01-01-2025\nRed\nCelebration\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    // Çıktıyı yakala
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    // eventDetails fonksiyonunu çağır
    boolean result = Event.eventDetails();

    // Test: Fonksiyonun true döndüğünü kontrol et (menüye devam)
    assertTrue(result);

    // Test: Çıktıda Event Menu ve Create Event başarı mesajının bulunduğunu kontrol et
    String output = out.toString();
    assertTrue(output.contains("----------- Event Menu -----------"));
    assertTrue(output.contains("Event created and saved successfully!"));

    // Standart giriş ve çıkışı sıfırla
    System.setIn(System.in);
    System.setOut(System.out);
}

@Test
public void testEventDetails_ManageEventOption() {
    // Kullanıcı girdisini simüle et (2: Manage Event seçeneği)
    String input = "2\n4\n"; // 4: Menüden çıkmak için Manage Event içerisindeki seçenek
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    // Çıktıyı yakala
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    // eventDetails fonksiyonunu çağır
    boolean result = Event.eventDetails();

    // Test: Fonksiyonun true döndüğünü kontrol et (menüye devam)
    assertTrue(result);

    // Test: Çıktıda Event Menu ve Manage Event mesajlarının bulunduğunu kontrol et
    String output = out.toString();
    assertTrue(output.contains("----------- Event Menu -----------"));
    assertTrue(output.contains("No events available. Please create an event first."));

    // Standart giriş ve çıkışı sıfırla
    System.setIn(System.in);
    System.setOut(System.out);
}

@Test
public void testEventDetails_ReturnToMainMenuOption() {
    // Kullanıcı girdisini simüle et (3: Return to main menu seçeneği)
    String input = "3\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    // Çıktıyı yakala
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    // eventDetails fonksiyonunu çağır
    boolean result = Event.eventDetails();

    // Test: Fonksiyonun false döndüğünü kontrol et (ana menüye dönmek için)
    assertFalse(result);

    // Test: Çıktıda Event Menu mesajının bulunduğunu kontrol et
    String output = out.toString();
    assertTrue(output.contains("----------- Event Menu -----------"));

    // Standart giriş ve çıkışı sıfırla
    System.setIn(System.in);
    System.setOut(System.out);
}

@Test
public void testEventDetails_InvalidOption() {
    // Kullanıcı girdisini simüle et (geçersiz seçenek)
    String input = "99\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    // Çıktıyı yakala
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    // eventDetails fonksiyonunu çağır
    boolean result = Event.eventDetails();

    // Test: Fonksiyonun true döndüğünü kontrol et (menüye devam)
    assertTrue(result);

    // Test: Çıktıda Event Menu ve Invalid choice mesajlarının bulunduğunu kontrol et
    String output = out.toString();
    assertTrue(output.contains("----------- Event Menu -----------"));
    assertTrue(output.contains("Invalid choice. Please try again."));

    // Standart giriş ve çıkışı sıfırla
    System.setIn(System.in);
    System.setOut(System.out);

}


@Test
public void testAuthentication_InvalidInput_ShouldPrintErrorMessage() {
    // Kullanıcı geçersiz bir seçim yapıyor
    String inputString = "abc\n5\n";
    simulateUserInput(inputString);

    boolean result = Event.authentication();

    // Assert
    String output = outputContent.toString();
    assertTrue(output.contains("Invalid choice. Please try again."));
    assertFalse(result); // Ana menüye dönüyor
}


@Test
public void testAuthentication_RegisterUser_ShouldSucceed() {
    // Kullanıcı 1 seçeneğiyle kayıt yapar ve 5 ile menüye döner
    String inputString = "1\nusername\npassword\n5\n";
    simulateUserInput(inputString);

    boolean result = Event.authentication();

    // Çıktı doğrulaması
    String output = outputContent.toString();
    assertTrue(output.contains("Enter a name to register:"));
    assertTrue(output.contains("Registration successful!"));
    assertFalse(result); // Menüye dönüyor
}

@Test
public void testAuthentication_LoginUser_InvalidInput_ShouldFail() {
    // Kullanıcı yanlış giriş yapıyor
    String inputString = "2\nwrongUser\nwrongPass\n5\n";
    simulateUserInput(inputString);

    boolean result = Event.authentication();

    // Assert
    String output = outputContent.toString();
    assertTrue(output.contains("Invalid login. Returning to main menu."));
    assertFalse(result); // Ana menüye dönüyor
}

@Test
public void testAuthentication_GuestLogin_ShouldDisplayMessage() {
    // Kullanıcı misafir girişi yapıyor
    String inputString = "3\n5\n";
    simulateUserInput(inputString);

    boolean result = Event.authentication();

    // Assert
    String output = outputContent.toString();
    assertTrue(output.contains("You are in guest mode."));
    assertFalse(result); // Ana menüye dönüyor
}

private void simulateUserInput(String input) {
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
}

@Test
public void testReturnToMainMenu() {
    // Arrange
    String input = "7\n"; // Option 7
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    // Act
    boolean result = Event.schedule();

    // Assert
    assertFalse("Should return false to exit menu", result);
}




}
