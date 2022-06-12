import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BasicSearchService} from "../../service/basic/basic-search.service";
import {ShortForm} from "../../service/basic/shortform";
import {formatDate} from "@angular/common";
import {FlightService} from "../../service/complex/flight/flight.service";
import {Flight} from "../../service/complex/flight/flight";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-flight',
  templateUrl: './flight.component.html',
  styleUrls: ['./flight.component.css']
})
export class FlightComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private flightService: FlightService,
              private basicSearchService: BasicSearchService,
              private router: Router,
              private app: AppComponent) {
  }

  public id: any;
  public isEditable: boolean = false;
  public flight: Flight = new Flight(null, new Date(), new Date(), 0, 0, 0,
    new ShortForm(0, ""), new ShortForm(0, ""), new ShortForm(0, ""), 0);
  public dep_date: string = "";
  public arr_date: string = "";
  public title: string = "Редактирование перелета";

  ngOnInit(): void {
    this.app.loginCheck();
    this.route.params.subscribe(param => {
      this.id = param['id'];
      this.flight.tourId = param['tour_id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      if (sessionStorage.getItem("TOUR_EDIT")) {
        this.flightService.findById(this.id).subscribe(result => {
          this.flight = result;
          this.dep_date = formatDate(result.departureDate, 'yyyy-MM-ddThh:mm', 'en-US');
          this.arr_date = formatDate(result.arrivalDate, 'yyyy-MM-ddThh:mm', 'en-US');
        }, error => {
          console.log(`Error ${error}`);
        });
      } else {
        this.loadPresaveSafe();
      }
    } else {
      this.title = `Создание нового перелета`;
      this.loadPresaveSafe();
    }
  }

  save() {
    this.flight.departureDate = new Date(this.dep_date);
    console.log(this.flight.departureDate);
    this.flight.arrivalDate = new Date(this.arr_date);
    this.flightService.save(this.flight).subscribe(result => {
      this.flight = result;
      this.title = `Редактирование перелета`;
      this.isEditable = false;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  loadPresaveSafe() {
    this.isEditable = true;
    if (sessionStorage.getItem("FE_PRESAVE")) {
      this.flight = JSON.parse(sessionStorage.getItem("FE_PRESAVE") || 'new Flight(null, new Date(), new Date(), 0, 0, 0,\n' +
        'new ShortForm(0, ""), new ShortForm(0, ""),new ShortForm(0, ""), 0)');
      if (sessionStorage.getItem("FE_DEP_PORT")) {
        this.flight.departureAirport = JSON.parse(sessionStorage.getItem("FE_DEP_PORT") || 'new ShortForm(0, "")');
        sessionStorage.removeItem("FE_DEP_PORT");
      }
      if (sessionStorage.getItem("FE_ARR_PORT")) {
        this.flight.arrivalAirport = JSON.parse(sessionStorage.getItem("FE_ARR_PORT") || 'new ShortForm(0, "")');
        sessionStorage.removeItem("FE_ARR_PORT");
      }
      if (sessionStorage.getItem("FE_AIRLINE")) {
        this.flight.airline = JSON.parse(sessionStorage.getItem("FE_AIRLINE") || 'new ShortForm(0, "")');
        sessionStorage.removeItem("FE_AIRLINE");
      }
      sessionStorage.removeItem("FE_PRESAVE");
      this.dep_date = formatDate(this.flight.departureDate, 'yyyy-MM-ddThh:mm', 'en-US');
      this.arr_date = formatDate(this.flight.arrivalDate, 'yyyy-MM-ddThh:mm', 'en-US');
    }
  }

  makePresave() {
    if (this.dep_date != "") {
      this.flight.departureDate = new Date(this.dep_date);
    }
    if (this.arr_date != "") {
      this.flight.arrivalDate = new Date(this.arr_date);
    }
    sessionStorage.setItem(`FE_PRESAVE`, JSON.stringify(this.flight));
  }

  deleteConfirm() {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      alert("Удаление в процессе реализации.");
    }
  }

  findDepPort() {
    this.makePresave();
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl("search_components/airport/FE_DEP_PORT");
  }

  findArrPort() {
    this.makePresave();
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl("search_components/airport/FE_ARR_PORT");
  }

  findAirline() {
    this.makePresave();
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl("search_components/airline/FE_AIRLINE");
  }

  return() {
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl(`tour/${this.flight.tourId}`);
  }

}
