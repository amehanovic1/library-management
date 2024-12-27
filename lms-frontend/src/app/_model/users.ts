export class Users {
  userId: number;
  username: string;
  name: string;
  password: string;
  role: { roleName: string }[] = [];
}
