import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsComponentsComponent } from './products-components.component';

describe('ProductsComponentsComponent', () => {
  let component: ProductsComponentsComponent;
  let fixture: ComponentFixture<ProductsComponentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductsComponentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductsComponentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
