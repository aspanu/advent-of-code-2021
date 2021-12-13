
fun main() {
    println(day12Part1(day12TestInput))
    println(day12Part1(day12PuzzleInput))
    println(day12Part2(day12TestInput))
    println(day12Part2(day12PuzzleInput))
}

fun day12Part1(input: String): Int {
    // Construct the graph from the input, where each node can have any number of children nodes
    //
    return 0
}

fun day12Part2(input: String): Int {
    return 0
}

const val day12TestInput = """start-A
start-b
A-c
A-b
b-d
A-end
b-end"""

const val day12TestInput2 = """dc-end
HN-start
start-kj
dc-start
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
ka-start
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
cv-start
by-cv
ka-cv"""