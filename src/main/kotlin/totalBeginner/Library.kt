package totalBeginner

import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import totalBeginner.Book.Companion.bookToString
import totalBeginner.Book.Companion.getBorrower
import totalBeginner.Book.Companion.getTitle
import totalBeginner.Book.Companion.setBorrower
import totalBeginner.Borrower.Companion.borrowerToString
import totalBeginner.Borrower.Companion.getMaxBooks
import totalBeginner.Borrower.Companion.getName

object Library {

    fun <A> addItem(x: A, xs: List<A>): List<A> {
        return if (xs.contains(x))
            xs
        else
            xs.plusElement(x)
    }

    fun removeBook(bk: Book, bks: List<Book>): List<Book> =
            bks.filter { it != bk }

    fun <A> findItem(tgt: String, coll: List<A>, f: (A) -> String): A? {
        val result: List<A> = coll.filter { it -> f(it) == tgt }
        return result.firstOrNull()
    }

    fun getBooksForBorrower(br: Borrower, bks: List<Book>): List<Book> =
            bks.filter { getBorrower(it) == br }

    private fun numBooksOut(br: Borrower, bks: List<Book>): Int =
            getBooksForBorrower(br, bks).count()

    private fun notMaxedOut(br: Borrower, bks: List<Book>): Boolean =
            numBooksOut(br, bks) < getMaxBooks(br)

    private fun bookNotOut(bk: Book): Boolean =
            getBorrower(bk) == null

    private fun bookOut(bk: Book): Boolean =
            getBorrower(bk) != null

    fun checkOut(n: String, t: String, brs: List<Borrower>, bks: List<Book>): List<Book> {
        val mbk: Book? = findItem(t, bks) { getTitle(it) }
        val mbr: Borrower? = findItem(n, brs) { getName(it) }
        return if (mbk != null && mbr != null && notMaxedOut(mbr, bks) && bookNotOut(mbk)) {
            val newBook = setBorrower(mbr, mbk)
            val fewerBooks = removeBook(mbk, bks)
            addItem(newBook, fewerBooks)
        } else bks
    }

    fun checkIn(t: String, bks: List<Book>): List<Book> {
        val mbk: Book? = findItem(t, bks) { getTitle(it) }
        return if (mbk != null && bookOut(mbk)) {
            val newBook = setBorrower(null, mbk)
            val fewerBooks = removeBook(mbk, bks)
            addItem(newBook, fewerBooks)
        } else bks
    }

    private fun libraryToString(bks: List<Book>, brs: List<Borrower>): String {
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
                bks.joinToString("\n") { it -> bookToString(it) } + "\n" +
                "\n" +
                brs.joinToString("\n") { it -> borrowerToString(it) } + "\n" +
                "\n" +
                "--- End of Status Report ---" +
                "\n"
    }

    fun jsonStringToBorrowers(jsonString: String): List<Borrower> {
        return try {
            JSON.parse(Borrower.serializer().list, jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun jsonStringToBooks(jsonString: String): List<Book> {
        return try {
            JSON.parse(Book.serializer().list, jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun borrowersToJsonString(brs: List<Borrower>): String {
        return JSON.stringify(Borrower.serializer().list, brs)
    }

    fun booksToJsonString(bks: List<Book>): String {
        return JSON.stringify(Book.serializer().list, bks)
    }

}
