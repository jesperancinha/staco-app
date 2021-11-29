import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SearchComponent} from "./components/search/search.component";
import {LoginComponent} from "./components/login/login.component";
import {SearchDynamoComponent} from "./components/search-dynamo/search.dynamo.component";

const routes: Routes = [
  {path: 'search', component: SearchComponent},
  {path: 'search/dynamo', component: SearchDynamoComponent},
  {path: 'service', component: SearchComponent},
  {path: 'login', component: LoginComponent},
  {path: '', component: LoginComponent},
  {path: ':anything', component: LoginComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: false})],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
