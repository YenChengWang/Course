#
import warnings
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')

#   $Word2Vec$
import gensim
from gensim.models import word2vec

#   $Tokenize$
import nltk


#   $codecs$
import codecs


#2-grams dealing
ngrams_dict = {}
with open('bigrams_DBpedia.dat','r',encoding='utf-8') as file:
    for line in file:
        data = line.strip('\n').strip(' ').split('\t')
        word = data[0]
        COUNT= data[1]
        ngrams_dict[word] = COUNT

#ngrams_list = sorted(ngrams_dict.items(), key=lambda d: d[1], reverse=True)
gramwd_list = ngrams_dict.keys()
file.close()

#--------------------------Functions--------------------------
#deal with 2-grams
def ngram_proc(article):
    ind = 0
    for grams in gramwd_list:

        grams = grams.split(' ')
        wd1 = grams[0]
        wd2 = grams[1]
        #print(wd1,' ',wd2)    
        #substitue
        for sent in Global_text:
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
        ind+=1
        print("Finish#",ind,"\t",wd1+' '+wd2)
        
    return article 
#--------------------------Functions--------------------------

    
    
#__________________________Main function__________________________  


#Global var.
Global_text = []
index = 0


#Stop word building
stop_words_list = []
with open('stop_words.dat',encoding='utf-8') as file:
    for stop_word in file:
        stop_word = stop_word.strip("\n").strip('  ').strip(' ')
        stop_words_list.append(stop_word)


#_________Training corpus processing_________
print('Start corpus processing')
with open('tok_DBpedia_Brown_wiki_36m.dat') as file:    #For finsih processing file!
    for line in file:
        line = line.strip('\n').strip(' ').replace('\\','').replace('/','')
        line = nltk.tokenize.word_tokenize(line)
        
        #remove stop words
        data = []
        for word in line:
            if word not in stop_words_list:
                data.append(word)
                
        #Buildind text list by sentence
        Global_text.append(data)

        index = index + 1

        #Logging / early stop
        if index%10000==0:
            print('Have finished ',index,' lines.')
            #break

#bigrams word processing     
print('ngram processing ....')
Global_text = ngram_proc(Global_text)           
            

#_________Training W2V model_________        

#Training word2vec model
print('Start word2vec training ...')        
model = gensim.models.Word2Vec(Global_text, min_count=3, window = 5)
model.save("W2V_DBpedia_Brown_wiki_36m.bin")
print('Finished word2vec training ...')  
