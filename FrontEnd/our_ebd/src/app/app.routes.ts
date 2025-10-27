import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { NewPasswordComponent } from './pages/new-password/new-password.component';
import { ViewFrequencyManagerComponent } from './pages/view-frequency-manager/view-frequency-manager.component';
import { ViewReportManagerComponent } from './pages/view-report-manager/view-report-manager.component';
import { ClassHistoryComponent } from './pages/class-history/class-history.component';
import { ChamadaComponent } from './pages/chamada/chamada.component';
import { ActivitysManagerComponent } from './pages/activitys-manager/activitys-manager.component';

export const routes: Routes = [
    {
        path: '',
        component:HomeComponent
    },
    {
      path: 'register',
      component:RegisterComponent
    },
    {
      path: 'new_password',
      component: NewPasswordComponent
    },
    {
      path: 'frequencyManager',
      component: ViewFrequencyManagerComponent
    },
    {
      path: 'reportManager',
      component: ViewReportManagerComponent
    },
    {
      path: 'schoolHistoryManager/:turma',
      component: ClassHistoryComponent
    },
    {
      path: 'chamada/:turma/:data',
      component: ChamadaComponent
    },
    {
      path: 'activitysManager',
      component: ActivitysManagerComponent
    }
];
