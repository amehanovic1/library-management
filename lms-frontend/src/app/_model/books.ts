import { Author } from "./author";
import { Genre } from "./genre";

export class Books {
    bookId: number;
    authorId: number;
    genreId: number;
    bookName: string;
    author: Author;
    genre: Genre;
    authorName: string;
    genreName: string;
    noOfCopies: number;
}
