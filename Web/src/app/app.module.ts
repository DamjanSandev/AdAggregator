import {NgModule} from '@angular/core';
import {BrowserModule, provideClientHydration} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule, provideHttpClient} from '@angular/common/http';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {LoginComponent} from './features/login/login.component';
import {HomeComponent} from './features/home/home.component';
import {ForbiddenComponent} from './features/forbidden/forbidden.component';
import {AdModule} from './features/ad/ad.module';
import {JwtInterceptor} from './core/interceptors/jwt-interceptor.interceptor';
import {NavbarComponent} from './features/navbar/navbar.component';
import {AppLayoutComponent} from './features/layouts/app-layout/app-layout.component';
import {AuthLayoutComponent} from './features/layouts/auth-layout/auth-layout.component';
import {RegisterComponent} from './features/register/register.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ForbiddenComponent,
    NavbarComponent,
    AppLayoutComponent,
    AuthLayoutComponent,
    RegisterComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    AdModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
  ],
  exports: [
    NavbarComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
