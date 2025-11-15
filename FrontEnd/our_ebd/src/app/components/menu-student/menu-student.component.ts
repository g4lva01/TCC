import { Component} from '@angular/core';
import { RouterLink} from '@angular/router';
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-menu-student',
  imports: [RouterLink, CommonModule],
  templateUrl: './menu-student.component.html',
  styleUrl: './menu-student.component.css'
})
export class MenuStudentComponent {
  menuVisible = false;

  toggleMenu() {
    this.menuVisible = !this.menuVisible;
  }
}
