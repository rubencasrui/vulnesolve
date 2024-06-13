import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearConfiguracionApiComponent } from './crear-configuracion-api.component';

describe('CrearConfiguracionApiComponent', () => {
  let component: CrearConfiguracionApiComponent;
  let fixture: ComponentFixture<CrearConfiguracionApiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearConfiguracionApiComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CrearConfiguracionApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
