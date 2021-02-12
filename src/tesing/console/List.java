package tesing.console;

import java.util.LinkedList;

public class List {
	
	

	public static void main(String[] args) {
		
		LinkedList<String> strings = new LinkedList<>();
		
		String str1, str2;
		
		str1 = "Daryll";
		str2 = "David";
		
		strings.add(str1);
		strings.add(str2);
		
		System.out.println(strings);
		
		str1 = "Dagondon";
		
		System.out.println(strings);
	}

}
