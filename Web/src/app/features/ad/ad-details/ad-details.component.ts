import {AfterContentInit, AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {AdResponse} from '../../../models/AdResponse';
import {AdService} from '../../../core/services/ad.service';
import {ActivatedRoute} from '@angular/router';
import {filter, map, Subject, Subscription, switchMap, takeUntil} from 'rxjs';
import {InteractionService} from '../../../core/services/interaction.service';
import {StorageService} from '../../../core/services/storage.service';
import {InteractionRequest} from '../../../models/InteractionRequest';

@Component({
  selector: 'app-ad-details',
  standalone: false,
  templateUrl: './ad-details.component.html',
  styleUrl: './ad-details.component.css'
})
export class AdDetailsComponent implements OnInit, OnDestroy {

  ad: AdResponse | null = null;
  private destroy$ = new Subject<void>();


  constructor(private adService: AdService, private route: ActivatedRoute, private interactionService: InteractionService, private storage: StorageService) {
  }

  ngOnInit(): void {
    const id = +this.route.snapshot.params['id'];
    this.adService.getAdById(id).pipe(takeUntil(this.destroy$)).subscribe(
      ad => {
        this.ad = ad;
        const username = this.storage.getItem<string>('username');

        if (username && this.ad) {
          const req: InteractionRequest = {
            userUsername: username,
            adId: this.ad.id,
            interactionType: 'CLICK'
          };
          this.interactionService.addClick(req).pipe(takeUntil(this.destroy$)).subscribe();
        }
      }
    );
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
