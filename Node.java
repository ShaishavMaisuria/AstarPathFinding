/**
 * 
 * @author shaishav Will use this class to communicate with all other tiles
 *         which means to keep track which is parent node, child node will also
 *         help finiding the
 *
 */
public class Node {
	private Node parent, child;// to go back and forth
	private int x_value, y_value; // for locating the node on the board
	private int f, g, h;// for finding the cost
	private boolean goal;
	private boolean traversal_node;

	public Node(int x_value, int y_value, int f, int g, int h) {
		this.y_value = y_value;
		this.x_value = x_value;
		this.f = f;
		this.g = g;
		this.h = h;

	}

	public Node(int x_value, int y_value) {
		this.y_value = y_value;
		this.x_value = x_value;

	}

	///////////////////////////////////////
	public void set_x_y_value(int x_val, int y_val) {
		this.y_value = y_val;
		this.x_value = x_val;

	}

	public void set_f_cost(int f) {
		this.f = f;
	}

	public void set_g_cost(int g) {
		this.g = g;
	}

	public void set_h_cost(int h) {
		this.h = h;
	}

	public void set_parent_node(Node parent) {
		this.parent = parent;
	}

	public void set_child_node(Node child) {
		this.child = child;
	}

	public void set_goal_node(boolean goal) {
		this.goal = goal;
	}

	public void set_traversal_node(boolean traversal) {
		this.traversal_node = traversal;
	}

	////////////////////////////
	public Node get_child_node() {
		return child;
	}

	public Node get_parent_node() {
		return parent;
	}

	public int get_x_value() {

		return x_value;
	}

	public int get_y_value() {
		return y_value;
	}

	public int get_f_cost() {
		return f;
	}

	public int get_g_cost() {
		return g;
	}

	public int get_h_cost() {
		return h;
	}

	public boolean get_goal_node() {
		return goal;
	}

	public boolean get_traversal_node() {
		return traversal_node;
	}

	///////////////////////////////////
	public boolean isEqual(Node N1, Node N2) {
		if (N2.get_x_value() == N1.get_x_value() && N2.get_y_value() == N1.get_y_value()) {
			return true;
		}

		return false;

	}

	public int compareTo(Node N1, Node N2) {
		if (N2.get_f_cost() > N1.get_f_cost()) {
			return 1;
		} else if (N2.get_f_cost() < N1.get_f_cost()) {
			return -1;
		}
		return 0;
	}

}
