import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ArticleDetailedDTO, ArticleSimpleListDTO} from '../dto/dtos';
import {ArticleSimpleDTO} from '../dto/dtos';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {SessionService} from './session.service';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private httpClient: HttpClient, private sessionService: SessionService) {
  }

  public getArticleById(id: string): Observable<ArticleDetailedDTO> {
    const headers = new HttpHeaders({
      'Auth-Token': this.sessionService.getSession()
    });

    return this.httpClient.get<ArticleDetailedDTO>(environment.ROOT_URL + '/article/id/' + id, {headers});
  }

  public getArticles(): Observable<ArticleSimpleDTO[]> {
    return this.httpClient.get<ArticleSimpleDTO[]>(environment.ROOT_URL + '/article');
  }

  public getArticlesInRange(lower: number, upper: number): Observable<ArticleSimpleListDTO> {
    const headers = new HttpHeaders({
      'Auth-Token': this.sessionService.getSession()
    });

    return this.httpClient.get<ArticleSimpleListDTO>(
      environment.ROOT_URL + '/article/index/' + lower + '-' + upper, {headers});
  }

  public likeArticle(articleId: string): Observable<number> {
    const headers = new HttpHeaders({
      'Auth-Token': this.sessionService.getSession()
    });

    return this.httpClient.put<number>(environment.ROOT_URL + '/article/like/' + articleId, null, {headers});
  }

  public unlikeArticle(articleId: string): Observable<number> {
    const headers = new HttpHeaders({
      'Auth-Token': this.sessionService.getSession()
    });

    return this.httpClient.put<number>(environment.ROOT_URL + '/article/unlike/' + articleId, null, {headers});
  }
}
