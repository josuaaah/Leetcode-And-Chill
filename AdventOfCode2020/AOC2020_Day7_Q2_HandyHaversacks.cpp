#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <map>
#include <set>
#include <list>

using namespace std;

/*
    Make a graph where each vertex V represents a colour.
    Each directed edge E_(c1,c2) denotes that a bag with
    colour c1 can contain at least one bag of colour c2.
*/
class ColourGraph {
private:
    // Adjacency list implementation
    ::map<string, vector<pair<int, string>>> colourMap;

public:
    ColourGraph() {}

    void addContainerColour(string containerColour) {
        vector<pair<int, string>> requirements;
        colourMap.insert(make_pair(containerColour, requirements));
    }

    void addContainedColour(string containerColour, int numRequired, string containedColour) {
        colourMap[containerColour].push_back(make_pair(numRequired, containedColour));
    }

    void printColourGraph() {
        map<string, vector<pair<int, string>>>::iterator it;
        for (it = colourMap.begin(); it != colourMap.end(); it++) {
            string container = it -> first;
            cout << container << ": ";
            vector<pair<int, string>> requirements = it -> second;
            for (pair<int, string> requirement : requirements) {
                cout << to_string(requirement.first) + " " + requirement.second << ", ";
            }
            cout << "\n";
        }
    }

    vector<pair<int, string>> getRequirements(string containerColour) {
        return colourMap[containerColour];
    }

    bool containNoOtherBags(string containerColour) {
        return getRequirements(containerColour).empty();
    }

    int getNumBagsRequired(string containerColour) {
        int num = 0;
        for (pair<int, string> requirement : getRequirements(containerColour)) {
            int numRequired = requirement.first;
            string containerColour = requirement.second;
            if (containNoOtherBags(containerColour)) {
                num += numRequired;
            } else {
                num += numRequired + numRequired * getNumBagsRequired(containerColour);
            }
        }
        return num;
    }
};

// Declare a global ColourGraph
ColourGraph cg;

vector<string> split(string fullString, string delimiter) {
    vector<string> tokens;
    auto start = 0U;
    auto end = fullString.find(delimiter);
    while (end != string::npos) {
        tokens.push_back(fullString.substr(start, end - start));
        start = end + delimiter.length();
        end = fullString.find(delimiter, start);
    }
    tokens.push_back(fullString.substr(start, end));
    return tokens;
}

void parseRule(string rule) {
    string BAG_NOUN = " bag";
    string BAGS_NOUN = " bags";
    string NO_OTHER_BAGS_NOUN = "no other bags";
    string BAG_DELIMITER = ", ";
    string CONTAINS_DELIMITER = " contain ";

    string container = rule.substr(0, rule.find(CONTAINS_DELIMITER));
    string containerColour = container.substr(0, container.find(BAGS_NOUN));
    cg.addContainerColour(containerColour);

    string contained = rule.substr(rule.find(CONTAINS_DELIMITER) 
        + CONTAINS_DELIMITER.length());
    contained.pop_back();

    if (contained != NO_OTHER_BAGS_NOUN) {
        vector<string> requirements = split(contained, BAG_DELIMITER);
        for (string requirement : requirements) {
            int numRequired = requirement.at(0) - '0';
            string containedColour = requirement
                .substr(0, requirement.find(BAG_NOUN)).substr(2);
            cg.addContainedColour(containerColour, numRequired, containedColour);
        }
    }
};

void readInput() {
    ifstream myfile;
    myfile.open("data.in");
    if (myfile.is_open()) {
        string rule;
        while (getline(myfile, rule)) {
            parseRule(rule);
        }
    }
    myfile.close();
};

int main() {
    readInput();
    cout << cg.getNumBagsRequired("shiny gold") << "\n";
    return 0;
};
