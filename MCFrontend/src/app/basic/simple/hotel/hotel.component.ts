import {Component, OnInit} from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {RequestParams} from "../../../service/basic/request-params";
import {ActivatedRoute, Router} from "@angular/router";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {Hotel} from "../../../service/basic/hotel/hotel";
import {HotelService} from "../../../service/basic/hotel/hotel.service";
import {FileService} from "../../../service/files/file.service";
import { DomSanitizer } from '@angular/platform-browser';
import {AppComponent} from "../../../app.component";

@Component({
  selector: 'app-hotel',
  templateUrl: './hotel.component.html',
  styleUrls: ['./hotel.component.css']
})
export class HotelComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public hotel: Hotel = new Hotel(null, "", "", 0, null, null);
  public title: string = "СОЗДАНИЕ НОВОГО ОТЕЛЯ";
  public countryId: number = 0;
  public regionId: number = 0;
  public imageOneUrl: any = null;
  public imageOneFile: any = null;
  public imageTwoUrl: any = null;
  public imageTwoFile: any = null;
  countryList: ShortForm[] = [];
  regionList: ShortForm[] = [];
  locationList: ShortForm[] = [];
  private params: RequestParams = new RequestParams();
  public isDeleteOn: boolean = false;

  constructor(private route: ActivatedRoute,
              private hotelService: HotelService,
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
      this.title = "РЕДАКТИРОВАНИЕ ОТЕЛЯ";
      this.editEnabled = false;
      this.hotelService.findById(this.id).subscribe(result => {
        this.hotel = result;
        this.loadImageOneIfNeeded();
        this.loadImageTwoIfNeeded();
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

  save() {
    if (confirm("Вы уверены, что хотите сохранить изменения?")) {
      this.saveStepOne();
    }
  }
  private saveStepOne(){
      if (this.hotel.imageOne == "changed" && this.hotel.id != null){
        this.fileService.saveImage("hotel", this.hotel.id, 1, this.imageOneFile).subscribe(result => {
          this.saveStepTwo();
        });
      } else if(this.hotel.imageOne == "deleted" && this.hotel.id != null) {
        this.fileService.deleteImage("hotel", this.hotel.id, 1).subscribe(result => {
          this.saveStepTwo();
        });
      } else {
        this.saveStepTwo();
      }
  }
  private saveStepTwo(){
      if (this.hotel.imageTwo == "changed" && this.hotel.id != null){
        this.fileService.saveImage("hotel", this.hotel.id, 2, this.imageTwoFile).subscribe(result => {
          this.saveStepThree();
        });
      }else if(this.hotel.imageTwo == "deleted" && this.hotel.id != null) {
        this.fileService.deleteImage("hotel", this.hotel.id, 2).subscribe(result => {
          this.saveStepThree();
        });
      } else {
        this.saveStepThree();
      }
  }
  private saveStepThree(){
    this.hotelService.save(this.hotel).subscribe(result => {
      this.hotel = result;
      this.loadImageOneIfNeeded();
      this.loadImageTwoIfNeeded();
      this.title = "РЕДАКТИРОВАНИЕ ОТЕЛЯ";
      this.editEnabled = false;
    }, error => {
      console.log(`Error ${error}`);
    });
  }

  deleteConfirm() {
    if (confirm("Вы уверены, что хотите удалить элемент?")) {
      this.hotelService.delete(this.id).subscribe(result => {
        if (result){
          this.router.navigateByUrl("/search_components/hotel");
        }
      }, error => {
        console.log(`Error ${error}`);
      });
    }
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
    this.hotel.locationId = 0;
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
    this.hotel.locationId = 0;
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
    this.getLocations();
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
      this.hotel.imageOne = "changed";
    } else if (event.target.classList.contains("imageTwo")) {
      this.imageTwoFile = event.target.files[0];
      fileReader.readAsDataURL(this.imageTwoFile);
      fileReader.onload = () => {
        this.imageTwoUrl = fileReader.result;
      };
      this.hotel.imageTwo = "changed";
    }

  }
  deleteImage(imageNum: number){
    if (imageNum == 1) {
      this.imageOneUrl = null;
      this.imageOneFile = null;
      this.hotel.imageOne = "deleted";
    }
    if (imageNum == 2) {
      this.imageTwoUrl = null;
      this.imageTwoFile = null;
      this.hotel.imageTwo = "deleted";
    }
  }

  private loadImageOneIfNeeded(){
    const fileReader: FileReader = new FileReader();
    if (this.hotel.imageOne != null){
      this.fileService.getImage(this.hotel.imageOne).subscribe(result => {
        this.imageOneFile = new File([result], "imageOne", {type: "image", lastModified: Date.now()});
        fileReader.readAsDataURL(this.imageOneFile);
        fileReader.onload = () => {
          this.imageOneUrl = fileReader.result;
          this.imageOneUrl = this.domSanitizer.bypassSecurityTrustUrl(this.imageOneUrl);
        };
      });
    } else this.imageOneUrl = null;
  }

  private loadImageTwoIfNeeded(){
    const fileReader: FileReader = new FileReader();
    if (this.hotel.imageTwo != null){
      this.fileService.getImage(this.hotel.imageTwo).subscribe(result => {
        this.imageTwoFile = new File([result], "imageTwo", {type: "image", lastModified: Date.now()});
        fileReader.readAsDataURL(this.imageTwoFile);
        fileReader.onload = () => {
          this.imageTwoUrl = fileReader.result;
          this.imageTwoUrl = this.domSanitizer.bypassSecurityTrustUrl(this.imageTwoUrl);
        };
      });
    }else this.imageTwoUrl = null;
  }
}
