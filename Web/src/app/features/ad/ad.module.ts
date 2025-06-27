import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdRoutingModule} from './ad-routing.module';
import {AdListComponent} from './ad-list/ad-list.component';
import {SharedModule} from "../../shared/shared.module";
import { AdFavouritesComponent } from './ad-favourites/ad-favourites.component';
import { AdDetailsComponent } from './ad-details/ad-details.component';
import { AdCardComponent } from './ad-card/ad-card.component';


@NgModule({
    declarations: [
        AdListComponent,
        AdFavouritesComponent,
        AdDetailsComponent,
        AdCardComponent
    ],
    imports: [
        CommonModule,
        AdRoutingModule,
        SharedModule
    ]
})
export class AdModule {
}
