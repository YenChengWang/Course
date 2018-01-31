import warnings
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')

from collections import namedtuple
from gensim.models.doc2vec import Doc2Vec,LabeledSentence
import jieba
import gensim
import numpy as np

from svmutil import *
import math
import random

def readData(path, target_class):
    x = []      #stored for string
    y = []      #stored for float
    x_dim = 160                          # dimemsion
    x_feature = [] 
    X = []      #stored for float

    #Read the data.dat   
    with open(path) as file:
        for line in file:
            Data = line.strip().split(' ')            #Data[0]:digit, Data[1]:inten + sym

            #print(Data[0])
            #x feature
            x_feature = Data[1:]
            #print(x_feature)
            
            x.append(x_feature[0:])
            #print(x)
            #print(type(Data[0]))
            
            #y feature
            if (float(Data[0]))==target_class:
                y.append(1)                             #taget class: 1, others:-1
            else:
                y.append(-1)

    for i in range(len(x)):
        X.append([ float(x[i][q]) for q in range(0,x_dim)])
     
    file.close()
    return X,y,x_dim

def error_cal( sum_y, Y ):

    error_count = sum( sum_y!= Y )
    
    return error_count/len(Y)    

#============================main fuunction============================
    
train_sentence = []
train_relation = []
train = []
x_d =160                          # dimemsion

#====================training start====================
print(x_d)

class_list = [0,677,1292,741,3982]
#target = 1  #target class
#x,y,x_dim = readData('relation_vec'+str(int(x_d/2))+'.dat', target)

#Target class: (1)Temporal (2)Contingency (3)Comparison (4)Expansion
#                   677           1292          741          3928
#Dimension 50
#1:89.8011%, 2:80.5363%, 3:88.837%, 4:59.1745%(!)

for label in range(1,5,1):
#for c in range(1):

    print("Target (",label,") starts training")
    more = True
    while(more):
        TBAG = 100                      #____________TBAG____________
        target = label                  #____________target class____________
        x,y,x_dim = readData('relation_vec'+str(int(x_d/2))+'.dat', target)    

        sum_train = np.zeros(len(y))
        for i in range(0,TBAG,1):                 #200 rounds
            #print("TBAG: ",i+1)
            X_train_bagging = []
            Y_train_bagging = []

            num_target = 0
            num_untarget = 0
                
            for j in range(0, len(x), 1):    #take 6638 samples each round
            
                ind = random.randint( 0, len(x)-1 )   #remember to -1
                
                #too many -1 sample
                if (num_untarget > 3320):
                    #print(1)
                    out = True
                    while(out):
                        if y[ind] == 1:
                            out = False
                        else:
                            ind = random.randint( 0, len(x)-1 )
                    X_train_bagging.append( x[ind] )
                    Y_train_bagging.append( y[ind] )    
                        
                #too many +1 sample
                elif (num_target > 3320):
                    #print(2)
                    out = True
                    while(out):
                        if y[ind] == -1:
                            out = False
                        else:
                            ind = random.randint( 0, len(x)-1 )
                    X_train_bagging.append( x[ind] )
                    Y_train_bagging.append( y[ind] )   

                else:
                    #print(3)
                    X_train_bagging.append( x[ind] )
                    Y_train_bagging.append( y[ind] )   
                

                if y[ind]==1:
                    num_target = num_target + 1
                else:
                    num_untarget = num_untarget + 1

            #print("======",num_target," ",num_untarget,"======")
                    
            #________________________SVM________________________             
            #Build problem
            problem = svm_problem(Y_train_bagging, X_train_bagging)

            #Build parameter
            #print ('c',c)
            c=1                                         #________c value________
            
            c_value = '-c {0}'.format(math.pow(10,c))   #{0} = 10^ -5,-3,-1,1,3
            parameter = c_value +' -t 0 '
            params = svm_parameter(parameter)

            #Build model
            model = svm_train(problem, params)
            model_name = 'model'+str(c)
            svm_save_model(model_name,model)                
            
            #predict        
            train_model = svm_load_model(model_name)
            p_label, p_acc, p_val = svm_predict(y, x, train_model)
            #________________________SVM________________________
            #if error_cal(p_label, y)  > 0.5:
                #p_label = -1*p_label
                #print("inverse")
            
            #print("P_label content 1:",1 in p_label," -1:",-1 in p_label)
            sum_train = sum_train + p_label


            if i == 4 or i==9 or i==39 or i==69 or i ==89:
                sum_train_temp = np.sign(sum_train)   
                Ein  = error_cal(sum_train_temp,y)
                print("======================")
                print("TBAG = ",i+1)
                print(" Ein : ",Ein)
                print("======================")

        sum_train_sign = np.sign(sum_train)   
        Ein  = error_cal(sum_train_sign, y) 

        #Calculate the # of 1/-1
        w = sum(sum_train_sign ==  1)
        x = sum(sum_train_sign == -1)


        if w>=500:
            #________________________Write binary classification to file________________________
            file = open("SVM_Binary.dat", "w", encoding = 'UTF-8')

            for x in sum_train:
                file.write(str(x)+' ')
            file.write('\n')
            file.close()
            more = False
            #________________________Write binary classification to file________________________

            #output the result
            print("TBAG = ",TBAG)
            print(" Ein(G) : ",Ein)
            print("1: ",w," -1: ",x)
            print("Target (",label,") finished training")
            print("______________________________")
        else:
            print("1: ",w," -1: ",x)
            print("Target ",target, " one more round!")            

        
