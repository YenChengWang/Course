import warnings
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')

from collections import namedtuple
from gensim.models.doc2vec import Doc2Vec,LabeledSentence
import jieba
import gensim
import numpy as np
import math


#
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import TfidfTransformer  
from sklearn.feature_extraction.text import CountVectorizer

#Random Forest
from sklearn.ensemble import RandomForestClassifier

from scipy.sparse import hstack


def error_cal( sum_y, Y ):

    error_count = 0
    for i in range(0,len(sum_y)):
        if sum_y[i] != Y[i]:
            error_count = error_count + 1
            #print(type(sum_y[i])," ",type(Y[i]))
            
    #sum_y = np.array(sum_y)
    #Y = np.array(Y)
    #error_count = sum( sum_y != Y )
    
    return float(error_count/len(Y))   

       
#============================main fuunction============================
    
train_sentence = []
train_relation = []
train = []


#http://www.cnblogs.com/baiting/p/5874994.html
#===============(Parsing DATA)Data processing for "LabeledLineSentence"===============

#Sentence + aspect for relation determine
with open('train_2.dat', encoding='UTF-8') as pair:
    for line in pair:
        #print(line)
        line = line.strip("\n")
        data = line.split(",")

        #relation
        if data[-1] == 'Temporal':
            train_relation.append(1)
        elif data[-1] == 'Contingency':
            train_relation.append(2)
        elif data[-1] == 'Comparison':
            train_relation.append(3)
        elif data[-1] == 'Expansion':
            train_relation.append(4)
pair.close()
        
#for i in train_relation:
#    print(i)

print("Finish_Y")
#===============(Parsing)Data processing for "LabeledLineSentence"-(END)===============






#_____________________________________Training data_____________________________________
print("Start training data")
#sentences =LabeledSentence('train_doc2vec.dat')
#list for storing 2 sentence
List_1 = []
List_2 = []
corpus = []
corpus_ = []
corpus_2 = []

#==============================================================

#FINISH
#===========================TF-IDF===========================
'''
with open ('train_doc2vec.dat',encoding='utf8') as file:
    for line in file:
        corpus.append(line)
        
vectorizer = TfidfVectorizer(max_df=0.98, min_df=5,max_features=500)
tfidf = vectorizer.fit_transform(corpus)

with open ('train_doc2vec_2.dat',encoding='utf8') as file:
    for line in file:
        corpus_2.append(line)
        
vectorizer = TfidfVectorizer(max_df=0.98, min_df=5,max_features=500)
tfidf_2 = vectorizer.fit_transform(corpus_2)
'''

with open ('train_doc2vec.dat',encoding='utf8') as file:
    for line in file:
        corpus.append(line)

file.close()
with open ('train_doc2vec_2.dat',encoding='utf8') as file:
    for line in file:
        corpus_.append(line)
file.close()

#Train
vectorizer = TfidfVectorizer(max_df=0.95, min_df=3)

tfidf = vectorizer.fit(corpus)
tfidf2 = vectorizer.fit(corpus_)

x_1 = tfidf.transform(corpus)
x_2 = tfidf2.transform(corpus_)
#print(x_1.shape)
#print(x_2.shape)
x_1 = x_1.toarray()
x_2 = x_2.toarray()
x = np.concatenate((x_1,x_2), axis=1)


#===========================TF-IDF===========================
#FINISH



#________________________Random Forest________________________

for d in range(4,5,1):
    for n in range(1000,1001,100):
        depth =d
        num_of_tree = n
        weight_list = {1:5.73, 2:3.04, 3:5.3, 4:0.98}

        #weight_list = {1:5.68, 2:3, 3:5.27, 4:0.96695}
        #x = hstack( (tfidf, tfidf_2))
        y = np.array(train_relation)
        #print(x.shape)

        ##Target class: (1)Temporal (2)Contingency (3)Comparison (4)Expansion
        ##                   677           1292          741          3928



        #rf = RandomForestClassifier(n_estimators = num_of_tree, class_weight = 'balanced', oob_score=True, random_state=10, max_depth = depth)
        rf = RandomForestClassifier(n_estimators =num_of_tree, oob_score=True,
                                    random_state=8,
                                    class_weight = weight_list,
                                    #class_weight = 'balanced',
                                    min_samples_split = 100, max_depth = depth)    
        rf = rf.fit(x, y)
        #rf = rf.fit(x, y, sample_weight=weight_list)

        #Ein
        predict_y = rf.predict(x)
        print("____________________________________")
        print("Max Depth: ",depth)
        print("Num of tree: ", num_of_tree)
        print("Ein :", error_cal(predict_y, y))
        print("OOB_score: ",rf.oob_score_)
        print("____________________________________")

#for i in predict_y:
#    print(i)

#________________________Random Forest________________________



#test
#________________________Random Forest________________________      

#FINISH
#===========================TF-IDF===========================

corpus_test = []
corpus_test_2 = []
with open ('test_doc2vec.dat',encoding='utf8') as file:
    for line in file:
        corpus_test.append(line)
file.close()

with open ('test_doc2vec_2.dat',encoding='utf8') as file:
    for line in file:
        corpus_test_2.append(line)
file.close()


x_test_1 = tfidf.transform(corpus_test)
x_test_2 = tfidf2.transform(corpus_test_2)

#print(x_test_1.shape)
#print(x_test_2.shape)


x_test_1 = x_test_1.toarray()
x_test_2 = x_test_2.toarray()

test_x = np.concatenate((x_test_1,x_test_2), axis=1)
#print(test_x.shape)
#===========================TF-IDF===========================
#FINISH

file = open("2_RF_Ans.dat", "w", encoding = 'UTF-8')
file.write("Id,Relation\n")

predict_y_test = rf.predict(test_x)






#for i in predict_y_test:
#    print(type(i))

p = [0,0,0,0,0]
for i in predict_y_test:
    
    if i == 1:
        p[1] = p[1] + 1
    if i == 2:
        p[2] = p[2] + 1
    if i == 3:
        p[3] = p[3] + 1
    if i == 4:
        p[4] = p[4] + 1
                
#Output distribution
for i in range(len(p)):
    print(i," :",p[i])

for idx in range (0,len(predict_y_test)):
    if predict_y_test[idx] == 1:
        file.write( str(6639+idx)+",Temporal\n" )

    elif predict_y_test[idx] == 2:
        file.write( str(6639+idx)+",Contingency\n" )


    elif predict_y_test[idx] == 3:
        file.write( str(6639+idx)+",Comparison\n" )


    elif predict_y_test[idx] == 4:
        file.write( str(6639+idx)+",Expansion\n" )

    #file.write( str(predict_y_test[idx])+"\n" )
file.close()

        #________________________Random Forest________________________

print("Finish_Training")
    








