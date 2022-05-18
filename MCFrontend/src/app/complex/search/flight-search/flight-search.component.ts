import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {formatDate} from "@angular/common";
import {Flight} from "../../../service/complex/flight/flight";
import {FlightService} from "../../../service/complex/flight/flight.service";
import {FlightSearchParams} from "../../../service/complex/flight/flight-search-params";
import {ShortForm} from "../../../service/basic/shortform";

@Component({
  selector: 'app-flight-search',
  templateUrl: './flight-search.component.html',
  styleUrls: ['./flight-search.component.css']
})
export class FlightSearchComponent implements OnInit {

  public flightList: Flight[] = [];
  public isSearchCommitted: boolean = false;
  public flightSearchParams: FlightSearchParams = new FlightSearchParams();
  public airline: ShortForm = new ShortForm(0, "");
  public depAirport: ShortForm = new ShortForm(0, "");
  public arrAirport: ShortForm = new ShortForm(0, "");

  constructor(private route: ActivatedRoute,
              private router: Router,
              private flightService: FlightService) {
  }

  ngOnInit(): void {
    if (sessionStorage.getItem("FS_AIRLINE")) {
      this.airline = JSON.parse(sessionStorage.getItem("FS_AIRLINE") || 'new ShortForm(0, "")');
    }
    if (sessionStorage.getItem("FS_DEP_AIRPORT")) {
      this.depAirport = JSON.parse(sessionStorage.getItem("FS_DEP_AIRPORT") || 'new ShortForm(0, "")');
    }
    if (sessionStorage.getItem("FS_ARR_AIRPORT")) {
      this.arrAirport = JSON.parse(sessionStorage.getItem("FS_ARR_AIRPORT") || 'new ShortForm(0, "")');
    }
  }

  search() {
    this.flightSearchParams.departureAirportId = this.depAirport.id;
    this.flightSearchParams.arrivalAirportId = this.arrAirport.id;
    this.flightSearchParams.airlineId = this.airline.id;
    this.flightService.findByParams(this.flightSearchParams.prepareForSending()).subscribe(result => {
      this.flightList = result;
      this.isSearchCommitted = true;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  dateToPattern(date: Date) {
    return formatDate(date, 'dd.MM.YYYY', 'en-US')
  }

  dateTimeToPattern(date: Date) {
    return formatDate(date, 'dd.MM.YYYY HH:mm', 'en-US')
  }

  findAirline() {
    this.router.navigateByUrl("search_components/airline/FS_AIRLINE");
  }

  findDepPort() {
    this.router.navigateByUrl("search_components/airport/FS_DEP_AIRPORT");
  }

  findArrPort() {
    this.router.navigateByUrl("search_components/airport/FS_ARR_AIRPORT");
  }

}
