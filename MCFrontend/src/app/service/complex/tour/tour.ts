import {ShortForm} from "../../basic/shortform";

export class Tour {

  constructor(public id: any,
              public pax: number,
              public startDate: Date,
              public endDate: Date,
              public totalPrice: number,
              public totalPriceInBasicCurrency: number,
              public tourCurrency: string,
              public country: ShortForm,
              public nettoTotal: number,
              public creationDate: Date,
              public userName: string
  ) {
  }
}
