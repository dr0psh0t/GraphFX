package adt;

import java.util.LinkedList;

public class Temp
{
	public static void main(String[] args)
	{
		GraphAlgo algo = new GraphAlgo();
		LinkedList<Vertex> vList = new LinkedList<>();
		
		Vertex v0 = new Vertex("v0");
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");
		
		v0.setAdjacencies(new Edge[]{
							new Edge(v1, 16), 
							new Edge(v3, 2),
							new Edge(v4, 3)});
		
		v1.setAdjacencies(new Edge[]{
							new Edge(v2, 5)});
		
		v2.setAdjacencies(new Edge[]{
							new Edge(v1, 3)});
		
		v3.setAdjacencies(new Edge[]{
							new Edge(v1, 12),
							new Edge(v4, 7)});
		
		v4.setAdjacencies(new Edge[]{
							new Edge(v1, 10),
							new Edge(v2, 4),
							new Edge(v3, 5)});
		
		vList.add(v0);
		vList.add(v1);
		vList.add(v2);
		vList.add(v3);
		vList.add(v4);
		
		algo.breadthFirstTraversal(vList);
		System.out.println();
		
		algo.depthFirstTraversal(vList);
		System.out.println();
		
		algo.shortestPath(vList.getFirst());
		System.out.println();		
		
		for(Vertex v : vList)
		{
			System.out.println(vList.getFirst()+" to "+v+": "+v.getDistance());
			System.out.println(algo.getShortestPathTo(v)+"\n");
		}
		
		System.out.println();
		
		
	}
}