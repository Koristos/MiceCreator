import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TourService} from "../../service/complex/tour/tour.service";
import {BasicSearchService} from "../../service/basic/basic-search.service";
import {ShortForm} from "../../service/basic/shortform";
import {FullTour} from "../../service/complex/tour/full-tour";
import {Tour} from "../../service/complex/tour/tour";
import {Country} from "../../service/basic/country/country";
import {formatDate} from "@angular/common";
import {FileService} from "../../service/files/file.service";
import {AppComponent} from "../../app.component";
import {AccommodationService} from "../../service/complex/accommodation/accommodation.service";
import {FlightService} from "../../service/complex/flight/flight.service";
import {HotelEventService} from "../../service/complex/hotelevent/hotel-event.service";
import {RegionEventService} from "../../service/complex/regionevent/region-event.service";

@Component({
  selector: 'app-tour',
  templateUrl: './tour.component.html',
  styleUrls: ['./tour.component.css']
})
export class TourComponent implements OnInit {

  public id: any;
  public fullTour: FullTour = new FullTour(new Tour(null, 0, new Date(), new Date(), 0, 0, "", new ShortForm(0, "")),
    [], [], [], []);
  public countryList: ShortForm[] = [];
  public title: string = '';
  public isNew: boolean = false;
  public editEnabled: boolean = false;
  public startDate: string = "";
  public endDate: string = "";


  constructor(private route: ActivatedRoute,
              private tourService: TourService,
              private basicSearchService: BasicSearchService,
              private router: Router,
              private fileService: FileService,
              private app: AppComponent,
              private accommService: AccommodationService,
              private flightService: FlightService,
              private hotelEventService: HotelEventService,
              private regionEventService: RegionEventService
  ) {
  }

  ngOnInit(): void {
    this.app.loginCheck();
    this.route.params.subscribe(param => {
      this.id = param['id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      this.tourService.findById(this.id).subscribe(result => {
        this.fullTour = result;
        this.countryList.push(result.tour.country);
        this.startDate = formatDate(result.tour.startDate, 'yyyy-MM-dd', 'en-US');
        this.endDate = formatDate(result.tour.endDate, 'yyyy-MM-dd', 'en-US');
      }, error => {
        console.log(`Error ${error}`);
      });
      this.title = `ТУР №${this.id}`;
    } else {
      this.basicSearchService.findAll('country').subscribe(result => {
        this.countryList = result;
      }, error => {
        console.log(`Error ${error}`);
      });
      this.title = `НОВЫЙ ТУР`;
      this.isNew = true;
      this.editEnabled = true;
    }
  }

  deleteConfirm() {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      alert("Удаление в процессе реализации.");
    }
  }

  save() {
    this.fullTour.tour.startDate = new Date(this.startDate);
    this.fullTour.tour.endDate = new Date(this.endDate);
    this.tourService.save(this.fullTour.tour).subscribe(result => {
      this.fullTour.tour = result;
      this.title = `ТУР №${result.id}`;
      this.isNew = false;
      this.editEnabled = false;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  edit() {
    this.basicSearchService.findAll('country').subscribe(result => {
      this.countryList = result;
      this.editEnabled = true;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  dateToPattern(date: Date) {
    return formatDate(date, 'dd-MM-yyyy', 'en-US');
  }

  dateTimeToPattern(date: Date) {
    return formatDate(date, 'HH:mm dd.MM.YYYY', 'en-US');
  }

  deleteAccommodationConfirm(accommId: number) {
    if (confirm("Вы уверены, что хотите удалить размещение?")) {
      this.accommService.delete(accommId).subscribe(result => {
        if (result) {
          this.ngOnInit();
        }
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  deleteFlightConfirm(flightId: number) {
    if (confirm("Вы уверены, что хотите удалить перелет?")) {
      this.flightService.delete(flightId).subscribe(result => {
        if (result) {
          this.ngOnInit();
        }
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  deleteRegEventConfirm(regEventId: number) {
    if (confirm("Вы уверены, что хотите удалить региональный эвент?")) {
      this.regionEventService.delete(regEventId).subscribe(result => {
        if (result) {
          this.ngOnInit();
        }
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  deleteHotelEventConfirm(hotelEventId: number) {
    if (confirm("Вы уверены, что хотите удалить отельный эвент?")) {
      this.hotelEventService.delete(hotelEventId).subscribe(result => {
        if (result) {
          this.ngOnInit();
        }
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  downloadPresentation() {
    this.fileService.getPresentation(this.fullTour.tour.id);
  }

  downloadEstimate() {
    this.fileService.getEstimate(this.fullTour.tour.id);
  }

  showAccommodation(accommId: number) {
    sessionStorage.setItem(`TOUR_EDIT`, `true`);
    this.router.navigateByUrl(`accommodation/${accommId}/${this.fullTour.tour.id}`);
  }

  showFlight(flightId: number) {
    sessionStorage.setItem(`TOUR_EDIT`, `true`);
    this.router.navigateByUrl(`flight/${flightId}/${this.fullTour.tour.id}`);
  }

  showRegEvent(eventId: number) {
    sessionStorage.setItem(`TOUR_EDIT`, `true`);
    this.router.navigateByUrl(`region_event/${eventId}/${this.fullTour.tour.id}`);
  }

  showHotelEvent(eventId: number) {
    sessionStorage.setItem(`TOUR_EDIT`, `true`);
    this.router.navigateByUrl(`hotel_event/${eventId}/${this.fullTour.tour.id}`);
  }

}

