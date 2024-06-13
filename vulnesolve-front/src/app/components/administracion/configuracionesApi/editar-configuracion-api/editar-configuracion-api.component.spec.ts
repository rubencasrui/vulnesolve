import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarConfiguracionApiComponent } from './editar-configuracion-api.component';

describe('EditarConfiguracionApiComponent', () => {
  let component: EditarConfiguracionApiComponent;
  let fixture: ComponentFixture<EditarConfiguracionApiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarConfiguracionApiComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditarConfiguracionApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
