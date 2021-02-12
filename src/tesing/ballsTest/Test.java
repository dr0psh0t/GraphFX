package tesing.ballsTest;

import java.util.LinkedList;
import java.util.Random;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		test1();
	}

	public static void test0()
	{
		Random rand = new Random();

		for(int x = 0; x < 9; ++x)
			System.out.println((int) (Math.random() * (20 - 11) + 11));
		
		System.out.println();
		
		for(int x = 0; x < 9; ++x)
			System.out.println(rand.nextInt((20 - 11) + 1) + 11);
	}
	
	public static void test1()
	{
		LinkedList<Integer> list = new LinkedList<>();
		
		for(int x = 0, max = 10; x < max; ++x)
			list.add((int) (Math.random() * (10 - 1) + 1));
		
		System.out.println(list+"\n\n");
		
		int max = 0;
		for(Integer num : list)
		{
			if(max > 6)
			{
				System.out.println("stopped");
				break;
			}
			System.out.println(num);
			++max;
		}
	}
}
