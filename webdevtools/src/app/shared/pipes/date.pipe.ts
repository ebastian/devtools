import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'date2'
})
export class DatePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if(value !== undefined && value !== null) {
      const d = new Date(value);
      switch(args) {
        case 'date':
          return this.extractDate(d);
        case 'time':
          return this.extractTime(d);
        default:
          return this.extractDate(d) + ' - ' + this.extractTime(d);
      }
    }
    return '';
  }

  extractDate = (d:Date): string => this.fill(d.getDate()) + "/" + this.fill(d.getMonth()+1) + "/" + this.fill(d.getFullYear());

  extractTime = (d:Date): string => this.fill(d.getHours()) + ":" + this.fill(d.getMinutes()) + ":" + this.fill(d.getSeconds());

  fill = number => number <= 9 ? ("0"+number).slice(-2) : number;

}