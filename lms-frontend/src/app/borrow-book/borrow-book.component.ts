import { Component, OnInit } from '@angular/core';
import { Books } from '../_model/books';
import { Borrow } from '../_model/borrow';
import { BooksService } from '../_service/books.service';
import { BorrowService } from '../_service/borrow.service';
import { UserAuthService } from '../_service/user-auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-borrow-book',
  templateUrl: './borrow-book.component.html',
  styleUrls: ['./borrow-book.component.css'],
})
export class BorrowBookComponent implements OnInit {
  books: Books[] = [];
  borrowedStatus: { [bookId: number]: boolean } = {};

  constructor(
    private booksService: BooksService,
    private userAuthService: UserAuthService,
    private borrowService: BorrowService,
    private router: Router
  ) {}

  userId = this.userAuthService.getUserId();

  ngOnInit(): void {
    this.getBooks();
  }

  private getBooks() {
    this.booksService.getBooksList().subscribe((data) => {
      this.books = data;
      this.books.forEach((book) => {
        this.checkBorrowStatus(book.bookId);
      });
    });
  }

  private checkBorrowStatus(bookId: number) {
    this.borrowService
      .checkBorrowStatus(this.userId, bookId)
      .subscribe((status) => {
        this.borrowedStatus[bookId] = status;
      });
  }

  borrow: Borrow = new Borrow();

  borrowBook(bookId: number) {
    if (this.borrowedStatus[bookId]) {
      alert('You have already borrowed this book.');
      return;
    }

    this.borrow.bookId = bookId;
    this.borrow.userId = this.userId;

    this.borrowService.borrowBook(this.borrow).subscribe(
      (data) => {
        alert('Book borrowed successfully!');
        this.checkBorrowStatus(bookId); // Ažuriraj status nakon posudbe
        this.router.navigate(['/return-book']).then(() => {
          window.location.reload();
        });
      },
      (error) => {
        console.log(error);
        alert('Failed to borrow the book. Please try again.');
      }
    );
  }
}
