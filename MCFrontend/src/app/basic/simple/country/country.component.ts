import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Country} from "../../../service/basic/country/country";
import {CountryService} from "../../../service/basic/country/country.service";
import {AppComponent} from "../../../app.component";
import {ShortForm} from "../../../service/basic/shortform";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {CurrencyService} from "../../../service/currency/currency.service";

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.css']
})
export class CountryComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public country: Country = new Country(null, "", "");
  public title: string = "СОЗДАНИЕ НОВОЙ СТРАНЫ";
  public isDeleteOn: boolean = false;
  public currencyList: ShortForm[] = [];


  constructor(private route: ActivatedRoute,
              private countryService: CountryService,
              private app: AppComponent,
              private currencyService: CurrencyService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.app.loginCheck();
    this.isDeleteOn = (this.app.user.role.includes("ROLE_ADMIN"));
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
        this.currencyList.push(new ShortForm(1, this.country.currency));
      }, error => {
        console.log(`Error ${error}`);
      });
    } else {
      this.getCurrencies();
    }
  }

  save() {
    if (confirm("Вы уверены, что хотите сохранить изменения?")) {
      this.countryService.save(this.country).subscribe(result => {
        this.country = result;
        this.title = "РЕДАКТИРОВАНИЕ СТРАНЫ";
        this.editEnabled = false;
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  deleteConfirm() {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      this.countryService.delete(this.id).subscribe(result => {
        if (result){
          this.router.navigateByUrl("/search_components/country");
        }
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  getCurrencies() {
    this.currencyService.findAll().subscribe(result => {
      this.currencyList = result;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  enableEdit() {
    this.getCurrencies();
    this.editEnabled = true;
  }

}
