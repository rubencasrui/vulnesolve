import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarPuertoComponent } from './editar-puerto.component';

describe('EditarPuertoComponent', () => {
  let component: EditarPuertoComponent;
  let fixture: ComponentFixture<EditarPuertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarPuertoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditarPuertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
