fun main() {
//    println(day16Part1("D2FE28"))
//    println(day16Part1("38006F45291200"))
//    println(day16Part1("EE00D40C823060"))
//    println(day16Part1(day16TestInput1))
//    println(day16Part1(day16TestInput2))
//    println(day16Part1(day16TestInput3))
//    println(day16Part1(day16TestInput4))
    println(day16Part1(day16PuzzleInput))
    println(day16Part2(day16TestInput1))
    println(day16Part2(day16PuzzleInput))
}



fun day16Part1(input: String): Int {
    // Take the input and build the binary string one character at a time, then read the string
    // 3 options:
    // Literal, which is type 4 (i.e. 100), which has some number of packets behind it, grouped into
    // groups of 5
    // Operator, which are all other types, which specify the size of the underlying packet (if length
    // type 0) or the number of underlying packets (if length type 1)
    var binaryInput = ""
    for (char in input) {
        binaryInput += hexToBin[char.toString()] ?: ""
    }

    val packets = extractAllPackets(binaryInput)

    return packets.sumOf { it.version }
}

// Initial entry point for recursive packet reading
// There can only be one packet at the top level, so read that packet
private fun extractAllPackets(binaryInput: String): List<Packet> {
    val packets = mutableListOf<Packet>()

    val (additionalPackets, _) = when (sniffPacketType(binaryInput)) {
        PacketType.DATA -> extractDataPacket(binaryInput.substring(0))
        PacketType.OPERATOR -> extractOperatorPackets(binaryInput.substring(0))
    }
    packets.addAll(additionalPackets)
    return packets
}

fun extractOperatorPackets(binaryInput: String): Pair<List<Packet>, Int> {
    val packets = mutableListOf<Packet>()
    var offset = 0
    val version = binaryInput.substring(offset, offset + 3).toInt(2)
    offset += 3
    val type = binaryInput.substring(offset, offset + 3).toInt(2)
    offset += 3

    val lengthType = binaryInput.substring(offset, offset + 1)
    offset += 1

    if (lengthType == "0") {
        val lengthOfPackets = binaryInput.substring(offset, offset + 15).toInt(2)
        offset += 15
        packets.add(Packet(version, type, subPacket = binaryInput.substring(offset, offset + lengthOfPackets)))
        packets.addAll(extractOperatorBinLengthPackets(binaryInput.substring(offset), lengthOfPackets))
        offset += lengthOfPackets
    } else {
        val numPackets = binaryInput.substring(offset, offset + 11).toInt(2)
        offset += 11
        packets.add(Packet(version, type, numSubPackets = numPackets))
        val (newPackets, additionalOffset) = extractOperatorNumPackets(binaryInput.substring(offset), numPackets)
        packets.addAll(newPackets)
        offset += additionalOffset
    }
    return Pair(packets, offset)
}

fun extractOperatorBinLengthPackets(binaryInput: String, lengthOfPackets: Int): Collection<Packet> {
    var offset = 0
    val packets = mutableListOf<Packet>()
    while (offset < lengthOfPackets) {
        val packetType = sniffPacketType(binaryInput.substring(offset))
        val (newPackets, additionalOffset) = when (packetType) {
            PacketType.DATA -> extractDataPacket(binaryInput.substring(offset))
            PacketType.OPERATOR -> extractOperatorPackets(binaryInput.substring(offset))
        }
        packets.addAll(newPackets)
        offset += additionalOffset
    }
    return packets
}

fun extractOperatorNumPackets(binaryInput: String, numPackets: Int): Pair<Collection<Packet>, Int> {
    var offset = 0
    val packets = mutableListOf<Packet>()
    var numFound = 0
    while (numFound < numPackets) {
        val packetType = sniffPacketType(binaryInput.substring(offset))
        val (newPackets, additionalOffset) = when (packetType) {
            PacketType.DATA -> extractDataPacket(binaryInput.substring(offset))
            PacketType.OPERATOR -> extractOperatorPackets(binaryInput.substring(offset))
        }
        numFound++
        offset += additionalOffset
        packets.addAll(newPackets)
    }
    return Pair(packets, offset)
}

private fun extractDataPacket(binaryInput: String): Pair<Collection<Packet>, Int> {
    // Reach data packet and return the packet and how long it was
    var offset = 0
    var dataNotDone = true
    var binaryDataString = ""
    val version = binaryInput.substring(offset, offset + 3).toInt(2)
    offset += 3
    val type = binaryInput.substring(offset, offset + 3).toInt(2)
    offset += 3

    while (dataNotDone) {
        val nextPart = binaryInput.substring(offset, offset + 5)
        offset += 5
        dataNotDone = nextPart[0] == '1' // Check if the first is a 1 and we should continue
        binaryDataString += nextPart.substring(1)
    }
    val binaryData = binaryDataString.toLong(2)
    return Pair(listOf(Packet(version, type, binaryData)), offset)
}

fun sniffPacketType(binaryInput: String): PacketType {
    val type = binaryInput.substring(3, 6).toInt(2)
    return if (type == 4) PacketType.DATA
    else PacketType.OPERATOR
}

enum class PacketType {
    DATA, OPERATOR
}


data class Packet (val version: Int, val type: Int, val data: Long = 0L, val subPacket : String = "", val numSubPackets: Int = 0)

fun day16Part2(input: String): Int {
    return 0
}

val hexToBin = mapOf("0" to "0000",
    "1" to "0001",
    "2" to "0010",
    "3" to "0011",
    "4" to "0100",
    "5" to "0101",
    "6" to "0110",
    "7" to "0111",
    "8" to "1000",
    "9" to "1001",
    "A" to "1010",
    "B" to "1011",
    "C" to "1100",
    "D" to "1101",
    "E" to "1110",
    "F" to "1111",
)

const val day16TestInput1 = """8A004A801A8002F478"""
const val day16TestInput2 = """620080001611562C8802118E34"""
const val day16TestInput3 = """C0015000016115A2E0802F182340"""
const val day16TestInput4 = """A0016C880162017C3686B18A3D4780"""

const val day16PuzzleInput = """2052ED9802D3B9F465E9AE6003E52B8DEE3AF97CA38100957401A88803D05A25C1E00043E1545883B397259385B47E40257CCEDC7401700043E3F42A8AE0008741E8831EC8020099459D40994E996C8F4801CDC3395039CB60E24B583193DD75D299E95ADB3D3004E5FB941A004AE4E69128D240130D80252E6B27991EC8AD90020F22DF2A8F32EA200AC748CAA0064F6EEEA000B948DFBED7FA4660084BCCEAC01000042E37C3E8BA0008446D8751E0C014A0036E69E226C9FFDE2020016A3B454200CBAC01399BEE299337DC52A7E2C2600BF802B274C8848FA02F331D563B3D300566107C0109B4198B5E888200E90021115E31C5120043A31C3E85E400874428D30AA0E3804D32D32EED236459DC6AC86600E4F3B4AAA4C2A10050336373ED536553855301A600B6802B2B994516469EE45467968C016D004E6E9EE7CE656B6D34491D8018E6805E3B01620C053080136CA0060801C6004A801880360300C226007B8018E0073801A801938004E2400E01801E800434FA790097F39E5FB004A5B3CF47F7ED5965B3CF47F7ED59D401694DEB57F7382D3F6A908005ED253B3449CE9E0399649EB19A005E5398E9142396BD1CA56DFB25C8C65A0930056613FC0141006626C5586E200DC26837080C0169D5DC00D5C40188730D616000215192094311007A5E87B26B12FCD5E5087A896402978002111960DC1E0004363942F8880008741A8E10EE4E778FA2F723A2F60089E4F1FE2E4C5B29B0318005982E600AD802F26672368CB1EC044C2E380552229399D93C9D6A813B98D04272D94440093E2CCCFF158B2CCFE8E24017CE002AD2940294A00CD5638726004066362F1B0C0109311F00424CFE4CF4C016C004AE70CA632A33D2513004F003339A86739F5BAD5350CE73EB75A24DD22280055F34A30EA59FE15CC62F9500"""

