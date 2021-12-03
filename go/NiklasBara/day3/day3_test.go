package main

import "testing"

func TestGammaAndEpsilon(t *testing.T) {
	input := []string{
		"00100",
		"11110",
		"10110",
		"10111",
		"10101",
		"01111",
		"00111",
		"11100",
		"10000",
		"11001",
		"00010",
		"01010",
	}

	gotGamma, gotEpsilon, gotErr := getGammaAndEpsilon(input)
	wantGamma, wantEpsilon := 22, 9

	assertEqual(t, gotGamma, wantGamma)
	assertEqual(t, gotEpsilon, wantEpsilon)
	assertNil(t, gotErr)
}


func assertEqual(t *testing.T, got interface{}, want interface{}){
	if got != want {
		t.Errorf("got %v want %v\n", got, want)
	}
}

func assertNil(t *testing.T, got interface{}){
	if got != nil {
		t.Errorf("got %v wanted nil\n", got)
	}
}
