function readInput(adapters) {
    const fs = require('fs');
    try {
        const data: string = fs.readFileSync('data.in', 'UTF-8');
        const lines: string[] = data.split(/\r?\n/);
        lines.forEach((line) => {
            const num = parseInt(line);
            adapters.add(num);
        });
    } catch (err) {
        console.error(err);
    }
}

function countJoltageDifferences(adapters: Set<number>, start: number) {
    let current = start;
    let numOneDiff = 0;
    let numThreeDiff = 0;
    do {
        adapters.delete(current);
        let nextCandidates = [current + 1, current + 2, current + 3];
        for (const next of nextCandidates) {
            if (adapters.has(next)) {
                if (next - current == 1) { numOneDiff += 1 };
                if (next - current == 3) { numThreeDiff += 1 };
                current = next;
                break;
            }
        }
    } while (adapters.size != 0)
    console.log(numOneDiff);
    console.log(numThreeDiff + 1);
}

let adapters: Set<number> = new Set();
readInput(adapters);
countJoltageDifferences(adapters, 0);
