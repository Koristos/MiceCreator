import { Component, OnInit } from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {RequestParams} from "../../../service/basic/request-params";
import {ActivatedRoute} from "@angular/router";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {Hotel} from "../../../service/basic/hotel/hotel";
import {HotelService} from "../../../service/basic/hotel/hotel.service";

@Component({
  selector: 'app-hotel',
  templateUrl: './hotel.component.html',
  styleUrls: ['./hotel.component.css']
})
export class HotelComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public hotel: Hotel = new Hotel(null, "", "", 0);
  public title: string = "СОЗДАНИЕ НОВОГО ОТЕЛЯ";
  public countryId: number = 0;
  public regionId: number = 0;
  countryList: ShortForm[] = [];
  regionList: ShortForm[] = [];
  locationList: ShortForm[] = [];
  private params: RequestParams = new RequestParams();

  constructor(private route: ActivatedRoute,
              private hotelService: HotelService,
              private basicSearchService: BasicSearchService) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = param['id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      this.title = "РЕДАКТИРОВАНИЕ ОТЕЛЯ";
      this.editEnabled = false;
      this.hotelService.findById(this.id).subscribe(result => {
        this.hotel = result;
        this.basicSearchService.findById('location', this.hotel.locationId).subscribe(loc => {
          this.locationList.push(loc);
          this.basicSearchService.findParentById('location', loc.id).subscribe(reg => {
            this.regionId = reg.id;
            this.regionList.push(reg);
            this.basicSearchService.findParentById('region', reg.id).subscribe(country => {
              this.countryId = country.id;
              this.countryList.push(country);
            }, error => {
              console.log(`Error ${error}`);
            });
          }, error => {
            console.log(`Error ${error}`);
          });
        }, error => {
          console.log(`Error ${error}`);
        });
      }, error => {
        console.log(`Error ${error}`);
      });
    } else {
      this.getCountries()
    }
  }

  save(){
    if(confirm("Вы уверены, что хотите сохранить изменения?")) {
      this.hotelService.save(this.hotel).subscribe(result => {
        this.hotel = result;
        this.title = "РЕДАКТИРОВАНИЕ ОТЕЛЯ";
        this.editEnabled = false;
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  deleteConfirm(){
    if(confirm("Вы уверены, что хотите удалить элемент?")) {
      alert("Удаление в процессе реализации.");
    }
  }

  getLocations(){
    this.params.region = this.regionId;
    this.basicSearchService.findByParams('location', this.params.prepareForSending()).subscribe(result => {
      this.locationList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  getLocationsWithDrop(){
    this.getLocations();
    this.hotel.locationId = 0;
  }

  getRegions(){
    this.params.country = this.countryId;
    this.basicSearchService.findByParams('region', this.params.prepareForSending()).subscribe(result => {
      this.regionList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  getRegionsWithDrop(){
    this.getRegions();
    this.hotel.locationId = 0;
    this.regionId = 0;
  }

  getCountries(){
    this.basicSearchService.findAll('country').subscribe(result => {
      this.countryList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  enableEdit(){
    this.params.reset();
    this.params.country = this.countryId;
    this.params.region = this.regionId;
    this.getLocations();
    this.getRegions();
    this.getCountries();
    this.editEnabled = true;
  }
}
