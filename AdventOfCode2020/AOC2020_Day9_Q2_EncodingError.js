class SizableMap {
    constructor() {
        this.idToNum = new Map();
        this.latestId = 1;
    }

    add(num) {
        this.idToNum.set(this.latestId, num);
        this.latestId += 1;
    }

    findContiguousSet(num) {
        let contiguousSet = [];
        let start = 1;
        let end = 1;
        let sum = 0;

        while (sum != num) {
            if (sum < num) {
                contiguousSet.push(this.idToNum.get(end));
                sum += this.idToNum.get(end);
                end += 1;
            } else if (sum > num) {
                contiguousSet.shift();
                sum -= this.idToNum.get(start);
                start += 1;
            }
        }
        return contiguousSet;
    }

    toString() {
        let numsString = "[";
        for (const num of this.idToNum.values()) {
            numsString += num.toString() + ",";
        }
        if (numsString.charAt(numsString.length - 1) == ",") {
            numsString = numsString.substring(0, numsString.length - 1);
        }
        numsString += "]";
        return numsString;
    }
}

function readInput() {
    let sizableMap = new SizableMap();
    const fs = require('fs');
    try {
        const data = fs.readFileSync('data.in', 'UTF-8');
        const lines = data.split(/\r?\n/);
        lines.forEach((line) => {
            sizableMap.add(parseInt(line));
        });
    } catch (err) {
        console.error(err);
    }
    return sizableMap;
}

const WEAKNESS = 776203571;
let sizableMap = readInput();

contiguousSet = sizableMap.findContiguousSet(WEAKNESS);
console.log(Math.max(...contiguousSet) + Math.min(...contiguousSet));
