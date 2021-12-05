file = open('data/day3.txt', 'r')
values = []
for line in file.readlines():
    values.append(line.rstrip("\n"))

resultSet = {-1:0, 0:0, 1:0, 2:0, 3:0, 4:0, 5:0, 6:0, 7:0, 8:0, 9:0, 10:0, 11:0}

for value in values:
    i = 0
    valuesList = list(value)
    while i < len(valuesList):
        resultSet[i] += int(valuesList[i])
        i += 1
    resultSet[-1] += 1

gammaRate = ""
epsilonRate = ""
i = 0
while i < 12:
    if resultSet[i] > (resultSet[-1]/2):
        gammaRate += "1"
        epsilonRate += "0"
    else:
        gammaRate += "0"
        epsilonRate += "1"
    i += 1

gammaRateInt = int(gammaRate, 2)
epsilonRateInt = int(epsilonRate, 2)

print("Gamma:" + str(gammaRateInt) + " Epsilon:" + str(epsilonRateInt) + " PowerConsumption: " + str(gammaRateInt * epsilonRateInt))
