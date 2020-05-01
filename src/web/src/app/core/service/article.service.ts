import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {ArticleDetailedDTO, ArticleSimpleListDTO, IdDTO} from '../dto/dtos';
import {ArticleSimpleDTO} from '../dto/dtos';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {SessionService} from './session.service';
import {FormGroup} from '@angular/forms';
import {AbstractService} from './abstract.service';

@Injectable({
  providedIn: 'root'
})
export class ArticleService extends AbstractService {

  public getArticleById(id: string): Observable<ArticleDetailedDTO> {
    return this.httpClient.get<ArticleDetailedDTO>(environment.ROOT_URL + '/article/id/' + id, {headers: this.getAuthHeaders()});
  }

  public getFilteredArticles(
    title?: string,
    author?: string,
    category?: string,
    publisher?: string,
    lowerIndex?: number,
    upperIndex?: number,
  ): Observable<ArticleSimpleListDTO> {

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

    return this.httpClient.get<ArticleSimpleListDTO>(environment.ROOT_URL + '/article', {headers: this.getAuthHeaders(), params});
  }

  public getArticlesInRange(lower: number, upper: number): Observable<ArticleSimpleListDTO> {
    return this.httpClient.get<ArticleSimpleListDTO>(
      environment.ROOT_URL + '/article/index/' + lower + '-' + upper, {headers: this.getAuthHeaders()});
  }

  public getArticlesByAuthor(authorId: string, lower: number, upper: number): Observable<ArticleSimpleListDTO> {
    return this.httpClient.get<ArticleSimpleListDTO>(environment.ROOT_URL + '/article/author/' + authorId + '/' + lower + '-' + upper, {headers: this.getAuthHeaders()});
  }

  public insertArticle(articleForm: FormGroup): Observable<IdDTO> {
    let id = articleForm.value['id'];
    let title = articleForm.value['title'];
    let categories = articleForm.value['categories'].split(", ");
    let content = articleForm.value['content'];
    let authors = [this.sessionService.getSession()];

    if (title != '' && content != '' && authors[0] != null) {
      const body = {
        id: id,
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

      return this.httpClient.post<IdDTO>(environment.ROOT_URL + '/article/insert', body, {headers});
    }
  }

  public deleteArticle(articleId: string) {
    return this.httpClient.delete(environment.ROOT_URL + '/article/delete/' + articleId);
  }

  public insertComment(articleId: string, commentForm: FormGroup) {

    const body = {
      content: commentForm.value["comment"],
      articleId: articleId,
      appUserId: this.sessionService.getSession()
    };

    console.log(body);

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });

    return this.httpClient.post(environment.ROOT_URL + '/article/insert/comment', body, {headers});
  }

  public likeArticle(articleId: string): Observable<number> {
    return this.httpClient.put<number>(environment.ROOT_URL + '/article/like/' + articleId, null, {headers: this.getAuthHeaders()});
  }

  public unlikeArticle(articleId: string): Observable<number> {
    return this.httpClient.put<number>(environment.ROOT_URL + '/article/unlike/' + articleId, null, {headers: this.getAuthHeaders()});
  }
}
