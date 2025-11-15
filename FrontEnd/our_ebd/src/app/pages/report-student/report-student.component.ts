import { Component } from '@angular/core';
import { MenuStudentComponent } from '../../components/menu-student/menu-student.component';
import { ClassReportStudentComponent } from "../../components/class-report-student/class-report-student.component";

@Component({
  selector: 'app-report-student',
  imports: [MenuStudentComponent, ClassReportStudentComponent],
  templateUrl: './report-student.component.html',
  styleUrl: './report-student.component.css'
})
export class ReportStudentComponent {

}
