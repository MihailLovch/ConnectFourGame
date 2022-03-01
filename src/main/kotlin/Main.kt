package connectfour

fun main() {
    println("Connect Four")
    println("First player's name:")
    val firstName: String = readLine()!!
    println("Second player's name:")
    val secondName: String = readLine()!!
    println("Set the board dimensions (Rows x Columns) \nPress Enter for default (6 x 7)")
    var rows = 6
    var columns = 7
    var failed = true
    var input = readLine()!!
    while (input.isNotEmpty() && failed) {
        failed = false
        if (!input.matches("\\s*[0-9]+\\s*[xX]\\s*[0-9]+\\s*".toRegex())){
            println("Invalid input")
            failed = true
        }else {
            val rowsTest = input.split("x","X").first().replace(" ","").replace("\t","").toInt()
            val columnsTest = input.split("x","X").last().replace(" ","").replace("\t","").toInt()
            if (rowsTest !in 5..9) {
                println("Board rows should be from 5 to 9")
                failed = true
            }
            if (columnsTest !in 5..9) {
                println("Board columns should be from 5 to 9")
                failed = true
            }
            if (!failed) {
                rows = rowsTest
                columns = columnsTest
            }
        }
        if (failed) {
            println("Set the board dimensions (Rows x Columns) \nPress Enter for default (6 x 7)")
            input = readLine()!!
        }
    }
    println("$firstName VS $secondName\n$rows X $columns board")
}
