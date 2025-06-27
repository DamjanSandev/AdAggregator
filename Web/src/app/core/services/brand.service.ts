import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Brand} from '../../models/Brand';

@Injectable({
  providedIn: 'root'
})
export class BrandService {

  private baseUrl = `${environment.apiUrl}/brands`;


  constructor(private http: HttpClient) {
  }

  getBrands(): Observable<Brand[]> {
    return this.http.get<Brand[]>(this.baseUrl, {});
  }
}
