package adt;

/* The authors of this work have released all rights to it and placed it
2 in the public domain under the Creative Commons CC0 1.0 waiver
3 (http://creativecommons.org/publicdomain/zero/1.0/).
4 
5 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
6 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
7 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
8 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
9 CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
10 TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
11 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
12 
13 Retrieved from: http://en.literateprograms.org/Dijkstra's_algorithm_(Java)?oldid=15444
14 */
public class Edge
{
	private final Vertex target;
	private final int weight;
	
	public Edge(Vertex argTarget, int argWeight)
	{
		target = argTarget;
		weight = argWeight;
	}
	
	//	only accessor methods since instance variables are declared as final
	public Vertex getTarget(){
		return target;
	}
	
	public int getWeight(){
		return weight;
	}
	
	@Override
	public String toString(){
		//return target+" "+weight;
		return target.toString();
	}
}//	end class Edge