import {Component, OnInit} from '@angular/core';
import {AdService} from '../../../core/services/ad.service';
import {InteractionService} from '../../../core/services/interaction.service';
import {StorageService} from '../../../core/services/storage.service';
import {AdResponse} from '../../../models/AdResponse';
import {AdSearchFilter} from '../../../models/AdSearchFilter';
import {InteractionRequest} from '../../../models/InteractionRequest';
import {PreferenceService} from '../../../core/services/preference.service';

type ViewTab = 'ads' | 'recommended';


@Component({
  selector: 'app-ad-list',
  templateUrl: './ad-list.component.html',
  standalone: false,
  styleUrls: ['./ad-list.component.css']
})
export class AdListComponent implements OnInit {
  ads: AdResponse[] = [];
  totalPages = 0;
  currentPage = 0;
  filters: AdSearchFilter = {};
  favorites = new Map<number, number>();
  view: ViewTab = 'ads';

  constructor(
    private adService: AdService,
    private interactionService: InteractionService,
    private storage: StorageService,
    private preferenceService: PreferenceService
  ) {
  }

  ngOnInit() {
    this.loadFavorites();
    this.loadAds();
  }

  private loadFavorites() {
    const username = this.storage.getItem<string>('username');
    if (!username) return;
    this.interactionService.getFavorites(username)
      .subscribe(interactions => {
        interactions.forEach(i => this.favorites.set(i.ad.id, i.id));
      });
  }

  loadAds(page: number = 0, size: number = 10) {
    this.adService.getPaginatedAds(this.filters, page, size)
      .subscribe(resp => {
        this.ads = resp.content;
        this.totalPages = resp.totalPages;
        this.currentPage = resp.number;
      });
  }

  onPageChange(page: number) {
    this.loadAds(page);
  }

  filter(newFilters: AdSearchFilter) {
    this.filters = newFilters;
    this.loadAds();
  }

  isFavorite(ad: AdResponse): boolean {
    return this.favorites.has(ad.id);
  }

  toggleFavorite(ad: AdResponse) {
    const username = this.storage.getItem<string>('username');
    if (!username) return;

    if (this.isFavorite(ad)) {
      const interactionId = this.favorites.get(ad.id)!;
      this.interactionService.removeFavorite(interactionId)
        .subscribe(() => this.favorites.delete(ad.id));
    } else {
      const req: InteractionRequest = {
        userUsername: username,
        adId: ad.id,
        interactionType: 'FAV'
      };
      this.interactionService.addFavorite(req)
        .subscribe(resp => this.favorites.set(ad.id, resp.id));
    }
  }

  selectTab(tab: ViewTab) {
    this.view = tab;
    switch (tab) {
      case 'ads':
        this.loadAds();
        break;
      case 'recommended':
        this.loadRecommended();
        break;
    }
  }

  loadRecommended() {
    const username = this.storage.getItem<string>('username');
    if (!username) {
      this.ads = [];
      return;
    }
    this.preferenceService.getUserRecommendations(username)
      .subscribe({
        next: (response) => {
          this.ads = response;
          this.totalPages = 1;
          this.currentPage = 0;
        },
        error: (error) => {
          this.ads = [];
          console.error('Error loading recommendations:', error);
        }
      });
  }

  isActive(tab: ViewTab) {
    return this.view === tab;
  }
}
