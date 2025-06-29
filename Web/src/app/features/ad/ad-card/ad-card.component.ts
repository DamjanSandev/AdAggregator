import {
  Component,
  EventEmitter,
  Input,
  Output
} from '@angular/core';
import {AdResponse} from '../../../models/AdResponse';
import {Router} from '@angular/router';
import {AdService} from '../../../core/services/ad.service';
import {InteractionResponse} from '../../../models/InteractionResponse';

@Component({
  selector: 'app-ad-card',
  templateUrl: './ad-card.component.html',
  styleUrls: ['./ad-card.component.css'],
  standalone: false
})
export class AdCardComponent {
  @Input() ad!: AdResponse;

  @Input() interaction: InteractionResponse | null = null;

  @Input() isInteraction = false;

  @Input() isFavorited = false;

  @Output() toggleFavorite = new EventEmitter<any>();


  constructor(private router: Router, private adService: AdService) {
  }

  onHeartClick(ev: MouseEvent) {
    ev.stopPropagation();
    this.isInteraction ? this.toggleFavorite.emit(this.interaction) : this.toggleFavorite.emit(this.ad);
  }

  gotoDetails() {
    this.router.navigate(['/ads/details', this.ad.id]);
  }
}
