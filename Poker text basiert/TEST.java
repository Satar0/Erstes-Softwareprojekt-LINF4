/*

Programm zu testen von Input

*/
import java.util.Objects;
import java.util.Scanner;  // Import the Scanner class
public class TEST
{
 Scanner scanner = new Scanner(System.in);  // Create a Scanner object
 public int integer()
 {
   String input = scanner.nextLine();
   return Integer.parseInt(input);
 }
 public String string()
 {
   return scanner.nextLine();
 }
 public boolean bool()
 {
    String input = scanner.nextLine();
    if(Objects.equals(input, "true"))
    {
     return true;
    } else
    {
     return false;
    }
 }

}