map = []

def isLowpoint(x, y):
    height = getValues(x, y)
    top = getValues(x+1, y)
    bottom = getValues(x-1, y)
    left = getValues(x, y-1)
    right = getValues(x, y+1)
    for i in [top, bottom, left, right]:
        if i <= height:
            return False
    return True

def getValues(x, y):
    try:
        return map[x][y]
    except:
        return 10

file = open('data/day9.txt', 'r')
for line in file.readlines():
    lineList = []
    for char in line.rstrip("\n"):
        lineList.append(int(char))
    map.append(lineList)

lowPointValue = 0

for x in range(len(map)):
    for y in range(len(map[x])):
        if isLowpoint(x, y):
            lowPointValue += map[x][y]+1


print(lowPointValue)