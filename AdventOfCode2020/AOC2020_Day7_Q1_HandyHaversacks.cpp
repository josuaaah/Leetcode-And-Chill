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
    ::map<string, vector<string>> colourMap;

public:
    ColourGraph() {}

    void addContainerColour(string containerColour) {
        vector<string> containedColours;
        colourMap.insert(pair<string, vector<string>>(containerColour, containedColours));
    }

    void addContainedColour(string containerColour, string containedColour) {
        colourMap[containerColour].push_back(containedColour);
    }

    void printColourGraph() {
        map<string, vector<string>>::iterator it;
        for (it = colourMap.begin(); it != colourMap.end(); it++) {
            string container = it -> first;
            cout << container << ": ";
            vector<string> contained = it -> second;
            for (string colour : contained) {
                cout << colour << ", ";
            }
            cout << "\n";
        }
    }

    vector<string> getContainedColours(string containerColour) {
        return colourMap[containerColour];
    }

    // Breadth-first search (BFS)
    bool canContain(string containerColour, string containedColour) {
        if (containerColour == containedColour) {
            return false;
        }

        list<string> frontier;
        set<string> explored;
        frontier.push_back(containerColour);
        explored.insert(containerColour);

        while (!frontier.empty()) {
            string colour = frontier.front();
            frontier.pop_front();
            for (string contentColour : getContainedColours(colour)) {
                if (contentColour == containedColour) {
                    return true;
                } else {
                    bool isNotExplored = explored.find(contentColour) == explored.end();
                    if (isNotExplored) {
                        explored.insert(contentColour);
                        frontier.push_back(contentColour);
                    }
                }
            }
        }
        return false;
    }

    int getNumBagsCanContain(string containedColour) {
        int num = 0;
        map<string, vector<string>>::iterator it;
        for (it = colourMap.begin(); it != colourMap.end(); it++) {
            string containerColour = it -> first;
            if (canContain(containerColour, containedColour)) {
                num++;
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
    string BAG_DELIMITER = ", ";
    string CONTAINS_DELIMITER = " contain ";

    string container = rule.substr(0, rule.find(CONTAINS_DELIMITER));
    string containerColour = container.substr(0, container.find(BAGS_NOUN));
    cg.addContainerColour(containerColour);

    string contained = rule.substr(rule.find(CONTAINS_DELIMITER) 
        + CONTAINS_DELIMITER.length());
    contained.pop_back();
    vector<string> requirements = split(contained, BAG_DELIMITER);
    for (string requirement : requirements) {
        int numRequired = requirement.at(0) - '0';
        string containedColour = requirement
            .substr(0, requirement.find(BAG_NOUN)).substr(2);
        cg.addContainedColour(containerColour, containedColour);
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
    int num = cg.getNumBagsCanContain("shiny gold");
    cout << num << '\n';
    return 0;
};