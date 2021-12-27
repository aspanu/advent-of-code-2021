
fun main() {
    println(day14Part1(day14TestInput))
    println(day14Part1(day14PuzzleInput))
    println(day14Part2(day14TestInput, 10))
    println(day14Part2(day14PuzzleInput, 10))
    println(day14Part2(day14TestInput, 40))
    println(day14Part2(day14PuzzleInput, 40))

}

fun day14Part1(input: String): Int {
    val dictionary = mutableMapOf<Pair<Char, Char>, Char>()
    var currentPolymer = ""
    input.split("\n").forEach { line ->
        if (line.contains("->")) {
            val dictionaryLine = line.split(" -> ")
            dictionary[Pair(dictionaryLine[0][0], dictionaryLine[0][1])] = dictionaryLine[1].first()
        } else {
            currentPolymer = line
        }
    }
    for (step in 1..10) {
        var firstChar = currentPolymer[0]
        var newPolymer = ""
        for (charPoint in 1 until currentPolymer.length) {
            val secondChar = currentPolymer[charPoint]
            newPolymer += firstChar
            newPolymer += dictionary[Pair(firstChar, secondChar)] ?: '!'
            firstChar = secondChar
        }
        newPolymer += firstChar // Add the last character in at the end
        currentPolymer = newPolymer
    }

    // Frequency analysis
    val charFreq = mutableMapOf<Char, Int>()
    for (char in currentPolymer) {
        charFreq[char] = (charFreq[char] ?: 0) + 1
    }

    return charFreq.maxOf { it.value } - charFreq.minOf { it.value }
}

fun day14Part2(input: String, steps: Int): Long {
    val dictionary = mutableMapOf<Pair<Char, Char>, Char>()
    var bpFrequencies = mutableMapOf<Pair<Char, Char>, Long>()
    var firstChar = 'a'
    input.split("\n").forEach { line ->
        if (line.contains("->")) {
            val dictionaryLine = line.split(" -> ")
            dictionary[Pair(dictionaryLine[0][0], dictionaryLine[0][1])] = dictionaryLine[1].first()
        } else {
            // Break starting polymer into its bp frequencies
            firstChar = line[0]
            for (char in line.substring(1)) {
                bpFrequencies[Pair(firstChar, char)] = (bpFrequencies[Pair(firstChar, char)] ?: 0) + 1
                firstChar = char
            }
        }
    }

    for (step in 1..steps) {
        // Go through each BP frequency, figure out what each BP breaks down into and then add them
        // the same number of times
        val newBpFreqs = mutableMapOf<Pair<Char, Char>, Long>()
        for (basePair in bpFrequencies.keys) {
            val newChar = dictionary[basePair] ?: '!'
            newBpFreqs[Pair(basePair.first, newChar)] = (newBpFreqs[Pair(basePair.first, newChar)] ?: 0) + (bpFrequencies[basePair] ?: 0)
            newBpFreqs[Pair(newChar, basePair.second)] = (newBpFreqs[Pair(newChar, basePair.second)] ?: 0) + (bpFrequencies[basePair] ?: 0)
        }
        bpFrequencies = newBpFreqs
    }

    val charFreqs = mutableMapOf<Char, Long>()
    for (basePair in bpFrequencies.keys) {
        charFreqs[basePair.first] = (charFreqs[basePair.first] ?: 0) + (bpFrequencies[basePair] ?: 0)
    }
    charFreqs[firstChar] = (charFreqs[firstChar] ?: 0) + 1

    return charFreqs.maxOf { it.value } - charFreqs.minOf { it.value }
}

const val day14TestInput = """NNCB
CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C"""

const val day14PuzzleInput = """HBCHSNFFVOBNOFHFOBNO
HF -> O
KF -> F
NK -> F
BN -> O
OH -> H
VC -> F
PK -> B
SO -> B
PP -> H
KO -> F
VN -> S
OS -> B
NP -> C
OV -> C
CS -> P
BH -> P
SS -> P
BB -> H
PH -> V
HN -> F
KV -> H
HC -> B
BC -> P
CK -> P
PS -> O
SH -> N
FH -> N
NN -> P
HS -> O
CB -> F
HH -> F
SB -> P
NB -> F
BO -> V
PN -> H
VP -> B
SC -> C
HB -> H
FP -> O
FC -> H
KP -> B
FB -> B
VK -> F
CV -> P
VF -> V
SP -> K
CC -> K
HV -> P
NC -> N
VH -> K
PF -> P
PB -> S
BF -> K
FF -> C
FV -> V
KS -> H
VB -> F
SV -> F
HO -> B
FN -> C
SN -> F
OB -> N
KN -> P
BV -> H
ON -> N
NF -> S
OF -> P
NV -> S
VS -> C
OO -> C
BP -> H
BK -> N
CP -> N
PC -> K
CN -> H
KB -> B
BS -> P
KK -> P
SF -> V
CO -> V
CH -> P
FO -> B
FS -> F
VO -> H
NS -> F
KC -> H
VV -> K
NO -> P
OK -> F
PO -> V
FK -> H
OP -> H
PV -> N
CF -> P
NH -> K
SK -> O
KH -> P
HP -> V
OC -> V
HK -> F"""