import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Users } from '../_model/users';
import { UsersService } from '../_service/users.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css'],
})
export class UsersListComponent implements OnInit {
  users: Users[];

  constructor(
    private usersService: UsersService,
    private router: Router,
    private cdRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.getUsers();
  }

  private getUsers() {
    this.usersService.getUsersList().subscribe(
      (data) => {
        this.users = data;
        console.log('Users fetched:', this.users);
      },
      (error) => console.error('Error fetching users:', error)
    );
  }

  hasRole(user: Users, roleName: string): boolean {
    return user.role.some((r) => r.roleName === roleName);
  }

  userDetails(userId: number) {
    this.router.navigate(['user-details', userId]);
  }

  updateUser(userId: number) {
    this.router.navigate(['update-user', userId]);
  }

  deleteUser(userId: number) {
    if (confirm("Are you sure you want to delete this user?")) {
      this.usersService.deleteUser(userId).subscribe(
        (response) => {
          console.log(response);
          this.getUsers(); // Reload the users list after deletion
        },
        (error) => {
          console.error('Error deleting user:', error);
        }
      );
    }
  }
}
