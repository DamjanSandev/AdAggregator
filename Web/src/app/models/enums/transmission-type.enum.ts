export enum TransmissionType {
  MANUAL = 'MANUAL',
  AUTOMATIC = 'AUTOMATIC',
  SEMI_AUTOMATIC = 'SEMI_AUTOMATIC'
}

export const TransmissionTypeLabels: Record<TransmissionType, string> = {
  [TransmissionType.MANUAL]: 'Рачен',
  [TransmissionType.AUTOMATIC]: 'Автоматски',
  [TransmissionType.SEMI_AUTOMATIC]: 'Полуавтоматски'
};
