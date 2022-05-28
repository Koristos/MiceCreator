import {Component, OnInit} from '@angular/core';
import {ShortForm} from "../../../service/basic/shortform";
import {ActivatedRoute} from "@angular/router";
import {BasicSearchService} from "../../../service/basic/basic-search.service";
import {Region} from "../../../service/basic/region/region";
import {RegionService} from "../../../service/basic/region/region.service";
import {FileService} from "../../../service/files/file.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-region',
  templateUrl: './region.component.html',
  styleUrls: ['./region.component.css']
})
export class RegionComponent implements OnInit {

  public editEnabled: boolean = true;
  public id: any;
  public region: Region = new Region(null, "", 0, null);
  public title: string = "СОЗДАНИЕ НОВОГО РЕГИОНА";
  countryList: ShortForm[] = [];
  public imageOneUrl: any = null;
  public imageOneFile: any = null;

  constructor(private route: ActivatedRoute,
              private regionService: RegionService,
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
      this.title = "РЕДАКТИРОВАНИЕ РЕГИОНА";
      this.editEnabled = false;
      this.regionService.findById(this.id).subscribe(result => {
        this.region = result;
        this.loadImageOneIfNeeded();
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
      this.saveStepOne();
    }
  }

  private saveStepOne(){
      if (this.region.imageOne == "changed" && this.region.id != null){
        this.fileService.saveImage("region", this.region.id, 1, this.imageOneFile).subscribe(result => {
          this.saveStepTwo();
        });
      } else if(this.region.imageOne == "deleted" && this.region.id != null) {
        this.fileService.deleteImage("region", this.region.id, 1).subscribe(result => {
          this.saveStepTwo();
        });
      } else {
      this.saveStepTwo();
    }
  }
  private saveStepTwo(){
    this.regionService.save(this.region).subscribe(result => {
      this.region = result;
      this.loadImageOneIfNeeded();
      this.title = "РЕДАКТИРОВАНИЕ РЕГИОНА";
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


  onFileChanged(event: any) {
    const fileReader: FileReader = new FileReader();
    if (event.target.classList.contains("imageOne")){
      this.imageOneFile = event.target.files[0];
      fileReader.readAsDataURL(this.imageOneFile);
      fileReader.onload = () => {
        this.imageOneUrl = fileReader.result;
      };
      this.region.imageOne = "changed";
    }
  }

  deleteImage(imageNum: number){
    if (imageNum == 1) {
      this.imageOneUrl = null;
      this.imageOneFile = null;
      this.region.imageOne = "deleted";
    }
  }

  private loadImageOneIfNeeded(){
    const fileReader: FileReader = new FileReader();
    if (this.region.imageOne != null){
      this.fileService.getImage(this.region.imageOne).subscribe(result => {
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
