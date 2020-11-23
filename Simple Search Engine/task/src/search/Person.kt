package search

import java.util.*
import kotlin.collections.HashMap

class Person {

    val name: String
    val surname: String?
    val email: String?

    object Index {
        val table = HashMap<String, LinkedList<Person>>()
        val raw = HashMap<String, Person>()

        fun add(person: Person) {

            this.raw[person.getFullKey()] = person

            for (key in arrayOf(
                    person.name.toLowerCase(),
                    person.surname?.toLowerCase(),
                    person.email?.toLowerCase()
            )) {

                key ?: continue

                if (this.table.containsKey(key)) {
                    this.table[key]!!.add(person)
                } else {
                    val linkedList: LinkedList<Person> = LinkedList()
                    linkedList.add(person)
                    this.table[key] = linkedList
                }
            }
        }

        fun find(key: String, strategy: Strategy, action: (person: Person) -> Unit)  {

            val targets = key.toLowerCase().split(" ")
            var list = mutableListOf<Person>()

            when (strategy) {
                Strategy.ALL -> {
                    val target = targets[0]
                    if (this.table[target] != null) {
                        this.table[target]!!.forEach { person ->
                            if (person.fitsAll(targets)) list.add(person)
                        }
                    }
                }
                Strategy.ANY -> {
                    targets.forEach { target ->
                        if (this.table[target] != null) {
                            this.table[target]!!.forEach { person ->
                                if (!list.contains(person))
                                    list.add(person)
                            }
                        }
                    }
                }
                Strategy.NONE -> {
                    this.table.forEach { it ->
                        if (it.key !in targets) {
                            it.value.forEach { person ->
                                if (person.fitsNone(targets) && !list.contains(person))
                                    list.add(person)
                            }
                        }
                    }
                }
            }

            if (list.isEmpty()) {
                throw SearchException("No matching people found.")
            } else {
                println("\n${list.size} persons found:")
                list.forEach {person ->
                    action(person)
                }
            }
        }
    }

    constructor(line: String) {
        val data = line.split(' ')
        this.name = data[0]
        this.surname = data.getOrNull(1)
        this.email = data.getOrNull(2)
    }

    fun getFullKey(): String {
        return "${this.name.toLowerCase()} ${this.surname?.toLowerCase() ?: ""} ${this.email?.toLowerCase() ?: ""}".trim()
    }

    fun fitsAll(targets: List<String>): Boolean {
        targets.forEach { target ->
            if (this.name.toLowerCase() != target && this.surname?.toLowerCase() != target && this.email?.toLowerCase() != target)
                return false
        }

        return true
    }

    fun fitsNone(targets: List<String>): Boolean {
        targets.forEach { target ->
            if (this.name.toLowerCase() == target || this.surname?.toLowerCase() == target || this.email?.toLowerCase() == target)
                return false
        }

        return true
    }

    override fun toString(): String {
        return "$name ${surname ?: ""} ${email ?: ""}".trim()
    }
}