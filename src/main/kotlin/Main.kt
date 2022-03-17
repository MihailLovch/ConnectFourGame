import java.util.regex.Pattern

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
        if (gameBoard[i][column * 2 - 1] == ' ') {
            indexs[0] = i
            indexs[1] = column - 1
            val stringToChange = gameBoard[i].toMutableList()
            stringToChange[column * 2 - 1] = toPut
            gameBoard[i] = stringToChange.joinToString("")
            count++
            break
        }
    }
    return gameBoard
}

fun printName(firstName: String, secondName: String, isFirst: Boolean) {
    if (isFirst) {
        println("$firstName's turn:")
    } else {
        println("$secondName's turn:")
    }
}

var indexs = MutableList<Int>(2) { 0 }
var count = 0;
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
    val score = MutableList<Int>(2) { 0 }
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
    println(
        """
        Do you want to play single or multiple games?
        For a single game, input 1 or press Enter
        Input a number of games:
    """.trimIndent()
    )
    var numberOfGamesInput = readLine()!!
    var numberOfGames: Int
    while (true) {
        if (numberOfGamesInput.isEmpty()) {
            numberOfGames = 1
        } else if (numberOfGamesInput.toIntOrNull() == null || numberOfGamesInput.toInt() <= 0) {
            println("Invalid input")
            println(
                """
        Do you want to play single or multiple games?
        For a single game, input 1 or press Enter
        Input a number of games:
    """.trimIndent()
            )
            numberOfGamesInput = readLine()!!
        } else {
            numberOfGames = numberOfGamesInput.toInt()
            break
        }
    }
    println("$firstName VS $secondName\n$rows X $columns board")
    var gameBoard = createTable(rows, columns)
    if (numberOfGames == 1) {
        println("Single game")
    }else {
        println("Total $numberOfGames games")
    }
    var isFirstGoing: Boolean = true
    var firstPlayer = isFirstGoing
    loop@ for (i in 1..numberOfGames) {
        println("Game #$i")
        printTable(gameBoard)
        println("$firstName's turn:")
        var gameInput = readLine()!!
        while (gameInput != "end") {
            if (gameInput == "end"){
                break@loop
            }
            if (!gameInput.matches("\\d+".toRegex())) {
                println("Incorrect column number")
                printName(firstName, secondName, firstPlayer)
                gameInput = readLine()!!
                continue
            } else {
                val column = gameInput.toInt()
                if (column !in 1..columns) {
                    println("The column number is out of range (1 - $columns)")
                    printName(firstName, secondName, firstPlayer)
                    gameInput = readLine()!!
                    continue
                } else {
                    if (gameBoard[0][column * 2 - 1] != ' ') {
                        println("Column $column is full")
                        printName(firstName, secondName, firstPlayer)
                        gameInput = readLine()!!
                        continue
                    }
                    val toPut: Char = if (firstPlayer) 'o' else '*'
                    gameBoard = addToTable(column, gameBoard, toPut)
                    printTable(gameBoard)
                    val validate = checkGame(rows, columns, gameBoard, toPut)
                    if (validate != 0) {
                        if (validate == 1) {
                            if (firstPlayer) {
                                println("Player $firstName won")
                                score[0]++
                            } else {
                                println("Player $secondName won")
                                score[1]++
                            }
                        }
                        if (validate == -1) {
                            score[0]++
                            score[1]++
                        }
                        println("Score\n$firstName: ${score[0]} $secondName: ${score[1]}")
                        isFirstGoing = !isFirstGoing
                        firstPlayer = isFirstGoing
                        break
                    }
                    firstPlayer = !firstPlayer
                    printName(firstName, secondName, firstPlayer)
                    gameInput = readLine()!!
                }
            }
        }
    }
    println("Game over!")
}

fun converteTable(gameBoard: MutableList<String>): MutableList<String> {
    val newTable = mutableListOf<String>()
    for (i in gameBoard.indices) {
        var str = ""
        for (j in 1..gameBoard[i].lastIndex step 2) {
            str += gameBoard[i][j]
        }
        newTable.add(str)
    }
    return newTable
}

fun checkGame(rows: Int, columns: Int, gameBoard: MutableList<String>, toPut: Char): Int {
    if (columns * rows == count) {
        println("It is a draw")
        return -1
    } else {
        var row = indexs[0]
        var column = indexs[1]
        val newTable = converteTable(gameBoard)
        // check row
        if (toPut.toString().repeat(4) in newTable[row]) {
            return 1
        }
        //check column
        var strToCheck = ""
        for (i in newTable) {
            strToCheck += i[column]
        }
        if (toPut.toString().repeat(4) in strToCheck) {
            return 1
        }
        // check diagonal
        var row2 = row
        var column2 = column
        while (row2 < rows - 1 && column2 > 0) {
            row2++
            column2--
        }
        var firstDiag = newTable[row2][column2].toString()
        while (row2 > 0 && column2 < columns - 1) {
            row2--
            column2++
            firstDiag += newTable[row2][column2].toString()
        }
        if (toPut.toString().repeat(4) in firstDiag) {
            return 1
        }
        //
        while (row < rows - 1 && column < columns - 1) {
            row++
            column++
        }
        var secDiag = newTable[row][column].toString()
        while (row > 0 && column > 0) {
            row--
            column--
            secDiag += newTable[row][column].toString()
        }
        if (toPut.toString().repeat(4) in secDiag) {
            return 1
        }
    }
    return 0
}