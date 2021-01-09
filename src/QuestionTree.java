/*Edward Burns
 * 20776493
 * Project 3
 */

import java.io.PrintStream;
import java.util.Scanner;
import java.io.*;


public class QuestionTree {

	private Scanner scanner;
	private QuestionNode root; 
	private int count;
	private int wins;
	PrintStream output;
	
	public QuestionTree(Scanner input, PrintStream output) {
		/*	if(input==null||output==null) {
		throw new IllegalArgumentException();
		*/
			
			root = new QuestionNode("Jedi");
			scanner=input;
			this.output = output;
	}
	public void play() {
		count++;
	
		root=playHelper(root);
		
	}
		
/*
 * Assuming user gives appropriate responses. If this were not the case, a while loop 
 * would be added to ensure that 
 */
	private QuestionNode playHelper (QuestionNode current) {
		
		String answer;
		//test to see if it is an answer node
		if(current.left == null || current.right == null) {
			output.print("Would your object happen to be "+current.data+"?");
			//answer=scanner.nextLine().toUpperCase();
			//answer="Y";
			if(UserInterface.nextAnswer(scanner)) {
				output.println("I win!");
				wins++;
	
			}
			else {
				
				output.println("I lose. What is your object?");
				QuestionNode object = new QuestionNode(scanner.nextLine());
				output.println("Type a yes/no question to distinguish your item from "+current.data+":");
				String q = scanner.nextLine();
				output.println("And what is the answer for your object?");
				String a= scanner.nextLine().toUpperCase();
			
				if(a.contains("Y")) {
					//if answer was yes, add the question node as the left child. if no, add to the right child
					current = new QuestionNode(q,object,current);
				}
				else {
					current = new QuestionNode(q,current,object);
					//current = ply(q,current,object);
				}
				
				//current = lose(current);
			}
		}
		else {
			//print question and then traverse to either the left or right node
			output.println(current.data);
			answer=scanner.nextLine().toUpperCase();
			if(answer.contains("Y")) {
				
				current.left=playHelper(current.left);
			}
			else {
				
				current.right=playHelper(current.right);
				
			}
		}
		// make the root a question node
		if(count==1) {
			root=current;
			
		}
		return current; 
	}
	

	public void save(PrintStream output) {
		if(output==null) {
			throw new IllegalArgumentException();
		}
		saveHelper(output,root);
	}
	
	private void saveHelper(PrintStream output, QuestionNode root) {
		
		//test for answer node
		if(root.left == null || root.right == null) {
			
			output.print("A:");
			output.println(root.data);
		}
		else {
			output.print("Q:");
			output.println(root.data);
			saveHelper(output,root.left);
			saveHelper(output,root.right);
		}
	}
	
	
	public void load(Scanner input) {
		if(input==null) {
			throw new IllegalArgumentException();
		}
		root=loadHelper(input);
	}
		
	
	private QuestionNode loadHelper(Scanner input){
		QuestionNode current=null;
		
		if(input.hasNextLine()) {
			String QA=input.nextLine();
			
		//if question node, must then print out the child nodes
			if(QA.contains("Q:")) {
			
				current = new QuestionNode(QA.substring(2),loadHelper(input),loadHelper(input));
			}
			else {
				current = new QuestionNode(QA.substring(2));
				
			}
		}	
		return current;
	}
		
		
	public int totalGames() {
		
		return count;
		
	}
	public int gamesWon() {
		
		return wins;
	}
	
	
}
