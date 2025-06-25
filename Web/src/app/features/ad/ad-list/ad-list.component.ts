import {Component, OnInit} from '@angular/core';
import {AdService} from '../../../core/services/ad.service';
import {AdResponse} from '../../../models/AdResponse';
import {AdSearchFilter} from '../../../models/AdSearchFilter';

@Component({
  selector: 'app-ad-list',
  standalone: false,
  templateUrl: './ad-list.component.html',
  styleUrl: './ad-list.component.css'
})
export class AdListComponent implements OnInit {
  ads: AdResponse[] = [];
  totalPages = 0;
  currentPage = 0;
  filters: AdSearchFilter = {}

  constructor(private adService: AdService) {
  }

  ngOnInit() {
    this.loadAds();
  }

  loadAds(page: number = 0, size: number = 10) {
    this.adService.getPaginatedAds(this.filters, page, size).subscribe(response => {
      this.ads = response.content;
      this.totalPages = response.totalPages;
      this.currentPage = response.number;
    })
  }

  onPageChange(newPage: number): void {
    this.loadAds(newPage);
  }
}
