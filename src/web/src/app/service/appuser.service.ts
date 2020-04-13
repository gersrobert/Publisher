import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AppUserDetailedDTO} from '../dto/dtos';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppuserService {

  constructor(private httpClient: HttpClient) {}

  public getAppUser(userId: string): Observable<AppUserDetailedDTO> {
    return this.httpClient.get<AppUserDetailedDTO>(environment.ROOT_URL + '/user/detailed/' + userId);
  }
}
