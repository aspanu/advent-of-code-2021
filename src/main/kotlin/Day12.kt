
fun main() {
//    println(day12Part1(day12TestInput))
//    println(day12Part1(day12TestInput2))
//    println(day12Part1(day12TestInput3))
//    println(day12Part1(day12PuzzleInput))
    println(day12Part2(day12TestInput))
    println(day12Part2(day12TestInput2))
    println(day12Part2(day12TestInput3))
    println(day12Part2(day12PuzzleInput))
}

fun day12Part1(input: String): Int {
    // Construct the graph from the input into a map of string to set<string>
    // Start at 'start' and then start building paths, by going to all children that haven't been visited
    // Each time you visit a child that is lowercase, add that to a set of visited nodes

    val graph = mutableMapOf<String, MutableSet<String>>()
    input.split("\n").forEach {
        val fromNode = it.split('-')[0]
        val toNode = it.split('-')[1]
        if (!graph.containsKey(fromNode)) {
            graph[fromNode] = mutableSetOf()
        }
        graph[fromNode]!!.add(toNode)
        // Make the reverse link as well
        if (fromNode != "start" && toNode != "end") {
            if (!graph.containsKey(toNode)) {
                graph[toNode] = mutableSetOf()
            }
            graph[toNode]!!.add(fromNode)
        }
    }
    var numberPaths = 0
    val neighborsToVisit = graph["start"]!!.toList()
    val visited = mutableSetOf<String>()
    for (neighbor in neighborsToVisit) {
        numberPaths += lookForEnd(graph, visited, 0, neighbor)
    }

    return numberPaths
}

fun lookForEnd(graph: Map<String, MutableSet<String>>, visited: Set<String>, numPaths: Int, currentNode: String): Int {
    if (currentNode == "end") {
        return numPaths+1
    }
    val currentPathSet = visited.toMutableSet()
    if (currentNode[0].isLowerCase()) {
        currentPathSet.add(currentNode)
    }
    var pathsHere = 0
    for (node in graph[currentNode]!!) {
        if (node in currentPathSet) {
            continue
        }
        pathsHere += lookForEnd(graph, currentPathSet, numPaths, node)
    }
    return pathsHere
}

fun day12Part2(input: String): Int {
    val graph = mutableMapOf<String, MutableSet<String>>()
    input.split("\n").forEach {
        val fromNode = it.split('-')[0]
        val toNode = it.split('-')[1]
        if (!graph.containsKey(fromNode)) {
            graph[fromNode] = mutableSetOf()
        }
        graph[fromNode]!!.add(toNode)
        // Make the reverse link as well
        if (fromNode != "start" && toNode != "end") {
            if (!graph.containsKey(toNode)) {
                graph[toNode] = mutableSetOf()
            }
            graph[toNode]!!.add(fromNode)
        }
    }
    var numberPaths = 0
    val neighborsToVisit = graph["start"]!!.toList()
    val visited = mutableMapOf<String, Int>()
    for (neighbor in neighborsToVisit) {
        numberPaths += lookForEnd2(graph, visited, 0, neighbor, listOf("start"))
    }

    return numberPaths
}

fun lookForEnd2(graph: Map<String, MutableSet<String>>, visited: Map<String, Int>, numPaths: Int, currentNode: String, currentPath: List<String>): Int {
    if (currentNode == "end") {
//        println(currentPath)
        return numPaths + 1
    }
    if (currentNode == "start") {
        return 0
    }
    val currentPathVisited = visited.toMutableMap()
    if (currentNode[0].isLowerCase()) {
        currentPathVisited[currentNode] = (currentPathVisited[currentNode] ?: 0) + 1
    }
    var pathsHere = 0
    val newPath = currentPath.toMutableList()
    newPath.add(currentNode)
    for (node in graph[currentNode]!!) {
        if (currentPathVisited.containsValue(2) && (currentPathVisited[node] ?: 0) >= 1) {
            continue
        }
        pathsHere += lookForEnd2(graph, currentPathVisited, numPaths, node, newPath)
    }
    return pathsHere
}

const val day12TestInput = """start-A
start-b
A-c
A-b
b-d
A-end
b-end"""

const val day12TestInput2 = """dc-end
start-HN
start-kj
start-dc
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc"""

const val day12TestInput3 = """fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW"""

const val day12PuzzleInput = """by-TW
start-TW
fw-end
QZ-end
JH-by
start-ka
ka-by
end-JH
QZ-cv
vg-TI
by-fw
QZ-by
JH-ka
JH-vg
vg-fw
TW-cv
QZ-vg
ka-TW
ka-QZ
JH-fw
vg-hu
start-cv
by-cv
ka-cv"""