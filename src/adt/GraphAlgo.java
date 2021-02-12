package adt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import model.MinimumEdge;

/* The authors of this work have released all rights to it and placed it
2 in the public domain under the Creative Commons CC0 1.0 waiver
3 (http://creativecommons.org/publicdomain/zero/1.0/).
4 
5 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
6 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
7 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
8 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOL
DERS BE LIABLE FOR ANY
9 CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
10 TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
11 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
12 
13 Retrieved from: http://en.literateprograms.org/Dijkstra's_algorithm_(Java)?oldid=15444
14 */
public class GraphAlgo
{
	public LinkedList<Vertex> depthFirstTraversal(LinkedList<Vertex> vertices)
	{
		System.out.print("DFT: ");
		LinkedList<Vertex> visited = new LinkedList<>();
		
		for(Vertex vertex : vertices)
		{
			if(!visited.contains(vertex))
				depthFirstRec(vertex, visited);
		}
		
		return visited;
	}
	
	private void depthFirstRec(Vertex vertex, LinkedList<Vertex> visited)
	{
		visited.add(vertex);
		
		for(Edge edge : vertex.getAdjacencies())
		{
			Vertex node = edge.getTarget();
			
			if(!visited.contains(node))
				depthFirstRec(node, visited);
		}
	}
	
	public LinkedList<Vertex> breadthFirstTraversal(LinkedList<Vertex> vertices)
	{
		LinkedList<Vertex> queue = new LinkedList<>();
		LinkedList<Vertex> visited = new LinkedList<>();
		Vertex u;
		
		System.out.print("BFT: ");
		for(Vertex vertex : vertices)
		{
			if(!visited.contains(vertex))
			{
				queue.add(vertex);
				visited.add(vertex);
				
				while (!queue.isEmpty())
				{
					u = queue.pollFirst();
					for(Edge edge : u.getAdjacencies())
					{
						Vertex node = edge.getTarget();
						if(!visited.contains(node))
						{
							queue.add(node);
							visited.add(node);
						}
					}
				}
			}
		}
		
		System.out.println(visited);
		return visited;
	}
	
	//	computes the shortest distance from the source to other vertices
	public void shortestPath(Vertex source)
	{
		source.setDistance(0);	// set the source's distance to 0
		
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>(10);
		vertexQueue.add(source);
		//System.out.println(vertexQueue);
		
		while(!vertexQueue.isEmpty())
		{
			Vertex polledVertex = vertexQueue.poll();
			
			for(Edge edge : polledVertex.getAdjacencies())
			{
				Vertex vertex = edge.getTarget();
				double weight = edge.getWeight();
				double distanceThroughU = polledVertex.getDistance() + weight;
				
				if(distanceThroughU < vertex.getDistance())
				{
					vertex.setDistance(distanceThroughU);
					vertex.setPrevious(polledVertex);
					vertexQueue.add(vertex);
				}
				//System.out.println(vertexQueue);
				//System.out.println("Vertex: "+vertex.getName()+"\nDistance: "+vertex.getDistance()+"\n");
			}//	end for loop
		}//	end while
	}//	end computePaths
	
	//	returns a list from the source until the specified target
	public List<Vertex> getShortestPathTo(Vertex target)
	{
		List<Vertex> path = new ArrayList<>(10);		
		for(Vertex vertex = target; vertex != null; vertex = vertex.getPrevious())
			path.add(vertex);
		
		Collections.reverse(path);
		return path;
	}//	end getShortestPathTo
	
/*	=============================================================================================================================================
	 * In topological sorting, we use a temporary stack. We don’t print the vertex immediately,
	 * we first recursively call topological sorting for all its adjacent vertices, then push
	 * it to a stack. Finally, print contents of stack. Note that a vertex is pushed to stack
	 * only when all of its adjacent vertices (and their adjacent vertices and so on) are already in stack.
	 */
	
	public Stack<Vertex> getDepthFirstTopologicalSort(LinkedList<Vertex> vertices)
	{
		Stack<Vertex> stack = new Stack<>();
		LinkedList<Vertex> visited = new LinkedList<>();
		
		for(Vertex vertex : vertices)
		{
			if(!visited.contains(vertex))
				topologicalHelper(visited, stack, vertex);
		}
		
		Collections.reverse(stack);
		return stack;
	}
	
	private void topologicalHelper(LinkedList<Vertex> visited, Stack<Vertex> stack, Vertex vertex)
	{
		visited.add(vertex);
		for(Edge edge : vertex.getAdjacencies())
		{
			Vertex node = edge.getTarget();
			if(!visited.contains(node))
				topologicalHelper(visited, stack, node);
		}
		stack.push(vertex);
	}
//	=============================================================================================================================================
	
	public LinkedList<Vertex> getBreadthFirstTopologicalSort(LinkedList<Vertex> vertices)
	{
		LinkedList<Vertex> queue = new LinkedList<>();
		LinkedList<Vertex> tpOrder = new LinkedList<>();
		
		int size = vertices.size();
		int[] predCount = new int[size];
		
		for(int x = 0; x < size; ++x)
			predCount[x] = 0;
		
		for(int x = 0; x < size; ++x)
			predCount[x] = vertices.get(x).getInDegree();
		
		for(int ind = 0; ind < size; ++ind)
		{
			if(predCount[ind] == 0)
				queue.add(vertices.get(ind));
		}
		
		while(!queue.isEmpty())
		{
			Vertex uVertex = queue.pollFirst();
			tpOrder.add(uVertex);
			
			for(Edge edge : uVertex.getAdjacencies())
			{
				Vertex node = edge.getTarget();
				int index = vertices.indexOf(node);
				predCount[index]--;
				
				if(predCount[index] == 0)
					queue.add(node);
			}
		}		
		return tpOrder;
	}
	
	/*
	 * minimum spanning tree below
	 * using Prim's algorithm
	 */
	
	private static int size;
	
	/*
	 * A utility function to find the vertex with minimum key
	 * value, from the set of vertices not yet included in MST
	 */
	private int minKey(int key[], boolean[] mstSet)
	{
	   // Initialize min value
	   int min = Integer.MAX_VALUE,
			   min_index = -1;

	   for(int v = 0; v < size; v++)
	   {
	       if(mstSet[v] == false && key[v] < min)
	       {
	           min = key[v];
	           min_index = v;
	       }
	   }
	   return min_index;
	}
	
	//	A utility function to print the constructed MST stored in parent[]
	private ArrayList<MinimumEdge> printMST(int[] parent, int[][] graph)
	{
		ArrayList<MinimumEdge> mEdges = new ArrayList<>();
		
		@SuppressWarnings("unused")
		int weight;
		
		for(int i = 1; i < size; ++i)
		{
			weight = graph[i][parent[i]];
			//System.out.println((weight > 0) ? (parent[i]+" - "+ i+"    "+graph[i][parent[i]]) : "");
			
			mEdges.add(new MinimumEdge(parent[i], i));
		}
		
		return mEdges;
	}
	
	public ArrayList<MinimumEdge> minimumSpanningTree(int[][] graph)
	{
		size = graph.length;
		
		// Array to store constructed MST
		int parent[] = new int[size];

		// Key values used to pick minimum weight edge in cut
		int key[] = new int[size];
		
		// To represent set of vertices not yet included in MST
		boolean[] mstSet = new boolean[size];
		
		//	initialize all keys as Infinite
		for(int i = 0; i < size; ++i)
		{
			key[i] = Integer.MAX_VALUE;
			mstSet[i] = false;
		}
		
		/*
		 * Always include first 1st vertex in MST.
		 * Make key 0 so that this vertex is
		 * picked as first vertex
		 */
		key[0] = 0;
		
		// First node is always root of MST
		parent[0] = -1;
		
		// The MST will have V vertices
		for(int count = 0; count < (size - 1); ++count)
		{
			/*
			 * Pick thd minimum key vertex from the set of vertices
			 * not yet included in MST
			 */
			int u = minKey(key, mstSet);
			
			// Add the picked vertex to the MST Set
			mstSet[u] = true;
			
			/*
			 * Update key value and parent index of the adjacent
			 * vertices of the picked vertex. Consider only those
			 * vertices which are not yet included in MST
			 */
			for(int v = 0; v < size; ++v)
			{
				int weight = graph[u][v];
				if((weight != 0) && (mstSet[v] == false) && (weight < key[v]))
				{
					parent[v] = u;
					key[v] = weight;
				}
			}
		}
		
		// print the constructed MST
		return printMST(parent, graph);
	}
}