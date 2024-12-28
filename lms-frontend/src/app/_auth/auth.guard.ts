import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UserAuthService } from '../_service/user-auth.service';
import { UsersService } from '../_service/users.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    private userService: UsersService,
  ) {}
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
  
    const token = this.userAuthService.getToken();
    console.log('Token in canActivate:', token);  // Provjera tokena
  
    if (token !== null) {
      const roles = route.data["roles"] as Array<string>;
      console.log('Allowed Roles from Route:', roles);  // Provjera uloga iz rute
  
      if (roles) {
        const match = this.userService.roleMatch(roles);
        if (match) {
          return true; // Korisnik ima odgovarajuću ulogu
        } else {
          console.log('Role match failed, redirecting to forbidden');
          this.router.navigate(['/forbidden']);
          return false;
        }
      }
    }
  
    this.router.navigate(['/login']);
    return false;
  }
  
}
