
/**
 * An AVLTree implementation
 *
 */
public class AVLTree {

	public static void main(String[] args) {

	}

	//Entry point of the avl tree
	  private Node root;
	  //Count the number of nodes
	  private int nodeCounter = 0;

	  /**
	   * Get the height from the lowest point in the tree
	   * @return
	   */
	  public int height() {
	    if (root == null) return 0;
	    return root.getHeight();
	  } 

	  /**
	   * Get the total number of nodes in the tree
	   * @return
	   */
	  public int size() {
	    return nodeCounter;
	  }


	  /**
	   * If there are no nodes currently in the tree
	   * @return True if the tree is empty
	   */
	  public boolean isEmpty() {
	    return size() == 0;
	  }

	  /**
	   * Check if a value exists at this node
	   * @param value The value to check at this node
	   * @return True if the value exists, otherwise false
	   */
	  public boolean contains(int value) {
	    return contains(root, value);
	  }

	  /**
	   * Find the correct Node in the tree by recursively visting each node
	   * @param node Current node we are at
	   * @param value The value to search for
	   * @return True if the value was found, otherwise false
	   */
	  private boolean contains(Node node, Integer value) {
	    if (node == null) return false;
	    //Compare the value of each node
	    int compare = value.compareTo(node.getValue());
	    //Go into the left sub tree while the value is less than the value in the current node
	    if (compare < 0) { 
	    	return contains(node.getLeft(), value);
	    }
	    //Go into the right sub tree while the value is greater than the value in the current node
	    if (compare > 0) {
	    	return contains(node.getRight(), value);
	    }
	    //If the compared value is not less than or greater than, and not null, then the correct node is found
	    return true;
	  }

	  /**
	   * Add a new value to the tree
	   * @param value Integer value to be added
	   */
	  public void insert(Integer value) {
		//A new value cannot be null
	    if (value == null) return;
	    //Check that the new value is not already in the tree
	    if (!contains(root, value)) {
	    	//Insert the value
	      root = insert(root, value);
	      //Increase the number of nodes in the tree by 1
	      nodeCounter++;
	    }	
	  }

	  /**
	   * Find out where to add a new value to the tree
	   * @param node find the correct location to insert the new node
	   * @param value the Integer value to be added
	   * @return the node that has been added
	   */
	  private Node insert(Node node, Integer value) {
		//Create a new root node if the tree is empty
	    if (node == null) return new Node(value);
	    //Find out where to search to insert the new node
	    int compare = value.compareTo(node.getValue());
	    //If the value is less than at the current node, keep searching until this is false
	    if (compare < 0) {
	      node.setLeft(insert(node.getLeft(), value));
	      //Otherwise the value must be larger than at the current node, so keep searching until this is false
	    } 
	    else {
	      node.setRight(insert(node.getRight(), value));
	    }
	    //Update the height and balance facotr of the tree
	    update(node);
	    //Rebalance the tree
	    return balance(node);
	  }
	  
	  /**
	   * Remove a value from the tree
	   * @param value the value to be removed
	   */
	  public void remove(Integer value) {
		  //Cannot remove a null value
		  if(value == null) {
			  return;
		  }
		  //Check the location of the node
		  if(contains(root, value)) {
			  //Remove that node
			  root = remove(root, value);
			  //Decrease the number of nodes in the tree by 1
			  nodeCounter--;
		  }
	  }
	  
	  /**
	   * Find out where to remove a value from the tree
	   * @param node the node we are currently visiting
	   * @param value the Integer value to be removed
	   * @return the node to be removed
	   */
	  private Node remove(Node node, Integer value) {
		  //Cannot remove nothing
		  if(node == null) {
			  return null;
		  }
		  
		  //Find out which sub tree to visit
		  int compare = value.compareTo(node.getValue());
		  
		  //Find the value that we are trying to remove if that value is smaller than the current nodes value
		  if(compare < 0) {
			  node.setLeft(remove(node.getLeft(), value));
		  }
		  //Find the value we are trying to remove if that value is greater than the current nodes value
		  else if(compare > 0) {
			  node.setRight(remove(node.getRight(), value));
		  }
		  //If the node has been found
		  else {
			  //If only a right subtree, or no sub tree exists 
			  //Swap the node we want to remove with the right child
			  if(node.getLeft() == null) {
				  return node.getRight();
			  }
			  //If only a left subtree, or no sub tree exists
			  //Swap the node we want to remove with the left child
			  else if(node.getRight() == null) {
				  return node.getLeft();
			  }
			  //If a node has two sub trees
			  else {
				  //If the height of the left subtree is greater than the height of the right sub tree
				  if(node.getLeft().getHeight() > node.getRight().getHeight()) {
					  //Swap the value of the child into the new node
					  Node newValue = findMax(node.getLeft());
					  node.setValue(newValue.getValue());
					  //Find the largest node in the left subtree
					  node.setLeft(remove(node.getLeft(), newValue.getValue()));
				  }
				  else {
					  //Swap the value of this child into the new node
					  Node newValue = findMin(node.getRight());
					  node.setValue(newValue.getValue());
					  //Find the largest node if the right subtree
					  node.setRight(remove(node.getRight(), newValue.getValue()));
				  }
			  }
		  }
		  //Update the balance factor and height
		  update(node);
		  //Rebalance the tree
		  return balance(node);
	  }
	  
	  /**
	   * Find the maximum value in the tree
	   * @param node search to the right most node
	   * @return the right most node
	   */
	  private Node findMax(Node node) {
		  if(node == null) {
			  return null;
		  }
		  //Keep searching until the nodes right child is null
		  //return the node we are currently visiting
		  while(node.getRight() != null) {
			  node = node.getRight();		  
		  }
		  return node;
	  }

	  /**
	   * Find the minimum value in the tree
	   * @param node search to the left most node
	   * @return the left most node
	   */
	  private Node findMin(Node node) {
		  if(node == null) {
			  return null;
		  }
		  //Keep searching until the nodes left child is null
		  //returm the node we are currently visiting
		  while(node.getLeft() != null) {
			  node = node.getLeft();		  
		  }
		  return node;
	  }

	  /**
	   * Calculate the new value of the balance factor and the height of the current node
	   * @param node the balance factor and height of this node
	   */
	  private void update(Node node) {
		//If the node is equal to null return -1 else get the height of the current node
	    int leftNodeHeight = (node.getLeft() == null) ? -1 : node.getLeft().getHeight();
	    int rightNodeHeight = (node.getRight() == null) ? -1 : node.getRight().getHeight();
	    //Set the new height of this node
	    node.setHeight(1 + Math.max(leftNodeHeight, rightNodeHeight));
	    //Set the new balance factor of this node
	    node.setBalanceFactor(rightNodeHeight - leftNodeHeight);
	  }

	/**
	 * Rebalance the tree
	 * @param node check this nodes balance factor
	 * @return the new balance factor
	 */
	  private Node balance(Node node) {
	   
		//If the tree is left heavy
	    if (node.getBalanceFactor() == -2) {

	      //Left left case
	      if (node.getLeft().getBalanceFactor() <= 0) {
	        return leftLeftCase(node);
      
	      } 
	      //Left right case
	      else {
	        return leftRightCase(node);
	      }

	     
	    } 
	    //If the tree is right heavy
	    else if (node.getBalanceFactor() == +2) {

	      //Right right case
	      if (node.getRight().getBalanceFactor() >= 0) {
	        return rightRightCase(node);

	     
	      }
	      //Right left case
	      else {
	        return rightLeftCase(node);
	      }
	    }
	    //Otherwise the node only has -1, +1 or 0
	    return node;
	  }

	  private Node leftLeftCase(Node node) {
	    return rightRotation(node);
	  }

	  private Node leftRightCase(Node node) {
	    node.setLeft(leftRotation(node.getLeft()));
	    return leftLeftCase(node);
	  }

	  private Node rightRightCase(Node node) {
	    return leftRotation(node);
	  }

	  private Node rightLeftCase(Node node) {
	    node.setRight(rightRotation(node.getRight()));
	    return rightRightCase(node);
	  }

	  private Node leftRotation(Node node) {
	    Node parent = node.getRight();
	    node.setRight(parent.getLeft());
	    parent.setLeft(node);
	    update(node);
	    update(parent);
	    return parent;
	  }

	  private Node rightRotation(Node node) {
	    Node parent = node.getLeft();
	    node.setLeft(parent.getRight());
	    parent.setRight(node);
	    update(node);
	    update(parent);
	    return parent;
	  }
	  
	  public void preorderTraverseTree(Node currentNode) {
			if (currentNode != null) {
				System.out.println(currentNode.getValue());
				preorderTraverseTree(currentNode.getLeft());			
				preorderTraverseTree(currentNode.getRight());
			}
		}
	  
	  public void inOrderTraverseTree(Node currentNode) {
			if (currentNode != null) {
				inOrderTraverseTree(currentNode.getLeft());
				System.out.println(currentNode.getValue());
				inOrderTraverseTree(currentNode.getRight());
			}
		}
	  
	  public void postOrderTraverseTree(Node currentNode) {
			if (currentNode != null) {
				postOrderTraverseTree(currentNode.getLeft());
				postOrderTraverseTree(currentNode.getRight());
				System.out.println(currentNode.getValue());
			}
		}
	  /**
	   * Store information about each node in the tree
	   *
	   */

	  public class Node{

		    private int _balanceFactor;
		    private Integer _value;
		    private int _height;
		    private Node _left, _right;

		    /**
		     * Constructor
		     * @param value Integer value of this node
		     */
		    public Node(int value) {
		      this._value = value;
		    }

		    /**
		     * Check that the difference of heights of nodes are not more than 1
		     * @return
		     */
		    public int getBalanceFactor() {
		    	return _balanceFactor;
		    }
		    
		    /**
		     * Get the height of the tree
		     * @return
		     */
		    public int getHeight() {
		    	return _height;
		    }
		    
		    /**
		     * Get the value stored in this node
		     * @return
		     */
		    public Integer getValue() {
		    	return _value;
		    }
		    
		    /**
		     * Get the left child node from this node
		     * @return Null if no left sub tree exists
		     */
		    public Node getLeft() {
		      return _left;
		    }

		    /**
		     * Get the right child node of this node
		     * @return Null if no left sub tree exists
		     */
		    public Node getRight() {
		      return _right;
		    }

		    /**
		     * Update the balance factor of this node
		     * @param balanceFactor
		     */
		    public void setBalanceFactor(int balanceFactor) {
		    	_balanceFactor = balanceFactor;
		    }
		    
		    /**
		     * Set the height
		     * @param height
		     */
		    public void setHeight(int height) {
		    	_height = height;
		    }
		    
		    /**
		     * Set a new Integer value
		     * @param value
		     */
		    public void setValue(int value) {
		    	_value = value;
		    }
		    
		    /**
		     * Set a new left child node
		     * @param left
		     */
		    public void setLeft(Node left) {
		    	_left = left;
		    }
		    
		    /**
		     * Set a new right child node
		     * @param right
		     */
		    public void setRight(Node right) {
		    	_right = right;
		    }

		  }

}
