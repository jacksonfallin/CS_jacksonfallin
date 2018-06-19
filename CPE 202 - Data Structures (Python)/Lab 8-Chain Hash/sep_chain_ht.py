'''Step 1: Data Definition - Need to be able to create a hash table via seperate chaining, remove and insert accordingly
   Step 2: Signature - (list->list) input key-item pair into hash table and output list
   Step 3: Test Cases - self.assertEqual(ht.size(), 6)
                        self.assertEqual(ht.load_factor(), 0.36363636363636365)
                        self.assertEqual(ht.collisions(), 3)
                        self.assertEqual(ht.remove(3), [3, 'blue'])
                        self.assertEqual(ht.get(3), (3, 'green'))'''

#Body-
class MyHashTable:
    def __init__(self, table_size=11):                                                #sets up table size, hash table list,and counters
        self.capacity = table_size
        self.items_in_hash = 0
        self.num_of_collisions = 0
        self.ht = [[] for x in range(table_size)]

    def hash_function(self, key):                                                     #creates hash function
        return key % self.capacity

    def insert(self, key, item):                                                      #Insert string with key into hash table, chain insert
        if self.load_factor(1) >= 1.5:                                                #test load factor
            self.helper_increase()

        item_pair = [key, item]                                                       #make list of item pairs within hash                                                                                             # Traverse the list through to test if any of the values have the same key
        for x in self.ht[self.hash_function(key)]:
            i = 0
            if x[0] == key:                                                           #replace values of same key
                self.ht[self.hash_function(key)][i] = item_pair
                self.num_of_collisions += 1
                return
            i += 1                                                                    #increment index
        self.ht[self.hash_function(key)].append(item_pair)  # append to list
        self.items_in_hash += 1

    def helper_increase(self):                                                        #helper to make list bigger when load factor is too big
        self.capacity = (2 * self.capacity + 1)
        self.num_of_collisions = 0
        new_hash = [[] for x in range(self.capacity)]                                 #rehash
        for x in self.ht:                                                             #hash for new hash list
            for h in x:
                if len(new_hash[self.hash_function(h[0])]) != 0:
                    self.num_of_collisions += 1
                    new_hash[self.hash_function(h[0])].append(h)                      #append to new hash
        self.ht = new_hash                                                            #create new hashed list

    def get(self, key, boolean=False):                                                #get object with key
        for x in self.ht[self.hash_function(key)]:                                    #loop through hash
            if key == x[0]:
                if boolean is True:
                    return x, self.hash_function(key)                                 #return hash function and key i
                else:
                    return key, x[1]
        raise LookupError("Error: Value not found.")

    def remove(self, key):                                                            #Remove the object associated with key from the hashtable
        try:
            get_key = self.get(key, True)                                             #see if it exists
        except LookupError:
            raise LookupError("Error: Value not found.")
        self.ht[get_key[1]].remove(get_key[0])                                        #remove
        self.items_in_hash -= 1                                                       #deincrement the num_of items in hash
        return get_key[0]                                                             #return

    def load_factor(self, positional_arg = 0):                                        #calculates load factor
        if self.items_in_hash != 0:                                                   #check if load factor is not empty
            return float(float(self.items_in_hash + self.num_of_collisions) / float(self.capacity))          #calculation for load factor is number of items in hash/capacity
        else:
            return float(0)

    def size(self):                                                                   #return number of items in hash
        return self.items_in_hash

    def collisions(self):                                                             #return number of collisions that occur
        return self.num_of_collisions

#Run-