import { ComponentVersion } from './component-version';

import { ProductComponent } from './product-component';

const component1: ProductComponent = new ProductComponent();
component1.id = 1;

const component2: ProductComponent = new ProductComponent();
component2.id = 2;

export const COMPONENTS_VERSIONS: ComponentVersion[] = [{
    'id': 1,
    'major': '1',
    'minor': '0',
    'release': '0',
    'creation': '27/08/2012',
    'death': '02/04/2014',
    'component': component1
}, {
    'id': 2,
    'major': '1',
    'minor': '1',
    'release': '3',
    'creation': '02/03/2013',
    'death': '12/11/2015',
    'component': component1
}, {
    'id': 3,
    'major': '1',
    'minor': '2',
    'release': '0',
    'creation': '03/11/2016',
    'death': null,
    'component': component1
}, {
    'id': 4,
    'major': '2',
    'minor': '0',
    'release': '2',
    'creation': '09/06/2016',
    'death': null,
    'component': component2
}, {
    'id': 5,
    'major': '2',
    'release': '1',
    'minor': '1',
    'creation': '09/06/2016',
    'death': null,
    'component': component2
}]
/*
, {
  'id': 5,
  'name': 'Jodie',
  'description': 'Simpole',
  'creation': 'jsimpole4@abc.net.au',
  'death': null
}, {
  'id': 6,
  'name': 'Cherey',
  'description': 'Goodge',
  'creation': 'cgoodge5@51.la',
  'death': 'Female'
}, {
  'id': 7,
  'name': 'Reinaldo',
  'description': 'Gauler',
  'creation': 'rgauler6@buzzfeed.com',
  'death': 'Male'
}, {
  'id': 8,
  'name': 'Torr',
  'description': 'Mitroshinov',
  'creation': 'tmitroshinov7@topsy.com',
  'death': 'Male'
}, {
  'id': 9,
  'name': 'Lexie',
  'description': 'Nilles',
  'creation': 'lnilles8@discuz.net',
  'death': 'Female'
}, {
  'id': 10,
  'name': 'Normy',
  'description': 'Hold',
  'creation': 'nhold9@earthlink.net',
  'death': 'Male'
}, {
  'id': 11,
  'name': 'Judy',
  'description': 'Leaney',
  'creation': 'jleaneya@last.fm',
  'death': null
}, {
  'id': 12,
  'name': 'Eduard',
  'description': 'Lumsdon',
  'creation': 'elumsdonb@zdnet.com',
  'death': 'Male'
}, {
  'id': 13,
  'name': 'Zorine',
  'description': 'Clementi',
  'creation': 'zclementic@blogspot.com',
  'death': 'Female'
}, {
  'id': 14,
  'name': 'Sigismundo',
  'description': 'Marjoram',
  'creation': 'smarjoramd@wunderground.com',
  'death': 'Male'
}, {
  'id': 15,
  'name': 'Felecia',
  'description': 'Heathcott',
  'creation': 'fheathcotte@geocities.com',
  'death': 'Female'
}];*/