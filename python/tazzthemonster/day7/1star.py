crabs = []
file = open('data/day7.txt', 'r')
for line in file.readlines():
    allValues = line.rstrip("\n").split(",")
    for f in allValues:
        for c in f.split(","):
            crabs.append(int(c))

pos = -1
fuel = -1
for i in range(max(crabs)):
    thisFuel = 0
    for c in crabs:
        if i < c:
            thisFuel += c-i
        elif i > c:
            thisFuel += i-c
    if thisFuel < fuel or fuel == -1:
        fuel = thisFuel
        pos = i


print(fuel)