import {Component, OnInit} from '@angular/core';
import {AppComponent} from "../../app.component";
import {AuthorizationService} from "../../service/security/authorization.service";
import {Router} from "@angular/router";
import {User} from "../../service/security/user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public userName: string = "";
  public password: string = "";
  public errorMessage: string = "Неправильный логин или пароль.";
  public error: boolean = false;

  constructor(private app: AppComponent,
              private authorizationService: AuthorizationService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  login() {
    this.authorizationService.login(this.userName, this.password).subscribe(
      data => {
        this.app.user = new User(data.name, data.role, data.token);
        localStorage.setItem("MC_TOKEN", data.token);
        localStorage.setItem("MC_USER", JSON.stringify(this.app.user));
        this.error = false;
        this.router.navigateByUrl("/")
      },
      err => {
        this.error = true;
      }
    );
  }

}
