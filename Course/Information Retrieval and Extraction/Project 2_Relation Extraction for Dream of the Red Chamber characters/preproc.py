import re
filter_regex = re.compile(r'[^a-zA-Z0-9 -_。，；？！]')
''' #、 ：'''
from sklearn.feature_extraction.text import TfidfVectorizer, TfidfTransformer, CountVectorizer

#   Global variable
win = 30
FA_win = 2



#   $Stop word$
stopword_list = []
with open('CH_stopword.dat', encoding='utf-8') as file:
    for line in file:
        line = line.strip('\n')
        stopword_list.append(line)
file.close()



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
all_article = ""    #all article
#Build POS tag dict
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
        # FA : Cut by chapter       
        for i in range(0,win):
            all_article += '。'
        all_article += '_斷_'
file.close()
print("Finished preproc")

'''
#Get punctuation
#print(type(Po),len(Po))
Po.remove('\\')
Po.remove('@')
Po.remove('：')
Po.remove('、')
Po.remove('．')
print(Po) #All ['。', '：', '，', '？', '！', '、', '；', '…', '．','\\','@']
'''


#   CA $Separate by sentence$
#Replace all puncs to '。'
for punc in Po:
    all_article = all_article.replace(punc,'。')
#all_article.replace('」','') #all_article.replace('「','')
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

'''
#test
for idx, text in enumerate(article_chap):
    print("___________________Chapter ",idx+1,"___________________")
    for x in text:
        print("~~~~~~~~~~~~~~~",x,"~~~~~~~~~~~~~~~")
'''    



#   FA $Find the first appear chapter and sentence$
print('First appear processing ...')
nickname_list = ['賈寶玉','王熙鳳','林黛玉','薛寶釵','史湘雲','賈探春','賈迎春','賈惜春','賈元春']
FA_sent ="" #for TFIDF
FA_sent_dict = {} #for feature

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
                        print(chap_index, person, sent, first_appear[person])
                        print(FA_sent_dict[person])
                else:
                    if person in sent and first_appear[person] == 119:
                        first_appear[person] = chap_index
                        #FA_sent_dict[person] = sent
                        #FA_sent += sent
                        win_sent = " ".join(chapter[idx-2:idx+3])
                        FA_sent_dict[person] = win_sent
                        print(chap_index, person, sent, first_appear[person])
                        print(FA_sent_dict[person])                        
                    elif len(person) > 2 and person[1:] != '夫人' and person != '夏婆子'and person != '五嫂子':    
                        if person[1:] in sent and first_appear[person] == 119:
                            first_appear[person] = chap_index
                            #FA_sent_dict[person] = sent
                            #FA_sent += sent
                            win_sent = " ".join(chapter[idx-2:idx+3])
                            FA_sent_dict[person] = win_sent
                            print(chap_index, person, sent, first_appear[person])
                            print(FA_sent_dict[person])
                        
#   FA $First appear processing$
FA_sent_ID(article_chap)
FA_sent = " ".join(FA_sent_dict.values())
FA_sent.strip(' ')
print(119 in first_appear.values())

FA = sorted(first_appear.items(), key=lambda d: d[1])
for x,y in FA:
    print(x, y)

print(len(FA_sent.split(' ')))



#   CA $Co-appear sentence processing$
last_name_list = {}
def Co_appearance(p1, p2,article):
    p1_in = False
    p2_in = False
    skip = 0    #sentence boundary
    text = ""

    #last name statistic
    if p1[0] not in last_name_list:
        last_name_list[p1[0]] = 1
    else:
        last_name_list[p1[0]] +=1
    if p2[0] not in last_name_list:
        last_name_list[p2[0]] = 1
    else:
        last_name_list[p2[0]] +=1
        

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
    return text


# Training data processing
relation_dict = {}
NOT_FOUND = 0
#text = Co_appearance("寶玉","寶釵",article)
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
        else:
            continue

        print("_________________",p1,p2,relation,"_________________")
        text = Co_appearance(p1, p2, article)
        #print(text)
        if text:
            if relation not in relation_dict:
                relation_dict[relation] = text
            else:
                relation_dict[relation] += text
            print("Co-appearance found.")
        else:
            print("NOT found!!!!")
            NOT_FOUND+=1
file.close()
print("NOT FOUND: ",NOT_FOUND)
#last name distribution
#print(last_name_list)
'''
{ '賈': 79,'王': 20,'薛': 11,  '李': 6, '林': 6,'金': 6,'秦': 5,'邢': 4,'尤': 3,'史': 2}
'''


#Build relation text
relation_list = []
relation_text = []
relation_norm = []
#Build text list
print(relation_dict.keys())    #if no co-appearance-->遠親(?)
for relation in relation_dict:
    relation_list.append(relation)
    relation_text.append(relation_dict[relation])

#   FA
relation_list.append('FA')
relation_text.append(FA_sent)
relation_dict['FA'] = FA_sent
    
#Build norm
ave_length = len(''.join(relation_text))/len(relation_text)
for relation in relation_list:
    norm = len(relation_dict[relation])/ave_length / 2
    relation_norm.append(norm)

for norm in relation_norm:
    print(norm)

#   $Count matrix building$
#Count_vectorizer = CountVectorizer(ngram_range=(1, 1))
#freq_matrix = Count_vectorizer.fit_transform(relation_text)
Count_vectorizer = TfidfVectorizer(ngram_range=(1, 2)) #, stop_words=stopword_list)
freq_matrix = Count_vectorizer.fit_transform(relation_text)
weight = freq_matrix.toarray()
vocab = Count_vectorizer.vocabulary_    #key:word, value: index
vocab = sorted(vocab.items(), key=lambda d: d[1])
vocab = [x[0] for x in vocab]

print(weight.shape)
#print(weight)
print(len(vocab))
#print(ave_length)
#print(relation_list)
#print(relation_norm)

#   $Keyword finding$
keyword_list = set([])
for i in range(0, weight.shape[0]): #go through all relation
    print("Relation:",relation_list[i])
    word_dict = {}
    for j in range(0, weight.shape[1]):
        if weight[i][j]/(relation_norm[i] + 0.001) * 100 > 1:   #threshold
            word_dict[vocab[j]] = round(weight[i][j]/relation_norm[i],3)*100
            #if weight[i][j]>5:
            #    print(vocab[j],weight[i][j])
            #print(vocab[j], weight[i][j])
        
    keyword = sorted(word_dict.items(), key=lambda d: d[1],reverse=True)        
    #keyword = sorted(word_dict.items(), key=lamba d: d[1])
    length = len(keyword)

    #Output
    for i in range(0, length):
        #no filter
        print(keyword[i])

        #if keyword[i] not in keyword_list:
        keyword_list.add(keyword[i])
        
        '''
        #POS filter
        try:
            if(POS_dict[keyword[i][0]] != "Nb"): #"Nc" 居處
                print(keyword[i])
        except KeyError:
            continue
        '''
        
        if i>101:
            break
'''
for word in FA_sent.split(' '):
    if word not in keyword_list:
        print(word)
        keyword_list.append(word)
'''

#for i in range(int(0.05*length),int(0.2*length)):
#    print(keyword[i])

file = open('keywords_FA_W_30_bi.dat','w',encoding='utf-8')
for word in keyword_list:
    file.write(word[0] + '\n')
file.close()
















