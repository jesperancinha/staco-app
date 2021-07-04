import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {AppService} from "../../services/app.service";

@Component({
  selector: 'login-component',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  credentials = this.initialize();

  private initialize() {
    return {username: '', password: ''};
  }
  error: number;

  constructor(private app: AppService, private http: HttpClient, private router: Router) {
  }

  login() {
    this.app.authenticate(this.credentials, () => {
      this.router.navigateByUrl('/search');
    });
    return false;
  }

}
