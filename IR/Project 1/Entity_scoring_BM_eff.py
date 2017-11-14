#   $Format of data$
import sys
import codecs
import re
import json
import pandas as pd
import csv
#remove punctuation
filter_regex = re.compile(r'[^a-zA-Z0-9-_ ]')

#   $Calculation tools$
import scipy
import numpy as np
from collections import Counter
import math

#   $TFIDF$
from sklearn.feature_extraction.text import TfidfVectorizer, TfidfTransformer, CountVectorizer, ENGLISH_STOP_WORDS


#nltk for lemmatizing / tokenizing
import nltk
from nltk.stem import WordNetLemmatizer
wordnet_lemmatizer = WordNetLemmatizer()


#
import warnings
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')





'''
(1) contain digits / no contain digit
(2) Pure count / count + TFIDF
(3) Bigrams / no bigrams


(4) Choose better one plus POS

'''


#__________________________Function__________________________  
#   $Bigrams$
def bigram_building(path):
    ngrams_dict = {}
    with open(path,'r',encoding='utf-8') as file:
        for line in file:
            data = line.strip('\n').strip(' ').split('\t')
            word = data[0]
            COUNT= data[1]
            ngrams_dict[word] = COUNT
    #ngrams_list = sorted(ngrams_dict.items(), key=lambda d: d[1], reverse=True)
    bigram_list = ngrams_dict.keys()
    file.close()
    return bigram_list


#   $Bigrams replacement$
def ngram_proc(tok_list, bigram_list ):
    for bigram in bigram_list :
        wd1 = bigram.split(' ')[0]
        wd2 = bigram.split(' ')[1]
    
        for sent in tok_list:
            try:
                idx_lst = [idx for (idx, word) in enumerate(sent) if word == wd1]
                for idx in idx_lst:                
                    if sent[idx+1] == wd2:
                        sent.pop(idx)
                        sent.pop(idx)
                        wd = wd1+' '+wd2
                        sent.insert(idx, wd)
            except (ValueError, IndexError):
                pass
    return None        

#   $Stop words$
def stop_word_building(path):
    #Stop word building
    stop_words_list = []
    with open(path, encoding='utf-8') as file:
    #with open('stop_words.dat',encoding='utf-8') as file:
        for stop_word in file:
            stop_word = stop_word.strip("\n").strip('  ').strip(' ')
            stop_words_list.append(stop_word)
    return stop_words_list

#   $Query processing and building$
def query_proc(path, stop_words_list):
    #query_ID_list = []
    query_list    = []
    

    with open(path, encoding="utf-8") as file:
        for line in file:
            data = line.strip('\n').split("\t")
            ID = data[0]
            data[1] = data[1].replace('-',' ').replace('_',' ')
            queries = filter_regex.sub(' ', data[1]).split(' ')
            
            QUERIES = []
            
            #Start building
            for query in queries:
                #(1) remove ' '
                query = query.replace('\t','').replace(' ','')
                #(2) $lower + lemma
                query = (wordnet_lemmatizer.lemmatize(query.lower()))   # $$lower first and then lemmatize$$
                #(3) check "not None" and not stop_word and "not digit"
                if query and (query not in stop_words_list) and (not query.isdigit()):  ############### isdigit
                    QUERIES.append(query)
                       
            #Build ID & query list
            #query_ID_list.append(ID)
            query_list.append( (ID,QUERIES) )    
            
    file.close()    
    return query_list

#__________________________Function__________________________      
    
    
#__________________________Main function__________________________  

#General Processing Building
print("Bigrams building ...")
bigram_list     = bigram_building('bigrams_DBpedia.dat')

print("Stop words building ...")
stop_words_list = stop_word_building('stop_words.dat')

#Query building
print("Query building ...")
query_list = query_proc('queries-v2.txt', stop_words_list)

print("Bigram processing")
ngram_proc(query_list, bigram_list)

#Entity building
print("Entity building ...")

Text_list   = []
Entity_list = []
Entity_Text = []
idx = 0

with open('DBdoc.json','r') as file:
    datas = json.load(file)
    for line in datas:
        TEXT = ""
        text = line['abstract']
        entity = line['entity']

        if text != None:
            text = filter_regex.sub(' ', text).lower()  #Punctuation + to loeer case
            text = text.strip(' ')
            text = text.split(' ')
        
            for word in text:
                word = (wordnet_lemmatizer.lemmatize(word)).strip(' ')
                if word: #check not 
                    TEXT = TEXT + " " +word
        
            TEXT = TEXT.replace('-',' ').replace('_',' ') 
            Text_list.append(TEXT)
            Entity_list.append(entity)
            #Entity_Text.append( (entity,TEXT) )
            idx +=1
        
        
        if idx%1000 == 0:
            print("Has finished ",idx," entities.")
            #break
file.close()



#   $Start information retrieval$
#   $Word2Vec$
import gensim
from gensim.models import word2vec

#Load model
model = word2vec.Word2Vec.load("W2V_DBpedia_Brown_wiki_36m.bin")

print("Sim. word building ...")
query_term_sim = []     #list [[ dict1, dict2 ... ]q1, [dict1, dict2...]q2 ] for word1, word2, word3 in query...
for index in range(0, len(query_list)):
    query_term = query_list[index][1]
    query_ID   = query_list[index][0]
    entity_score = {}

    
    #Build similarity word for each word in query term
    #print('Sim word for query word processing')
    temp_list = []
    for word in query_term:
        try:
            sim_list = model.most_similar(positive=word, topn=5)
            temp_dict = {}
            temp_dict[word] = 2 
            for pair in sim_list:
                temp_dict[pair[0]] = pair[1]
            temp_list.append(temp_dict)           
            
        except KeyError:
            print(word, "Not in dict!")
            continue
    query_term_sim.append(temp_list)
    

#   $Length normalization$
total_length   = 0
LEN_list= []
#sum all length
for ENTITY in Text_list:
    length = len(ENTITY.split(' '))
    total_length = total_length + length
    LEN_list.append(length)
#average length
ave_article_len = float( total_length / len(Text_list) )
norm = [LEN / ave_article_len for LEN in LEN_list]

    
#Start the work of scoring            
print('Start scoring ...')


# $Scoring$ 
# %1st: Generate TFIDF value(no bigrams)%
print('1st TFIDF/Count calculation(no bigrams)')
vectorizer = TfidfVectorizer( stop_words=stop_words_list, min_df=5, norm='l2')
tfidf_matrix = vectorizer.fit_transform(Text_list)

vocab = vectorizer.vocabulary_           #get all the words in dict form, key:word; value:index
vocab = sorted(vocab.items(), key=lambda d: d[1])   #id, words

#Build dict for 2nd TFIDF
vocab_dict = {}
#unigram
for wd, idx in vocab:
    vocab_dict[wd] = idx
#bigrams
vocab_id = len(vocab)

for bigrams in bigram_list:
        vocab_dict[bigrams] = vocab_id
        vocab_id+=1
                                        
#%2nd: Generate TFIDF value and count matrix(combine bigrams)%
print('2nd TFIDF calculation(contains bigrams)')
vectorizer = TfidfVectorizer( stop_words=stop_words_list, min_df=5, sublinear_tf=True, vocabulary = vocab_dict, ngram_range=(1,2))
tfidf_matrix = vectorizer.fit_transform(Text_list)

Count_vectorizer = CountVectorizer(stop_words=stop_words_list, min_df=5, vocabulary = vocab_dict, ngram_range=(1,2))
freq_matrix = Count_vectorizer.fit_transform(Text_list)

vocab = vectorizer.vocabulary_           #get all the words in dict form, key:word; value:index
vocab = sorted(vocab.items(), key=lambda d: d[1])
#word list index by row_idx
word = [ x[0] for x in vocab ]


#TFIDF MATRIX
tfidf_matrix = scipy.sparse.coo_matrix(tfidf_matrix)
#record the index
tfidf_row_idx_list = tfidf_matrix.row
tfidf_col_idx_list = tfidf_matrix.col
tfidf_val_idx_list = tfidf_matrix.data

#Count MATRIX
freq_matrix  = scipy.sparse.coo_matrix(freq_matrix)
#record the index
row_idx_list = freq_matrix.row
col_idx_list = freq_matrix.col
val_idx_list = freq_matrix.data

#Add last item for tfidf_row_idx_list[idx + 1]
tfidf_row_idx_list = np.append(tfidf_row_idx_list,-1)


#Write file
file = codecs.open('result_3.dat','w','utf-8')

#Var.
author_N = max(row_idx_list) + 1    #N


#   $Scoring$
#Go through all queries
for index in range(0, len(query_term_sim)): #list [ "[ dict1, dict2 ... ]q1", "[dict1, dict2...]q2", ... ] for word1, word2, word3 in query...
    #Query info
    query_sim  = query_term_sim[index]  #[]
    query_ID   = query_list[index][0]
    query_term = query_list[index][1]
    
    #Var for calculating Rank for this query
    entity_rank = {}    #{entity1:score1, entity2:score2, ...}
    proc_idx = 0        #processing logging
    score = 0           #score for each doc according to a query
    

    print("_____Query ",query_term," processing ..._____")
    
    #Stored all query term and its similar word into a list
    Query_and_sim = []
    Sim = {}
    Sim = Counter(Sim) 
    query_ni = {}      #{query1:ni1, query2:ni2 ...}

    #Build sim list
    for sim_dict in query_sim:  #dict{query1:sim1, query2:sim2 ...}
        Query_and_sim += sim_dict.keys()
        Sim += Counter(sim_dict)
        
    #Calculate ni for each word & simword
    for query in Query_and_sim:
        try:
            col_idx = word.index(query)
            ni = list(col_idx_list).count(col_idx)
            query_ni[query] = ni
            #print(query," ",ni)
            
        except ValueError:
            query_ni[query] = 0
            continue

    #print(query_ni)
    
    #Go through all entities through format of sparse matrix
    #Start key word finding
    for idx in range (0, len(tfidf_row_idx_list)):    #For each user
        try:
            #Same entity for next word
            if tfidf_row_idx_list[idx] == tfidf_row_idx_list[idx + 1]:
                #TFIDF
                tfidf_row_idx = tfidf_row_idx_list[idx]
                tfidf_col_idx = tfidf_col_idx_list[idx]
                tfidf         = tfidf_val_idx_list[idx]

                #Freq.
                row_idx = row_idx_list[idx]
                col_idx = col_idx_list[idx]
                freq    = val_idx_list[idx]

                    
                wd = word[tfidf_col_idx]    #non zero count for this word in doc_i  
                #   scoring_function : 2*freq*sim / ( norm + freq*sim ) * log ( (N-ni+0.5)/(ni+0.5) )
                #   query_ni = {}      #{query1:ni1, query2:ni2 ...}
                #   $Scoring$
                #check if this word in Query_and_sim and calculate score
                if  wd in Query_and_sim:
                    score = score + (2*freq*Sim[wd])/(norm[row_idx] + freq * Sim[wd]) * math.log(  (author_N - query_ni[query] + 0.5)/(query_ni[query] + 0.5), 2 )
                    
                    
            #Last word for this user
            #NOTICE: Last word of last author-->index out of bounds
            elif tfidf_row_idx_list[idx] != tfidf_row_idx_list[idx + 1]:
                #TFIDF
                tfidf_row_idx = tfidf_row_idx_list[idx]
                tfidf_col_idx = tfidf_col_idx_list[idx]
                tfidf         = tfidf_val_idx_list[idx]

                #Freq.
                row_idx = row_idx_list[idx]
                col_idx = col_idx_list[idx]
                freq    = val_idx_list[idx]
                
               
                
                wd = word[tfidf_col_idx]
                #Update the last word for rank dict      
                #   $Scoring$
                #check if this word in Query_and_sim
                if wd in Query_and_sim:
                    score = score + (2*freq*Sim[wd])/(norm[row_idx] + freq * Sim[wd]) * math.log(  (author_N - query_ni[query] + 0.5)/(query_ni[query] + 0.5), 2 )
    
                
                
                #output, update and reset param     #print(proc_idx, Entity_list[row_idx],"\t",score)
                entity_rank[Entity_list[row_idx]] = score
                score=0
                proc_idx += 1

                if proc_idx % 10000 == 0:
                    print("Have finished: ",proc_idx," entities.")

    
        except IndexError:
            print("Finish checking all entity!")
    
    #   $Start ranking$                           
    #d[1] sort words by wegiht(count)
    final_list = sorted(entity_rank.items(), key=lambda d: d[1], reverse=True)  #category final list
                            
        
    #Output user info.
    print("=============",query_ID,"\t",query_term,"=============")

    rank = 1
    #Output result and write file
    #Format: INEX_LD-2009022	Q0	<dbpedia:Chen_Kenmin>	1
    while(rank <= 100):
        try:
            print(rank,".\t",final_list[rank])
            file.write(query_ID +"\t"+ "Q0\t<dbpedia:" + final_list[rank][0] + ">\t" + str(rank) + "\t" +str(final_list[rank][1]) + "\tSTANDARD" + "\n")
            rank += 1
        except IndexError:
            #print("Index Error")
            rank = rank + 1
        
        #file.write('\n')
    
file.close()

