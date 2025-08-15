package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeriesModelTest {
    private SeriesModel seriesModel;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        seriesModel = new SeriesModel();
    }

    // Helper method to simulate user input
    private void provideInput(String data) {
        InputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);
        // Reset scanner in the model (requires reflection or package-private access)
        // This is a workaround since Scanner is created internally
        seriesModel = new SeriesModel();
    }

    @Test
    void captureSeries_ShouldStoreValidInputInMemory() {
        // Arrange
        String input = "S123\nGame of Thrones\n16\n73\n";
        provideInput(input);

        // Act
        seriesModel.captureSeries();

        // Assert
        assertNotNull(seriesModel.getMemory()[0][0]); // Requires getter or reflection
        assertEquals("S123", seriesModel.getMemory()[0][0]);
        assertEquals("Game of Thrones", seriesModel.getMemory()[0][1]);
        assertEquals("16", seriesModel.getMemory()[0][2]);
        assertEquals("73", seriesModel.getMemory()[0][3]);
    }

    @Test
    void captureSeries_ShouldValidateAgeRestriction() {
        // Arrange
        String input = "S123\nBreaking Bad\n1\n19\n16\n50\n";
        provideInput(input);

        // Act
        seriesModel.captureSeries();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Error: Age must be between 2 and 18!"));
        assertEquals("16", seriesModel.getMemory()[0][2]);
    }

    @Test
    void captureSeries_ShouldValidateEpisodeCount() {
        // Arrange
        String input = "S123\nThe Witcher\n16\n0\n-5\n24\n";
        provideInput(input);

        // Act
        seriesModel.captureSeries();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Error: Must have at least 1 episode!"));
        assertEquals("24", seriesModel.getMemory()[0][3]);
    }

    @Test
    void captureAlgorithm_ShouldExpandStorageWhenAddingNewSeries() {
        // Arrange
        String input = "S123\nStranger Things\n16\n25\n";
        provideInput(input);

        // Act
        seriesModel.captureAlgorithm();

        // Assert
        assertEquals(1, seriesModel.getHistory().length); // Requires getter
        assertEquals("S123", seriesModel.getHistory()[0][0]);
    }


    // Test for printing output format
    @Test
    void printingConsole_ShouldFormatOutputCorrectly() {
        // Arrange
        seriesModel.setHistory(new String[1][4]); // Initialize history
        seriesModel.getHistory()[0] = new String[]{"S123", "Test Series", "16", "24"};

        // Act
        seriesModel.printingConsole(0);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("SERIES ID                   :   S123"));
        assertTrue(output.contains("SERIES NAME                 :   Test Series"));
        assertTrue(output.contains("AGE RESTRICTION             :   16"));
        assertTrue(output.contains("NUMBER OF EPISODES          :   24"));
    }

    // Test empty data check
    @Test
    void emptyDataChecker_ShouldHandleNullHistory() {
        // Arrange
        seriesModel.setHistory(null); // Requires setter or reflection

        // Act
        seriesModel.emptyDataChecker();

        // Assert
        assertTrue(outputStream.toString().contains("Database empty!!!"));
    }
}