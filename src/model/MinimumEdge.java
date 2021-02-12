package model;

public class MinimumEdge
{
	private int from,
				to;
	
	public MinimumEdge(int from, int to)
	{
		this.from = from;
		this.to = to;
	}
	
	public int getFrom(){
		return from;
	}
	
	public int getTo(){
		return to;
	}
	
	@Override
	public String toString(){
		return from+" - "+to;
	}
}