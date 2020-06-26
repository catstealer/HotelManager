import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserService} from './user.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private userService: UserService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const currentUser = this.userService.userValue;
    if (currentUser && currentUser?.token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Basic ${currentUser.token}`
        }
      });
    }
    return next.handle(request);
  }
}
