import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AccommodationService} from "../../service/complex/accommodation/accommodation.service";
import {Accommodation} from "../../service/complex/accommodation/accommodation";
import {ShortForm} from "../../service/basic/shortform";
import {formatDate} from "@angular/common";
import {BasicSearchService} from "../../service/basic/basic-search.service";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-accommodation',
  templateUrl: './accommodation.component.html',
  styleUrls: ['./accommodation.component.css']
})
export class AccommodationComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private accommodationService: AccommodationService,
              private basicSearchService: BasicSearchService,
              private router: Router,
              private app: AppComponent) {
  }

  public id: any;
  public isEditable: boolean = false;
  public accommodation: Accommodation = new Accommodation(null, new Date(), new Date(), 0, 0, 0,
    new ShortForm(0, ""), new ShortForm(0, ""), new ShortForm(0, ""), 0, 0, 0);
  public in_date: string = "";
  public out_date: string = "";
  public title: string = "Редактирование размещения";

  ngOnInit(): void {
    this.app.loginCheck();
    this.route.params.subscribe(param => {
      this.id = param['id'];
      this.accommodation.tourId = param['tour_id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      if (sessionStorage.getItem("TOUR_EDIT")) {
        this.accommodationService.findById(this.id).subscribe(result => {
          this.accommodation = result;
          this.in_date = formatDate(result.checkInDate, 'yyyy-MM-dd', 'en-US');
          this.out_date = formatDate(result.checkOutDate, 'yyyy-MM-dd', 'en-US');
        }, error => {
          console.log(`Error ${error}`);
        });
      } else {
        this.loadPresaveSafe();
      }
    } else {
      this.title = `Создание нового размещения`;
      this.loadPresaveSafe();
    }
  }

  save() {
    this.accommodation.checkInDate = new Date(this.in_date);
    this.accommodation.checkOutDate = new Date(this.out_date);
    this.accommodationService.save(this.accommodation).subscribe(result => {
      this.accommodation = result;
      this.title = `Редактирование размещения`;
      this.isEditable = false;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  loadPresaveSafe() {
    this.isEditable = true;
    if (sessionStorage.getItem("AE_PRESAVE")) {
      this.accommodation = JSON.parse(sessionStorage.getItem("AE_PRESAVE") || 'new Accommodation(null, new Date(), new Date(), 0, 0, 0, \n' +
        'new ShortForm(0, ""), new ShortForm(0, ""),new ShortForm(0, ""))');
      if (sessionStorage.getItem("AE_ROOM")) {
        this.accommodation.room = JSON.parse(sessionStorage.getItem("AE_ROOM") || 'new ShortForm(0, "")');
        this.basicSearchService.findParentById('room', this.accommodation.room.id).subscribe(result => {
          this.accommodation.hotel = result;
        });
        sessionStorage.removeItem("AE_ROOM");
      }
      if (sessionStorage.getItem("AE_ACCTYPE")) {
        this.accommodation.accType = JSON.parse(sessionStorage.getItem("AE_ACCTYPE") || 'new ShortForm(0, "")');
        sessionStorage.removeItem("AE_ACCTYPE");
      }
      sessionStorage.removeItem("AE_PRESAVE");
      this.in_date = formatDate(this.accommodation.checkInDate, 'yyyy-MM-dd', 'en-US');
      this.out_date = formatDate(this.accommodation.checkOutDate, 'yyyy-MM-dd', 'en-US');
    }
  }

  makePresave() {
    if (this.in_date != "") {
      this.accommodation.checkInDate = new Date(this.in_date);
    }
    if (this.out_date != "") {
      this.accommodation.checkOutDate = new Date(this.out_date);
    }
    sessionStorage.setItem(`AE_PRESAVE`, JSON.stringify(this.accommodation));
  }

  deleteConfirm() {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      alert("Удаление в процессе реализации.");
    }
  }

  findRoom() {
    this.makePresave();
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl("search_components/room/AE_ROOM");
  }

  findAccType() {
    this.makePresave();
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl("search_components/accomm_type/AE_ACCTYPE");
  }

  return() {
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl(`tour/${this.accommodation.tourId}`);
  }

}
