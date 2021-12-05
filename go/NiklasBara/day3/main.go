package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
	inputPath, err := filePath()
	if err != nil {
		fmt.Println(err)
		return
	}

	lines, err := linesOfFile(inputPath)
	if err != nil {
		fmt.Println(err)
		return
	}

	gamma, epsilon, err := getGammaAndEpsilon(lines)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Printf("part01: %d\n" ,multiply(gamma, epsilon))

	oxygen, co2, err := getOxygenAndCO2(lines)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Printf("part02: %d\n" ,multiply(oxygen, co2))
}

func multiply(a int, b int) int {
	return a * b
}

func filePath() (string, error){
	if len(os.Args) <= 1 {
		return "" , fmt.Errorf("please provide the input file as argument")
	}
	return os.Args[1], nil
}

func linesOfFile(filePath string) ([]string, error) {
	fileData, err := os.ReadFile(filePath)
	if err != nil {
		return nil, err
	}
	return splitLines(strings.TrimSpace(string(fileData))), nil
}

func splitLines(stringToSplit string) []string {
	return strings.Split(strings.ReplaceAll(stringToSplit, "/r/n", "/n"),"\n")
}



