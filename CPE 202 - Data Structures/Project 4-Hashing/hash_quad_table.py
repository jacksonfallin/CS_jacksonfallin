# Name:Jackson Fallin
# Section: 11/22
'''Step 1: Data Definition - Need to be able to open text file and create huffman tree; traverse through each node and assign binary values when traversing down
   Step 2: Signature - (int->int) assign nodes binary code and create huffman tree
   Step 3: Test Cases - print("File compare:", filecmp.cmp('test1.txt', 'concord1.txt'))
                        print("File compare:", filecmp.cmp('test2.txt','concord2.txt'))'''

#Body-
import string

class HashTableQuadPr:

    def __init__(self, capacity=251):                                 # initialize hash table
        self.table_size = capacity                                    # initialize size
        self.items_in_hash = 0                                        # 0 items to start
        self.hash = []                                                # creates hash list
        for i in range(capacity):                                     # range of the hash list from start to end
            self.hash.append(None)

    def helper_function(self, strng, void, val):                      # helper to read file
        if len(strng) is 0:                                           # if there is nothing in the file
            return
        try:                                                          # try except function to check for string
            float(strng)
        except:
            if void is None or not void.check_helper(strng.lower()):  # helper to see key exists, make string lower case
                self.helper_to_insert(strng.lower(), val)             # insert

    def read_stop(self, filename):                                    # sift through stop words and hash
        with open(filename, 'r') as fin:                              # with function to open file
            while True:                                               # continue to loop
                stop = fin.readline()
                if stop:                                              # go through until stop word ends
                    if stop.endswith("\n"):
                        stop = stop[:-1]
                    self.helper_to_insert(stop, None)                 # insert into hash
                else:                                                 # break loop
                    break

    def read_file(self, filename, stoptable):                         # read from file and input into hash
        with open(filename) as fin:                                   # with function to open file
            bool = True                                               # creates boolean to test string.digits
            val = 1                                                   # create counter value for checking
            strng = ''                                                # initialize string
            while True:                                               # while loop
                s = fin.read(1)                                       # open and read file
                if not s:
                    break
                elif s is "'":                                        # for apostrophe
                    pass
                elif s in string.ascii_letters or s in string.digits: # check digits and ascii letters
                    strng = strng + s
                    bool = False
                elif not bool or s is '\n':                           # recognize to stop
                    bool = True
                    self.helper_function(strng, stoptable, val)       # helper function which inserts
                    if s is '\n':
                        val += 1                                      # increment to track
                    strng = ''                                        # reinitialize string
            self.helper_function(strng, stoptable, val)               # helper function which inserts

    def get_tablesize(self):                                          # gets table size
        return self.table_size                                        # return table size

    def save_concordance(self, outputfilename):                       # sample output file
        with open(outputfilename, "w") as op:                         # with function to open file
            bool = True                                               # boolean for x in hash
            hash = []                                                 # hash list
            for x in self.hash:                                       # loop for item in hash
                if x is not None:
                    hash.append(x)                                    # append to list
            hash = sorted(hash)                                       # sort hash
            for x in hash:                                            # loop for item in hash
                if bool:
                    bool = False
                else:
                    op.write("\n")                                    # recognize end of hash
                op.write(x[0] + ":")                                  # write at list index 0
                for v in x[1]:
                    op.write("\t" + str(v))                           # tab and rest of string

    def get_load_fact(self): # return load factor
        return self.items_in_hash / self.table_size                   # calc for load factor is items in hash/table size

    def myhash(self, key, table_size):                                # return integer from 0 to size of table
        n = 0                                                         # create count
        for cont in range(min(len(key), 8)):                          # look for min in of len(key ) and 8
            n = 31 * n + ord(key[cont])
        return n % table_size                                         # calculation

    def helper_to_insert(self, key, item):                            # insert quad table using key, item
        if self.get_load_fact() + 1 / self.table_size > 0.5:          # check for load factor helper
            self.increase_table()                                     # increase table accordingly
        num = 0                                                       # create arb num
        s = 0                                                         # sum
        i = self.myhash(key, self.table_size)                         # create index
        prim = i                                                      # var for index
        if item is not None:                                          # if item exists
            while self.hash[i] is not None and self.hash[i][0] != key:
                num += 1                                              # increment counter num
                s = num ** 2                                          # sum to double num
                s += prim                                             # increment index sum
                s %= self.table_size                                  # remain
                i = s                                                 # set index to sum
            if self.hash[i] is None:
                self.hash[i] = (key, [item])                          # index new item
                self.items_in_hash += 1                               # increment up one
            elif self.hash[i] is not None and item not in self.hash[i][1]:
                self.hash[i][1].append(item)                          # append item to hash
        else:
            while self.hash[i] is not None:
                num += 1                                              # increment counter num
                s = num ** 2                                          # sum to double num
                s += prim                                             # increment index sum
                s %= self.table_size                                  # remain
                i = s                                                 # set index to sum
            self.hash[i] = (key, None)                                # hash into the list
            self.items_in_hash += 1                                   # track items in hash

    def check_helper(self, key):                                      # see if key is there
        num = 0                                                       # create arb num
        s = 0                                                         # sum
        i = self.myhash(key, self.table_size)                         # create index
        prim = i                                                      # var for index
        while self.hash[i] is not None and self.hash[i][0] != key:
            num += 1                                                  # increment counter num
            s = num ** 2                                              # sum to double num
            s += prim                                                 # increment index sum
            s %= self.table_size                                      # remain
            i = s                                                     # set index to sum
        return self.hash[i] is not None and self.hash[i][0] == key

    def increase_table(self):                                         # increase if table size is reaching max
        hash = self.hash                                              # create new hash
        increasehash = HashTableQuadPr(self.table_size * 2 + 1)       # double hash size
        self.table_size = increasehash.table_size
        self.items_in_hash = 0                                        # reset items
        self.hash = []                                                # create new hash list
        for i in range(self.table_size):                              # items in range of hashed table
            self.hash.append(None)                                    # append to hash
        for items in hash:
            if items is None:                                         # continue if nothing is there
                continue
            elif items[1] is None:                                    # if nothing
                self.helper_to_insert(items[0], None)                 # insert into hash
            elif len(items[1]) > 1:                                   # for items in hash
                for x in items[1]:                                    # items in hash at respective x
                    self.helper_to_insert(items[0], x)                # insert and rehash accordingly
            else:
                self.helper_to_insert(items[0], items[1])             # insert items

#Run