/*******************************************************************************
* NAME: HAROLD DANE C. BAGUINON                                                *
* DATE: 11/09/2016                                                             *
* DATE DUE: 12/14/2016 06:00:00 PM                                             *
* COURSE: CSC543                                                               *
* PROFESSOR: DR. PARSON                                                        *
* PROJECT: #4                                                                  *
* FILENAME: SingleCracker.java                                                 *
* PURPOSE: This program is the fourth assignment.                              *
*          Students were asked to write a program which uses multithreading.   *
*          As one of my focuses in the PSM Track is Security, I decided to     *
*          engineer a brute-force password cracker. The four types of crackers *
*          which I have coded are:                                             *
*          1. SingleCracker: Single-threaded, randomized word-building.        *
*          2. MultiCracker: Multi-threaded, randomized word-building.          *
*          3. SeqCracker: Single-threaded, sequential word-building. "a to z"  *
*          4. SplitCracker: Multi-threaded, sequential word-building. "a to z" *
*******************************************************************************/
import java.util.*;
import java.io.*;

class SingleCracker
{
    public static void main (String[] args)
    {
        Scanner scan = new Scanner( System.in );
        Random rand = new Random();

        Runtime.getRuntime().availableProcessors();

        String choices, solution, pwtest="", pwstring="";
        long attempts;
        int word_length;
        char[] password;
        char guess = 'd';

        System.out.println("Enter a password that is 4 characters long; symbols may not be used.");
        password = scan.next().toCharArray();
        for (int i = 0 ; i < password.length ; i++) {
            pwstring += password[i];
        }
        //System.out.println("Password string: " + pwstring);
        
        /*for (int i = 0 ; i < password.length ; i++) {
            System.out.println(password[i]);
        }*/
        
        word_length = password.length;

        choices = "abcdefghijklmnopqrstuvwxyz";
        //choices = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+[]{}\\;:\'\",<.>/?`~";
        solution = "";
        attempts = 0;

        System.out.println("You typed:");
        for (int i = 0 ; i < password.length ; i++) {
            System.out.print(password[i]);
        }
        System.out.println("\nThe length of your password is: " + word_length);
        // System.out.println("Guess: " + guess + "password :" + password);
        
        long startTime = System.currentTimeMillis();
        while (!solution.equals(pwstring)) { // compares solution to created string
            while (solution.length() != pwstring.length()) { // builds string until it is same length as password
                for (int i = 0 ; i < password.length ; i++) { // adds a random letter to built string until it is length of password
                    guess = choices.charAt( rand.nextInt ( choices.length() ) );
                    solution = solution + guess; // appends letter to solution
                }
                attempts++; //increments # of attempts
                System.out.println("Attempt: " + attempts + "    Guess: " + solution);
                if (solution.equals(pwstring))
                    break;
                else
                    solution = "";
            }
            
        }
        long endTime = System.currentTimeMillis();
        System.err.println("ELAPSED TIME = "
            + ((endTime-startTime) / 1000.0) + " seconds.");
    }
}