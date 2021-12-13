
fun main() {
    println(day11Part1(day11TestInput))
    println(day11Part1(day11PuzzleInput))
    println(day11Part2(day11TestInput))
    println(day11Part2(day11PuzzleInput))
}

fun day11Part1(input: String): Int {
    // Go through the entire grid and keep track of all points which flashed during one step
    // Add one for all points that flashed to their surroundings
    // Go through the entire grid again and gather all points that flashed
    // Repeat until no more points flash
    // If a point flashed, reset it to 0 before the next turn

    return 0
}

fun day11Part2(input: String): Int {
    return 0
}

const val day11TestInput = """5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526"""

const val day11PuzzleInput = """2138862165
2726378448
3235172758
6281242643
4256223158
1112268142
1162836182
1543525861
1882656326
8844263151"""