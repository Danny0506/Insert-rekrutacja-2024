import {Component} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';


@Component({
  selector: 'main-menu',
  templateUrl: 'main-menu-component.html',
  styleUrls: ['main-menu-component.css'],
  standalone: true,
  imports: [MatButtonModule, RouterOutlet, RouterLink, RouterLinkActive],
})
export class MainMenuComponent {

}
