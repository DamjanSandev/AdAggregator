import {Component, OnDestroy, OnInit} from '@angular/core';
import {AdResponse} from '../../../models/AdResponse';
import {AdService} from '../../../core/services/ad.service';
import {ActivatedRoute} from '@angular/router';
import {filter, map, Subject, Subscription, switchMap, takeUntil} from 'rxjs';

@Component({
  selector: 'app-ad-details',
  standalone: false,
  templateUrl: './ad-details.component.html',
  styleUrl: './ad-details.component.css'
})
export class AdDetailsComponent implements OnInit, OnDestroy {

  ad: AdResponse | null = null;
  private destroy$ = new Subject<void>();


  constructor(private adService: AdService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const id = +this.route.snapshot.params['id'];
    this.adService.getAdById(id).pipe(takeUntil(this.destroy$)).subscribe(ad => this.ad = ad);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
