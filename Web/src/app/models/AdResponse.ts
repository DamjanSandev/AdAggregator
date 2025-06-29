import {FuelType} from './enums/fuel-type.enum';
import {TransmissionType} from './enums/transmission-type.enum';
import {BodyType} from './enums/body-type.enum';
import {RegistrationType} from './enums/registration-type.enum';
import {EmissionType} from './enums/emission-type.enum';

export interface AdResponse {
  id: number,
  brand: string,
  model: string,
  year: number,
  fuelType: FuelType,
  kilometers: number,
  transmission: TransmissionType,
  bodyType: BodyType,
  color: string,
  registrationType: RegistrationType,
  registeredUntil: Date,
  enginePower: string,
  emissionType: EmissionType,
  description: string,
  photoUrl: string,
  price: number
}


