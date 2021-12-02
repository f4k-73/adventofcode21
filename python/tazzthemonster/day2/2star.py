file = open('data/day2.txt', 'r')

values = []
for line in file.readlines():
    values.append(line)

coordinates = {'forward':0, 'depth':0, 'aim':0}
for value in values:
    line = value.split(" ")
    if 'forward' in line[0]:
        coordinates['forward'] += int(line[1])
        coordinates['depth'] += int(line[1]) * coordinates['aim']
    elif 'down' in line[0]:
        coordinates['aim'] += int(line[1])
    elif 'up' in line[0]:
        coordinates['aim'] -= int(line[1])

print(coordinates['depth'] * coordinates['forward'])