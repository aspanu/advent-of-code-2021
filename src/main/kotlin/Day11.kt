
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
    val octopusLocations = ingestInput(input)
    var numberOfFlashes = 0

    val numSteps = 100
    for (step in 1..numSteps) {
        // In each step, go through the set of all octopuses multiple times
        // In each iteration, go through the whole list and flash all octopuses that are >=9
        // And haven't yet flashed this step
        for (row in octopusLocations.indices) {
            for (col in octopusLocations[row].indices) {
                octopusLocations[row][col] = Pair(octopusLocations[row][col].first + 1, octopusLocations[row][col].second)
            }
        }

        var someoneFlashed = true
        while (someoneFlashed) {
            var someoneFlashedThisTime = false
            for (row in octopusLocations.indices) {
                for (col in octopusLocations[row].indices) {
                    if (octopusLocations[row][col].first > 9 && octopusLocations[row][col].second < step) {
                        octopusFlashed(octopusLocations, row, col, step)
                        someoneFlashedThisTime = true
                        numberOfFlashes++
                    }
                }
            }
            someoneFlashed = someoneFlashedThisTime
        }

        // Reset all the octopuses that flashed this step back to 0
        for (row in octopusLocations.indices) {
            for (col in octopusLocations[row].indices) {
                if (octopusLocations[row][col].second == step) {
                    octopusLocations[row][col] = Pair(0, step)
                }
            }
        }
    }

    return numberOfFlashes
}

fun octopusFlashed(locations: List<MutableList<Pair<Int, Int>>>, row: Int, col: Int, step: Int) {
    for (dRow in -1..1) {
        for (dCol in -1..1) {
            val newRow = row + dRow
            val newCol = col + dCol
            if (dRow == 0 && dCol == 0) {
                locations[newRow][newCol] = Pair(locations[newRow][newCol].first, step)
            } else {
                if (newRow >= 0 && newRow < locations.size && newCol >= 0 && newCol < locations[0].size) {
                    locations[newRow][newCol] = Pair(locations[newRow][newCol].first + 1 , locations[newRow][newCol].second)
                }
            }
        }
    }
}

fun ingestInput(input: String): List<MutableList<Pair<Int, Int>>> {
    return input.split("\n").map { s -> s.toCharArray().map { Pair(it.digitToInt(), 0) }.toMutableList() }.toList()
}

fun day11Part2(input: String): Int {
    // Go through the entire grid and keep track of all points which flashed during one step
    // Add one for all points that flashed to their surroundings
    // Go through the entire grid again and gather all points that flashed
    // Repeat until no more points flash
    // If a point flashed, reset it to 0 before the next turn
    val octopusLocations = ingestInput(input)

    val numSteps = 1000
    for (step in 1..numSteps) {
        // In each step, go through the set of all octopuses multiple times
        // In each iteration, go through the whole list and flash all octopuses that are >=9
        // And haven't yet flashed this step
        for (row in octopusLocations.indices) {
            for (col in octopusLocations[row].indices) {
                octopusLocations[row][col] = Pair(octopusLocations[row][col].first + 1, octopusLocations[row][col].second)
            }
        }

        var someoneFlashed = true
        var numberOfFlashes = 0
        while (someoneFlashed) {
            var someoneFlashedThisTime = false
            for (row in octopusLocations.indices) {
                for (col in octopusLocations[row].indices) {
                    if (octopusLocations[row][col].first > 9 && octopusLocations[row][col].second < step) {
                        octopusFlashed(octopusLocations, row, col, step)
                        someoneFlashedThisTime = true
                        numberOfFlashes++
                    }
                }
            }
            someoneFlashed = someoneFlashedThisTime
        }
        if (numberOfFlashes == octopusLocations.size * octopusLocations[0].size) {
            // Everything flashed, so return the step that this occurred at
            return step
        }

        // Reset all the octopuses that flashed this step back to 0
        for (row in octopusLocations.indices) {
            for (col in octopusLocations[row].indices) {
                if (octopusLocations[row][col].second == step) {
                    octopusLocations[row][col] = Pair(0, step)
                }
            }
        }
    }

    return -1
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