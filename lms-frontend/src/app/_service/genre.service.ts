import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Genre } from '../_model/genre';

@Injectable({
  providedIn: 'root'
})
export class GenreService {
    
    private apiUrl = 'http://localhost:8080/genres';
    constructor(private http: HttpClient) { }

    getGenres(): Observable<Genre[]> {
        return this.http.get<Genre[]>(this.apiUrl);
    }
}
