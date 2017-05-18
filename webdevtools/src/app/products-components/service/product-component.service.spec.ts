import { TestBed, inject } from '@angular/core/testing';

import { ProductComponentService } from './product-component.service';

describe('ProductComponentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProductComponentService]
    });
  });

  it('should be created', inject([ProductComponentService], (service: ProductComponentService) => {
    expect(service).toBeTruthy();
  }));
});
