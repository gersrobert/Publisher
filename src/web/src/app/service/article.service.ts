import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ArticleDetailedDTO, ArticleSimpleListDTO} from '../dto/dtos';
import {ArticleSimpleDTO} from '../dto/dtos';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {SessionService} from './session.service';
import {FormGroup} from '@angular/forms';

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

  public insertArticle(articleForm: FormGroup): Observable<any> {
    let title = articleForm.value['title'];
    let categories = articleForm.value['categories'].split(", ");
    let content = articleForm.value['content'];
    let authors = [this.sessionService.getSession()];

    if (title != '' && content != '' && authors[0] != null) {
      const body = {
        title: title,
        categories: categories,
        content: content,
        authors: authors
      };

      console.log(body);

      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      });

      return this.httpClient.post(environment.ROOT_URL + '/article/insert', body, {headers});
    }
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
