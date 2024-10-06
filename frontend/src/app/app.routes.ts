import { Routes } from '@angular/router';
import {AddOrderComponent} from './components/order-components/add-order-component/add-order-component';
import {DisplayOrdersComponent} from './components/order-components/display-orders-component/display-orders-component';
import {MainMenuComponent} from './components/main-menu-component/main-menu-component';

export const routes: Routes = [
  { path: 'addOrder', component: AddOrderComponent },
  { path: 'orders', component: DisplayOrdersComponent },
  { path: '', component: MainMenuComponent },
];
