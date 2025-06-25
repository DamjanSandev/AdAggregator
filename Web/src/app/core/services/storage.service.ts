import {Inject, Injectable, PLATFORM_ID} from '@angular/core';
import {isPlatformBrowser} from '@angular/common';

@Injectable({providedIn: 'root'})
export class StorageService {
  constructor(@Inject(PLATFORM_ID) private platformId: object) {
  }

  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  setItem(key: string, value: unknown): void {
    if (!this.isBrowser()) return;
    if (typeof value === 'string') {
      localStorage.setItem(key, value);
    } else {
      localStorage.setItem(key, JSON.stringify(value));
    }
  }


  getItem<T>(key: string): T | null {
    if (!this.isBrowser()) return null;
    const raw = localStorage.getItem(key);

    try {
      if (raw && /^[{\["]/.test(raw)) {
        return JSON.parse(raw) as T;
      } else {
        return raw as unknown as T;
      }
    } catch (e) {
      console.warn(`Error parsing localStorage item ${key}:`, e);
      return null;
    }
  }


  removeItem(key: string): void {
    if (this.isBrowser()) {
      localStorage.removeItem(key);
    }
  }

  clear(): void {
    if (this.isBrowser()) {
      localStorage.clear();
    }
  }
}
