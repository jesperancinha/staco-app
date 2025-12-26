import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/types/http';
import {Router} from '@angular/router';
import {AppService} from "../../services/app.service";
import {MatGridList, MatGridTile} from "@angular/material/types/grid-list";
import {MatCard, MatCardTitle} from "@angular/material/types/card";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'login-component',
  standalone: true,
  templateUrl: './login.component.html',
  imports: [
    MatGridList,
    MatGridTile,
    MatCard,
    MatCardTitle,
    FormsModule
  ],
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

    credentials = {username: '', password: ''};

    error: number | undefined;
    private submitted: boolean | undefined;
    private loading: boolean | undefined;

    constructor(public appService: AppService, private http: HttpClient, private router: Router) {
      if(!router.url.endsWith("login")){
        router.navigateByUrl("/login")
      }
    }

    login() {
        this.submitted = true;
        this.loading = true;
        this.appService.login(this.credentials.username, this.credentials.password)
            .subscribe(data => {
                this.router.navigateByUrl('/search');
                this.appService.token = this.credentials
            })

    }

  goToDynamoDb() {
    this.router.navigateByUrl('/search/dynamo');
  }
}
