package keyouxing.tree;

import java.util.LinkedList;
import java.util.Queue;

//Definition for binary tree
class TreeNode {
	int val;
	int col = 0;
	TreeNode left = null;
	TreeNode right = null;
	TreeNode(int x) { val = x; }
}

class Tree{
	
	
	// ----------------------------------------------------- Constructors
	
	
	public Tree(int [] pre,int [] in) {
		
		initTree(pre, in);
		
	}
	
	
	// ----------------------------------------------------- Instance Variable
	
	
	public int level;
	
	public TreeNode root;
	
	
	// ----------------------------------------------------- Properties
	
	
	public int getLevel() {
		
		return level;
		
	}
	
	public TreeNode getRoot() {
		
		return root;
		
	}
	
	// ----------------------------------------------------- Construct Tree
    
	
	public TreeNode constructCore(int[] pre, int[] in, int preStart, int preEnd, int inStart, int inEnd) {
		
		if(preStart > preEnd || inStart > inEnd)
			return null;
		
		TreeNode root = new TreeNode(pre[preStart]);
		
		int i=inStart;
		while(i<=inEnd) {
			if(in[i] == pre[preStart]) {
				break;
			}
			i++;
		}
		
		root.left = constructCore(pre, in, preStart+1, preStart +i-inStart, inStart, i-1);
		root.right = constructCore(pre, in, preStart +i-inStart+1, preEnd, i+1, inEnd);
		
		return root;
		
	}
	
	private int getLevelCore(TreeNode root) {
		
		if(root == null) {
			return 0;
		}
		
		int left = getLevelCore(root.left);
		int right = getLevelCore(root.right);
		
		return ((left >= right) ? left : right)+1;
		
	}
	
	private void initTree(int [] pre,int [] in) {
		
		root= constructCore(pre, in, 0, pre.length-1, 0, in.length-1);
		level = getLevelCore(root);
		
	}

}

public class PrintTree {

	String[][] boundary = null;

	//level travel
	public void printTree(TreeNode root) {

		if(root == null) {
			return;
		}
		
		int curLevel = 0;
		int width = boundary[0].length;
		int distance = (width+1)/4;
		root.col = (width-1)/2;
		
		Queue<TreeNode> queue= new LinkedList<TreeNode>();
		queue.offer(root);
		
		boundary[0][width/2] = (Integer.toString(root.val));
		while(!queue.isEmpty()) {
			
			int length = queue.size();
			
			while(length > 0) {
				
				TreeNode node = queue.poll();
				
				if(node.left!=null) {
					node.left.col = node.col-distance;
					queue.offer(node.left);
					boundary[curLevel+1][node.left.col] = (Integer.toString(node.left.val));
				}
				
				if(node.right != null) {
					node.right.col = node.col+distance;
					queue.offer(node.right);
					boundary[curLevel+1][node.right.col] = (Integer.toString(node.right.val));
				}
				
				length--;
				
			}
			distance = distance/2;
			curLevel++;
		}
		
	}
	
	/**
	 * 完全二叉树
	 * 
	 */
	public void initBoundary(int level) {
		
		int col = (int)Math.pow(2, level)-1;
		int row = level;
		boundary = new String[row][col];
	}
	
	private void printAll() {
		
		for(int i=0;i<boundary.length;i++) {
			
			for(int j=0;j<boundary[0].length;j++) {
				if(boundary[i][j] ==null) {
					System.out.print("  ");
				}else {
					System.out.print(boundary[i][j]+" ");
				}
				
			}
			
			System.out.println();
			
		}
		
	}
	
	public static void main(String[] args) {
		
		int[] pre = new int[] {1,2,4,7,3,5,6,8};
		int[] in = new int[] {4,7,2,1,5,3,8,6};
		Tree tree = new Tree(pre, in);
		TreeNode root = tree.getRoot();
		
		PrintTree solution =new PrintTree();
		solution.initBoundary(tree.getLevel());
		solution.printTree(root);
		solution.printAll();
		
	}
}
