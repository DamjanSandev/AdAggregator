import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {PaginationComponent} from './pagination/pagination.component';
import { CarFilterComponent } from './car-filter/car-filter.component';
import {ReactiveFormsModule} from '@angular/forms';



@NgModule({
  declarations: [PaginationComponent, CarFilterComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [PaginationComponent, CarFilterComponent]
})
export class SharedModule { }
