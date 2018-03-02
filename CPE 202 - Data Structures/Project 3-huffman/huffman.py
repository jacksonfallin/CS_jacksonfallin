# Name:Jackson Fallin
# Section: 11/12
# Data Definition
'''Step 1: Data Definition - Need to be able to open text file and create huffman tree; traverse through each node and assign binary values when traversing down
   Step 2: Signature - (int->int) assign nodes binary code and create huffman tree'''

#Body-
class HuffmanNode:
    def __init__(self, char, freq):
        self.char = char                                 #the character code
        self.freq = freq
        self.code = None
        self.left = None
        self.right = None

# returns true if tree rooted at node a comes before tree rooted at node b
def comes_before(a, b):                                               #orders nodes
    if a.freq == b.freq:                                              #if frequencies are equal
        if a.char < b.char:
            return True
        else:
            return False
    if a.freq < b.freq:                                               #if frequency of node a is less than b
        return True
    else:
        return False

#counts the frequecy
def cnt_freq(filename):                                               #opens a text file and reads through
    try:
        with open(filename, encoding='utf-8-sig') as file:
            frequency = [0] * 256
            string = file.read()
            for character in string:
                index = ord(character)
                frequency[index] += 1
    except:                                                           #if file doesnt exist
        raise IOError('File doesnt exist')
    return frequency

#creates the huffman tree
def create_huff_tree(char_freq):
    list = []
    #list of nodes
    for index in range(0, 256):                                       #checking index
        if char_freq[index] != 0:
            new_node = HuffmanNode(index, char_freq[index])
            list.append(new_node)
    if len(list) == 0:
        return None
    while len(list) > 1:                                              #finding min then removing node
        node1 = findMin(list)
        list.remove(node1)
        node2 = findMin(list)
        list.remove(node2)
        add_freq = node1.freq + node2.freq
        min_char = min(node1.char, node2.char)
        huff_node = HuffmanNode(min_char, add_freq)
        huff_node.left = node1
        huff_node.right = node2
        list.insert(0, huff_node)
    return list[0]

#find min to help create huff tree
def findMin(list):                                                     #finds min of huffman nodes
    min = list[0]
    for index in range(0, len(list)):                                  #range of possible nodes
         current = list[index]
         if comes_before(current, min):
             min = current
    return min

def create_code(root_node):                                            #creats huffman code for nodes(str)
    if root_node is None:
        return [""] * 256
    else:
        str_list = [""] * 256
        helper_function(root_node, str_list)
        return str_list

def helper_function(node, str_list, rep = ""):                        #helper function to assign binary when traversing through huffman tree
    if node.left is None and node.right is None:                      #recognizes leaves of tree
        node.code = rep
        str_list[node.char] = node.code
    if node.left is not None:                                         #paths left
        prv_left = rep
        rep = rep + "0"
        helper_function(node.left, str_list, rep)
        rep = prv_left
    if node.right is not None:                                        #paths right
        prv_temp = rep
        rep = rep + "1"
        helper_function(node.right, str_list, rep)
        rep = prv_temp

#encode a text file
def huffman_encode(in_file, out_file):
    freqlist = cnt_freq(in_file)                                              #call other definitions
    hufftree = create_huff_tree(freqlist)
    codes = create_code(hufftree)

    with open(in_file, 'r') as fin, open(out_file, 'w') as fout:
        string = fin.read()
        if hufftree is None:                                                  #huff tree has no children
            pass
        elif hufftree.left is not None and hufftree.right is not None:        #huff tree has children
            for character in string:
                converted_code = codes[ord(character)]
                fout.write(converted_code)
        #else:
            #fout.write("'"+chr(hufftree.char)+"' " + hufftree.freq)

#decodes a text file
def huffman_decode(freqs, encoded_file, decode_file):
    with open(encoded_file) as fin, open(decode_file, 'w') as fout:
        huff_tree = create_huff_tree(freqs)                                #call definitions
        current = huff_tree
        while True:
            string = fin.read(1)
            if not string:                                                 #not string
                break
            else:
                if string == '0':                                          #path left
                    current = current.left
                else:                                                      #path right
                    current = current.right
                if current.right is None and current.left is None:         #reach leaf
                    fout.write(chr(current.char))
                    current = huff_tree

#returns a tree in preorder order
def tree_preord(node, temp = ''):
    if node is None:                                                 #if no root
        return ''
    if node.left is None and node.right is None:                     #if reach leaf
        return temp + '1' + chr(node.char)
    temp = temp + '0'                                                #track preorder
    if node.left is not None:                                        #path left
        temp = tree_preord(node.left, temp)
    if node.right is not None:                                       #path right
        temp = tree_preord(node.right, temp)
    return temp

#Run