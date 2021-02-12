package adt;

import java.util.ArrayList;

import model.CircleInfo;

/* 
 * The authors of this work have released all rights to it and placed it
 * in the public domain under the Creative Commons CC0 1.0 waiver
 * (http://creativecommons.org/publicdomain/zero/1.0/).
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
public class Vertex implements Comparable<Vertex>
{
	private final String name;
	private CircleInfo cInfo;
	private Vertex previous;
	private ArrayList<Edge> adjacencies;
	private double distance = Double.POSITIVE_INFINITY;
	private int inDegree;
	private int outDegree;
	private int indexKey;	// for minimum spanning tree

	//	constructor with only one parameter
	public Vertex(String name)
	{
		this.name = name;
		inDegree = outDegree = 0;
		cInfo = null;
		adjacencies = new ArrayList<>(10);
	}
	
	//	get CircleInfo
	public CircleInfo getCircleInfo(){
		return cInfo;
	}
	
	//	set CircleInfo
	public void setCircleInfo(CircleInfo cInfo){
		this.cInfo = cInfo;
	}
	
	//	set indexKey
	public void setIndexKey(int indexKey){
		this.indexKey = indexKey;
	}
	
	//	get indexKey
	public int getIndexKey(){
		return indexKey;
	}
	
	//	accessor method to return name
	public String getName(){
		return name;
	}
	
	//	mutator method to add adjacency to this vertex
	public void addAdjacency(Edge edge){
		adjacencies.add(edge);
	}
	
	//	mutator method set adjancencies array
	public void setAdjacencies(Edge[] edges)
	{
		adjacencies.clear();	
		for(Edge edge : edges)
			adjacencies.add(edge);
	}
	
	//	accessor method to return adjacencies arrays
	public ArrayList<Edge> getAdjacencies(){
		return adjacencies;
	}
	
	//	delete one adjacency
	public void deleteAdjacency(Edge edge){
		adjacencies.remove(edge);
	}
	
	//	mutator method to set distance
	public void setDistance(double distance){
		this.distance = distance;
	}
	
	//	accessor method to return distance
	public double getDistance(){
		return distance;
	}
	
	//	mutator method to set previous
	public void setPrevious(Vertex previous){
		this.previous = previous;
	}
	
	//	accessor method to return previous
	public Vertex getPrevious(){
		return previous;
	}
	
	// get the indegree of this vertex
	public int getInDegree(){
		return inDegree;
	}
	
	// get the outdegree of this vertex
	public int getOutDegree(){
		return outDegree;
	}
	
	// get total degree. used for undirected graph
	public int getTotalDegree(){
		return inDegree + outDegree;
	}
	
	// set the indegree of this vertex by incrementing
	public void incrementInDegree(){
		++inDegree;
	}
	
	// set the outdegree of this vertex by incrementing
	public void incrementOutDegree(){
		++outDegree;
	}
	
	// set the indegree of this vertex by incrementing
	public void decrementInDegree()
	{
		if(inDegree > 0)
			--inDegree;
	}
		
	// set the outdegree of this vertex by incrementing
	public void decrementOutDegree()
	{
		if(outDegree > 0)
			--outDegree;
	}
	
	// remove adjacencies
	public void clearAdjacencies(){
		adjacencies.clear();
	}
	
	/*
	 * we override compareTo from the implemented interface Comparable
	 * which is used for PriorityQueue
	 */
	@Override
	public int compareTo(Vertex other){
		return Double.compare(distance, other.distance);
	}
	
	public boolean equal(Vertex vertex){
		return this.name.equals(vertex.getName());
	}
	
	@Override
	public String toString(){
		//return "("+name+" "+distance+")";
		return name;
	}
}//	end class Vertex