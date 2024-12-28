import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Books } from '../_model/books';
import { BooksService } from '../_service/books.service';
import { Author } from '../_model/author';
import { AuthorService } from '../_service/author.service';
import { Genre } from '../_model/genre';
import { GenreService } from '../_service/genre.service';

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.css']
})
export class UpdateBookComponent implements OnInit {

  bookId: number;
  book: Books = new Books();
  authors: Author[] = [];
  genres: Genre[] = [];

  constructor(
    private booksService: BooksService,
    private authorService: AuthorService,
    private genreService: GenreService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.bookId = this.route.snapshot.params['bookId'];

    // Učitavanje autora za dropdown
    this.authorService.getAllAuthors().subscribe(data => {
      this.authors = data;
      console.log('Loaded authors:', this.authors); 
    });

    this.genreService.getGenres().subscribe(data => {
      this.genres = data;
      console.log('Loaded genres:', this.genres);
    });

    this.booksService.getBookById(this.bookId).subscribe(data => {
      this.book = data;
      console.log('Loaded book:', this.book);

      this.book.author = this.authors.find(author => author.authorId === this.book.authorId)!;
      this.book.genre = this.genres.find(genre => genre.genreId === this.book.genreId)!;
    });
  }

  onSubmit() {
    this.booksService.updateBook(this.bookId, this.book).subscribe(data => {
        this.goToBooksList();
    },
    error => console.log(error));
  }

  goToBooksList() {
    this.router.navigate(['/books']);
  }
}
