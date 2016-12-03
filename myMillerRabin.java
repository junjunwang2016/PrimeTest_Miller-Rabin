/**************************************************
I based my code on this explanation on youtube.
I also compared this explanation to others and found them to be consistent

https://www.youtube.com/watch?v=qfgYfyyBRcY


Example: 
is 561 prime? 

n = 561
subtract 1 from candidate number = 560

while (answer == int) do
    560 / 2^1  = 280
    560 / 2^2 = 140
    560 / 2^3 = 70
    560 / 2^4 = 35
    560 / 2^5 = 17.5  xxxxxxx  use line above
end while

k = 4;  m = 35

choose a =2 or 3 or 4
in this case I chose a = 2

b = a^m mod candidate
while (b != 1 or -1) do
        b = a^m mod n

        b0 = 2^35 mod 561 = 263 mod 561
        b1 = 263^2 mod 561 = 166 mod 561
        b2 = 166^2 mod 561 = 67 mod 561
        b3 = 67^2 mod 561 = 1 mod 561

end while

NOTE:  if bo (and only bo) had been either +1 OR -1, 
n would be prime (it was 263, in this example). 
BUT for b1, b2, and so on, +1 implies composite, -1 probable prime.


***************************************************/


import java.lang.*;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class myMillerRabin{
        
    
    // these variables are made global so that their creation has no effect on computation time
    private static BigInteger number;        // number  = (n-1)
    private static BigInteger candidate;     // number being tested
    private static Scanner scan = new Scanner(System.in);    // scanner for keyboard input
    private static String input;             // reads in candidate as string and passes it to BigInteger
    private static long endPrimeTest = 0;    // timer end
    private static long startPrimeTest = 0;  // timer start
    private static BigInteger testForNegOne; // tests for eg. 2 mod 3 = 2 = -1
    private static String _a;                // var to hold value for 'a'. Often 2 is used, but 'a'' can be: 1<a<(candidate-1)
    private static BigInteger loopIterator;  // used to determine when to stop loop
    private static BigInteger _k;            // BigInteger version of k for comparing loop iterator
    private static boolean test1, test2, test3;
    private static int twoToK = 0x0001;    // 2^0 = 1
    private static BigInteger two = new BigInteger("2");
    private static BigInteger aExp, b, modTest, a; 
    private static int k = 0;                //  k and m 
    private static BigInteger m;             //  
    private static String stringK;
        



    public static void main(String[] args){
        loopIterator = BigInteger.ZERO;     // initialize outside isProbablePrime method to reduce computation time
        System.out.println("Enter a candidate number: ");

        while(isValidInput() == false){     // wait for valid numerical input
        System.out.println("Error: Enter valid input!");
        }
        if(candidate.longValue() == 1){
            System.out.println("One is NOT a prime number");
        }
        else if (candidate.longValue() == 2){    // 2 is prime
            System.out.println("Two is a prime number.");
        }
        else if(candidate.longValue() % 2 == 0){    // evens are not prime
            System.out.println("Number is even, thus it is NOT prime! ");
        }
        else{
            isProbablePrime(candidate);     // run the test
            if((endPrimeTest - startPrimeTest)<5000000)    // if time less that 5ms, report in nanoseconds
                System.out.println("Time taken : " + (endPrimeTest - startPrimeTest) + " nanoseconds\n\n");
            else 
                System.out.println("Time taken : " + (endPrimeTest - startPrimeTest)/1000000 + "milliseconds");
        }
    }   ////////////end main method



    private static boolean isProbablePrime(BigInteger x){
            
            number = x.subtract(BigInteger.ONE);              // number = n-1
            //System.out.println("n - 1: " + number);                               // used for testing
            //System.out.println("candidate as bigint: " + candidate.intValue());   // used for testing 

            System.out.println("Enter an iterator: 2 is usually fine...");
            _a = scan.next();
            a = new BigInteger(_a);
            startPrimeTest = System.nanoTime(); // start timer   
            String _twoToK;
            _twoToK = Integer.toString(twoToK);
            BigInteger __twoToK;
            __twoToK = new BigInteger(_twoToK);
        // this increases the powers of 2 (starting with 2^0)that are divided by
        // to obtain the values of k and m  
        while(number.mod(__twoToK).equals(BigInteger.ZERO)){   // get highest value of k
            __twoToK = __twoToK.shiftLeft(1);                  // Bitshift left to increase power of 2
            //System.out.println(__twoToK);                    // used for testing
            k++;                                               // this final value of will be one more than the one we want
            //System.out.println("k = " + k);                  // used for testing loop         
            
        }
        k--;                                                    // use the previous value of k
            __twoToK = __twoToK.shiftRight(1);                  // and twoToK

        //System.out.println("twoToK : " + __twoToK);           // used for testing
        m = number.divide(__twoToK);                            // get value for m                                       
        stringK = String.valueOf(k);                            // read in k to BigInteger
        _k = new BigInteger(stringK);                           //
        //System.out.println("k = " + _k.toString());             // used for testing
        //System.out.println("m = " + m);                         // used for testing

        
            b = a.modPow(m,candidate);
            //System.out.println("b= "+ b + " mod " + candidate.intValue());    // used for testing
            
            // tests for a congruence of -1 eg: 2mod3 = 2 = -1
            testForNegOne = candidate.subtract(b); 

            //System.out.println("Test for neg one: " + testForNegOne.intValue());  // used for testing

            test1 = b.equals(BigInteger.ONE);                    // if initial test is 1 OR -1, then prime
            test2 = b.equals(BigInteger.ONE.negate());
            test3 = testForNegOne.equals(BigInteger.ONE);       // test for:  a^m mod candidate (congruent to) -1

            //System.out.println("Test for +1  initial test: "+test1);  // used for testing
            //System.out.println("Test for -1 initial test: "+test2);  // used for testing
            //System.out.println("Test for -1 Congruence: " + test3);   // used for testing

            // if test1, 2, or 3 return true for b0, then candidate is a probable prime 
            if(test1 == true || test2 == true || test3 == true){
                System.out.println("Candidate is probable prime \nUse more iterators to increase probability");
                endPrimeTest = System.nanoTime();
                return true;
            }
            else{  // otherwise keep testing
                while(!test1 && !test2 && !test3){
                    if (loopIterator.equals(_k)){        // if we reach 'k' squarings and havent got 1 or -1, then n is composite
                        //System.out.println("k : " + loopIterator.toString());     // used for testing
                        System.out.println("'k' has reached maximum iterations. \nNumber is not prime.");
                        endPrimeTest = System.nanoTime();
                        return false;
                    }                                   //  otherwise keep going
                    b = b.modPow(two, candidate);
                    //System.out.println("b :" + b.toString());
                    loopIterator =  loopIterator.add(BigInteger.ONE);   // loopIterator ++
                    //System.out.println("loop iterator: " + loopIterator.toString());      // used for testing
                    modTest = candidate.subtract(b);
                    //System.out.println("b = " + b + ", -" + modTest);     // used for testing
                    test1 = b.equals(BigInteger.ONE);           // is b == 1 ?
                    test2 = b.equals(BigInteger.ONE.negate());  // is b== -1 ?
                    test3 = modTest.equals(BigInteger.ONE);     // is b congruent to -1 ?
                    //System.out.println("Test 1: "+ test1);                // used for testing
                    //System.out.println("Test 2:" + test2);                // used for testing
                    //System.out.println("Test 3:" + test3);                // used for testing
                
                // sleep used for testing purposes
                /*   
                    try {
                        Thread.sleep(1000);
                        } 
                        catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                    }
                    */
            }
            if (test1){     // if bn = 1, then the number is composite    
                System.out.println("Implied Composite");
                endPrimeTest = System.nanoTime();
                return false;

            }
            else{           // if bn = -1, or is congruent to -1, then candidate is a probable prime
                System.out.println("Probable Prime \nUse more iterators to increase probability");
                //System.out.println("b=  "+ b.intValue());     // used for testing
                endPrimeTest = System.nanoTime();
                return true;
        }
        }
    }       // end isProbablePrime method



    // Method to check input to see if it is a valid integer input
    private static boolean isValidInput(){   
        try{
            input = scan.next();
            candidate = new BigInteger(input);
        }

        catch (NumberFormatException exception){
            //System.out.println("Bad input detected");  // used for debugging
            return false;
        }
        return true;
    }   // end isValidInput method   

}      // end myMillerRabin class