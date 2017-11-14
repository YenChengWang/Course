#
import warnings
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')

#   $Word2Vec$
import gensim
from gensim.models import word2vec

#Load Model
model = word2vec.Word2Vec.load("W2V_DBpedia_Brown_wiki_36m.bin")

query_list = ["film directed","1990","1944","war", "cuisine","chicago", "basketball","solar power","war ii","Capital"]


for word in query_list:
    try:
        #print( word, end="\t")
        word = word.strip('\n').lower()   
        print('#', word )
        sim_list = model.most_similar(positive=word, topn=10)
        for pair in sim_list:
            #print("%s_%s," %(pair[0],pair[1]),end='')
            print(pair[0],':\t\t',pair[1])
            #print()
        print('__________________________________________________________')

    except KeyError:
        print(word, "___________________________not in model___________________________")
      
