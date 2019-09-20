package total_beginner

import arrow.core.None
import arrow.core.Some
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import total_beginner.Book.Companion.bookToString
import total_beginner.Book.Companion.getAuthor
import total_beginner.Book.Companion.getBorrower
import total_beginner.Book.Companion.getTitle
import total_beginner.Book.Companion.setAuthor
import total_beginner.Book.Companion.setBorrower

private val br1 = Borrower("Borrower1", 1)
private val bk1 = Book("Title1", "Author1", Some(br1))
private val bk2 = Book("Title2", "Author2")

class BookTest : StringSpec({

    "getTitle should return the Book title" {
        getTitle(bk1).shouldBe("Title1")
    }

    "getAuthor should return the Book author" {
        getAuthor(bk2).shouldBe("Author2")
    }

    "setAuthor should set the Book author" {
        setAuthor("Author11", bk1).shouldBe(Book(title = "Title1", author = "Author11", maybeBorrower = Some(br1)))
    }

    "getBorrower-someone should get the Book borrower" {
        getBorrower(bk1).shouldBe(Some(Borrower(name = "Borrower1", maxBooks = 1)))
    }

    "getBorrower-None should get the Book borrower - None" {
        getBorrower(bk2).shouldBe(None)
    }

    "setBorrower-someone should set the Book borrower" {
        val nbr = Book(title = "Title2", author = "Author2",
                maybeBorrower = Some(Borrower(name = "BorrowerNew", maxBooks = 111)))
        setBorrower(Some(Borrower(name = "BorrowerNew", maxBooks = 111)), bk2).shouldBe(nbr)
    }

    "setBorrower-None should set the Book borrower - None" {
        val nbr = Book(title = "Title2", author = "Author2",
                maybeBorrower = None)
        setBorrower(None, bk2).shouldBe(nbr)
    }

    "bookToString should return the Book - checked out" {
        bookToString(bk1).shouldBe("Title1 by Author1; Checked out to Borrower1")
    }

    "bookToString should return the Book - not checked out" {
        bookToString(bk2).shouldBe("Title2 by Author2; Available")
    }
})
