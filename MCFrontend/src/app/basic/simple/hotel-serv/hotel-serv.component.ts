import {Component, OnInit} from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {RequestParams} from "../../../service/basic/request-params";
import {ActivatedRoute} from "@angular/router";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {HotelServ} from "../../../service/basic/hotelserv/hotelserv";
import {HotelServService} from "../../../service/basic/hotelserv/hotel-serv.service";

@Component({
  selector: 'app-hotel-serv',
  templateUrl: './hotel-serv.component.html',
  styleUrls: ['./hotel-serv.component.css']
})
export class HotelServComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public hotelServ: HotelServ = new HotelServ(null, "", "", 0);
  public title: string = "СОЗДАНИЕ НОВОЙ ОТЕЛЬНОЙ УСЛУГИ";
  public countryId: number = 0;
  public regionId: number = 0;
  public locationId: number = 0;
  countryList: ShortForm[] = [];
  regionList: ShortForm[] = [];
  locationList: ShortForm[] = [];
  hotelList: ShortForm[] = [];
  private params: RequestParams = new RequestParams();

  constructor(private route: ActivatedRoute,
              private hotelServService: HotelServService,
              private basicSearchService: BasicSearchService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = param['id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      this.title = "РЕДАКТИРОВАНИЕ ОТЕЛЬНОЙ УСЛУГИ";
      this.editEnabled = false;
      this.hotelServService.findById(this.id).subscribe(result => {
        this.hotelServ = result;
        this.basicSearchService.findById('hotel', this.hotelServ.hotelId).subscribe(htl => {
          this.hotelList.push(htl);
          this.basicSearchService.findParentById('hotel', htl.id).subscribe(loc => {
            this.locationId = loc.id;
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
      }, error => {
        console.log(`Error ${error}`);
      });
    } else {
      this.getCountries()
    }
  }

  save() {
    if (confirm("Вы уверены, что хотите сохранить изменения?")) {
      this.hotelServService.save(this.hotelServ).subscribe(result => {
        this.hotelServ = result;
        this.title = "РЕДАКТИРОВАНИЕ ОТЕЛЬНОЙ УСЛУГИ";
        this.editEnabled = false;
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  deleteConfirm() {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      alert("Удаление в процессе реализации.");
    }
  }

  getHotels() {
    this.params.location = this.locationId;
    this.basicSearchService.findByParams('hotel', this.params.prepareForSending()).subscribe(result => {
      this.hotelList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  getHotelsWithDrop() {
    this.getHotels();
    this.hotelServ.hotelId = 0;
  }


  getLocations() {
    this.params.region = this.regionId;
    this.basicSearchService.findByParams('location', this.params.prepareForSending()).subscribe(result => {
      this.locationList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  getLocationsWithDrop() {
    this.getLocations();
    this.hotelServ.hotelId = 0;
    this.locationId = 0;
  }

  getRegions() {
    this.params.country = this.countryId;
    this.basicSearchService.findByParams('region', this.params.prepareForSending()).subscribe(result => {
      this.regionList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  getRegionsWithDrop() {
    this.getRegions();
    this.hotelServ.hotelId = 0;
    this.locationId = 0;
    this.regionId = 0;
  }

  getCountries() {
    this.basicSearchService.findAll('country').subscribe(result => {
      this.countryList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  enableEdit() {
    this.params.reset();
    this.params.country = this.countryId;
    this.params.region = this.regionId;
    this.params.location = this.locationId;
    this.getHotels();
    this.getLocations();
    this.getRegions();
    this.getCountries();
    this.editEnabled = true;
  }
}
