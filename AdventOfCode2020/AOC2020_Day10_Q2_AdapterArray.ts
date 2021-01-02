function readInput(adapters) {
    const fs = require('fs');
    try {
        const data: string = fs.readFileSync('data.in', 'UTF-8');
        const lines: string[] = data.split(/\r?\n/);
        let highest = Number.MIN_SAFE_INTEGER;
        lines.forEach((line) => {
            const num = parseInt(line);
            adapters.add(num);
            if (num > highest) { highest = num };
        });
        return highest;
    } catch (err) {
        console.error(err);
    }
}

function countArrangements(adapters: Set<number>, start: number, largest: number) {
    if (start == largest) { return 1 };

    if (memo.has(start)) { return memo.get(start); }

    let arrangements = 0;
    const nexts = [start + 1, start + 2, start + 3];
    for (const next of nexts) {
        if (adapters.has(next)) {
            arrangements += countArrangements(adapters, next, largest);
        }
    }

    if (!memo.has(start)) { memo.set(start, arrangements); }
    
    return arrangements;
}

let adapters: Set<number> = new Set();
const largest = readInput(adapters);
let memo: Map<number, number> = new Map();
const arrangements = countArrangements(adapters, 0, largest)
console.log(arrangements)
