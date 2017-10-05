/*******************************************************************************
* NAME: HAROLD DANE C. BAGUINON                                                *
* DATE: 11/09/2016                                                             *
* DATE DUE: 12/14/2016 06:00:00 PM                                             *
* COURSE: CSC543                                                               *
* PROFESSOR: DR. PARSON                                                        *
* PROJECT: #4                                                                  *
* FILENAME: SplitCracker.java                                                  *
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
import java.util.concurrent.* ;

class SplitCracker
{
    //public long attempts = 0;
    volatile static Boolean stopAllThreads = false;
    
    public static void main (String[] args)
    {
        
        try { 
            ThreadManager<Thread> t = new ThreadManager<Thread>();
            
            Scanner scan = new Scanner( System.in );
            Random rand = new Random();

            //Runtime.getRuntime().availableProcessors();

            String choices, solution, pwtest="", pwstring="";
            long attempts = 0;
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
            //attempts = 0;

            System.out.println("You typed:");
            for (int i = 0 ; i < password.length ; i++) {
                System.out.print(password[i]);
            }
            System.out.println("\nThe length of your password is: " + word_length);
            // System.out.println("Guess: " + guess + "password :" + password);
            
            int numthreads = 4;
            Calculate [] calculators = new Calculate[numthreads];
            Thread[] threads = new Thread[numthreads];
            
            for (int i = 0 ; i < calculators.length ; i++) {
                calculators[i] = new Calculate(solution, pwstring, password, guess,
                    choices, rand, attempts, i, t, stopAllThreads);
                threads[i] = new Thread(calculators[i]);
                t.add(threads[i]);
            }
            
            // Wait to start threads until all are ready to pile up bottleneck.
            long startTime = System.currentTimeMillis();
            for (int i = 0 ; i < calculators.length ; i++) {
                threads[i].start();
            }
            for (int i = 0 ; i < calculators.length ; i++) {
                threads[i].join();
            }
            long endTime = System.currentTimeMillis();
            System.err.println("ELAPSED TIME = "
                + ((endTime-startTime) / 1000.0) + " seconds.");
        
        } catch (Exception exx) {
            System.err.println("EXCEPTION TYPE " + exx.getClass().getName()
                + ", MESSAGE = " + exx.getMessage());
        }
        
    }
    
    //@ThreadSafe
    //private static class Calculate implements Runnable {
    private static class Calculate extends Thread {
        
        static boolean signalled = false;

        private ThreadManager m;
        
        private String solution;
        private String pwstring;
        private char[] password;
        private char guess;
        private String choices;
        private Random rand;
        private long attempts;
        private int i;
        private char beginning; //a, h, n, u
        private char ending; //g, m, t, z
        private Calculate (String solution, String pwstring, char[] password,
            char guess, String choices, Random rand, long attempts, int i, ThreadManager tm,
            Boolean stopAllThreads) {
            this.solution = solution;
            this.pwstring = pwstring;
            this.password = password;
            this.guess = guess;
            this.choices = choices;
            this.rand = rand;
            this.attempts = attempts;
            this.i = i;
            m = tm;
            if (i == 0) {
                beginning = 'a';
                ending = 'h';
            } else if (i == 1) {
                beginning = 'h';
                ending = 'n';
            } else if (i == 2) {
                beginning = 'n';
                ending = 'u';
            } else if (i == 3) {
                beginning = 'u';
                ending = '{';
            } 
        }
        public void run(){
            try {
                // periodically check ...
                if (this.interrupted()) throw new InterruptedException();
                if (stopAllThreads) throw new InterruptedException();
                for (char a = beginning; a < ending; a++) {
                    for (char b = 'a'; b < '{'; b++) {
                        for (char c = 'a'; c < '{'; c++) {
                            for (char d = 'a'; d < '{'; d++) {
                                //for (char f = 'a'; f < '{'; f++) {
                                    solution = solution + a;
                                    solution = solution + b;
                                    solution = solution + c;
                                    solution = solution + d;
                                    //solution = solution + f;
                                    attempts++;
                                    System.out.println("Thread: " + i + " Attempt: " + attempts + " Guess: " + solution);
                                    if (stopAllThreads) throw new InterruptedException();
                                    if (solution.equals(pwstring)) {
                                        stopAllThreads = true;
                                        throw new InterruptedException();
                                    } else {
                                        solution = "";
                                    }
                                //}
                            }
                        }
                    }
                }

            } catch (Exception e) {
                synchronized(getClass()) {
                    if (!signalled) {
                        signalled = true;
                        m.stopThreads();
                    }
                }
            }
        }
        
    }
    
}


class ThreadManager<T> extends ArrayList<T> {

    public void stopThreads() {
        for (T t : this) {
            Thread thread = (Thread) t;
            if (thread.isAlive()) {
                try { thread.interrupt(); } 
                catch (Exception e) {/*ignore on purpose*/}
            }
        }
    }
}
    
