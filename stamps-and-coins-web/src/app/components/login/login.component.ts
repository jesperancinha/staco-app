import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {AppService} from "../../services/app.service";

@Component({
    selector: 'login-component',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent {

    credentials = {username: '', password: ''};

    error: number;
    private submitted: boolean;
    private loading: boolean;

    constructor(public appService: AppService, private http: HttpClient, private router: Router) {

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
}
