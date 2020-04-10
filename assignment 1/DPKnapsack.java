import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class DPKnapsack {

	public int capacity;
	ArrayList<String> itemName = new ArrayList<String>();
	ArrayList<Integer> itemWeight = new ArrayList<Integer>();
	ArrayList<Integer> itemValue = new ArrayList<Integer>();
	ArrayList<String> optimalSet = new ArrayList<String>();
	ArrayList<String> optimalSet2 = new ArrayList<String>();
	
	
	public DPKnapsack(int capacity,String itemFile)
	{
		this.capacity = capacity;
		
		try {
			    File file = new File(itemFile);
				Scanner in = new Scanner(file);
				//in.useDelimiter(",|\r\n");
				while(in.hasNext())
				{
					itemName.add(in.next());
					itemWeight.add(in.nextInt());
					itemValue.add(in.nextInt());
					in.nextLine();
				}
			in.close();
	         	}
		catch(Exception e)
		{
			System.out.println("File not Found !");
		}
		//System.out.println(itemName);
		//System.out.println(itemWeight);
		//System.out.print(capacity);
	}
	
	public int optimalWeight()
	{
		int result=0;
		for(int i=0; i< itemWeight.size(); i++ )
		{
			if(itemWeight.get(i)<= capacity)
			{
				result += itemWeight.get(i);
				optimalSet.add(itemName.get(i));
			}
		}
		return result;
	}
	
	public int optimalNumber()
	{
		return optimalSet.size();
	}
	
	public boolean contains(String item)
	{
		if(optimalSet.contains(item) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String solution()
	{
		String temp =  "Solution includes: "+ optimalSet;
		return temp;
				
	}
	
	public int optimalWeight(int maxWeight)
	{
		int result=0;
		for(int i=0; i< itemWeight.size(); i++ )
		{
			if(itemWeight.get(i)<= maxWeight)
			{
				result += itemWeight.get(i);
				optimalSet2.add(itemName.get(i));
			}
		}
		return result;
	}
	
}
