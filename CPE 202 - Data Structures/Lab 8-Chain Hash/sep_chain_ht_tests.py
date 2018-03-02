import unittest
from sep_chain_ht import *


class Test(unittest.TestCase):
    def test_Insert_and_Get(self):
        ht = MyHashTable(11)
        ht.insert(1, 'blue')
        ht.insert(2, 'red')
        ht.insert(3, 'green')
        self.assertEqual(ht.ht, [[], [[1, 'blue']], [[2, 'red']], [[3, 'green']], [], [], [], [], [], [], []])
        self.assertEqual(ht.get(1), (1, 'blue'))
        self.assertEqual(ht.get(2), (2, 'red'))
        self.assertEqual(ht.get(3), (3, 'green'))

    def test_remove(self):
        ht = MyHashTable(11)
        ht.insert(2, 'red')
        ht.insert(3, 'blue')
        ht.insert(7, 'yellow')
        ht.insert(11, 'grey')
        self.assertEqual(ht.remove(11), [11, 'grey'])
        self.assertEqual(ht.remove(3), [3, 'blue'])

    def test_size(self):
        ht = MyHashTable(11)
        ht.insert(1, 'red')
        ht.insert(2, 'blue')
        ht.insert(3, 'yellow')
        ht.insert(4, 'grey')
        ht.insert(5, 'green')
        ht.insert(6, 'black')
        self.assertEqual(ht.size(), 6)

    def test_load_factor(self):
        ht = MyHashTable(11)
        ht.insert(3, 'red')
        ht.insert(4, 'blue')
        ht.insert(5, 'purple')
        ht.insert(6, 'purple')
        self.assertEqual(ht.load_factor(), 0.36363636363636365)

    def test_collision(self):
        ht = MyHashTable(11)
        ht.insert(1, 'purple')
        ht.insert(1, 'purple')
        ht.insert(2, 'white')
        ht.insert(2, 'white')
        ht.insert(3, 'brown')
        ht.insert(3, 'brown')
        self.assertEqual(ht.collisions(), 3)


if __name__ == "__main__":
    unittest.main()