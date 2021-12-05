class Coordinate:
    def __init__(self, x, y):
        self.x = int(x)
        self.y = int(y)

    def __repr__(self):
        return "%d/%d" % (self.x, self.y)

    def __str__(self):
        return self.__repr__()

    def xPlus(self, plus):
        return Coordinate(self.x + plus, self.y)

    def yPlus(self, plus):
        return Coordinate(self.x, self.y + plus)

class Map:
    def __init__(self):
        self.map = {}

    def add(self, coordinate):
        coordinateStr = coordinate.__str__()
        if coordinateStr in self.map:
            self.map[coordinateStr] += 1
        else:
            self.map[coordinateStr] = 1

def addOne(number) -> int:
    if number < 0:
        return int(number - 1)
    if number > 0:
        return int(number + 1)
    return 0

def myRange(dist):
    if dist < 0:
        return range(0, addOne(dist), -1)
    else:
        return range(0, addOne(dist))

file = open('data/day5.txt', 'r')
inputs = []

for line in file.readlines():
    set = []
    for i in line.rstrip().split(" -> "):
        c = i.split(",")
        set.append(Coordinate(c[0], c[1]))
    inputs.append(set)
map = Map()
for set in inputs:
    xDist = set[1].x - set[0].x
    yDist = set[1].y - set[0].y
    if xDist and yDist:
        same = False
        if yDist == xDist:
            same = True
        for i in myRange(xDist):
            if same:
                map.add(set[0].xPlus(i).yPlus(i))
            else:
                map.add(set[0].xPlus(i).yPlus(i*-1))
    else:
        for i in myRange(xDist):
            map.add(set[0].xPlus(i))
        for i in myRange(yDist):
            map.add(set[0].yPlus(i))

counter = 0
for coordinates in map.map:
    if map.map[coordinates] > 1:
        counter += 1

print(counter)