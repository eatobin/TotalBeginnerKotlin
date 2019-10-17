package total_beginner

import arrow.core.None
import arrow.core.Some
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import total_beginner.Book.Companion.bookToString
import total_beginner.Book.Companion.getBorrower
import total_beginner.Book.Companion.getTitle
import total_beginner.Book.Companion.setBorrower
import total_beginner.Borrower.Companion.borrowerToString
import total_beginner.Borrower.Companion.getMaxBooks
import total_beginner.Borrower.Companion.getName
import java.io.StringReader

object Library {

    fun <A> addItem(x: A, xs: List<A>): List<A> {
        return if (xs.contains(x))
            xs
        else
            xs.plusElement(x)
    }

    fun removeBook(bk: Book, bks: Books): Books =
            bks.filter { it != bk }

    fun <A> findItem(tgt: String, coll: List<A>, f: (A) -> String): A? {
        val result: List<A> = coll.filter { f(it) == tgt }
        return result.firstOrNull()
    }

    fun getBooksForBorrower(br: Borrower, bks: Books): List<Book> =
            bks.filter { getBorrower(it) == Some(br) }

    private fun numBooksOut(br: Borrower, bks: Books): Int =
            getBooksForBorrower(br, bks).count()

    private fun notMaxedOut(br: Borrower, bks: Books): Boolean =
            numBooksOut(br, bks) < getMaxBooks(br)

    private fun bookNotOut(bk: Book): Boolean =
            getBorrower(bk).isEmpty()

    private fun bookOut(bk: Book): Boolean =
            getBorrower(bk).isDefined()

    fun checkOut(n: String, t: String, brs: Borrowers, bks: Books): Books {
        val mbk: Book? = findItem(t, bks) { getTitle(it) }
        val mbr: Borrower? = findItem(n, brs) { getName(it) }
        return if (mbk != null && mbr != null && notMaxedOut(mbr, bks) && bookNotOut(mbk)) {
            val newBook = setBorrower(Some(mbr), mbk)
            val fewerBooks = removeBook(mbk, bks)
            addItem(newBook, fewerBooks)
        } else bks
    }

    fun checkIn(t: String, bks: Books): Books {
        val mbk: Book? = findItem(t, bks) { getTitle(it) }
        return if (mbk != null && bookOut(mbk)) {
            val newBook = setBorrower(None, mbk)
            val fewerBooks = removeBook(mbk, bks)
            addItem(newBook, fewerBooks)
        } else bks
    }

    private fun libraryToString(bks: Books, brs: Borrowers): String {
        return "Test Library: " +
                bks.count() +
                " books; " +
                brs.count() +
                " borrowers."
    }

    fun statusToString(bks: List<Book>, brs: List<Borrower>): String {
        return "\n" +
                "--- Status Report of Test Library ---\n" +
                "\n" +
                libraryToString(bks, brs) + "\n" +
                "\n" +
                bks.joinToString("\n") { bookToString(it) } + "\n" +
                "\n" +
                brs.joinToString("\n") { borrowerToString(it) } + "\n" +
                "\n" +
                "--- End of Status Report ---" +
                "\n"
    }

    fun jsonStringToBorrowers(jsonString: JsonString): Borrowers {
        val klaxon = Klaxon()
        JsonReader(StringReader(jsonString)).use { reader ->
            val result = arrayListOf<Borrower>()
            try {
                reader.beginArray {
                    while (reader.hasNext()) {
                        val borrower = klaxon.parse<Borrower>(reader)
                        if (borrower != null)
                            result.add(borrower)
                    }
                }
            } catch (_: Exception) {
                println("\n***The JSON string:\n " + jsonString + "could not be parsed***\n")
            }
            return result
        }
    }

    fun jsonStringToBooks(jsonString: JsonString): Books {
        val klaxon = Klaxon()
        JsonReader(StringReader(jsonString)).use { reader ->
            val result = arrayListOf<Book>()
            try {
                reader.beginArray {
                    while (reader.hasNext()) {
                        val book = klaxon.parse<Book>(reader)
                        if (book != null)
                            result.add(book)
                    }
                }
            } catch (_: Exception) {
                println("\n***The JSON string:\n " + jsonString + "could not be parsed***\n")
            }
            return result
        }
    }

    fun borrowersToJsonString(brs: List<Borrower>): String {
        return Klaxon().toJsonString(brs)
    }

    fun booksToJsonString(bks: List<Book>): String {
        return Klaxon().toJsonString(bks)
    }

}
