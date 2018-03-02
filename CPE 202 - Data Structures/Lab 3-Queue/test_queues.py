import unittest
from queue import QueueArray
from queue import QueueLinked



class TestQueueLinked(unittest.TestCase):
    def test_is_empty(self):
        que_a = QueueLinked(4)
        self.assertTrue(que_a.is_empty())

    def test_is_not_empty(self):
        que_a = QueueLinked(4)
        que_a.enqueue(4)
        que_a.enqueue(5)
        que_a.enqueue(6)
        self.assertFalse(que_a.is_empty())

    def test_is_full(self):
        que_a = QueueLinked(1)
        que_a.enqueue(1)
        que_a.enqueue(2)
        self.assertTrue(que_a.is_full())

    def test_is_not_full(self):
        que_a = QueueLinked(6)
        que_a.enqueue(4)
        self.assertFalse(que_a.is_full())

    def test_enqueue(self):
        que_a = QueueLinked(3)
        que_a.enqueue(3)
        que_a.enqueue(4)
        self.assertEqual(que_a.head.data, 3, 4)

    def test_dequeue(self):
        que_a = QueueLinked(5)
        with self.assertRaises(IndexError):
            que_a.dequeue()
        que_a.enqueue(6)
        que_a.enqueue(7)
        que_a.enqueue(8)
        que_a.enqueue(9)
        self.assertEqual(que_a.dequeue(), 6)
        self.assertEqual(que_a.dequeue(), 7)
        self.assertEqual(que_a.dequeue(), 8)

    def test_num_in_queue(self):
        que_a = QueueLinked(2)
        que_a.enqueue(6)
        que_a.enqueue(7)
        que_a.enqueue(7)
        self.assertTrue(que_a.is_full())
        with self.assertRaises(IndexError):
            que_a.enqueue(0)
        self.assertEqual(que_a.num_in_queue, 2)
        self.assertEqual(que_a.dequeue(), 6)
        self.assertEqual(que_a.num_in_queue, 1)
        self.assertFalse(que_a.is_empty())

class TestQueueArray(unittest.TestCase):
    def test_is_empty(self):
        que_a = QueueArray(4)
        self.assertTrue(que_a.is_empty())

    def test_is_not_empty(self):
        que_a = QueueArray(4)
        que_a.enqueue(4)
        que_a.enqueue(5)
        que_a.enqueue(6)
        self.assertFalse(que_a.is_empty())

    def test_is_full(self):
        que_a = QueueArray(2)
        que_a.enqueue(1)
        que_a.enqueue(2)
        self.assertTrue(que_a.is_full())

    def test_is_not_full(self):
        que_a = QueueArray(6)
        que_a.enqueue(4)
        self.assertFalse(que_a.is_full())

    def test_enqueue(self):
        que_a = QueueArray(3)
        que_a.enqueue(3)
        que_a.enqueue(4)
        que_a.enqueue(5)
        self.assertEqual(que_a.queue, [3, 4, 5])

    def test_dequeue(self):
        que_a = QueueArray(3)
        with self.assertRaises(IndexError):
            que_a.dequeue()
        que_a.enqueue(6)
        que_a.enqueue(7)
        que_a.enqueue(8)
        self.assertEqual(que_a.dequeue(), 6)
        self.assertEqual(que_a.dequeue(), 7)
        self.assertEqual(que_a.dequeue(), 8)

    def test_num_in_queue(self):
        que_a = QueueArray(2)
        que_a.enqueue(6)
        que_a.enqueue(7)
        self.assertTrue(que_a.is_full())
        with self.assertRaises(IndexError):
            que_a.enqueue(0)
        self.assertEqual(que_a.num_in_queue, 2)
        self.assertEqual(que_a.dequeue(), 6)
        self.assertEqual(que_a.num_in_queue, 1)
        self.assertFalse(que_a.is_empty())


if __name__ == "__main__":
    unittest.main()



