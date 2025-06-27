import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {tap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {StorageService} from './storage.service';
import {Router} from '@angular/router';
import {jwtDecode} from 'jwt-decode';


interface LoginResponse {
  token: string;
}

interface JwtPayload {
  sub: string;
  roles: any[];
  exp: number;
  iat: number;
}

@Injectable({providedIn: 'root'})
export class AuthService {
  private readonly api = `${environment.apiUrl}/user`;

  constructor(
    private http: HttpClient,
    private storage: StorageService,
    private router: Router
  ) {
  }

  login(username: string, password: string): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.api}/login`, {username, password})
      .pipe(
        tap(({token}) => {
          this.storage.setItem('token', token);
          const decoded: JwtPayload = jwtDecode(token);
          this.storage.setItem('username', decoded.sub);
          this.storage.setItem('roles', JSON.stringify(decoded.roles));
        })
      );
  }

  register(req: any) {
    return this.http.post<any>(`${this.api}/register`, req);
  }

  logout(): void {
    this.storage.clear();
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!this.storage.getItem<string>('token');
  }
}
