import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {FormGroup} from '@angular/forms';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private readonly URL: string = 'http://localhost:7070/orders';

  constructor(private http: HttpClient) {}

  saveOrder(form: FormGroup): any {
    return this.http.post(this.URL, form.value, {observe: 'response'});
  }

  getOrdersFromApi(): Observable<any> {
     return this.http.get(this.URL);
  }
}
