import {AdResponse} from './AdResponse';

export interface InteractionResponse {
  id: number;
  userUsername: string;
  ad: AdResponse;
  interactionType: string;
  strength: number;
}
