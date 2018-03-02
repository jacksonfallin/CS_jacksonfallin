'''Step 1: Data Definition - Need to be able to pop/push values into a stack; also recognize when can no longer push/pop
   Step 2: Signature - (int->int) input values into stack and output values
   Step 3: Test Cases - self.assertTrue(s1.is_empty())
                        self.assertFalse(s1.is_empty())
                        self.assertTrue(s1.is_full())
                        self.assertFalse(s1.is_full())
                        self.assertEqual(s1.pop(), 1)
                        self.assertFalse(s1.pop())
                        self.assertEqual(s1.peek(), 3)
                        self.assertEqual(s1.size(), 2)'''

#Step 5: Body -
class Node:
    def __init__(self, data=None, next_node=None):              #
        self.data = data
        self.next_node = next_node

class StackLinked:
    def __init__(self, capacity):
        self.capacity = capacity
        self.head = Node(None, None)
        self.num_items = 0

    def is_empty(self):
        return self.num_items == 0

    def is_full(self):
        if self.num_items == self.capacity:
            return True
        else:
            return False
    def push(self, item):
        if self.is_full():
            raise IndexError('Stack is full.')
        self.num_items += 1
        temp = Node(item)
        temp.next_node = self.head
        self.head = temp
    def pop(self):
        if self.is_empty():
            raise IndexError('Cant pop from empty stack.')
        else:
            self.num_items -= 1
            temp = self.head.data
            self = self.head.next_node
            return temp
    def peek(self):
        return self.head.data
    def size(self):
        if self.is_empty():
            return 0
        else:
            return self.num_items

class StackArray:
    """Implements an efficient last-in first-out Abstract Data Type using a Python List"""
    def __init__(self, capacity):
        """Creates and empty stack with a capacity"""
        self.capacity = capacity       #this is example for list implementation
        self.head = [None] * capacity  #this is example for list implementation
        self.num_items = 0             #this is example for list implementation

    def is_empty(self):                #checks to see if stack is empty or not
        """Returns true if the stack self is empty and false otherwise"""
        if self.num_items == 0:
            return True
        else:
            return False

    def is_full(self):                 #checks to see if stack is full by comparing it to the capacity
        """Returns true if the stack self is full and false otherwise"""
        if self.num_items == self.capacity:
            return True
        else:
            return False

    def push(self, item):              #pushes values into stack
        if self.is_full():
            raise IndexError('Stack is full.')
        self.num_items += 1
        self.head[self.num_items - 1] = item
        return self

    def pop(self):                     #pops (LIFO) value from stack
        if self.is_empty():
            raise IndexError("Nothing to remove from stack.")
        temp = self.head[self.num_items - 1]
        self.head[self.num_items - 1] = None
        self.num_items -= 1
        return temp

    def peek(self):                    #grabs top element in the stack without altering stack
        if self.is_empty():
            raise IndexError('Nothing to peek in the stack.')
        else:
            return self.head[self.num_items - 1]

    def size(self):                    #returns the size or number of items in the stack
        """Returns the number of elements currently in the stack, not the capacity"""
        if self.is_empty():
            return 0
        else:
            return self.num_items


#Step 6: Run