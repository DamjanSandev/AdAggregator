import {Injectable} from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StorageService} from '../services/storage.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private storage: StorageService) {
  }

  intercept(
    req: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const token = this.storage.getItem<string>('token');

    const authReq = token
      ? req.clone({setHeaders: {Authorization: `Bearer ${token}`}})
      : req;
    return next.handle(authReq);
  }
}
