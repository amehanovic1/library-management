import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';  // Importanje jwt-decode

@Injectable({
  providedIn: 'root',
})
export class UserAuthService {
  constructor() {}

  public setRoles(roles: []) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  public getRoles(): [] {
    return JSON.parse(localStorage.getItem('roles')!);
  }

  public setToken(jwtToken: string) {
    localStorage.setItem('jwtToken', jwtToken);
  }

  public getToken(): string {
    return localStorage.getItem('jwtToken')!;
  }

  public getAllRolesFromToken(): string[] {
    const token = this.getToken();
    if (token) {
      const decodedToken = jwtDecode<any>(token); // Dekodiranje JWT tokena
      return decodedToken.roles || []; // Vraća sve uloge ili prazan niz ako nema
    }
    return [];
  }

  public setUserId(userId: number) {
    localStorage.setItem('userId', JSON.stringify(userId));
  }

  public getUserId() {
    return JSON.parse(localStorage.getItem('userId')!);
  }

  public setName(userId: number) {
    localStorage.setItem('name', JSON.stringify(userId));
  }

  public getName() {
    return JSON.parse(localStorage.getItem('name')!);
  }

  public clear() {
    localStorage.clear();
  }

  public isLoggedIn() {
    return this.getRoles() && this.getToken();
  }
}
