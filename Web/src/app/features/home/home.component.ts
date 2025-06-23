import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../core/services/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  standalone: false,
  styleUrl: './home.component.css'
})
export class HomeComponent {
  constructor(private authService: AuthService) {
  }

  logout() {
    this.authService.logout();
  }

}
