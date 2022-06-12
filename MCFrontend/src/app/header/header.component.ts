import {Component, OnInit} from '@angular/core';
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public name: string = "";
  public authenticated: boolean = false;

  constructor(private app: AppComponent) {
  }

  ngOnInit(): void {
    this.app.getUserName.subscribe(next => this.name = next);
    this.app.getAuthenticated.subscribe(next => this.authenticated = next);
  }

  logout() {
    this.app.logout();
  }

}
