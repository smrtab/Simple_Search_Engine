package search

import java.io.File

fun main(args: Array<String>) {

    if (args.isEmpty())
        throw SearchException("File was not provided.")

    if (args[0] != "--data" || args.getOrNull(1) == null)
        throw SearchException("Wrong option provided.")

    File(args[1]).forEachLine { line ->
        val person = Person(line)
        Person.Index.add(person)
    }

    while (true) {
        try {
            Handler.run()
        } catch (e: SearchException) {
            println(e.message)
        } catch (e: ExitCommandException) {
            println(e.message)
            break
        }
    }
}