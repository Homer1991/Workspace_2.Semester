def dividiere(x,y): return(x/y)
tests=((23, 69), (42, 0), ('Spam', 'Eggs'))
try:
    for a,b in tests:
        print(a,b),
        try:
            print dividiere(a,b)
        finally:
            print("versuchte zu dividieren")
except ZeroDivisionError:
    print('aber ich mag nicht ..')
print('\nBye')                 