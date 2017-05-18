import { TestBed, inject } from '@angular/core/testing';

import { ComponentVersionService } from './component-version.service';

describe('ComponentVersionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ComponentVersionService]
    });
  });

  it('should be created', inject([ComponentVersionService], (service: ComponentVersionService) => {
    expect(service).toBeTruthy();
  }));
});
