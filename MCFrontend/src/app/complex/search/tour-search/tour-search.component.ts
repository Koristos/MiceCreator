import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TourService} from "../../../service/complex/tour/tour.service";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {ShortForm} from "../../../service/basic/shortform";
import {Tour} from "../../../service/complex/tour/tour";
import {TourSearchParams} from "../../../service/complex/tour/tour-search-params";
import {formatDate} from "@angular/common";


@Component({
  selector: 'app-tour-search',
  templateUrl: './tour-search.component.html',
  styleUrls: ['./tour-search.component.css']
})
export class TourSearchComponent implements OnInit {

  public countryList: ShortForm[] = [];
  public tourList: Tour[] = [];
  public isSearchCommitted: boolean = false;
  public tourSearchParams: TourSearchParams = new TourSearchParams();

  constructor(private route: ActivatedRoute,
              private tourService: TourService,
              private basicSearchService: BasicSearchService) {
  }

  ngOnInit(): void {
    this.basicSearchService.findAll('country').subscribe(result => {
      this.countryList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  search() {
    this.tourService.findByParams(this.tourSearchParams.prepareForSending()).subscribe(result => {
      this.tourList = result;
      this.isSearchCommitted = true;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  deleteConfirm(id: number) {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      alert("Удаление в процессе реализации.");
    }
  }

  dateToPattern(date: Date) {
    return formatDate(date, 'dd.MM.YYYY', 'en-US')
  }

}
