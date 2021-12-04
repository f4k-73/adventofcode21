package main

import (
	"encoding/json"
	"fmt"
	"regexp"
	"sort"
)

func winBingo(lines []string) (int, int){
	boards, numbers := parseInput(lines)
	winningBoard, usedNumbers := playBingoUntilFirstWin(boards, numbers)
	
	return unmarkedSum(winningBoard), usedNumbers[len(usedNumbers) - 1]
}

func looseBingo(lines []string) (int, int){
	boards, numbers := parseInput(lines)
	winningBoard, usedNumbers := playBingoUntilLastBoardWin(boards, numbers)
	
	return unmarkedSum(winningBoard), usedNumbers[len(usedNumbers) - 1]
}


func unmarkedSum(board board) int {
	sum := 0

	for _, row := range board.cells {
		for _, cell := range row {
			if !cell.IsMarked {
				sum += cell.Value
			}
		}
	}

	return sum
}

func playBingoUntilFirstWin(boards []board, numbers []int) (board, []int) {
	usedNumbers := make([]int, 0)
	for i, number := range numbers {
		usedNumbers = append(usedNumbers, number)
		for _, board := range boards {
			markBoard(board, number)
			if hasWon(board.cells) {
				return board, usedNumbers[:i + 1]
			}
		}
	}

	return board{}, usedNumbers
}

func playBingoUntilLastBoardWin(boards []board, numbers []int) (board, []int) {
	boardsLeft := len(boards)
	boadsDone := make([]int, 0)
	usedNumbers := make([]int, 0)
	
	for i, number := range numbers {
		usedNumbers = append(usedNumbers, number)

		for j, board := range boards {
			if contains(boadsDone, j){
				continue
			}
			markBoard(board, number)
			if hasWon(board.cells) {
				boardsLeft --
				boadsDone = append(boadsDone, j)
				if boardsLeft == 0 {
					return board, usedNumbers[:i + 1]
				}
			}
		}
	}

	return board{}, usedNumbers
}

func contains(arr []int, number int) bool {

	for _, v := range arr {
		if v == number {
			return true
		}
	}
	return false
}

func markBoard(board board, number int) {
	for i, row := range board.cells {
		for j, cell := range row {
			if cell.Value == number {
				board.cells[i][j].IsMarked = true
			}
		}
	}
}

func hasWon(cells [][]cell) bool {
	return hasWonInRows(cells) || hasWonInCols(cells)
}

func hasWonInRows(cells [][]cell) bool {
	for _, row := range cells {
		if allTrue(row) {
			return true
		}
	}
	return false
}

func hasWonInCols(cells [][]cell) bool {
	return hasWonInRows(transpose(cells))
}

func allTrue(vals []cell) bool {
	for _, v := range vals {
		if !v.IsMarked {
			return false
		}
	}
	return true
}


func parseInput(lines []string) ([]board, []int){
	return parseBoards(lines[2:]), parseIntArray(lines[0])
}

func parseBoards(lines []string) []board {
	noEmptyLines := filter(lines, func(str string) bool {
		return str != ""
	})

	boardSize := 5
	slices := splitSlices(noEmptyLines, boardSize)
	return toBoards(slices, boardSize)
}

func toBoards(slices [][]string, boardSize int) []board {
	boards := make([]board, len(slices))
	for index, boardSlices := range slices {
		boards[index] = toBoard(boardSlices, boardSize)
	}
	return boards
}
func toBoard(slice []string, boardSize int) board {
	cells := make([][]cell, boardSize)
	for index, rowStr := range slice {
		matcher := regexp.MustCompile(`([0-9]+)`)
		jsonRowStr := matcher.ReplaceAllString(rowStr, `{"value": $1 ,"isMarked": false},`)
		cellsRow := parseBoardRow(jsonRowStr[:len(jsonRowStr)-1])
		
		cells[index] = cellsRow
	}
	return board{
		cells: cells,
	}
}


type board struct {
	cells [][]cell
}

type cell struct {
	Value int     `json:"value"`
	IsMarked bool `json:"isMarked"`
}

func filter(arr []string, predicate func(string) bool) []string {
	arrCopy := make([]string, len(arr))
	copy(arrCopy, arr)
	toDelete := make([]int, 0)

	for index, val := range arr {
		if !predicate(val) {
			toDelete = append(toDelete, index)
		}
	}
	sort.Sort(sort.Reverse(sort.IntSlice(toDelete)))

	for _, index := range toDelete {
		arrCopy = removeIndex(arrCopy, index)
		
	}

	return arrCopy
}

func removeIndex(s []string, index int) []string {
    return append(s[:index], s[index+1:]...)
}

func splitSlices(slice []string, size int) [][]string {
	res := make([][]string, 0)
	var j int
	for i := 0; i < len(slice); i += size{
		j += size
		if j > len(slice) {
			j = len(slice)
		}
		
		res = append(res, slice[i:j])
	}
	return res
}

func parseIntArray(csv string) []int {
	var ints []int
	json.Unmarshal([]byte(fmt.Sprintf("[%s]", csv)), &ints)	

	return ints
}

func parseBoardRow(csv string) []cell {
	var cells []cell
	json.Unmarshal([]byte(fmt.Sprintf("[%s]", csv)), &cells)
	return cells
}

func transpose(slice [][]cell) [][]cell {
    xl := len(slice[0])
    yl := len(slice)
    result := make([][]cell, xl)
    for i := range result {
        result[i] = make([]cell, yl)
    }
    for i := 0; i < xl; i++ {
        for j := 0; j < yl; j++ {
            result[i][j] = slice[j][i]
        }
    }
    return result
}