import numpy as np
from scipy.sparse import csr_matrix

#GBDT
from sklearn.ensemble import GradientBoostingClassifier

#Random Forest
from sklearn.ensemble import RandomForestClassifier


def error_cal( sum_y, Y ):
    '''
    error_count = 0
    for i in range(0,len(sum_y)):
        if sum_y[i] != Y[i]:
            error_count = error_count + 1
            #print(type(sum_y[i])," ",type(Y[i]))

    '''
            
    sum_y = np.array(sum_y)
    Y = np.array(Y)
    error_count = sum( sum_y != Y )
    
    return float(error_count/len(Y))    


#Sparse example
#row = np.array([0, 0, 1, 2, 2, 2])
#col = np.array([0, 2, 2, 0, 1, 2])
#data = np.array([1, 2, 3, 4, 5, 6])
#b = csr_matrix((data, (row, col)), shape=(3, 3)).toarray()
#print(b)



#row_list = np.zeros(164859339)
#col_list = np.zeros(164859339)
#val_list = np.zeros(164859339)
row_list = []
col_list = []
val_list = []
#Y_train = np.zeros(10282909)
Y_train = []

click = 0
index = 0


with open('click.dat') as file:
    for line in file:
        data = line.strip('\n').split(' ')
        
        #Y_train[click] = data[0]
        Y_train.append(data[0])

        click = click + 1

        if click%100000 == 0:
            print('click: ',click)

        if click == 500001: #_________split_________
            break;


with open('all sparse(1).dat') as file:
    for line in file:
        data = line.strip('\n').split(' ')
        
        if data[0] == '500001':#_________split_________
            break;

        row_list.append(data[0])
        col_list.append(data[1])
        val_list.append(data[2])
        #row_list[index] = data[0]
        #col_list[index] = data[1]
        #val_list[index] = data[2]

        index = index + 1

        if index%100000 == 0:
            print('index: ',index, 'row: ',data[0])



print('index: ',index, 'row: ',data[0])
print('Finish')


val_list = [int(x) for x in val_list]
col_list = [int(x) for x in col_list]
row_list = [int(x) for x in row_list]
Y_train  = [int(x) for x in Y_train]

#print( val_list.shape[0] )
#print( col_list[-1] )
#print( row_list[-1] )
print( len(Y_train))

val_list = np.array(val_list)
col_list = np.array(col_list)
row_list = np.array(row_list)
Y_train  = np.array(Y_train)


#X_train = csr_matrix((val_list, (row_list, col_list)), shape=(164859339,759))
X_train = csr_matrix((val_list, (row_list, col_list)), shape=(len(Y_train), 759))#_________split_________
print("Finish Sparse transform!")

#________________________Random Forest________________________

for depth in range(7,8,1):
    for estm in range(300,301):
        rf = RandomForestClassifier(n_estimators = estm, class_weight = 'balanced',random_state = 10,oob_score=True, max_depth = depth, min_samples_leaf= int(0.002*len(Y_train)))
        #rf = RandomForestClassifier(n_estimators = estm,random_state = 10,oob_score=True, max_depth = depth, min_samples_leaf= 50)
        rf = rf.fit(X_train, Y_train)


        #Ein
        predict_y = rf.predict(X_train)

        sss = sum(predict_y == 1)
        print("____________________________________")
        print('Num of 1(train):', sss, ' all: ', len(predict_y), ' percent: ', sss/len(predict_y))
        print('num of tree: ',estm,' depth: ',depth)
        print("Ein :", error_cal(predict_y, Y_train))
        print("Eoob: ",rf.oob_score_)
        print("____________________________________")
#________________________Random Forest________________________


'''
GBDT =GradientBoostingClassifier(n_estimators = 100, random_state=10, max_depth =5, learning_rate = 0.01)    
#GBDT = GBDT.fit(X_train, Y_train, sample_weight=weight_list)
GBDT = GBDT.fit(X_train.toarray(), Y_train)

#Ein
predict_y = GBDT.predict(X_train)
#predict_y_val = GBDT.predict(X_val)
print("____________________________________")

print("Ein :", error_cal(predict_y, y))
print("Eval:", error_cal(predict_y_val, y_val))

'''
#________________________Test________________________
row_list_test = []
col_list_test = []
val_list_test = []


#Sparse Matrix
with open('all sparse_test(1).dat') as file:
    for line in file:
        data = line.strip('\n').split(' ')
        

        row_list_test.append(data[0])
        col_list_test.append(data[1])
        val_list_test.append(data[2])

        index = index + 1

        if index%100000 == 0:
            print('index: ',index, 'row: ',data[0])

        if data[0] == '10001':     #__________split__________
            break;        
            

print('index: ',index, 'row: ',data[0])
print('Finish Test data processing')


val_list_test = [int(x) for x in val_list_test]
col_list_test = [int(x) for x in col_list_test]
row_list_test = [int(x) for x in row_list_test]

val_list_test = np.array(val_list_test)
col_list_test = np.array(col_list_test)
row_list_test = np.array(row_list_test)

X_test = csr_matrix((val_list_test, (row_list_test, col_list_test)), shape=(val_list_test.shape[0], 759))

print("Finish Test Sparse transform!")

#________________________xgboost________________________

#Data interface
#csr = scipy.sparse.csr_matrix((dat, (row, col)))


predict_y_test = rf.predict(X_test)


for i in range(0,len(predict_y_test)):
    if predict_y_test[i] > 0.25:
        predict_y_test[i] = 1
    else:
        predict_y_test[i] = 0



h = sum(predict_y_test == 1)
print('Percent of 1:',float(h/predict_y_test.shape[0]))
print("Finish testing")


file = open('Track_2_Submission(RF).dat','w')
for i in predict_y_test:
    file.write(str(int(i)) + '\n')


file.close()
