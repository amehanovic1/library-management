import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Books } from '../_model/books'
import { BooksService } from '../_service/books.service';
import { UsersService } from '../_service/users.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-books-user',
  imports: [ CommonModule ],
  standalone: true,
  templateUrl: './books-user.component.html',
  styleUrls: ['./books-user.component.css']
})
export class BooksUserComponent implements OnInit {

  books: Books[];
  message: string = '';

  constructor(
    private booksService: BooksService,
    private router: Router,
    public userService: UsersService
  ) { }

  ngOnInit(): void {
    this.getBooks();
  }

  private getBooks() {
    this.booksService.getBooksUserList().subscribe(data =>{
      this.books = data;
    });
  }

  viewDetails() {
    alert('You must log in or register at the library if you are not already.');
  }

}
