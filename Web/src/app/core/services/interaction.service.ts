import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {InteractionRequest} from '../../models/InteractionRequest';
import {InteractionResponse} from '../../models/InteractionResponse';


@Injectable({providedIn: 'root'})
export class InteractionService {
  private base = `${environment.apiUrl}/interactions`;

  constructor(private http: HttpClient) {
  }

  getFavorites(username: string): Observable<InteractionResponse[]> {
    const params = new HttpParams()
      .set('username', username)
      .set('interactionType', 'FAV');
    return this.http.get<InteractionResponse[]>(`${this.base}/findByUserAndInteraction`, {params});
  }

  addFavorite(request: InteractionRequest): Observable<InteractionResponse> {
    request.interactionType = 'FAV';
    return this.http.post<InteractionResponse>(`${this.base}/add`, request, {});
  }

  removeFavorite(interactionId: number): Observable<InteractionResponse> {
    return this.http.delete<InteractionResponse>(`${this.base}/delete/${interactionId}`, {});
  }
}
