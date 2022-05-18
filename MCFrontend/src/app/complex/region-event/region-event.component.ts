import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BasicSearchService} from "../../service/basic/basic-search.service";
import {ShortForm} from "../../service/basic/shortform";
import {formatDate} from "@angular/common";
import {RegionEventService} from "../../service/complex/regionevent/region-event.service";
import {RegEvent} from "../../service/complex/regionevent/regevent";

@Component({
  selector: 'app-region-event',
  templateUrl: './region-event.component.html',
  styleUrls: ['./region-event.component.css']
})
export class RegionEventComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private regionEventService: RegionEventService,
              private basicSearchService: BasicSearchService,
              private router: Router) {
  }

  public id: any;
  public isEditable: boolean = false;
  public regEvent: RegEvent = new RegEvent(null, new Date(), 0, 0, 0,
    new ShortForm(0, ""), 0);
  public event_date: string = "";
  public title: string = "Редактирование регионального эвента";
  public region: ShortForm = new ShortForm(0, "");

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = param['id'];
      this.regEvent.tourId = param['tour_id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      if (sessionStorage.getItem("TOUR_EDIT")) {
        this.regionEventService.findById(this.id).subscribe(result => {
          this.regEvent = result;
          this.event_date = formatDate(result.eventDate, 'yyyy-MM-dd', 'en-US');
          this.basicSearchService.findParentById('region_service', this.regEvent.service.id).subscribe(result => {
            this.region = result;
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
      this.title = `Создание нового регионального эвента`;
      this.loadPresaveSafe();
    }
  }

  save() {
    this.regEvent.eventDate = new Date(this.event_date);
    this.regionEventService.save(this.regEvent).subscribe(result => {
      this.regEvent = result;
      this.title = `Редактирование регионального эвента`;
      this.isEditable = false;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  loadPresaveSafe() {
    this.isEditable = true;
    if (sessionStorage.getItem("RE_PRESAVE")) {
      this.regEvent = JSON.parse(sessionStorage.getItem("RE_PRESAVE") || 'new HotelEvent(null, new Date(), 0,0, 0,\n' +
        'new ShortForm(0, ""), 0)');
      this.region = JSON.parse(sessionStorage.getItem("RE_PRESAVE_REGION") || 'new ShortForm(0, ""), 0)');
      if (sessionStorage.getItem("RE_REGION_SERV")) {
        this.regEvent.service = JSON.parse(sessionStorage.getItem("RE_REGION_SERV") || 'new ShortForm(0, "")');
        this.basicSearchService.findParentById('region_service', this.regEvent.service.id).subscribe(result => {
          this.region = result;
        }, error => {
          console.log(`Error ${error}`);
        });
        sessionStorage.removeItem("RE_REGION_SERV");
      }
      sessionStorage.removeItem("RE_PRESAVE_REGION");
      sessionStorage.removeItem("RE_PRESAVE");
      this.event_date = formatDate(this.regEvent.eventDate, 'yyyy-MM-dd', 'en-US');
    }
  }

  makePresave() {
    if (this.event_date != "") {
      this.regEvent.eventDate = new Date(this.event_date);
    }
    sessionStorage.setItem(`RE_PRESAVE_REGION`, JSON.stringify(this.region));
    sessionStorage.setItem(`RE_PRESAVE`, JSON.stringify(this.regEvent));
  }

  deleteConfirm() {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      alert("Удаление в процессе реализации.");
    }
  }

  findRegionService() {
    this.makePresave();
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl("search_components/region_service/RE_REGION_SERV");
  }

  return() {
    sessionStorage.removeItem("TOUR_EDIT");
    this.router.navigateByUrl(`tour/${this.regEvent.tourId}`);
  }

}
