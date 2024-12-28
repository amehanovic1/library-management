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
    const userRoles: string[] = this.userAuthService.getAllRolesFromToken(); // Dohvati uloge iz tokena
    console.log('User Roles:', userRoles);
  
    if (userRoles && userRoles.length > 0) {
      const cleanedUserRoles = userRoles.map(role => role.replace('ROLE_', ''));
      console.log('Cleaned User Roles:', cleanedUserRoles);
  
      // Provjera da li korisnik ima barem jednu od dopuštenih uloga
      for (let i = 0; i < cleanedUserRoles.length; i++) {
        if (allowedRoles.includes(cleanedUserRoles[i])) {
          return true; // Ako korisnik ima neku od dopuštenih uloga
        }
      }
    }
  
    return false; // Ako nema odgovarajuće uloge
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

  deleteUser(userId: number): Observable<any> {
    return this.httpClient.delete(`${this.baseURL}/${userId}`, {
      responseType: 'text',
    });
  }
}
