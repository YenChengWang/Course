import warnings
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')

from collections import namedtuple
from gensim.models.doc2vec import Doc2Vec,LabeledSentence
import jieba
import gensim
import numpy as np

from svmutil import *
import math
        
#============================main fuunction============================
    
train_sentence = []
train_relation = []
train = []
x_d =200                 # dimemsion

#namedtuple template
#TPoint = namedtuple('TPoint', ['x', 'y'])
#t = [11,22]
#p = TPoint._make(t)
#p
#x=11, y=22


#http://www.cnblogs.com/baiting/p/5874994.html
#===============Data processing for "LabeledLineSentence"===============
x = 0   #count
file = open("train_doc2vec.dat", "w", encoding = 'UTF-8')
file_ = open("train_doc2vec_2.dat", "w", encoding = 'UTF-8')


#Sentence + aspect for relation determine
with open('train_2.dat', encoding='UTF-8') as pair:
    for line in pair:
        x = x + 1
        #print(line)
        line = line.strip("\n")
        data = line.split(",")
        train.append(line)
        #Stored by list
        train_sentence.append(data[1]+str(" ")+data[2])  #sentence
        #print(data[1],"  ",data[2])
        #data[1]:first sentence / data[2]:second sentence

        #relation
        if data[-1] == 'Temporal':
            train_relation.append(1)
        elif data[-1] == 'Contingency':
            train_relation.append(2)
        elif data[-1] == 'Comparison':
            train_relation.append(3)
        elif data[-1] == 'Expansion':
            train_relation.append(4)
            
        #train_relation.append(data[-1])
        #print(data[1]+data[2])
        
        
        #parsing the Chinese words
        #words = jieba.cut(data[1]+data[2], cut_all=False)

        words_1 = jieba.cut(data[1], cut_all=False)
        words_2 = jieba.cut(data[2], cut_all=False)
        
        
        for word in words_1:
            #print(word)
            file.write(word + " ")

        file.write("\n")

        for word in words_2:
            #print(word)
            file_.write(word + " ")
        
        file_.write("\n")
        #file_.write(str(data[-1]) + "\n")
        #print(x)

pair.close()
file.close()
file_.close()
print("Finish_parsing")

#====================Doc2vec training start====================
#sentences =LabeledSentence('train_doc2vec.dat')
#list for storing 2 sentence
List_1 = []
List_2 = []
#==============================================================
#first sentence
out = open('relation_vec_'+str(x_d)+'_1.dat', 'w')
#docs = g.doc2vec.TaggedLineDocument(train_corpus)

'''class gensim.models.doc2vec.Doc2Vec(documents=None, dm_mean=None, dm=1,
                                    dbow_words=0, dm_concat=0, dm_tag_count=1,
                                    docvecs=None, docvecs_mapfile=None, comment=None
                                    , trim_rule=None, **kwargs)'''

sentences = gensim.models.doc2vec.TaggedLineDocument('train_doc2vec.dat')
#################
#docs = gensim.models.doc2vec.Doc2Vec('train_doc2vec.dat',size=100, min_count=1)


model = Doc2Vec(sentences, size = x_d, window = 5, min_count=1)
model.save('doc2vec_model.txt')
#print(len(model.docvecs))

for idx, docvec in enumerate(model.docvecs):
    out.write(str(train_relation[idx]) + ' ')
    for value in docvec:
        out.write(str(value) + ' ')
    ##### 1   
    List_1.append([round(float(value),8) for value in docvec])    

    out.write('\n')
    #print(idx)
    #print(docvec)
out.close()

#second sentence
out_2 = open('relation_vec_'+str(x_d)+'_2.dat', 'w')
sentences = gensim.models.doc2vec.TaggedLineDocument('train_doc2vec_2.dat')

model_2 = Doc2Vec(sentences, size = x_d, window = 5, min_count=1)
model_2.save('doc2vec_model_2.txt')


for idx, docvec in enumerate(model_2.docvecs):
    #out.write(str(train_relation[idx]) + ' ')
    for value in docvec:
        out_2.write(str(value) + ' ')
    ##### 2   
    List_2.append([round(float(value),8) for value in docvec])    

    out_2.write('\n')
out_2.close()
#==============================================================

#print(len(List_1), '  ',len(List_2))

file = open('relation_vec'+str(x_d)+'.dat', 'w')


for i in range (0,len(List_1)):
    file.write(str(train_relation[i]) + ' ')

    file.write(str(List_1[i]).strip('[]').replace(',',''))
    file.write(' ')
    file.write(str(List_2[i]).strip('[]').replace(',',''))
    file.write('\n')

file.close()

print("Finish_Doc2vec training")


