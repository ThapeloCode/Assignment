package org.example;

import java.util.Arrays;
import java.util.Scanner;

public class SeriesModel {

    private String seriesId;
    private String seriesName;
    private String seriesAge;
    private String seriesNumberOfEpisodes;
    private byte select;
    private int size = 0;
    private final byte COLUMNS = 4;

    private final Scanner scanner = new Scanner(System.in);
    String[][] elasticStorage;
    private final String[][] memory = new String[1][COLUMNS];
    private String[][] history;

    public void launch(){

        System.out.println("\nLATEST SERIES - 2025");
        System.out.println("*************************************************");
        System.out.println("Enter (1) to launch menu or any other key to exit");

        select = scanner.nextByte();

        if (select == 1) {
            menu();
        } else {
            System.exit(0);
        }

    }

    private void menu(){

        System.out.println("\n");
        System.out.println("Please select one of the following menu items: ");
        System.out.println("(1) Capture a new series.");
        System.out.println("(2) Search for a series.");
        System.out.println("(3) Update series age restriction.");
        System.out.println("(4) Delete a series.");
        System.out.println("(5) Print series report.");
        System.out.println("(6) Exit Application.");

        select = scanner.nextByte();

        switch (select){
            case 1:
                captureAlgorithm();
                launch();
                break;
            case 2:
                System.out.println("Enter the series id to search   :  ");
                System.out.println("******************************************");
                seriesId = scanner.next();
                searchSeries(seriesId);
                launch();
                break;
            case 3:
                System.out.println("Enter the series id to update   :  ");
                System.out.println("******************************************");
                seriesId = scanner.next();
                updateSeries(seriesId);
                launch();
                break;
            case 4:
                System.out.println("Delete a series   :  ");
                System.out.println("******************************************");
                seriesId = scanner.next();
                removalAlgorithm(seriesId);
                launch();
                break;

            case 5:
                printSeries();
                launch();
                break;
            case 6:
                launch();
                break;
        }

    }

    private void removalAlgorithm(String seriesId) {
        // Check if history is empty or invalid
        if (history == null || history.length == 0 || history[0] == null) {
            System.out.println("History is Empty!!!");
            return; // Nothing to delete
        }

        // Find the row index with matching seriesId (assuming ID is in column 0)
        int rowToDelete = -1;
        for (int row = 0; row < history.length; row++) {
            if (history[row][0].equals(seriesId)) {
                rowToDelete = row;
                break;
            }
        }

        // If ID not found, exit
        if (rowToDelete == -1)
            return;

        // Create new storage with reduced size
        size--;
        String[][] elasticStorage = new String[size][COLUMNS];

        // Copy all rows except the deleted one
        for (int row = 0, newRow = 0; row < history.length; row++) {
            if (row != rowToDelete) {
                for (int column = 0; column < COLUMNS; column++) {
                    elasticStorage[newRow][column] = history[row][column];
                }
                newRow++;
            }
        }

        // Update history reference
        history = elasticStorage;
    }

    //Flexible Memory/Array"
    private void captureAlgorithm(){
        /*This method will start off by increasing the elastic Array(elasticStorage[][]) by one from the previous elastic,
        Secondly it will add history Array(history[][]) into the new expanded elastic Array(elasticStorage[][]),
        Thirdly it will take new input from user and add into memory Array(memory[][]) into the elastic Array.
        Meaning both history[][] and new data(memory[][]) will be captured in the newly expanded elastics Array(elasticStorage[][]),
        Lastly it will take all the data from the elastic Array and Add it to History Array(history[][])*/

        //Increase The Elastic Storage by Single Row From History
        size++;
        String[][] elasticStorage = new String[size][COLUMNS];

        //Checking Empty History
        if (Arrays.deepToString(history).isEmpty() || Arrays.deepToString(history).contains("null")){
                captureSeries();
                history = memory;
        }else {
            //If History Contains Objects
            int row;
            for (row = 0; row < history.length; row++){
                //Copy History Rows (to expandable memory)
                for (int column = 0; column < COLUMNS; column++){
                    elasticStorage[row][column] = history[row][column];
                }
            }
            //If History Contains Objects
            //Take New Input From User
            captureSeries();
            for (int coloumb = 0; coloumb < COLUMNS; coloumb++){
                //Take The New Input to Freshly Expanded elastic memory
                elasticStorage[row][coloumb] = memory[0][coloumb];
            }
            //Take All the Expanded Array(elasticStorage) and Copy to History
            history = elasticStorage;
        }
    }

    private void searchSeries(String seriesId){

        //Loop Through history
        for (int x = 0; x < history.length; x++ ){
            //Check for ID
            if ((history[x][0].contains(seriesId))){
                //Print it if found
                printingConsole(x);
                break;
            } else {
                //if not found in Loop
                if ((x == (history.length)-1))
                    System.out.println("It does not exist!!!");
            }

        }

    }

    //Update Method (Excluding ID)
    private void updateSeries(String seriesId){

        for (int x = 0; x < history.length; x++ ){

            if (history[x][0].contains(seriesId)){

                System.out.println("-------------------------------------------");
                System.out.println("Update Series  " +  (x+1));
                System.out.println("===========================================");
                inputSetter(0);
                copyToHistory(x);
                break;
            } else {

                if (x == (history.length-1))
                    System.out.println("It does not exist!!!");
            }

        }
    }

    //Setter Method (All input)
    private void captureSeries(){

        System.out.println("CAPTURE A NEW SERIES");
        System.out.println("******************************************");

        System.out.print("Enter Series ID               :   ");
        String seriesId = scanner.next();
        memory[0][0] = seriesId;

        inputSetter(0);
    }

    //Setter Method (Excluding ID)
    private void inputSetter(int x){

        scanner.nextLine();
        //\n

        System.out.print("Enter Series Name             :   ");
        seriesName = scanner.nextLine();
        memory[x][1] = seriesName;

        System.out.print("Enter Series Age Restriction  :   ");
        seriesAge = scanner.next();
        memory[x][2] = seriesAge;

        System.out.print("Number Of Episodes            :   ");
        seriesNumberOfEpisodes = scanner.next();
        memory[x][3] = seriesNumberOfEpisodes;

    }

    //This Method Copy Current Memory Row to History x Row
    private void copyToHistory(int x){

        history[x][1] = seriesName;
        history[x][2] = seriesAge;
        history[x][3] = seriesNumberOfEpisodes;

    }

    //Print Row x
    private void printingConsole(int x){

        System.out.println("Series  " +  (x + 1));
        System.out.println("-------------------------------------------");
        System.out.println("SERIES ID                   :   " + history[x][0]);
        System.out.println("SERIES NAME                 :   " + history[x][1]);
        System.out.println("AGE RESTRICTION             :   " + history[x][2]);
        System.out.println("NUMBER OF EPISODES          :   " + history[x][3]);
        System.out.println("-------------------------------------------");

    }

    //Print All Existing Rows
    private void printSeries(){
        if (this.history == null){
            System.out.println("History Empty!!!");
        }else {
            for (int x = 0; x < history.length; x++) {
                printingConsole(x);
            }
        }

    }

}
