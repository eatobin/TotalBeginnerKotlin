package total_beginner

//import com.beust.klaxon.Klaxon

import arrow.core.None
import arrow.core.Some
import total_beginner.Book.Companion.bookToString
import total_beginner.Book.Companion.getBorrower
import total_beginner.Book.Companion.getTitle
import total_beginner.Book.Companion.setBorrower
import total_beginner.Borrower.Companion.borrowerToString
import total_beginner.Borrower.Companion.getMaxBooks
import total_beginner.Borrower.Companion.getName

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

//    fun jsonStringToBorrowers(jsonString: String): List<Borrower> {
//        return try {
//            val mbrs: List<Borrower>? = Klaxon().parseArray(jsonString)
//            return mbrs ?: emptyList()
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
//
//    fun jsonStringToBooks(jsonString: String): List<Book> {
//        return try {
//            val mbks: List<Book>? = Klaxon().parseArray(jsonString)
//            return mbks ?: emptyList()
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
//
//    fun borrowersToJsonString(brs: List<Borrower>): String {
//        return Klaxon().toJsonString(brs)
//    }
//
//    fun booksToJsonString(bks: List<Book>): String {
//        return Klaxon().toJsonString(bks)
//    }
//
}
