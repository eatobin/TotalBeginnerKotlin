package total_beginner

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import total_beginner.Borrower.Companion.getName

data class Book(val title: String, val author: String, val borrower: Option<Borrower> = None) {

    companion object {

        fun getTitle(bk: Book): String = bk.title

        fun getAuthor(bk: Book): String = bk.author

        fun setAuthor(a: String, bk: Book): Book = bk.copy(author = a)

        fun getBorrower(bk: Book): Option<Borrower> = bk.borrower

        fun setBorrower(br: Borrower, bk: Book): Book = bk.copy(borrower = Some(br))

        private fun availableString(bk: Book): String {
            return when (val br = getBorrower(bk)) {
                is Some -> "Checked out to " + getName(br.t)
                is None -> "Available"
            }
        }

        fun bookToString(bk: Book): String {
            return getTitle(bk) +
                    " by " + getAuthor(bk) +
                    "; " + availableString(bk)
        }

    }

}
