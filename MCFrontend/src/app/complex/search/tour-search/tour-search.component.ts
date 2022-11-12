import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TourService} from "../../../service/complex/tour/tour.service";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {ShortForm} from "../../../service/basic/shortform";
import {Tour} from "../../../service/complex/tour/tour";
import {TourSearchParams} from "../../../service/complex/tour/tour-search-params";
import {formatDate} from "@angular/common";
import {AppComponent} from "../../../app.component";
import {UsersService} from "../../../service/users/users.service";


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
  public isDeleteOn: boolean = false;
  public userNames: string[] = [];

  constructor(private route: ActivatedRoute,
              private tourService: TourService,
              private basicSearchService: BasicSearchService,
              private app: AppComponent,
              private userService: UsersService) {
  }

  ngOnInit(): void {
    this.app.loginCheck();
    this.isDeleteOn = (this.app.user.role.includes("ROLE_ADMIN") || this.app.user.role.includes("ROLE_LEADER"));
    this.basicSearchService.findAll('country').subscribe(result => {
      this.countryList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
    this.userService.findAll().subscribe(result => {
      this.userNames = result;
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
      this.tourService.delete(id).subscribe(result => {
        if (result == true) {
          this.search();
        }
      });
    }
  }

  dateToPattern(date: Date) {
    return formatDate(date, 'dd.MM.YYYY', 'en-US')
  }

}
