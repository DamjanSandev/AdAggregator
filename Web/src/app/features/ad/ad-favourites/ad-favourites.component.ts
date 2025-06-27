import {Component, OnInit} from '@angular/core';
import {InteractionService} from '../../../core/services/interaction.service';
import {StorageService} from '../../../core/services/storage.service';
import {InteractionResponse} from '../../../models/InteractionResponse';

@Component({
  selector: 'app-ad-favourites',
  standalone: false,
  templateUrl: './ad-favourites.component.html',
  styleUrl: './ad-favourites.component.css'
})
export class AdFavouritesComponent implements OnInit {
  favourites: InteractionResponse[] = [];


  constructor(private interactionService: InteractionService, private storage: StorageService) {
  }

  ngOnInit(): void {
    this.loadFavourites();
  }

  loadFavourites(): void {
    const username = this.storage.getItem<string>('username');
    if (!username) return;
    this.interactionService.getFavorites(username).subscribe(response => {
      this.favourites = response;
    })
  }

  removeFavourite(interaction: InteractionResponse): void {
    this.interactionService.removeFavorite(interaction.id).subscribe(response => {
      this.loadFavourites();
    })
  }
}
