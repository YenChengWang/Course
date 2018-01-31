from sklearn.feature_extraction.text import TfidfVectorizer, TfidfTransformer, CountVectorizer
#RF
from sklearn.ensemble import RandomForestClassifier
import numpy as np
from sklearn.model_selection import train_test_split
#SVM
from sklearn import datasets, svm
from sklearn.svm import SVC
#GBDT
from sklearn.ensemble import GradientBoostingClassifier



def error_cal( predict_y, Y ):
    error_count = sum(predict_y != Y)
    print("Wrong",error_count)
    print(float(error_count/len(Y)))
    return float(error_count/len(Y))


#   Global variable
win = 30
FA_win = 1


#   $Stop word$
stopword_list = []
with open('CH_stopword.dat', encoding='utf-8') as file:
    for line in file:
        line = line.strip('\n')
        stopword_list.append(line)
file.close()



#   $gender dict$
gender_dict = {}
print("Gender dict building...")
with open('gender.dat',encoding='utf-8') as file:
    for line in file:
        line = line.strip('\n')
        data = line.split(' ')
        data[0] = data[0].replace('\t','').replace(' ','')
        if data[1] == "無":
            gender_dict[data[0]] = 0
        elif data[1] == "男":
            gender_dict[data[0]] = 1
        elif data[1] == "女":
            gender_dict[data[0]] = 2            
file.close()    
'''
print(len(gender_dict))
for i in gender_dict:
    print(i,"___",gender_dict[i])
'''
#   FA : Build name list for first appear chapter index
first_appear = {}
with open('gender.dat',encoding='utf-8') as file:
    for line in file:
        name = line.split(' ')[0].replace('\t','')
        first_appear[name] = 119
file.close()





#   $POS tag statistics$
POS_dict = {}
Po = []
idx = 0
all_article = ""
#Build POS tag dict
print("POS dict building ...")
with open('Dream_of_the_Red_Chamber_seg.txt',encoding='utf-8') as file:
    for line in file:
        #line = filter_regex.sub('', line)
        sent = line.split(' ')


        for word in sent:
            data = word.split('_')   #data[0] word, data[1] POS
            data[0] = data[0].strip(' ')
            data[1] = data[1].strip(' ')

            #Build POS dict
            if data[0] not in POS_dict:
                POS_dict[data[0]] = data[1]
            #Get Punctuation
            if data[1] == "Po": #or data[1]=="Ps" or data[1]=="Pe":
                #print(data)
                #print(data[1],data[0])
                if data[0] not in Po and data[0]:
                    #print(data[0])
                    Po.append(data[0])
            #Build article without POS
            if data[0] and data[1]!="Ps" and data[1]!="Pe":
                data[0] += " "
                all_article += data[0]
                
        #Cut by chapter       
        for i in range(0,win):
            all_article += '。'
        all_article += '_斷_'
file.close()
print("Finished preproc")

#   CA $Separate by sentence$
#Replace all puncs to '。'
for punc in Po:
    all_article = all_article.replace(punc,'。')
#all_article.replace('」','')  #all_article.replace('「','')
#Separate sentence by 。
article = all_article.split('。')

#   FA $Article processed by chapter$
article_chap = []   #   $FORMAT$   [[chap1_sent1,chap1_sent2...],[chap2_sent1,...]...]
art_ch = all_article.split('_斷_')
art_ch.pop()

for chap_text in art_ch:
    sent = chap_text.split('。')
    sent = [x.strip(' ') for x in sent if x]
    sent.pop()
    article_chap.append(sent)



#   FA $Find the first appear chapter and sentence$
print('First appear processing ...')
nickname_list = ['賈寶玉','王熙鳳','林黛玉','薛寶釵','史湘雲','賈探春','賈迎春','賈惜春','賈元春']
FA_sent ="" #for TFIDF
FA_sent_dict = {} #for feature
'''
def FA_sent_ID(article_chap, FA_sent):
    for chap_index, chapter in enumerate(article_chap):
        for sent in chapter:
            for person in first_appear.keys():
                if person in nickname_list:
                    if person[1:] in sent and first_appear[person] == 119:
                        first_appear[person] = chap_index
                        FA_sent_dict[person] = sent
                        FA_sent += sent
                        #print(chap_index, person, sent, first_appear[person])
                else:
                    if person in sent and first_appear[person] == 119:
                        first_appear[person] = chap_index
                        FA_sent_dict[person] = sent
                        FA_sent += sent
                        #print(chap_index, person, sent, first_appear[person])
                    elif len(person) > 2 and person[1:] != '夫人' and person != '夏婆子'and person != '五嫂子':    
                        if person[1:] in sent and first_appear[person] == 119:
                            first_appear[person] = chap_index
                            FA_sent_dict[person] = sent
                            FA_sent += sent
                            #print(chap_index, person, sent, first_appear[person])
'''

def FA_sent_ID(article_chap):
    for chap_index, chapter in enumerate(article_chap):
        for idx, sent in enumerate(chapter):
            for person in first_appear.keys():
                if person in nickname_list:
                    if person[1:] in sent and first_appear[person] == 119:
                        first_appear[person] = chap_index
                        #FA_sent_dict[person] = sent
                        #FA_sent += sent
                        win_sent = " ".join(chapter[idx-2:idx+3])
                        FA_sent_dict[person] = win_sent                  
                        #print(chap_index, person, sent, first_appear[person])
                        #print(FA_sent_dict[person])
                else:
                    if person in sent and first_appear[person] == 119:
                        first_appear[person] = chap_index
                        #FA_sent_dict[person] = sent
                        #FA_sent += sent
                        win_sent = " ".join(chapter[idx-2:idx+3])
                        FA_sent_dict[person] = win_sent                   
                        #print(chap_index, person, sent, first_appear[person])
                        #print(FA_sent_dict[person])                        
                    elif len(person) > 2 and person[1:] != '夫人' and person != '夏婆子'and person != '五嫂子':    
                        if person[1:] in sent and first_appear[person] == 119:
                            first_appear[person] = chap_index
                            #FA_sent_dict[person] = sent
                            #FA_sent += sent
                            win_sent = " ".join(chapter[idx-2:idx+3])
                            FA_sent_dict[person] = win_sent              
                            #print(chap_index, person, sent, first_appear[person])
                            #print(FA_sent_dict[person])
                        
#   FA $First appear processing$
FA_sent_ID(article_chap)
FA_sent = " ".join(FA_sent_dict.values())
FA_sent.strip(' ')
print(119 in first_appear.values())
'''
FA = sorted(first_appear.items(), key=lambda d: d[1])
for x,y in FA:
    print(x, y)
'''
print(len(FA_sent.split(' ')))

#   CA $Co-appear sentence processing$
nickname_list = ['賈寶玉','王熙鳳','林黛玉','薛寶釵','史湘雲','賈探春','賈迎春','賈惜春','賈元春']
last_name_list = {}
def Co_appearance(p1, p2,article):
    p1_in = False
    p2_in = False
    skip = 0    #sentence boundary
    text = ""
    #Start processing    
    for i in range(0, len(article)):
        if not skip:
            if i < len(article) - win:
                window = article[i:i+win]
                for sent in window:
                    #p1
                    if p1 in sent:
                        p1_in = True
                    #nickname checking
                    if p1 in nickname_list:
                        if p1[1:] in sent:
                            p1_in = True                        
                    #p2
                    if p2 in sent:
                        p2_in = True
                    #nickname checking
                    if p2 in nickname_list:
                        if p2[1:] in sent:
                            p2_in = True      

                if p1_in and p2_in:
                    #print(window)
                    wd = ""
                    for x in window:
                        wd += x
                    wd = wd.strip(' ')
                    text+=wd
                    text+=" "
                    #print(text)
                    skip=win
            else:
                window = article[i:]
                for sent in window:
                    #p1
                    if p1 in sent:
                        p1_in = True
                    #nickname checking
                    if p1 in nickname_list:
                        if p1[1:] in sent:
                            p1_in = True                        
                    #p2
                    if p2 in sent:
                        p2_in = True
                    #nickname checking
                    if p2 in nickname_list:
                        if p2[1:] in sent:
                            p2_in = True      

                if p1_in and p2_in:
                    #print(window)
                    for x in window:
                        wd += x
                    wd = wd.strip(' ')
                    text+=wd
                    text+=" "
                    #print(text)
                    skip=win
            #reset
            p1_in = False
            p2_in = False

        else:
            skip-=1

    ######
    text += FA_sent_dict[p1]
    text += FA_sent_dict[p2]
    return text
    

    

#   $vocab_list building$
print('key vocab building...')
vocab_dict = {}
index = 0
with open('keywords_W_30.dat',encoding='utf-8') as file:          #___________________________vocab dict___________________________
    for line in file:
        line = line.strip('\n')
        if line not in vocab_dict:
            vocab_dict[line] = index
            index+=1
file.close()

print(len(vocab_dict))
'''
for i in vocab_dict:
    print(i, vocab_dict[i])
'''
#_________________Training data processing_________________
print('Training data processing...')
#last_name distribution: { '賈': 79,'王': 20,'薛': 11,  '李': 6, '林': 6,'金': 6,'秦': 5,'邢': 4,'尤': 3,'史': 2}
last_name_index = {'賈': 0,'王': 1,'薛': 2,  '李': 3, '林': 4,'金': 5,'秦': 6,'邢': 7,'尤': 8,'史': 9}    #other: 10, place:11
relation_index = {'夫妻':1,'父女':2,'父子':3,'母女':4,'母子':5,'主僕':6,'兄弟姊妹':7,'遠親':8,'姑叔舅姨甥侄':9,'居處':10,'祖孫':11,'師徒':12}
index_relation = dict((k, v) for v, k in relation_index.items())



NOT_FOUND = 0
Text_list = []
relation_list = []
last_name_list = []

print('Entity co-appearance finding ...')
with open('train.txt',encoding='utf-8') as file:
    for line in file:
        line = line.strip('\n')
        data = line.split('\t')
        #Build info
        if data[3] != 'Relation':
            ID = data[0]
            p1 = data[1]
            p2 = data[2]
            relation = data[3]
            last_name = [0,0,0,0,0,0,0,0,0,0,0,0]
            gender = [0,0,0]  ####gender
            chap = [0]*120  ####chap
            # Y_Train
            relation_list.append(relation_index[relation])
            # X_Train_gender
            gender[gender_dict[p1]] = 1
            gender[gender_dict[p2]] = 1
            # X_Train_chap #####################
            chap[first_appear[p1]] = 1
            chap[first_appear[p2]] = 1

            # X_Train_last name
            try:
                p = last_name_index[p1[0]]
            except KeyError:
                p = 10
            last_name[p] = 1
            try:
                q = last_name_index[p2[0]]
            except KeyError:
                q = 10
            last_name[q] = 1
            #####
            try:
                if POS_dict[p1] == "Nc":
                    p = 11
                    last_name[p] = 1
            except KeyError:
                if POS_dict[p1[1:]] == "Nc":
                    p = 11
                    last_name[p] = 1
            try:
                if POS_dict[p2] == "Nc":
                    q = 11
                    last_name[q] = 1
            except KeyError:
                if POS_dict[p2[1:]] == "Nc":
                    q = 11
                    last_name[q] = 1
            #######
            last_name += gender
            last_name += chap
            last_name_list.append(last_name)
                
        else:
            continue
            
        print("_________________",p1,p2,relation,"_________________")
        text = Co_appearance(p1, p2, article)
        #print(text)
        if text:
            print("Co-appearance found.")
        else:
            print("NOT found!!!!")
            NOT_FOUND+=1
        # X_Train
        Text_list.append(text)
file.close()
print("NOT FOUND: ",NOT_FOUND)

print(relation_list)

#for name in last_name_list:
#    print(name)

#   $Count matrix building$
print('Vectorizer building...')
Count_vectorizer = CountVectorizer(ngram_range=(1, 2), vocabulary = vocab_dict, stop_words=stopword_list)
freq_matrix = Count_vectorizer.fit_transform(Text_list)
#Count_vectorizer = TfidfVectorizer(ngram_range=(1, 1), vocabulary = vocab_dict , sublinear_tf=True, stop_words=stopword_list)
#freq_matrix = Count_vectorizer.fit_transform(Text_list)         #___________________________Matrix___________________________

weight = freq_matrix.toarray()
print(weight.shape)
#print(weight)

relation_list = np.array(relation_list)
last_name_list = np.array(last_name_list)

print(last_name_list.shape)
print(relation_list.shape)

X_Train = np.concatenate((weight, last_name_list), axis=1)
Y_Train = relation_list



#_________________Training data processing_________________    
#   $Training$    
#Random Forest
print("Model training ...")

#Random Forest
print('Rnadom Forest')
clf = RandomForestClassifier(n_estimators = 10, #class_weight = "balanced",
                                    random_state = 8, max_depth = 7, n_jobs=4)        

'''
#SVC
clf = SVC(C=5, gamma='auto')
'''

'''
#GBDT
print("GBDT")
clf = GradientBoostingClassifier( n_estimators = 200, random_state=10, max_depth = 10, min_samples_leaf=2, learning_rate = 0.1)    

'''

#   $Training$
clf.fit(X_Train,Y_Train)
#Ein
predict_y = clf.predict(X_Train)
score = clf.score(X_Train,Y_Train)
Ein = error_cal(predict_y, Y_Train)

print(predict_y)
print("Score: ", score)
print("Ein:\t",Ein)


for ans in predict_y:
    print(index_relation[ans])
    
    
#_________________Testing data processing_________________
print('Testing data processing...')

NOT_FOUND = 0
Text_list_test = []
relation_list_test = []
last_name_list_test = []

print('Entity co-appearance finding ...')
with open('test.txt',encoding='utf-8') as file:
    for line in file:
        line = line.strip('\n')
        data = line.split('\t')
        #Build info
        if data[3] != 'Relation':
            ID = data[0]
            p1 = data[1]
            p2 = data[2]
            relation = data[3]
            last_name = [0,0,0,0,0,0,0,0,0,0,0,0]
            gender = [0,0,0]  ####gender
            chap = [0]*120 ####chap
            # Y_Train
            relation_list_test.append(relation_index[relation])
            # X_Train_gender
            gender[gender_dict[p1]] = 1
            gender[gender_dict[p2]] = 1
            # X_Train_chap #####################
            chap[first_appear[p1]] = 1
            chap[first_appear[p2]] = 1
            
            # X_Train_last name
            try:
                p = last_name_index[p1[0]]
            except KeyError:
                p = 10
            last_name[p] = 1
            try:
                q = last_name_index[p2[0]]
            except KeyError:
                q = 10
            last_name[q] = 1
            #####
            try:
                if POS_dict[p1] == "Nc":
                    p = 11
                    last_name[p] = 1
            except KeyError:
                if POS_dict[p1[1:]] == "Nc":
                    p = 11
                    last_name[p] = 1
            try:
                if POS_dict[p2] == "Nc":
                    q = 11
                    last_name[q] = 1
            except KeyError:
                if POS_dict[p2[1:]] == "Nc":
                    q = 11
                    last_name[q] = 1
            #####
            last_name += gender
            last_name += chap
            last_name_list_test.append(last_name)
                

        else:
            continue
            
        print("_________________",p1,p2,relation,"_________________")
        text = Co_appearance(p1, p2, article)
        #print(text)
        if text:
            print("Co-appearance found.")
        else:
            print("NOT found!!!!")
            NOT_FOUND+=1
        # X_Train
        Text_list_test.append(text)
file.close()
print("NOT FOUND: ",NOT_FOUND)
relation_list_test = np.array(relation_list_test)
last_name_list_test = np.array(last_name_list_test)


#   $Count matrix building$
print('Count vectorizer building...') #___________________________Matrix___________________________
Count_vectorizer_test = CountVectorizer(ngram_range=(1, 2), vocabulary = vocab_dict, stop_words=stopword_list)
#Count_vectorizer_test = TfidfVectorizer(ngram_range=(1, 1), vocabulary = vocab_dict, sublinear_tf=True, stop_words=stopword_list)
freq_matrix_test = Count_vectorizer_test.fit_transform(Text_list_test)    


weight_test = freq_matrix_test.toarray()
print(weight_test.shape)
#print(weight_test)
print(last_name_list_test.shape)
print(relation_list_test.shape)

X_Test = np.concatenate((weight_test, last_name_list_test), axis=1)
Y_Test = relation_list_test



#_________________Testing data processing_________________    
#   $Prediction$    
#Eout
predict_y_test = clf.predict(X_Test)
score2 = clf.score(X_Test, Y_Test)
Eout = error_cal(predict_y_test, Y_Test)

print(predict_y_test)
print("Score_out: ", score2)
print("Eout:\t",Eout)

for ans in predict_y_test:
    print(index_relation[ans])

#   $save model$
import pickle 
with open('RF_n_5_d_5_FA.model', 'wb') as file:
    pickle.dump(clf, file)
    
    
    
