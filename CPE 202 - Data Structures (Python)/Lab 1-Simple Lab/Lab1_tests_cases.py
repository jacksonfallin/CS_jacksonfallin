#Test Cases for Lab1
from Lab1 import *

import unittest

class TestCase(unittest.TestCase):
    def test_max_list_iter(self):
        tlist = [10, 9, 8 ,4, 9]
        self.assertEqual(max_list_iter(tlist),10)
        tlist = []
        with self.assertRaises(ValueError):  # magic - uses context manager
            max_list_iter(tlist)  

    def test_reverse_rec(self):
        self.assertEqual(reverse_rec("abcd"),"dcba")

    def test_bin_search(self):
        # Add code here.
        pass

        
# Run the unit tests.
if (__name__ == '__main__'):
    unittest.main()
