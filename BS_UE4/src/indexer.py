##########################################################
#
#    Abgabe von Martin Schultz
#        Mat.-Nr.: 780331 
#
##########################################################

import os;
import pickle;
import pprint;

txtfiles = [];
for blah in os.walk(os.getcwd()):
    for b in blah[2]:
        txtfiles.append(blah[0] + "/" + b);

dict1 = {};
for ffile in txtfiles :
    if ffile.endswith(".txt"):
        txt = open(ffile, "r");
        lines = txt.readlines();
        for line in lines:
            words = line.strip().split(" ");
            for word in words:
                if word not in dict1:
                    dict1[word] = [ffile];
                else:
                    dict1[word].append(ffile);

pickle.dump(dict1, open("save.p", "wb"));

# only for test
dict2 = pickle.load(open("save.p", "rb"));

# print dict2

pprint.PrettyPrinter().pprint(dict2);
