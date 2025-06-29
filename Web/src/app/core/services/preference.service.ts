import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AdResponse} from '../../models/AdResponse';

@Injectable({
  providedIn: 'root'
})
export class PreferenceService {
  private base = `${environment.apiUrl}/preferences`;

  constructor(private http: HttpClient) {
  }

  getUserRecommendations(username: string): Observable<AdResponse[]> {
    return this.http.get<AdResponse[]>(`${this.base}/${username}`);
  }
}
