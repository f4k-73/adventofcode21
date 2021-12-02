package main

import "testing"

func TestGetCoords(t *testing.T) {
	input := []string{
		"forward 5",
		"down 5",
		"forward 8",
		"up 3",
		"down 8",
		"forward 2",
	}
	gotX, gotY, gotErr := getCoords(input)
	wantX, wantY := 15, 10

	if gotX != wantX {
		t.Errorf("got %v want %v", gotX, wantX)
	}
	if gotY != wantY {
		t.Errorf("got %v want %v", gotY, wantY)
	}
	if gotErr != nil {
		t.Errorf("got %v want %v", gotErr, nil)
	}
}
func TestGetCoordsUsingAim(t *testing.T) {
	input := []string{
		"forward 5",
		"down 5",
		"forward 8",
		"up 3",
		"down 8",
		"forward 2",
	}
	gotX, gotY, gotErr := getCoordsWithAim(input)
	wantX, wantY := 15, 60

	if gotX != wantX {
		t.Errorf("got %v want %v", gotX, wantX)
	}
	if gotY != wantY {
		t.Errorf("got %v want %v", gotY, wantY)
	}
	if gotErr != nil {
		t.Errorf("got %v want %v", gotErr, nil)
	}
}
