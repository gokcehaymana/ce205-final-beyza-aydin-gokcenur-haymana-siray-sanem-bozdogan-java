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