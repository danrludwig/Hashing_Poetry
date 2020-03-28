A program written to write it's own poetry based on a certain txt file. The program reads in one txt file at a time and writes its own poetry pattered after the txt file. The program is given a txt file to mimic, a beginning word, a length of the poem its creating, and whether you want the hash table to be displayed.

This program works by taking the txt file, uploading all the words into a hashtable also storing what words comes after that word and how often. When the program runs, it takes the given word and randomly selects which word to come next based on which words come after it in the txt file (the next word is calculated by taking the probability into account based on word frequency). The program stops once it's hit the desired size.




Example run:

Input (pre-programmed into main):
  Text file: "green.txt", start word: "sam", length: 20, print hash table: true

Output: 
  sam i do you like that sam sam i do not like them here or there i am that sam sam
  3: them
  4: i
  6: sam
  11: anywhere
  12: green
  20: or
  40: ham
  46: there
  53: you
  63: like
  70: and
  79: eggs
  80: do
  86: am
  87: not
  88: that
  89: would
  92: here

