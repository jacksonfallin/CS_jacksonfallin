import unittest
from heap_lab import *


class Test(unittest.TestCase):
    def test_insert(self):
        h1 = MaxHeap(3)
        h1.insert(10)
        self.assertEqual(h1.heap, [0, 10, 0, 0])
        self.assertTrue(h1.build_heap([]), False)

    def test_is_empty(self):
        h1 = MaxHeap(5)
        h2 = MaxHeap(1)
        h2.insert(22)
        self.assertTrue(h1.is_empty())
        self.assertFalse(h2.is_empty())

    def test_is_full(self):
        h1 = MaxHeap(1)
        h1.insert(22)
        self.assertTrue(h1.is_full())

    def test_heap_cap(self):
        h1 = MaxHeap(5)
        self.assertEqual(h1.get_heap_cap(), 5)

    def test_heap_size(self):
        h1 = MaxHeap(5)
        h1.insert(10)
        self.assertEqual(h1.get_heap_size(), 1)

    def test_build_heap(self):
        h1 = MaxHeap(5)
        h1.insert(10)
        self.assertEqual(h1.build_heap([]), True)

    def test_heap_build(self):
        heap_list = [6,9,3,2,4,1,7,5,10]
        h1 = MaxHeap(9)
        h1.build_heap(heap_list)
        self.assertEqual(h1.heap_contents(), [6, 9, 3, 2, 4, 1, 7, 5])

    def test_heap_increasing(self):
        heap = [6, 9, 3, 2, 4, 1, 7, 5, 10]
        h1 = MaxHeap(9)
        heap_list = heap_sort_increase(heap)
        self.assertEqual(heap_list, [1, 2, 3, 4, 5, 6, 7, 9, 10])


if __name__ == "__main__":
    unittest.main()