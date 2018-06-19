'''Step 1: Data Definition - Need to be able to create a max heap, insert, and perculate up and down through heap tree
   Step 2: Signature - (int->int) input value into maxheap and output list/array
   Step 3: Test Cases - provided in heap_lab_tests.py'''


#Body-
class MaxHeap:
    def __init__(self, capacity = 50):
        self.capacity = capacity
        self.add_to_heap = [None]
        self.heap = [0] * (capacity + 1)
        self.size = 0

    def insert(self, item):                                          #inserts item
        if self.is_full():                                           #checks if full then it cant insert
            return False
        else:
            self.size += 1                                    #increase counter
            self.add_to_heap[self.size - 1] = item            #goes to add to max heap
            self.heap[self.size] = item
            self.perc_up(self.size)                           #perc up the correct position
            return True

    def del_max(self):
        if self.is_empty():                                          #checks if its empty first
            return None
        else:
            self.size -= 1                                    #decreases counter
            self.heap[self.size] = None
            self.add_to_heap.remove(self.heap[1])                    #remove at index 1
            self.heap[1], self.heap[self.size] = self.heap[1], self.heap[self.size]
            self.perc_down()                                         #perc down to correct position

    def heap_contents(self):                                         #return heap from index one to size of list
        return self.heap[1:self.size]

    def build_heap(self, alist):
        if self.is_full():                                           #if its full cant build
            return False
        else:
            i = 0
            self.size = len(alist)
            self.heap = [0] + alist[:]
            for i in range(len(alist)):                             #range through list
                self.insert(alist[i])                               #insert at nearest index
            return True

    def is_empty(self):                                             #checks if its empty first
        if self.size == 0:
            return True
        else:
            return False

    def is_full(self):                                              #checks to see if heap is full
        if self.size == self.capacity:
            return True
        else:
            return False

    def get_heap_cap(self):                                         #returns the initialized capacuty
        return self.capacity

    def get_heap_size(self):                                        #returns the numbers in the heaps
        return self.size

    def perc_down(self, i):                                         #go down through max heap
        while (i * 2) <= self.size:
            mv = self.helper_max(i)
            if self.heap[i] < self.heap[mv]:
                tmp = self.heap[i]
                self.heap[i] = self.heap[mv]
                self.heap[mv], self.heap[i],  = self.heap[mv], self.heap[i]
            i = mv

    def helper_max(self, i):                                         #finds the max
        if self.heap[2 * i + 1] <= self.heap[2 * i] or self.size < (2 * i + 1):
            return 2 * i                                             #left
        else:
            return (2 * i) + 1                                       #right

    def find_max(self):                    #returns index one/top of list
        if self.is_empty():
            return None
        else:
            return self.heap[1]            #index 1

    def perc_up(self, i):                  #perc up through maxheap
        while i // 2 > 0:
            if self.heap[i//2] < self.heap[i]:       #compare heap indexes
                tmp = self.heap[i//2]
                self.heap[i//2] = self.heap[i]
                self.heap[i] = tmp
        i = i // 2

def heap_sort_increase(alist):                                #heap sort increasing order
    global size
    size = len(alist)
    Build_heap(size,alist)                                    #build heap helper
    for i in range(size-1,0,-1):
        heap[0],heap[i] = heap[i],heap[0]                     #compare index
        size = size-1
        heapify(alist, 0, size)
        heapify(alist,i,size)
    print("heap sort array:", heap)

def left_child(i):                                            #left
    return 2*i+1

def right_child(i):                                           #right
    return 2*i+2

def heapify(heap,i,size):                                     #heapify helper to traverse and sort through
    l = left_child(i)
    r = right_child(i)
    if l <= size and r <= size:
        if r != size:
            if heap[l] >= heap[r]:
                max = heap[l]
                max_index = l
            elif heap[l] <= heap[r]:
                max = heap[r]
                max_index = r
            if heap[i] >= max:
                return
            elif heap[i] <= max:
                heap[i],heap[max_index] = max,heap[i]
                heapify(heap,max_index,size)
        else:
            heap[i],heap[l] = heap[l],heap[i]                  #indexes

# build a heap from unsorted array
def Build_heap(size,elements):
    iterate = size//2-1
    for i in range(iterate,-1,-1):
        heapify(elements,i,size)

#test heap sort increase
if __name__ == '__main__':
    #get input from user
    heap = [6,9,3,2,4,1,7,5,10]
    #sort the list
    heap_sort_increase(heap)