import {ChangeDetectionStrategy, Component, signal} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {provideNativeDateAdapter} from '@angular/material/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { OrderService } from '../order-service';

@Component({
  selector: 'add-order',
  templateUrl: 'add-order-component.html',
  styleUrls: ['add-order-component.css'],
  providers: [provideNativeDateAdapter()],
  standalone: true,
  imports: [MatButtonModule, MatFormFieldModule, MatInputModule, MatSelectModule, ReactiveFormsModule, MatDatepickerModule, RouterOutlet, RouterLink, RouterLinkActive],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AddOrderComponent {

    form = new FormGroup({
         nameOfOrder: new FormControl<string>('', [Validators.required]),
         orderPrice: new FormControl<number>(0, [Validators.required]),
         description: new FormControl<string>('', [Validators.required]),
         receiver: new FormControl<string>('', [Validators.required]),
         sender: new FormControl<string>('', [Validators.required])
    });
    submitted = false;

    constructor(private orderService: OrderService) {}

    saveProduct() {
      this.submitted = true;
      if (this.form.valid)
      {
          this.orderService.saveOrder(this.form)
          .subscribe({
              next: (result: any) => { alert('Zamówienie zostało dodane !')},
              error: (error: any)  => { alert('Zamówienie nie zostało dodane !')}
          });
      }
    }
}
