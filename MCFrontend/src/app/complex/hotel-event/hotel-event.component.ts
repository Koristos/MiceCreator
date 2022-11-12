import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BasicSearchService} from "../../service/basic/basic-search.service";
import {ShortForm} from "../../service/basic/shortform";
import {formatDate} from "@angular/common";
import {HotelEventService} from "../../service/complex/hotelevent/hotel-event.service";
import {HotelEvent} from "../../service/complex/hotelevent/hotelevent";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-hotel-event',
  templateUrl: './hotel-event.component.html',
  styleUrls: ['./hotel-event.component.css']
})
export class HotelEventComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private hotelEventService: HotelEventService,
              private basicSearchService: BasicSearchService,
              private router: Router,
              private app: AppComponent) {
  }

  public id: any;
  public isEditable: boolean = false;
  public hotelEvent: HotelEvent = new HotelEvent(null, new Date(), 0, 0, 0, 0,
    new ShortForm(0, ""), 0, 0, new Date());
  public event_date: string = "";
  public title: string = "Редактирование отельного эвента";
  public hotel: ShortForm = new ShortForm(0, "");
  public create_date: string = "";

  ngOnInit(): void {
    this.app.loginCheck();
    this.route.params.subscribe(param => {
      this.id = param['id'];
      this.hotelEvent.tourId = param['tour_id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      if (sessionStorage.getItem("TOUR_EDIT")) {
        this.hotelEventService.findById(this.id).subscribe(result => {
          this.hotelEvent = result;
          this.event_date = formatDate(result.eventDate, 'yyyy-MM-dd', 'en-US');
          this.create_date = formatDate(result.creationDate, 'yyyy-MM-dd', 'en-US');
          this.basicSearchService.findParentById('hotel_service', this.hotelEvent.service.id).subscribe(result => {
            this.hotel = result;
          }, error => {
            console.log(`Error ${error}`);
          });
        }, error => {
          console.log(`Error ${error}`);
        });
      } else {
        this.loadPresaveSafe();
      }
    } else {
      this.title = `Создание нового отельного эвента`;
      this.loadPresaveSafe();
    }
  }

  save() {
    this.hotelEvent.eventDate = new Date(this.event_date);
    this.hotelEvent.creationDate = new Date(this.create_date);
    this.hotelEventService.save(this.hotelEvent).subscribe(result => {
      this.hotelEvent = result;
      this.title = `Редактирование отельного эвента`;
      this.isEditable = false;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  loadPresaveSafe() {
    this.isEditable = true;
    if (sessionStorage.getItem("HE_PRESAVE")) {
      this.hotelEvent = JSON.parse(sessionStorage.getItem("HE_PRESAVE") || 'new HotelEvent(null, new Date(), 0,0, 0,\n' +
        'new ShortForm(0, ""), 0)');
      this.hotel = JSON.parse(sessionStorage.getItem("HE_PRESAVE_HOTEL") || 'new ShortForm(0, ""), 0)');
      if (sessionStorage.getItem("HE_HOTEL_SERV")) {
        this.hotelEvent.service = JSON.parse(sessionStorage.getItem("HE_HOTEL_SERV") || 'new ShortForm(0, "")');
        this.basicSearchService.findParentById('hotel_service', this.hotelEvent.service.id).subscribe(result => {
          this.hotel = result;
        }, error => {
          console.log(`Error ${error}`);
        });
        sessionStorage.removeItem("HE_HOTEL_SERV");
      }
      sessionStorage.removeItem("HE_PRESAVE_HOTEL");
      sessionStorage.removeItem("HE_PRESAVE");
      this.event_date = formatDate(this.hotelEvent.eventDate, 'yyyy-MM-dd', 'en-US');
      this.create_date = formatDate(this.hotelEvent.creationDate, 'yyyy-MM-dd', 'en-US');
    }
  }

  makePresave() {
    if (this.event_date != "") {
      this.hotelEvent.eventDate = new Date(this.event_date);
    }
    if (this.create_date != "") {
      this.hotelEvent.creationDate = new Date(this.create_date);
    }
    sessionStorage.setItem(`HE_PRESAVE_HOTEL`, JSON.stringify(this.hotel));
    sessionStorage.setItem(`HE_PRESAVE`, JSON.stringify(this.hotelEvent));
  }

  deleteConfirm() {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      alert("Удаление в процессе реализации.");
    }
  }

  findHotelService() {
    this.makePresave();
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl("search_components/hotel_service/HE_HOTEL_SERV");
  }

  return() {
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl(`tour/${this.hotelEvent.tourId}`);
  }

}
