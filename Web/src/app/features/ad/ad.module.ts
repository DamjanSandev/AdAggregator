import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdRoutingModule} from './ad-routing.module';
import {AdListComponent} from './ad-list/ad-list.component';
import {SharedModule} from "../../shared/shared.module";


@NgModule({
    declarations: [
        AdListComponent
    ],
    imports: [
        CommonModule,
        AdRoutingModule,
        SharedModule
    ]
})
export class AdModule {
}
