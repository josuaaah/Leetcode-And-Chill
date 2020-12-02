"""
Application question for the Credit Suisse Coding Challenge 2020.

Given an array of integers, return the smallest set of indices 
of numbers such that they add up to a target number. You may not 
use the same element twice.
 
Examples:
 
[1,2,6,3,17,82,23,234] -> 26
Solution [3,6]
 
[1,2,6,3,17,82,23,234] -> 40
Solution [4,6]
 
[1,2,6,3,17,82,23,234] -> 23
Solution [6]
"""

class SumsProblem:

    shortest = None

    def __init__(self, array, target):
        self.array = array
        self.target = target

        '''A hashtable containing tuples as keys, mapped to a boolean
        value which indicates if the tuple's elements sum to target.'''
        self.memo = {}

        '''A hashtable containing the array integers as keys, mapped to
        their respective indices within the array'''
        self.indices = {}
        for i in range(len(array)):
            self.indices[array[i]] = i


    def is_valid_subset(self, arr):
        """
        Checks if the sum of all the elements in a subset of the array is equal to target.
        :param arr: A subset of the array of integers.
        :return: True if the sum of all integers in the tuple is equal to target.
        """
        return sum(arr) == self.target


    def is_next_shortest(self, tuple):
        """
        Checks if we have found the next shortest valid subset.
        :param tuple: A subset of the array of integers.
        :return: True if tuple is the next shortest valid subset.
        """
        return self.shortest is None or (len(tuple) < len(self.shortest))


    def process_subsets(self, arr):
        """
        Checks if the array and each of its possible subsets sum to target,
        and stores these results as key-value pairs in the memo. At the end of
        its execution, the shortest valid subset is stored as an attribute in the SumsProblem.
        :param arr: A subset of the array of integers.
        :return: None
        """

        # Process the full array first
        tup = tuple(arr)
        if tup not in self.memo:
            self.memo[tup] = self.is_valid_subset(arr)
            if self.memo[tup] and self.is_next_shortest(tup):
                self.shortest = tup

        length = len(arr)
        if length == 1:
            return

        for i in range(length):
            copy = arr.copy()

            # Process one of the elements on its own
            singleton = tuple([copy.pop(i)])

            if singleton not in self.memo:
                self.memo[singleton] = self.is_valid_subset(singleton)
                if self.memo[singleton] and self.is_next_shortest(singleton):
                    self.shortest = singleton

            # Process the subsets of the remaining elements
            remain = copy
            self.process_subsets(remain)


    def to_indices(self, tuple):
        """
        Maps all the elements in the tuple into their respective indices
        with respect to the problem array.
        :param tuple: The shortest possible valid subset found.
        :return: A list containing the indices of the elements with respect
        to the problem array.
        """
        if tuple is None:
            return None
        index_list = []
        for i in range(len(tuple)):
            index_list.append(self.indices[tuple[i]])
        return index_list


    def solve(self):
        """Solves the Sums Problem."""
        if self.target == 0:
            return []
        self.process_subsets(self.array)
        return self.to_indices(self.shortest)


array = [1,2,6,3,17,82,23,234]
target = 26
sums_problem = SumsProblem(array, target)
print(sums_problem.solve())
