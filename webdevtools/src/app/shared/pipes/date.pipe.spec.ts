import { DatePipe } from './date.pipe';

describe('DatePipe', () => {
  it('create an instance', () => {
    const pipe = new DatePipe();
    expect(pipe).toBeTruthy();
  });

  it('formats date with pattern dd/mm/yyyy', () => {
    const pipe = new DatePipe();
    const d = new Date('09/06/1986');
    expect(pipe.extractDate(d)).toBe('09/06/1986');
  });

  it('formats time with pattern HH:MM:ss', () => {
    const pipe = new DatePipe();
    const d = new Date('09/06/1986');
    expect(pipe.extractDate(d)).toBe('09/06/1986');
  });
});
