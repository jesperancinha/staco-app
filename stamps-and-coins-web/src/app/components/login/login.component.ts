import {Component, inject, signal} from '@angular/core';
import {Router} from '@angular/router';
import {AppService} from '../../services/app.service';
import {MatGridList, MatGridTile} from '@angular/material/grid-list';
import {MatCard, MatCardTitle} from '@angular/material/card';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-login',
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
  readonly appService = inject(AppService);
  private readonly router = inject(Router);

  credentials = {username: '', password: ''};

  readonly error = signal<number | null>(null);
  private readonly submitted = signal(false);
  private readonly loading = signal(false);

  constructor() {
    if (!this.router.url.endsWith('login')) {
      this.router.navigateByUrl('/login');
    }
  }

  login() {
    this.submitted.set(true);
    this.loading.set(true);
    this.appService.login(this.credentials.username, this.credentials.password)
      .subscribe(() => {
        this.router.navigateByUrl('/search');
        this.appService.token.set(this.credentials);
      });
  }

  goToDynamoDb() {
    this.router.navigateByUrl('/search/dynamo');
  }
}
