import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ArticleDetailedDTO} from '../dto/dtos';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import * as shajs from 'sha.js';
import {sha256} from 'js-sha256';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  readonly SESSION_KEY = 'sessionKey';


  constructor(private httpClient: HttpClient) {
  }

  public login(userName: string, password: string): Observable<string> {
    const body = {
      username: userName,
      passwordHash: sha256(password)
    };

    console.log(body, password);

    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Accept': 'application/json'
    });

    const observable = this.httpClient.post<string>(environment.ROOT_URL + '/user/login', body, {headers});
    observable.subscribe(response => {
      sessionStorage.setItem(this.SESSION_KEY, response);
    });
    return observable;
  }

}
