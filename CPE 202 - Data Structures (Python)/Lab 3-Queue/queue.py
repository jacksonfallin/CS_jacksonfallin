'''Step 1: Data Definition - Need to be able to enqueue/dequeue values into a queue
   Step 2: Signature - (int->int) input values into queue and output values, FIFO
   Step 3: Test Cases - self.assertFalse(que_a.is_empty())
                        self.assertTrue(que_a.is_full())
                        self.assertEqual(que_a.head.data, 3, 4)
                        self.assertEqual(que_a.dequeue(), 8)
                        self.assertEqual(que_a.num_in_queue, 0)'''

#5: Body-
class Node:
    def __init__(self, data=None):
        self.data = data
        self.next_node = None

class QueueLinked:
    def __init__(self, capacity):
        self.capacity = capacity
        self.num_in_queue = 0
        self.head = None
        self.tail = None

    def is_empty(self):                                     #checks to see if queue is empty or not
        if self.num_in_queue == 0:
            return True
        else:
            return False

    def is_full(self):                                      #checks to see if queue is full or not
        if self.num_in_queue == self.capacity:
            return True
        else:
            return False

    def enqueue(self, item):                                #allows to input of a new node, checks if queue is full first
        newNode = Node(item)
        if self.is_full():
            raise IndexError('Queue is full.')
        elif self.head is None and self.tail is None:
            self.num_in_queue = 0
            self.head = newNode
            self.tail = self.head
        else:
            self.num_in_queue += 1
            self.tail.next_node = newNode
            self.tail = newNode

    def dequeue(self):                                       #allows to remove the first node
        if self.num_in_queue == 0:
            raise IndexError("There is nothing in the queue.")
        else:
            data = self.head.data
            next = self.head.next_node
            if next is None:
                self.head = None
                self.tail = None
            else:
                self.num_in_queue -= 1
                self.head = next
            return data

    def num_in_queue(self):                                    #returns  queue size
            return self.num_in_queue

class QueueArray:
    def __init__(self, capacity):
        self.capacity = capacity
        self.head = 0
        self.tail = 0
        self.queue = [None]*capacity
        self.num_in_queue = 0

    def is_empty(self):
        if self.num_in_queue == 0:
            return True
        else:
            return False

    def is_full(self):
        if self.num_in_queue == self.capacity:
            return True
        else:
            return False

    def enqueue(self, item):
        if self.is_full():
            raise IndexError('Queue is full.')
        self.num_in_queue += 1
        self.queue[self.tail] = item
        if self.tail is self.capacity - 1:
            self.tail = 0
        else:
            self.tail += 1
        return item

    def dequeue(self):
        if self.is_empty():
            raise IndexError("Nothing to remove from Queue.")
        item = self.queue[self.head]
        self.head = (self.head + 1) % self.capacity
        self.num_in_queue -= 1
        return item

    def num_in_queue(self):
        if self.is_empty():
            return 0
        else:
            return self.num_in_queue


#6: Run
