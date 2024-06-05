import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadisticasV2Component } from './estadisticas-v2.component';

describe('EstadisticasV2Component', () => {
  let component: EstadisticasV2Component;
  let fixture: ComponentFixture<EstadisticasV2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadisticasV2Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EstadisticasV2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
