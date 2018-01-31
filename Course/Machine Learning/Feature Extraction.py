import numpy as np
from sklearn.decomposition import NMF,TruncatedSVD,ProjectedGradientNMF
model = NMF(n_components=2, alpha=0.01)

#Store AD
ad_ID_dict   = {}
#ad_list = []
ad_list = np.zeros(20000000)
ad_list = list(ad_list)


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
time_ind, day = 0, 0                #______________time feature 1~15______________
p = 0


#out = open('ad_onehot.dat','w')
out = open('all sparse_804.dat','w')

for file_num in range(0, 15, 1):
#for file_num in range(0, 1, 1):

    day = day + 1       #1~15, morning: day*3-3,;noon:day*3-2,;noght:day*3-1
    start_time = 0
    start = True
    
    
    #Train_Full 0~7:
    if file_num < 8:
        with open ('train_full_'+str(file_num), encoding='UTF-8') as file:
            print("Training_data_",file_num," Start")
            #Record the first time if each dat
            
            for line in file:
                Data = line.strip('\n').split(' ') 
            
                if start:
                    start_time = int((Data[0])[4:])
                    start = False



                if True:
                    
                    ad = int(Data[1].replace('id-','')) 
                    user = Data[4:]
                    #print(user)
                #____________USER___________
                    for d in user:
                        d = int(d)-1
                        out.write(str(ad_ind) + ' ' +  str(d) + ' 1\n')
                        
                #____________ad___________
                    out.write(str(ad_ind) + ' ' + str(  adID_for_num[ad] + 136  ) + ' 1\n')



                #___________time feature____________

                    #start time!!!!!!!!!!!!!!!!!!!!!!!!!
                    time = int((Data[0])[4:])
                    time_index = 0

                    if time - start_time <= 28800:
                        time_index = day*3-3

                    elif time - start_time <=57600 and time - start_time > 28800:
                        time_index = day*3-2

                    elif time - start_time > 57600:
                        time_index = day*3-1

                    out.write(str(ad_ind) + ' ' + str(  time_index + 759  ) + ' 1\n')

                #______________time feature______________

                    #update ind
                    ad_ind = ad_ind + 1

                #Stop condition
                #if p == 100000:
                #    break
                #Count
                p = p + 1

                if(p%250000)==0:
                    print("Have finshed: ", p)
                
            #Finish
            print("Training_data_",file_num," Finish")
            p=0
            start = True

    #Train Half 8~14:        
    elif file_num > 7:        
        with open ('train_half_'+str(file_num), encoding='UTF-8') as file:
            print("Training_data_",file_num," Start")

            for line in file:
                Data = line.strip('\n').split(' ') 
                #Record the first time if each dat
                if start:
                    start_time = int((Data[0])[4:])
                    start = False
            



                if True:
                    
                    ad = int(Data[1].replace('id-','')) 
                    user = Data[4:]
                    #print(user)
                #____________USER___________
                    for d in user:
                        d = int(d)-1
                        out.write(str(ad_ind) + ' ' +  str(d) + ' 1\n')
                        
                #____________ad___________
                    out.write(str(ad_ind) + ' ' + str(  adID_for_num[ad] + 136  ) + ' 1\n')



                #___________time feature____________

                    #start time!!!!!!!!!!!!!!!!!!!!!!!!!
                    time = int((Data[0])[4:])
                    time_index = 0

                    if time - start_time <= 28800:
                        time_index = day*3-3

                    elif time - start_time <=57600 and time - start_time > 28800:
                        time_index = day*3-2

                    elif time - start_time > 57600:
                        time_index = day*3-1

                    out.write(str(ad_ind) + ' ' + str(  time_index + 759  ) + ' 1\n')

                #______________time feature______________

                #update ind
                    ad_ind = ad_ind + 1


                #Stop condition
                #if p == 100000:
                #    break
                #Count
                p = p + 1

                if(p%250000)==0:
                    print("Have finshed: ", p)
                
            #Finish
            print("Training_data_",file_num," Finish")
            p=0
            start = True

out.close()

print("Shape of sparse",ad_ind-1)
print("________________Finsh training data________________")





#___________TEST DATA___________
#___________one hot ad building___________
ad_ind = 0
p = 0

#Reset the day to 8th day
day = 8

out = open('all sparse_test_804.dat','w')

#for file_num in range(0, 1, 1):
for file_num in range(8, 15, 1):

    day = day + 1       #1~15, morning: day*3-3,;noon:day*3-2,;noght:day*3-1
    start_time = 0
    start = True
    

    #Test_half  8~14:
    if file_num > 7:
        with open ('test_half_'+str(file_num), encoding='UTF-8') as file:
            print("Testing_data_",file_num," Start")
            
            for line in file:
                Data = line.strip('\n').split(' ')             
                #Record the first time if each dat
                if start:
                    start_time = int((Data[0])[4:])
                    start = False



                if True:
                    
                    ad = int(Data[1].replace('id-','')) 
                    user = Data[3:]
                    #print(user)
                #____________USER___________
                    for d in user:
                        d = int(d)-1
                        out.write(str(ad_ind) + ' ' +  str(d) + ' 1\n')
                        
                #____________ad___________
                    out.write(str(ad_ind) + ' ' + str(  adID_for_num[ad] + 136  ) + ' 1\n')



                #___________time feature____________

                    #start time!!!!!!!!!!!!!!!!!!!!!!!!!
                    time = int((Data[0])[4:])
                    time_index = 0

                    #
                    if time - start_time <= 14400:
                        time_index = day*3-2

                    elif time - start_time > 14400:
                        time_index = day*3-1

                    out.write(str(ad_ind) + ' ' + str(  time_index + 759  ) + ' 1\n')

                #______________time feature______________

                #update ind
                    ad_ind = ad_ind + 1


                #Stop condition
                #if p == 100000:
                #    break
                #Count
                p = p + 1

                if(p%250000)==0:
                    print("Have finshed: ", p)

            #Finish
            print("Testing_data_",file_num," Finish")
            p=0
            start = True

                    
out.close()

print("________________Finsh test data________________")




