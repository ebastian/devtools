import { WebdevtoolsPage } from './app.po';

describe('webdevtools App', () => {
  let page: WebdevtoolsPage;

  beforeEach(() => {
    page = new WebdevtoolsPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
