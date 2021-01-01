package main

import (
    "bufio"
    "fmt"
    "log"
    "os"
    "strconv"
)

var instructions = make(map[int] *Instruction)
var accumulator = 0
var currentInstruction = 0

func main() {
    readInstructions()

    if (isCorrupted()) {
        fixInstructions()
    }

    executeFromStart()

    fmt.Println(accumulator)
}

func printInstructions() {
    id := 0
    for id < len(instructions) {
        instPtr := instructions[id]
        instType := (*instPtr).instruction_type
        argument := (*instPtr).argument
        fmt.Printf("(%d) " + instType + " %d\n", id, argument)
        id++
    }
}

func readInstructions() {
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
            return Instruction{instruction_type, AccExecution{}, argument, 0}
        case "jmp":
            return Instruction{instruction_type, JmpExecution{}, argument, 0}
        case "nop":
            return Instruction{instruction_type, NopExecution{}, argument, 0}
        default:
            panic("Invalid instruction!")
    }
}

func fixInstructions() {
    // Try changing jmp to nop
    for id, instPtr := range instructions {
        instType := (*instPtr).instruction_type
        if instType == "jmp" {
            setToNop(id)
            if (isCorrupted()) {
                setToJmp(id)
            } else {
                return
            }
        }
    }

    // Try changing nop to jmp
    for id, instPtr := range instructions {
        instType := (*instPtr).instruction_type
        if instType == "nop" {
            setToJmp(id)
            if (isCorrupted()) {
                setToNop(id)
            } else {
                return
            }
        }
    }

    panic("Unable to fix instructions!")
}

func setToNop(id int) {
    argument := instructions[id].argument
    nopInstruction := Instruction{"nop", NopExecution{}, argument, 0}
    instructions[id] = &nopInstruction
}

func setToJmp(id int) {
    argument := instructions[id].argument
    jmpInstruction := Instruction{"jmp", JmpExecution{}, argument, 0}
    instructions[id] = &jmpInstruction
}

func reset() {
    accumulator = 0
    currentInstruction = 0
    for _, instPtr := range instructions {
        (*instPtr).execution_count = 0
    }
}

func isCorrupted() bool {
    numInstructions := len(instructions)
    for {
        isCompleteBoot := currentInstruction == numInstructions
        if isCompleteBoot {
            reset()
            return false
        }

        isExecuted := instructions[currentInstruction].execution_count > 0
        if isExecuted {
            reset()
            return true
        }

        // fmt.Printf("Current instruction: %d\n", currentInstruction)
        executeInstruction(instructions[currentInstruction])
    }
}

func executeFromStart() {
    numInstructions := len(instructions)
    for {
        isCompleteBoot := currentInstruction == numInstructions
        if isCompleteBoot { return }
        executeInstruction(instructions[currentInstruction])
    }
}

func executeInstruction(instPtr *Instruction) {
    (*instPtr).execution.execute((*instPtr).argument)
    (*instPtr).execution_count++
}

type Execution interface {
    execute(argument int)
}

type AccExecution struct {}

type JmpExecution struct {}

type NopExecution struct {}

func (ae AccExecution) execute(argument int) {
    accumulator += argument
    currentInstruction += 1
}

func (je JmpExecution) execute(argument int) {
    currentInstruction += argument
}

func (ne NopExecution) execute(argument int) {
    currentInstruction += 1
}

type Instruction struct {
    instruction_type string
    execution Execution
    argument int
    execution_count int
}
