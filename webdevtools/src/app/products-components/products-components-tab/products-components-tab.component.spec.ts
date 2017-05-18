import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsComponentsTabComponent } from './products-components-tab.component';

describe('ProductsComponentsTabComponent', () => {
  let component: ProductsComponentsTabComponent;
  let fixture: ComponentFixture<ProductsComponentsTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductsComponentsTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductsComponentsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
