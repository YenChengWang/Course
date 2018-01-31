import numpy as np
from sklearn.decomposition import NMF,TruncatedSVD,ProjectedGradientNMF
model = NMF(n_components=2, alpha=0.01)

#Store AD
ad_ID_dict   = {}
#ad_list = []
#ad_list = list(ad_list)


#Assign ID number
ad_ID = 0
user_ID = 0

max_feature = 0


#ad_ID for ad_nmu
adID_for_num = {}


with open ('ad_ID.dat') as file:
    for line in file:
        data = line.strip('\n').split('   ')
        #print(data)
        adID_for_num[int(data[1])] = int(data[0])

file.close()



#___________one hot ad building___________
ad_ind = 0
user_ind = 0
p = 0


#out = open('ad_onehot.dat','w')
out = open('click.dat_804.dat','w')

for file_num in range(0, 15, 1):
#for file_num in range(0, 1, 1):
    #Train_Full 0~7:
    if file_num < 8:
        with open ('train_full_'+str(file_num), encoding='UTF-8') as file:
            print("Training_data_",file_num," Start")
            for line in file:
                Data = line.strip('\n').split(' ') 

                #___________click___________
                #if not Data[4:]==['1']:
                if True:

                    out.write( Data[2] + '\n')


                #update ind
                    ad_ind = ad_ind + 1

                p = p + 1

                if(p%250000)==0:
                    print("Have finshed: ", p)
                
            #Finish
            print("Training_data_",file_num," Finish")
            p=0




    #Train Half 8~14:        
    elif file_num > 7:        
        with open ('train_half_'+str(file_num), encoding='UTF-8') as file:
            print("Training_data_",file_num," Start")
            for line in file:
                Data = line.strip('\n').split(' ') 


                #___________click___________
                #if not Data[4:]==['1']:
                if True:

                    out.write( Data[2] + '\n')


                #update ind
                    ad_ind = ad_ind + 1

                p = p + 1

                if(p%250000)==0:
                    print("Have finshed: ", p)
                
            #Finish
            print("Training_data_",file_num," Finish")
            p=0
out.close()

print("Shape of click",ad_ind-1)
print("________________Finsh training data________________")

