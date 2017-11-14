#Tokenize
import nltk

#nltk for lemmatizing
from nltk.stem import WordNetLemmatizer
wordnet_lemmatizer = WordNetLemmatizer()

#pandas
import pandas as pd
import codecs
import csv

#remove punctuation
import re
filter_regex = re.compile(r'[^a-zA-Z0-9   ]')

#time calculating
import time

#TFIDF
from sklearn.feature_extraction.text import TfidfVectorizer, TfidfTransformer, CountVectorizer, ENGLISH_STOP_WORDS

#Numpy 
import numpy as np




#Stop word building
stop_words_list = []
with open('stop_words.dat', encoding='utf-8') as file:
#with open('stop_words.dat') as file:
    for stop_word in file:
        stop_word = stop_word.strip("\n").strip('  ').strip(' ')
        stop_words_list.append(stop_word)

        

#VAR.
Media_string = []
print("start DBpedia Article processing")
sentence = ""
index = 0
df = []


#Load tokenized article
with open('tok_DBpedia.dat','r',encoding='utf-8') as file:
    for line in file:
        line = line.strip('\n')
        if line:
            line = line.strip(' ').strip('\n')
            Media_string.append(line)
            index+=1

            if index%10000 == 0:
                print("Has finished ,",index,"lines")
                #break
        #elif line == "":
        #    print(line," None")
print("Total processing finished ,",index,"lines")
file.close()            



#Calculate total bigrams number
print("Whole Count matrix building ...")
Count_vectorizer = CountVectorizer(stop_words=stop_words_list, min_df=5, ngram_range=(3,3))
freq_matrix = Count_vectorizer.fit_transform(Media_string)
bigram_num = int(len( Count_vectorizer.vocabulary_))


print("Max Feature = ", min(int(bigram_num/10),5000)  ,"  Count matrix building ...")

Count_vectorizer = CountVectorizer(stop_words=stop_words_list, min_df=5, max_features=min(int(bigram_num/10),5000), ngram_range=(3,3))
freq_matrix = Count_vectorizer.fit_transform(Media_string)
vocab = Count_vectorizer.vocabulary_
final_list = sorted(vocab.items(), key=lambda d: d[1])

#Check the count
weight = freq_matrix.toarray()  #Weight [i] sentence, [j] word



count_list = np.sum (weight, axis=0)

print(len(count_list))
print(len(final_list))
print(count_list.shape)

#for i in count_list:
#    print(i)


vocab_count_dict = {}

for i in range(0, len(final_list)):
    #print(final_list[i],"\t",count_list[i])
    vocab_count_dict[final_list[i][0]] = count_list[i]

final_rank = sorted(vocab_count_dict.items(), key=lambda d: d[1], reverse=True)


#Write file
file = open('trigrams_DBpedia.dat','w',encoding='utf-8')
for bigram in final_rank:
    print(bigram[0],"\t",bigram[1]) 
    file.write(str(bigram[0]) + '\t' + str(bigram[1]) + '\n')
file.close()



