import {Component, OnInit} from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {ActivatedRoute, Router} from "@angular/router";
import {formatDate} from "@angular/common";
import {Accommodation} from "../../../service/complex/accommodation/accommodation";
import {AccommodationSearchParams} from "../../../service/complex/accommodation/accommodation-search-params";
import {AccommodationService} from "../../../service/complex/accommodation/accommodation.service";

@Component({
  selector: 'app-accom-search',
  templateUrl: './accom-search.component.html',
  styleUrls: ['./accom-search.component.css']
})
export class AccomSearchComponent implements OnInit {

  public accommList: Accommodation[] = [];
  public isSearchCommitted: boolean = false;
  public accommodationSearchParams: AccommodationSearchParams = new AccommodationSearchParams();
  public hotel: ShortForm = new ShortForm(0, "");
  public accType: ShortForm = new ShortForm(0, "");

  constructor(private route: ActivatedRoute,
              private router: Router,
              private accommodationService: AccommodationService) {
  }

  ngOnInit(): void {
    if (sessionStorage.getItem("AS_HOTEL")) {
      this.hotel = JSON.parse(sessionStorage.getItem("AS_HOTEL") || 'new ShortForm(0, "")');
    }
    if (sessionStorage.getItem("AS_ACC_TYPE")) {
      this.accType = JSON.parse(sessionStorage.getItem("AS_ACC_TYPE") || 'new ShortForm(0, "")');
    }
  }

  search() {
    this.accommodationSearchParams.hotelId = this.hotel.id;
    this.accommodationSearchParams.accTypeId = this.accType.id;
    this.accommodationService.findByParams(this.accommodationSearchParams.prepareForSending()).subscribe(result => {
      this.accommList = result;
      this.isSearchCommitted = true;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  dateToPattern(date: Date) {
    return formatDate(date, 'dd.MM.YYYY', 'en-US')
  }

  findHotel() {
    this.router.navigateByUrl("search_components/hotel/AS_HOTEL");
  }

  findAccType() {
    this.router.navigateByUrl("search_components/accomm_type/AS_ACC_TYPE");

  }

}
