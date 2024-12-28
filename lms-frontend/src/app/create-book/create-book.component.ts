import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Books } from '../_model/books';
import { BooksService } from '../_service/books.service';
import { Genre } from '../_model/genre';
import { Author } from '../_model/author';
import { AuthorService } from '../_service/author.service';
import { GenreService } from '../_service/genre.service';

@Component({
  selector: 'app-create-book',
  templateUrl: './create-book.component.html',
  styleUrls: ['./create-book.component.css']
})
export class CreateBookComponent implements OnInit {

  book: Books = new Books();
  authors: Author[] = [];
  genres: Genre[] = [];

  constructor(
    private booksService: BooksService,
    private authorService: AuthorService,
    private genreService: GenreService,
    private router: Router) { }

  ngOnInit(): void {
    this.getAuthors();
    this.getGenres();
  }

  getAuthors(): void {
    this.authorService.getAllAuthors().subscribe(data => {
      this.authors = data;
    });
  }

  getGenres(): void {
    this.genreService.getGenres().subscribe(data => {
      this.genres = data;
    });
  }

  saveBook() {
    this.booksService.createBook(this.book).subscribe(data => {
      console.log(data);
      this.goToBooksList();
    },
    error => console.log(error));
  }

  goToBooksList() {
    this.router.navigate(['/books']);
  }

  onSubmit() {
    console.log(this.book);
    this.saveBook();
  }

}
