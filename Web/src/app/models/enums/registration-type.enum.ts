export enum RegistrationType {
  MACEDONIAN = 'MACEDONIAN',
  FOREIGN = 'FOREIGN'
}

export const RegistrationTypeLabels: Record<RegistrationType, string> = {
  [RegistrationType.MACEDONIAN]: 'Македонска',
  [RegistrationType.FOREIGN]: 'Странска'
};
