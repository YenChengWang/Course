import re
import json
filter_regex = re.compile(r'[^a-zA-Z0-9 .,!?-_ ]')  #_________$Hyphen dealing$ ("-_")_________

#nltk for lemmatizing / tokenization
import nltk
from nltk.stem import WordNetLemmatizer
wordnet_lemmatizer = WordNetLemmatizer()

#
import codecs


text_list   = []
entity_list = []

idx = 1
with open('DBdoc.json','r') as file:
    datas = json.load(file)
    for line in datas:

        text = line['abstract']
        entity = line['entity']

        if text != None:
            text = filter_regex.sub(' ', text).lower()  #Punctuation
            text = text.strip(' ')
        
        #print(entity)
        #print(text)
        #print("--------------------")
        text_list.append(text)
        entity_list.append(entity)
        idx +=1
file.close()
print("Total ",idx," lines")



idx = 1
Total_text = ""
for text in text_list:
    #if text != None:
    try:
        line = text.split(' ')
        paragraph = ""
        #Start lemma.
        for word in line:
            #(1)remove ' ' '\t' '\n'
            word = word.replace('\t','').replace(' ','').strip('  ').strip(' ')
            #(2)lemma + to lower
            word = (wordnet_lemmatizer.lemmatize(word)).strip(' ')
            #combine all word
            if word:
                paragraph = paragraph + ' ' + word

        #concatenate
        if paragraph:            #Not empty 
            Total_text =  Total_text + " " + paragraph


        #Logging and reset

        if(idx%1000 == 0):
            print("Has finished ,",idx,"lines")
            #break
        idx = idx + 1
    except AttributeError:
        print(text," ",type(text))
        continue

#Separate by sentence
Total_text = Total_text.replace(',','.').replace('!','.').replace('?','.')
Total_text = Total_text.replace('-',' ').replace('_',' ')   #_________$Hyphen dealing$_________
Text = Total_text.split('.')




DBpeida_list = []

#Tokenize->Dpedia
for sent in Text:
    #ignore empty list  
    try:
        #tokenize
        sent =   nltk.tokenize.word_tokenize(sent)
        #_________$Make up lemmatization$_________
        original = sent[-1]
        sent[-1] = (wordnet_lemmatizer.lemmatize(sent[-1]))
        if original != sent[-1]:
            print(original," ",sent[-1])    
        #sent = [ word for word in sent if not (str(word).isdigit() or len(str(word))<2)]
        sent = [ word for word in sent if len(str(word))>2]    
        #Append
        DBpeida_list.append( sent )        
    except IndexError:
        print(sent,"~~~")
        continue


#___________________Saved to file___________________
file = codecs.open('tok_DBpedia_2.dat','w','utf-8')

for sentence in DBpeida_list:
   for words in sentence:
      #words = words.replace(',','')
      #At least 2 char and not number
      if (len(words) > 1): #and ( not words.isdigit()):
         file.write(str(words)+" ")
   file.write("\n")
file.close()
#___________________Saved to file___________________


