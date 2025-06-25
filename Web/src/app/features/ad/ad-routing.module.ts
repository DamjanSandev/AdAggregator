import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {AdListComponent} from './ad-list/ad-list.component';
import {AuthGuard} from '../../core/guards/auth.guard';

const routes: Routes = [
  {path: '', component: AdListComponent, canActivate: [AuthGuard]},
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdRoutingModule {
}
