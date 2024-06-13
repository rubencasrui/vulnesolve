import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfiguracionApiComponent } from './configuracion-api.component';

describe('ConfiguracionApiComponent', () => {
  let component: ConfiguracionApiComponent;
  let fixture: ComponentFixture<ConfiguracionApiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfiguracionApiComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConfiguracionApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
