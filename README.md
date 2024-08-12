# Graph Operations Java Application

This Java application performs various operations on graph structures, including parsing DOT files, managing nodes and edges, and visualizing graphs. It also supports search algorithms like Breadth-First Search (BFS) and Depth-First Search (DFS).

## Features

- **Graph Parsing**: Parse .dot files to create and manipulate graph structures.
- **Node and Edge Management**: Add and remove nodes and edges from the graph.
- **Visualization**: Export the graph to .dot files and images.
- **Search Algorithms**: Implement BFS and DFS for graph traversal.
- **Command-Line Interface**: Interact with the graph and perform operations via a command-line interface.

## How to Run

1. **Run the Application**:
   - Execute `src/main/application/Main.java` to access the command-line interface.

2. **Run Test Cases**:
   - Execute `test/myGraphClass.java` to run the test cases.

## Command-Line Interface

Use the following commands to interact with the application:

- **Parse .dot file**: `src/main/java/testGraph.dot`
- **Print graph details**: `0`
- **Output graph details to file**: `1` followed by `src/main/java/testGraph.txt`
- **Add a node**: `2` followed by `node0`
- **Add multiple nodes**: `3` followed by `node1,node2,node3`
- **Add an edge**: `4` followed by `white` and `node3`
- **Remove a node**: `5` followed by `yellow`
- **Remove multiple nodes**: `6` followed by `node1,black`
- **Remove an edge**: `7` followed by `blue` and `cyan`
- **Output graph to .dot file**: `8` followed by `src/main/java/testGraph_o.dot`
- **Output graph to image**: `9` followed by `src/main/java/testGraph.png`
- **Exit**: `Q`
- **Perform BFS/DFS search**: `10` followed by `bfs` or `dfs`

## Features and Commits

- **Feature 1**: Parsing DOT files, printing details, and outputting to file.
  - Commit: [992acecb2f21363762b58f52b81f04d088277959](https://github.com/Divy2000/CSE464_2023_dpate164/commit/992acecb2f21363762b58f52b81f04d088277959)
  
- **Feature 2**: Adding/removing nodes.
  - Commit: [c2e5e0723c60eead8c52ad7ac50c373458055bff](https://github.com/Divy2000/CSE464_2023_dpate164/commit/c2e5e0723c60eead8c52ad7ac50c373458055bff)
  
- **Feature 3**: Adding/removing edges.
  - Commit: [4cfc172714d5db0b8b19bc522782d1f3f1fa26b0](https://github.com/Divy2000/CSE464_2023_dpate164/commit/4cfc172714d5db0b8b19bc522782d1f3f1fa26b0)
  
- **Feature 4**: Outputting to .dot file and image.
  - Commit: [f36ef90b4ae229af14958bf57da1d9c8179503c5](https://github.com/Divy2000/CSE464_2023_dpate164/commit/f36ef90b4ae229af14958bf57da1d9c8179503c5)
  
- **Final Testing**: [82b8581aa5068c9804c18beb58abe7cc01e21766](https://github.com/Divy2000/CSE464_2023_dpate164/commit/82b8581aa5068c9804c18beb58abe7cc01e21766)

- **Continuous Integration**: [669e2d62a7a94fbc0a038a561ab0c1f0aa705246](https://github.com/Divy2000/CSE464_2023_dpate164/commit/669e2d62a7a94fbc0a038a561ab0c1f0aa705246)

- **BFS Branch**: [bfs](https://github.com/Divy2000/CSE464_2023_dpate164/tree/bfs)
- **DFS Branch**: [dfs](https://github.com/Divy2000/CSE464_2023_dpate164/tree/dfs)

- **BFS Implementation**: [c65d58291562086c2838ff501920aae9e405d5bf](https://github.com/Divy2000/CSE464_2023_dpate164/commit/c65d58291562086c2838ff501920aae9e405d5bf)
- **DFS Implementation**: [b6c9a76f85e7a4d1f695e29629fe0a0943fe39b5](https://github.com/Divy2000/CSE464_2023_dpate164/commit/b6c9a76f85e7a4d1f695e29629fe0a0943fe39b5)
- **Merge BFS**: [119d64372419fbe526ec6756e19c5ca274fc16e6](https://github.com/Divy2000/CSE464_2023_dpate164/commit/119d64372419fbe526ec6756e19c5ca274fc16e6)
- **Merge DFS**: [0192df91cf231a6e8ce38f5ef6508d49a49af2bc](https://github.com/Divy2000/CSE464_2023_dpate164/commit/0192df91cf231a6e8ce38f5ef6508d49a49af2bc)

- **Template Design Pattern**: [a79a63ad9249bf99742825b2c7acda88e04ec72f](https://github.com/Divy2000/CSE464_2023_dpate164/commit/a79a63ad9249bf99742825b2c7acda88e04ec72f)

- **Strategy Design Pattern**: [e746659a58d4015f3e24ef76d6fa79e427f70aab](https://github.com/Divy2000/CSE464_2023_dpate164/commit/e746659a58d4015f3e24ef76d6fa79e427f70aab)

- **Random Walk**: [d7e93ca544637b9c1528202ade4cb161e1ccd803](https://github.com/Divy2000/CSE464_2023_dpate164/commit/d7e93ca544637b9c1528202ade4cb161e1ccd803)

## Pull Requests

- [Pull Request #9](https://github.com/Divy2000/CSE464_2023_dpate164/pull/9) - Merging Template and Strategy Patterns

## Merge Commit

- [Merge Commit](https://github.com/Divy2000/CSE464_2023_dpate164/commit/7360518dec1145f87e982e6ff47789019a6761e9)
