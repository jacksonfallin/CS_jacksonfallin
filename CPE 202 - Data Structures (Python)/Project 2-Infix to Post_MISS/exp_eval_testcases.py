import unittest
from expressions import * 

class test_expressions(unittest.TestCase):
    def test_invalid(self):
        self.assertFalse(postfix_valid(""))
        self.assertFalse(postfix_valid("2 3"))
 
    def test_valid(self):
        self.assertTrue(postfix_valid("2 3 +"))
        self.assertTrue(postfix_valid("2 3 -"))
        self.assertTrue(postfix_valid("2 3 *"))
        self.assertTrue(postfix_valid("2 3 /"))

    def test_postfixeval1(self):
        self.assertAlmostEqual(postfix_eval("3 5 +"), 8)

    def test_inToPostBasicAssoc(self):
        self.assertEqual(infix_to_postfix("6 - 3"), "6 3 -")

    def test_inToPostBasicAssoc1(self):
        self.assertEqual(infix_to_postfix("6 - 3 + 2"), "6 3 - 2 +")

    def test_inToPostBasicAssoc2(self):
        self.assertEqual(infix_to_postfix("6 ^ 3 ^ 2"), "6 3 2 ^ ^")

    def test_inToPostBasicAssoc3(self):
        self.assertEqual(infix_to_postfix("6 * ( 3 + 2 )"), "6 3 2 + *")

    def test_inToPostBasicAssoc5(self):
        self.assertEqual(infix_to_postfix("6"), "6")
        with self.assertRaises(ValueError): 
            postfix_eval("3 0 /")

if __name__ == "__main__":
    unittest.main()
