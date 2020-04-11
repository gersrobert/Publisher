import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SessionService} from '../service/session.service';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {sha256} from 'js-sha256';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.less']
})
export class RegisterUserComponent implements OnInit {

  registerForm: FormGroup;
  showUserNameError = false;
  showPasswordError = false;

  constructor(private formBuilder: FormBuilder,
              private sessionService: SessionService,
              private router: Router,
              private httpClient: HttpClient,) {
  }

  ngOnInit(): void { this.registerForm = this.formBuilder.group({
      username: '',
      firstname: '',
      lastname: '',
      password: '',
      passwordCheck: ''
    });
  }

  public onSubmit() {
    let username = this.registerForm.value['username'];
    let firstname = this.registerForm.value['firstname'];
    let lastname = this.registerForm.value['lastname'];
    let password = this.registerForm.value['password'];
    let passwordCheck = this.registerForm.value['passwordCheck'];


    if (username != '' && firstname != '' && lastname != '' && password != '' && passwordCheck != '') {
      if (password != passwordCheck) {
        this.showPasswordError = true;
      } else {

        const body = {
          firstName: firstname,
          lastName: lastname,
          userName: username,
          passwordHash: sha256(password)
        };

        const headers = new HttpHeaders({
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        });

        this.httpClient.post(environment.ROOT_URL + '/user/register', body, {headers}).subscribe(response => {
          console.log('success', response);
          this.router.navigate(['home/login']);
        }, error => {
          console.log('error', error);
          this.showUserNameError = true;
        });;
      }
  }
  }
}
