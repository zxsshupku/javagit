import java.io.File;
import java.util.Scanner;

public class test{
	
	public static void main(String arg[]) throws Exception {
        Scanner input = new Scanner(System.in);
        while(true) {
        	String com = input.next();
        	if(com == "x") 
        		break;
        	else
        		command.getCommand(com);
        }
        input.close();
	}
	
}