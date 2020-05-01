import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SessionService} from './session.service';

@Injectable({
  providedIn: 'root'
})
export abstract class AbstractService {

  protected constructor(protected httpClient: HttpClient,
                        protected sessionService: SessionService) {
  }

  protected getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Auth-Token': this.sessionService.getSession()
    });
  }
}
