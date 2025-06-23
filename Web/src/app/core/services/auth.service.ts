import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { StorageService } from './storage.service';

interface LoginResponse {
  token: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly api = `${environment.apiUrl}/user`;

  constructor(
    private http: HttpClient,
    private storage: StorageService
  ) {}

  login(username: string, password: string): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.api}/login`, { username, password })
      .pipe(
        tap(({ token }) => this.storage.setItem('token', token))
      );
  }

  logout(): void {
    this.storage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.storage.getItem<string>('token');
  }
}
