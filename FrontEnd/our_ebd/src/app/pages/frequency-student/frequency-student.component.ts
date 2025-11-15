import { Component } from '@angular/core';
import { MenuStudentComponent } from '../../components/menu-student/menu-student.component';
import { FrequencyGraphStudentComponent } from "../../components/frequency-graph-student/frequency-graph-student.component";

@Component({
  selector: 'app-frequency-student',
  imports: [MenuStudentComponent, FrequencyGraphStudentComponent],
  templateUrl: './frequency-student.component.html',
  styleUrl: './frequency-student.component.css'
})
export class FrequencyStudentComponent {

}
