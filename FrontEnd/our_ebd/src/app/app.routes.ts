import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { NewPasswordComponent } from './pages/new-password/new-password.component';
import { ViewFrequencyManagerComponent } from './pages/view-frequency-manager/view-frequency-manager.component';

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
      path: 'frequecyManager',
      component: ViewFrequencyManagerComponent
    }
];
