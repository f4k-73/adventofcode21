package main

import (
	"fmt"
	"strconv"
	"strings"
)

func getCoords(lines []string) (int, int, error) {
	sumMap, err := reduceToSumMap(lines)
	if err != nil {
		return 0, 0, err
	}

	x, y := coordinates(sumMap)
	return x, y, nil
}

func getCoordsWithAim(lines []string) (int, int, error) {
	sumMap, err := reduceToSumMapWithAim(lines)
	if err != nil {
		return 0, 0, err
	}

	x, y := coordinates(sumMap)
	return x, y, nil
}

func coordinates(sumMap map[string]int) (int,int) {
	horizontalPos := sumMap[Forward] - sumMap[Backward]
	depth := sumMap[Down] - sumMap[Up]
	return horizontalPos, depth
}

func reduceToSumMap(lines []string) (map[string]int, error) {
	directionToSum := make(map[string]int)
	for _, element := range lines {
		direction, number, err := splitLine(element)
		if err != nil {
			return nil, err
		}
		directionToSum[direction] += number
	}
	return directionToSum, nil
}

func reduceToSumMapWithAim(lines []string) (map[string]int, error) {
	directionToSum := make(map[string]int)
	for _, element := range lines {
		direction, number, err := splitLine(element)
		if err != nil {
			return nil, err
		}

		switch direction {
		case Down:
			directionToSum[Aim] += number
			break
		case Up:
			directionToSum[Aim] -= number
			break
		case Forward:
			directionToSum[Forward] += number
			directionToSum[Down] += number * directionToSum[Aim]
			break
		case Backward:
			directionToSum[Backward] += number
			break
		}
	}
	return directionToSum, nil
}

func splitLine(line string) (string, int, error) {
	split := strings.Split(line, " ")
	if len(split) != 2 {
		return "", 0, fmt.Errorf("malformed entry should only have 2 data points")
	}

	number, err := strconv.ParseInt(split[1], 10, 64)
	if err != nil {
		return "", 0, err
	}

	return split[0], int(number), nil
}

const (
	Up       string = "up"
	Down            = "down"
	Forward         = "forward"
	Backward        = "backward"
	Aim             = "aim"
)
