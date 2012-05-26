tupel=({(42,69):["Spam"],"Eric":(1999,)},"Otternasen")

print tupel[1]
print tupel[0]["Eric"][0]
print tupel[0][42,69][0][2:]
del tupel[1]
