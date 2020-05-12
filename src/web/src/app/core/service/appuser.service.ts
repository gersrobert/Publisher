import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AppUserDetailedDTO} from '../dto/dtos';
import {environment} from '../../../environments/environment';
import {SessionService} from './session.service';
import {AbstractService} from './abstract.service';

@Injectable({
  providedIn: 'root'
})
export class AppuserService extends AbstractService{

  public getAppUser(userId: string): Observable<AppUserDetailedDTO> {
    if (!userId) {
      return null;
    }
    return this.httpClient.get<AppUserDetailedDTO>(environment.ROOT_URL + '/user/detailed/' + userId);
  }

  public getActions(articleId: string): Observable<string[]> {
    return this.httpClient.get<string[]>(environment.ROOT_URL + '/user/actions/' + sessionStorage.getItem(SessionService.SESSION_KEY) + '/' + articleId);
  }

}
