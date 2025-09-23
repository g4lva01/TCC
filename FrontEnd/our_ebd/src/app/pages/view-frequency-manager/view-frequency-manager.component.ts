import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from "../../components/menu/menu.component";
import { FrequencyGraphComponent } from "../../components/frequency-graph/frequency-graph.component";

@Component({
  selector: 'app-view-frequency-manager',
  standalone: true,
  imports: [CommonModule, MenuComponent, FrequencyGraphComponent],
  templateUrl: './view-frequency-manager.component.html',
  styleUrl: './view-frequency-manager.component.css'
})
export class ViewFrequencyManagerComponent {

}
