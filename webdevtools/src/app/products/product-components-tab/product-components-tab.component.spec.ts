import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductComponentsTabComponent } from './product-components-tab.component';

describe('ProductComponentsTabComponent', () => {
  let component: ProductComponentsTabComponent;
  let fixture: ComponentFixture<ProductComponentsTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductComponentsTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductComponentsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
