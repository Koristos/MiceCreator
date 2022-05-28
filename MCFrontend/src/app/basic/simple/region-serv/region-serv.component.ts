import {Component, OnInit} from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {RequestParams} from "../../../service/basic/request-params";
import {ActivatedRoute} from "@angular/router";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {RegionServService} from "../../../service/basic/regionserv/region-serv.service";
import {RegionServ} from "../../../service/basic/regionserv/regionserv";
import {FileService} from "../../../service/files/file.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-region-serv',
  templateUrl: './region-serv.component.html',
  styleUrls: ['./region-serv.component.css']
})
export class RegionServComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public regionServ: RegionServ = new RegionServ(null, "", "", 0, null, null);
  public title: string = "СОЗДАНИЕ НОВОЙ РЕГИОНАЛЬНОЙ УСЛУГИ";
  public countryId: number = 0;
  countryList: ShortForm[] = [];
  regionList: ShortForm[] = [];
  public imageOneUrl: any = null;
  public imageOneFile: any = null;
  public imageTwoUrl: any = null;
  public imageTwoFile: any = null;
  private params: RequestParams = new RequestParams();

  constructor(private route: ActivatedRoute,
              private regionServService: RegionServService,
              private basicSearchService: BasicSearchService,
              private fileService: FileService,
              private domSanitizer: DomSanitizer) {
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
        this.loadImageOneIfNeeded();
        this.loadImageTwoIfNeeded();
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
      this.saveStepOne();
    }
  }

  private saveStepOne(){
    if (this.regionServ.imageOne == "changed" && this.regionServ.id != null){
      this.fileService.saveImage("region_service", this.regionServ.id, 1, this.imageOneFile).subscribe(result => {
        this.saveStepTwo();
      });
    } else if(this.regionServ.imageOne == "deleted" && this.regionServ.id != null) {
      this.fileService.deleteImage("region_service", this.regionServ.id, 1).subscribe(result => {
        this.saveStepTwo();
      });
    } else {
      this.saveStepTwo();
    }
  }
  private saveStepTwo(){
    if (this.regionServ.imageTwo == "changed" && this.regionServ.id != null){
      this.fileService.saveImage("region_service", this.regionServ.id, 2, this.imageTwoFile).subscribe(result => {
        this.saveStepThree();
      });
    }else if(this.regionServ.imageTwo == "deleted" && this.regionServ.id != null) {
      this.fileService.deleteImage("region_service", this.regionServ.id, 2).subscribe(result => {
        this.saveStepThree();
      });
    } else {
      this.saveStepThree();
    }
  }
  private saveStepThree(){
    this.regionServService.save(this.regionServ).subscribe(result => {
      this.regionServ = result;
      this.loadImageOneIfNeeded();
      this.loadImageTwoIfNeeded();
      this.title = "РЕДАКТИРОВАНИЕ РЕГИОНАЛЬНОЙ УСЛУГИ";
      this.editEnabled = false;
    }, error => {
      console.log(`Error ${error}`);
    });
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


  onFileChanged(event: any) {
    const fileReader: FileReader = new FileReader();
    if (event.target.classList.contains("imageOne")){
      this.imageOneFile = event.target.files[0];
      fileReader.readAsDataURL(this.imageOneFile);
      fileReader.onload = () => {
        this.imageOneUrl = fileReader.result;
      };
      this.regionServ.imageOne = "changed";
    } else if (event.target.classList.contains("imageTwo")) {
      this.imageTwoFile = event.target.files[0];
      fileReader.readAsDataURL(this.imageTwoFile);
      fileReader.onload = () => {
        this.imageTwoUrl = fileReader.result;
      };
      this.regionServ.imageTwo = "changed";
    }

  }
  deleteImage(imageNum: number){
    if (imageNum == 1) {
      this.imageOneUrl = null;
      this.imageOneFile = null;
      this.regionServ.imageOne = "deleted";
    }
    if (imageNum == 2) {
      this.imageTwoUrl = null;
      this.imageTwoFile = null;
      this.regionServ.imageTwo = "deleted";
    }
  }

  private loadImageOneIfNeeded(){
    const fileReader: FileReader = new FileReader();
    if (this.regionServ.imageOne != null){
      this.fileService.getImage(this.regionServ.imageOne).subscribe(result => {
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
    if (this.regionServ.imageTwo != null){
      this.fileService.getImage(this.regionServ.imageTwo).subscribe(result => {
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
