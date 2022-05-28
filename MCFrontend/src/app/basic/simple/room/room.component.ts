import {Component, OnInit} from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {RequestParams} from "../../../service/basic/request-params";
import {ActivatedRoute} from "@angular/router";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {Room} from "../../../service/basic/romm/room";
import {RoomService} from "../../../service/basic/romm/room.service";
import {FileService} from "../../../service/files/file.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public room: Room = new Room(null, "", "", 0, null, null);
  public title: string = "СОЗДАНИЕ НОВОГО ТИПА КОМНАТЫ";
  public countryId: number = 0;
  public regionId: number = 0;
  public locationId: number = 0;
  countryList: ShortForm[] = [];
  regionList: ShortForm[] = [];
  locationList: ShortForm[] = [];
  hotelList: ShortForm[] = [];
  public imageOneUrl: any = null;
  public imageOneFile: any = null;
  public imageTwoUrl: any = null;
  public imageTwoFile: any = null;
  private params: RequestParams = new RequestParams();

  constructor(private route: ActivatedRoute,
              private roomService: RoomService,
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
      this.title = "РЕДАКТИРОВАНИЕ ТИПА КОМНАТЫ";
      this.editEnabled = false;
      this.roomService.findById(this.id).subscribe(result => {
        this.room = result;
        this.loadImageOneIfNeeded();
        this.loadImageTwoIfNeeded();
        this.basicSearchService.findById('hotel', this.room.hotelId).subscribe(htl => {
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
      this.saveStepOne();
    }
  }

  private saveStepOne(){
    if (this.room.imageOne == "changed" && this.room.id != null){
      this.fileService.saveImage("room", this.room.id, 1, this.imageOneFile).subscribe(result => {
        this.saveStepTwo();
      });
    } else if(this.room.imageOne == "deleted" && this.room.id != null) {
      this.fileService.deleteImage("room", this.room.id, 1).subscribe(result => {
        this.saveStepTwo();
      });
    } else {
      this.saveStepTwo();
    }
  }
  private saveStepTwo(){
    if (this.room.imageTwo == "changed" && this.room.id != null){
      this.fileService.saveImage("room", this.room.id, 2, this.imageTwoFile).subscribe(result => {
        this.saveStepThree();
      });
    }else if(this.room.imageTwo == "deleted" && this.room.id != null) {
      this.fileService.deleteImage("room", this.room.id, 2).subscribe(result => {
        this.saveStepThree();
      });
    } else {
      this.saveStepThree();
    }
  }
  private saveStepThree(){
    this.roomService.save(this.room).subscribe(result => {
      this.room = result;
      this.loadImageOneIfNeeded();
      this.loadImageTwoIfNeeded();
      this.title = "РЕДАКТИРОВАНИЕ ТИПА КОМНАТЫ";
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
    this.room.hotelId = 0;
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
    this.room.hotelId = 0;
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
    this.room.hotelId = 0;
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


  onFileChanged(event: any) {
    const fileReader: FileReader = new FileReader();
    if (event.target.classList.contains("imageOne")){
      this.imageOneFile = event.target.files[0];
      fileReader.readAsDataURL(this.imageOneFile);
      fileReader.onload = () => {
        this.imageOneUrl = fileReader.result;
      };
      this.room.imageOne = "changed";
    } else if (event.target.classList.contains("imageTwo")) {
      this.imageTwoFile = event.target.files[0];
      fileReader.readAsDataURL(this.imageTwoFile);
      fileReader.onload = () => {
        this.imageTwoUrl = fileReader.result;
      };
      this.room.imageTwo = "changed";
    }

  }
  deleteImage(imageNum: number){
    if (imageNum == 1) {
      this.imageOneUrl = null;
      this.imageOneFile = null;
      this.room.imageOne = "deleted";
    }
    if (imageNum == 2) {
      this.imageTwoUrl = null;
      this.imageTwoFile = null;
      this.room.imageTwo = "deleted";
    }
  }

  private loadImageOneIfNeeded(){
    const fileReader: FileReader = new FileReader();
    if (this.room.imageOne != null){
      this.fileService.getImage(this.room.imageOne).subscribe(result => {
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
    if (this.room.imageTwo != null){
      this.fileService.getImage(this.room.imageTwo).subscribe(result => {
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
