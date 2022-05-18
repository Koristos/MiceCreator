import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Airport} from "../../../service/basic/airport/airport";
import {AirportService} from "../../../service/basic/airport/airport.service";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {ShortForm} from "../../../service/basic/shortform";
import {RequestParams} from "../../../service/basic/request-params";

@Component({
  selector: 'app-airport',
  templateUrl: './airport.component.html',
  styleUrls: ['./airport.component.css']
})
export class AirportComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public airport: Airport = new Airport(null, "", "", 0);
  public title: string = "СОЗДАНИЕ НОВОГО АЭРОПОРТА";
  public countryId: number = 0;
  countryList: ShortForm[] = [];
  regionList: ShortForm[] = [];
  private params: RequestParams = new RequestParams();

  constructor(private route: ActivatedRoute,
              private airportService: AirportService,
              private basicSearchService: BasicSearchService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = param['id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      this.title = "РЕДАКТИРОВАНИЕ АЭРОПОРТА";
      this.editEnabled = false;
      this.airportService.findById(this.id).subscribe(result => {
        this.airport = result;
        this.basicSearchService.findById('region', this.airport.regionId).subscribe(reg => {
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
    } else {
      this.getCountries()
    }
  }

  save() {
    if (confirm("Вы уверены, что хотите сохранить изменения?")) {
      this.airportService.save(this.airport).subscribe(result => {
        this.airport = result;
        this.title = "РЕДАКТИРОВАНИЕ АЭРОПОРТА";
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
    this.airport.regionId = 0;
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
    this.getRegions();
    this.getCountries();
    this.editEnabled = true;
  }
}
