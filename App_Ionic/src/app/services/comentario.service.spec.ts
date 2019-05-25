import { TestBed } from '@angular/core/testing';

import { ComentarioService } from './comentario.service';

describe('ComentarioService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ComentarioService = TestBed.get(ComentarioService);
    expect(service).toBeTruthy();
  });
});
