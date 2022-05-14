import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AccType} from "../../../service/basic/accom-type/acctype";
import {AccomTypeService} from "../../../service/basic/accom-type/accom-type.service";

@Component({
  selector: 'app-accom-type',
  templateUrl: './accom-type.component.html',
  styleUrls: ['./accom-type.component.css']
})
export class AccomTypeComponent implements OnInit {
  public editEnabled: boolean = true;
  public id: any;
  public accType: AccType = new AccType(null, "", 0);
  public title: string = "СОЗДАНИЕ НОВОГО ТИПА РАЗМЕЩЕНИЯ";

  constructor(private route: ActivatedRoute,
              private accomTypeService: AccomTypeService) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = param['id'];
    }, error => {
      console.log(`Error ${error}`);
    });
    if (this.id != "new") {
      this.title = "РЕДАКТИРОВАНИЕ ТИПА РАЗМЕЩЕНИЯ";
      this.editEnabled = false;
      this.accomTypeService.findById(this.id).subscribe(result => {
        this.accType = result;
      }, error => {
        console.log(`Error ${error}`);
      });
    }
  }

  save(){
    if(confirm("Вы уверены, что хотите сохранить изменения?")) {
      this.accomTypeService.save(this.accType).subscribe(result => {
        this.accType = result;
        this.title = "СОЗДАНИЕ НОВОГО ТИПА РАЗМЕЩЕНИЯ";
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
