import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest,
  } from '@angular/common/http';
  import { Router } from '@angular/router';
  import { catchError } from 'rxjs/operators';
  import { Observable, throwError } from 'rxjs';
  import { UserAuthService } from '../_service/user-auth.service';
  import { Injectable } from '@angular/core';
  
  @Injectable()
  export class AuthInterceptor implements HttpInterceptor {
    constructor(
      private userAuthService: UserAuthService,
      private router: Router
    ) {}
  
    intercept(
      req: HttpRequest<any>,
      next: HttpHandler
    ): Observable<HttpEvent<any>> {
      // Log outgoing request
      console.log('Outgoing Request:', req.url);
  
      // Bypass interceptor for "No-Auth" requests
      if (req.headers.get('No-Auth') === 'True') {
        return next.handle(req.clone());
      }
  
      // Add Authorization token if available
      const token = this.userAuthService.getToken();
      if (token) {
        req = this.addToken(req, token);
      }
  
      return next.handle(req).pipe(
        catchError((err: HttpErrorResponse) => {
          console.error('Error Occurred:', {
            status: err.status,
            message: err.message,
            details: err.error,
          });
  
          // Handle specific HTTP status codes
          if (err.status === 401) {
            alert('Session expired. Please login again.');
            this.router.navigate(['/login']);
          } else if (err.status === 403) {
            alert('You do not have permission to access this resource.');
            this.router.navigate(['/forbidden']);
          } else {
            alert(`Failed to process request. Server returned: ${err.status} ${err.statusText}`);
          }
  
          // Pass error to the caller
          return throwError(() => new Error(`HTTP Error: ${err.status}`));
        })
      );
    }
  
    // Add Bearer token to headers
    private addToken(request: HttpRequest<any>, token: string) {
      return request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
  }
  