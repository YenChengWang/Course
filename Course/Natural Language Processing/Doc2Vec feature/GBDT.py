import warnings
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')

from collections import namedtuple
from gensim.models.doc2vec import Doc2Vec,LabeledSentence
import jieba
import gensim
import numpy as np
import math
import random
from sklearn import cross_validation

#GBDT
from sklearn.ensemble import GradientBoostingClassifier


def readData(path):
    x = []      #stored for string
    y = []      #stored for float
    x_dim = 2*120                          #____________Dimemsion____________
    x_feature = [] 
    X = []      #stored for float

    #Read the data.dat   
    with open(path) as file:
        for line in file:
            line.replace("  "," ")
            Data = line.strip().split(' ')            #Data[0]:digit, Data[1]:inten + sym

            #print('' in Data)
            #print(Data)
            #print(line)
            #print(Data[0])
            #x feature
            x_feature = Data[1:]
            #print(x_feature)
            
            x.append(x_feature[0:])
            #print(x)
            #print(type(Data[0]))
            y.append(Data[0])

    for i in range(len(x)):
        X.append([ float(x[i][q]) for q in range(0,x_dim)])
     
    file.close()
    return X,y,x_dim


def read_testData(path):
    x = []      #stored for string
    x_dim = 2*120                          #____________Dimemsion____________
    x_feature = [] 
    X = []      #stored for float

    #Read the data.dat   
    with open(path) as file:
        for line in file:
            #line.replace('  ',' ')
            Data = line.strip().split(' ')            #Data[0]:digit, Data[1]:inten + sym


            #x feature
            x_feature = Data[0:]
            
            x.append(x_feature[0:])
            #print(x)
            #print(type(Data[0]))
    for i in range(len(x)):
        X.append([ float(x[i][q]) for q in range(0,x_dim)])
     
    file.close()
    return X,x_dim




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
    

x_d =2*120                                #____________Dimension____________


#====================training start====================
print("X_dimension: ",x_d)

class_list = [0,677,1292,741,3982]
#target = 1                              #____________target class____________
#x,y,x_dim = readData('relation_vec_'+str(int(x_d/2))+'.dat', target)

##Target class: (1)Temporal (2)Contingency (3)Comparison (4)Expansion
##                   677           1292          741          3928

#1:2600/7; 






for depth in range(3,4):  #depth 11 the best
#for i in range(1):

    more = True
    #while(more):
    for estm in range(200,201):
        x,y,x_dim = readData('relation_vec_'+str(int(x_d/2))+'.dat')#Data processing





        #split data for val
        x_val = np.array(x[:1000])
        y_val = np.array(y[:1000])

        #split data for training
        x = np.array(x[1000:])
        y = np.array(y[1000:])

        #dim of train and val
        #print(x.shape[0], ' ', x_val.shape[0])
        
        weight_list = []

        #weight : best - x_dim:80; 2.5/1.8/2.3/0.8; depth:11
        #weight : best - x_dim:80; 2.45/1.75/2.3/0.8; depth:14
        for cat in y:
            cat = int(cat)
            if cat == 1:
                weight_list.append(5.8)
            if cat == 2:
                weight_list.append(3.1)                  
            if cat == 3:
                weight_list.append(5.3)                   
            if cat == 4:
                weight_list.append(0.9)


        #________________________Random Forest________________________
            
        #GBDT = GradientBoostingClassifier(n_estimators = 100, class_weight = 'balanced', oob_score=True, random_state=10, max_depth = 10)
        GBDT =GradientBoostingClassifier(n_estimators = estm, random_state=10, max_depth = depth, learning_rate = 0.05)    
        #GBDT = rf.fit(x, y)
        GBDT = GBDT.fit(x, y, sample_weight=weight_list)

        #Ein
        predict_y = GBDT.predict(x)
        predict_y_val = GBDT.predict(x_val)
        print("____________________________________")
        print("Max Depth: ",depth)
        print("Estamor: ", estm)
        print("Ein :", error_cal(predict_y, y))
        print("Eval:", error_cal(predict_y_val, y_val))
        
        #________________________Random Forest________________________
            

        #test
        #________________________Random Forest________________________      

        file = open("output.dat", "w", encoding = 'UTF-8')
        file.write("Id,Relation\n")
        test_x, x_dim = read_testData('test_relation_vec_'+str(int(x_d/2))+'.dat')#Data processing
        predict_y_test = GBDT.predict(test_x)

        p = [0,0,0,0,0]
        for i in predict_y_test:
            if i == '1':
                p[1] = p[1] + 1
            if i == '2':
                p[2] = p[2] + 1
            if i == '3':
                 p[3] = p[3] + 1
            if i == '4':
                p[4] = p[4] + 1
                
        #Output distribution
        for i in range(len(p)):
            print(i," :",p[i])

        for idx in range (0,len(predict_y_test)):
            if predict_y_test[idx] == '1':
                file.write( str(6639+idx)+",Temporal\n" )

            elif predict_y_test[idx] == '2':
                file.write( str(6639+idx)+",Contingency\n" )


            elif predict_y_test[idx] == '3':
                file.write( str(6639+idx)+",Comparison\n" )


            elif predict_y_test[idx] == '4':
                file.write( str(6639+idx)+",Expansion\n" )

            #file.write( str(predict_y_test[idx])+"\n" )
        file.close()

        more = False
        #________________________Random Forest________________________

print("Finish_Training")
    
 
