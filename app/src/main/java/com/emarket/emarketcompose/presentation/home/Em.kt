sealed class HomeEvent {
    data class LoadData(val pageIndex: Int) : HomeEvent() {
        override fun accept(visitor: HomeEventVisitor) = visitor.visit(this)
    }
    data class SearchItem(val query: String) : HomeEvent() {
        override fun accept(visitor: HomeEventVisitor) = visitor.visit(this)
    }
    // Diğer olaylar...

    abstract fun accept(visitor: HomeEventVisitor)
}

interface HomeEventVisitor {
    fun visit(event: HomeEvent.LoadData)
    fun visit(event: HomeEvent.SearchItem)
    // Diğer olaylar...
}

class EventProcessor : HomeEventVisitor {
    override fun visit(event: HomeEvent.LoadData) {
        // Burada gerçek işlevi yerine getirin
        println("Loading data for page index ${event.pageIndex}")
    }

    override fun visit(event: HomeEvent.SearchItem) {
        // Burada gerçek işlevi yerine getirin
        println("Searching for items with query: ${event.query}")
    }

}

fun onEvent(event: HomeEvent) {
    val processor = EventProcessor()
    event.accept(processor)
}

fun main() {
    println("Enter command (e.g., LoadData 1, SearchItem 'query'):")
    val input = readLine() ?: return

    val parts = input.split(" ", limit = 2)
    val command = parts[0]
    val argument = parts.getOrNull(1) ?: ""

    val event = when (command) {
        "LoadData" -> HomeEvent.LoadData(argument.toIntOrNull() ?: 1)
        "SearchItem" -> HomeEvent.SearchItem(argument)
        // Diğer olaylar...
        else -> null
    }

    if (event != null) {
        onEvent(event)
    } else {
        println("Invalid command")
    }
}