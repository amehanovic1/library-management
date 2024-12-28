import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Author } from '../_model/author'; 

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private baseUrl = 'http://localhost:8080/authors';

  constructor(private http: HttpClient) { }

  getAllAuthors(): Observable<Author[]> {
    return this.http.get<Author[]>(this.baseUrl);
  }
}
