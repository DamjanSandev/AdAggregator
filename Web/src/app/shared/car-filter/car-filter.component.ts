import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AdSearchFilter} from '../../models/AdSearchFilter';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Brand} from '../../models/Brand';
import {Model} from '../../models/Model';
import {EmissionType, EmissionTypeLabels} from '../../models/enums/emission-type.enum';
import {BodyType, BodyTypeLabels} from '../../models/enums/body-type.enum';
import {FuelType, FuelTypeLabels} from '../../models/enums/fuel-type.enum';
import {RegistrationType, RegistrationTypeLabels} from '../../models/enums/registration-type.enum';
import {TransmissionType, TransmissionTypeLabels} from '../../models/enums/transmission-type.enum';
import {debounceTime} from 'rxjs';
import {BrandService} from '../../core/services/brand.service';
import {ModelService} from '../../core/services/model.service';

@Component({
  selector: 'app-car-filter',
  standalone: false,
  templateUrl: './car-filter.component.html',
  styleUrl: './car-filter.component.css'
})
export class CarFilterComponent implements OnInit{
  @Output() filterChange = new EventEmitter<AdSearchFilter>();

  form: FormGroup;
  brands: Brand[] = [];
  models: Model[] = [];

  emissionTypes = Object.values(EmissionType);
  emissionTypeLabels = EmissionTypeLabels;
  bodyTypes = Object.values(BodyType);
  bodyTypeLabels = BodyTypeLabels;
  fuelTypes = Object.values(FuelType);
  fuelTypeLabels = FuelTypeLabels;
  registrationTypes = Object.values(RegistrationType);
  registrationTypeLabels = RegistrationTypeLabels;
  transmissionTypes = Object.values(TransmissionType);
  transmissionTypeLabels = TransmissionTypeLabels;

  constructor(
    private fb: FormBuilder,
    private brandService: BrandService,
    private modelService: ModelService
  ) {
    this.form = this.fb.group({
      brand: [''],
      model: [''],
      fromYear: [''],
      toYear: [''],
      fuelType: [''],
      transmission: [''],
      bodyType: [''],
      color: [''],
      registrationType: [''],
      emissionType: [''],
      fromKilometers: [''],
      toKilometers: [''],
      enginePower: ['']
    });
  }

  ngOnInit() {
    this.brandService.getBrands().subscribe(bs => this.brands = bs);

    this.form.get('brand')!.valueChanges.subscribe(brand => {
      this.models = [];
      this.form.patchValue({ model: '' });
      if (brand) {
        this.modelService.getModelsByBrand(brand)
          .subscribe(ms => this.models = ms);
      }
    });

    this.form.valueChanges.pipe(
      debounceTime(300)
    ).subscribe(val => {
      const filter: AdSearchFilter = { ...val };
      this.filterChange.emit(filter);
    });
  }
}
