#!/user/bin/

dic={}
f=open("useradressen.txt")
text=f.readlines()
for x in text:
	x=x.split(",")
	name=x[0].split(" ")
	plz=x[2].split(" ")
	plz=plz[0]
	tupel=(name[1],name[0])
	
	if plz in dic:
		dic[plz].append(tupel) 
	else:
		dic[plz]=[tupel]	
	
print dic

f.close()





