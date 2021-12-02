file = open('data/day1.txt', 'r')

values = []
for line in file.readlines():
    values.append(int(line))

counter = 0
i = 3
while i < len(values):
    if values[i-3] < values [i]:
        counter = counter + 1
    i = i + 1

print(counter)