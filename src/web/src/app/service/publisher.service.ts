import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Publisher} from '../dto/dtos';

@Injectable({
  providedIn: 'root'
})
export class PublisherService {

  constructor(private httpClient: HttpClient) {}

  public getTopPublishers(): Observable<Publisher[]> {
    return this.httpClient.get<Publisher[]>(environment.ROOT_URL + '/publisher/top');
  }

  public getRowOfPublisher(publisherId: string) {
    return this.httpClient.get<Publisher>(environment.ROOT_URL + '/publisher/rowOf/' + publisherId);
  }
}
