import unittest
import Samples
class TestSample(unittest.TestCase):
    def test_fib(self):
        self.assertEqual(Samples.fib(6), 8)
        
    def test_fact(self):
        self.assertEqual(Samples.factorial(6), 720)
        
    def test_maxlst(self):
        lst1 = [-2, 4, 99, 8, 24, 17]
        self.assertEqual(Samples. maxlist_rec(lst1), 99)


if __name__ == "__main__":
    unittest.main()

        
