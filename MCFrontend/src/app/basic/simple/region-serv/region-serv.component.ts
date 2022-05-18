import {Component, OnInit} from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {RequestParams} from "../../../service/basic/request-params";
import {ActivatedRoute} from "@angular/router";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {RegionServService} from "../../../service/basic/regionserv/region-serv.service";
import {RegionServ} from "../../../service/basic/regionserv/regionserv";

@Component({
  selector: 'app-region-serv',
  templateUrl: './region-serv.component.html',
  styleUrls: ['./region-serv.component.css']
})
export class RegionServComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public regionServ: RegionServ = new RegionServ(null, "", "", 0);
  public title: string = "СОЗДАНИЕ НОВОЙ РЕГИОНАЛЬНОЙ УСЛУГИ";
  public countryId: number = 0;
  countryList: ShortForm[] = [];
  regionList: ShortForm[] = [];
  private params: RequestParams = new RequestParams();

  constructor(private route: ActivatedRoute,
              private regionServService: RegionServService,
              private basicSearchService: BasicSearchService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = param['id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      this.title = "РЕДАКТИРОВАНИЕ РЕГИОНАЛЬНОЙ УСЛУГИ";
      this.editEnabled = false;
      this.regionServService.findById(this.id).subscribe(result => {
        this.regionServ = result;
        this.basicSearchService.findById('region', this.regionServ.regionId).subscribe(reg => {
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
      this.regionServService.save(this.regionServ).subscribe(result => {
        this.regionServ = result;
        this.title = "РЕДАКТИРОВАНИЕ РЕГИОНАЛЬНОЙ УСЛУГИ";
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
    this.regionServ.regionId = 0;
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
