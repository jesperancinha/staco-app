import {Component, inject} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {AppService} from './services/app.service';
import {catchError, Observable, of, retry} from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  imports: [
    RouterOutlet,
    RouterLinkActive,
    RouterLink
  ],
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  readonly appService = inject(AppService);
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  title = 'stamps-and-coins-manager';

  logout() {
    const url = '/api/staco/service/logout';
    this.http.post(url, {}).pipe(
      retry(3), catchError(this.handleError<string>())).subscribe(() => {
        this.appService.token.set(null);
        window.location.href = "/login";
      }
    );
  }

  handleError<T>(operation = 'operation', result?: T) {
    return (error: HttpErrorResponse): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      this.router.navigateByUrl('/login');
      return of(result as T);
    };
  }

}
