export enum BodyType {
  SMALL_CITY_CAR = 'SMALL_CITY_CAR',
  HATCHBACK = 'HATCHBACK',
  SEDAN = 'SEDAN',
  CARAVAN = 'CARAVAN',
  MPV = 'MPV',
  SUV = 'SUV',
  CABRIOLET = 'CABRIOLET',
  COUPE = 'COUPE',
  OTHER = 'OTHER'
}

export const BodyTypeLabels: Record<BodyType, string> = {
  [BodyType.SMALL_CITY_CAR]: 'Мали градски',
  [BodyType.HATCHBACK]: 'Хеџбек',
  [BodyType.SEDAN]: 'Седани',
  [BodyType.CARAVAN]: 'Каравани',
  [BodyType.MPV]: 'Моноволумен',
  [BodyType.SUV]: 'Теренци - SUV',
  [BodyType.CABRIOLET]: 'Кабриолети',
  [BodyType.COUPE]: 'Купеа',
  [BodyType.OTHER]: 'Останато'
};
