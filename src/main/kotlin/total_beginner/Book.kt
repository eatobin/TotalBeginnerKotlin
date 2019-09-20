package total_beginner

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import total_beginner.Borrower.Companion.getName

data class Book(val title: Title, val author: Author, val maybeBorrower: Option<Borrower> = None) {

    companion object {

        fun getTitle(bk: Book): Title = bk.title

        fun getAuthor(bk: Book): Author = bk.author

        fun setAuthor(a: Author, bk: Book): Book = bk.copy(author = a)

        fun getBorrower(bk: Book): Option<Borrower> = bk.maybeBorrower

        fun setBorrower(mbr: Option<Borrower>, bk: Book): Book = bk.copy(maybeBorrower = mbr)

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
