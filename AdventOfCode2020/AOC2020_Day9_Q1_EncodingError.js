class SizableMap {
    constructor(maxSize) {
        this.maxSize = maxSize;
        this.idToNum = new Map();
        this.numToCount = new Map();
        this.latestId = 1;
    }

    isCompliant(num) {
        // Check if number is part of the preamble
        if (this.latestId <= this.maxSize) {
            return true;
        }

        // Check if sum of the number can be found
        for (const subtrahend of this.numToCount.keys()) {
            const difference = num - subtrahend;
            if (difference != subtrahend && this.numToCount.has(difference)) {
                return true;
            }
        }
        return false;
    }

    add(num) {
        // Add number to the data structure
        this.idToNum.set(this.latestId, num);
        if (this.numToCount.has(num)) {
            this.numToCount.set(num, this.numToCount.get(num) + 1);
        } else {
            this.numToCount.set(num, 1);
        }
        this.latestId += 1;

        // If exceeded capacity, remove the number with the lowest id
        if (this.latestId > this.maxSize + 1) {
            const idToDelete = this.latestId - 1 - this.maxSize;
            const numToDelete = this.idToNum.get(idToDelete);
            this.idToNum.delete(idToDelete);

            this.numToCount.set(numToDelete, this.numToCount.get(numToDelete) - 1);
            if (this.numToCount.get(numToDelete) == 0) {
                this.numToCount.delete(numToDelete);
            }
        }
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
    const readline = require('readline');
    const fs = require('fs');
    const readInterface = readline.createInterface({
        input: fs.createReadStream('data.in'),
        output: null,
        console: false
    });
    readInterface.on('line', function(line) {
        num = parseInt(line);
        if (!sizableMap.isCompliant(num)) {
            console.log(num);
        }
        sizableMap.add(num);
    });
}

const PREAMBLE_SIZE = 25;
let sizableMap = new SizableMap(PREAMBLE_SIZE);
readInput();
