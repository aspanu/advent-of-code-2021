
fun main() {
    println(day6Part1(day6TestInput))
    println(day6Part1(day6PuzzleInput))
    println(day6Part2(day6TestInput))
    println(day6Part2(day6PuzzleInput))
}

fun day6Part1(input: String): Int {
    val numberOfDays = 80

    var currentList = input.split(",").map { it.toInt() }
    for (i in 0 until numberOfDays) {
        val newList = mutableListOf<Int>()
        currentList.forEach { if (it == 0) { newList.add(6); newList.add(8) } else newList.add(it-1) }
        currentList = newList
    }
    return currentList.size
}

fun day6Part2(input: String): Long {
    val numberOfDays = 256

    val startingList = input.split(",").map { it.toInt() }
    var currentMap = mutableMapOf<Int, Long>()
    for (item in startingList) {
        currentMap[item] = (currentMap[item] ?: 0) + 1
    }
    for (i in 0 until numberOfDays) {
        val newMap = mutableMapOf<Int, Long>()
        currentMap.forEach {
            if (it.key == 0) {
                newMap[6] = (newMap[6] ?: 0) + it.value
                newMap[8] = it.value }
            else
                newMap[it.key - 1] = (newMap[it.key - 1] ?: 0) + it.value
        }
        currentMap = newMap
    }
    var sum = 0L
    currentMap.forEach { sum += it.value }
    return sum
}
// 26,984,457,539
const val day6TestInput = """3,4,3,1,2"""

const val day6PuzzleInput = """1,3,4,1,1,1,1,1,1,1,1,2,2,1,4,2,4,1,1,1,1,1,5,4,1,1,2,1,1,1,1,4,1,1,1,4,4,1,1,1,1,1,1,1,2,4,1,3,1,1,2,1,2,1,1,4,1,1,1,4,3,1,3,1,5,1,1,3,4,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,1,5,2,5,5,3,2,1,5,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,5,1,1,1,1,5,1,1,1,1,1,4,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,3,1,2,4,1,5,5,1,1,5,3,4,4,4,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,1,1,5,3,1,4,1,1,2,2,1,2,2,5,1,1,1,2,1,1,1,1,3,4,5,1,2,1,1,1,1,1,5,2,1,1,1,1,1,1,5,1,1,1,1,1,1,1,5,1,4,1,5,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,5,4,5,1,1,1,1,1,1,1,5,1,1,3,1,1,1,3,1,4,2,1,5,1,3,5,5,2,1,3,1,1,1,1,1,3,1,3,1,1,2,4,3,1,4,2,2,1,1,1,1,1,1,1,5,2,1,1,1,2"""