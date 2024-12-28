import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Books } from '../_model/books'
import { BooksService } from '../_service/books.service';
import { UsersService } from '../_service/users.service';

@Component({
  selector: 'app-books-user',
  templateUrl: './books-user.component.html',
  styleUrls: ['./books-user.component.css']
})
export class BooksUserComponent implements OnInit {

  books: Books[];

  constructor(
    private booksService: BooksService,
    private router: Router,
    public userService: UsersService
  ) { }

  ngOnInit(): void {
    this.getBooks();
  }

  private getBooks() {
    this.booksService.getBooksList().subscribe(data =>{
      this.books = data;
    });
  }

}
