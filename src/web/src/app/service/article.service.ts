import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ArticleDetailedDTO} from '../dto/dtos';
import {ArticleSimpleDTO} from '../dto/dtos';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private httpClient: HttpClient) {
  }

  public getArticleById(id: string): Observable<ArticleDetailedDTO> {
    return this.httpClient.get<ArticleDetailedDTO>(environment.ROOT_URL + '/article/id/' + id);
  }

  public getArticles(): Observable<ArticleSimpleDTO[]> {
    return this.httpClient.get<ArticleSimpleDTO[]>(environment.ROOT_URL + '/article');
  }
}
