
'''Step 1: Data Definition - Need string 'abc' and a returned list of permuated string
   Step 2: Signiture - (str -> list) input of a stinrg and outputs a list
   Step 3: Test Cases - self.assertEqual(perm_gen_lex('abc'), ['abc', 'acb', 'bac', 'bca', 'cab', 'cba'])
                        self.assertEqual(perm_gen_lex('a'), ['a'])
                        self.assertEqual(perm_gen_lex(''), [''])
   Step 4: Template
   -If the string contains a single character return a list containing that string
   -Loop through all character positions of the string containing the characters to be
    permuted, for each character:
   -Form a simpler string by removing the character
   -Generate all permutations of the simpler string recursively
   -Add the removed character to the front of each permutation of the simpler
    word, and add the resulting permutation to a list
   -Return all these newly constructed permutations'''

#Step 5: Body -

def perm_gen_lex(string):
    if len(string) == 0:                                              #checks to see if length of string = 0
        return ['']
    elif len(string) == 1:                                            #checks to see if only one character in the stirng
        return [string]
    else:
        return_list = []                                              #blank list where permutations are to be stored
        for i in range(len(string)):                                  #checks the length of string
            for p in perm_gen_lex(string[:i]+string[i+1:]):           #generates permutations
                return_list.append(string[i] + p)
        return return_list                                            #returns permutated list





'''Step 6: Run'''















