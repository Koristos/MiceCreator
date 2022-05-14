import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Country} from "../../../service/basic/country/country";
import {CountryService} from "../../../service/basic/country/country.service";

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.css']
})
export class CountryComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public country: Country = new Country(null, "");
  public title: string = "СОЗДАНИЕ НОВОЙ СТРАНЫ";

  constructor(private route: ActivatedRoute,
              private countryService: CountryService) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = param['id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      this.title = "РЕДАКТИРОВАНИЕ СТРАНЫ";
      this.editEnabled = false;
      this.countryService.findById(this.id).subscribe(result => {
        this.country = result;
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  save(){
    if(confirm("Вы уверены, что хотите сохранить изменения?")) {
      this.countryService.save(this.country).subscribe(result => {
        this.country = result;
        this.title = "РЕДАКТИРОВАНИЕ СТРАНЫ";
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

}
