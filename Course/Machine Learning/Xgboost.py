import numpy as np
from scipy.sparse import csr_matrix,hstack


#Xgboost
import xgboost as xgb

from sklearn.model_selection import train_test_split




def error_cal( sum_y, Y ):
    
    error_count = 0
    for i in range(0,len(sum_y)):
        if sum_y[i] != Y[i]:
            error_count = error_count + 1
            #print(type(sum_y[i])," ",type(Y[i]))
    #print(error_count)
    '''
            
    sum_y = np.array(sum_y)
    Y = np.array(Y)
    error_count = sum( sum_y != Y )
    '''
    return float(error_count/len(Y))    
    
#_________________________mian func_________________________

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

#Click list
with open('click.dat') as file:
    for line in file:
        data = line.strip('\n').split(' ')
        
        #Y_train[click] = data[0]
        Y_train.append(data[0])

        click = click + 1

        if click%100000 == 0:
            print('click: ',click)

        #if click == 500001:     #__________split__________
        #    break;


#Sparse Matrix
with open('all sparse(1).dat') as file:
    for line in file:
        data = line.strip('\n').split(' ')
        
        #if data[0] == '500001':     #__________split__________
        #    break;

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

#print( val_list.shape[0] ) #print( col_list[-1] ) #print( row_list[-1] )
print( len(Y_train))

val_list = np.array(val_list)
col_list = np.array(col_list)
row_list = np.array(row_list)
Y_train  = np.array(Y_train)

q = sum(Y_train==1) #num of 1 in train data


#X_train = csr_matrix((val_list, (row_list, col_list)), shape=(164859339,759))
X_train = csr_matrix((val_list, (row_list, col_list)), shape=(len(Y_train), 759)) #__________split__________
print("Finish Sparse transform!")

#add 1 col!!!!!
X_train = hstack((X_train, csr_matrix(np.ones((X_train.shape[0], 1)))))


#________________________xgboost________________________

#Data interface
#csr = scipy.sparse.csr_matrix((dat, (row, col)))



X_dtrain, X_deval, y_dtrain, y_deval = train_test_split(X_train, Y_train, random_state=1026, test_size=0.3)
dtrain = xgb.DMatrix(X_dtrain, y_dtrain)
deval  = xgb.DMatrix(X_deval, y_deval)
watchlist = [(deval, 'eval')]

params = {
    'booster': 'gbtree',
    'objective': 'binary:logistic',
    'subsample': 0.8,
    'colsample_bytree': 0.8,
    'eta': 0.1,
    'max_depth': 7,
    'seed': 2016,
    'silent': 0,
    'scale_pos_weight': 3,      #deal with unbalanced data
    'eval_metric': 'rmse'
    
}


clf = xgb.train(params, dtrain, 500, watchlist ,early_stopping_rounds=50)
print('Finish_fitting')
#Ein
predict_y = clf.predict(dtrain)
predict_y_eval = clf.predict(deval)

for i in range(0,len(predict_y)):
    if predict_y[i] > 0.3: #threshold
        predict_y[i] = 1
    else:
        predict_y[i] = 0


for i in range(0,len(predict_y_eval)):
    if predict_y_eval[i] > 0.3:
        predict_y_eval[i] = 1
    else:
        predict_y_eval[i] = 0

        
sss = sum(predict_y == 1) #/len(predict_y))
ppp = sum(predict_y_eval == 1) #/len(predict_y_eval))
print("____________________________________")
print( 'percent of 1 in train data: ',float(q/Y_train.shape[0]) )
print('Num of 1(train):', sss, ' all: ', len(predict_y), ' percent: ', sss/len(predict_y))
print('Num of 1(eval):',ppp,' all: ',len(predict_y_eval), ppp/len(predict_y_eval) )

print("Ein :", error_cal(predict_y, y_dtrain))
print("Eval :", error_cal(predict_y_eval, y_deval))
print("____________________________________")
#________________________xgboost________________________








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

        #if data[0] == '10001':     #__________split__________
        #    break;        
            

print('index: ',index, 'row: ',data[0])
print('Finish Test data processing')


val_list_test = [int(x) for x in val_list_test]
col_list_test = [int(x) for x in col_list_test]
row_list_test = [int(x) for x in row_list_test]

val_list_test = np.array(val_list_test)
col_list_test = np.array(col_list_test)
row_list_test = np.array(row_list_test)

X_test = csr_matrix((val_list_test, (row_list_test, col_list_test)), shape=(val_list_test.shape[0], 759))

#add 1 col!!!!!
X_test = hstack((X_test, csr_matrix(np.ones((X_test.shape[0], 1)))))

print("Finish Test Sparse transform!")



#________________________xgboost________________________

#Data interface
#csr = scipy.sparse.csr_matrix((dat, (row, col)))

X_dtest = xgb.DMatrix(X_test)
predict_y_test = clf.predict(X_dtest)


for i in range(0,len(predict_y_test)):
    if predict_y_test[i] > 0.25:
        predict_y_test[i] = 1
    else:
        predict_y_test[i] = 0



h = sum(predict_y_test == 1)
print(float(h/predict_y_test.shape[0]))
print("Finish testing")


file = open('Track_2_Submission.dat','w')
for i in predict_y_test:
    file.write(str(int(i)) + '\n')


file.close()


