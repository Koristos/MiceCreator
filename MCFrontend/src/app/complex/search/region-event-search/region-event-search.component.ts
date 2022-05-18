import {Component, OnInit} from '@angular/core';
import {formatDate} from "@angular/common";
import {ShortForm} from "../../../service/basic/shortform";
import {ActivatedRoute, Router} from "@angular/router";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {RegEvent} from "../../../service/complex/regionevent/regevent";
import {RegEventSearchParams} from "../../../service/complex/regionevent/reg-event-search-params";
import {RegionEventService} from "../../../service/complex/regionevent/region-event.service";

@Component({
  selector: 'app-region-event-search',
  templateUrl: './region-event-search.component.html',
  styleUrls: ['./region-event-search.component.css']
})
export class RegionEventSearchComponent implements OnInit {

  public regionEventList: RegEvent[] = [];
  public isSearchCommitted: boolean = false;
  public regEventSearchParams: RegEventSearchParams = new RegEventSearchParams();
  public regionService: ShortForm = new ShortForm(0, "");
  public region: ShortForm = new ShortForm(0, "");

  constructor(private route: ActivatedRoute,
              private router: Router,
              private regionEventService: RegionEventService,
              private basicSearchService: BasicSearchService) {
  }

  ngOnInit(): void {
    if (sessionStorage.getItem("RS_REGION_SERVICE")) {
      this.regionService = JSON.parse(sessionStorage.getItem("RS_REGION_SERVICE") || 'new ShortForm(0, "")');
      this.basicSearchService.findParentById('region_service', this.regionService.id).subscribe(result => {
        this.region = result;
      });
    }
  }

  search() {
    this.regEventSearchParams.regionServId = this.regionService.id;
    this.regionEventService.findByParams(this.regEventSearchParams.prepareForSending()).subscribe(result => {
      this.regionEventList = result;
      this.isSearchCommitted = true;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  dateToPattern(date: Date) {
    return formatDate(date, 'dd.MM.YYYY', 'en-US')
  }

  findHotelService() {
    this.router.navigateByUrl("search_components/region_service/RS_REGION_SERVICE");
  }

}
