import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from "../../components/menu/menu.component";

@Component({
  selector: 'app-view-frequency-manager',
  standalone: true,
  imports: [CommonModule, MenuComponent],
  templateUrl: './view-frequency-manager.component.html',
  styleUrl: './view-frequency-manager.component.css'
})
export class ViewFrequencyManagerComponent {
  menuVisible = true;

  toggleMenu() {
    this.menuVisible = !this.menuVisible;
  }
}
