import {Injectable} from '@angular/core';
import {Observable, Observer} from 'rxjs';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {sha256} from 'js-sha256';
import {AppUserDTO} from '../dto/dtos';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public static readonly SESSION_KEY = 'sessionKey';

  private observers: Observer<string>[] = [];

  constructor(private httpClient: HttpClient) {
  }

  public login(userName: string, password: string): Observable<string> {
    const body = {
      username: userName,
      passwordHash: sha256(password)
    };

    console.log(body, password);

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });

    const observable = this.httpClient.post<string>(environment.ROOT_URL + '/user/login', body, {headers});
    observable.subscribe(response => {
      sessionStorage.setItem(SessionService.SESSION_KEY, response);
      this.observers.forEach(observer => observer.next(response));
    });
    return observable;
  }

  public getSession(): string {
    return sessionStorage.getItem(SessionService.SESSION_KEY);
  }

  public getCurrentUser(): Observable<AppUserDTO> {
    const id = sessionStorage.getItem(SessionService.SESSION_KEY);
    if (id == null) {
      return null;
    }

    return this.httpClient.get<AppUserDTO>(environment.ROOT_URL + '/user/' + id);
  }

  public subscribeLoginEvents(observer: Observer<string>) {
    this.observers.push(observer);
  }
}
