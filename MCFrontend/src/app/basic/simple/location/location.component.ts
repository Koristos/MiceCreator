import {Component, OnInit} from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {RequestParams} from "../../../service/basic/request-params";
import {ActivatedRoute, Router} from "@angular/router";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {Location} from "../../../service/basic/location/location";
import {LocationService} from "../../../service/basic/location/location.service";
import {FileService} from "../../../service/files/file.service";
import {DomSanitizer} from "@angular/platform-browser";
import {AppComponent} from "../../../app.component";

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public location: Location = new Location(null, "", "", 0, null);
  public title: string = "СОЗДАНИЕ НОВОЙ ЛОКАЦИИ";
  public countryId: number = 0;
  countryList: ShortForm[] = [];
  regionList: ShortForm[] = [];
  public imageOneUrl: any = null;
  public imageOneFile: any = null;
  private params: RequestParams = new RequestParams();
  public isDeleteOn: boolean = false;

  constructor(private route: ActivatedRoute,
              private locationService: LocationService,
              private basicSearchService: BasicSearchService,
              private fileService: FileService,
              private domSanitizer: DomSanitizer,
              private app: AppComponent,
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
      this.title = "РЕДАКТИРОВАНИЕ ЛОКАЦИИ";
      this.editEnabled = false;
      this.locationService.findById(this.id).subscribe(result => {
        this.location = result;
        this.loadImageOneIfNeeded();
        this.basicSearchService.findById('region', this.location.regionId).subscribe(reg => {
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
      this.saveStepOne();
    }
  }

  private saveStepOne(){
      if (this.location.imageOne == "changed" && this.location.id != null){
        this.fileService.saveImage("location", this.location.id, 1, this.imageOneFile).subscribe(result => {
          this.saveStepTwo();
        });
      } else if(this.location.imageOne == "deleted" && this.location.id != null) {
        this.fileService.deleteImage("location", this.location.id, 1).subscribe(result => {
          this.saveStepTwo();
        });
      } else {
      this.saveStepTwo();
    }
  }
  private saveStepTwo(){
    this.locationService.save(this.location).subscribe(result => {
      this.location = result;
      this.loadImageOneIfNeeded();
      this.title = "РЕДАКТИРОВАНИЕ ЛОКАЦИИ";
      this.editEnabled = false;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  deleteConfirm() {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      this.locationService.delete(this.id).subscribe(result => {
        if (result){
          this.router.navigateByUrl("/search_components/location");
        }
      }, error => {
        console.log(`Error ${error}`);
      });
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
    this.location.regionId = 0;
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


  onFileChanged(event: any) {
    const fileReader: FileReader = new FileReader();
    if (event.target.classList.contains("imageOne")){
      this.imageOneFile = event.target.files[0];
      fileReader.readAsDataURL(this.imageOneFile);
      fileReader.onload = () => {
        this.imageOneUrl = fileReader.result;
      };
      this.location.imageOne = "changed";
    }
  }

  deleteImage(imageNum: number){
    if (imageNum == 1) {
      this.imageOneUrl = null;
      this.imageOneFile = null;
      this.location.imageOne = "deleted";
    }
  }

  private loadImageOneIfNeeded(){
    const fileReader: FileReader = new FileReader();
    if (this.location.imageOne != null){
      this.fileService.getImage(this.location.imageOne).subscribe(result => {
        this.imageOneFile = new File([result], "imageOne", {type: "image", lastModified: Date.now()});
        fileReader.readAsDataURL(this.imageOneFile);
        fileReader.onload = () => {
          this.imageOneUrl = fileReader.result;
          this.imageOneUrl = this.domSanitizer.bypassSecurityTrustUrl(this.imageOneUrl);
        };
      });
    } else this.imageOneUrl = null;
  }

}
