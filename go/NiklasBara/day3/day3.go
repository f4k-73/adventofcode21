package main

import (
	"strconv"
)

func getGammaAndEpsilon(inputs []string) (int,int,error) {
	if len(inputs) <= 0 {
		return 0, 0, nil
	}
	transposed := transpose(toRunes(inputs))
	return gammaAndEpsilon(transposed)
}

func gammaAndEpsilon(runes [][]rune) (int,int,error){
	gammaCount, epsilonCount := make([]rune, 0), make([]rune, 0)
	for _, runeLine := range runes {
		gammaRune, epsilonRune := gammeAndEpsilonRune(runeLine)
		
		gammaCount = append(gammaCount, gammaRune)
		epsilonCount = append(epsilonCount, epsilonRune)
	}

	x, _ := parseBinRunes(gammaCount)
	y, _ := parseBinRunes(epsilonCount)

	return int(x), int(y), nil
}

func gammeAndEpsilonRune(line []rune ) (rune, rune){
	zeros, ones := 0, 0
	for _, r := range line {
		if r == rune(Zero) {
			zeros ++
		} else {
			ones ++
		}
	}
	if  ones > zeros {
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
