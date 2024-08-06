import java.util.Scanner;

public class SafeInput {

    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = "";
        do{
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        }while(retString.length() == 0);
        return retString;
    }

    public static int getInt(Scanner pipe, String prompt)
    {
        int retInt = 0;
        String trash ="";
        boolean ready = false;
        do{
            System.out.print("\n" + prompt + ": ");
            if(pipe.hasNextInt())
            {
                retInt = pipe.nextInt();
                pipe.nextLine();
                ready = true;
            }
            else
            {
              trash = pipe.nextLine();
                System.out.println("\nPlease enter a valid integer, not " + trash + ".");
            }
        }while(!ready);
        return retInt;
    }

    public static double getDouble(Scanner pipe, String prompt)
    {
        double retDouble = 0;
        String trash ="";
        boolean ready = false;
        do{
            System.out.print("\n" + prompt + ": ");
            if(pipe.hasNextDouble())
            {
                retDouble = pipe.nextDouble();
                pipe.nextLine();
                ready = true;
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("\nPlease enter a valid integer, not " + trash + ".");
            }
        }while(!ready);
        return retDouble;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int lo, int hi)
    {
        int retRangedInt = 0;
        String trash ="";
        boolean ready = false;
        do{
            System.out.print("\n" + prompt + " [" + lo + "-" + hi + "]: ");
            if(pipe.hasNextInt())
            {
                retRangedInt = pipe.nextInt();
                pipe.nextLine();
                if(retRangedInt >= lo && retRangedInt <= hi)
                {
                    ready = true;
                }
                else
                {
                    System.out.println("\nPlease enter an integer in the valid range ["
                    + lo + "-" + hi + "], not " + retRangedInt + ".");
                }
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("\nPlease enter a valid integer [" + lo + "-" + hi + "], not " + trash + ".");
            }
        }while(!ready);
        return retRangedInt;
    }
    public static double getRangedDouble(Scanner pipe, String prompt, double lo, double hi)
    {
        double retRangedDouble = 0;
        String trash ="";
        boolean ready = false;
        do{
            System.out.print("\n" + prompt + " [" + lo + "-" + hi + "]: ");
            if(pipe.hasNextDouble())
            {
                retRangedDouble = pipe.nextDouble();
                pipe.nextLine();
                if(retRangedDouble >= lo && retRangedDouble <= hi)
                {
                    ready = true;
                }
                else
                {
                    System.out.println("\nPlease enter an integer in the valid range ["
                            + lo + "-" + hi + "], not " + retRangedDouble + ".");
                }
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("\nPlease enter a valid integer [" + lo + "-" + hi + "], not " + trash + ".");
            }
        }while(!ready);
        return retRangedDouble;
    }
    public static boolean getYNConfirm(Scanner pipe, String prompt)
    {
     boolean retBoo = false;
     boolean ready = false;
     String booAnswer = "";
        do{
            System.out.print("\n" + prompt + " [Y/N]: ");
            booAnswer = pipe.nextLine();
            if(booAnswer.equalsIgnoreCase("Y"))
            {
                retBoo = true;
                ready = true;
            }
            else if(booAnswer.equalsIgnoreCase("N"))
            {
                ready = true;
            } else System.out.println("Please enter [yY/nN]."); // is this necessary?
        }while(!ready);
        return retBoo;
    }
    public static String getRegExString(Scanner pipe, String prompt, String regExPattern)
    {
        boolean ready = false;
        String retReg = "";
        String trash = "";
                do{
                    System.out.print("\n" + prompt + ": ");
                    retReg = pipe.nextLine();
                    if(retReg.matches(regExPattern))
                    {
                        ready = true;
                    }
                    else{
                        System.out.println("\nYour response must match.  It cannot be " + retReg + ".");
                    }
                }while(!ready);
                return retReg;
    }

}
