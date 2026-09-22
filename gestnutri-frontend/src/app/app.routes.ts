import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { authGuard } from './auth.guard';
import { Formulation } from './pages/formulation/formulation';
import { Historique } from './pages/historique/historique';
import { FormuleDetail } from './pages/formule-detail/formule-detail';
import { ProfilForm } from './pages/profil-form/profil-form';
import { ProfilList } from './pages/profil-list/profil-list';

export const routes: Routes = [
	{ path: 'login', component: Login },
	{ path: 'dashboard', component: Dashboard, canActivate: [authGuard] },
	{ path: 'formulation', component: Formulation, canActivate: [authGuard] },
	{ path: 'formules', component: Historique, canActivate: [authGuard] },
	{ path: 'formules/:id', component: FormuleDetail, canActivate: [authGuard] },
	{ path: 'profils/nouveau', component: ProfilForm, canActivate: [authGuard] },
	{ path: 'profils', component: ProfilList, canActivate: [authGuard] },
	{ path: '', redirectTo: 'login', pathMatch: 'full' },
];
