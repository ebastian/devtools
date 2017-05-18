import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsComponentsFormComponent } from './products-components-form.component';

describe('ProductsComponentsFormComponent', () => {
  let component: ProductsComponentsFormComponent;
  let fixture: ComponentFixture<ProductsComponentsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductsComponentsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductsComponentsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
