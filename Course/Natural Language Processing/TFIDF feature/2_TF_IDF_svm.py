import warnings
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')

from collections import namedtuple
from gensim.models.doc2vec import Doc2Vec,LabeledSentence
import jieba
import gensim
import numpy as np
import math
import random

from sklearn.model_selection import train_test_split

#
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import TfidfTransformer  
from sklearn.feature_extraction.text import CountVectorizer

#SVC
from sklearn.svm import SVC,LinearSVC


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
with open ('train.dat',encoding='utf8') as file:
    for line in file:
        corpus.append(line)

#Train
vectorizer = TfidfVectorizer(max_df=0.9, min_df=5)
tfidf = vectorizer.fit(corpus)
x =tfidf.transform(corpus)
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
vectorizer = TfidfVectorizer(max_df=0.95, min_df=5)

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



#________________________SVC________________________

c = 3


weight_list = {1:5.64, 2:3, 3:5.2, 4:0.96}


y = np.array(train_relation)
print(x.shape)



train_x = x[1001:]
train_y = y[1001:]
val_x   = x[:1001]
val_y   = y[:1001]

print(len(train_x))
weight_list = []

for cat in train_y:
    cat = int(cat)
    if cat == 1:
        weight_list.append(5.8)
    if cat == 2:
        weight_list.append(3)                  
    if cat == 3:
        weight_list.append(5.3)                   
    if cat == 4:
        weight_list.append(0.5)

print('start training')

X_train_bagging = []
Y_train_bagging = []

if True:

    if True:
        num_target =0    
            
        for j in range(0, len(train_x), 1):    #take 6638 samples each round
        
            ind = random.randint( 0, len(train_x)-1 )   #remember to -1
                    
            #too many +1 sample
            if (num_target > 1400):
                #print(1)
                out = True
                while(out):
                    if train_y[ind] != 4 :
                        out = False
                    else:
                        ind = random.randint( 0, len(train_x)-1 )
                X_train_bagging.append( train_x[ind] )
                Y_train_bagging.append( train_y[ind] )   

            else:
                #print(2)
                X_train_bagging.append( train_x[ind] )
                Y_train_bagging.append( train_y[ind] )   
            
            if train_y[ind]==4:
                num_target = num_target + 1


print('Start_fitting')
#clf = SVC(C=1, gamma=gamma, class_weight = 'balanced')
clf = LinearSVC(C=c, penalty='l2',multi_class='ovr', class_weight = 'balanced')


clf.fit(X_train_bagging, Y_train_bagging)

#clf.fit(train_x, train_y,sample_weight=weight_list)
print('Finish_fitting')

predict_y = clf.predict(train_x)
predict_y_val = clf.predict(val_x)


#for i in predict_y:
#    print(i)
#    print(type(i))

print("____________________________________")
print("Ein :", error_cal(predict_y, train_y))
print("Eval :", error_cal( predict_y_val, val_y ))
print("____________________________________")



#for i in predict_y:
#    print(i)



#test
    

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

#===========================TF-IDF===========================
#FINISH

file = open("Ans.dat", "w", encoding = 'UTF-8')
file.write("Id,Relation\n")


predict_y_test = clf.predict(test_x)





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

        #________________________Xgboost________________________

print("Finish_Training")
    









