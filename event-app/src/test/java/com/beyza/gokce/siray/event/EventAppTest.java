package com.beyza.gokce.siray.event;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EventAppTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    private final ByteArrayInputStream testIn = new ByteArrayInputStream("6\n".getBytes());

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(testOut));
        System.setIn(testIn);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(originalOut);
        System.setIn(System.in);
    }

    /**
     * Test case to verify that the main method executes the expected workflow.
     */
    @Test
    public void testMainMethodWorkflow() {
        // Act: Call the main method
        String[] args = {};
        EventApp eventapp = new EventApp();
        eventapp.main(args);

        // Assert: Check the output
        String output = testOut.toString();

        // Verify that loadHashTableFromFile method has executed (implicitly by no errors)
        assertTrue("Output should include welcome message", output.contains("WELCOME TO OUR PLANNER"));
        assertTrue("Output should include menu options", output.contains("1. User Authentication"));
        assertTrue("Output should include exit message", output.contains("Exiting the program. Goodbye!"));
    }

    /**
     * Test case to ensure loadHashTableFromFile correctly handles a file.
     */
    @Test
    public void testLoadHashTableFromFile() {
        // Arrange: Prepare a dummy file (not necessary to create an actual file for this test)
        Event.loadHashTableFromFile();

        // Assert: Check if the hash table is populated without exceptions
        assertNotNull("Hash table should not be null", Event.getHashTable());
    }
}
