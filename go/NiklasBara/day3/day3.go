package main

import (
	"sort"
	"strconv"
)

func getGammaAndEpsilon(inputs []string) (int,int,error) {
	if len(inputs) <= 0 {
		return 0, 0, nil
	}
	transposed := transpose(toRunes(inputs))
	return gammaAndEpsilon(transposed)
}

func getOxygenAndCO2(inputs []string) (int,int,error) {
	if len(inputs) <= 0 {
		return 0, 0, nil
	}
	return oxygenAndCO2(toRunes(inputs))
}

func oxygenAndCO2(runes [][]rune) (int, int, error) {
	oxygenFilter := func (r [][]rune, index int) rune {
		c, _ := mostCommonAndUncommonRune(r, index)
		return c
	}
	co2Filter := func (r [][]rune, index int) rune {
		_, u := mostCommonAndUncommonRune(r, index)
		return u
	}

	oxygen, _ := parseBinRunes(reduceByFunc(runes, oxygenFilter))
	co2, _ := parseBinRunes(reduceByFunc(runes, co2Filter))
	return int(oxygen), int(co2), nil
}

func reduceByFunc(runes [][]rune, fun func([][]rune, int) rune) []rune {
	runesCopy := make([][]rune, len(runes))
	copy(runesCopy, runes)
	
	for i:= 0; len(runesCopy) > 1; i++ {
		filterValue := fun(runesCopy, i)
		toDelete := make([]int, 0)
		for j, runeLine := range runesCopy {
			if runeLine[i] != filterValue {
				toDelete = append(toDelete, j)
			}
		}

		sort.Sort(sort.Reverse(sort.IntSlice(toDelete)))
		for _, index := range toDelete {
			runesCopy = removeIndex(runesCopy, index)
		}
	}
	return runesCopy[0]
}

func removeIndex(s [][]rune, index int) [][]rune {
    return append(s[:index], s[index+1:]...)
}

func mostCommonAndUncommonRune(runes [][]rune, i int) (rune, rune) {
	c, u := mostCommonAndUncommotBit(transpose(runes)[i])
	return c, u
}

func gammaAndEpsilon(runes [][]rune) (int,int,error){
	gammaCount, epsilonCount := make([]rune, 0), make([]rune, 0)
	for _, runeLine := range runes {
		gammaRune, epsilonRune := mostCommonAndUncommotBit(runeLine)
		
		gammaCount = append(gammaCount, gammaRune)
		epsilonCount = append(epsilonCount, epsilonRune)
	}

	x, _ := parseBinRunes(gammaCount)
	y, _ := parseBinRunes(epsilonCount)

	return int(x), int(y), nil
}

func mostCommonAndUncommotBit(line []rune ) (rune, rune){
	zeros, ones := 0, 0
	for _, r := range line {
		if r == rune(Zero) {
			zeros ++
		} else {
			ones ++
		}
	}
	if  ones >= zeros {
		return One, Zero
	} else {
		return Zero, One
	}
}

func parseBinRunes(runes []rune) (int64, error) {
	return strconv.ParseInt(string(runes), 2, 64)
}


func toRunes(vs []string) [][]rune {
    vsm := make([][]rune, len(vs))
    for i, v := range vs {
        vsm[i] = []rune(v)
    }
    return vsm
}

func transpose(slice [][]rune) [][]rune {
    xl := len(slice[0])
    yl := len(slice)
    result := make([][]rune, xl)
    for i := range result {
        result[i] = make([]rune, yl)
    }
    for i := 0; i < xl; i++ {
        for j := 0; j < yl; j++ {
            result[i][j] = slice[j][i]
        }
    }
    return result
}


const (
	Zero       rune = 48
	One 	        = 49
)
