import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router, NavigationStart} from "@angular/router";
import {BasicSearchService} from "../../service/basic/basic-search.service";
import {ShortForm} from "../../service/basic/shortform";
import {RequestParams} from "../../service/basic/request-params";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  public hotelAllowed: string[] = ['hotel_service','room'];
  public locationAllowed: string[] = ['hotel_service','room', 'hotel'];
  public regionAllowed: string[] = ['hotel_service','room', 'hotel', 'airport', 'region_service', 'location'];
  public countryAllowed: string[] = ['hotel_service','room','hotel', 'airport', 'region_service', 'location', 'region'];

  public type = "";
  public selectedOption: any;
  public header = "unknown";
  public params = new RequestParams();
  searchResult: ShortForm[] = [];
  countryList: ShortForm[] = [];
  regionList: ShortForm[] = [];
  locationList: ShortForm[] = [];
  hotelList: ShortForm[] = [];
  private typeMap = new Map();

  constructor(public searchService: BasicSearchService,
              public route: ActivatedRoute,
              public router: Router) {
  }

  ngOnInit(): void {
    this.fillTypes();
    this.route.params.subscribe(param => {
      this.type = param['type'];
      this.header = this.typeMap.get(this.type);
      this.searchService.findAll(this.type).subscribe(result => {
        this.searchResult = result;
      }, error => {
        console.log(`Error ${error}`);
      });
      this.searchService.findAll('country').subscribe(result => {
        this.countryList = result;
      }, error => {
        console.log(`Error ${error}`);
      });
      this.params.reset();
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  fillTypes(): void{
    this.typeMap.set('country', 'СТРАН');
    this.typeMap.set('region', 'РЕГИОНОВ');
    this.typeMap.set('location', 'ЛОКАЦИЙ');
    this.typeMap.set('hotel', 'ОТЕЛЕЙ');
    this.typeMap.set('airport', 'АЭРОПОРТОВ');
    this.typeMap.set('airline', 'АВИАКОМПАНИЙ');
    this.typeMap.set('accomm_type', 'ТИПОВ РАЗМЕЩЕНИЯ');
    this.typeMap.set('room', 'ОТЕЛЬНЫХ НОМЕРОВ');
    this.typeMap.set('hotel_service', 'ОТЕЛЬНЫХ УСЛУГ');
    this.typeMap.set('region_service', 'РЕГИОНАЛЬНЫХ УСЛУГ');

  }

  getRegions(){
    this.searchService.findByParams('region', this.params.prepareForSending()).subscribe(result => {
      this.regionList = result;
      this.params.region = "null";
      this.locationList = [];
      this.params.location = "null";
      this.hotelList = [];
      this.params.hotel = "null";
      this.findByParams();
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  getLocations(){
    this.searchService.findByParams('location', this.params.prepareForSending()).subscribe(result => {
      this.locationList = result;
      this.hotelList = [];
      this.params.hotel = "null";
      this.findByParams();
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  getHotels(){
    this.searchService.findByParams('hotel', this.params.prepareForSending()).subscribe(result => {
      this.hotelList = result;
      this.findByParams();
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  findByParams(){
    this.searchService.findByParams(this.type, this.params.prepareForSending()).subscribe(result => {
      this.searchResult = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  findByName(event: any){
    if (event.target.value == ""){
      this.params.name = "null";
    } else {
      this.params.name = event.target.value;
    }
    this.findByParams();
  }

  deleteConfirm(id: number){
    if(confirm("Вы уверены, что хотите удалить элемент?")) {
      alert("Удаление в процессе реализации.");
    }
  }
}
