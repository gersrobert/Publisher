import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'web';

  readonly ROOT_URL = 'http://localhost:10420';

  posts: any;

  constructor(private http: HttpClient) {}

  getHelloWorld() {
    this.posts = this.http.get(this.ROOT_URL + '/hello');
    console.log(this.posts);
  }
}
