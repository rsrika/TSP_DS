import java.util.NoSuchElementException;


public class Tour 
{

	public static String author()
	{
		return "Srikanth, Roshni";
	}
	private Node head;
    // creates an empty tour
    public Tour()                                    
    {
    	head = null;
    }
    // creates the 4-point tour a->b->c->d->a (for debugging)
    public Tour(Point a, Point b, Point c, Point d)  
    {
    	Node nodeA = new Node(a);
    	Node nodeB = new Node(b);
    	Node nodeC = new Node(c);
    	Node nodeD = new Node(d);
    	head = nodeA;
    	nodeA.next = nodeB;
    	nodeB.next = nodeC;
    	nodeC.next = nodeD;
    	nodeD.next = nodeA;
    }
    // returns the number of points in this tour
    public int size()
    {
    	int i = 1;
		if(head == null)
		{
			return 0;
		}
		else
		{
			Node currentNode = head;
			while(currentNode.next != head)
			{
				i++;
				currentNode = currentNode.next;
			}
		}
		return i; 	
    }
    // returns the length of this tour
    public double length()
    {
    	int size = size();
    	Node currentNode = head;
		if(head == null)
		{
			return 0;
		}
		else if(size == 1)
		{
			return 0;
		}
		else if(size == 2)
		{
			return head.p.distanceTo(head.next.p);
		}
		else
		{
			double totalDistance = 0;
			while(currentNode.next!= head)
			{
				totalDistance+= currentNode.p.distanceTo(currentNode.next.p);
				currentNode = currentNode.next;
		
			}
			totalDistance+= currentNode.p.distanceTo(currentNode.next.p);
			return totalDistance;
		} 	
		
    }
    public Node getNode(int index)
    {
    	if(index == 0)
    	{
    		return head;
    	}
    	int size = size(); 
		Node currentNode = head;
		if(index< 0 || index>= size|| head == null)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			int i = 0;
			while(i<index)
			{
				currentNode = currentNode.next;
				i++;
			}
		}
		return currentNode;
    }
    public int getIndex(Node node)
    {
    	int size = size(); 
		for(int i = 0; i< size; i++)
		{
			if(node == getNode(i))
			{
				return i;
			}
		}
		return -1;
	}
		
    
    // returns a string representation of this tour
    public String toString()
    {
    	String i = "";
    	if(head == null)
    	{
    		return i;
    	}
    	else
    	{
    		
    		Node currentNode = head;
    		
    		while (currentNode.next!= head)
    		{
    			i+= (currentNode.p.toString()+"\n");
    			currentNode = currentNode.next;
    		}
    		i+=currentNode.p.toString();
    	}
    	return i; 
    }
    // draws this tour to standard drawing
    public void draw()
    {
    	if(head == null )
    	{
    		return; 
    	}
    	if(size()< 2)
    	{
    		head.p.draw();
    	}
    	else
    	{
    		Node currentNode = head;
    		Point p = currentNode.p;
    		while(currentNode.next!= head)
    		{    
    			p.drawTo(currentNode.next.p);
    			currentNode = currentNode.next;
    			p = currentNode.p;
    		}
    		
    		p.drawTo(currentNode.next.p);
    	}
    }
    
    public Node findSmallestDistanceNode(Node node)
    {
    	double shortestDistance = Integer.MAX_VALUE;    	
    	Node currentNode = head;
    	while (currentNode.next!= head)
    	{
    		Node nextNode = currentNode.next;
    		currentNode.next = node;
    		node.next = nextNode;
    		double distance = length();
    		if(distance< shortestDistance)
    		{
    			distance = shortestDistance;
    			
    		}
    		
    	}
    	return getNode(0);
    }
    // inserts p using the nearest neighbor heuristic
    public void insertNearest(Point p)
    {    
    	if(head == null)
    	{
    		add(p);
    	}
    	else
    	{
    		Node currentNode = new Node(p); 
    		Node closestNode = findSmallestDistanceNode(currentNode);
    		currentNode.next = closestNode.next;
    		closestNode.next = currentNode;
    	}
    }
    
    public int returnShortestTotalDistanceNodeIndex(Node node)
    {
    	double shortestDistance = Integer.MAX_VALUE;  
    	double distance;
    	int size = size();
    	int addNodeAt = 0;
    	for(int i = 0; i< size; i++)
    	{
    		node.next = getNode(i);
    		if(i == 0)
    		{
    			Node lastNode = getNode(size-1);
    			lastNode.next = node;
    			distance = length();
    			lastNode.next = head;
    		}
    		else if(i == 1)
    		{
    			head.next= node;
    			distance = length();
    			head.next = node.next;
    		}
    		else
    		{
    			Node previousNode = getNode(i-1);
    			previousNode.next = node;
    			distance = length();
    			previousNode.next = node.next;
    		}
    		if(distance<shortestDistance)
    		{
    			shortestDistance = distance;
    			addNodeAt = i;
    		}
    		
        	
    	}
    	// for 4.point list
    	
    	return addNodeAt;
    }
 // inserts p using the smallest increase heuristic
    public void insertSmallest(Point p)                   
    {
    	if(head == null)
    	{
    		add(p);
    	}
    	Node currentNode = new Node(p);
    	int indexToInsertNode = returnShortestTotalDistanceNodeIndex(currentNode);
    	if(indexToInsertNode == 0)
    	{
    		currentNode.next = head;
    		Node lastNode = getNode(size()-1);
    		lastNode.next = currentNode;
    	}
    	else
    	{
    		Node previousNode = getNode(indexToInsertNode-1);
    		currentNode.next = previousNode.next;
    		previousNode.next = currentNode;
    	}
    	
    	
    	
    }
    public void add(Point p)
	{
		int size = size();
		if(head == null)
		{
			head = new Node(p);
			head.next = head;
		}
		else 
		{
			Node newNode = new Node(p);
			Node lastNode = getNode(size-1);
			lastNode.next = newNode;
			newNode.next = head;
		}
		
		
	}
    // adds before the node
	public void add(int index, Point n) 
	{
		int size = size();
		if((index<0 || index>size) && index !=0)
		{
			throw new IndexOutOfBoundsException();
		}
		if(head == null && index == 0)
		{
			add(n);
		}
		else if( index>0 && index<size-1)
		{
			Node newNode = new Node(n);
			Node previousNode = getNode(index);
			Node nextNode = getNode(index+1);
			previousNode.next = newNode;
			newNode.next = nextNode;
		}
		else if (index == 0)
		{
			Node newNode = new Node(n);
			Node currentNode = head;
			newNode.next = currentNode;
			head = newNode;
		}
		else if(index == size)
		{
			Node newNode = new Node(n);
			Node previousNode = getNode(index-1);
			previousNode.next = newNode;
			newNode.next = head;
		}
	}
	
	// removes the first element of the array
	public void remove()
	{
		if(head == null)
		{
			throw new NoSuchElementException();
		}
		head = head.next;
	}
	public void remove(int index)
	{
		int size = size();
		if(head == null || index<0 || index>=size)
		{
			throw new IndexOutOfBoundsException();
		}
		if(index>0 && index<size-1)
		{
			Node previousNode = getNode(index-1);
			Node nextNode = getNode(index+1);
			previousNode.next = nextNode;
		}
		else if(index == 0)
		{
			remove();
		}
		else if(index == size-1)
		{
			Node previousNode = getNode(index-1);
			previousNode.next = head;
		}
	}
	
    
   //tests this class by directly calling all constructors and methods
    public static void main(String[] args)
    {
    	// define 4 points that are the corners of a square
    	Point a = new Point(100.0, 100.0);
    	Point b = new Point(500.0, 100.0);
    	Point c = new Point(500.0, 500.0);
    	Point d = new Point(100.0, 500.0);
    	// create the tour a -> b -> c -> d -> a
    	Tour squareTour = new Tour(a, b, c, d);
    	
    	StdDraw.setXscale(0,600);
    	StdDraw.setYscale(0,600);
    	Point e = new Point(0,0);
    	System.out.println(squareTour.size());
    	System.out.println(squareTour.length());
    	squareTour.insertSmallest(e);
    	System.out.println(squareTour.size());
    	System.out.println(squareTour.length());
    	squareTour.draw();
    			
    	
    }
    // Implements Node Class
    private class Node
    {
    	private Point p;
    	private Node next;
    	 public Node()
		{
			p = null;
			next = null;
		}
		public Node(Point p1)
		{
			p = p1;
			next = null;
		}
		public Node(Point p1, Node nextNode)
		{
			p = p1;
			next = nextNode;
		}
    	
    }
}
