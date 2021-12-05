package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)
 
func main() {
	filePath, err := fileName()
	if err != nil {
		fmt.Println(err)
	}

	numbers, err := parseFile(filePath)
	if err != nil {
		fmt.Println(err)
	}

	sums := slidingWindowSum(numbers, 3)

	increases := countIncreases(sums)
	fmt.Printf("Count of increases in inputfile: %d\n", increases)
}

func countIncreases(numbers []int) int {
	count := 0
	
	for i, v := range numbers {
		if i == 0 {
			continue
		}
		if v > numbers[i - 1]{
			count ++
		}
	}

	return count
}



func fileName() (string, error) {
	if len(os.Args) <= 1 {
		return "", fmt.Errorf("please provide an input file as first command-line argument")
	}
	return os.Args[1], nil
}

func parseFile(inputPath string) ([]int, error){
	fileData, err := os.ReadFile(inputPath)
	if err != nil {
		return nil, err
	}
	strEntries := strings.Split(strings.ReplaceAll(strings.TrimSpace(string(fileData)), "\r\n", "\n"), "\n")
	
	entries, err := Map(strEntries, func(x string) (int, error){
			i, err2 := strconv.ParseInt(x, 10, 64)
			return int(i), err2
	})
	if err != nil {
		return nil, err
	}

	return entries, nil
}

func Map(vs []string, f func(string) (int, error)) ([]int, error) {
    vsm := make([]int, len(vs))
	var err error
    for i, v := range vs {
        vsm[i], err = f(v)
		if err != nil {
			return nil, err
		}
    }
    return vsm, nil
}

func slidingWindowSum(numbers []int, sliceSize int) []int {
	var sums []int
	for i := range numbers {
		endOfSlice := i + sliceSize
		if endOfSlice > len(numbers){
			return sums
		}
		slice := numbers[i:endOfSlice]
		sums = append(sums, sum(slice))
	}
	return sums	
}

func sum(array []int) int {  
	result := 0  
	for _, v := range array {  
	 result += v  
	}  
	return result  
}