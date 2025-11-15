import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { NewPasswordComponent } from './pages/new-password/new-password.component';
import { ViewFrequencyManagerComponent } from './pages/view-frequency-manager/view-frequency-manager.component';
import { ViewReportManagerComponent } from './pages/view-report-manager/view-report-manager.component';
import { ClassHistoryComponent } from './pages/class-history/class-history.component';
import { ChamadaComponent } from './pages/chamada/chamada.component';
import { ActivitysManagerComponent } from './pages/activitys-manager/activitys-manager.component';
import { PerfilSelectorComponent } from './pages/perfil-selector/perfil-selector.component';
import { GerenciarPerfisComponent } from './pages/gerenciar-perfis/gerenciar-perfis.component';
import { FrequencyStudentComponent } from './pages/frequency-student/frequency-student.component';
import { ReportStudentComponent } from './pages/report-student/report-student.component';
import { SchoolHistoryStudentComponent } from './pages/school-history-student/school-history-student.component';

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
    },
    {
      path: 'selecionar-perfil',
      component: PerfilSelectorComponent
    },
    {
      path: 'managingProfiles',
      component: GerenciarPerfisComponent
    },
    {
      path: 'frequencyStudent',
      component: FrequencyStudentComponent
    },
    {
      path: 'reportStudent',
      component: ReportStudentComponent
    },
    {
      path: 'schoolHistoryStudent',
      component: SchoolHistoryStudentComponent
    }
];
