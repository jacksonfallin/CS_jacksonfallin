'''Step 1: Data Definition - Need to be able to insert a node into a BST as well as delete a node, find max, find min, print inorder, print levels, etc
   Step 2: Signature - (int->int) input integer node into BST and output is a new BST
   Step 3: Test Cases - self.assertEqual(t.root.key, 5)
                        self.assertEqual(t.root.left.key, 2)
                        self.assertTrue(t.is_empty())
                        self.assertTrue(t.find(-8))
                        self.assertFalse(t.find(0))
                        self.assertEqual(t.root.key, 8)
                        self.assertEqual(t.root.right.key, 8)
                        self.assertEqual(t.root.left, None)'''

#Step 5: Body -

class TreeNode:
    """Tree node: left and right child + data which can be any object"""
    def __init__(self, key):
        self.key = key
        self.data = None
        self.left = None
        self.right = None

    def insert(self, key):
        """Insert new node with key, assumes data not present """
        if self.key is not None:
            if key < self.key:
                if self.left is None:                                        #goes to left subtree to insert
                    self.left = TreeNode(key)
                else:
                    self.left.insert(key)
            elif key > self.key:
                if self.right is None:                                       #goes ot right sub tree to insert
                    self.right = TreeNode(key)
                else:
                    self.right.insert(key)
        else:
            self.key = key

    def find_successor(self):
        """returns successor"""
        if self.right is not None:                                                  #right side of tree
            return self.right.helper_successor()                                    #access helper def
        current = self
        while current.parent is not None and current.parent.right is current:       #checks for parents
            current = current.parent
        return current.parent

    def helper_successor(self):
        """helper to find_successor"""
        """returns node with smallest key in subtree rooted by this node"""
        current = self
        while current.left is not None:                 #checks left side of tree
            current = current.left
        return current

    def helper_delete(self, key):
        """delete node with given key and return the root node"""
        if self.key == key:
            if self.right and self.left:                          #node we need to delete
                [psucc, succ] = self.right.helper_Find_Min(self)  #get successor node and parent
                if psucc.left == succ:                            #splice out successor
                    psucc.left = succ.right
                else:
                    psucc.right = succ.right
                succ.left = self.left                           #reset the left and right of successor
                succ.right = self.right
                return succ
            else:
                if self.left:
                    return self.left                            #left subtree
                else:
                    return self.right                           #right subtree
        else:
            if self.key > key:                                  #key in the left subtree
                if self.left:
                    self.left = self.left.helper_delete(key)
                else:                                           #key not in tree
                    return  None
            else:                                               #key in right subtree
                if self.right:
                    self.right = self.right.helper_delete(key)
        return self

    def helper_Find_Min(self, parent):
        """parent node passed as an argument"""
        """leftmost child reached, call can return both the parent to the successor and the successor"""
        if self.left:
            return self.left.helper_Find_Min(self)
        else:
            return [parent, self]

    def find_max(self):
        if self.right is not None:                              #right side of subtree
            return self.right.find_max()
        else:                                                   #no right side so return key
            return self.key

    def find_min(self):
        if self.left is not None:                               #left side of subtree
            return self.left.find_min()
        else:                                                   #no left side so return key
            return self.key

    def inorder_print_tree(self):
        if self.left is not None:
            self.left.helper_print_tree()                   #access helper_print_tree for left side
        print(self.key)                                    #prints tree data
        if self.right is not None:
            self.right.helper_print_tree()                  #access helper_print_tree for right side
        print("")

    def helper_print_tree(self):
        """print tree content inorder"""
        if self.left is not None:                               #checks left side
            self.left.helper_print_tree()
        print(self.key, end == ", ")                               #print key and ends
        if self.right is not None:                              #checks right side
            self.right.helper_print_tree()

    def print_levels(self, lvl=0):
        if self.left is not None:                                   #checks to see that left subtree exists
            self.left.print_levels(lvl + 1)
        print("(" + str(self.key) + "," + str(lvl) + ")" + ",")
        if self.right is not None:
            self.right.print_levels(lvl + 1)

class BinarySearchTree:
    def __init__(self):
        self.root = None

    def find(self, key):
        current_root = self.root                                                    #current node
        while current_root is not None and current_root.key != key:
            if key < current_root.key:                                              #checks to see if key is less than root
                current_root = current_root.left                                    #goes to left subtree
            else:
                current_root = current_root.right                                   #goes to right subtree
        if current_root is None:                                                    #checks to see if root exists
            return False
        else:
            return True

    def insert(self, newkey):
        if self.root is None:                                                       #if tree is empty
            self.root = TreeNode(newkey)
            return
        else:
            p = self.root
            if p.key > newkey:                                                      #checks to see if root is greater than newkey
                if p.left is None:                                                  #goes to left subtree
                    p.left = TreeNode(newkey)
                else:
                    p.left.insert(newkey)                                           #inserts newkey
            else:
                if p.right is None:                                                 #goes to right subtree
                    p.right = TreeNode(newkey)
                else:
                    p.right.insert(newkey)                                          #inserts newkey

    def delete(self, key):
        if self.root:
            self.root = self.root.helper_delete(key)                                #access helper_delete

    def print_tree(self):
        if self.root is not None:                                                   #checks to see if root exists
            print('Inorder: ')
            self.root.inorder_print_tree()                                          #access helper function to print inorder

    def is_empty(self):
        if self.root is None:                                                       #checks to see if root exists/is empty
            return True
        else:
            return False

#Step 6: Run
