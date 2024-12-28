import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Observable } from 'rxjs';
import { Users } from '../_model/users';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  private baseURL = 'http://localhost:8080/admin/users';
  requestHeader = new HttpHeaders({ 'No-Auth': 'True' });

  constructor(
    private httpClient: HttpClient,
    private userAuthService: UserAuthService
  ) {}

  public login(loginData: NgForm) {
    return this.httpClient.post(
      'http://localhost:8080/authenticate',
      loginData,
      {
        headers: this.requestHeader,
      }
    );
  }

  public roleMatch(allowedRoles: string[]): boolean {
    const userRoles: any[] = this.userAuthService.getRoles(); // Dohvaćamo korisničke uloge
  
    // Ako postoje uloge korisnika
    if (userRoles != null && userRoles.length > 0) {
      // Provjera svake uloge korisnika
      for (let i = 0; i < userRoles.length; i++) {
        // Provjera da li korisnik ima neku od dozvoljenih uloga
        if (allowedRoles.includes(userRoles[i].roleName)) {
          return true; // Ako je našao podudaranje, vrati true
        }
      }
    }
  
    return false; // Ako nije pronašao podudaranje, vraća false
  }
  

  getUsersList(): Observable<Users[]> {
    return this.httpClient.get<Users[]>(`${this.baseURL}`);
  }

  createUser(user: Users): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, user);
  }

  getUserById(userId: number): Observable<Users> {
    return this.httpClient.get<Users>(`${this.baseURL}/${userId}`);
  }

  updateUser(userId: number, user: Users): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${userId}`, user);
  }

  deleteUser(userId: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${userId}`);
  }
  
}
