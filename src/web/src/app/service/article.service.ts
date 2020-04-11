import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
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

  public getFilteredArticles(
    title?: string,
    author?: string,
    category?: string,
    publisher?: string,
    lowerIndex?: number,
    upperIndex?: number,
  ): Observable<ArticleSimpleListDTO> {
    const headers = new HttpHeaders({
      'Auth-Token': this.sessionService.getSession()
    });

    let params = new HttpParams();
    if (title && title !== '') {
      params = params.append('title', title);
    }
    if (author && author !== '') {
      params = params.append('author', author);
    }
    if (category && category !== '') {
      params = params.append('category', category);
    }
    if (publisher && publisher !== '') {
      params = params.append('publisher', publisher);
    }
    if (lowerIndex) {
      params = params.append('lowerIndex', String(lowerIndex));
    }
    if (upperIndex) {
      params = params.append('upperIndex', String(upperIndex));
    }

    return this.httpClient.get<ArticleSimpleListDTO>(environment.ROOT_URL + '/article', {headers, params});
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
