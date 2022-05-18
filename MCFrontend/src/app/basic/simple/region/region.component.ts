import {Component, OnInit} from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {ActivatedRoute} from "@angular/router";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {Region} from "../../../service/basic/region/region";
import {RegionService} from "../../../service/basic/region/region.service";

@Component({
  selector: 'app-region',
  templateUrl: './region.component.html',
  styleUrls: ['./region.component.css']
})
export class RegionComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public region: Region = new Region(null, "", 0);
  public title: string = "СОЗДАНИЕ НОВОГО РЕГИОНА";
  countryList: ShortForm[] = [];

  constructor(private route: ActivatedRoute,
              private regionService: RegionService,
              private basicSearchService: BasicSearchService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = param['id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      this.title = "РЕДАКТИРОВАНИЕ РЕГИОНА";
      this.editEnabled = false;
      this.regionService.findById(this.id).subscribe(result => {
        this.region = result;
        this.basicSearchService.findById('country', this.region.countryId).subscribe(result => {
          this.countryList.push(result);
        });
      }, error => {
        console.log(`Error ${error}`);
      });
    } else {
      this.getCountries();
    }
  }

  save() {
    if (confirm("Вы уверены, что хотите сохранить изменения?")) {
      this.regionService.save(this.region).subscribe(result => {
        this.region = result;
        this.title = "РЕДАКТИРОВАНИЕ РЕГИОНА";
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

  enableEdit() {
    this.getCountries();
    this.editEnabled = true;
  }

  getCountries() {
    this.basicSearchService.findAll('country').subscribe(result => {
      this.countryList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }
}
