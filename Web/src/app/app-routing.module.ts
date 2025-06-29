import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

;

import {LoginComponent} from './features/login/login.component';
// ... import your guarded components:
import {HomeComponent} from './features/home/home.component';
import {AuthGuard} from './core/guards/auth.guard';
import {AuthLayoutComponent} from './features/layouts/auth-layout/auth-layout.component';
import {AppLayoutComponent} from './features/layouts/app-layout/app-layout.component';
import {ForbiddenComponent} from './features/forbidden/forbidden.component';
import {RegisterComponent} from './features/register/register.component';

const routes: Routes = [
  {
    path: '',
    component: AuthLayoutComponent,
    children: [
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegisterComponent},
      {path: 'forbidden', component: ForbiddenComponent},
      {path: '', redirectTo: 'login', pathMatch: 'full'},
    ]
  },

  {
    path: '',
    component: AppLayoutComponent,
    children: [
      {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
      {
        path: 'ads',
        loadChildren: () =>
          import('./features/ad/ad.module').then(m => m.AdModule)
      },
      {path: '**', redirectTo: 'home'}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
