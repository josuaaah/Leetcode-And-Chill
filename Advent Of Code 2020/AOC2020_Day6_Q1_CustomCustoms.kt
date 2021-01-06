import java.io.File

val EMPTY_STRING: String = ""
val SPACE_STRING: String = " "

fun main(args: Array<String>) {
    val groups: List<String> = readInputs()
    val sets: List<Set<Char>> = groups.map{ toSet(it) }
    val counts: List<Int> = sets.map{ it.size }
    println(counts.toIntArray().sum())
}

fun toSet(group: String): Set<Char> {
    val questions = mutableSetOf<Char>()
    group.forEach {
        questions.add(it)
    }
    return questions
}

fun readInputs(): List<String> {
    var input = EMPTY_STRING
    File("data.in").inputStream().bufferedReader().forEachLine {
        input = when (it) {
            EMPTY_STRING -> input.plus(SPACE_STRING)
            else -> input.plus(it)
        }
    }
    return input.split(SPACE_STRING)
}
