package total_beginner

import arrow.core.Some
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import total_beginner.Book.Companion.getAuthor
import total_beginner.Book.Companion.getTitle
import total_beginner.Book.Companion.setAuthor

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
        setAuthor("Author11", bk1).shouldBe(Book(title = "Title1", author = "Author11", borrower = Some(br1)))
    }

//    "getBorrower-someone should get the Book borrower" {
//        getBorrower(bk1).shouldBe(br1)
//    }
//
//    "getBorrower-null should get the Book borrower - null" {
//        getBorrower(bk2).shouldBe(null)
//    }
//
//    "setBorrower-someone should set the Book borrower" {
//        val nbr = Book(title = "Title2", author = "Author2",
//                borrower = Some(Borrower(name = "BorrowerNew", maxBooks = 111)))
//        setBorrower(Borrower(name = "BorrowerNew", maxBooks = 111), bk2).shouldBe(nbr)
//    }

})


//    fun testSetBorrowerNull() {
//        val nbr = Book(title = "Title1", author = "Author1",
//                borrower = null)
//        assertEquals(nbr, setBorrower(null, bk1))
//    }
//
//    fun testBookToStringSomeone() {
//        assertEquals("Title1 by Author1; Checked out to Borrower1", bookToString(bk1))
//    }
//
//    fun testBookToStringNull() {
//        assertEquals("Title2 by Author2; Available", bookToString(bk2))
//    }
//
//}
