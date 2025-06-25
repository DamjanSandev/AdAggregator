import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {AdSearchFilter} from '../../models/AdSearchFilter';
import {Observable} from 'rxjs';
import {AdResponse} from '../../models/AdResponse';

@Injectable({
  providedIn: 'root'
})
export class AdService {
  private baseUrl = `${environment.apiUrl}/ads`;


  constructor(private http: HttpClient) {
  }

  getPaginatedAds(filter: AdSearchFilter = {}, page: number = 0, size: number = 10): Observable<any> {
    let params = new HttpParams().set('page', page).set('size', size);
    Object.entries(filter).forEach(([key, value]) => {
      if (value !== null && value !== undefined && value !== '') {
        params = params.set(key, value);
      }
    })
    return this.http.get<any>(`${this.baseUrl}/paginated`, {
      params,
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('token')}`
      })
    });
  }
}
