import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdListComponent} from './ad-list/ad-list.component';
import {AuthGuard} from '../../core/guards/auth.guard';
import {AdFavouritesComponent} from './ad-favourites/ad-favourites.component';
import {AdDetailsComponent} from './ad-details/ad-details.component';

const routes: Routes = [
  {path: '', component: AdListComponent, canActivate: [AuthGuard]},
  {path: 'favourites', component: AdFavouritesComponent, canActivate: [AuthGuard]},
  {path: 'details/:id', component: AdDetailsComponent, canActivate: [AuthGuard]}
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdRoutingModule {
}
