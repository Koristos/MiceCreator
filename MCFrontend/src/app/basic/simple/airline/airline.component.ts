import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AirlineserviceService} from "../../../service/basic/airline/airlineservice.service";
import {Airline} from "../../../service/basic/airline/airline";
import {AppComponent} from "../../../app.component";

@Component({
  selector: 'app-airline',
  templateUrl: './airline.component.html',
  styleUrls: ['./airline.component.css']
})
export class AirlineComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public airline: Airline = new Airline(null, "", "");
  public title: string = "СОЗДАНИЕ НОВОЙ АВИАКОМПАНИИ";
  public isDeleteOn: boolean = false;

  constructor(private route: ActivatedRoute,
              private airlineService: AirlineserviceService,
              private app: AppComponent,
              private router: Router) {
  }

  ngOnInit(): void {
    this.app.loginCheck();
    this.isDeleteOn = (this.app.user.role.includes("ROLE_ADMIN"));
    this.route.params.subscribe(param => {
      this.id = param['id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      this.title = "РЕДАКТИРОВАНИЕ АВИАКОМПАНИИ";
      this.editEnabled = false;
      this.airlineService.findById(this.id).subscribe(result => {
        this.airline = result;
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  save() {
    if (confirm("Вы уверены, что хотите сохранить изменения?")) {
      this.airlineService.save(this.airline).subscribe(result => {
        this.airline = result;
        this.title = "РЕДАКТИРОВАНИЕ АВИАКОМПАНИИ";
        this.editEnabled = false;
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  deleteConfirm() {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      this.airlineService.delete(this.id).subscribe(result => {
        if (result){
          this.router.navigateByUrl("/search_components/airline");
        }
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

}
