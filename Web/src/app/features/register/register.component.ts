import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../core/services/auth.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';

interface RegisterRequest {
  username: string;
  password: string;
  repeatPassword: string;
  name: string;
  surname: string;
  role: 'ROLE_USER';
}

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  form!: FormGroup;
  errorMessage = '';


  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      repeatPassword: ['', Validators.required],
      name: ['', Validators.required],
      surname: ['', Validators.required]
    });
  }

  register() {
    if (this.form.invalid) return;
    const {password, repeatPassword} = this.form.value;
    if (password !== repeatPassword) {
      this.errorMessage = 'Passwords do not match';
      return;
    }
    const payload: RegisterRequest = {
      ...this.form.value,
      role: 'ROLE_USER'
    };

    this.authService.register(payload).subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => this.errorMessage = "Registration failed. Please try again."
    })


  }

}
