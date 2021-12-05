def countValues(values):
    resultSet = {-1:0, 0:0, 1:0, 2:0, 3:0, 4:0, 5:0, 6:0, 7:0, 8:0, 9:0, 10:0, 11:0}

    for value in values:
        i = 0
        valuesList = list(value)
        while i < len(valuesList):
            resultSet[i] += int(valuesList[i])
            i += 1
        resultSet[-1] += 1
    return resultSet

def selectByPos(values, pos, selector):
    newValues = []
    for value in values:
        if list(value)[pos] is selector:
            newValues.append(value)
    if len(newValues) is 0:
        return values
    else:
        return newValues



file = open('data/day3.txt', 'r')
values = []
for line in file.readlines():
    values.append(line.rstrip("\n"))

resultSet = countValues(values)

i = 0
oxList = values.copy()
while i < len(oxList[0]):
    counter = countValues(oxList)
    selector = "1"
    if counter[i] < (counter[-1]/2):
        selector = "0"
    oxList = selectByPos(oxList, i, selector)
    i += 1
oxygen = int(oxList[0], 2)

i = 0
co2List = values.copy()
while i < len(co2List[0]):
    counter = countValues(co2List)
    selector = "0"
    if counter[i] < (counter[-1]/2):
        selector = "1"
    co2List = selectByPos(co2List, i, selector)
    i += 1
co2 = int(co2List[0], 2)


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

print("Gamma:" + str(gammaRateInt) + "\nEpsilon:" + str(epsilonRateInt) + "\nOxigen" + str(oxygen) + "\nco2:" + str(co2) + "\nPowerConsumption: " + str(gammaRateInt * epsilonRateInt) + "\nLifeSupport Rating:" + str(co2 * oxygen))