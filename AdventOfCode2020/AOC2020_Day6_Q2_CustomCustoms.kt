import java.io.File

val EMPTY_STRING: String = ""
val GROUP_DELIMITER: String = " "
val INDIVIDUAL_DELIMITER: String = "."

fun main(args: Array<String>) {
    val groups: List<String> = readInputs()
    val sets: List<Set<Char>> = groups.map{ toSet(it) }
    val counts: List<Int> = sets.map{ it.size }
    println(counts.toIntArray().sum())
}

fun toSet(group: String): Set<Char> {
    val responses = group.split(INDIVIDUAL_DELIMITER)
    val questionsAllAnswered = mutableSetOf<Char>()
    val firstResponse = responses[0]
    firstResponse.forEach {
        question ->
        run {
            val isAllAnswered = responses.all { it.contains(question) }
            if (isAllAnswered) questionsAllAnswered.add(question)
        }
    }
    return questionsAllAnswered
}

fun readInputs(): List<String> {
    var input = EMPTY_STRING
    File("data.in").inputStream().bufferedReader().forEachLine {
        input = when (it) {
            EMPTY_STRING -> input.dropLast(1).plus(GROUP_DELIMITER)
            else -> input.plus(it) + INDIVIDUAL_DELIMITER
        }
    }
    return input.dropLast(1).split(GROUP_DELIMITER)
}
