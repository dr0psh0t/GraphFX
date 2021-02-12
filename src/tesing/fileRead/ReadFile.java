package tesing.fileRead;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ReadFile
{
	public static void main(String[] args)
	{
		try
		{
			Scanner inFile = new Scanner(new FileReader("src/res/txt/Test"));
			
			while(inFile.hasNextLine())
			{
				String str1 = inFile.next();
				String str2 = inFile.next();
				double num = inFile.nextDouble();
				inFile.nextInt();
				
				System.out.println(str1+" "+str2+" "+num);
			}
			
			inFile.close();
		}
		catch(FileNotFoundException exception){
			exception.printStackTrace();
		}
	}
}