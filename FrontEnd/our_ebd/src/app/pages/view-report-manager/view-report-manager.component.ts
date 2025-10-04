import { Component } from '@angular/core';
import { MenuComponent } from '../../components/menu/menu.component';
import { ClassReportComponent } from '../../components/class-report/class-report.component';

@Component({
  selector: 'app-view-report-manager',
  imports: [MenuComponent, ClassReportComponent],
  templateUrl: './view-report-manager.component.html',
  styleUrl: './view-report-manager.component.css'
})
export class ViewReportManagerComponent {

}
