## Introduction

Subset Sum problem is defined as “Given a set of numbers and a target T, is there any subset of them whose sum is equal to T?”. It can be regarded as a special case of Knapsack problem and has important application in complexity theory and cryptography. In this project, the subset sum problem is systematically studied and four different techniques are implemented to solve it: Exhaustive, Greedy, ILP and Local Search. To see a comprehensive analysis of the Subset Sum, please take a look at the final report under the name "*SS Report - Bocanegra and Zhou.pdf*".

**NOTE** The language used in the project is Java. The environment needs to be configured with JDK 1.8 or later.

## Project hierarchy

- **main**: Main and unique executable in the project. The code first generate instances calling the **Benchmark** class. Upon configuring the solver to use, it attempts to find the best solution of the Subset Problem. At the end, some metrics are provided regarding the performance, in terms of the execution time, accuracy and other parameters of interest.
- **Benchmark**: The objective of our benchmark is to generate instances of different sizes that contain elements (positive integers) within a specified range. 
- **ExhaustiveSolver**: The exhaustive algorithm analyzes every single possible subset within the set and compares the sum of the elements within it with a target value T. Our implementation makes use of the binary notation, where 0/1 shows if the element is being considered or not in the current subset respectively. The class calls *BinaryGenerator*.
- **GreedySolver**: The high success rate is achieved at the expense of the unreasonable execution time. Since the algorithm not only has to find the solution to a problem but also solve it within a reasonable time, we investigate the applicability of the greedy approach for the Subset Sum
- **QuickSort**: The main idea of local search is heuristically generating neighboring solutions from the initial solution, and probabilistically decides whether moving to a new solution or not. The above process is repeated until the system reaches a state that is good enough for the application, or until a given computation budget has been exhausted. Different local search algorithms have different ways of deciding whether to accept a certain neighbor solution or not. In this experiment, we choose two local search heuristics: gradient (steepest) descent and simulated annealing. The difference between the two algorithms is that the gradient descent always chooses the best from neighbor solutions, while the simulated annealing may accept bad solution with a certain probability.

## Contact

Carlos Bocanegra  
PhD Candidate  
Electrical and Computer Engineering (EECE)  
Northeastern University  
bocanegrac@coe.neu.edu

Fan Zhou  
PhD Candidate  
Electrical and Computer Engineering (EECE)  
Northeastern University  
zhou.fan1@husky.neu.edu

## Acknowledgements

We thank professor Waleed Meleis for his guidance and support throughout the project. 