#Tokenize
import nltk

#nltk for lemmatizing
from nltk.stem import WordNetLemmatizer
wordnet_lemmatizer = WordNetLemmatizer()

#Crawler
import requests
from bs4 import BeautifulSoup


#remove punctuation
import re
filter_regex = re.compile(r'[^a-zA-Z0-9 .,!? ]')  # $Hyphen dealing$ ("-_")

#pandas
import pandas as pd
import codecs

#word2vec
#import gensim
#from gensim.models import word2vec



#Crawling function
def wiki_crawling(name, address):
    content=requests.get(address)
    soup=BeautifulSoup(content.text,'html.parser')
    wiki_text = []
    eles=[]
    for row in soup.find_all('p'):
        eles.append(row.text)

    article = (' '.join([ele if ele else "" for ele in eles])).lower()
    #Punctuation and lowercase
    article = filter_regex.sub('', article).lower()
   
    #Separate by sentence
    article = article.replace(',','.').replace('!','.').replace('?','.')
    article = article.replace('-',' ').replace('_',' ')   #_________$Hyphen dealing$_________
    Text = article.split('.')   

    
    #Tokenize->Wiki text
    for sent in Text:
        sent = nltk.tokenize.word_tokenize(sent)

      
        #sent = [ wordnet_lemmatizer.lemmatize(word) for word in sent if not (str(word).isdigit() or len(str(word))<2)]
        sent = [ wordnet_lemmatizer.lemmatize(word) for word in sent if len(str(word))>2 and not word.isdigit()]
        #print(sent)
        if len(sent) >= 2:
            wiki_text.append( sent )
    print(name,"finished")
   
    return wiki_text



wiki_pair = []

with open("country.dat", encoding="utf-8") as file:
    for line in file:
        country = line.strip('\n').strip(' ')
        country = country.replace(' ','_')
        pair = (country, 'https://en.wikipedia.org/wiki/' + country)
        wiki_pair.append(pair)
        
file.close()

wiki_text = []

for idx, pair in enumerate(wiki_pair):
    print("===",idx,"===")
    wiki_text = wiki_text + wiki_crawling(pair[0], pair[1])

print("Finished Wiki")



#___________________Saved to file___________________
file = codecs.open('tok_wiki.dat','w','utf-8')

for sentence in wiki_text:
   for words in sentence:
      words = words.replace(',','')
      #At least 2 char and not number
      if (len(words) > 1) and ( not words.isdigit()):
         file.write(str(words)+" ")
   file.write("\n")
file.close()
#___________________Saved to file___________________
