import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Users } from '../_model/users';
import { UsersService } from '../_service/users.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit {
  user: Users = new Users();
  constructor(private usersService: UsersService, private router: Router) {}

  ngOnInit(): void {}

  saveUser() {
    if (!this.user.role || this.user.role.length === 0) {
      this.user.role = [{ roleName: '' }];
    }

    this.usersService.createUser(this.user).subscribe(
      (data) => {
        console.log('OVO JE DODANI USER:', data);
        this.goToUsersList();
      },
      (error) => console.log(error)
    );
  }

  goToUsersList() {
    this.router.navigate(['/users']);
  }

  onSubmit() {
    console.log(this.user);

    // Osiguraj da je role niz s barem jednim objektom
    if (!this.user.role || this.user.role.length === 0) {
      this.user.role = [{ roleName: 'User' }]; // Default role ako nije uneseno
    }

    this.saveUser();
  }
}
