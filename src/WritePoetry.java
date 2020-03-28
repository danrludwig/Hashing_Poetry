import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WritePoetry {

    private String file;
    private String beginningWord;
    private int poemLength;
    private boolean printTable;
    private HashTable hashTable;

    public String WritePoem(String file, String beginningWord, int poemLength, boolean printTable) {
        this.file = file;
        this.beginningWord = beginningWord;
        this.poemLength = poemLength;
        this.printTable = printTable;
        this.hashTable = new HashTable();

        //Get all words from the text file
        ArrayList<String> words = getWords(file);
        // Get each distinct word
        ArrayList<String> eachWord = differentWords(words);

        // Run through the words, build a WordFreqInfo class with each word putting in the appropriate information
        // and add it to the hash table.
        for (int i=0; i<eachWord.size(); i++) {
            int count = 0;
            for (int j=0; j<words.size(); j++) {
                if (words.get(j).compareTo(eachWord.get(i)) == 0) {
                    count++;
                }
            }
            WordFreqInfo wordFreq = new WordFreqInfo(eachWord.get(i), count);
            for (int j=0; j<words.size() - 1; j++) {
                if (words.get(j).compareTo(eachWord.get(i)) == 0) {
                    if (j+1 < words.size()) {
                        wordFreq.updateFollows(words.get(j + 1));
                    } else {
                        wordFreq.updateFollows(words.get(0));
                    }
                }
            }
            this.hashTable.insert(wordFreq.word, wordFreq);
        }

        String[] sentenceWords = new String[poemLength];
        String myPoem = beginningWord;
        sentenceWords[1] = beginningWord;

        // Add words to myPoem.
        String currentWord = beginningWord;
        for (int i = 0; i < poemLength; i++)
        {
            String nextWord = getFollowingWord(currentWord);
            myPoem = myPoem.concat(" " + nextWord);
            currentWord = nextWord;
        }

        // If printTable is true add the table to the end of myPoem to be a part of the output.
        if (printTable) {
            myPoem += "\n";
            myPoem += hashTable.toString(hashTable.size());
        }

        return myPoem;
    }

    // returns the following word of the current word for the poem by randomly selecting out of the following words
    // on the original file
    private String getFollowingWord(String currentWord) {
        String nextWord = null;
        Random rd = new Random();

        if (this.hashTable.contains(currentWord))
        {
            WordFreqInfo wordFreq = (WordFreqInfo)this.hashTable.find(currentWord);
            ArrayList<String> wordsInAList = new ArrayList<>();
            for (int i = 0; i < wordFreq.followList.size(); i++)
            {
                for (int j = 0; j < wordFreq.followList.get(i).followCt; j++)
                {
                    wordsInAList.add(wordFreq.followList.get(i).follow);
                }
            }
            nextWord = wordsInAList.get(rd.nextInt(wordFreq.occurCt));
        }

        return nextWord;
    }


    // Finds hash value
    private int findHash( String x , HashTable hash) {
        int hashVal = x.hashCode( );

        hashVal %= hash.arraySize();
        if( hashVal < 0 )
            hashVal += hash.arraySize();

        return hashVal;
    }


    // finds position in hash table from the hash value and returns it
    private int findPos( String x, HashTable hash ) {
        int offset = 1;
        int currentPos = findHash(x, hash);

        currentPos += offset;  // Compute ith probe
        offset += 2;
        if( currentPos >= hash.arraySize() )
            currentPos -= hash.arraySize();

        return currentPos;
    }


    // Gets every distinct word from text file and puts it into a list
    public ArrayList<String> differentWords(ArrayList<String> list) {
        ArrayList<String> eachWord = new ArrayList<>();
        for (int word=0; word < list.size(); word++) {
            if (!eachWord.contains(list.get(word))) {
                eachWord.add(list.get(word));
            }
        }
        return eachWord;
    }

    // Gets every word from text file and puts it into a list
    public ArrayList<String> getWords(String file) {
        // Throw words into ArrayList
        ArrayList<String> dict = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(file))) {
            while (scanner.hasNext()) {
                dict.add(scanner.next());
            }
        } catch (Exception e) {
            System.out.printf("Caught Exception: %s%n", e.getMessage());
            e.printStackTrace();
        }
        // Goes through ArrayList and removes punctuation and sets all letters to lowercase
        for (int word=0; word < dict.size(); word++) {
            String newWord = dict.get(word);
            newWord = newWord.replaceAll("[^a-zA-Z ]", "");
            newWord = newWord.toLowerCase();
            dict.remove(word);
            dict.add(word, newWord);
        }
        return dict;

    }
}
