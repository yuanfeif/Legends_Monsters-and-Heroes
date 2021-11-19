import java.util.Scanner;

public class UserInputUtil {
    public static final String ANSI_BLUE = "\u001B[34m";

    /**
     * If int that input is not in the bound, it will require an input agian
     */
    public static int getIntInput(int lowerBound, int UpperBound){
        Scanner scanIn = new Scanner(System.in);
        System.out.print(ANSI_BLUE + "Input:");
        try{
            int i = scanIn.nextInt();
            if (i >= lowerBound && i <= UpperBound){return i;}
            else {
                System.out.println("Input out of bound, try again!");
                return getIntInput(lowerBound,UpperBound);
            }
        }
        catch (Exception e){
            System.out.println("Illegal input, try again!");
            return getIntInput(lowerBound,UpperBound);
        }
    }
}
