import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsComponentsListComponent } from './products-components-list.component';

describe('ProductsComponentsListComponent', () => {
  let component: ProductsComponentsListComponent;
  let fixture: ComponentFixture<ProductsComponentsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductsComponentsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductsComponentsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
