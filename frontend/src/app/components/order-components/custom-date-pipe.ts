import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'datePipe', standalone: true})
export class CustomDatePipe implements PipeTransform {
  transform(dateValueArray: string[]): string {
    return dateValueArray[2] + '.' + dateValueArray[1] + '.' + dateValueArray[0];
  }
}
