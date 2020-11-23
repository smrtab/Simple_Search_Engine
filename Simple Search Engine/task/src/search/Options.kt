package search

enum class Options(val number: Int, val title: String) {
    FIND_PERSON(1, "Find a person"),
    SHOW_LIST(2, "Print all people"),
    EXIT(0, "Exit");

    object Menu {
        fun show() {
            println("\n=== Menu ===")
            println(Menu)
        }
        fun findPoint(point: String): Options? {

            if (point.toIntOrNull() == null)
                return null

            for (enum in Options.values()) {
                if (point.toInt() == enum.number) return enum
            }

            return null
        }
        override fun toString(): String {
            return Options.values().joinToString("\n") { "${it.number}. ${it.title}" }
        }
    }
}