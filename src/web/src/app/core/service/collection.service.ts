import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {CollectionDTO} from '../dto/dtos';
import {environment} from '../../../environments/environment';
import {AbstractService} from './abstract.service';
import {HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CollectionService extends AbstractService {

  public getCollections(): Observable<CollectionDTO[]> {
    return this.httpClient.get<CollectionDTO[]>(environment.ROOT_URL + '/collection/', {headers: this.getAuthHeaders()});
  }

  public getCollectionForUser(): Observable<CollectionDTO[]> {
    return this.httpClient.get<CollectionDTO[]>(environment.ROOT_URL + '/collection/user/' + this.sessionService.getSession());
  }

  public getCollectionDetail(id): Observable<CollectionDTO> {
    return this.httpClient.get<CollectionDTO>(environment.ROOT_URL + '/collection/' + id, {headers: this.getAuthHeaders()});
  }

  public assignToCollection(articleId, collectionId): Observable<any> {
    return this.httpClient.put<Observable<any>>(environment.ROOT_URL + '/collection/assign/' + collectionId + '/' + articleId, null);
  }

  public insertCollection(collection: CollectionDTO): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });

    const articleIds = [];
    for (const article of collection.articles) {
      articleIds.push(article.id);
    }
    const tmpCollection: any = collection;
    tmpCollection.articles = articleIds;
    tmpCollection.author = collection.author.id;
    console.log("collection ", tmpCollection);

    return this.httpClient.post<Observable<any>>(environment.ROOT_URL + '/collection', tmpCollection, {headers});
  }
}
