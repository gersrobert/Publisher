import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ArticleDetailedDTO} from '../dto/dtos';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import * as shajs from 'sha.js';

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
      passwordHash: shajs('sha256').update({password}).digest('hex')
    };

    const observable = this.httpClient.post<string>(environment.ROOT_URL + '/user/login', body);
    observable.subscribe(response => {
      sessionStorage.setItem(this.SESSION_KEY, response);
    });
    return observable;
  }

}
