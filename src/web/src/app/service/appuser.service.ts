import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AppUserDTO, Publisher} from '../dto/dtos';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppuserService {

  constructor(private httpClient: HttpClient) {}

  public getAppUser(userId: string): Observable<AppUserDTO> {
    return this.httpClient.get<AppUserDTO>(environment.ROOT_URL + '/user/' + userId);
  }
}
