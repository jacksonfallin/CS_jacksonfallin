import unittest
import filecmp
from huffman import *

class TestList(unittest.TestCase):
   def test_cnt_freq1(self):
      freqlist	= cnt_freq("secondfile.txt")
      anslist = [0]*256
      anslist[98:105] = [0, 3, 7, 0, 0, 0, 0]
      self.assertListEqual(freqlist[98:105], anslist[98:105])

   def test_create_huff_tree1(self):
      freqlist = cnt_freq("secondfile.txt")
      hufftree = create_huff_tree(freqlist)
      numchars = 11
      charforroot = "a"
      self.assertEqual(hufftree.freq, 11)
      self.assertEqual(hufftree.char, 97)
      left = hufftree.left
      self.assertEqual(left.freq, 4)
      self.assertEqual(left.char, 97)
      right = hufftree.right
      self.assertEqual(right.freq, 7)
      self.assertEqual(right.char, 100)

   def test_create_code1(self):
      freqlist = cnt_freq("secondfile.txt")
      hufftree = create_huff_tree(freqlist)
      codes = create_code(hufftree)
      self.assertEqual(codes[ord('d')], '1')
      self.assertEqual(codes[ord('a')], '00')
      self.assertEqual(codes[ord('c')], '01')

   def test_cnt_freq2(self):
      freqlist	= cnt_freq("onechar.txt")
      anslist = [0]*256
      anslist[91:100] = [0, 0, 0, 0, 0, 0, 0, 1, 0]
      self.assertListEqual(freqlist[91:100], anslist[91:100])

   def test_create_huff_tree2(self):
      freqlist = cnt_freq("onechar.txt")
      hufftree = create_huff_tree(freqlist)
      numchars = 1
      charforroot = "a"
      self.assertEqual(hufftree.freq, 1)
      self.assertEqual(hufftree.char, 98)

   def test_create_code2(self):
      freqlist = cnt_freq("onechar.txt")
      hufftree = create_huff_tree(freqlist)
      codes = create_code(hufftree)
      self.assertEqual(codes[ord('b')], '')

   def test_01_encodefile(self):
      huffman_encode("morechar.txt", "output1.txt")
      # capture errors by running 'filecmp' on your encoded file
      # with a *known* solution file
      self.assertTrue(filecmp.cmp("output1.txt", "encoded_morechar.txt"))

   def test_01_decodefile(self):
      freqlist = cnt_freq("morechar.txt")
      huffman_decode(freqlist, "output1.txt", "decodefile1.txt")
      # capture errors by running 'filecmp' on your encoded file
      # with a *known* solution file
      self.assertTrue(filecmp.cmp("decodefile1.txt", "morechar.txt"))

   def test_02_encodefile(self):
      huffman_encode("onechar.txt", "output1.txt")
      # capture errors by running 'filecmp' on your encoded file
      # with a *known* solution file
      self.assertTrue(filecmp.cmp("output1.txt", "encoded_onechar.txt"))

   def test_empty_file(self):
        with open('empty_encoded.txt', 'w') as file:
            file.write('')
        freq_list = cnt_freq('empty.txt')
        expected_freq_list = [0] * 256
        self.assertEqual(freq_list, expected_freq_list)
        huffman_tree = create_huff_tree(freq_list)
        expected_huffman_tree = None
        self.assertEqual(huffman_tree, expected_huffman_tree)
        codes = create_code(huffman_tree)
        expected_codes = [''] * 256
        self.assertEqual(codes, expected_codes)
        tree_preorder = tree_preord(huffman_tree)
        expected_tree_preorder = ''
        self.assertEqual(tree_preorder, expected_tree_preorder)

if __name__ == '__main__':
   unittest.main()