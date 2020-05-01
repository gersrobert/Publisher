import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {CollectionDTO} from '../dto/dtos';
import {environment} from '../../../environments/environment';
import {AbstractService} from './abstract.service';

@Injectable({
  providedIn: 'root'
})
export class CollectionService extends AbstractService {

  public getCollectionForUser(): Observable<CollectionDTO[]> {
    return this.httpClient.get<CollectionDTO[]>(environment.ROOT_URL + '/collection/user/' + this.sessionService.getSession());
  }

  public getCollectionDetail(id): Observable<CollectionDTO> {
    return this.httpClient.get<CollectionDTO>(environment.ROOT_URL + '/collection/' + id, {headers: this.getAuthHeaders()});
  }

  public assignToCollection(articleId, collectionId): Observable<any> {
    return this.httpClient.put<Observable<any>>(environment.ROOT_URL + '/collection/assign/' + collectionId + '/' + articleId, null);
  }
}
