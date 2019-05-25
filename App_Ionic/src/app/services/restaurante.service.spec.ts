import { TestBed } from '@angular/core/testing';

import { RestauranteService } from './restaurante.service';

describe('RestauranteService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RestauranteService = TestBed.get(RestauranteService);
    expect(service).toBeTruthy();
  });
});
