fun printTable(gameTable: MutableList<String>) {
    val columns = gameTable[0].length / 2
    val rows = gameTable.size
    for (i in 1..columns) {
        print(" $i")
    }
    print("\n")
    var under = "╚═"
    for (i in 1 until columns) {
        under += "╩═"
    }
    under += "╝"
    for (i in 0 until rows) {
        println(gameTable[i])
    }
    println(under)
}

fun createTable(rows: Int, columns: Int): MutableList<String> {
    var str = "║"
    val gameTable = mutableListOf<String>()
    for (i in 1..columns) {
        str += " ║"
    }
    for (i in 1..rows) {
        gameTable.add(str)
    }
    return gameTable
}
fun addToTable(column: Int, gameBoard: MutableList<String>, toPut: Char): MutableList<String> {
    for (i in gameBoard.lastIndex downTo 0) {
        if (gameBoard[i][column*2 - 1] == ' ') {
            val stringToChange = gameBoard[i].toMutableList()
            stringToChange[column*2 - 1] = toPut
            gameBoard[i] = stringToChange.joinToString("")
            break
        }
    }
    return gameBoard
}
fun printName(firstName: String, secondName:String,isFirst: Boolean) {
    if (isFirst) {
        println("$firstName's turn:")
    }else {
        println("$secondName's turn:")
    }
}
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
        if (!input.matches("\\s*[0-9]+\\s*[xX]\\s*[0-9]+\\s*".toRegex())) {
            println("Invalid input")
            failed = true
        } else {
            val rowsTest = input.split("x", "X").first().replace(" ", "").replace("\t", "").toInt()
            val columnsTest = input.split("x", "X").last().replace(" ", "").replace("\t", "").toInt()
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
    var gameBoard = createTable(rows, columns)
    printTable(gameBoard)

    println("$firstName's turn:")
    var gameInput = readLine()!!
    var firstPlayer = true
    while(gameInput != "end") {
        if (!gameInput.matches("\\d+".toRegex())) {
            println("Incorrect column number")
            printName(firstName,secondName,firstPlayer)
            gameInput = readLine()!!
            continue
        }else {
            val column = gameInput.toInt()
            if (column !in 1..columns) {
                println("The column number is out of range (1 - $columns)")
                printName(firstName,secondName,firstPlayer)
                gameInput = readLine()!!
                continue
            }else {
                if (gameBoard[0][column*2 -1] != ' ') {
                    println("Column $column is full")
                    printName(firstName,secondName,firstPlayer)
                    gameInput = readLine()!!
                    continue
                }
                val toPut: Char = if (firstPlayer) 'o' else '*'
                gameBoard = addToTable(column,gameBoard,toPut)
                firstPlayer = !firstPlayer
                printTable(gameBoard)
                printName(firstName,secondName,firstPlayer)
                gameInput = readLine()!!
            }
        }
    }
    println("Game over!")
}
