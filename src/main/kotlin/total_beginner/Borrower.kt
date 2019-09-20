package total_beginner

data class Borrower(val name: Name, val maxBooks: MaxBooks) {

    companion object {

        fun getName(br: Borrower): Name = br.name

        fun setName(n: Name, br: Borrower): Borrower = br.copy(name = n)

        fun getMaxBooks(br: Borrower): MaxBooks = br.maxBooks

        fun setMaxBooks(mb: MaxBooks, br: Borrower): Borrower = br.copy(maxBooks = mb)

        fun borrowerToString(br: Borrower): String {
            return getName(br) + " (" + getMaxBooks(br) + " books)"
        }
    }

}
