import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Model} from '../../models/Model';

@Injectable({
  providedIn: 'root'
})
export class ModelService {

  private readonly baseUrl = `${environment.apiUrl}/models`;

  constructor(private http: HttpClient) {
  }


  getModelsByBrand(brandName: string): Observable<Model[]> {
    const params = new HttpParams().set('brand', brandName);
    return this.http.get<Model[]>(`${this.baseUrl}/by-brand`, {params});
  }
}
