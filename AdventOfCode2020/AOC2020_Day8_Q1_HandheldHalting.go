package main

import (
    "bufio"
    "fmt"
    "log"
    "os"
    "strconv"
)

var accumulator = 0
var currentInstruction = 0

func main() {
    instructions := readInstructions()
    executeFromStart(instructions)
    fmt.Println(accumulator)
}

func executeFromStart(instructions map[int] *Instruction) {
    for instructions[currentInstruction].execution_count < 1 {
        executeInstruction(instructions[currentInstruction])
    }
}

func executeInstruction(instPtr *Instruction) {
    (*instPtr).execution.execute()
    (*instPtr).execution_count++
}


func readInstructions() map[int] *Instruction {
    instructions := make(map[int] *Instruction)

    // Open input data file
    file, err := os.Open("data.in")
    if err != nil {
        log.Fatal(err)
    }
    defer file.Close()

    // Read input line by line
    scanner := bufio.NewScanner(file)
    id := 0
    for scanner.Scan() {
        instruction := parseInstruction(scanner.Text())
        instructions[id] = &instruction
        id++
    }
    if err := scanner.Err(); err != nil {
        log.Fatal(err)
    }

    return instructions
}

func parseInstruction(content string) Instruction {
    instruction_type := content[0:3]

    sign := string(content[4])
    magnitude, err := strconv.Atoi(content[5:])
    if err != nil {
        panic("Invalid argument")
    }

    var argument int
    switch sign {
        case "+":
            argument = magnitude
        case "-":
            argument = -magnitude
        default:
            panic("Invalid sign!")
    }

    switch instruction_type {
        case "acc":
            return Instruction{AccExecution{argument}, 0}
        case "jmp":
            return Instruction{JmpExecution{argument}, 0}
        case "nop":
            return Instruction{NopExecution{argument}, 0}
        default:
            panic("Invalid instruction!")
    }
}

type Execution interface {
    execute()
}

type AccExecution struct {
    argument int
}

type JmpExecution struct {
    argument int
}

type NopExecution struct {
    argument int
}

func (ae AccExecution) execute() {
    accumulator += ae.argument
    currentInstruction += 1
}

func (je JmpExecution) execute() {
    currentInstruction += je.argument
}

func (ne NopExecution) execute() {
    currentInstruction += 1
}

type Instruction struct {
    execution Execution
    execution_count int
}
