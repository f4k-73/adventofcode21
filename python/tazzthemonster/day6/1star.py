class Fish:

    def __init__(self, timer=8):
        self.timer = timer

    def __repr__(self):
        return str(self.timer)

    def spawn(self):
        if self.timer <= 0:
            self.timer = 6
            return Fish()
        else:
            self.timer -= 1


file = open('data/day6.txt', 'r')
fishes = []
for line in file.readlines():
    allValues = line.rstrip("\n").split(",")
    for f in allValues:
        fishes.append(Fish(int(f)))

for i in range(80):
    spawnedFishes = []
    for f in fishes:
        newFish = f.spawn()
        if newFish:
            spawnedFishes.append(newFish)
    fishes += spawnedFishes

print(len(fishes))