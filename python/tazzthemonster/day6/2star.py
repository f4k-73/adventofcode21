fishes = {0: 0, 1: 0, 2: 0, 3: 0, 4: 0, 5: 0, 6: 0, 7: 0, 8: 0}

def addFish(timer: int, fishes, number=1):
    fishes[timer] = fishes[timer] + number

def time(fishes):
    return {
        0: fishes[1],
        1: fishes[2],
        2: fishes[3],
        3: fishes[4],
        4: fishes[5],
        5: fishes[6],
        6: fishes[7] + fishes[0],
        7: fishes[8],
        8: fishes[0]
    }


file = open('data/day6.txt', 'r')
for line in file.readlines():
    allValues = line.rstrip("\n").split(",")
    for f in allValues:
        addFish(int(f), fishes)

for _ in range(256):
    fishes = time(fishes)

counter = 0
for key in fishes:
    counter += fishes[key]

print(counter)