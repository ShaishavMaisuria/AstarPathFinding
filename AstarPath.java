import java.util.*;
public class AstarPath {
	//https://en.wikipedia.org/wiki/A*_search_algorithm
	
	
	// using the priorirty queue we can apply it to the open list in order to set up the lowest cost is stored at begining
		//https://en.wikipedia.org/wiki/A*_search_algorithm
	//Typical implementations of A* use a priority queue to perform the repeated selection of minimum (estimated) cost nodes to expand. 
	//This priority queue is known as the open set or fringe.
	private PriorityQueue<Node> openNode;
		private boolean [][] closed;
	
		// starting node point (x0,y0)
		private int x0,y0;
		//endong of the node point x,y
		private int x,y;
	// cost for vertical and horizontal value
	public static final int vertical_h_values=9;
	//cost for dialogonall moves
	public static final int d_values=10;
	//We also need to have the nodes setup based on grid
	private ArrayList<Node> path;
	
	private Node [][] grid;
	int breadth, length;
	
	
	
	public AstarPath( Node start, Node end) {
		path=new ArrayList<Node>();
		breadth= length=15;
		x0=start.get_x_value();
		y0=start.get_y_value();
		x=end.get_x_value();
		y=end.get_y_value();
		
		
		grid = new Node [breadth][length];
		closed= new boolean[breadth][length];
		// setting up how each node will be sorted before adding to the open node
		openNode = new PriorityQueue<Node>((Node N1 , Node N2)-> {
		if(N1.get_f_cost() < N2.get_f_cost()) {
			return -1;
		} else if(N1.get_f_cost()>N2.get_f_cost()) {
			return 1;
		} 
		else {
			return 0;
		}
		});		
	
	// begging the heuristic values
	for( int i=0;i< grid.length;i++) {
		for(int j=0;j<grid[i].length;j++) {
			grid [i][j]= new Node(i,j);
			int h_cost= Math.abs(i-x)+Math.abs(j-y);
			grid[i][j].set_h_cost(h_cost);
			grid[i][j].set_goal_node(false);
		}
		
	}
		
	grid[x0][y0].set_f_cost(0);
	
	
	
	buildBlocks();
	
	}
	
	
	public void buildBlocks() {
		Random rand  = new Random();
		float buildBlock=0;
		
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[0].length;j++) {
				 buildBlock = rand.nextFloat();
				if(buildBlock<=0.10f) {
					grid[i][j]=null;
				}
			}
		}
		
	}
	
	
	//fixed
	public void setCost(Node current, Node child) {
		int childfinalcost=0;
		int cost=0;
		boolean isOpen;
		if( child==null || closed[child.get_x_value()][child.get_y_value()] ) {
			return ;
		}
		//			0 (f=Vc+Yc++Vc)
		//			^
		//			|
//(f=Vc+Yc+Hc) 0	Y		->	0 (f=Vc+Yc+Hc)
		//
		//			0  (f=Vc+Yc+vc)
		//
		cost =current.get_f_cost()+ vertical_h_values;
		
		childfinalcost=child.get_h_cost()+cost;
		isOpen=openNode.contains(child);
		//set the final cost if a node is not opened and needs to be open and added to open list
		if(!isOpen || childfinalcost<child.get_f_cost()) {
			child.set_f_cost(childfinalcost);
			child.set_parent_node(current);
			if(!isOpen) {
				openNode.add(child);
			}

		}
		
	}
	//fixed
	public void traverse() {
		//we add the start location to open list 
if(openNode!=null)
		openNode.add(grid[x0][y0]);
		//Retrieves and removes the head of this queue, or returns null if this queue is empty.
		//https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
		Node current=openNode.poll() ; 
		while(current!=null) {
				
			//once the node is not in use pop it out from thee open list and make the close list of that variable true indicating that the node is closed
			Node temp;
			closed[current.get_x_value()][current.get_y_value()]=true;
			
			gridViewEachSol(current.get_x_value(),current.get_y_value());
			
			
			if(current.equals(grid[x][y])) {
				return;
			}
			
			//				0 (y-1)
			//				^
			//				|
			//	0(x-1)<-	Y		->	0 (x+1)
			//				|
			//			   \/
			//				0  (y+1)
			//
			
			//x+1
			if(current.get_x_value()+1<grid.length) {
				temp= grid[current.get_x_value()+1] [current.get_y_value()];
				setCost(current, temp);
				
		
			}	
			//x-1
			if(current.get_x_value()-1>=0) {
				temp= grid[current.get_x_value()-1][current.get_y_value()];
				setCost(current, temp);
				
		
			}
			//y-1
			if(current.get_y_value()-1>=0) {
				temp= grid[current.get_x_value()][current.get_y_value()-1];
				setCost(current, temp);
			}
			//y+1
			if(current.get_y_value()+1<grid[0].length) {
					temp= grid[current.get_x_value()][current.get_y_value()+1];
					setCost(current, temp);
			
			
				}
				
				
				//Retrieves and removes the head of this queue, or returns null if this queue is empty.
				//https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
				current=openNode.poll();
			
	}
		
	}
	/*
	
	public String[] grid() {
		System.out.println("Plain grid view");
		int count=0; int couI=0;
		for(int i=0;i<grid.length;i++) {
			System.out.print(i);
			for(int j=0;j<grid[i].length;j++) {
				
				if(grid[i][j]!=null) {
					System.out.printf(" "+0);
				}
				
				else if(i==x0 && j == y0) {
					System.out.print("S ");
				}
				else if(i==x && j == y) {
					System.out.print(" G ");
				}
				else if (grid[i][j]==null){
					System.out.print(" B ");
				}
				else {
					System.out.print("Error");
				}
				
				count++;
			}
			System.out.println("");
			// couI++;
		} 
		
	}*/
	
	public void gridView() {
		System.out.println("Plain grid view");
		int count=0; int couI=0;
		for(int i=0;i<grid.length;i++) {
			System.out.print(i);
			for(int j=0;j<grid[i].length;j++) {
				
				if(grid[i][j]!=null) {
					System.out.printf(" "+0);
				}
				
				else if(i==x0 && j == y0) {
					System.out.print("S ");
				}
				else if(i==x && j == y) {
					System.out.print(" G ");
				}
				else if (grid[i][j]==null){
					System.out.print(" B ");
				}
				else {
					System.out.print("Error");
				}
				
				count++;
			}
			System.out.println("");
			// couI++;
		} 
		
	}

	//fixed
	public void finalCostAllGridView() {
	System.out.println("Showing f cost for each rows and columns");
	
	for(int i=0;i<grid.length;i++ ) {
		for(int j=0;j<grid[i].length;j++) {
		
			if (grid[i][j]==null){
				System.out.print("	B ");
			}else if(grid[i][j]!=null) {
				System.out.print("   "+grid[i][j].get_f_cost());
			}
			else {
				System.out.print("Error");
			}
		}
		System.out.println();
	}
	System.out.println();
}


	// fixed
	public void gridViewEachSol(int b,int l) {
	if(closed[b][l]) {
		Node current = grid[b][l];
		System.out.println("Path  of iteration:");
		
		System.out.println(current);
		grid[current.get_x_value()][current.get_y_value()].set_traversal_node(true);
		
		while(current.get_parent_node()!=null) {
			
			grid[current.get_parent_node().get_x_value()][current.get_parent_node().get_y_value()].set_traversal_node(true);	 
			
		
			
			current = current.get_parent_node();
			
			System.out.print(" ("+current.get_x_value()+" , "+current.get_y_value()+ " ) , " );
			
		}
	
	
	System.out.println("\n");
	// i 0-> 1,2,3,...14 
	// i 1 -> 1 ,2 3,...14
	//I have all grid breadth values
	//J have all length value 
	
	for(int i=0;i<grid.length;i++) {
		
		for(int j=0;j<grid[i].length;j++) {
		
			 if(i==x0 && j == y0) {
				System.out.print("   S ");
			}
			else if(i==x && j == y) {
				
				System.out.print("   G ");
				
			}else if(grid[i][j]==null) {
				System.out.print("   W "); 
			}	
		else if(grid[i][j]!=null) {
				String r="";
				if(grid[i][j].get_traversal_node()) {
					r=" 1 ";
				} else { r=" 0 "; }
				
			//System.out.print("  "+ (grid[i][j].get_traversal_node() ? " 1 ":" 0 "));
			System.out.print("  "+r);
			
		} else {
				
					System.out.print("Error in displaysolution itteration");
			}
			
			
		}
		System.out.println();
		
	}
	System.out.println();
	
	
	}
	else {
		System.out.println("Path is not found");
	}
}
	//fixed
	public void gridFinalSol() {
	System.out.println("Path :");
	if(closed[x][y]) {
	
		Node current = grid[x][y];
		
		Node temp= new Node(current.get_x_value(), current.get_y_value());
		path.add(temp);
		
		grid[current.get_x_value()][current.get_y_value()].set_goal_node(true);
		
		while(current.get_parent_node()!=null) {
			//temp = new Node(current.get_parent_node().get_x_value(), current.get_parent_node().get_y_value());
			////////////
			
			
			//System.out.println("-> ("+current.get_parent_node().get_x_value()+" , "+current.get_parent_node().get_y_value()+ " )" );
			grid[current.get_parent_node().get_x_value()][current.get_parent_node().get_y_value()].set_goal_node(true);	
			
			current = current.get_parent_node();
			temp= new Node(current.get_x_value(), current.get_y_value());
			if(temp!=null)
				path.add(temp);
		} 
		
	System.out.println(get_runPath());
	int count=0; int countI=0;
	
	for(int i=0;i<grid.length;i++) {
		countI++;
		for(int j=0;j<grid[i].length;j++) {
			
		count++;
			 if(i==x0 && j == y0) {
				System.out.print(" S ");
			}
			 else if(i==x && j == y) {
				
				System.out.print(" G ");
				
			}else if(grid[i][j]==null) {
				System.out.print(" W "); 
			} else if(grid[i][j]!=null) {
				String r="";
				if(grid[i][j].get_goal_node()) {
					r=" 2 ";
				} else { r=" 0 " ; }
				
			
			System.out.print(""+r);
			
		}else {
				
					System.out.print("Error in displaysolution itteration");
			}
			
			
		}
		System.out.println();
		
	}
	System.out.println();
	
	
	
	}
	else {
		System.out.println("Path not found");
	}
	
}

	//fi
	public String get_runPath() {
	String temp="";
	for(int i=path.size()-1;i>=0;i--) {
	temp+=(" ("+path.get(i).get_x_value()+" , "+path.get(i).get_y_value()+ " ) ," );
	}
	
	return temp;
}
	//fi
	public void setLengthBreadth(int breath, int length) {
	 this.breadth=breath;
	 this.length=length;
}
	//fi
	public ArrayList<Node> get_path () {
	return path;
}


	public static void main (String[] args) {
		
		int x0, y0,x,y;
		System.out.print("Please enter the start cordinates X0 ");
		Scanner scan = new Scanner(System.in);
		x0=scan.nextInt();
		System.out.print("Please enter the start cordinates Y0 ");
		
		y0=scan.nextInt();
		
	Node start = new Node(x0, y0);
	System.out.print("Please enter the goal cordinates X ");
	
	x=scan.nextInt();
	System.out.print("Please enter the start cordinates Y ");
	
	y=scan.nextInt();
	

	Node end = new Node(x,y);
	AstarPath astar = 	astar= new AstarPath(start, end);
	//astar.setLengthBreadth(15, 15);
	//fixed
	astar.gridView();
	System.out.println("process");
	//fixed
	astar.traverse();
	System.out.println("displayscores");
	// not need possibly
	astar.finalCostAllGridView();
	System.out.println("displaysolution");
	
	astar.gridFinalSol();
	
	
}

}

	



