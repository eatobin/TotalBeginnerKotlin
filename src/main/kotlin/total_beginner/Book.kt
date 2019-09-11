package total_beginner

// import kotlinx.serialization.Optional
// import kotlinx.serialization.Serializable
import total_beginner.Borrower.Companion.getName

// @Serializable
data class Book(val title: String, val author: String, val borrower: Borrower? = null) {

    companion object {

        fun getTitle(bk: Book): String = bk.title

        fun getAuthor(bk: Book): String = bk.author

        fun setAuthor(a: String, bk: Book): Book = bk.copy(author = a)

        fun getBorrower(bk: Book): Borrower? = bk.borrower

        fun setBorrower(br: Borrower?, bk: Book): Book = bk.copy(borrower = br)

        private fun availableString(bk: Book): String {
            val br = getBorrower(bk)
            return if (br == null) {
                "Available"
            } else {
                "Checked out to " + getName(br)
            }
        }

        fun bookToString(bk: Book): String {
            return getTitle(bk) +
                    " by " + getAuthor(bk) +
                    "; " + availableString(bk)
        }

    }

}
