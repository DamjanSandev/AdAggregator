export enum FuelType {
  DIESEL = 'DIESEL',
  PETROL = 'PETROL',
  PETROL_LPG = 'PETROL_LPG',
  HYBRID_DIESEL_ELECTRIC = 'HYBRID_DIESEL_ELECTRIC',
  HYBRID_PETROL_ELECTRIC = 'HYBRID_PETROL_ELECTRIC',
  ELECTRIC = 'ELECTRIC'
}

export const FuelTypeLabels: Record<FuelType, string> = {
  [FuelType.DIESEL]: 'Дизел',
  [FuelType.PETROL]: 'Бензин',
  [FuelType.PETROL_LPG]: 'Бензин / Плин',
  [FuelType.HYBRID_DIESEL_ELECTRIC]: 'Хибрид (Дизел / Електро)',
  [FuelType.HYBRID_PETROL_ELECTRIC]: 'Хибрид (Бензин / Електро)',
  [FuelType.ELECTRIC]: 'Електричен автомобил'
};
