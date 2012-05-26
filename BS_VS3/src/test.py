# -*- coding: cp1252 -*-
# test

dividiere=lambda x,y:x/y

tests=((23,0.1),('Spam','Eggs'),(25,0))
try:
    for a,b in tests:
        try:
            print dividiere(a,b)
        finally:
            print("Division ausgeführt")
except ZeroDivisionError:
    print('Nicht durch Null teilen...')
finally:
    print "Cleaning."
print('Bye.')
    