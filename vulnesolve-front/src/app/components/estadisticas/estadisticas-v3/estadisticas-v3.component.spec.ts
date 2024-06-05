import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadisticasV3Component } from './estadisticas-v3.component';

describe('EstadisticasV3Component', () => {
  let component: EstadisticasV3Component;
  let fixture: ComponentFixture<EstadisticasV3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadisticasV3Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EstadisticasV3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
