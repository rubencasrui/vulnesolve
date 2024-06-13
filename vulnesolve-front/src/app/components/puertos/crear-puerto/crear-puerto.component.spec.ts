import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearPuertoComponent } from './crear-puerto.component';

describe('CrearPuertoComponent', () => {
  let component: CrearPuertoComponent;
  let fixture: ComponentFixture<CrearPuertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearPuertoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CrearPuertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
