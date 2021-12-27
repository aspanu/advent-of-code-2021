import java.io.File

fun main() {
    generateDay(17)
    generateDay(18)
    generateDay(19)
    generateDay(20)
    generateDay(21)
    generateDay(22)
    generateDay(23)
    generateDay(24)
    generateDay(25)
}

fun generateDay(dayNum: Int): String {
    val generatedString = StringBuilder()
    generatedString.appendLine()
    generatedString.append("fun main() {").appendLine()
    generatedString.append("println(day${dayNum}Part1(day${dayNum}TestInput))").appendLine()
    generatedString.append("println(day${dayNum}Part1(day${dayNum}PuzzleInput))").appendLine()
    generatedString.append("println(day${dayNum}Part2(day${dayNum}PuzzleInput))").appendLine()
    generatedString.append("println(day${dayNum}Part2(day${dayNum}PuzzleInput))").appendLine()
    generatedString.append("}").appendLine()
    generatedString.appendLine()
    generatedString.append("fun day${dayNum}Part1(input: String): Int {").appendLine()
    generatedString.append("return 0").appendLine()
    generatedString.append("}").appendLine()
    generatedString.appendLine()
    generatedString.append("fun day${dayNum}Part2(input: String): Int {").appendLine()
    generatedString.append("return 0").appendLine()
    generatedString.append("}").appendLine()
    generatedString.appendLine()
    generatedString.append("const val day${dayNum}TestInput = \"\"\"\"\"\"").appendLine()
    generatedString.appendLine()
    generatedString.append("const val day${dayNum}PuzzleInput = \"\"\"\"\"\"").appendLine()
    generatedString.appendLine()

    val file = File("src/main/kotlin/Day${dayNum}.kt")
    file.writeText(generatedString.toString())
    return generatedString.toString()
}
