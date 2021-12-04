class Bingo:
    def __init__(self, lines):
        self.field = []
        for line in lines:
            fieldLine = []
            for number in line:
                if number:
                    fieldLine.append(Number(number))
            self.field.append(fieldLine)

    def __repr__(self):
        return str(self.field)

    def winningNumber(self, winNumber):
        for line in self.field:
            for number in line:
                if number.number is int(winNumber):
                    number.win()

    def checkForWin(self):
        return self.checkForWinVertival() or self.checkForWinHorizontal()

    def checkForWinVertival(self):
        if self.field[0][0].active and self.field[1][0].active and self.field[2][0].active and self.field[3][0].active and self.field[4][0].active:
            return True
        if self.field[0][1].active and self.field[1][1].active and self.field[2][1].active and self.field[3][1].active and self.field[4][1].active:
            return True
        if self.field[0][2].active and self.field[1][2].active and self.field[2][2].active and self.field[3][2].active and self.field[4][2].active:
            return True
        if self.field[0][3].active and self.field[1][0].active and self.field[2][3].active and self.field[3][3].active and self.field[4][3].active:
            return True
        if self.field[0][4].active and self.field[1][4].active and self.field[2][4].active and self.field[3][4].active and self.field[4][4].active:
            return True
        return False

    def checkForWinHorizontal(self):
        if self.field[0][0].active and self.field[0][1].active and self.field[0][2].active and self.field[0][3].active and self.field[0][4].active:
            return True
        if self.field[1][0].active and self.field[1][1].active and self.field[1][2].active and self.field[1][3].active and self.field[1][4].active:
            return True
        if self.field[2][0].active and self.field[2][1].active and self.field[2][2].active and self.field[2][3].active and self.field[2][4].active:
            return True
        if self.field[3][0].active and self.field[3][1].active and self.field[3][2].active and self.field[3][3].active and self.field[3][4].active:
            return True
        if self.field[4][0].active and self.field[4][1].active and self.field[4][2].active and self.field[4][3].active and self.field[4][4].active:
            return True
        return False

    def getScore(self, lastNumber):
        calc = 0
        for line in self.field:
            for number in line:
                if not number.active:
                    calc += number.number
        return calc * int(lastNumber)


class Number:
    def __init__(self, number):
        self.number = int(number)
        self.active = False

    def __repr__(self):
        return str(self.number)

    def win(self):
        self.active = True

##################>MAIN<############################

file = open('data/day3.txt', 'r')
winningNumbers = []
bingoFields = []
lines = []
first = True
for line in file.readlines():
    if first:
        winningNumbers = line.rstrip("\n").split(",")
        first = False
    else:
        if not line.rstrip("\n"):
            if lines:
                bingoFields.append(Bingo(lines))
            lines = []
        else:
            lines.append(line.rstrip("\n").split(" "))

for winningNumber in winningNumbers:
    for bingoField in bingoFields:
        bingoField.winningNumber(winningNumber)
        if bingoField.checkForWin():
            print(bingoField.getScore(winningNumber))
            exit(0)