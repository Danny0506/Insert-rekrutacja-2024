import {Component, OnInit} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { OrderService } from '../order-service';
import { OrderDto } from '../api-dto';
import {CustomDatePipe} from '../custom-date-pipe';

@Component({
  selector: 'display-orders',
  templateUrl: 'display-orders-component.html',
  styleUrls: ['display-orders-component.css'],
  standalone: true,
  imports: [MatButtonModule, MatTableModule, RouterOutlet, RouterLink, RouterLinkActive, CustomDatePipe],
})
export class DisplayOrdersComponent implements OnInit {

    displayedColumns: string[] = ['id', 'nameOfOrder', 'status', 'orderPrice', 'dateOfOrder', 'description', 'receiver', 'sender'];
    dataSource: OrderDto[] = [];
    constructor(private orderService: OrderService) {}

    ngOnInit() {
       this.orderService.getOrdersFromApi().subscribe(data => {this.dataSource = data});
    }
}
