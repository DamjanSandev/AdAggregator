export enum EmissionType {
  EURO1 = 'EURO1',
  EURO2 = 'EURO2',
  EURO3 = 'EURO3',
  EURO4 = 'EURO4',
  EURO5 = 'EURO5',
  EURO6 = 'EURO6',
  OTHER = 'OTHER'
}

export const EmissionTypeLabels: Record<EmissionType, string> = {
  [EmissionType.EURO1]: 'Еуро 1',
  [EmissionType.EURO2]: 'Еуро 2',
  [EmissionType.EURO3]: 'Еуро 3',
  [EmissionType.EURO4]: 'Еуро 4',
  [EmissionType.EURO5]: 'Еуро 5',
  [EmissionType.EURO6]: 'Еуро 6',
  [EmissionType.OTHER]: 'Останато'
};
