import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Publisher} from '../dto/dtos';
import {AbstractService} from './abstract.service';

@Injectable({
  providedIn: 'root'
})
export class PublisherService extends AbstractService {

  public getTopPublishers(): Observable<Publisher[]> {
    return this.httpClient.get<Publisher[]>(environment.ROOT_URL + '/publisher/top');
  }

  public getRowOfPublisher(publisherId: string) {
    return this.httpClient.get<Publisher>(environment.ROOT_URL + '/publisher/rowOf/' + publisherId);
  }
}
