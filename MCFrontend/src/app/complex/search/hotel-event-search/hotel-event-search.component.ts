import {Component, OnInit} from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {ActivatedRoute, Router} from "@angular/router";
import {formatDate} from "@angular/common";
import {HotelEvent} from "../../../service/complex/hotelevent/hotelevent";
import {HotelEventSearchParams} from "../../../service/complex/hotelevent/hotel-event-search-params";
import {HotelEventService} from "../../../service/complex/hotelevent/hotel-event.service";
import {BasicSearchService} from "../../../service/basic/basic-search.service";

@Component({
  selector: 'app-hotel-event-search',
  templateUrl: './hotel-event-search.component.html',
  styleUrls: ['./hotel-event-search.component.css']
})
export class HotelEventSearchComponent implements OnInit {

  public hotelEventList: HotelEvent[] = [];
  public isSearchCommitted: boolean = false;
  public hotelEventSearchParams: HotelEventSearchParams = new HotelEventSearchParams();
  public hotelService: ShortForm = new ShortForm(0, "");
  public hotel: ShortForm = new ShortForm(0, "");

  constructor(private route: ActivatedRoute,
              private router: Router,
              private hotelEventService: HotelEventService,
              private basicSearchService: BasicSearchService) {
  }

  ngOnInit(): void {
    if (sessionStorage.getItem("HS_HOTEL_SERVICE")) {
      this.hotelService = JSON.parse(sessionStorage.getItem("HS_HOTEL_SERVICE") || 'new ShortForm(0, "")');
      this.basicSearchService.findParentById('hotel_service', this.hotelService.id).subscribe(result => {
        this.hotel = result;
      });
    }
  }

  search() {
    this.hotelEventSearchParams.hotel_service = this.hotelService.id;
    this.hotelEventService.findByParams(this.hotelEventSearchParams.prepareForSending()).subscribe(result => {
      this.hotelEventList = result;
      this.isSearchCommitted = true;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  dateToPattern(date: Date) {
    return formatDate(date, 'dd.MM.YYYY', 'en-US')
  }

  findHotelService() {
    this.router.navigateByUrl("search_components/hotel_service/HS_HOTEL_SERVICE");
  }

}
