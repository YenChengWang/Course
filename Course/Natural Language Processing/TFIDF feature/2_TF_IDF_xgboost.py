import warnings
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')

from collections import namedtuple
from gensim.models.doc2vec import Doc2Vec,LabeledSentence
import jieba
import gensim
import numpy as np
import math


from sklearn.model_selection import train_test_split

#
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import TfidfTransformer  
from sklearn.feature_extraction.text import CountVectorizer

#Xgboost
import xgboost as xgb
from xgboost.sklearn import XGBClassifier


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



#________________________Xgboost________________________
depth = 20
num_of_tree = 500
weight_list = []


y = np.array(train_relation)
print(x.shape)




X_dtrain, X_deval, y_dtrain, y_deval = train_test_split(x, y, random_state=1026, test_size=0.3)
dtrain = xgb.DMatrix(X_dtrain, y_dtrain)
deval  = xgb.DMatrix(X_deval, y_deval)
watchlist = [(deval, 'eval')]

params = {
    'booster': 'gblinear',
    #'objective': 'reg:linear',
    'objective':'multi:softmax;',
    #'num_class':4,
    'subsample': 0.8,
    'colsample_bytree': 0.85,
    'eta': 0.005,
    'max_depth': 7,
    'seed': 2016,
    'silent': 0,
    'scale_pos_weight': 3,
    'eval_metric': 'rmse'
    
}


clf = xgb.train(params, dtrain, 500, watchlist ,early_stopping_rounds=50)
#clf = xgb.train(params, dtrain, 500, watchlist)

'''
clf = XGBClassifier(
    #'objective': 'reg:linear',
    objective = 'binary:logitic',
    #'objective':'multi:softmax;','num_class':4,
    subsample = 0.8,
    colsample_bytree = 0.85,
    eta = 0.005,
    max_depth = 7,
    seed = 2016,
    silent = 0,
    scale_pos_weight = 1
    )

modelfit(clf, dtrain())
'''
print('Finish_fitting')



#Ein
predict_y = clf.predict(dtrain)
predict_y_test = clf.predict(deval)

for i in range(0,len(predict_y)):
    if predict_y[i] > 0 and predict_y[i] <=1.5:
        predict_y[i] = 1
    elif predict_y[i] > 1.5 and predict_y[i] <=2.5:
        predict_y[i] = 2
    elif predict_y[i] > 2.5 and predict_y[i] <=3.5:
        predict_y[i] = 3
    elif predict_y[i] > 3.5:
        predict_y[i] = 4
    else:
        predict_y[i] = 0


for i in range(0,len(predict_y_test)):
    if predict_y_test[i] > 0 and predict_y_test[i] <=1.9:
        predict_y_test[i] = 1
    elif predict_y_test[i] > 1.9 and predict_y_test[i] <=2.7:
        predict_y_test[i] = 2
    elif predict_y_test[i] > 2.7 and predict_y_test[i] <=3.5:
        predict_y_test[i] = 3
    elif predict_y_test[i] > 3.5:
        predict_y_test[i] = 4
    else:
        predict_y[i] = 0


#for i in predict_y:
#    print(i)
#    print(type(i))

print("____________________________________")
sss = sum(predict_y == 1)/len(predict_y)
ppp = sum(predict_y_test == 1)/len(predict_y_test)
print('Num of 1:', sss, ' all: ', len(predict_y), ' ',ppp,' all: ',len(predict_y_test) )
print("Ein :", error_cal(predict_y, y_dtrain))
print("Eval :", error_cal(predict_y_test, y_deval))
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

dtest = xgb.DMatrix(test_x)

predict_y_test = clf.predict(dtest)


for i in range(0,len(predict_y_test)):
    if predict_y_test[i] > 0 and predict_y_test[i] <=1.9:
        predict_y_test[i] = 1
    elif predict_y_test[i] > 1.9 and predict_y_test[i] <=2.7:
        predict_y_test[i] = 2
    elif predict_y_test[i] > 2.7 and predict_y_test[i] <=3.5:
        predict_y_test[i] = 3
    elif predict_y_test[i] > 3.5:
        predict_y_test[i] = 4
    else:
        predict_y[i] = 0


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
    









