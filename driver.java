import java.util.Scanner;

public class driver {

		public static void main (String[] args) {
			
			String turn="Y";
			do {
				
			int x0, y0,x,y;
			System.out.println("Welcome to the AI run path finding program of 15*15 board game");
			System.out.println("Please make sure each number is between 0-14");
			System.out.print("Please enter the start cordinates X0 ");
			
			
			Scanner scan = new Scanner(System.in);
			x0=scan.nextInt();
			
			while(x0<0 || x0>=15) {
				System.out.println("Please Proper enter the start cordinates x0 ");
				x0=scan.nextInt();
			}
			
			System.out.println("Please enter the start cordinates Y0 ");
			y0=scan.nextInt();
			while(y0<0 || y0>=15) {
			System.out.println("Please enter the start cordinates Y0  between 0-14 ");
			y0=scan.nextInt();
			}
		Node start = new Node(x0, y0);
		System.out.print("Please enter the goal cordinates X ");
		x=scan.nextInt();
		while(x<0 || x>=15) {
		System.out.print("Please enter the goal cordinates X between 0-14 ");
		x=scan.nextInt();
		}
		
		System.out.print("Please enter the start cordinates Y ");
		y=scan.nextInt();
		while(y<0 || y>=15) {
		System.out.print("Please enter the start cordinates Y between 0-14 ");
		y=scan.nextInt();
		}

		Node end = new Node(x,y);
		AstarPath2 astar = 	astar= new AstarPath2(start, end);
		
		//shows intial grid
		astar.gridStartView();
		System.out.println("process");
		//help to move up down and left and right
		astar.traverse(false);
		System.out.println("Solution grid");
		
		//shows the animation for the extra credit and displays final solution
		astar.gridFinalSol();
		System.out.println("Path Found"+astar.get_asendingPath());
		System.out.println("Do you want to see each agent moving each tile till it reaches the goal Y or N ");
		scan.nextLine();
		String traverseEachSolution=scan.nextLine();
		
		if(traverseEachSolution.equals("Y")) {
			// i would recommend scrolling up as it has 
			AstarPath2 astar1= 	astar= new AstarPath2(start, end);
			astar1.traverse(true);
		}
		System.out.println("Thank you for playing");
		System.out.println("Do you want to play again Y or N ");
	
		turn=scan.nextLine();
		
		}while(turn=="Y");
		System.out.println("Thank you for playing Have a good day");
		
	}


	

}
