/**
 * Alvin Nguyen
 * 11/7/2016
 * CSC 210 - MWF 9-10 am
 * This program is designed to validate credit card numbers based on Hans Luhn's algorithm.
 * 
 *
 */
package ccvalidator;

import java.util.Arrays;

public class CCValidator {

    /**
     * Return true if the card number is valid
     *
     * This function will take a credit card number as its parameter. It will first determine if the card belongs to one of the given vendors
     *
     * Then it will compute the two sums needed for the mod 10 check.
     *
     * And, finally it will perform the mod 10 check.
     */
    public static boolean isValid(long number) {
        if(isValidVendor(number) == true){
           int totalNumber = sumOfDoubleEvenPlace(number) + sumOfOddPlace(number);
        if(totalNumber % 10 == 0){
            return true; 
        }
      }
        return false;
    }
    /**
     * Determine if number matches prefix for Visa, MasterCard, American Express or Discover
     */

    public static boolean isValidVendor(long number) {
        boolean visa =  prefixMatched(number, 4);
        boolean masterCard = prefixMatched(number, 5);
        boolean americanExpress = prefixMatched(number, 37);
        boolean discover = prefixMatched(number, 6);
        
        if(visa == true){
            return true;
        }
        if(masterCard == true){
            return true;
        }
        if(americanExpress == true){
            return true;
        }
        if(discover == true){
          return true;  
        }
        else{
            return false;
        }
    }

    /**
     * Get the result from Step 2
     */

    public static int sumOfDoubleEvenPlace(long number) {
        long sum = 0;
        long lastDigit = 0;
        long doubleDigit = 0;
        long evenPlace = 0;
        while(number != 0){
            lastDigit = number % 100;
            evenPlace = lastDigit /10;
            doubleDigit = 2 * evenPlace;
            sum = sum + getDigit((int) doubleDigit);
            number = number / 100;
        }
        return (int) sum;
    }

    /**
     * Return sum of odd place digits in number
     */
    public static int sumOfOddPlace(long number) {
        long sum = 0;
        long lastDigit = 0;
        while(number != 0){
            lastDigit = number % 10;
            sum = sum + lastDigit;
            number = number / 100;  
        }
        return (int) sum;
        
    }

    /**
     * Return this number if it is a single digit, otherwise, return * the sum of the two digits
     */
    public static int getDigit(int number) {
        int x = 0;
        int y = 0;
        int sum = 0;
        if (number > 9) {
            x = number % 10;
            y = number / 10;
            sum = x + y;
            return sum;
        }
        return number;
    }

    /**
     * Return true if the digit d is a prefix for number
     */
    public static boolean prefixMatched(long number, int d) {
        int size = getSize((long) d);
        long prefix = getPrefix((number), size);
        if(prefix == d){
            return true;
        }
        return false;
    }

    /**
     * Return the number of digits in d
     */
    public static int getSize(long d) {
        int counter = 0;
        while (d > 0) {
            d /= 10;
            counter++;
        }
        return counter;
    }

    /**
     * Return the first k number of digits from number. If the number of digits in number is less than k, return number.
     */
    public static long getPrefix(long number, int k) {
        int size = getSize(number);
        if (size < k) {
            return number;
        }
        int remove_count = size - k;
        while (remove_count > 0) {
            number /= 10;
            remove_count--;
        }
        return number;
    }

    public static void main(String[] args) {

        /**
         * Begin testing for getSize Inputs: 213,452343,21312 Outputs: 3,6,5
         */
        int[] ins = {213, 452343, 21312};
        int[] outs = {3, 6, 5};
        int size = -1;
        for (int num = 0; num < ins.length; num++) {
            size = getSize(ins[num]);
            if (size != outs[num]) {
                System.out.println("getSize test has Failed\nInput : " + ins[num] + "\nOutput: " + size + "\nExpected Output: " + outs[num]);
                System.exit(-1);
            }
        }
        System.out.println("All getSize tests have passed");

        /**
         * Begin testing for getPrefix Inputs: (213,1),(452343,3),(21312,6) Outputs: 2,452,21312
         */
        int[][] t1_ins = {{213, 1}, {452343, 3}, {21312, 6}};
        int[] t1_outs = {2, 452, 21312};
        long pref = -1;
        for (int num = 0; num < t1_ins.length; num++) {
            pref = getPrefix(t1_ins[num][0], t1_ins[num][1]);
            if (pref != t1_outs[num]) {
                System.out.println("getPrefix test has Failed\nInput : " + Arrays.toString(t1_ins[num]) + "\nOutput: " + pref + "\nExpected Output: " + t1_outs[num]);
                System.exit(-1);
            }
        }
        System.out.println("All getPrefix tests have passed");

        /**
         * Begin testing for prefixMatched Inputs: (213,1),(452343,3),(21312,6) Outputs: 2,452,21312
         */
        int[][] t2_ins = {{213, 21}, {452343, 452}, {21312, 21312}, {56547, 23}};
        boolean[] t2_outs = {true, true, true, false};
        boolean boolMatched = false;
        for (int num = 0; num < t2_ins.length; num++) {
            boolMatched = prefixMatched(t2_ins[num][0], t2_ins[num][1]);
            if (boolMatched != t2_outs[num]) {
                System.out.println("prefixMatched test has Failed\nInput : " + Arrays.toString(t2_ins[num]) + "\nOutput: " + boolMatched + "\nExpected Output: " + t2_outs[num]);
                System.exit(-1);
            }
        }
        System.out.println("All prefixMatched tests have passed");

        /**
         * Begin testing for getDigit Inputs: 12,1,18,2,4 Outputs: 3,1,9,2,4
         */
        int[] t3_ins = {12, 1, 18, 2, 4};
        int[] t3_outs = {3, 1, 9, 2, 4};
        int d = -1;
        for (int num = 0; num < t3_ins.length; num++) {
            d = getDigit(t3_ins[num]);
            if (d != t3_outs[num]) {
                System.out.println("getDigit test has Failed\nInput : " + t3_ins[num] + "\nOutput: " + d + "\nExpected Output: " + t3_outs[num]);
                System.exit(-1);
            }
        }
        System.out.println("All getDigit tests have passed");

        /**
         * Begin testing for sumofOddPlace Inputs: 46546556465465l,42568559665995l,46543333465465l,465465789358565l Outputs: 36,42,31,46
         */
        long[] t4_ins = {46546556465465l, 42568559665995l, 46543333465465l, 465465789358565l};
        int[] t4_outs = {36, 42, 31, 46};
        int sum_odd = -1;
        for (int num = 0; num < t4_ins.length; num++) {
            sum_odd = sumOfOddPlace(t4_ins[num]);
            if (sum_odd != t4_outs[num]) {
                System.out.println("sumOfOddPlace test has Failed\nInput : " + t4_ins[num] + "\nOutput: " + sum_odd + "\nExpected Output: " + t4_outs[num]);
                System.exit(-1);
            }
        }
        System.out.println("All sumOfOddPlace tests have passed");

        /**
         * Begin testing for sumofOddPlace Inputs: 46546556465465l,42568559665995l,46543333465465l,465465789358565l Outputs: 36,42,31,46
         */
        long[] t5_ins = {46546556465465l, 42568559665995l, 46543333465465l, 465465789358565l};
        int[] t5_outs = {25, 30, 33, 35};
        int sum_even = -1;
        for (int num = 0; num < t5_ins.length; num++) {
            sum_even = sumOfDoubleEvenPlace(t5_ins[num]);
            if (sum_even != t5_outs[num]) {
                System.out.println("sumOfDoubleEvenPlace test has Failed\nInput : " + t5_ins[num] + "\nOutput: " + sum_even + "\nExpected Output: " + t5_outs[num]);
                System.exit(-1);
            }
        }
        System.out.println("All sumOfDoubleEvenPlace tests have passed");

        //Valid CC Number
        long validTest = 4388576018410707L;
        //invalid CC number
        long inValidTest = 4388576018402626L;

        System.out.println("Credit Card Number " + validTest + (isValid(validTest) ? " is Valid" : " is Invalid"));
        System.out.println("Credit Card Number " + inValidTest + (isValid(inValidTest) ? " is Valid" : " is Invalid"));

    }
}
