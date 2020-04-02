import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SessionService} from '../service/session.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-screen',
  templateUrl: './login-screen.component.html',
  styleUrls: ['./login-screen.component.less']
})
export class LoginScreenComponent implements OnInit {

  loginForm: FormGroup;
  showPasswordError = false;


  constructor(private formBuilder: FormBuilder,
              private sessionService: SessionService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });
  }

  public onSubmit() {
    this.sessionService.login(this.loginForm.value['username'], this.loginForm.value['password']).subscribe(
      value => this.router.navigate(['home/articleList']),
      error => this.showPasswordError = true
    );
  }

}
