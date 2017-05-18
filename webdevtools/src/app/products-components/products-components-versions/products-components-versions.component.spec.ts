import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsComponentsVersionsComponent } from './products-components-versions.component';

describe('ProductsComponentsVersionsComponent', () => {
  let component: ProductsComponentsVersionsComponent;
  let fixture: ComponentFixture<ProductsComponentsVersionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductsComponentsVersionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductsComponentsVersionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
