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
      this.user.role = [{ roleName: 'Member' }];
    }

    this.usersService.createUser(this.user).subscribe(
      (data) => {
        console.log('OVO JE DODANI USER:', data);
        this.goToUsersList();
      },
      (error) => console.error('Error creating user:', error)
    );
  }

  onRoleChange(event: Event) {
    const selectedRole = (event.target as HTMLSelectElement).value;
    this.user.role = [{ roleName: selectedRole }];
  }

  onSubmit() {
    if (!this.user.role || this.user.role.length === 0) {
      this.user.role = [{ roleName: 'Member' }];
    }

    if (!this.user.role[0].roleName) {
      this.user.role[0].roleName = 'Member';
    }

    this.saveUser();
  }

  goToUsersList() {
    this.router.navigate(['/users']);
  }
}
