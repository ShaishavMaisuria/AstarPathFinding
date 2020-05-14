import java.util.*;

public class AstarPath2 {
	// https://en.wikipedia.org/wiki/A*_search_algorithm

	// using the priorirty queue we can apply it to the open list in order to set up
	// the lowest cost is stored at begining
	// https://en.wikipedia.org/wiki/A*_search_algorithm
	// Typical implementations of A* use a priority queue to perform the repeated
	// selection of minimum (estimated) cost nodes to expand.
	// This priority queue is known as the open set or fringe.
	private PriorityQueue<Node> openNode;
	private boolean[][] closed;

	// starting node point (x0,y0)
	private int x0, y0;
	// endong of the node point x,y
	private int x, y;
	// cost for vertical and horizontal value
	public static final int vertical_h_values = 9; // basically a set g cost

	// We also need to have the nodes setup based on grid
	private ArrayList<Node> path; // use to obtain path from goal node to start node
	private ArrayList<Node> ascendingPath; // store the path from start node to goal node
	private Node[][] grid; // actual grid that to use
	int breadth, length; // dimennsions of grid

	public AstarPath2(Node start, Node end) {
		path = new ArrayList<Node>();
		ascendingPath = new ArrayList<Node>();
		breadth = length = 15;
		x0 = start.get_x_value();
		y0 = start.get_y_value();
		x = end.get_x_value();
		y = end.get_y_value();

		grid = new Node[breadth][length];
		closed = new boolean[breadth][length];
		// setting up how each node will be sorted before adding to the open node
		// Therfore open node will have a sorted order from lowesst to highest
		openNode = new PriorityQueue<Node>((Node N1, Node N2) -> {
			if (N1.get_f_cost() < N2.get_f_cost()) {
				return -1;
			} else if (N1.get_f_cost() > N2.get_f_cost()) {
				return 1;
			} else {
				return 0;
			}
		});

		// intial heruistic values using manhatan distnace. using to find herustics of
		// start board
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Node(i, j);
				int h_cost = Math.abs(i - x) + Math.abs(j - y);
				grid[i][j].set_h_cost(h_cost);
				grid[i][j].set_goal_node(false);
			}

		}

		grid[x0][y0].set_f_cost(0);

		// creating wall or blocks 10 percent of the grid randomly.
		buildBlocks();
	}

	/*
	 * build walls randomly 10 percent the boarrd size
	 */
	public void buildBlocks() {
		Random rand = new Random();
		float buildBlock = 0;
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {

				buildBlock = rand.nextFloat();
				if (count >= 24) {
					break;
				} else if (i == x0 && j == y0) {
					System.out.print("");
				} else if (buildBlock <= 0.11f) {
					grid[i][j] = null;
					count++;
				} else if (i == x && j == y) {
					System.out.print("");
				}

			}
		}
	}

	/*
	 * find cost for each near by node used from traverse method apprantely sets the
	 * costs h and f values to reach node
	 */
	public void setCost(Node current, Node child) {
		int childfinalcost = 0;
		int cost = 0;
		boolean isOpen;
		if (child == null || closed[child.get_x_value()][child.get_y_value()]) {
			return;
		}
		// 0 (f=Vc+Yc++Vc)
		// ^
		// |
//(f=Vc+Yc+Hc) 0	Y		->	0 (f=Vc+Yc+Hc)
		//
		// 0 (f=Vc+Yc+vc)
		//
		cost = current.get_f_cost() + vertical_h_values;

		childfinalcost = child.get_h_cost() + cost;
		isOpen = openNode.contains(child);
		// set the final cost if a node is not opened and needs to be open and added to
		// open list
		if (!isOpen || childfinalcost < child.get_f_cost()) {
			child.set_f_cost(childfinalcost);
			child.set_parent_node(current);
			if (!isOpen) {
				openNode.add(child);
			}

		}

	}

	/*
	 * Helps the agent to move in different direction (up, down , left right) moves
	 * to the different direction based on cost and set the new cost for near by
	 * nodes
	 */

	public void traverse(boolean gridEachSol) {
		// we add the start location to open list
//if(openNode!=null) 
		boolean gridEachSolutionDisplayed = gridEachSol;
		Node current = null;
		try {
			if (openNode != null) {
				openNode.add(grid[x0][y0]);
			}
		} catch (NullPointerException e) {
			openNode.add(grid[x0][y0]);
		}
		// Retrieves and removes the head of this queue, or returns null if this queue
		// is empty.
		// https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
		if (openNode != null) {
			current = openNode.poll();
		}
		// boolean goal=false;
		while (current != null) {

			// once the node is not in use pop it out from thee open list and make the close
			// list of that variable true indicating that the node is closed
			Node temp;
			closed[current.get_x_value()][current.get_y_value()] = true;

			if (gridEachSolutionDisplayed) {
				gridViewEachSol(current.get_x_value(), current.get_y_value());
			}

			if (current.equals(grid[x][y])) {
				return;
			}

			// 0 (y-1)
			// ^
			// |
			// 0(x-1)<- Y -> 0 (x+1)
			// |
			// \/
			// 0 (y+1)
			//

			// x+1
			if (current.get_x_value() + 1 < grid.length) {
				temp = grid[current.get_x_value() + 1][current.get_y_value()]; // Accessing right side of the agent
				setCost(current, temp); // setting the cost of that newly explored node

			}
			// x-1
			if (current.get_x_value() - 1 >= 0) {
				temp = grid[current.get_x_value() - 1][current.get_y_value()];// Accessing left side of the agent
				setCost(current, temp); // setting the cost of that newly explored node

			}
			// y-1
			if (current.get_y_value() - 1 >= 0) {
				temp = grid[current.get_x_value()][current.get_y_value() - 1];// Accessing down side of the agent
				setCost(current, temp); // setting the cost of that newly explored node

			}
			// y+1
			if (current.get_y_value() + 1 < grid[0].length) {
				temp = grid[current.get_x_value()][current.get_y_value() + 1];// Accessing top side of the agent
				setCost(current, temp); // setting the cost of that newly explored node

			}

			// Retrieves and removes the head of this queue, or returns null if this queue
			// is empty.
			// https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
			if (openNode != null) {
				current = openNode.poll(); // Depending the near by node pop the least f cost node
			} else {
				System.out.println("Error during traversing");
			}

		}

	}

	/*
	 * This method basically shows the starting matrix
	 * 
	 */
	public void gridStartView() {
		System.out.println("Plain grid view");

		for (int i = 0; i < grid.length; i++) {

			for (int j = 0; j < grid[i].length; j++) {
				// if it is path print path
				if (grid[i][j] != null) {
					System.out.printf(" - ");
				}
				// if it is starting point
				else if (i == x0 && j == y0) {
					System.out.print(" S ");
				} // if it is goal node
				else if (i == x && j == y) {
					System.out.print(" G ");
				} // if it is wall show wall
				else if (grid[i][j] == null) {
					System.out.print(" w ");
				} else {
					System.out.print("Error in grid view");
				}

			}
			System.out.println("");
			// couI++;
		}

	}

	/*
	 * For extra credit it shows each moves made by agent on tile
	 * 
	 * shows grid for each tile an agent moves each tile
	 */
	public void gridViewEachSol(int b, int l) {
		if (closed[b][l]) {
			Node current = grid[b][l];
			System.out.println("Path  of each moves made by agent :");

			// in order to make traversal more easier using a bollean to locate which is the
			// path node that from the grid
			// as soon as we have travel a node the value becomes true and we than each part
			// know we have travelled the node
			grid[current.get_x_value()][current.get_y_value()].set_traversal_node(true);

			while (current.get_parent_node() != null) {

				grid[current.get_parent_node().get_x_value()][current.get_parent_node().get_y_value()]
						.set_traversal_node(true);

				// Changing the node to traverse
				current = current.get_parent_node();

				System.out.print(" (" + current.get_x_value() + " , " + current.get_y_value() + " ) , ");

			}

			System.out.println("\n");
			// i 0-> 1,2,3,...14
			// i 1 -> 1 ,2 3,...14
			// I have all grid breadth values
			// J have all length value

			for (int i = 0; i < grid.length; i++) {

				for (int j = 0; j < grid[i].length; j++) {

					if (i == x0 && j == y0) {
						System.out.print("   S ");
					} else if (i == x && j == y) {

						System.out.print("   G ");

					} else if (grid[i][j] == null) {
						System.out.print("   W ");
					} else if (grid[i][j] != null) {
						String r = "";
						if (grid[i][j].get_traversal_node()) {
							r = " 0 ";
						} else {
							r = " - ";
						}

						// System.out.print(" "+ (grid[i][j].get_traversal_node() ? " 1 ":" 0 "));
						System.out.print("  " + r);

					} else {

						System.out.print("Error in griwViewSolution for each tile move itteration");
					}

				}
				System.out.println();

			}
			System.out.println();

		} else {
			System.out.println("Path is not found");

		}

		if (b == x && l == y) {
			gridSolved();
		}
	}
	/*
	 * shows the final path obtained and also shows the animation of how the travel
	 * a path form start to goal node
	 * 
	 */

	public void gridFinalSol() {
		System.out.println("Path :");
		System.out.println();
		if (closed[x][y]) {

			Node current = grid[x][y];

			// 1 we are focusing to obtaining the best path by going backwards
			// is that from goal node I am tracking down the path through each parent node
			// going from goal node to start nodee.
			Node temp = new Node(current.get_x_value(), current.get_y_value());
			path.add(temp);

			while (current.get_parent_node() != null) {

				current = current.get_parent_node();
				temp = new Node(current.get_x_value(), current.get_y_value());
				if (temp != null)
					path.add(temp);
			}

			System.out.println(get_runPath());

			// using it to reverse the path list to s to g instead of g to s
			for (int i = path.size() - 1; i >= 0; i--) {
				ascendingPath.add(new Node(path.get(i).get_x_value(), path.get(i).get_y_value()));

			}
			System.out.print(get_asendingPath());
// once path is discovered giving that  node indication that it is part of path and then showing animation on console
			for (int e = 0; e < ascendingPath.size(); e++) {

				grid[ascendingPath.get(e).get_x_value()][ascendingPath.get(e).get_y_value()].set_goal_node(true);

				for (int i = 0; i < grid.length; i++) {
					// countI++;
					for (int j = 0; j < grid[i].length; j++) {

						// count++;
						if (i == x0 && j == y0) {
							System.out.print(" S ");
						} else if (i == x && j == y) {

							System.out.print(" G ");

						} else if (grid[i][j] == null) {
							System.out.print(" W ");
						} else if (grid[i][j] != null) {
							String r = "";
							if (grid[i][j].get_goal_node()) {
								r = " 0 ";
							} else {
								r = " - ";
							}

							System.out.print("" + r);

						} else {

							System.out.print("Error in displaysolution itteration");
						}

					}
					System.out.println();

				}

				System.out.println();

			}

			System.out.println();

		} else {
			System.out.println("Path not found");
		}

	}

	/*
	 * This method helps to get last final solved grid
	 * 
	 */
	public void gridSolved() {
		System.out.println("Path :");
		if (closed[x][y]) {

			Node current = grid[x][y];

			Node temp = new Node(current.get_x_value(), current.get_y_value());
			path.add(temp);

			grid[current.get_x_value()][current.get_y_value()].set_goal_node(true);

			while (current.get_parent_node() != null) {
				// temp = new Node(current.get_parent_node().get_x_value(),
				// current.get_parent_node().get_y_value());
				////////////

				// System.out.println("-> ("+current.get_parent_node().get_x_value()+" ,
				// "+current.get_parent_node().get_y_value()+ " )" );
				grid[current.get_parent_node().get_x_value()][current.get_parent_node().get_y_value()]
						.set_goal_node(true);

				current = current.get_parent_node();
				temp = new Node(current.get_x_value(), current.get_y_value());
				if (temp != null)
					path.add(temp);
			}

			System.out.println(get_runPath());
			int count = 0;
			int countI = 0;

			for (int i = 0; i < grid.length; i++) {
				countI++;
				for (int j = 0; j < grid[i].length; j++) {

					count++;
					if (i == x0 && j == y0) {
						System.out.print(" S ");
					} else if (i == x && j == y) {

						System.out.print(" G ");

					} else if (grid[i][j] == null) {
						System.out.print(" W ");
					} else if (grid[i][j] != null) {
						String r = "";
						if (grid[i][j].get_goal_node()) {
							r = " 0 ";
						} else {
							r = " - ";
						}

						System.out.print("" + r);

					} else {

						System.out.print("Error in displaysolution itteration");
					}

				}
				System.out.println();

			}
			System.out.println();

		} else {
			System.out.println("Path not found");
		}

	}

	/*
	 * helps to get path that one is running on
	 * 
	 */
	public String get_runPath() {
		String temp = "";
		for (int i = path.size() - 1; i >= 0; i--) {
			temp += (" (" + path.get(i).get_x_value() + " , " + path.get(i).get_y_value() + " ) ,");
		}

		return temp;
	}

	/*
	 * return the asending the order of path
	 */
	public String get_asendingPath() {
		String temp = "";
		for (int i = 0; i < path.size(); i++) {
			temp += (" (" + ascendingPath.get(i).get_x_value() + " , " + ascendingPath.get(i).get_y_value() + " ) ,");
		}

		return temp;
	}

	/*
	 * set method for l and B for dimension
	 */
	public void setLengthBreadth(int breath, int length) {
		this.breadth = breath;
		this.length = length;
	}

	/*
	 * to provide the path get method
	 */
	public ArrayList<Node> get_path() {
		return path;
	}

	/*
	 * our main methd
	 *//*
		 * public static void main (String[] args) {
		 * 
		 * int x0, y0,x,y; System.out.print("Please enter the start cordinates X0 ");
		 * Scanner scan = new Scanner(System.in); x0=scan.nextInt();
		 * System.out.print("Please enter the start cordinates Y0 ");
		 * 
		 * y0=scan.nextInt();
		 * 
		 * Node start = new Node(x0, y0);
		 * System.out.print("Please enter the goal cordinates X ");
		 * 
		 * x=scan.nextInt(); System.out.print("Please enter the start cordinates Y ");
		 * 
		 * y=scan.nextInt();
		 * 
		 * 
		 * Node end = new Node(x,y); AstarPath2 astar = astar= new AstarPath2(start,
		 * end);
		 * 
		 * //shows intial grid astar.gridView(); System.out.println("process"); //help
		 * to move up down and left and right astar.traverse();
		 * System.out.println("displaysolution");
		 * 
		 * //shows the animation for the extra credit and displays final solution
		 * astar.gridFinalSol();
		 * 
		 * 
		 * }
		 */
}
