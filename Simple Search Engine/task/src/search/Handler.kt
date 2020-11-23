package search

import java.util.*

enum class Strategy {
    ALL,
    ANY,
    NONE;
}

class Handler {

    companion object {

        val scanner = Scanner(System.`in`)

        fun run() {

            Options.Menu.show()

            print("> ")
            when (Options.Menu.findPoint(scanner.next())) {
                Options.EXIT -> {
                    throw ExitCommandException("\nBye!")
                }
                Options.FIND_PERSON -> {
                    this.find()
                }
                Options.SHOW_LIST -> {
                    this.show()
                }
                else -> throw SearchException("\nIncorrect option! Try again.")
            }
        }

        private fun find() {
            println("\nSelect a matching strategy: ALL, ANY, NONE")
            val strategy = scanner.next()

            scanner.nextLine()
            println("\nEnter a name or email to search all suitable people.")
            Person.Index.find(scanner.nextLine(), Strategy.valueOf(strategy)) { person ->
                println(person)
            }
        }

        private fun show() {
            println("\n=== List of people ===")
            Person.Index.raw.forEach { it ->
                println(it.value)
            }
        }
    }
}