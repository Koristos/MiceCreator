import {ShortForm} from "../../basic/shortform";

export class RegEvent {

  constructor(public id: any,
              public eventDate: Date,
              public pax: number,
              public price: number,
              public tourId: number,
              public service: ShortForm,
              public total: number
  ) {
  }
}

